package com.java.o2o.dao;

import com.java.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

    List<Product> queryProductListMorePage(@Param("limit") int limit,@Param("offset") int offset);

    /*查询商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺Id，商品类别*/
    List<Product> queryProductList(@Param("productCondition") Product productCondition,
                                   @Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);

    /*查询对应的商品的总数*/
    int queryProductCount(@Param("productCondition") Product productCondition);

    /*通過productId查詢唯一的商品信息*/
    Product queryProductById(long productId);

    /*更新商品信息*/
    int updateProduct(Product product);

    /*删除商品类别之前，将商品类Id置为空*/
    int updateProductCategoryToNull(long productCategory);

    /*插入商品信息*/
    int insertProduct(Product product);
}
