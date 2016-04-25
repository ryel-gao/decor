package com.bluemobi.decor.portal.util;

import com.bluemobi.decor.core.Configue;
import com.bluemobi.decor.core.bean.Response;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangbin on 2015/3/25.
 */
public class HttpUtils {

    protected final static Log logger = LogFactory.getLog(HttpUtils.class);

    private static final CloseableHttpClient httpClient;

    public static final String CHARSET = "UTF-8";

    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }


    public static Response sendPost(String url, Map<String, String> params) {
        Response result = new Response();

        if (StringUtils.isBlank(url)) {
            result.setError("url为空!");
            result.setStatus(false);
            return result;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(params.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if (pairs != null && pairs.size() > 0) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHARSET));
            }
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            result.setStatusCode(statusCode);
            if (statusCode != 200) {
                httpPost.abort();
                result.setError("返回状态错误，错误码:" + statusCode);
                result.setStatus(false);
                return result;
            }
            HttpEntity entity = response.getEntity();
            String content = null;
            if (entity != null) {
                content = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            result.setStatus(true);
            result.setBody(content);
        } catch (Exception e) {
            result.setStatus(false);
            result.setError(e.getMessage());
            logger.error(e);
        }
        return result;

    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            System.out.print(realUrl);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String sendPost4String(String url, Map<String, String> params) {
        return sendPost(url, params).getBody();
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String sendPost4String1(String url,String params) {
        return sendGet(url, params);
    }


    public static String sendWuyeRequest(String suffixUrl, Map<String, String> params) throws Exception {
        String baseUrl = Configue.getWuyeUrl();
        return sendPost4String(baseUrl + suffixUrl, params);
    }


    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        String url = "http://121.40.132.219:8082/globle/tran-auth/reg?mobile=18801285391";
        map.put("password", "123456");
        map.put("nickname", "wangbin3");


    }
}
