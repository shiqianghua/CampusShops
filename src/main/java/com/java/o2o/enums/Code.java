package com.java.o2o.enums;

public enum Code {
//    登录成功
    SUCCESS(200,true),
//   登陆失败
    ERROR(400,false),
//    出错
    FAILURE(403,false);

    private Integer code;
    private boolean isLogin;

    Code(Integer code, boolean isLogin) {
        this.code = code;
        this.isLogin = isLogin;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
