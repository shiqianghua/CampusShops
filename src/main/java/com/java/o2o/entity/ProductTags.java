package com.java.o2o.entity;

public class ProductTags {
    private String name;
    private Long id;

    public ProductTags(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ShopTags{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
