package com.bluemobi.decor.portal.util;

import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * pc端　分页处理
 */
public class PcPageFactory {

    /**
     * json转换公共类
     *
     * @param page 分页数据
     * @return
     */
    public static Map<String, Object> fitting(Page<?> page) {
        List<?> list = page.getContent();

        // data中的page
        Map<String, Object> pageMap = new HashMap<String, Object>();
        pageMap.put("totalNum", page.getTotalElements());
        pageMap.put("totalPage", page.getTotalPages());
        pageMap.put("currentPage", page.getNumber() + 1);

        // data中的list
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("list", list);
        dataMap.put("page", pageMap);
        return dataMap;
    }


}
