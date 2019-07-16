package com.linziyi.product.service.impl;

import com.linziyi.product.dataobject.ProductCategory;
import com.linziyi.product.repository.ProductCategoryRepository;
import com.linziyi.product.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return this.productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
    }
}
