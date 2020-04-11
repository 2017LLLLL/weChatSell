package com.imooc.sell.dao;


import com.imooc.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/*
 *  类目Dao层
 */
public interface ProductCategoryDao extends JpaRepository<ProductCategory,Integer> {
    List<ProductCategory> findByCategoryTypeIn(List<Integer> list);
    ProductCategory findByCategoryTypey(Integer CategoruType);
}
