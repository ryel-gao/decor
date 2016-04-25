package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.TResource;
import com.bluemobi.decor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by gaoll on 2015/7/7 9:55.
 */
public interface TResourceDao extends JpaRepository<TResource, Integer>, JpaSpecificationExecutor<TResource> {

    @Query("select a from TResource a where a.pid = ?1")
    public List<TResource> findTResourceByPid(Integer pid);

}