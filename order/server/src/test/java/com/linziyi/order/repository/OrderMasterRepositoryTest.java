package com.linziyi.order.repository;

import com.linziyi.order.dataobject.OrderMaster;
import com.linziyi.order.enums.OrderStatusEnum;
import com.linziyi.order.enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.linziyi.order.OrderApplicationTests;

import java.math.BigDecimal;

@Component
class OrderMasterRepositoryTest extends OrderApplicationTests{
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1231");
        orderMaster.setBuyerAddress("北京市");
        orderMaster.setBuyerName("林子懿");
        orderMaster.setBuyerOpenid("asda78879789ads798");
        orderMaster.setBuyerPhone("13020078873");
        orderMaster.setOrderAmount(new BigDecimal(3.5));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        OrderMaster save = this.orderMasterRepository.save(orderMaster);
        Assert.assertTrue(save != null);
    }
}