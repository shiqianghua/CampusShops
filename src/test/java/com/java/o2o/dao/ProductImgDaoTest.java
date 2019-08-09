package com.java.o2o.dao;

import com.java.o2o.BaseTest;
import com.java.o2o.dto.ImageHolder;
import com.java.o2o.entity.Product;
import com.java.o2o.entity.ProductImg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.java.o2o.util.ImageUtil.generateNormalImg;
import static com.java.o2o.util.ImageUtil.generateThumbnail;
import static com.java.o2o.util.PathUtil.getImgBasePath;
import static junit.framework.TestCase.assertEquals;

public class ProductImgDaoTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testBatchInsertProductImg() throws Exception
    {
        //productId为1的商品里添加两个详情图片记录
        ProductImg productImg1=new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(1L);

        ProductImg productImg2=new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setImgDesc("测试图片2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(1L);

        List<ProductImg> productImgList=new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum=productImgDao.batchInsertProductImg(productImgList);

        assertEquals(2,effectedNum);
    }

    @Test
    public void testQueryProductImgList()
    {
        Product product=new Product();
        product.setProductId(4L);
        Long productId=product.getProductId();
        List<ProductImg> productImgList=productImgDao.queryProductImgList(productId);
        int effectedNum=productImgList.size();
        assertEquals(2,effectedNum);
    }

    @Test
    public void testDeleteProductImgByProductId() throws Exception
    {
//        测试删除新增的两条商品详情图片
        long productId=1;
        int effctedNum=productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2,effctedNum);
    }

    @Test
    public void testImageUtil() throws Exception
    {
        File file=new File("E:\\IDEA\\WorkSpace\\javaWeb2\\RequiredPictures\\images\\item\\shopcategory\\2017060420464594520.png");
        InputStream inputStream=new FileInputStream(file);

//        创建两个商品详情图文件流并将添加至详情图列表
        File productImg1=new File("E:\\IDEA\\WorkSpace\\javaWeb2\\RequiredPictures\\images\\item\\shopcategory\\2017060420464594520.png");
        InputStream is1=new FileInputStream(productImg1);
        File productImg2=new File("E:\\IDEA\\WorkSpace\\javaWeb2\\RequiredPictures\\images\\item\\shopcategory\\2017060420315183203.png");
        InputStream is2=new FileInputStream(productImg2);



        ImageHolder imageHolder=new ImageHolder(productImg2.getName(),is2);
        String relativeAddr=generateNormalImg(imageHolder,"\\test\\");
        System.out.println("正式路径："+relativeAddr);
    }

}
