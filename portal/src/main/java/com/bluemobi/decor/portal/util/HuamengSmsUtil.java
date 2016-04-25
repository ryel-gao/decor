package com.bluemobi.decor.portal.util;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by gaoll on 2014/12/16.
 */
public class HuamengSmsUtil {

    /**
     * 服务http地址
     */
    private static String URI_SEND_SMS = "http://114.112.64.134:8888/sms.aspx";

    /**
     * 密码
     */
    private static String  password ="112233";


    /**
     * api接口
     */
    private final static String account = "huameng";


    /**
     * 编码格式
     */
    private static String ENCODING = "UTF-8";


    /**
     *
     * @return
     * @throws java.io.IOException
     */
    public static String sendSms( String mobile, String content) throws IOException{
        Map<String,String> params= new HashMap<String,String>();
        params.put("userid", "1485");
        params.put("account", account);
        params.put("password", password);
        params.put("mobile", mobile);
        params.put("content", content);
        params.put("sendTime", "");
        params.put("action", "send");
        params.put("extno", "");
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
            String result =   HuamengSmsUtil.sendSms("13554372007", "【华盟教育】您的验证码为12355。(请勿告知他人)");
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
