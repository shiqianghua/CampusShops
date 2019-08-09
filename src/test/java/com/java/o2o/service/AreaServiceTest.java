package com.java.o2o.service;

import com.java.o2o.BaseTest;
import com.java.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class AreaServiceTest extends BaseTest{
    @Autowired
    private AreaService areaService;

    @Test
    public void testGetAreaList()
    {
        List<Area> areaList=areaService.getAreaList();
        assertEquals("西苑",areaList.get(0).getAreaName());
    }
}
