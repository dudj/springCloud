package com.linziyi.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
public class HystrixController {
    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping("/getProductInfoList")
    public String getProductInfoList(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://localhost:8060/product/listForId",
                Arrays.asList("157875196366160022"),
                String.class);

    }
    private String fallback(){
        return "太拥挤，请稍后再试~~~";
    }
}
