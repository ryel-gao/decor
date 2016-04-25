package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.TResource;
import com.bluemobi.decor.entity.TRole;
import com.bluemobi.decor.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;


/**
 * Created by gaoll on 2015/3/13.
 */
public interface TResourceService extends ICommonService<TResource> {
    /**
     * 根据父ID获取父菜单下的子菜单
     */
    public List<TResource> findTResourceByPid(Integer pid);
}
