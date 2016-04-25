package com.bluemobi.decor.portal.controller.background;

import com.bluemobi.decor.common.factory.DataTableFactory;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.DrawBoard;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.DrawBoardService;
import com.bluemobi.decor.utils.JsonUtil;
import com.bluemobi.decor.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 画板Controller
 * Created by reny on 2015/7/20.
 */
@Controller
@RequestMapping(value = "/backend/drawBoard")
public class DrawBoardController extends CommonController {
    @Autowired
    private DrawBoardService drawBoardService;

    @RequestMapping(value = "/index")
    public String index() {
        return "画板作品";
    }

    /**
     * 分页获取画板信息列表
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length,
                     Integer id,
                     String author,
                     ModelMap model) {

        try {
            int pageNum = getPageNum(start, length);
            Page<DrawBoard> page = drawBoardService.findDrawBoards(id, author, pageNum, length);
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除单条画板信息
     */
    @RequestMapping(value = "/delete")
    public void delete(HttpServletRequest request,
                       HttpServletResponse response,
                       Integer id) {
        try {
            DrawBoard drawBoard = drawBoardService.getById(id);
            if (drawBoard.getImage() != null) {
                UploadUtils.deleteFile(drawBoard.getImage());
            }
            drawBoardService.deleteById(id);
            WebUtil.print(response, new Result(true).msg("操作成功！"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败！"));
        }
    }

    /**
     * 删除多条画板信息
     */
    @RequestMapping(value = "/deletes")
    public void deletes(HttpServletRequest request,
                        HttpServletResponse response,
                        String ids) {
        try {
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);
            drawBoardService.deleteAll(arrayId);
            WebUtil.print(response, new Result(true).msg("操作成功！"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败！"));
        }
    }
}
