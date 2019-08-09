package com.java.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request)
    {
        //从图片里面获取的验证码
        String verifyCodeExpected=(String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);

        String verifyCodeActual=HttpServletRequestUtil.getString(request,"verifyCodeActual");
        if(verifyCodeActual==null || !verifyCodeActual.equals(verifyCodeExpected))
        {
            return false;
        }

        return true;
    }
}
