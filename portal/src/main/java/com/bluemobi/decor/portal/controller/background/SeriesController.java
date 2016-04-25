package com.bluemobi.decor.portal.controller.background;

import com.bluemobi.decor.common.factory.DataTableFactory;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.dao.SeriesSceneDao;
import com.bluemobi.decor.entity.CommentUpdateStatus;
import com.bluemobi.decor.entity.Series;
import com.bluemobi.decor.entity.SeriesScene;
import com.bluemobi.decor.entity.SeriesTag;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.SessionUtils;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.*;
import com.bluemobi.decor.utils.JsonUtil;
import com.bluemobi.decor.utils.UploadUtils;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系列图管理
 * Created by tuyh on 2015/7/20
 */
@Controller
@RequestMapping(value = "/backend/series")
public class SeriesController extends CommonController {
    @Autowired
    private SeriesService seriesService;
    @Autowired
    private SeriesSceneService seriesSceneService;


    @RequestMapping(value = "/index")
    public String index() {
        return "系列图列表";
    }

    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length,
                     Integer id,
                     String author,
                     Integer sort) {
        try {
            int pageNum = getPageNum(start, length);
            Page<Series> page = seriesService.pageByParams(pageNum, length, id, author, sort);

            // 将系列图发布者和分类写入返回值中
            for (Series series : page.getContent()) {
                series.setAuthor(series.getUser().getNickname());
                series.setSort(series.getSeriesTag().getName());
            }

            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/addSeriesUI")
    public String addSeriesUI() {
        return "添加系列图";
    }

    @RequestMapping(value = "/save")
    public void save(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer tagId, String info,
                     String sceneIds) {
        Integer userId = null == SessionUtils.getCurrentUser() ? 0 : SessionUtils.getCurrentUser().getId();
        if (userId == 0) {
            WebUtil.print(response, new Result(false).msg("暂未登录！"));
            return;
        }
        if (tagId == null) {
            WebUtil.print(response, new Result(false).msg("必须选择一个系列图分类！"));
            return;
        }
        if (StringUtils.isEmpty(sceneIds)) {
            WebUtil.print(response, new Result(false).msg("至少选择一个场景图！"));
            return;
        }

        try {
            seriesService.insert(userId, tagId, info, sceneIds);
            WebUtil.print(response, new Result(true).msg("操作成功！"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败！"));
        }
    }

    @RequestMapping(value = "/delete")
    public void delete(HttpServletRequest request,
                       HttpServletResponse response,
                       Integer id) {
        try {
            seriesService.deleteBySeriesId(id);
            WebUtil.print(response, new Result(true).msg("删除系列图成功！"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("删除系列图失败！"));
        }
    }

    @RequestMapping(value = "/batchDelete")
    public void batchDelete(HttpServletRequest request,
                            HttpServletResponse response,
                            String ids) {
        try {
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);
            seriesService.deleteAll(arrayId);
            WebUtil.print(response, new Result(true).msg("批量删除系列图成功！"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("批量删除系列图失败！"));
        }
    }

    @RequestMapping(value = "/shows")
    public String shows(HttpServletRequest request,
                        HttpServletResponse response,
                        Integer id,
                        ModelMap model) {
        Series series = seriesService.getById(id);

        // 将系列图发布者和分类写入返回值中
        series.setAuthor(series.getUser().getNickname());
        series.setSort(series.getSeriesTag().getName());

        List<SeriesScene> list = seriesSceneService.iFindBySeries(series);
        List<Map<String, Object>> imagePlusList = new ArrayList<Map<String, Object>>();
        Map<String, Object> imgMap = null;
        if (null != list && list.size() > 0) {
            for (SeriesScene seriesScene : list) {
                // 拼接返回值
                imgMap = new HashMap<String, Object>();
                imgMap.put("name", seriesScene.getScene().getName());
                imgMap.put("path", seriesScene.getScene().getImage());

                // 组合返回值
                imagePlusList.add(imgMap);
            }
        }
        model.put("series", series);
        model.put("imageList", JSONArray.fromObject(imagePlusList.size() == 0 ? new ArrayList<Map<String, Object>>() : imagePlusList));
        return "查看系列图";
    }
}