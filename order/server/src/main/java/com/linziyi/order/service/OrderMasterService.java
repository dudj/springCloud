package com.linziyi.order.service;

import com.linziyi.order.dto.OrderDTO;

public interface OrderMasterService {
    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    public OrderDTO create(OrderDTO orderDTO);
    /**
     * 完成订单(只能卖家操作)
     * @param orderId
     * @return
     */
    OrderDTO finish(String orderId);
}
