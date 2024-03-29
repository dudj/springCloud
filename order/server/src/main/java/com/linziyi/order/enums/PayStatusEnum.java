package com.linziyi.order.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum {
    WAIT(0,"未支付"),
    SUCCESS(1,"支付成功"),
    ERROR(2,"支付失败");
    private Integer code;
    private String msg;
    PayStatusEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
