package com.imooc.sell.service.impl;

import com.imooc.sell.dao.ProductCategoryDao;
import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.enmus.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryImpl implements CategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return productCategoryDao.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryDao.findAll();
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        ProductCategory result = productCategoryDao.findByCategoryTypey(productCategory.getCategoryType());
        if(result != null){
            throw new SellException(ResultEnum.CATGEGORY_EXIST);
        }
        return productCategoryDao.save(productCategory);
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryDao.findByCategoryTypeIn(categoryTypeList);
    }
}
