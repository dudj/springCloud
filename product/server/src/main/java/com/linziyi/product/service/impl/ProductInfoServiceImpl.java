package com.linziyi.product.service.impl;

import com.linziyi.product.common.ProduceStockInput;
import com.linziyi.product.common.ProductInfoOutput;
import com.linziyi.product.dataobject.ProductInfo;
import com.linziyi.product.dto.CartDTO;
import com.linziyi.product.enums.ProductStatusEnum;
import com.linziyi.product.enums.ResultEnum;
import com.linziyi.product.exception.ProductException;
import com.linziyi.product.repository.ProductInfoRepository;
import com.linziyi.product.service.ProductInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Override
    public List<ProductInfo> findUpAll() {
        return this.productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfoOutput> findDataByIds(List<String> productIdList) {
        return this.productInfoRepository.findByProductIdIn(productIdList)
                .stream()
                .map(e -> {
                    ProductInfoOutput output = new ProductInfoOutput();
                    BeanUtils.copyProperties(e, output);
                    return output;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void produceStock(List<ProduceStockInput> produceStockInputList) {
        for(ProduceStockInput produceStockInput:produceStockInputList){
            produceStockInput.getProductId();
            Optional<ProductInfo> optionalProductInfo = productInfoRepository.findById(produceStockInput.getProductId());
            //验证商品是否存在
            if(!optionalProductInfo.isPresent()){
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXISTS);
            }
            ProductInfo productInfo = optionalProductInfo.get();
            Integer produceStock = productInfo.getProductStock() - produceStockInput.getProductQuantity();
            //验证库存是否足够
            if(produceStock < 0){
                throw new ProductException(ResultEnum.PRODUCT_STOCK_NOT_ENOUGH);
            }
            //更改库存
            productInfo.setProductStock(produceStock);
            productInfoRepository.save(productInfo);
        }
    }
}
