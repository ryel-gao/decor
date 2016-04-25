package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.CommentUpdateStatus;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 */
public interface CommentUpdateStatusService extends ICommonService<CommentUpdateStatus> {

    void createOrUpdateBusiness(Integer userId,Integer objectId,String objectType);
    //查询用户评论记录
    public Page<CommentUpdateStatus> pcFindCommentPage(Integer userId, Integer pageNum, Integer pageSize);
    //删除某一类的用户评论记录
    public void deleteByObjectIdAndObjectType(int objId,String objType);

}