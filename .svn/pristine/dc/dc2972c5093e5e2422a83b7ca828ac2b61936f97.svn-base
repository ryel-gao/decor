package com.bluemobi.decor.portal.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 各种类型数据转换为json对象工具类
 *
 * @author 龙哲
 * @依赖：json-lib-2.3-jdk13.jar
 * @date 2014-10-14 16:07:05
 */
public class JsonUtils {

    protected final static Log logger = LogFactory.getLog(JsonUtils.class);

    public static GsonBuilder createGsonBuilder() {
        return new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public static String obj2Json(Object obj) {
        Gson gson = createGsonBuilder().create();
        return gson.toJson(obj);
    }

    /**
     * 把json对象串转换成map对象
     *
     * @param jsonObjStr e.g. {'name':'get','int':1,'double',1.1,'null':null}
     * @return Map
     */
    @SuppressWarnings({"unchecked"})
    public static Map getMapFromJsonObjStr(String jsonObjStr) {
        JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);

        Map map = new HashMap();
        for (Iterator iter = jsonObject.keys(); iter.hasNext(); ) {
            String key = (String) iter.next();
            map.put(key, jsonObject.get(key));
        }
        return map;
    }

    /**
     * 把map对象串转换成json对象
     *
     * @param map
     * @return jsonObjStr e.g. {'name':'get','int':1,'double',1.1,'null':null}
     */
    public static String toJson(Map<String, Object> map) {
        JSONObject jsonObject = JSONObject.fromObject(map);
        String str = jsonObject.toString();
        // replace "{
        System.out.println("str = " + str);
        if (str.indexOf("\"{") > 0) {
            str = str.replaceAll("\"{", "{");
            str = str.replaceAll("}\"", "}");
        }
        if (str.indexOf("\\") > 0) {
            str = str.replaceAll("\\", "");
        }
        System.out.println("str = " + str);
        return str;
    }

    /**
     * @return {"data":{"result":[{"id":58,"title":"LOL"}]}, "page": {"pageNo":1,"pageSize":10,"allCount":19},"code": "SUCCESS","errorMsg":""}
     * @Description 把list对象转换成json对象
     * Page page 存放;
     * @date 2014-10-16 13:11:04
     * @author 龙哲
     */
    @SuppressWarnings("unchecked")
    public static String returnList(List list, int pageNo, int pageSize, int total, String code, String errorMsg) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> pageMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // "code": "SUCCESS"
        returnMap.put("code", code);
        // "errorMsg": "000000"
        returnMap.put("errmsg", errorMsg);
        // "page":{"pageNo":1,"pageSize":10,"allCount":19}
        pageMap.put("pageNo", pageNo);
        pageMap.put("pageSize", pageSize);
        pageMap.put("allCount", total);
        resultMap.put("pageCondition", pageMap);
        // resultMap
        resultMap.put("result", null == list ? new ArrayList() : list);
        returnMap.put("data", resultMap);
        JSONObject json = JSONObject.fromObject(returnMap);
        return json.toString();
    }

    /**
     * @param list
     * @return
     * @Description 把list对象转换成json对象
     * @author TuTu
     * @Date 2014年10月31日 上午11:23:12
     */
    @SuppressWarnings("unchecked")
    public static String returnList(List list) {
        JSONArray jsonArray = JSONArray.fromObject(list);
        return jsonArray.toString();
    }

    /**
     * @param obj
     * @return
     * @Description 把object对象转换成json对象
     * @author TuTu
     * @Date 2014年10月31日 上午11:23:12
     */
    public static String returnObject(Object obj) {
        JSONArray jsonArray = JSONArray.fromObject(obj);
        return jsonArray.toString();
    }

    /**
     * @param arr
     * @return
     * @Description 把数组转换成json对象
     * @author TuTu
     * @Date 2014年10月31日 上午11:23:12
     */
    public static String returnStrings(String[] arr) {
        JSONArray jsonArray = JSONArray.fromObject(arr);
        return jsonArray.toString();
    }

    /**
     * @return
     * @Description 把obj对象转换成json对象
     * Page page 存放;
     * @date 2014-10-16 13:11:04
     * @author 龙哲
     */
    public static String returnObj(Object obj, String objName, String code, String errorMsg) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // "code": "SUCCESS"
        returnMap.put("code", code);
        // "errorMsg": "000000"
        returnMap.put("errmsg", errorMsg);
        // resultMap
        if (!"".equals(objName)) {
            resultMap.put(objName, obj);
        }
        returnMap.put("data", resultMap);
        JSONObject json = JSONObject.fromObject(returnMap);
        return json.toString();
    }

    /**
     * @param arr
     * @return
     * @Description 把json对象转成List<map>
     * @author TuTu
     * @Date 2014年10月31日 上午11:23:12
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> returnlist(String arr) {
        JSONArray jsonArray2 = JSONArray.fromObject(arr);
        List<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < jsonArray2.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray2.get(i);
            Map map = new HashMap();
            for (Iterator iter = jsonObject.keys(); iter.hasNext(); ) {
                String key = (String) iter.next();
                map.put(key, jsonObject.get(key));
            }
            resultlist.add(map);
        }
        return resultlist;
    }
}