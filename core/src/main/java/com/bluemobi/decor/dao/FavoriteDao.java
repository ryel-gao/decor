package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.Collection;
import com.bluemobi.decor.entity.Favorite;
import com.bluemobi.decor.entity.Praise;
import com.bluemobi.decor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by tuyh on 2015/7/9 10:41.
 */
public interface FavoriteDao extends JpaRepository<Favorite, Integer>, JpaSpecificationExecutor<Favorite> {

    @Query("select a from Favorite a where a.user = ?1")
    public List<Favorite> iFindAllCollects(User user);

    @Query("select a from Favorite a where a.user.id = ?1")
    public List<Favorite> listByUserId(Integer userId);

}