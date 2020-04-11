package com.imooc.sell.controller;


import com.imooc.sell.VO.CategoryView;
import com.imooc.sell.VO.ProductInfoView;
import com.imooc.sell.VO.ResultView;
import com.imooc.sell.dao.ProductCategoryDao;
import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductInfoService;
import com.imooc.sell.util.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

/*
* 商品信息Controller
* */
@Controller
@RequestMapping("/buyer/product")
@CrossOrigin(allowCredentials="true",allowedHeaders="*")
public class ProductInfoController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/list")
    public ResultView getProduct(Map<String, Object> map){

        //1.查询上架商品
        List<ProductInfo> productInfoList = productInfoService.findAllByStatus();
        //2.查询所有类目（一次性查询）
        //使用java8 lambda表达式，把上架的数据类目取出来
        List<Integer> categoryTypeList = productInfoList.stream().map(e ->e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3.数据拼接，数据便利由大到小；先便利类目，在便利商品。
        //类目的type和商品的type相同的话就直接添加进去。
        List<CategoryView> categoryViews = new ArrayList<>();
        for(ProductCategory productCategory : categoryList){
            CategoryView category = new CategoryView();
            category.setCategoryType(productCategory.getCategoryType());
            category.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoView> productInfoViews = new ArrayList<>();
            for(ProductInfo productInfo : productInfoList){
                if(productCategory.getCategoryType().equals(productInfo.getCategoryType())){
                    ProductInfoView productInfoView = new ProductInfoView();
                    BeanUtils.copyProperties(productInfo,productInfoView);
                    productInfoViews.add(productInfoView);
                }
            }
            category.setFoods(productInfoViews);
            categoryViews.add(category);
        }
        /*返回信息*/
        return ResultUtils.success(categoryViews);
    }


    @GetMapping("/listModel")
    public ModelAndView getProductByModel(Map<String, Object> map){

        //1.查询上架商品
        List<ProductInfo> productInfoList = productInfoService.findAllByStatus();
        //2.查询所有类目（一次性查询）
        //使用java8 lambda表达式，把上架的数据类目取出来
        List<Integer> categoryTypeList = productInfoList.stream().map(e ->e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3.数据拼接，数据便利由大到小；先便利类目，在便利商品。
        //类目的type和商品的type相同的话就直接添加进去。
        List<CategoryView> categoryViews = new ArrayList<>();
        for(ProductCategory productCategory : categoryList){
            CategoryView category = new CategoryView();
            category.setCategoryType(productCategory.getCategoryType());
            category.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoView> productInfoViews = new ArrayList<>();
            for(ProductInfo productInfo : productInfoList){
                if(productCategory.getCategoryType().equals(productInfo.getCategoryType())){
                    ProductInfoView productInfoView = new ProductInfoView();
                    BeanUtils.copyProperties(productInfo,productInfoView);
                    productInfoViews.add(productInfoView);
                }
            }
            category.setFoods(productInfoViews);
            categoryViews.add(category);
        }
        /*返回信息*/
        map.put("productInfoPage",categoryViews);
        System.out.println(categoryViews);
        return new ModelAndView("pay/list",map);
    }
}
