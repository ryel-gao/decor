package com.bluemobi.decor.portal.controller.pc;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.Praise;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.CommentService;
import com.bluemobi.decor.service.PraiseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 点赞
 * Created by gaoll on 2015/3/3.
 */
@Controller
@RequestMapping(value = "/pc/praise")
public class PraiseController4Pc extends CommonController {

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private CommentService commentService;

    /**
     * 点赞
     */
    @RequestMapping("/praise")
    public void praise(HttpServletRequest request,
                       HttpServletResponse response,
                       Integer userId,
                       Integer objectId,
                       String objectType) {
        try {
            if (userId == null || objectId == null || StringUtils.isEmpty(objectType)) {
                WebUtil.print(response, new Result(false).msg("操作失败!"));
                return;
            }

            if (!Constant.PRAISE_TYPE_SCENE.equals(objectType)
                    && !Constant.PRAISE_TYPE_GOODS.equals(objectType)
                    && !Constant.PRAISE_TYPE_SERIES.equals(objectType)
                    && !Constant.PRAISE_TYPE_COMMENT.equals(objectType)
                    && !Constant.PRAISE_TYPE_USER.equals(objectType)) {
                WebUtil.print(response, new Result(false).msg("操作失败!"));
                return;
            }

            if (Constant.PRAISE_TYPE_COMMENT.equals(objectType) && commentService.getById(objectId).getUser().getId() == userId) {
                WebUtil.print(response, new Result(false).msg("不能对自己的评论点赞，操作失败!"));
                return;
            }

            Praise praise = new Praise();
            praise.setObjectId(objectId);
            praise.setObjectType(objectType);
            User user = new User();
            user.setId(userId);
            praise.setUser(user);
            praiseService.create(praise);

            WebUtil.print(response, new Result(true).msg("点赞成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    /**
     * 取消点赞
     */
    @RequestMapping("/cancelPraise")
    public void cancelPraise(HttpServletRequest request,
                             HttpServletResponse response,
                             Integer userId,
                             Integer objectId,
                             String objectType) {
        try {
            if (userId == null || objectId == null || StringUtils.isEmpty(objectType)) {
                WebUtil.print(response, new Result(false).msg("操作失败!"));
                return;
            }

            if (!Constant.PRAISE_TYPE_SCENE.equals(objectType)
                    && !Constant.PRAISE_TYPE_GOODS.equals(objectType)
                    && !Constant.PRAISE_TYPE_SERIES.equals(objectType)
                    && !Constant.PRAISE_TYPE_COMMENT.equals(objectType)
                    && !Constant.PRAISE_TYPE_USER.equals(objectType)) {
                WebUtil.print(response, new Result(false).msg("操作失败!"));
                return;
            }

            Praise praise = new Praise();
            praise.setObjectId(objectId);
            praise.setObjectType(objectType);
            User user = new User();
            user.setId(userId);
            praise.setUser(user);
            praiseService.cancelPraiseBusiness(praise);

            WebUtil.print(response, new Result(true).msg("取消点赞成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }


}
