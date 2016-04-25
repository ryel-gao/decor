package com.bluemobi.decor.portal.controller.pc;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.*;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.SessionUtils;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 公共
 * Created by gaoll on 2015/3/3.
 */
@Controller
@RequestMapping(value = "/pc/comm")
public class CommController4Pc extends CommonController {

    @Autowired
    private KindTagService kindTagService;

    @Autowired
    private StyleTagService styleTagService;

    @Autowired
    private SpaceTagService spaceTagService;

    @Autowired
    private SeriesTagService seriesTagService;

    @Autowired
    private AttentionService attentionService;

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    // ajax 商品分类列表
    @RequestMapping(value = "/ajaxKindTagList")
    public void ajaxKindTagList(HttpServletRequest request,
                                 HttpServletResponse response){
        try {
            List<KindTag> list = kindTagService.all();
            WebUtil.print(response, new Result(true).data(list));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    // ajax风格分类列表
    @RequestMapping(value = "/ajaxStyleTagList")
    public void ajaxStyleTagList(HttpServletRequest request,
                                 HttpServletResponse response){
        try {
            List<StyleTag> list = styleTagService.all();
            WebUtil.print(response, new Result(true).data(list));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    // ajax空间分类列表
    @RequestMapping(value = "/ajaxSpaceTagList")
    public void ajaxSpaceTagList(HttpServletRequest request,
                                 HttpServletResponse response){
        try {
            List<SpaceTag> list = spaceTagService.all();
            WebUtil.print(response, new Result(true).data(list));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    // ajax系列图分类
    @RequestMapping(value = "/ajaxSeriesTagList")
    public void ajaxSeriesTagList(HttpServletRequest request,
                                 HttpServletResponse response){
        try {
            List<SeriesTag> list = seriesTagService.all();
            WebUtil.print(response, new Result(true).data(list));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    // ajax 查询是否关注
    @RequestMapping(value = "/isAttention")
    public void isAttention(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Integer userId,
                                 Integer fansId){
        try {
            Boolean flag = attentionService.isAttention(userId,fansId);
            WebUtil.print(response, new Result(true).data(flag));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    // ajax 查询是否点赞
    @RequestMapping(value = "/isPraise")
    public void isPraise(HttpServletRequest request,
                            HttpServletResponse response,
                            Integer userId,
                            Integer objectId,
                            String objectType){
        try {
            Boolean flag = praiseService.isPraise(userId, objectId, objectType);
            WebUtil.print(response, new Result(true).data(flag));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    // ajax 查询是否收藏
    @RequestMapping(value = "/isCollect")
    public void isCollect(HttpServletRequest request,
                          HttpServletResponse response,
                          Integer userId,
                          Integer objectId,
                          String objectType){
        try {
            Boolean flag = collectionService.isCollect(userId,objectId,objectType);
            WebUtil.print(response, new Result(true).data(flag));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    // ajax 关注
    @RequestMapping(value = "/attention")
    public void attention(HttpServletRequest request,
                          HttpServletResponse response,
                          Integer userId,
                          Integer fansId){
        try {
            attentionService.pcAttentionBusiness(userId,fansId);
            WebUtil.print(response, new Result(true).msg("关注成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("关注失败!"));
        }
    }

    // ajax 关注
    @RequestMapping(value = "/cancelAttention")
    public void cancelAttention(HttpServletRequest request,
                          HttpServletResponse response,
                          Integer userId,
                          Integer fansId){
        try {
            attentionService.pcCancelAttentionBusiness(userId,fansId);
            WebUtil.print(response, new Result(true).msg("取消关注成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("取消关注失败!"));
        }
    }

    // ajax查询省份
    @RequestMapping(value = "/ajaxProvince")
    public void ajaxProvince(HttpServletRequest request,
                                 HttpServletResponse response){
        try {
            List<Province> list = provinceService.findProvinceList();
            WebUtil.print(response, new Result(true).data(list));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    // ajax查询城市
    @RequestMapping(value = "/ajaxCity")
    public void ajaxCity(HttpServletRequest request,
                             HttpServletResponse response,
                             Integer provinceId){
        try {
            Province province = new Province();
            province.setId(provinceId);
            List<City> list = cityService.findCityByProvince(province);
            WebUtil.print(response, new Result(true).data(list));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }



}
