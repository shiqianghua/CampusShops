package com.java.o2o.dao;

import com.java.o2o.BaseTest;
import com.java.o2o.entity.LocalAuth;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LocalAuthDaoTest extends BaseTest {
    @Autowired
    LocalAuthDao localAuthDao;

    @Test
    public void insertLocalAhth()
    {
        LocalAuth localAuth = new LocalAuth();
        localAuth.setUsername("tom");
        localAuth.setPassword("123345");
        localAuthDao.insertLocalAuthInfo(localAuth);
    }
}
