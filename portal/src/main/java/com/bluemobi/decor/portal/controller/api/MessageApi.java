package com.bluemobi.decor.portal.controller.api;

import com.bluemobi.decor.core.ERROR_CODE;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.CollectionMessage;
import com.bluemobi.decor.entity.Message;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.CollectionMessageService;
import com.bluemobi.decor.service.MessageService;
import com.bluemobi.decor.service.UserService;
import com.bluemobi.decor.utils.APIFactory;
import com.bluemobi.decor.utils.ComFun;
import com.bluemobi.decor.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 资讯
 * Created by tuyh on 2015/7/14.
 */
@Controller
@RequestMapping("api/message")
public class MessageApi extends CommonController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private CollectionMessageService collectionMessageService;

    @Autowired
    private UserService userService;

    // 资讯列表查询
    @RequestMapping(value = "/findMessageList")
    public void findMyCommentList(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Integer ifAll,
                                  Integer userId,
                                  Integer pageNum,
                                  Integer pageSize) {
        if (null == userId || null == pageNum || null == pageSize) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        Page<Message> messagePage = messageService.iFindMessagePage(ifAll, pageNum, pageSize);

        // 解析资讯标签
        CollectionMessage collectionMessage = null;
        for (Message message : messagePage.getContent()) {
            message.setPath(message.getImage());

            // tag去掉@
            message.setShowTags(ComFun.tagsThin(message.getShowTags()));

            collectionMessage = collectionMessageService.iFindCollectionWithUser(userService.getById(userId), message);
            // 如果查询结果不为空，则说明当前用户已收藏该资讯
            if (null != collectionMessage) {
                message.setIfCollect(0);
            } else {
                message.setIfCollect(1);
            }
        }

        Map<String, Object> dataMap = APIFactory.fitting(messagePage);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "showTurn", "isRecommend", "messageTag");
        WebUtil.printApi(response, result);
    }

    // 我收藏的资讯
    @RequestMapping(value = "/findMessageCollect")
    public void findMessageCollect(HttpServletRequest request,
                                   HttpServletResponse response,
                                   Integer userId,
                                   Integer pageNum,
                                   Integer pageSize) {
        if (null == userId || null == pageNum || null == pageSize) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        Page<CollectionMessage> messagePage = collectionMessageService.iPageCollectionWithUser(userId, pageNum, pageSize);

        // 解析资讯标签
        Message tempMessage = null;
        List<Message> list = new ArrayList<Message>();

        for (CollectionMessage message : messagePage.getContent()) {
            tempMessage = message.getMessage();
            tempMessage.setPath(tempMessage.getImage());

            // tag去掉@
            tempMessage.setShowTags(ComFun.tagsThin(tempMessage.getShowTags()));

            list.add(tempMessage);
        }

        Map<String, Object> dataMap = APIFactory.fitting(messagePage, list);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "showTurn", "isRecommend", "messageTag", "ifCollect");
        WebUtil.printApi(response, result);
    }

    /**
     * 收藏 or 取消收藏
     */
    @RequestMapping("/addOrDelete")
    public void addOrDelete(HttpServletRequest request,
                            HttpServletResponse response,
                            Integer userId,
                            Integer type,
                            Integer objectId) {
        if (null == type || null == userId || null == objectId) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        Message message = messageService.getById(objectId);
        if (null == message) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        // 类型
        // 1表示收藏
        // 2表示取消收藏
        CollectionMessage collectionMessage = collectionMessageService.iFindCollectionWithUser(userService.getById(userId), message);
        if (type == 1) {
            if (null != collectionMessage) {
                WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0017));
                return;
            }
            CollectionMessage collectionTemp = new CollectionMessage();
            collectionTemp.setUser(userService.getById(userId));
            collectionTemp.setMessage(message);
            collectionMessageService.create(collectionTemp);
        } else {
            if (null == collectionMessage) {
                WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0023));
                return;
            }
            collectionMessageService.deleteById(collectionMessage.getId());
        }

        WebUtil.printApi(response, new Result(true));
    }
}
