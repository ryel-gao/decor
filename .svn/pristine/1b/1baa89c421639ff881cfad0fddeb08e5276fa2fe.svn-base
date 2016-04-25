package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.TRoleResourceDao;
import com.bluemobi.decor.entity.TResource;
import com.bluemobi.decor.entity.TRole;
import com.bluemobi.decor.entity.TRoleResource;
import com.bluemobi.decor.utils.ClassUtil;
import com.bluemobi.decor.service.TRoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gaoll on 2015/7/16.
 */
@Service
@Transactional(readOnly = true)
public class TRoleResourceServiceImpl implements TRoleResourceService {

    @Autowired
    private TRoleResourceDao tRoleResourceDao;


    @Override
    public List<TRoleResource> findAll() {
        return tRoleResourceDao.findAll();
    }

    @Override
    public Page<TRoleResource> find(int pageNum, int pageSize) {

        return tRoleResourceDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

    }

    @Override
    public Page<TRoleResource> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public TRoleResource getById(int id) {
        return tRoleResourceDao.findOne(id);
    }

    @Override
    @Transactional
    public TRoleResource deleteById(int id) {
        TRoleResource tRoleResource = getById(id);
        tRoleResourceDao.delete(tRoleResource);
        return tRoleResource;
    }

    @Override
    @Transactional
    public TRoleResource create(TRoleResource tRoleResource) {
        tRoleResourceDao.save(tRoleResource);
        return tRoleResource;
    }

    @Override
    @Transactional
    public TRoleResource update(TRoleResource o) {
        TRoleResource dest = getById(o.getId());
        ClassUtil.copyProperties(dest, o);
        return tRoleResourceDao.save(dest);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void saveResource(Integer roleId, String[] resourceIds) {
        clearResource(roleId);
        for (int i = 0; i < resourceIds.length; i++) {
            TRoleResource t = new TRoleResource();
            TRole role = new TRole();
            role.setId(roleId);
            TResource resource = new TResource();
            resource.setId(Integer.parseInt(resourceIds[i]));
            t.setRole(role);
            t.setResource(resource);
            create(t);
        }
    }

    @Override
    public void clearResource(Integer roleId) {
        tRoleResourceDao.clearResource(roleId);
    }

    @Override
    public List<TResource> resourceList(Integer roleId) {
        return tRoleResourceDao.resourceList(roleId);
    }

    @Override
    public List<TResource> oneLevelResourceList(Integer roleId) {
        return tRoleResourceDao.resourceList(roleId);
    }

    @Override
    public List<TResource> findTResourceByUrl(Integer pid, String url) {
        return tRoleResourceDao.findTResourceByUrl(pid,url);
    }
}