package com.bluemobi.decor.portal.controller.api;

import com.bluemobi.decor.core.ERROR_CODE;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.Reminding;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.RemindingService;
import com.bluemobi.decor.service.UserService;
import com.bluemobi.decor.utils.APIFactory;
import com.bluemobi.decor.utils.DateUtils;
import com.bluemobi.decor.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * 消息提醒
 * Created by tuyh on 2015/8/6.
 */
@Controller
@RequestMapping("api/reminding")
public class RemindingApi extends CommonController {

    @Autowired
    private RemindingService remindingService;

    @Autowired
    private UserService userService;

    // 消息列表查询
    @RequestMapping(value = "/findRemindingList")
    public void findRemindingList(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Integer pageNum,
                                  Integer pageSize,
                                  Integer userId) {
        if (null == userId) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        Page<Reminding> page = remindingService.iPage(null == pageNum ? 1 : pageNum, null == pageSize ? 1000 : pageSize, userId);

        Map<String, Object> dataMap = APIFactory.fitting(page.getContent());
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "user");
        WebUtil.printApi(response, result);
    }

    // 新建消息提醒
    @RequestMapping(value = "/addReminding")
    public void addReminding(HttpServletRequest request,
                             HttpServletResponse response,
                             String content,
                             Integer userId,
                             String createTime) throws ParseException {
        if (null == userId || null == content || null == createTime) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        User user = userService.getById(userId);

        if (null == user) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0004));
            return;
        }

        Reminding reminding = new Reminding();
        reminding.setUser(user);
        reminding.setCreateTime(DateUtils.stringToDateWithFormat(createTime, "yyyy-MM-dd HH:mm:ss"));
        reminding.setContent(content);

        // 执行新建操作
        remindingService.create(reminding);
        String result = JsonUtil.obj2ApiJson(new Result(true));
        WebUtil.printApi(response, result);
    }
}
