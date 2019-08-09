package com.java.o2o.exceptions;

public class ProductCategoryOperationException extends RuntimeException{


    //是否需要生成序列值？？？？
    //private static final long serialVersionUID=2361446884822298905L;

    public ProductCategoryOperationException(String msg)
    {
        super(msg);
    }
}
