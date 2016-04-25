package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.TResource;
import com.bluemobi.decor.entity.TRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by gaoll on 2015/7/7 9:55.
 */
public interface TRoleDao extends JpaRepository<TRole, Integer>, JpaSpecificationExecutor<TRole> {

    @Query("select a from TRole a where a.name =?1")
    public List<TRole> get(String name);

    @Query("select a from TRole a where a.id<>?1 and a.name =?2")
    public List<TRole> getNotId(Integer id,String name);
}