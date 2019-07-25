package com.linziyi.order.controller;

import com.linziyi.order.dto.OrderDTO;
import com.linziyi.order.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class SendMessageController {
    @Autowired
    private StreamClient streamClient;

    /**
     * 发送字符串
     */
//    @GetMapping("/sendMessage")
//    public void process(){
//        String message = "now: " + new Date();
//        this.streamClient.output().send(MessageBuilder.withPayload(message).build());
//    }

    /**
     * 发送orderDTO对象
     */
    @GetMapping("/sendMessage")
    public void process(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("111111");
        orderDTO.setBuyerName("test");
        this.streamClient.output().send(MessageBuilder.withPayload(orderDTO).build());
    }
}
