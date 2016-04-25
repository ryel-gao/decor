package com.bluemobi.decor.utils.utils;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 公共方法
 * Created by gaoll on 2015/7/8.
 */
public class ComFun {

    /**
     * 把   1,2,3 形式的数组转为  @1@,@2@,@3@
     * @param tags
     * @return
     */
    public static String tagsFat(String tags){
        String str = "";
        if(StringUtils.isNotEmpty(tags)){
            String[] arr = tags.split(",");
            for (int i = 0; i < arr.length; i++) {
                if(i != 0){
                    str += ",";
                }
                str += "@" + arr[i] + "@";
            }
        }
        return str;
    }

    /**
     * 把    @1@,@2@,@3@形式的数组转为  1,2,3
     * @param tags
     * @return
     */
    public static String tagsThin(String tags){
        String str = "";
        if(StringUtils.isNotEmpty(tags)){
            str = tags.replace("@","");
        }
        return str;
    }

    /**
     * 把    @1@,@2@,@3@形式的数组转为list
     * @param tags
     * @return
     */
    public static List<Integer> tagsToList(String tags){
        List<Integer> list = new ArrayList<Integer>();
        if(StringUtils.isNotEmpty(tags)){
            String str = tags.replace("@","");
            String[] arr = str.split(",");
            for (int i = 0; i < arr.length; i++) {
                list.add(Integer.parseInt(arr[i]));
            }
         }
        return list;
    }
}
