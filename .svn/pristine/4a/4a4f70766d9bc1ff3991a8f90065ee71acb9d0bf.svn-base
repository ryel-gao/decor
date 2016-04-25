package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.CollectionMessage;
import com.bluemobi.decor.entity.Message;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by tuyh on 2015/7/14 11:00.
 */
public interface CollectionMessageService extends ICommonService<CollectionMessage> {

    // 通过用户ID和资讯ID查询指定的收藏记录
    public CollectionMessage iFindCollectionWithUser(User user, Message message);
     // 通过用户ID和资讯ID查询指定的收藏记录pc
    public List<CollectionMessage> pcFindCollectionWithUser(User user, Message message);

    // 查询我收藏的资讯列表
    public Page<CollectionMessage> iPageCollectionWithUser(Integer userId, Integer pageNum, Integer pageSize);
    // 查询我收藏的资讯列表PC
    public Page<CollectionMessage> pcPageCollectionWithUser(Integer userId, Integer pageNum, Integer pageSize);

    // 根据资讯ID查询对应的收藏信息
    public List<CollectionMessage> findInfoWithMessage(Message message);
}