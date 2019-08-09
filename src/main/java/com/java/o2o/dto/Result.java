package com.java.o2o.dto;

/*封装json对象，返回所有结果都使用它*/
public class Result<T> {
//    成功与否标志
    private boolean success;
//    成功时返回的数据
    private T data;
//    错误信息
    private String errorMsg;
    private int errorCode;
    private int code;



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Result() {
    }

    //    成功时构造器
    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

//    错误时构造器

    public Result(boolean success, String errorMsg, int errorCode) {
        this.success = success;
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
//        this.code=code;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
