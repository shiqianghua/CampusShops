package com.java.o2o.util;

public class PageCalculate {
//    分页查找
    public static int calculateRowIndex(int pageIndex,int pageSize)
    {
        return (pageIndex>0) ? (pageIndex-1)* pageSize : 0;
    }
}
