package com.bluemobi.decor.utils.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.Key;

/**
 * Created by wangbin on 2015/4/2.
 */
public class DesUtil {


    protected final static Log logger = LogFactory.getLog(DesUtil.class);

    public static final String  key = "1426e7a8903bcdf5";

    public static Key convertSecretKey =null;

    static {
        try {
            byte[] bytesKey = str16hexToByte(key);
            // KEY转换
            DESKeySpec desKeySpec = new DESKeySpec(bytesKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
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
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
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
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            // 解密
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
            result = cipher.doFinal(decode(content));
            return new String(result);
        }catch (Exception e){
            logger.error(e);
        }

        return null;
    }

    private static byte[] str16hexToByte(String str){
        byte[] bytes = new byte[8];
        for(int i=0;i<8;i++){
            String s =str.substring(0,2);
            str = str.replaceAll(s,"");
            bytes[i] =(byte) (Integer.parseInt(s,16) & 0x000000ff);
        }
        return bytes;
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



    public static void main(String[] args) {
        String content = "23213213123";

        System.out.println("加密:"+encrypt(content));
        System.out.println("解密:"+decrypt(encrypt(content)));
    }


}
