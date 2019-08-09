package com.java.o2o.service;

import com.java.o2o.dto.ProductCategoryExecution;
import com.java.o2o.entity.ProductCategory;
import com.java.o2o.exceptions.ProductCategoryOperationException;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategory> getProductCategoryListAll();

    /*查询指定某个店铺下的所有商品类别信息*/

    List<ProductCategory> getProductCategoryList(long shopId);

    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;

    /*将此类别下的商品里的类别id置为空，在删除掉该商品类别*/
    ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId) throws ProductCategoryOperationException;
}
