package com.java.o2o.service;

import com.java.o2o.BaseTest;
import com.java.o2o.dto.ImageHolder;
import com.java.o2o.dto.ShopExecution;
import com.java.o2o.entity.Area;
import com.java.o2o.entity.PersonInfo;
import com.java.o2o.entity.Shop;
import com.java.o2o.entity.ShopCategory;
import com.java.o2o.enums.ShopStateEnum;
import com.java.o2o.exceptions.ShopOperationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ShopServiceTest extends BaseTest{

    @Autowired
    private ShopService shopService;

    @Test
    public void testGetShopList()
    {
       Shop shopCondition = new Shop();
       ShopCategory SC=new ShopCategory();
       SC.setShopCategoryId(2L);
       shopCondition.setShopCategory(SC);

       ShopExecution se=shopService.getShopList(shopCondition,3,4);
       System.out.println("店铺列表数："+se.getShopList().size());
       System.out.println("店铺列表数："+se.getCount());
    }


    @Test
    public void testModifyShop() throws ShopOperationException,FileNotFoundException
    {
       Shop shop=new Shop();
       shop.setShopId(1L);
       shop.setShopName("修改后的店铺名称");
       File shopImg=new File("E:\\IDEA\\WorkSpace\\javaWeb2\\RequiredPictures\\images\\item\\shop\\2017060609084595067.jpg");
       InputStream is=new FileInputStream(shopImg);

       ImageHolder imageHolder=new ImageHolder("2017060609084595067.jpg",is);
       ShopExecution shopExecution=shopService.modifyShop(shop,imageHolder);
       System.out.println("新图片地址为："+shopExecution.getShop().getShopImg());
    }

   @Test
    public void testAddShop() throws FileNotFoundException {
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

       shop.setShopName("测试的店铺4");
       shop.setShopDesc("test4");
       shop.setShopAddr("test4");
       shop.setShopImg("test4");
       shop.setEnableStatus(ShopStateEnum.CHECK.getState());
       shop.setCreateTime(new Date());
       shop.setAdvice("审核中");

       File shopImg=new File("E:\\IDEA\\WorkSpace\\javaWeb2\\RequiredPictures\\2017060609084595067.jpg");

       InputStream fileInputStream = new FileInputStream(shopImg);

       ImageHolder imageHolder=new ImageHolder(shopImg.getName(),fileInputStream);
       ShopExecution se=shopService.addShop(shop,imageHolder);
       assertEquals(ShopStateEnum.CHECK.getState(),se.getState());

   }

   @Test
    public void testQueryShopById()
   {
       Shop shopList=shopService.getByShopId(1L);
       System.out.println("shop:"+shopList.toString());
   }
}
