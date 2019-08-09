package com.java.o2o.dao;

import com.java.o2o.BaseTest;
import com.java.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ShopCategoryDaoTest extends BaseTest{
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testQueryShopCategory()
    {
        List<ShopCategory> shopCategoryList=shopCategoryDao.queryShopCategory(null);
        System.out.println(shopCategoryList.size());

        for(ShopCategory shopCategory:shopCategoryList)
            System.out.println(shopCategory.toString());



        /*assertEquals(2,shopCategoryList.size());

        ShopCategory testShopCategory=new ShopCategory();
        ShopCategory parentShopCategory=new ShopCategory();

        parentShopCategory.setShopCategoryId(1L);
        testShopCategory.setParent(parentShopCategory);
        List<ShopCategory> shopCategoryList1=shopCategoryDao.queryShopCategory(testShopCategory);
        System.out.println(shopCategoryList1.get(0).getShopCategoryName());
*/
    }
}
