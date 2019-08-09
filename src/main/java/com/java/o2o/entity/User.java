package com.java.o2o.entity;

import java.util.Random;

public class User {
    private String userName;
    private String password;
    private Long userInfo;
    private Long shopId;
    private Long weight;
    private String phone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Long userInfo) {
        this.userInfo = userInfo;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User() {

//        this.userInfo = 1 + (((long) (new Random().nextDouble() * (11 - 1))));
//        this.shopId = 1 + (((long) (new Random().nextDouble() * (31 - 1))));
//        this.weight = 1 + new Random().nextDouble() * (6 - 1);

    }


    public User(String userName, String password, Long userInfo, Long shopId,Long weight,String phone) {
        this.userName = userName;
        this.password = password;
        this.userInfo = userInfo;
        this.shopId = shopId;
        this.weight=weight;
        this.phone=phone;
    }



    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userInfo=" + userInfo +
                ", shopId=" + shopId +
                ", weight=" + weight +
                '}';
    }
}
