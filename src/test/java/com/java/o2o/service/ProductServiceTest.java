package com.java.o2o.service;

import com.java.o2o.BaseTest;
import com.java.o2o.dto.ImageHolder;
import com.java.o2o.dto.ProductExecution;
import com.java.o2o.entity.Product;
import com.java.o2o.entity.ProductCategory;
import com.java.o2o.entity.Shop;
import com.java.o2o.enums.ProductStateEnum;
import com.java.o2o.exceptions.ShopOperationException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends BaseTest
{
    @Autowired
    private ProductService productService;

    @Test
    public void testAddProduct() throws ShopOperationException,FileNotFoundException
    {
        Product product=new Product();

        Shop shop=new Shop();
        shop.setShopId(1L);

        ProductCategory pc=new ProductCategory();
        pc.setProductCategoryId(1L);

        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());

//        创建缩略图文件流
        File thumbnailFile=new File("E:\\IDEA\\WorkSpace\\javaWeb2\\RequiredPictures\\images\\item\\shop\\2017060715512185473.jpg");
        InputStream is=new FileInputStream(thumbnailFile);
        ImageHolder thumbnail=new ImageHolder(thumbnailFile.getName(),is);

//        创建两个商品详情图文件流并将它们添加到详情图列表中
        File productImg1=new File("E:\\IDEA\\WorkSpace\\javaWeb2\\RequiredPictures\\images\\item\\shop\\118.png");
        InputStream is1=new FileInputStream(productImg1);
        File productImg2=new File("E:\\IDEA\\WorkSpace\\javaWeb2\\RequiredPictures\\images\\item\\shop\\119.png");
        InputStream is2=new FileInputStream(productImg2);

        List<ImageHolder> productImgList=new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImg1.getName(),is1));
        productImgList.add(new ImageHolder(productImg2.getName(),is2));

        ProductExecution pe=productService.addProduct(product,thumbnail,productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());

    }

    @Test
    @Ignore
    public void testModifyProduct() throws ShopOperationException,FileNotFoundException
    {
        Product product=new Product();
        Shop shop=new Shop();
        shop.setShopId(1L);
        ProductCategory pc=new ProductCategory();

        pc.setProductCategoryId(1L);
        pc.setShopId(shop.getShopId());
        product.setProductId(1L);
        product.setShop(shop);

        product.setProductCategory(pc);
        product.setProductName("正式的商品");
        product.setProductDesc("正式的商品");
        product.setPriority(23);
        product.setEnableStatus(0);

//        创建缩略图文件流
        File thumbnaiFile=new File("E:\\IDEA\\WorkSpace\\javaWeb2\\RequiredPictures\\images\\item\\shop\\118.png");
        InputStream is=new FileInputStream(thumbnaiFile);
        ImageHolder thumbnail=new ImageHolder(thumbnaiFile.getName(),is);

//        创建两个商品详情图文件流并将添加至详情图列表
        File productImg1=new File("E:\\IDEA\\WorkSpace\\javaWeb2\\RequiredPictures\\images\\item\\shop\\118.png");
        InputStream is1=new FileInputStream(productImg1);
        File productImg2=new File("E:\\IDEA\\WorkSpace\\javaWeb2\\RequiredPictures\\images\\item\\shop\\119.png");
        InputStream is2=new FileInputStream(productImg2);

        List<ImageHolder> productImgList=new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(productImg1.getName(),is1));
        productImgList.add(new ImageHolder(productImg2.getName(),is2));

        ProductExecution pe=productService.modifyProduct(product,thumbnail,productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
    }


}
