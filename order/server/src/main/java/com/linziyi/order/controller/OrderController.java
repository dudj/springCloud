package com.linziyi.order.controller;

import com.linziyi.order.converter.OrderFormToOrderDTOConverter;
import com.linziyi.order.dto.OrderDTO;
import com.linziyi.order.enums.ResultEnum;
import com.linziyi.order.exception.OrderException;
import com.linziyi.order.form.OrderForm;
import com.linziyi.order.service.OrderMasterService;
import com.linziyi.order.utils.ResultVOUtil;
import com.linziyi.order.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderMasterService orderMasterService;
    @PostMapping("create")
    /**
     * 1.参数校验
     * 2.根据商品信息查询商品详细信息(调用商品的服务)
     * 3.计算总价
     * 4.扣除库存(调用商品服务)
     * 5.入库(订单创建和订单详情)
     */
    public ResultVO create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】订单参数有错误，orderForm={}",orderForm);
            throw new OrderException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        //数据转化
        OrderDTO orderDTO = OrderFormToOrderDTOConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车数据有错误");
            throw new OrderException(ResultEnum.CART_EMPTY);
        }
        OrderDTO dto = orderMasterService.create(orderDTO);
        if("".equals(dto.getOrderId())){
            log.error("【创建订单】失败");
            throw new OrderException(ResultEnum.ORDER_WAREHOUSING);
        }
        Map<String, String> map = new HashMap<>();
        map.put("orderId",dto.getOrderId());
        return ResultVOUtil.success(map);
    }

    /**
     * 完结订单
     * @param orderId
     * @return
     */
    @PostMapping("/finish")
    public ResultVO<OrderDTO> finish(@RequestParam("orderId") String orderId){
        return ResultVOUtil.success(orderMasterService.finish(orderId));
    }

}
