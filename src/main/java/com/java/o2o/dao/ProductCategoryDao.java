package com.java.o2o.dao;

import com.java.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryDao {

    List<ProductCategory> queryProductCategoryListAll();

    //通过shop_id查询店铺类别
    List<ProductCategory> queryProductCategoryList(long ShopId);

    /*批量新增商品类别*/
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /*删除指定商品类别*/
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId,@Param("shopId") long shopId);
}
