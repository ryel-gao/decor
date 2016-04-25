package com.bluemobi.decor.portal.controller.background;

import com.bluemobi.decor.common.factory.DataTableFactory;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.Reminding;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.RemindingService;
import com.bluemobi.decor.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 消息提醒Controller
 * Created by reny on 2015/7/17.
 */
@Controller
@RequestMapping(value = "/backend/reminding")
public class RemindingController extends CommonController {

    @Autowired
    private RemindingService remindingService;

    @RequestMapping(value = "/index")
    public String index(){
        return "消息提醒";
    }

    /**
     * 分页获取消息提醒列表
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length,
                     ModelMap model){

        try {
            int pageNum = getPageNum(start, length);
            Page<Reminding> page = remindingService.find(pageNum, length);
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除单条消息提醒
     */
    @RequestMapping(value = "/delete")
    public void delete(HttpServletRequest request,
                       HttpServletResponse response,
                       Integer  id){
        try {
            remindingService.deleteById(id);
            WebUtil.print(response, new Result(true).msg("操作成功！"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败！"));
        }
    }

    /**
     * 删除多条消息提醒
     */
    @RequestMapping(value = "/deletes")
    public void deletes(HttpServletRequest request,
                       HttpServletResponse response,
                       String ids){
        try {
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);
            remindingService.deleteAll(arrayId);
            WebUtil.print(response, new Result(true).msg("操作成功！"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败！"));
        }
    }
}
