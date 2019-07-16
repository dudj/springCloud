package com.linziyi.product.common;

import lombok.Data;

@Data
public class ProduceStockInput {
    /**
     * 商品编号ID
     */
    private String productId;
    /**
     * 商品购买数量
     */
    private Integer productQuantity;

    public ProduceStockInput() {
    }

    public ProduceStockInput(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
