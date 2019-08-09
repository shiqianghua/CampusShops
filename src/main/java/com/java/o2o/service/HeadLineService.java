package com.java.o2o.service;

import com.java.o2o.entity.HeadLine;

import java.io.IOException;
import java.util.List;

public interface HeadLineService {
//    根据传入事务条件返回指定的头条列表
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;
}
