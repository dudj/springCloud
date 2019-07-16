package com.linziyi.order.dto;

import lombok.Data;

@Data
public class CartDTO {
    /**
     * 商品编号ID
     */
    private String productId;

    /**
     * 商品购买数量
     */
    private Integer productQuantity;
    public CartDTO(){

    }
    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
