package com.linziyi.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    public void test(){
        //http://113.247.241.66:8090/sany-card/pushToAppointUser?info=123&status=123&infoByEmployeeNum=456
        String info = "信息";
        Integer status = 1;
        Integer infoByEmployeeNum = 456;
        StringBuffer str = new StringBuffer("http://113.247.241.66:8090/sany-card/pushToAppointUser?info=");
        str.append(info);
        str.append("&status=");
        str.append(status);
        str.append("&infoByEmployeeNum=");
        str.append(infoByEmployeeNum);
        System.out.println("str="+str);
    }

}
