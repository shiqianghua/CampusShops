package com.java.o2o.entity;

public class HeadLineInfo {
    
    private String bannerUrl;
    private Long id;
    private String jumpUrl;

    public HeadLineInfo(String bannerUrl, Long id, String jumpUrl) {
        this.bannerUrl = bannerUrl;
        this.id = id;
        this.jumpUrl = jumpUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }
}
