package com.linziyi.order.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.linziyi.order.dataobject.OrderDetail;
import com.linziyi.order.dto.OrderDTO;
import com.linziyi.order.enums.ResultEnum;
import com.linziyi.order.exception.OrderException;
import com.linziyi.order.form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
@Slf4j
public class OrderFormToOrderDTOConverter {
    public static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail> orderDetailsList = new ArrayList<OrderDetail>();
        //使用google下gson工具来转化
        try{
            orderDetailsList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("【订单转化】数据转化出错：" + orderForm.getItems());
            throw new OrderException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailsList);
        return orderDTO;
    }
}
