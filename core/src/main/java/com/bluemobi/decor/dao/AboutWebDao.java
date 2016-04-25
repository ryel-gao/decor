package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.AboutWeb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by tuyh on 2015/7/23 14:03.
 */
public interface AboutWebDao extends JpaRepository<AboutWeb, Integer>, JpaSpecificationExecutor<AboutWeb> {

    @Query("select a from AboutWeb a where a.id = ?1")
    public AboutWeb iGetAboutUS(Integer id);
}