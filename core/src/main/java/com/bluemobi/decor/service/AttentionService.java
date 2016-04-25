package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.Attention;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by tuyh on 2015/7/10 14:09.
 */
public interface AttentionService extends ICommonService<Attention> {

    // 获取指定用户的粉丝列表
    public Page<Attention> iFindFansPage(User user, Integer pageNum, Integer pageSize);

    // 获取指定用户的关注列表
    public Page<Attention> iFindAttentionPage(User user, Integer pageNum, Integer pageSize);

    // 检查用户是否是另一个用户的粉丝
    public Attention iCheckUser(User user,User user2);

    public void userHasUpdate(Integer userId);

    public void clearUserUpdate(Integer attentionUserId,Integer userId);
    //查询关注量和被关注量
    public List findFansNum(User user);
    //查询当前用户的关注的user分页
    public Page<Attention> pcFindAttentionPage( User user, Integer pageNum, Integer pageSize);
    //查询当前用户被关注的FANS分页
    public Page<Attention> pcFindFansPage(User user, Integer pageNum, Integer pageSize);

    // 检查用户是否是另一个用户的粉丝
    public Boolean isAttention(Integer userId,Integer fansId);

    public void pcAttentionBusiness(Integer userId,Integer fansId);

    public void pcCancelAttentionBusiness(Integer userId,Integer fansId);
}