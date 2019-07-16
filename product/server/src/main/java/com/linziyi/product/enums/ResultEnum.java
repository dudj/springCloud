package com.linziyi.product.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXISTS(0,"商品不存在"),
    PRODUCT_STOCK_NOT_ENOUGH(1,"库存不足");
    private Integer code;
    private String msg;
    ResultEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
