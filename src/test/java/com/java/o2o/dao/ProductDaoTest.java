package com.java.o2o.dao;

import com.java.o2o.BaseTest;
import com.java.o2o.entity.Product;
import com.java.o2o.entity.ProductCategory;
import com.java.o2o.entity.ProductImg;
import com.java.o2o.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductDaoTest extends BaseTest {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testInsertProduct() throws Exception
    {
        Shop shop1=new Shop();
        shop1.setShopId(1L);
        ProductCategory pc1=new ProductCategory();
        pc1.setProductCategoryId(1L);

        /*初始化三个商品实例并添加进shopId为 1的店铺里，
        * 同时商品类别id也为1*/

        Product product1=new Product();
        product1.setProductName("测试1");
        product1.setProductDesc("测试DESC1");
        product1.setPriority(1);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(pc1);

        Product product2=new Product();
        product2.setProductName("测试2");
        product2.setProductDesc("测试DESC2");
        product2.setPriority(1);
        product2.setEnableStatus(1);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setShop(shop1);
        product2.setProductCategory(pc1);


        List<Product> productList=new ArrayList<Product>();
        productList.add(product1);
        productList.add(product2);

        int effectedNum1=productDao.insertProduct(product1);
        int effectedNum2=productDao.insertProduct(product2);
        int TotalNum=effectedNum1+effectedNum2;

        assertEquals(2,TotalNum);
    }

    @Test
    public void testQueryProductByProductId() throws  Exception
    {
        long productId=1;
//        将两个创建的商品详情图片加入到商品详情表中
        ProductImg productImg1=new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(productId);

        ProductImg productImg2=new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setImgDesc("测试图片2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(productId);

        List<ProductImg> productImgList=new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);

        /*int effectedNum=productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2,effectedNum);
*/

//        查询productId为1的商品信息并返回的详情图实例列表size是否为2
        Product product=productDao.queryProductById(productId);
        assertEquals(2,product.getProductImgList().size());

//        删除新增的这两个图片详情图实例
        int effectedNum=productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2,effectedNum);
    }

    @Test
    public void testUpdateProduct() throws Exception
    {
        Product product=new Product();
        ProductCategory pc=new ProductCategory();
        Shop shop=new Shop();
        shop.setShopId(1L);
        pc.setProductCategoryId(1L);
        product.setProductId(1L);
        product.setShop(shop);
        product.setProductName("第二个产品");
        product.setProductCategory(pc);

        int effectedNum=productDao.updateProduct(product);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testQueryProductList() throws Exception
    {
        Product productCondition=new Product();
        productCondition.setEnableStatus(1);
       /* ProductCategory productCategory=new ProductCategory();
        productCategory.setProductCategoryId(1L);
        productCondition.setProductCategory(productCategory);*/

        //        分页查询
        List<Product> productList=productDao.queryProductList(productCondition,1,2);


        assertEquals(2,productList.size());
//        查询名称为测试的商品总数
        int count=productDao.queryProductCount(productCondition);
        assertEquals(9,count);
//        使用商品名称模糊查询
        productCondition.setProductName("test");
        productList=productDao.queryProductList(productCondition,1,2);
        count=productDao.queryProductCount(productCondition);
//        assertEquals(2,count);/**/
        System.out.println("count:"+count);
    }

    @Test
    public void testUpdateProductCategoryToNull()
    {
        int effectedNum=productDao.updateProductCategoryToNull(4L);
        assertEquals(4,effectedNum);
    }

    @Test
    public void testQueryProductByProductModePage()
    {
        int limit=3;
        int offset=0;
        List<Product> products = productDao.queryProductListMorePage(limit, offset);

        for (Product product:products)
        {
            System.out.println(product.toString());
        }
    }
}
