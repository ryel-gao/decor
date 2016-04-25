package com.bluemobi.decor.portal.controller.api;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.core.ERROR_CODE;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.Praise;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.CommentService;
import com.bluemobi.decor.service.PraiseService;
import com.bluemobi.decor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 点赞
 * Created by tuyh on 2015/7/9.
 */
@Controller
@RequestMapping("api/praise")
public class PraiseApi extends CommonController {

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    /**
     * 点赞 or 取消点赞
     */
    @RequestMapping("/addOrDelete")
    public void addOrDelete(HttpServletRequest request,
                            HttpServletResponse response,
                            Integer userId,
                            Integer type,
                            Integer objectId,
                            String objectType) {
        if (null == type || null == userId || null == objectId || null == objectType) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        // 类型
        // 1表示点赞
        // 2表示取消点赞
        Praise praise = praiseService.iFindByUserAndObjectId(userService.getById(userId), objectId, objectType);
        if (type == 1) {
            if (Constant.PRAISE_TYPE_COMMENT.equals(objectType) && commentService.getById(objectId).getUser().getId() == userId) {
                WebUtil.print(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0026));
                return;
            }

            if (null != praise) {
                WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0015));
                return;
            }
            Praise praiseTemp = new Praise();
            praiseTemp.setObjectType(objectType);
            praiseTemp.setObjectId(objectId);
            praiseTemp.setUser(userService.getById(userId));
            praiseService.create(praiseTemp);
        } else {
            if (null == praise) {
                WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
                return;
            }
            praiseService.deleteById(praise.getId());
        }

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * 判断是否点赞
     */
    @RequestMapping("/check")
    public void check(HttpServletRequest request,
                      HttpServletResponse response,
                      Integer userId,
                      Integer objectId,
                      String objectType) {
        if (null == userId || null == objectId || null == objectType) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        // 查询点赞记录，如果有记录，则表示已点赞；否则表示没有点赞
        Praise praise = praiseService.iFindByUserAndObjectId(userService.getById(userId), objectId, objectType);
        Map<String, Object> map = new HashMap<String, Object>();
        if (null == praise) {
            map.put("status", "N");
        } else {
            map.put("status", "Y");
        }

        WebUtil.printApi(response, new Result(true).data(map));
    }
}
