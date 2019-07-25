package com.linziyi.order.message;

import com.linziyi.order.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReciver {
//    @StreamListener(StreamClient.INPUT)
//    public void process(Object message){
//        log.info("StreamReciver:{}" + message);
//    }
    @StreamListener(StreamClient.INPUT)
    @SendTo(StreamClient.INPUT2)
    public String process(OrderDTO message){
        log.info("StreamReciverObj:{}" + message);
        return "recived...";
    }
    @StreamListener(StreamClient.INPUT2)
    public void process2(String message){
        log.info("StreamReciver2:{}" + message);
    }

}
