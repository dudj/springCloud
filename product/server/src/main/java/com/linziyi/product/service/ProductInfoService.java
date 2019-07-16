package com.linziyi.product.service;

import com.linziyi.product.common.ProduceStockInput;
import com.linziyi.product.common.ProductInfoOutput;
import com.linziyi.product.dataobject.ProductInfo;
import com.linziyi.product.dto.CartDTO;

import java.util.List;

public interface ProductInfoService {
    /**
     * 查询所有商品上架列表
     */
    List<ProductInfo> findUpAll();

    /**
     * 根据ID 查询所对应的产品
     * @param productIdList
     * @return
     */
    List<ProductInfoOutput> findDataByIds(List<String> productIdList);

    /**
     * 减少库存
     * @param produceStockInputList
     */
    void produceStock(List<ProduceStockInput> produceStockInputList);
}
