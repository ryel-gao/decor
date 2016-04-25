package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.TRoleDao;
import com.bluemobi.decor.entity.TRole;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.service.TRoleResourceService;
import com.bluemobi.decor.utils.ClassUtil;
import com.bluemobi.decor.service.TRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoll on 2015/7/16.
 */
@Service
@Transactional(readOnly = true)
public class TRoleServiceImpl implements TRoleService {

    @Autowired
    private TRoleDao tRoleDao;

    @Autowired
    private TRoleResourceService tRoleResourceService;


    @Override
    public List<TRole> findAll() {
        return tRoleDao.findAll();
    }

    @Override
    public Page<TRole> find(int pageNum, int pageSize) {

        return tRoleDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

    }

    @Override
    public Page<TRole> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public TRole getById(int id) {
        return tRoleDao.findOne(id);
    }

    @Override
    @Transactional
    public TRole deleteById(int id) {
        TRole tRole = getById(id);
        tRoleDao.delete(tRole);
        return tRole;
    }

    @Override
    @Transactional
    public TRole create(TRole tRole) {
        tRoleDao.save(tRole);
        return tRole;
    }

    @Override
    @Transactional
    public TRole update(TRole o) {
        TRole dest = getById(o.getId());
        ClassUtil.copyProperties(dest, o);
        return tRoleDao.save(dest);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<TRole> page(Integer pageNum, Integer pageSize, final String name) {
        Page<TRole> page = tRoleDao.findAll(new Specification<TRole>() {
            @Override
            public Predicate toPredicate(Root<TRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if(StringUtils.isNotEmpty(name)){
                    Predicate predicate = cb.like(root.get("name").as(String.class), "%" + name + "%");
                    predicateList.add(predicate);
                }

                if (predicateList.size() > 0) {
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }

                if (result != null) {
                    query.where(result);
                }

                return query.getRestriction();
            }

        }, new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

        return page;
    }

    @Override
    @Transactional
    public void saveRole(TRole tRole, String[] resourceIds) {
        if(tRole.getId()==null){
            tRole = create(tRole);
        }else {
            tRole = update(tRole);
        }
        tRoleResourceService.saveResource(tRole.getId(),resourceIds);
    }

    @Override
    @Transactional
    public void deleteRole(Integer roleId) {
        tRoleResourceService.clearResource(roleId);
        deleteById(roleId);
    }

    @Override
    public Boolean isNameExist(TRole tRole) {
        List<TRole> list = null;
        if(tRole.getId()==null){
            list = tRoleDao.get(tRole.getName());
        }else {
            list = tRoleDao.getNotId(tRole.getId(), tRole.getName());
        }
        if (list!=null&&list.size()>0){
            return true;
        }else {
            return false;
        }
    }
}