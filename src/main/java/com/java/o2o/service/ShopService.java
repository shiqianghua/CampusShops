package com.java.o2o.service;

import com.java.o2o.dto.ImageHolder;
import com.java.o2o.dto.ShopExecution;
import com.java.o2o.entity.Shop;
import com.java.o2o.exceptions.ShopOperationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public interface ShopService {
    //根据分页返回shopCondition分页返回相应的店铺列表
    public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSzie);

//    通过店铺id获取店铺信息
    Shop getByShopId(long shopId);

//    更新店铺信息，包括对图片处理
   // ShopExecution modifyShop(Shop shop,InputStream shopImgInputStream,String fileName) throws ShopOperationException;
   ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    //ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws  ShopOperationException;
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws  ShopOperationException;
}
