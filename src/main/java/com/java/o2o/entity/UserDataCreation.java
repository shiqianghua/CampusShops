package com.java.o2o.entity;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class UserDataCreation {
      private String userName;
      private String password;
      private Long userInfo;
      private Long shopId;
      private double weight;

    public UserDataCreation() {
        this.userInfo = 1 + (((long) (new Random().nextDouble() * (5 - 1))));
        this.shopId = 1 + (((long) (new Random().nextDouble() * (30 - 1))));
        this.weight =  1 + new Random().nextDouble() * (5 - 1);

    }

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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "UserDataCreation{" +
                "userInfo=" + userInfo +
                ", shopId=" + shopId +
                ", weight=" + weight +
                '}';
    }
}
