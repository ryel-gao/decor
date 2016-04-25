package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.City;
import com.bluemobi.decor.entity.CommentUpdateStatus;
import com.bluemobi.decor.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 */
public interface CommentUpdateStatusDao extends JpaRepository<CommentUpdateStatus, Integer>, JpaSpecificationExecutor<CommentUpdateStatus> {

    @Query("select a from CommentUpdateStatus a where a.user.id = ?1 and a.objectId = ?2 and a.objectType = ?3")
    public List<CommentUpdateStatus> listByUserIdAndObjectIdAndObjectType(Integer userId,Integer objectId,String objectType);

    @Query("select a from CommentUpdateStatus a where a.objectId = ?1 and a.objectType = ?2 order by a.updateTime desc")
    public List<CommentUpdateStatus> findCommentUpdateStatusByObjectIdAndObjectType(Integer objectId, String objectType);

}