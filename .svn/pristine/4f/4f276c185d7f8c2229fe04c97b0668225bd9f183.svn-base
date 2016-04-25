package com.bluemobi.decor.portal.controller.api;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.core.ERROR_CODE;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.Attention;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.AttentionService;
import com.bluemobi.decor.service.UserService;
import com.bluemobi.decor.utils.APIFactory;
import com.bluemobi.decor.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 关注 or 粉丝
 * Created by tuyh on 2015/7/10.
 */
@Controller
@RequestMapping("api/attention")
public class AttentionApi {

    @Autowired
    private AttentionService attentionService;

    @Autowired
    private UserService userService;

    // 关注 or 粉丝列表
    @RequestMapping(value = "/findFans")
    public void findGoodsList(HttpServletRequest request,
                              HttpServletResponse response,
                              Integer pageNum,
                              Integer pageSize,
                              Integer id,
                              Integer type, Integer userId) {
        Page<Attention> page = null;

        // 类型，0=粉丝，1=关注
        if (type == 0) {
            // 此处查询到的是id这个用户的粉丝列表
            page = attentionService.iFindFansPage(userService.getById(id), pageNum, pageSize);
        } else {
            // 此处查询到的是id这个用户关注的人的列表（即id这个用户本身是这个列表中所有人的粉丝）
            page = attentionService.iFindAttentionPage(userService.getById(id), pageNum, pageSize);
        }

        for (Attention attention : page.getContent()) {
            // 类型，0=粉丝，1=关注
            if (type == 0) {
                attention.setUserId(null == attention.getFansUser() ? 0 : attention.getFansUser().getId());
                attention.setPhoto(null == attention.getFansUser().getHeadImage() ? "" : attention.getFansUser().getHeadImage());
                attention.setName(null == attention.getFansUser() ? "" : attention.getFansUser().getNickname());
                attention.setInfo(null == attention.getFansUser() ? "" : attention.getFansUser().getInfo());
                attention.setIsAttention(null != attentionService.iCheckUser(attention.getFansUser(), userService.getById(id)) ? "Y" : "N");
            } else {
                attention.setUserId(null == attention.getAttentionUser() ? 0 : attention.getAttentionUser().getId());
                attention.setPhoto(null == attention.getAttentionUser().getHeadImage() ? "" : attention.getAttentionUser().getHeadImage());
                attention.setName(null == attention.getAttentionUser() ? "" : attention.getAttentionUser().getNickname());
                attention.setInfo(null == attention.getAttentionUser() ? "" : attention.getAttentionUser().getInfo());
                attention.setIsAttention(null != attentionService.iCheckUser(attention.getAttentionUser(), userService.getById(id)) ? "Y" : "N");
            }

            // 类型，0=粉丝，1=关注
            if (type == 0) {
                // 查询关注记录，如果有记录，则表示已关注；否则表示没有关注
                Attention attention1 = attentionService.iCheckUser(attention.getFansUser(), userService.getById(userId));
                if (attention1 == null) {
                    attention.setStatus("N");
                } else {
                    attention.setStatus("Y");
                }
            } else {
                // 查询关注记录，如果有记录，则表示已关注；否则表示没有关注
                Attention attention1 = attentionService.iCheckUser(attention.getAttentionUser(), userService.getById(userId));
                if (attention1 == null) {
                    attention.setStatus("N");
                } else {
                    attention.setStatus("Y");
                }
            }

        }

        Map<String, Object> dataMap = APIFactory.fitting(page);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "attentionUser", "fansUser", "id");
        WebUtil.printApi(response, result);
    }

    /**
     * 关注 or 取消关注
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

        // 类型
        // 1表示关注
        // 2表示取消关注
        Attention attention = attentionService.iCheckUser(userService.getById(objectId), userService.getById(userId));
        if (type == 1) {
            if (null != attention) {
                WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0014));
                return;
            }
            Attention attentionTemp = new Attention();
            attentionTemp.setAttentionUser(userService.getById(objectId));
            attentionTemp.setFansUser(userService.getById(userId));

            attentionService.create(attentionTemp);

            // 关注成功后，被关注对象的粉丝数量加1
            User user = userService.getById(objectId);
            user.setFans(user.getFans() + 1);
            userService.update(user);
        } else {
            if (null == attention) {
                WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0021));
                return;
            }
            attentionService.deleteById(attention.getId());

            // 取消关注成功后，被关注对象的粉丝数量减1
            User user = userService.getById(objectId);
            user.setFans(user.getFans() > 0 ? user.getFans() - 1 : 0);
            userService.update(user);
        }

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * 判断是否关注
     */
    @RequestMapping("/check")
    public void check(HttpServletRequest request,
                      HttpServletResponse response,
                      Integer userId,
                      Integer objectId) {
        if (null == userId || null == objectId) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        // 查询关注记录，如果有记录，则表示已关注；否则表示没有关注
        Attention attention = attentionService.iCheckUser(userService.getById(objectId), userService.getById(userId));
        Map<String, Object> map = new HashMap<String, Object>();
        if (null == attention) {
            map.put("status", "N");
        } else {
            map.put("status", "Y");
        }

        WebUtil.printApi(response, new Result(true).data(map));
    }

    /**
     * 清除关注用户更新状态
     */
    @RequestMapping("/clearUserUpdate")
    public void clearUserUpdate(HttpServletRequest request,
                                HttpServletResponse response,
                                Integer attentionUserId,
                                Integer userId) {
        attentionService.clearUserUpdate(attentionUserId, userId);
        WebUtil.printApi(response, new Result(true));
    }
}
