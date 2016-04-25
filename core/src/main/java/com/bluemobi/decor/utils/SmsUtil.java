package com.bluemobi.decor.utils;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by wangbin on 2014/12/16.
 */
public class SmsUtil {

    /**
     * 服务http地址
     */
    private static String URI_SEND_SMS = "http://sdk4report.eucp.b2m.cn:8080/sdkproxy/sendsms.action";

    /**
     * 密码
     */
    private static String  password ="260354";


    /**
     * api接口
     */
    private final static String cdkey = "6SDK-EMY-6688-KETQM";


    /**
     * 编码格式
     */
    private static String ENCODING = "UTF-8";


    /**
     *
     * @param phone
     * @param message
     * @return
     * @throws java.io.IOException
     */
    public static String sendSms( String phone, String message) throws IOException{
        Map<String,String> params= new HashMap<String,String>();
        params.put("cdkey", cdkey);
        params.put("password", password);
        params.put("phone", phone);
        params.put("message", message);
        return HttpUtils.sendPost4String(URI_SEND_SMS,params);
    }


    public static String getVerCode(int length){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<length;i++){
            sb.append(new Random().nextInt(10));
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        try {
            for(int i=0;i<100;i++){
                System.out.println(getVerCode(5));
            }



            String result =   SmsUtil.sendSms("18801285391", "【天地E家】您的验证码为3334");
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
