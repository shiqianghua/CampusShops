package com.java.o2o.service;

import com.java.o2o.entity.ShopCategory;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ShopCategoryService {
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
