package com.java.o2o.entity;

public class ProductRequireData {
    private String coverUrl;
    private String name;
    private double price;
    private Integer purchaseCount;
    private Long id;

    public ProductRequireData() {
    }

    public ProductRequireData(String coverUrl, String name, double price, Integer purchaseCount, Long id) {
        this.coverUrl = coverUrl;
        this.name = name;
        this.price = price;
        this.purchaseCount = purchaseCount;
        this.id = id;
    }

    public String getcoverUrl() {
        return coverUrl;
    }

    public void setcoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public double getprice() {
        return price;
    }

    public void setprice(double price) {
        this.price = price;
    }

    public Integer getpurchaseCount() {
        return purchaseCount;
    }

    public void setpurchaseCount(Integer purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public Long getid() {
        return id;
    }

    public void setid(Long id) {
        this.id = id;
    }
}
