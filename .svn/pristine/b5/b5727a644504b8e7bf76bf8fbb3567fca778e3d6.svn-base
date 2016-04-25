package com.bluemobi.decor.portal.controller.pc;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.core.ERROR_CODE;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.Comment;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.CommentService;
import com.bluemobi.decor.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 评论
 */
@Controller
@RequestMapping("/pc/comment")
public class CommentController4Pc extends CommonController {

    @Autowired
    private CommentService commentService;

    /**
     * 新增商品评论
     */
    @RequestMapping("/saveGoodsComment")
    public void saveGoodsComment(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer userId,
                     String content,
                     Integer goodsId) {
        if (null == userId || StringUtils.isEmpty(content) || goodsId == null) {
            WebUtil.print(response, new Result(false).msg("操作错误!"));
            return;
        }

        try {
            commentService.createComment(userId, goodsId, Constant.COMMENT_TYPE_GOODS, content);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        }catch (Exception e){
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    /**
     * 新增场景评论
     */
    @RequestMapping("/saveSceneComment")
    public void saveSceneComment(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Integer userId,
                                 String content,
                                 Integer sceneId) {
        if (null == userId || StringUtils.isEmpty(content) || sceneId == null) {
            WebUtil.print(response, new Result(false).msg("操作错误!"));
            return;
        }

        try {
            commentService.createComment(userId, sceneId, Constant.COMMENT_TYPE_SCENE, content);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        }catch (Exception e){
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    /**
     * 新增系列图评论
     */
    @RequestMapping("/saveSeriesComment")
    public void saveSeriesComment(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Integer userId,
                                 String content,
                                 Integer seriesId) {
        if (null == userId || StringUtils.isEmpty(content) || seriesId == null) {
            WebUtil.print(response, new Result(false).msg("操作错误!"));
            return;
        }

        try {
            commentService.createComment(userId, seriesId, Constant.COMMENT_TYPE_SERIES, content);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        }catch (Exception e){
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    /**
     * 新增场景评论
     */
    @RequestMapping("/saveCommentReply")
    public void saveCommentReply(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Integer userId,
                                 String content,
                                 Integer commentId) {
        if (null == userId || StringUtils.isEmpty(content) || commentId == null) {
            WebUtil.print(response, new Result(false).msg("操作错误!"));
            return;
        }

        try {
            commentService.createComment(userId, commentId, Constant.COMMENT_TYPE_COMMENT, content);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        }catch (Exception e){
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }


}
