package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.Praise;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.service.common.ICommonService;

/**
 * Created by tuyh on 2015/7/9 11:38.
 */
public interface PraiseService extends ICommonService<Praise> {

    // 根据用户ID和点赞源ID查询指定点赞信息
    public Praise iFindByUserAndObjectId(User user, Integer objectId, String objectType);

    // 获取用户点赞数
    public int iGetPraiseNumByUser(User user);

    // 判断用户是否点赞了某个对象
    public Boolean isPraise(Integer userId,Integer objectId,String objectType);

    // 获取用户的被点赞数
    public int iGetPraiseNumWithUser(Integer userId);

    //根据点赞内容ID和点赞内容类型删除点赞
    public void deleteByObjectIdAndObjectType(Integer ObjectId, String objectType);

    // 取消点赞业务方法
    public String cancelPraiseBusiness(Praise praise);
}