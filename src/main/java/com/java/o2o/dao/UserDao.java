package com.java.o2o.dao;

import com.java.o2o.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int insertUser(User user);

    int insertUserData();

    List<User> selectUser();

    User selectUser_2(User user);
}
