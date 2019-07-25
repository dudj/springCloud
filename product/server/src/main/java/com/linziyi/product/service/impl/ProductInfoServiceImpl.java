package com.linziyi.product.service.impl;

import com.linziyi.product.common.ProduceStockInput;
import com.linziyi.product.common.ProductInfoOutput;
import com.linziyi.product.dataobject.ProductInfo;
import com.linziyi.product.enums.ProductStatusEnum;
import com.linziyi.product.enums.ResultEnum;
import com.linziyi.product.exception.ProductException;
import com.linziyi.product.repository.ProductInfoRepository;
import com.linziyi.product.service.ProductInfoService;
import com.linziyi.product.utils.JsonUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Autowired
    private AmqpTemplate amqpTemplate;
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
    public void produceStock(List<ProduceStockInput> produceStockInputList) {
        //发送mq消息
        List<ProductInfo> productInfoList = produceStockProcess(produceStockInputList);
        List<ProductInfoOutput> outputs = productInfoList.stream().map(e -> {
            ProductInfoOutput output = new ProductInfoOutput();
            BeanUtils.copyProperties(e, output);
            return output;
        }).collect(Collectors.toList());
        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(outputs));
    }

    @Transactional
    public List<ProductInfo> produceStockProcess(List<ProduceStockInput> produceStockInputList) {
        List<ProductInfo> productInfoList = new ArrayList<>();
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
            productInfoList.add(productInfo);
        }
        return productInfoList;
    }
}
