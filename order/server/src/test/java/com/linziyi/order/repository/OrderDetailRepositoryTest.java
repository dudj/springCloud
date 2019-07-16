package com.linziyi.order.repository;

import com.linziyi.order.OrderApplicationTests;
import com.linziyi.order.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
class OrderDetailRepositoryTest extends OrderApplicationTests {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void testSave(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId("1231");
        orderDetail.setDetailId("1");
        orderDetail.setProductIcon("aaaaaa");
        orderDetail.setProductId("157875227953464068");
        orderDetail.setProductName("慕斯蛋糕");
        orderDetail.setProductPrice(new BigDecimal(10.5));
        orderDetail.setProductQuantity(1);
        OrderDetail save = this.orderDetailRepository.save(orderDetail);
        Assert.assertTrue(save!=null);

    }
}