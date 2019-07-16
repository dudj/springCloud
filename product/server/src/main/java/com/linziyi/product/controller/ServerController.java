package com.linziyi.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {
    @GetMapping("/test")
    public String Test(){
        return "this is Product Application";
    }
}
