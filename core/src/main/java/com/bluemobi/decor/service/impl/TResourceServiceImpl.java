package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.TResourceDao;
import com.bluemobi.decor.entity.TResource;
import com.bluemobi.decor.utils.ClassUtil;
import com.bluemobi.decor.service.TResourceService;
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
public class TResourceServiceImpl implements TResourceService {

    @Autowired
    private TResourceDao tResourceDao;


    @Override
    public List<TResource> findAll() {
        return tResourceDao.findAll(new Sort(Sort.Direction.ASC, "id"));
    }

    @Override
    public Page<TResource> find(int pageNum, int pageSize) {

        return tResourceDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

    }

    @Override
    public Page<TResource> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public TResource getById(int id) {
        return tResourceDao.findOne(id);
    }

    @Override
    @Transactional
    public TResource deleteById(int id) {
        TResource tResource = getById(id);
        tResourceDao.delete(tResource);
        return tResource;
    }

    @Override
    @Transactional
    public TResource create(TResource tResource) {
        tResourceDao.save(tResource);
        return tResource;
    }

    @Override
    @Transactional
    public TResource update(TResource o) {
        TResource dest = getById(o.getId());
        ClassUtil.copyProperties(dest, o);
        return tResourceDao.save(dest);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<TResource> findTResourceByPid(Integer pid) {
        return tResourceDao.findTResourceByPid(pid);
    }
}