package com.linziyi.order.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    NEW(0,"新订单"),
    COMPLEATE(1,"已完成"),
    CANCLE(2,"已取消");
    private Integer code;
    private String msg;
    OrderStatusEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
