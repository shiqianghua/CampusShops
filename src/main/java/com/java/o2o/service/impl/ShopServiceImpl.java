package com.java.o2o.service.impl;

import com.java.o2o.dao.ProductImgDao;
import com.java.o2o.dao.ShopDao;
import com.java.o2o.dto.ImageHolder;
import com.java.o2o.dto.ShopExecution;
import com.java.o2o.entity.Product;
import com.java.o2o.entity.ProductImg;
import com.java.o2o.entity.Shop;
import com.java.o2o.enums.ShopStateEnum;
import com.java.o2o.exceptions.ProductOperationException;
import com.java.o2o.exceptions.ShopOperationException;
import com.java.o2o.service.ShopService;
import com.java.o2o.util.ImageUtil;
import com.java.o2o.util.PageCalculate;
import com.java.o2o.util.PathUtil;

import javafx.scene.image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    @Autowired
    private ProductImgDao productImgDao;


//获取商品列表
    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSzie) {

        int rowIndex= PageCalculate.calculateRowIndex(pageIndex,pageSzie);
        List<Shop> shopList=shopDao.queryShopList(shopCondition,rowIndex,pageSzie);
        int count=shopDao.queryShopCount(shopCondition);
        ShopExecution se=new ShopExecution();

        if (shopList != null)
        {
            se.setShopList(shopList);
            se.setCount(count);
        }else
            {
                se.setState(ShopStateEnum.INNER_ERROR.getState());
            }

        return se;
    }

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
        try {
            if (shop == null || shop.getShopId() == null) {
                return new ShopExecution(ShopStateEnum.NULL_SHOP);
            }
            else
            {
                //      1.判断是否需要处理图片
                if (thumbnail.getImage() != null && thumbnail.getImageName()!= null && !"".equals(thumbnail.getImageName()))
                {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null)
                    {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }

                    addShopImg(shop, thumbnail);
                }

                //        2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0)
                {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }
                else
                {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS,shop);
                }
            }
        } catch (Exception e)
        {
            throw  new ShopOperationException("modifySHop error:"+e.getMessage());
        }
    }

    @Override
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
        if(shop==null || shop.getShopId()==null ){
            return new ShopExecution((ShopStateEnum.NULL_SHOP));
        }
        else
        {
            try {
//            给店铺信息初始值赋值
                shop.setEnableStatus(0);
                shop.setCreateTime(new Date());
                shop.setLastEditTime(new Date());
//            添加店铺信息
                int effectedNum=shopDao.insertShop(shop);

                if(effectedNum<=0)
                {
                    throw new ShopOperationException("创建店铺失败");
                }
                else
                {
                    //                    存储图片
                    try{
                        if(thumbnail.getImage() !=null )
                            addShopImg(shop, thumbnail);
                    }catch (Exception e)
                    {
                        throw new ShopOperationException("addShopImg error"+e.getMessage());
                    }
                    //                   更新店铺的图片地址
                    effectedNum=shopDao.updateShop(shop);
                    if(effectedNum<=0)
                    {
                        throw new ShopOperationException("更新图片地址失败");
                    }
                    else
                    {
                        shop=shopDao.queryByShopId(shop.getShopId());
                        return new ShopExecution(ShopStateEnum.SUCCESS,shop);
                    }

                }

            }catch (Exception e)
            {
                throw new ShopOperationException("addShop error:"+e.getMessage());
            }
        }
    }



    //private void  addShopImg(Shop shop,InputStream shopImginputStream,String fileName)
    private void  addShopImg(Shop shop,ImageHolder thumbnail)
    {
//        获取shop图片目录的相对值路径
        String dest= PathUtil.getShopImagePath(shop.getShopId());
//        String shopImgAddr= ImageUtil.generateThumbnail(shopImginputStream,fileName,dest);
        String shopImgAddr= ImageUtil.generateThumbnail(thumbnail,dest);
        shop.setShopImg(shopImgAddr);
    }

}






/*@Override
    public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException {

        try {
                if (shop == null || shop.getShopId() == null) {
                    return new ShopExecution(ShopStateEnum.NULL_SHOP);
                }
            else
               {
                //      1.判断是否需要处理图片
                if (shopImgInputStream != null && fileName != null && !"".equals(fileName))
                {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null)
                    {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }

                    addShopImg(shop, shopImgInputStream, fileName);
                }

                    //        2.更新店铺信息
                    shop.setLastEditTime(new Date());
                    int effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0)
                        {
                            return new ShopExecution(ShopStateEnum.INNER_ERROR);
                         }
                    else
                        {
                            shop = shopDao.queryByShopId(shop.getShopId());
                            return new ShopExecution(ShopStateEnum.SUCCESS,shop);
                        }
                }
             } catch (Exception e)
             {
            throw  new ShopOperationException("modifySHop error:"+e.getMessage());
            }

    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName)
    {
        if(shop==null || shop.getShopId()==null ){
            return new ShopExecution((ShopStateEnum.NULL_SHOP));
        }
        else
        {
            try {
//            给店铺信息初始值赋值
                shop.setEnableStatus(0);
                shop.setCreateTime(new Date());
                shop.setLastEditTime(new Date());
//            添加店铺信息
                int effectedNum=shopDao.insertShop(shop);

                if(effectedNum<=0)
                {
                    throw new ShopOperationException("创建店铺失败");
                }
                else
                {
                        //                    存储图片
                        try{
                            if(shopImgInputStream !=null  &&  fileName !=null  &&  !"".equals(fileName))
                            addShopImg(shop, shopImgInputStream,fileName);
                        }catch (Exception e)
                        {
                            throw new ShopOperationException("addShopImg error"+e.getMessage());
                        }
                        //                   更新店铺的图片地址
                        effectedNum=shopDao.updateShop(shop);
                        if(effectedNum<=0)
                        {
                            throw new ShopOperationException("更新图片地址失败");
                        }
                        else
                        {
                            shop=shopDao.queryByShopId(shop.getShopId());
                            return new ShopExecution(ShopStateEnum.SUCCESS,shop);
                        }

                }

            }catch (Exception e)
            {
                throw new ShopOperationException("addShop error:"+e.getMessage());
            }
        }
//        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }*/