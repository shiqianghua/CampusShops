package com.java.o2o.dao;

import com.java.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineDao {
//    根据传入的查询条件（头条名查询）
    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
