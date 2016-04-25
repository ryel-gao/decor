package com.bluemobi.decor.portal.controller.background;

import com.bluemobi.decor.common.exception.GeneralExceptionHandler;
import com.bluemobi.decor.common.factory.DataTableFactory;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.Ad;
import com.bluemobi.decor.entity.KindTag;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.AdService;
import com.bluemobi.decor.service.KindTagService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Controller
@RequestMapping(value = "/backend/ad")
public class AdController extends CommonController {
    @Autowired
    private AdService adService;

    @RequestMapping(value = "/index")
    public String index() {
        return "广告管理";
    }

    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length) {
        try {
            int pageNum = getPageNum(start, length);
            Page<Ad> page = adService.backendPage(pageNum, length);
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/save")
    public void save(HttpServletRequest request,
                     HttpServletResponse response,
                     String image,
                     Ad ad) {
        try {
            if (StringUtils.isEmpty(image)) {
                WebUtil.print(response, new Result(false).msg("图片不能为空"));
                return;
            }
            ad.setImage(image);

            if (ad.getId() == null) {
                adService.create(ad);
            } else {
                adService.update(ad);
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
            adService.deleteById(id);
            WebUtil.print(response, new Result(true).msg("操作成功！"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败！"));
        }
    }

}
