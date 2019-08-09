package com.java.o2o.dao;

import com.java.o2o.BaseTest;
import com.java.o2o.entity.Area;
import com.java.o2o.entity.PersonInfo;
import com.java.o2o.entity.Shop;
import com.java.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


public class ShopDaoTest extends BaseTest{

    @Autowired
    private ShopDao shopDao;

    @Test
    public void testQueryShopListAndCount()
    {
        Shop shopCondition = new Shop();
        ShopCategory childCategory=new ShopCategory();
        ShopCategory parentCategory=new ShopCategory();
        parentCategory.setShopCategoryId(1L);
        childCategory.setParent(parentCategory);
        shopCondition.setShopCategory(childCategory);
       /* PersonInfo owner=new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);*/

        /*ShopCategory SC=new ShopCategory();
        SC.setShopCategoryId(2L);
        shopCondition.setShopCategory(SC);*/

        List<Shop> shopList=shopDao.queryShopList(shopCondition,0,1);

        int countTotal=shopDao.queryShopCount(shopCondition);

        System.out.println("店铺列表："+shopList.size());
        System.out.println("店铺列表总数："+countTotal);

    }

    @Test
    @Ignore
    public void testQueryShopId()
    {
        long shopId=1;
        Shop shop=shopDao.queryByShopId(shopId);
        System.out.println("areaId:"+shop.getArea().getAreaId());
        System.out.println("areaName:"+shop.getArea().getAreaName());
    }

    @Test
    @Ignore
    public void testInsertShop()
    {
        Shop shop=new Shop();

        PersonInfo owner=new PersonInfo();
        Area area=new Area();
        ShopCategory shopCategory=new ShopCategory();

        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);

        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);

        shop.setShopName("测试的店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setShopImg("test");
        shop.setEnableStatus(1);
        shop.setCreateTime(new Date());
        shop.setAdvice("审核中");
        int effectedNum=shopDao.insertShop(shop);
       assertEquals(1,effectedNum);
    }

    @Test
    @Ignore
    public void testUpdateShop()
    {
        Shop shop=new Shop();

        shop.setShopId(1L);
        shop.setShopName("测试商店");
        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setLastEditTime(new Date());
        int effectedNum=shopDao.updateShop(shop);
        assertEquals(1,effectedNum);
    }

}
