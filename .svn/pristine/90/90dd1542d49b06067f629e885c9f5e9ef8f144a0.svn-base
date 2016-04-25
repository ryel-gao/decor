package com.bluemobi.decor.portal.controller.background;

import com.bluemobi.decor.common.factory.DataTableFactory;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.Feedback;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * feedback管理
 * Created by liuhm on 2015/7/6
 */
@Controller
@RequestMapping(value = "/backend/feedback")
public class FeedbackController extends CommonController {
        @Autowired
        private FeedbackService feedbackService;

        @RequestMapping(value = "/index")
        public String index(){
            return "意见反馈列表";
        }

    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length,
                     String id,
                     String username,
                     ModelMap model){
        try {
            int pageNum = getPageNum(start, length);
            Page<Feedback> page = feedbackService.backendFindByCondition(pageNum, length, id, username);
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/delete")
    public void delete(HttpServletRequest request,
                       HttpServletResponse response,
                       Integer  id){
        try {
            feedbackService.deleteById(id);
            WebUtil.print(response, new Result(true).msg("操作成功！"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败！"));
        }
    }
}