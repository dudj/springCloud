package com.linziyi.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
/**
 * 接收mq消息
 */
public class MQRevicer {
//    1.@RabbitListener(queues = "myQueue")
//    2.自动创建队列@RabbitListener(queuesToDeclare = @Queue("myQueue"))
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")

    ))
    public void process(String message){
        log.info("message[]:"+message);
    }
}
