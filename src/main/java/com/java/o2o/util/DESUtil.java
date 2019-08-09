package com.java.o2o.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

public class DESUtil {
    /*对称加密算法，加密和解密使用相同密匙算法
    * */

//    设置密匙key
    private static Key key;
    private static String KEY_STR="myKey";
    private static String CHARSETNAME="UTF-8";
    private static String ALGORITHM="DES";

    static
    {
        try
        {
//            生成DES算法对象
            KeyGenerator generator=KeyGenerator.getInstance(ALGORITHM);
//            运用SHA1安全策略随机数算法
            SecureRandom secureRandom=SecureRandom.getInstance("SHA1PRNG");
//            设置密匙种子
            secureRandom.setSeed(KEY_STR.getBytes());
//            初始化基于SHA1的算法对象
            generator.init(secureRandom);
//            生成密匙对象
            key=generator.generateKey();
            generator=null;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /*获取加密后的信息,得到密文*/
    public static String getEncryptString(String str)
    {
//        基于BASE64编码，接收byte[]并转换为String
        BASE64Encoder base64Encoder=new BASE64Encoder();
        try
        {
//            按UTF8编码
            byte[] bytes=str.getBytes(CHARSETNAME);
//            获取加密对象
            Cipher cipher=Cipher.getInstance(ALGORITHM);
//            初始化加密对象
            cipher.init(Cipher.ENCRYPT_MODE,key);
//            加密
            byte[] doFinal=cipher.doFinal(bytes);
//            byte[] to encode好的string并返回
            return base64Encoder.encode(doFinal);
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    /*获取解密后信息，得到明文*/
    public static String getDecryptString(String str)
    {
//        基于BASE64编码，接收byte[]并转换为string
        BASE64Decoder base64Decoder=new BASE64Decoder();
        try
        {
//            将字符串decode成byte[]
            byte[] bytes=base64Decoder.decodeBuffer(str);
//            获取解密对象
            Cipher cipher=Cipher.getInstance(ALGORITHM);
//            初始化解密信息
            cipher.init(Cipher.DECRYPT_MODE,key);
//            解密
            byte[] doFinal=cipher.doFinal(bytes);
//            返回解密后的信息
            return new String(doFinal,CHARSETNAME);
        }catch (Exception e)
        {
         throw new RuntimeException(e);
        }
    }

    public static void main(String args[])
    {
        System.out.println(getEncryptString("root"));
        System.out.println(getEncryptString("123456"));
        System.out.println(getDecryptString("WnplV/ietfQ="));
        System.out.println(getDecryptString("QAHlVoUc49w="));

        System.out.println(getEncryptString("work"));
        System.out.println(getEncryptString("654321"));

    }
}
