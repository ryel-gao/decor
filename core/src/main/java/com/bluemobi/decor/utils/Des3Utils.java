package com.bluemobi.decor.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;

/**
 * Created by wangbin on 2015/4/2.
 */
public class Des3Utils {


    protected final static Log logger = LogFactory.getLog(Des3Utils.class);

    public static final String  key = "1234567890qwertyuiopasdf";

    public static Key convertSecretKey =null;

    static {
        try {
            byte[] bytesKey = build3DesKey(key);
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");

            keyGenerator.init(112);
            // KEY转换
            DESedeKeySpec desKeySpec = new DESedeKeySpec(bytesKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
           convertSecretKey = factory.generateSecret(desKeySpec);

        }catch (Exception e){
            logger.error(e);
        }

    }


    /**
     * 加密
     * @param content
     * @return
     */
    public static String encrypt(String content){

        try {

            // 加密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
            byte[] result = cipher.doFinal(content.getBytes());
            return encode(result);
        }catch (Exception e){
            logger.error(e);
        }

        return null;
    }




    /**
     * 解密
     * @param content
     * @return
     */
    public static String decrypt(String content){

        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            // 解密
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            result = cipher.doFinal(decode(content));
            return new String(result);
        }catch (Exception e){
            logger.error(e);
        }

        return null;
    }


    /**
     * base64编码
     * @param bstr
     * @return
     */
    public static String encode(byte[] bstr){
        return new sun.misc.BASE64Encoder().encode(bstr);
    }


    /**
     * base64解码
     * @param str
     * @return string
     */
    public static byte[] decode(String str){
        byte[] bt = null;
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            bt = decoder.decodeBuffer( str );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bt;
    }


    public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
        byte[] key = new byte[24];    //声明一个24位的字节数组，默认里面都是0
        byte[] temp = keyStr.getBytes("UTF-8");    //将字符串转成字节数组
        /*
         * 执行数组拷贝
         * System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
         */
        if(key.length > temp.length){
            //如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, temp.length);
        }else{
            //如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }

    public static void main(String[] args) throws Exception{

        //String b = decrypt(URLDecoder.decode("%2BtZILuHyeRoLXkX7qnW1rrCRWfm7IOjV", "utf-8"));
        String  e = URLEncoder.encode("dIGjPfvHm6E=", "utf-8");
        System.out.println(e);

    }
}
