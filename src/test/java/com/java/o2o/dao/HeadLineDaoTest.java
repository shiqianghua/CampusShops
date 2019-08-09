package com.java.o2o.dao;

import com.java.o2o.BaseTest;
import com.java.o2o.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HeadLineDaoTest extends BaseTest {
    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void testQueryArea()
    {
        List<HeadLine> headLineList=headLineDao.queryHeadLine(new HeadLine());
        System.out.println("size:"+headLineList.size());
    }
}

