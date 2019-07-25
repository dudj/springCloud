package com.linziyi.order.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.linziyi.order.utils.JsonUtil;
import com.linziyi.product.common.ProductInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductInfoRevicer {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";
    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message){
        //message 转换成 ProductInfoOutput对象
        List<ProductInfoOutput> productInfoOutputList = (List<ProductInfoOutput>) JsonUtil.fromJson(message, new TypeReference<List<ProductInfoOutput>>() {});
        log.info("从队列【{}】接收到消息:{}","productInfo",productInfoOutputList);
        //存储到redis中
        for(ProductInfoOutput productInfoOutput:productInfoOutputList){
            stringRedisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE,productInfoOutput.getProductId()),String.valueOf(productInfoOutput.getProductStock()));
        }
    }
}
