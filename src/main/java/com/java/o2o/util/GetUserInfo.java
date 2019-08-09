package com.java.o2o.util;

import com.java.o2o.dao.UserDao;
import com.java.o2o.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


//更新数据集
@Component
public class GetUserInfo {
    
    @Autowired
    UserDao userDao;


    public  void  getUserInfoData()  {
        String data="E:\\IDEA\\WorkSpace\\javaWeb2\\o2o\\data.cvs";
        File file = new File(data);

//        if (file.exists())
//        {
//            System.out.println("更新数据文件");
//            file.delete();
//        }

        FileOutputStream fileOutputStream=null;
        try {
             fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<User> users = userDao.selectUser();

        Iterator<User> iterator = users.iterator();

//        List<Integer> listIntegers = null;

        while (iterator.hasNext())
        {
            User next = iterator.next();
            String Strdata=next.getUserInfo()+","+next.getShopId()+","+next.getWeight()+"\n";

            try {
                fileOutputStream.write(Strdata.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String args[])
    {
//        getUserInfoData();
    }
}
