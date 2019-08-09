package com.java.o2o.service;

import com.java.o2o.dto.ImageHolder;
import com.java.o2o.dto.ProductExecution;
import com.java.o2o.entity.Product;
import com.java.o2o.exceptions.ProductOperationException;

import java.io.InputStream;
import java.util.List;

public interface ProductService {

    List<Product> getProductListMorePage(int limit,int offset);

//    查询商品列表并分页
    ProductExecution getProductList(Product productCondition,int pageIndex,int pageSize);

//    添加商品信息以及图片处理
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> imageHolderList) throws ProductOperationException;

//通过商品Id查询唯一的商品信息
    Product getProductById(long productId);

//    修改商品信息以图片处理
    ProductExecution modifyProduct(Product product,ImageHolder thumbnail,List<ImageHolder> productImageHolderList) throws ProductOperationException;

}
