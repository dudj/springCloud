package com.linziyi.product.controller;

import com.linziyi.product.common.ProduceStockInput;
import com.linziyi.product.common.ProductInfoOutput;
import com.linziyi.product.dataobject.ProductCategory;
import com.linziyi.product.dataobject.ProductInfo;
import com.linziyi.product.dto.CartDTO;
import com.linziyi.product.service.ProductCategoryService;
import com.linziyi.product.service.ProductInfoService;
import com.linziyi.product.utils.ResultVoUtil;
import com.linziyi.product.vo.ProductInfoVo;
import com.linziyi.product.vo.ProductVo;
import com.linziyi.product.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductInfoService productInfoService;
    /**
     * 1.查询商品列表
     * 2.获取类目type列表
     * 3.查询类目
     * 4.构造数据
     */
    @GetMapping("/list")
    public ResultVo<ProductVo> list(){
        //1.查询商品列表
        List<ProductInfo> upAll = this.productInfoService.findUpAll();
        //2.获取类目type列表
        List<Integer> categoryTypes = upAll.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());
        //3.查询类目
        List<ProductCategory> byCategoryTypeIn = this.productCategoryService.findByCategoryTypeIn(categoryTypes);
        //4.构造数据
        List<ProductVo> productVoList = new ArrayList<>();
        for(ProductCategory categoryList: byCategoryTypeIn){
            ProductVo productVo = new ProductVo();
            productVo.setCategoryName(categoryList.getCategoryName());
            productVo.setCategoryType(categoryList.getCategoryType());
            List<ProductInfoVo> productInfoVoList = new ArrayList<>();
            for(ProductInfo infoList: upAll){
                if(categoryList.getCategoryType().equals(infoList.getCategoryType())){
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(infoList, productInfoVo);
                    /*productInfoVo.setProductDescription(infoList.getProductDescription());
                    productInfoVo.setProductIcon(infoList.getProductIcon());
                    productInfoVo.setProductId(infoList.getProductId());
                    productInfoVo.setProductName(infoList.getProductName());
                    productInfoVo.setProductPrice(infoList.getProductPrice());*/
                    productInfoVoList.add(productInfoVo);
                }
            }
            productVo.setProductInfoVoList(productInfoVoList);
            productVoList.add(productVo);
        }
        return ResultVoUtil.success(productVoList);
    }

    /**
     * 根据ID 查询产品列表 给订单服务使用
     * @return
     */
    @PostMapping("/listForId")
    public List<ProductInfoOutput> listForId(@RequestBody List<String> productIdList){
        return this.productInfoService.findDataByIds(productIdList);
    }

    @PostMapping("/produceStock")
    public void produceStock(@RequestBody List<ProduceStockInput> produceStockInputList){
        productInfoService.produceStock(produceStockInputList);
    }
}
