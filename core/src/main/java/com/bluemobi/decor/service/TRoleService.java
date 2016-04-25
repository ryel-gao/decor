package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.TRole;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.service.common.ICommonService;
import org.springframework.data.domain.Page;


/**
 * Created by gaoll on 2015/3/13.
 */
public interface TRoleService extends ICommonService<TRole> {
    public Page<TRole> page(Integer pageNum, Integer pageSize,String name);

    public void saveRole(TRole tRole,String[] resourceIds);
    public void deleteRole(Integer roleId);

    public Boolean isNameExist(TRole tRole);

}
