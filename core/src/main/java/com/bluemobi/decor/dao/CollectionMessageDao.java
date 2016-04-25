package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.CollectionMessage;
import com.bluemobi.decor.entity.Message;
import com.bluemobi.decor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by tuyh on 2015/7/14 10:58.
 */
public interface CollectionMessageDao extends JpaRepository<CollectionMessage, Integer>, JpaSpecificationExecutor<CollectionMessage> {

    @Query("select a from CollectionMessage a where a.user = ?1 and a.message = ?2")
    public List<CollectionMessage> iFindCollectionWithUser(User user, Message message);

    @Query("select a from CollectionMessage a where a.user = ?1 and a.message = ?2")
    public List<CollectionMessage> pcFindCollectionWithUser(User user, Message message);

    @Query("select a from CollectionMessage a where a.message = ?1")
    public List<CollectionMessage> findInfoWithMessage(Message message);
}