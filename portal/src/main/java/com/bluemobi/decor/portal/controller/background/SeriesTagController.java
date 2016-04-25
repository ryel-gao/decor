package com.bluemobi.decor.portal.controller.background;

import com.bluemobi.decor.common.exception.GeneralExceptionHandler;
import com.bluemobi.decor.common.factory.DataTableFactory;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.KindTag;
import com.bluemobi.decor.entity.SeriesTag;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.SeriesTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;


/**
 * Created by liuhm on 2015/7/17.
 */
@Controller
@RequestMapping(value = "/backend/seriesTag")
public class SeriesTagController extends CommonController {
    @Autowired
    private SeriesTagService seriesTagService;

    @RequestMapping(value = "/index")
    public String index(){
        return "系列图标签管理";
    }
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length,
                     String id,
                     String name,
                     String num,
                     ModelMap model){
        try {
            int pageNum = getPageNum(start, length);
            Page<SeriesTag> page = seriesTagService.backendFindByCondition(pageNum, length, id, name, num);
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/save")
    public void save(HttpServletRequest request,
                     HttpServletResponse response,
                     SeriesTag seriesTag){
        try {
            seriesTag.setCreateDate(new Date());
            if (seriesTag.getId() == null){
                seriesTagService.create(seriesTag);
            }else{
                seriesTagService.update(seriesTag) ;
            }
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            GeneralExceptionHandler.log("操作失败", e);
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }
    @RequestMapping(value = "/delete")
    public void delete(HttpServletRequest request,
                       HttpServletResponse response,
                       Integer  id){
        try {
            seriesTagService.deleteById(id);
            WebUtil.print(response, new Result(true).msg("操作成功！"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败！"));
        }
    }
}
