package com.bluemobi.decor.portal.controller.api;

import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.SpaceTag;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.SpaceTagService;
import com.bluemobi.decor.utils.APIFactory;
import com.bluemobi.decor.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 图片
 * Created by tuyh on 2015/7/8.
 */
@Controller
@RequestMapping("api/image")
public class ImageApi {

    @Autowired
    private SpaceTagService spaceTagService;

    // 图库分类页列表（空间分类列表）
    @RequestMapping(value = "/depotImages")
    public void findGoodsList(HttpServletRequest request,
                              HttpServletResponse response,
                              Integer pageNum,
                              Integer pageSize) {
        Page<SpaceTag> page = spaceTagService.iPage(pageNum, pageSize);
        Map<String, Object> dataMap = APIFactory.fitting(page);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "num");
        WebUtil.printApi(response, result);
    }
}
