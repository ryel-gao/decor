package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.TResource;
import com.bluemobi.decor.entity.TRole;
import com.bluemobi.decor.entity.TRoleResource;
import com.bluemobi.decor.service.common.ICommonService;

import java.util.List;


/**
 * Created by gaoll on 2015/3/13.
 */
public interface TRoleResourceService extends ICommonService<TRoleResource> {

    public void saveResource(Integer roleId,String[] resourceIds);

    public void clearResource(Integer roleId);

    public List<TResource> resourceList(Integer roleId);

    public List<TResource> oneLevelResourceList(Integer roleId);

    public List<TResource> findTResourceByUrl(Integer pid,String url);

}
