package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.TUserRoleDao;
import com.bluemobi.decor.entity.TUserRole;
import com.bluemobi.decor.utils.ClassUtil;
import com.bluemobi.decor.service.TUserRoleService;
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
public class TUserRoleServiceImpl implements TUserRoleService {

    @Autowired
    private TUserRoleDao tUserRoleDao;


    @Override
    public List<TUserRole> findAll() {
        return tUserRoleDao.findAll();
    }

    @Override
    public Page<TUserRole> find(int pageNum, int pageSize) {

        return tUserRoleDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

    }

    @Override
    public Page<TUserRole> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public TUserRole getById(int id) {
        return tUserRoleDao.findOne(id);
    }

    @Override
    @Transactional
    public TUserRole deleteById(int id) {
        TUserRole tUserRole = getById(id);
        tUserRoleDao.delete(tUserRole);
        return tUserRole;
    }

    @Override
    @Transactional
    public TUserRole create(TUserRole tUserRole) {
        tUserRoleDao.save(tUserRole);
        return tUserRole;
    }

    @Override
    @Transactional
    public TUserRole update(TUserRole o) {
        TUserRole dest = getById(o.getId());
        ClassUtil.copyProperties(dest, o);
        return tUserRoleDao.save(dest);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<TUserRole> list(Integer roleId) {
        return tUserRoleDao.list(roleId);
    }

    @Override
    public TUserRole findByUserId(Integer userId) {
        List<TUserRole> list = tUserRoleDao.findByUserId(userId);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public TUserRole findMaster() {
        return tUserRoleDao.findMaster();
    }
}