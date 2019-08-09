package com.java.o2o.dao;

import com.java.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {

    /*分页查询店铺，可输入的条件有：店铺名（模糊），店铺状态，区域id，owner
    * param1
    * rowIndex：从第几行开始取数据
    * pageSize：返回的条数*/
    List<Shop> queryShopList(@Param("shopCondition") Shop param1, @Param("rowIndex")
                             int rowIndex, @Param("pageSize") int pageSize);

    /*返回queryShopList总数*/
    int queryShopCount(@Param("shopCondition") Shop shopCondition);

    //    通过shop_id查询店铺
        Shop queryByShopId(long shopId);

    //    插入店铺
        int insertShop(Shop shop);
    //更新店铺
        int updateShop(Shop shop);
}
