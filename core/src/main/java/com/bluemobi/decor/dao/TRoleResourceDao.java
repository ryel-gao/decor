package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.TResource;
import com.bluemobi.decor.entity.TRole;
import com.bluemobi.decor.entity.TRoleResource;
import com.bluemobi.decor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by gaoll on 2015/7/7 9:55.
 */
public interface TRoleResourceDao extends JpaRepository<TRoleResource, Integer>, JpaSpecificationExecutor<TRoleResource> {
    @Modifying
    @Query("delete from TRoleResource a where a.role.id =?1")
    public void clearResource(Integer roleId);

    @Query("select a.resource from TRoleResource a where a.role.id =?1")
    public List<TResource> resourceList(Integer roleId);

    @Query("select a from TResource a where a.pid =?1 and a.url like ?2")
    public List<TResource> findTResourceByUrl(Integer roleId, String url);
}