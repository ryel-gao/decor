package com.bluemobi.decor.portal.controller.background;

import com.bluemobi.decor.common.exception.GeneralExceptionHandler;
import com.bluemobi.decor.common.factory.DataTableFactory;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.SpaceTag;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.UploadUtils;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.SpaceTagService;
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
@RequestMapping(value = "/backend/spaceTag")
public class SpaceTagController extends CommonController {
    @Autowired
    private SpaceTagService spaceTagService;

    @RequestMapping(value = "/index")
    public String index() {
        return "图片空间分类管理";
    }

    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length,
                     String id,
                     String name,
                     ModelMap model) {
        try {
            int pageNum = getPageNum(start, length);
            Page<SpaceTag> page = spaceTagService.backendFindByCondition(pageNum, length, id, name);
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/save")
    public void save(HttpServletRequest request,
                     HttpServletResponse response,
                     String imagePath, String logoPath,
                     SpaceTag spaceTag) {
        try {
            if (imagePath == null) {
                WebUtil.print(response, new Result(false).msg("图片不能为空"));
                return;
            }
            spaceTag.setImage(imagePath);
            spaceTag.setPicImage(logoPath);
            spaceTag.setCreateDate(new Date());

            if (spaceTag.getId() == null){
                spaceTagService.create(spaceTag);
            }else{
                spaceTagService.update(spaceTag) ;
            }
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            GeneralExceptionHandler.log("操作失败", e);
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @RequestMapping(value = "/delete")
    public void delete(HttpServletRequest request,
                       HttpServletResponse response,
                       Integer id) {
        try {
            spaceTagService.deleteById(id);
            WebUtil.print(response, new Result(true).msg("操作成功！"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败！"));
        }
    }


}
