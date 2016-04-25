package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.Praise;
import com.bluemobi.decor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by tuyh on 2015/7/9 11:39.
 */
public interface PraiseDao extends JpaRepository<Praise, Integer>, JpaSpecificationExecutor<Praise> {

    @Query("select a from Praise a where a.user = ?1 and a.objectId = ?2 and a.objectType = ?3")
    public List<Praise> iFindByUserAndObjectId(User user, Integer objectId, String objectType);

    @Query("select a from Praise a where a.user = ?1")
    public List<Praise> iGetPraiseNumByUser(User user);

    @Query("select a from Praise a where a.objectId = ?1 and a.objectType = ?2")
    public List<Praise> iGetPraiseNumWithUser(Integer userId, String objectType);

    @Query("select a from Praise a where a.user.id = ?1 and a.objectId = ?2 and a.objectType = ?3")
    public List<Praise> getByUserIdAndObjectIdAndObjectType(Integer userId, Integer objectId, String objectType);

    //根据点赞内容ID和点赞内容类型删除点赞
    @Modifying
    @Query("delete from Praise a where a.objectId =?1 and a.objectType =?2")
    public void deleteByObjectIdAndObjectType(Integer ObjectId, String objectType);
}