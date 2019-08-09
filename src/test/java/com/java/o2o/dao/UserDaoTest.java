package com.java.o2o.dao;

import com.java.o2o.BaseTest;
import com.java.o2o.entity.Shop;
import com.java.o2o.entity.User;
import com.java.o2o.entity.UserDataCreation;
import com.java.o2o.pt.ItemCF;
import com.java.o2o.pt.UserCF;
import com.java.o2o.util.GetUserInfo;
import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class UserDaoTest extends BaseTest {
    @Autowired
    UserDao userDao;

    @Autowired
    GetUserInfo getUserInfo;

    @Autowired
    UserDataCreation userDataCreation;

    @Autowired
    UserCF userCF;

    @Autowired
    ItemCF itemCF;

    @Test
     public void RandomTest()
    {
       for(int i=0;i<20;i++)
       {
           long l = 1 + (((long) (new Random().nextDouble() * (11 - 1))));
           long l1 = 1 + (((long) (new Random().nextDouble() * (31 - 1))));
           double v = 1 + new Random().nextDouble() * (6 - 1);


           System.out.println(l+"  <====>  "+l1+"  <=====>  "+v);
       }
    }

    @Test
    public void RecItemCF()
    {
//        更新训练数据集
        getUserInfo.getUserInfoData();

        try {
            itemCF.RecItemCF();
        } catch (IOException e) {

        } catch (TasteException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void RecUserCF()
    {
//更新数据集
        getUserInfo.getUserInfoData();

        try {
            List<Shop> shopList = userCF.nearUser(new User());
            Iterator<Shop> iterator = shopList.iterator();

            while (iterator.hasNext())
            {
                System.out.println(iterator.next().toString());
            }

//            for (Shop shop:shopList)
//                System.out.println(shop.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TasteException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectUserTest()
    {
        User user = new User();
        user.setUserName("tom");
        user.setPassword("123");
        user.setPhone("13356");
        User user2 = userDao.selectUser_2(user);
        System.out.println(user2.toString());

    }

    @Test
    public void insertUserTest()
    {
        User user = new User();
        user.setUserName("tom");
        user.setPassword("123");
        user.setShopId(1L);
        user.setUserInfo(1L);
        user.setWeight(2L);
//        for (int i=0;i<1000;i++)
        userDao.insertUser(user);

//        List<User> users = userDao.selectUser();
//        for(User user1:users)
//        {
//            System.out.println(user1.getUser_info());
//            System.out.println(user1.getWeight());
//            System.out.println(user1.getWeight());
//        }


    }

    @Test
    public void outputData() throws IOException {
//        GetUserInfo getUserInfo = new GetUserInfo();
        getUserInfo.getUserInfoData();
    }

    @Test
    public void testData()
    {
        System.out.println(userDataCreation);
    }
}
