package com.java.o2o.dao;

import com.java.o2o.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {

    List<ProductImg> queryProductImgList(long productId);

    /*批量添加商品详情图片*/
    int batchInsertProductImg(List<ProductImg> productImgList);

    /*刪除指定商品下的所有詳情圖*/
    int deleteProductImgByProductId(long product);


}
