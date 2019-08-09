package com.java.o2o.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
//    需要加密的字段数组
    private String[] encryptPropNames={"jdbc.root","jdbc.password"};

//    对关键的属性进行转换
    protected String convertProperty(String propertyName,String propertyValue)
    {
        if (isEncryProp(propertyName))
        {
//            对密文进行解密
            String decryptValue=DESUtil.getDecryptString(propertyValue);
            return decryptValue;
        }
        else 
        {
            return propertyValue;
        }
    }

//    判断该属性是否已加密
    private boolean isEncryProp(String propertyName)
    {
        for (String encryptpropertyName:encryptPropNames)
        {
            if (encryptpropertyName.equals(propertyName))
            {
                return true;
            }
        }

        return false;
    }
}
