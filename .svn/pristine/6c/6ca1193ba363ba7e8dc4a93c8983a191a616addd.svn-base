package com.bluemobi.decor.portal.controller.api;


import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.Scene;
import com.bluemobi.decor.entity.Series;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.CollectionService;
import com.bluemobi.decor.service.SeriesSceneService;
import com.bluemobi.decor.service.SeriesService;
import com.bluemobi.decor.utils.APIFactory;
import com.bluemobi.decor.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 系列图
 * Created by gaoll on 2015/3/12.
 */
@Controller
@RequestMapping("api/series")
public class SeriesApi extends CommonController {

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private SeriesSceneService seriesSceneService;

    @Autowired
    private CollectionService collectionService;

    /**
     * 系列图查询
     *
     * @param request
     * @param response
     * @param pageNum
     * @param pageSize
     * @param userId      当前用户id，用来判断是否收藏
     * @param name
     * @param seriesTagId 系列图标签
     */
    @RequestMapping(value = "/findSeriesList")
    public void findSeriesList(HttpServletRequest request,
                               HttpServletResponse response,
                               Integer pageNum,
                               Integer pageSize,
                               Integer userId,
                               String name,
                               Integer seriesTagId) {
        Page<Series> page = seriesService.iPage(pageNum, pageSize, name, seriesTagId);
        for (Series series : page.getContent()) {
            // 是否收藏
            Boolean isCollection = collectionService.isCollectionSeries(userId, series.getId());
            if (isCollection) {
                series.setIsCollection("yes");
            } else {
                series.setIsCollection("no");
            }

            // 获取系列图里面的场景列表
            List<Scene> sceneList = seriesSceneService.findSceneListBySeriesId(series.getId());
            for (Scene scene : sceneList){
                if(scene.getThumbnailImage() == null){
                    scene.setThumbnailImage(scene.getImage());
                }
            }
            series.setSceneList(sceneList);

            if(series.getThumbnailCover() == null){
                series.setThumbnailCover(series.getCover());
            }
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "user");
        WebUtil.printApi(response, result);
    }

    // 我发布的系列图
    @RequestMapping(value = "/my")
    public void my(HttpServletRequest request,
                   HttpServletResponse response,
                   Integer pageNum,
                   Integer pageSize,
                   Integer userId) {
        Page<Series> page = seriesService.iPageMy(pageNum, pageSize, userId);
        for (Series series : page.getContent()) {
            // 获取系列图里面的场景列表
            List<Scene> sceneList = seriesSceneService.findSceneListBySeriesId(series.getId());
            series.setSceneList(sceneList);

            if(series.getThumbnailCover() == null){
                series.setThumbnailCover(series.getCover());
            }
        }
        Map<String, Object> dataMap = APIFactory.fitting(page);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "user");
        WebUtil.printApi(response, result);
    }

    /**
     * 新增系列图
     *
     * @param request
     * @param response
     * @param userId   用户id
     * @param info     描述信息
     */
    @RequestMapping(value = "/addSeriesInfo")
    public void addSeriesInfo(HttpServletRequest request,
                              HttpServletResponse response,
                              Integer userId,
                              Integer seriesTagId,
                              String info,
                              String sceneIds) {
        seriesService.iAddSeries(userId, seriesTagId, info, sceneIds);
        WebUtil.printApi(response, new Result(true));
    }
}
