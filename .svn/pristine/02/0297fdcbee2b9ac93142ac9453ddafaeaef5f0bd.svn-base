package com.bluemobi.decor.portal.controller.pc;

import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.Message;
import com.bluemobi.decor.entity.MessageTag;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.PcPageFactory;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.MessageService;
import com.bluemobi.decor.service.MessageTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by xiaoj on 2015/9/28.
 */
@Controller
@RequestMapping(value = "/pc/message")
public class MessageController4Pc extends CommonController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageTagService messageTagService;


    //查询messageTagList
    @RequestMapping(value = "/messageTagShow")
    public void list(HttpServletResponse response
    ) {
        List<MessageTag> tagList = messageTagService.findAll();
        WebUtil.print(response, new Result(true).data(tagList));
    }


    //分页
    @RequestMapping(value = "/pageMessage")
    public void showMessageList(HttpServletRequest request, HttpServletResponse response, Integer pageNum,
                                Integer pageSize, Integer styleTagId, String name,Integer userId) {
        try {
            if (pageNum == null) {
                pageNum = 1;
            }
            if (pageSize == null) {
                pageSize = 10;
            }
            Page<Message> page = messageService.pcPage(pageNum, pageSize, styleTagId, name);
            for (Message message : page.getContent()){
                // 判断是否收藏
                if(userId != null){
                    Integer messageId = message.getId();
                    boolean judge = messageService.ajaxJudgeCollection(messageId, userId);
                    if(judge){
                        message.setIfCollect(1);
                    }else {
                        message.setIfCollect(0);
                    }

                }else {
                    message.setIfCollect(0);
                }
            }
            Map<String, Object> dataMap = PcPageFactory.fitting(page);
            WebUtil.print(response, new Result(true).data(dataMap));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    // forward to 资讯详情页
    @RequestMapping(value = "/detail")
    public String detail(ModelMap modelMap,
                         HttpServletRequest request,
                         Integer messageId) {
        Message message = messageService.getById(messageId);
        modelMap.put("message", message);
        return "pc/资讯详情页";
    }

    // ajax查询展示的资讯信息
    @RequestMapping(value = "/ajaxMessageById")
    public void ajaxMessageById(HttpServletRequest request,
                                HttpServletResponse response,
                                Integer messageId) {
        try {
            Message message = messageService.getById(messageId);
            WebUtil.print(response, new Result(true).data(message));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    //ajax 根据推荐和类型 查询同类资讯
    @RequestMapping(value = "/ajaxSameTypeMessage")
    public void ajaxSameTypeMessage(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Integer messageId) {
        try {
            List<Message> messageList = messageService.SameTypeMessage(messageId);
            WebUtil.print(response, new Result(true).data(messageList));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    //ajax 上下页跳转
    @RequestMapping(value = "/ajaxPageJump")
    public void ajaxPageJump(HttpServletRequest request,
                             HttpServletResponse response,
                             Integer messageId) {
        try {
            List<Message> messageList = messageService.PageJump(messageId);
            WebUtil.print(response, new Result(true).data(messageList));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    //收藏资讯
    @RequestMapping(value = "/saveCollectionMessage")
    public void saveCollectionMessage(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Integer messageId, Integer userId) {
        try {
            messageService.collectionMessage(messageId, userId);
            WebUtil.print(response, new Result(true).msg("收藏成功!"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("收藏失败!"));
        }
    }

    //取消收藏
    @RequestMapping(value = "/cancelledMessage")
    public void cancelledMessage(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Integer messageId, Integer userId) {
        try {
            messageService.cancelledMessage(messageId, userId);
            WebUtil.print(response, new Result(true).msg("取消成功!"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("取消失败!"));
        }
    }

    /*判断该资讯用户是否收藏*/
    @RequestMapping(value = "/ajaxJudgeCollection")
    public void ajaxJudgeCollection(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Integer messageId, Integer userId) {
        try {
            boolean judge = messageService.ajaxJudgeCollection(messageId, userId);
            WebUtil.print(response, new Result(true).data(judge));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("显示失败!"));
        }
    }

    /*增加收藏量*/
    @RequestMapping(value = "/ajaxCollectionNumAdd")
    public void ajaxMessageCollectionAdd(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Integer messageId, boolean addOrDel) {
        try {
            int collectionNum = messageService.messageCollectionAdd(messageId, addOrDel);
            if (collectionNum<0){
                collectionNum=0;
               Message message=messageService.getById(messageId);
               message.setCollectionNum(0);
                messageService.update(message);
            }
            WebUtil.print(response, new Result(true).data(collectionNum));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("显示失败!"));
        }
    }

}

