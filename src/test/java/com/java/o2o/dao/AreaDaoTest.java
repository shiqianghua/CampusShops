package com.java.o2o.dao;

import com.java.o2o.entity.Area;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.java.o2o.BaseTest;

import static junit.framework.TestCase.assertEquals;


public class AreaDaoTest extends BaseTest{
    @Autowired
    private AreaDao areaDao;
    @Test
    public void testQueryArea()
    {
        List<Area> areaList=areaDao.queryArea();
        assertEquals(2,areaList.size());
    }
}
