package com.bluemobi.decor.portal.controller.api;

import com.bluemobi.decor.core.ERROR_CODE;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.AboutWeb;
import com.bluemobi.decor.entity.Feedback;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.AboutWebService;
import com.bluemobi.decor.service.FeedbackService;
import com.bluemobi.decor.service.UserService;
import com.bluemobi.decor.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 关于网站
 * Created by tuyh on 2015/7/27.
 */
@Controller
@RequestMapping("api/aboutweb")
public class AboutWebApi {

    @Autowired
    private AboutWebService aboutWebService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserService userService;

    // 关于我们
    @RequestMapping(value = "/aboutus")
    public void iGetAboutUS(HttpServletRequest request,
                            HttpServletResponse response) {
        AboutWeb aboutWeb = aboutWebService.iGetAboutUS();
        String result = JsonUtil.obj2ApiJson(new Result(true).data(aboutWeb), "id", "title", "createTime");
        WebUtil.printApi(response, result);
    }

    // 提交意见反馈
    @RequestMapping(value = "/addFeedBack")
    public void addFeedBack(HttpServletRequest request,
                            HttpServletResponse response,
                            Integer userId,
                            String content) {
        if (null == userId || null == content) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        User user = userService.getById(userId);
        if (null == user) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0004));
            return;
        }

        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setContent(content);
        feedback.setCreateTime(new Date());

        feedbackService.create(feedback);
        WebUtil.printApi(response, new Result(true));
    }
}
