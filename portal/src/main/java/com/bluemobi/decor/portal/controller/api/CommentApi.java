package com.bluemobi.decor.portal.controller.api;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.core.ERROR_CODE;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.*;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.*;
import com.bluemobi.decor.utils.APIFactory;
import com.bluemobi.decor.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 评论 or 回复
 * Created by tuyh on 2015/7/10.
 */
@Controller
@RequestMapping("api/comment")
public class CommentApi {

    @Autowired
    private CommentService commentService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SceneService sceneService;

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsImageService goodsImageService;

    // 个人评论列表查询
    @RequestMapping(value = "/findMyCommentList")
    public void findMyCommentList(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Integer userId,
                                  Integer pageNum,
                                  Integer pageSize) {
        if (null == userId || null == pageNum || null == pageSize) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        Page<Comment> commentPage = commentService.iFindCommentPage(userId, pageNum, pageSize);

        Goods goods = null;
        Scene scene = null;

        // 读取评论的类型，从而查询出对应的评论源对象具体信息
        for (Comment comment : commentPage.getContent()) {
            // 存入评论用户的呢称和头像
            comment.setNickname(null == (comment.getUser().getNickname()) ? "" : comment.getUser().getNickname());
            comment.setPhoto(null == (comment.getUser().getHeadImage()) ? "" : comment.getUser().getHeadImage());

            // objectType=scene时，为场景图评论
            // objectType=goods时，为商品图评论
            if (comment.getObjectType().equals(Constant.COMMENT_TYPE_GOODS)) {
                // 此时为商品图评论
                goods = goodsService.getById(comment.getObjectId());
                List<GoodsImage> goodsImageList = goodsImageService.listByGoodsId(goods.getId());
                if (null == goodsImageList || goodsImageList.size() == 0) {
                    comment.setObjectImage("");
                } else {
                    comment.setObjectImage((goodsImageList.get(0)).getImage());
                }
            } else {
                // 此时为场景图评论
                scene = sceneService.getById(comment.getObjectId());
                if(scene != null){
                    comment.setObjectImage(scene.getImage());
                }else {
                    comment.setObjectImage("");
                }
            }

            User currUser = new User();
            currUser.setId(userId);
            // 判断当前评论是否点赞过
            // 查询点赞记录，如果有记录，则表示已点赞；否则表示没有点赞
            Praise praise = praiseService.iFindByUserAndObjectId(currUser, comment.getId(), Constant.PRAISE_TYPE_COMMENT);
            if (null == praise) {
                comment.setIfLove(1);
            } else {
                comment.setIfLove(0);
            }

            // 获取指定的评论回复列表
            Reply reply = null;
            User user = null;
            List<Reply> list = new ArrayList<Reply>();
            List<Comment> commentList = commentService.iFindReply(comment.getId());
            for (Comment comment1 : commentList) {
                user = comment1.getUser();

                reply = new Reply();
                reply.setNickname(null == (user.getNickname()) ? "" : user.getNickname());
                reply.setHeadImage(null == (user.getHeadImage()) ? "" : user.getHeadImage());
                reply.setContent(comment1.getContent());
                reply.setCreateTime(comment1.getCreateTime());

                list.add(reply);
            }
            comment.setReplyList(list);
        }

        Map<String, Object> dataMap = APIFactory.fitting(commentPage);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "thumbs", "createDate", "attribute", "brevitycode", "user", "objectType", "pid");
        WebUtil.printApi(response, result);
    }

    // 评论列表查询（商品图评论 or 场景图评论）
    @RequestMapping(value = "/findCommentList")
    public void findCommentList(HttpServletRequest request,
                                HttpServletResponse response,
                                Integer objectId,
                                String objectType,
                                Integer userId,
                                Integer pageNum,
                                Integer pageSize) {
        if (null == objectId || null == objectType || null == pageNum || null == pageSize) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        Page<Comment> commentPage = commentService.iFindCommentPages(objectId, objectType, pageNum, pageSize);

        Goods goods = null;
        Scene scene = null;

        // 读取评论的类型，从而查询出对应的评论源对象具体信息
        for (Comment comment : commentPage.getContent()) {
            // 存入评论用户的呢称和头像
            comment.setNickname(null == (comment.getUser().getNickname()) ? "" : comment.getUser().getNickname());
            comment.setPhoto(null == (comment.getUser().getHeadImage()) ? "" : comment.getUser().getHeadImage());

            // objectType=scene时，为场景图评论
            // objectType=goods时，为商品图评论
            if (comment.getObjectType().equals(Constant.COMMENT_TYPE_GOODS)) {
                // 此时为商品图评论
                goods = goodsService.getById(comment.getObjectId());
                List<GoodsImage> goodsImageList = goodsImageService.listByGoodsId(goods.getId());
                if (null == goodsImageList || goodsImageList.size() == 0) {
                    comment.setObjectImage("");
                } else {
                    comment.setObjectImage((goodsImageList.get(0)).getImage());
                }
                // 将商品图的作者ID返回
                comment.setObjectId(goods.getUser().getId());

                // 判断当前评论是否点赞过
                // 查询点赞记录，如果有记录，则表示已点赞；否则表示没有点赞
                if(userId == null){
                    comment.setIfLove(0);
                }else {
                    Praise praise = praiseService.iFindByUserAndObjectId(userService.getById(userId), comment.getId(), Constant.PRAISE_TYPE_GOODS);
                    if (null == praise) {
                        comment.setIfLove(1);
                    } else {
                        comment.setIfLove(0);
                    }
                }
            } else {
                // 此时为场景图评论
                scene = sceneService.getById(comment.getObjectId());
                comment.setObjectImage(scene.getImage());
                // 将场景图的作者ID返回
                comment.setObjectId(scene.getUser().getId());

                // 判断当前评论是否点赞过
                // 查询点赞记录，如果有记录，则表示已点赞；否则表示没有点赞
                if(userId == null){
                    comment.setIfLove(0);
                }else {
                    Praise praise = praiseService.iFindByUserAndObjectId(userService.getById(userId), comment.getId(), Constant.PRAISE_TYPE_SCENE);
                    if (null == praise) {
                        comment.setIfLove(1);
                    } else {
                        comment.setIfLove(0);
                    }
                }
            }


            // 获取指定的评论回复列表
            Reply reply = null;
            User user = null;
            List<Reply> list = new ArrayList<Reply>();
            List<Comment> commentList = commentService.iFindReply(comment.getId());
            for (Comment comment1 : commentList) {
                user = comment1.getUser();

                reply = new Reply();
                reply.setNickname(null == (user.getNickname()) ? "" : user.getNickname());
                reply.setHeadImage(null == (user.getHeadImage()) ? "" : user.getHeadImage());
                reply.setContent(comment1.getContent());
                reply.setCreateTime(comment1.getCreateTime());

                list.add(reply);
            }
            comment.setReplyList(list);

            comment.setMobile(comment.getUser().getMobile());
        }

        Map<String, Object> dataMap = APIFactory.fitting(commentPage);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "thumbs", "createDate", "attribute", "brevitycode", "user", "objectType", "pid", "objectStr", "objectName", "objectImage");
        WebUtil.printApi(response, result);
    }

    /**
     * 新增评论
     *
     * @param request
     * @param response
     * @param userId
     * @param type
     * @param content
     * @param objectType
     * @param objectId
     */
    @RequestMapping("/save")
    public void register(HttpServletRequest request,
                         HttpServletResponse response,
                         Integer userId,
                         Integer type,
                         String content,
                         String objectType,
                         Integer objectId) {
        if (null == userId || null == type || null == content || null == objectId || null == objectType) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        Comment comment = new Comment();

        comment.setUser(userService.getById(userId));
        comment.setContent(content);
        comment.setCreateTime(new Date());
        comment.setObjectType(objectType);
        comment.setObjectId(objectId);

        // type为0即是评论，为1即是回复
        if (type == 0) {
            comment.setPid(0);
        } else {
            comment.setPid(objectId);
        }

        // 执行添加评论操作
        commentService.iCreateCommentBusiness(comment);

        String result = JsonUtil.obj2ApiJson(new Result(true));
        WebUtil.printApi(response, result);
    }
}
