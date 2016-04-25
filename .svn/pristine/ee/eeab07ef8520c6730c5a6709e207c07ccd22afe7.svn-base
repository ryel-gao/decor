package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.TResource;
import com.bluemobi.decor.entity.TRole;
import com.bluemobi.decor.entity.TUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by gaoll on 2015/7/7 9:55.
 */
public interface TUserRoleDao extends JpaRepository<TUserRole, Integer>, JpaSpecificationExecutor<TUserRole> {
    @Query("select a from TUserRole a where a.role.id =?1")
    public List<TUserRole> list(Integer roleId);

    @Query("select a from TUserRole a where a.user.id =?1")
    public List<TUserRole> findByUserId(Integer userId);

    @Query("select a from TUserRole a where a.role.id = 11")
    public TUserRole findMaster();
}