package com.linziyi.product.client;

import com.linziyi.product.common.ProduceStockInput;
import com.linziyi.product.common.ProductInfoOutput;
import org.springframework.cloud.netflix.feign.FeignClient;
//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="product", fallback = ProductClient.ProductClientFallback.class)
public interface ProductClient {
    /**
     * 根据产品ID 查询产品详细信息 需要填写完整路径
     * @return
     */
    @PostMapping("/product/listForId")
    List<ProductInfoOutput> findDataByIds(@RequestBody List<String> productIdList);
    /**
     * 扣库存
     * @return
     */
    @PostMapping("/product/produceStock")
    void produceStock(@RequestBody List<ProduceStockInput> cartDTOList);
    //注意一定要加注解
    @Component
    static class ProductClientFallback implements ProductClient{

        @Override
        public List<ProductInfoOutput> findDataByIds(List<String> productIdList) {
            return null;
        }

        @Override
        public void produceStock(List<ProduceStockInput> cartDTOList) {

        }
    }
}
