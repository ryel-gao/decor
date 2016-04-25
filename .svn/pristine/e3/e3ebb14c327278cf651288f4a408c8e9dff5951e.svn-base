package com.bluemobi.decor.portal.controller.pc;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.Favorite;
import com.bluemobi.decor.entity.Praise;
import com.bluemobi.decor.entity.Series;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.CollectionService;
import com.bluemobi.decor.service.FavoriteService;
import com.bluemobi.decor.service.PraiseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 收藏
 * Created by gaoll on 2015/3/3.
 */
@Controller
@RequestMapping(value = "/pc/collect")
public class CollectController4Pc extends CommonController {

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private CollectionService collectionService;

    // ajax查询收藏夹列表
    @RequestMapping(value = "/ajaxFavoriteList")
    public void ajaxFavoriteList(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Integer userId){
        try {
            List<Favorite> favoriteList = favoriteService.listByUserId(userId);
            for (Favorite favorite:favoriteList){
                String cover = favoriteService.cover(favorite.getId());
                favorite.setCover(cover);
            }
            WebUtil.print(response, new Result(true).data(favoriteList));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    /**
     * collect
     */
    @RequestMapping("/collect")
    public void collect(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Integer objectId,
                                 String objectType,
                                 Integer favoriteId,
                                 String favoriteName,
                                 String favoriteInfo,
                                 String favoriteCover,
                                 Integer userId) {
        try {
            if (objectId == null || StringUtils.isEmpty(objectType)) {
                WebUtil.print(response, new Result(false).msg("参数错误!"));
                return;
            }

            if(!Constant.COLLECTION_TYPE_GOODS.equals(objectType)
                    && !Constant.COLLECTION_TYPE_SCENE.equals(objectType)
                    && !Constant.COLLECTION_TYPE_SERIES.equals(objectType)){
                WebUtil.print(response, new Result(false).msg("收藏类型错误!"));
                return;
            }

            if(favoriteId == null && StringUtils.isEmpty(favoriteName)){
                WebUtil.print(response, new Result(false).msg("没有选择收藏夹!"));
                return;
            }

            String flag = collectionService.pcCollectBusiness(objectId, objectType, favoriteId, favoriteName, favoriteInfo, favoriteCover,userId);
            if("alreadyCollect".equals(flag)){
                WebUtil.print(response, new Result(false).msg("你已经收藏过!"));
                return;
            }

            WebUtil.print(response, new Result(true).msg("收藏成功!"));
        }catch (Exception e){
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    /**
     * 取消收藏
     */
    @RequestMapping("/cancelCollect")
    public void cancelPraise(HttpServletRequest request,
                       HttpServletResponse response,
                       Integer userId,
                       Integer objectId,
                       String objectType) {
        try {
            if (userId == null || objectId == null || StringUtils.isEmpty(objectType)) {
                WebUtil.print(response, new Result(false).msg("取消失败!"));
                return;
            }

            if(!Constant.COLLECTION_TYPE_GOODS.equals(objectType)
                    && !Constant.COLLECTION_TYPE_SERIES.equals(objectType)
                    && !Constant.COLLECTION_TYPE_SCENE.equals(objectType)){
                WebUtil.print(response, new Result(false).msg("取消失败!"));
                return;
            }

            collectionService.pcCancelCollectBusiness(userId,objectId,objectType);

            WebUtil.print(response, new Result(true).msg("取消成功!"));
        }catch (Exception e){
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }





}
