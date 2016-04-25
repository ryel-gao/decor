package com.bluemobi.decor.portal.controller.background;

import com.bluemobi.decor.common.exception.GeneralExceptionHandler;
import com.bluemobi.decor.common.factory.DataTableFactory;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.StyleTag;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.StyleTagService;
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
@RequestMapping(value = "/backend/styleTag")
public class StyleTagController extends CommonController {
    @Autowired
    private StyleTagService styleTagService;

    @RequestMapping(value = "/index")
    public String index() {
        return "图片风格分类管理";
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
            Page<StyleTag> page = styleTagService.backendFindByCondition(pageNum, length, id, name,num);
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/save")
    public void save(HttpServletRequest request,
                     HttpServletResponse response,
                     StyleTag styleTag){
        try {
            styleTag.setCreateDate(new Date());

            if (styleTag.getId() == null){
                styleTagService.create(styleTag);
            }else{
                styleTagService.update(styleTag) ;
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
            styleTagService.deleteById(id);
            WebUtil.print(response, new Result(true).msg("操作成功！"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败！"));
        }
    }
}