package com.java.o2o.service.impl;

import com.java.o2o.dao.UserDao;
import com.java.o2o.entity.User;
import com.java.o2o.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    public int insertUser(User user)
    {
        int status= userDao.insertUser(user);

        return status;
    }

    @Override
    public User selectUser_2(User user) {
        return userDao.selectUser_2(user);
    }
}
