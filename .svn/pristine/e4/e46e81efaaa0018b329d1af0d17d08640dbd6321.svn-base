package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.Attention;
import com.bluemobi.decor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by tuyh on 2015/7/10 14:08.
 */
public interface AttentionDao extends JpaRepository<Attention, Integer>, JpaSpecificationExecutor<Attention> {

    @Query("select a from Attention a where a.attentionUser = ?1")
    public List<Attention> findFansList(User user);

    @Query("select a from Attention a where a.fansUser = ?1")
    public List<Attention> findAttentionList(User user);

    @Query("select a from Attention a where a.attentionUser = ?1 and a.fansUser = ?2")
    public List<Attention> iCheckUser(User user, User user2);

    @Modifying
    @Query("update Attention a set a.userHasUpdate = 1 where a.attentionUser.id = ?1")
    public void userHasUpdate(Integer userId);

    @Modifying
    @Query("update Attention a set a.userHasUpdate = 0 where a.attentionUser.id = ?1 and a.fansUser.id = ?2")
    public void clearUserUpdate(Integer attentionUserId,Integer userId);

    @Query("select count(a.id) from Attention a where a.attentionUser.id = ?1 and a.fansUser.id = ?2")
    public Integer isAttention(Integer userId,Integer fansId);

    @Query("select a from Attention a where a.attentionUser.id = ?1 and a.fansUser.id = ?2")
    public List<Attention> listByUserIdAndFansId(Integer userId,Integer fansId);

}