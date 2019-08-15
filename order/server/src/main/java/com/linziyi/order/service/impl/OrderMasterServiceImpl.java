package com.linziyi.order.service.impl;

import com.linziyi.order.dataobject.OrderDetail;
import com.linziyi.order.dataobject.OrderMaster;
import com.linziyi.order.dto.OrderDTO;
import com.linziyi.order.enums.OrderStatusEnum;
import com.linziyi.order.enums.PayStatusEnum;
import com.linziyi.order.enums.ResultEnum;
import com.linziyi.order.exception.OrderException;
import com.linziyi.order.repository.OrderDetailRepository;
import com.linziyi.order.repository.OrderMasterRepository;
import com.linziyi.order.service.OrderMasterService;
import com.linziyi.order.utils.KeyUtil;
import com.linziyi.product.client.ProductClient;
import com.linziyi.product.common.ProduceStockInput;
import com.linziyi.product.common.ProductInfoOutput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderMasterServiceImpl implements OrderMasterService {
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductClient productClient;

    /**
     * TODO 2.根据商品信息查询商品详细信息(调用商品的服务)
     * TODO 3.计算总价
     * TODO 4.扣除库存(调用商品服务)
     * 5.入库(订单创建和订单详情)
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.getUniqueKey();
        //根据商品信息查询商品详细信息(调用商品的服务)
        /*List list = new ArrayList();
        for (OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            list.add(orderDetail.getProductId());
        }*/
        List<String> productIdList = orderDTO.getOrderDetailList().stream().map(OrderDetail::getProductId).collect(Collectors.toList());
        List<ProductInfoOutput> productInfoList = productClient.findDataByIds(productIdList);
        //计算总价
        BigDecimal totalPrice = new BigDecimal(BigInteger.ZERO);
        for (ProductInfoOutput productInfo:productInfoList){
            for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
                if(orderDetail.getProductId().equals(productInfo.getProductId())){
                    //单价*数量
                    totalPrice = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(totalPrice);
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.getUniqueKey());
                    orderDetailRepository.save(orderDetail);
                }
            }
        }
        //扣除库存
        List<ProduceStockInput> produceStockInputList = orderDTO.getOrderDetailList().stream().map(e ->
                new ProduceStockInput(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productClient.produceStock(produceStockInputList);
        //先拷贝，防止数据覆盖(orderDTO)中有空
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(totalPrice);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        OrderMaster save = this.orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        //1.查询订单信息
        Optional<OrderMaster> orderMasterOptional = orderMasterRepository.findById(orderId);
        if(!orderMasterOptional.isPresent()){
            throw new OrderException(ResultEnum.ORDER_NOT_EXISTS);
        }
        //2.判断订单状态
        OrderMaster orderMaster = orderMasterOptional.get();
        if(OrderStatusEnum.NEW.getCode()!=orderMaster.getOrderStatus()){
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //3.修改订单状态为已完结
        orderMaster.setOrderStatus(OrderStatusEnum.COMPLEATE.getCode());
        orderMasterRepository.save(orderMaster);
        //4.查询订单详情
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXISTS);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

}
