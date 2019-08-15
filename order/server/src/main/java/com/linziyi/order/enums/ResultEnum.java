package com.linziyi.order.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PARAM_ERROR(1001,"参数错误"),
    CART_EMPTY(1002,"购物车信息为空"),
    PARAM_CONVERT(1003,"转换异常"),
    ORDER_WAREHOUSING(1004,"订单信息入库失败"),
    ORDER_NOT_EXISTS(1005,"订单不存在"),
    ORDER_STATUS_ERROR(1006,"订单状态有误"),
    ORDER_DETAIL_NOT_EXISTS(1007,"订单详情不存在"),
    ;
    private Integer code;
    private String message;
    ResultEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
