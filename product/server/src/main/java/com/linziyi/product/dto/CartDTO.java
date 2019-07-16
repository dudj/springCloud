package com.linziyi.product.dto;

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
}
