package com.bluemobi.decor.portal.controller.background;

import com.bluemobi.decor.common.factory.DataTableFactory;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.Comment;
import com.bluemobi.decor.entity.Goods;
import com.bluemobi.decor.entity.Scene;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.CommentService;
import com.bluemobi.decor.service.GoodsService;
import com.bluemobi.decor.service.SceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * comment管理
 * Created by liuhm on 2015/7/6
 */
@Controller
@RequestMapping(value = "/backend/comment")
public class CommentController extends CommonController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SceneService sceneService;

        @RequestMapping(value = "/index")
        public String index(){
            return "评价管理";
        }

    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length,
                     String username,
                     String pid,
                     ModelMap model){
        try {
            int pageNum = getPageNum(start, length);
            Page<Comment> page = commentService.backendFindByCondition(pageNum, length, username,pid);
            for(Comment comment : page.getContent()){
                if("goods".equals(comment.getObjectType())){
                    comment.setObjectStr("商品图");
                    Goods goods = goodsService.getById(comment.getObjectId());
                    if (goods==null){
                        comment.setObjectName("");
                    }else {
                        comment.setObjectName(goods.getName());
                    }

                }
                if ("scene".equals(comment.getObjectType())){
                    comment.setObjectStr("场景图");
                    Scene scene = sceneService.getById(comment.getObjectId());
                    if(scene==null){
                        comment.setObjectName("");
                    }else {
                        comment.setObjectName(scene.getName());
                    }

                }

            }
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/delete")
    public void delete(HttpServletRequest request,
                       HttpServletResponse response,
                       Integer id){
        try {
            commentService.deleteById(id);
            WebUtil.print(response, new Result(true).msg("操作成功！"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败！"));
        }
    }
}