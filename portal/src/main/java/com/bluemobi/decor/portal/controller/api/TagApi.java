package com.bluemobi.decor.portal.controller.api;

import com.bluemobi.decor.core.ERROR_CODE;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.*;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.*;
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
 * 类别
 * Created by tuyh on 2015/7/8.
 */
@Controller
@RequestMapping("api/tag")
public class TagApi extends CommonController {

    @Autowired
    private KindTagService kindTagService;

    @Autowired
    private SpaceTagService spaceTagService;

    @Autowired
    private SeriesTagService seriesTagService;

    @Autowired
    private StyleTagService styleTagService;

    @Autowired
    private MessageTagService messageTagService;

    // 商品列表查询
    @RequestMapping(value = "/list")
    public void findGoodsList(HttpServletRequest request,
                              HttpServletResponse response,
                              Integer type,
                              Integer pageNum,
                              Integer pageSize) {
        if (null == type) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        String result = "";
        Map<String, Object> dataMap = null;
        Result obj = null;

        switch (type) {
            case 1:
                // 商品图分类
                Page<KindTag> goodsPage = kindTagService.iFindGoodsSortPage(pageNum, pageSize);
                dataMap = APIFactory.fitting(goodsPage);
                obj = new Result(true).data(dataMap);
                result = JsonUtil.obj2ApiJson(obj, "num");
                WebUtil.printApi(response, result);
                break;
            case 2:
                // 空间分类
                Page<SpaceTag> spacePage = spaceTagService.iFindSpaceSortPage(pageNum, pageSize);
                dataMap = APIFactory.fitting(spacePage);
                obj = new Result(true).data(dataMap);
                result = JsonUtil.obj2ApiJson(obj, "num");
                WebUtil.printApi(response, result);
                break;
            case 3:
                // 风格分类
                Page<StyleTag> stylePage = styleTagService.iFindStyleSortPage(pageNum, pageSize);

                dataMap = APIFactory.fitting(stylePage);
                obj = new Result(true).data(dataMap);
                result = JsonUtil.obj2ApiJson(obj, "num");
                WebUtil.printApi(response, result);
                break;
            case 4:
                // 资讯标签分类
                Page<MessageTag> messagePage = messageTagService.iFindMessageSortPage(pageNum, pageSize);

                dataMap = APIFactory.fitting(messagePage);
                obj = new Result(true).data(dataMap);
                result = JsonUtil.obj2ApiJson(obj, "num");
                WebUtil.printApi(response, result);
                break;
            case 5:
                // 系列图标签
                Page<SeriesTag> seriesPage = seriesTagService.iFindSeriesSortPage(pageNum, pageSize);

                dataMap = APIFactory.fitting(seriesPage);
                obj = new Result(true).data(dataMap);
                result = JsonUtil.obj2ApiJson(obj, "num");
                WebUtil.printApi(response, result);
                break;
            default:
                WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
                return;
        }
    }
}
