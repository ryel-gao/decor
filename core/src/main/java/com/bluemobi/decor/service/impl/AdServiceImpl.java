package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.AdDao;
import com.bluemobi.decor.entity.Ad;
import com.bluemobi.decor.entity.KindTag;
import com.bluemobi.decor.utils.ClassUtil;
import com.bluemobi.decor.service.AdService;
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
 * Created by gaoll on 2015/9/23.
 */
@Service
@Transactional(readOnly = true)
public class AdServiceImpl implements AdService {

    @Autowired
    private AdDao adDao;


    @Override
    public List<Ad> findAll() {
        return adDao.findAll();
    }

    @Override
    public Page<Ad> find(int pageNum, int pageSize) {

        return adDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

    }

    @Override
    public Page<Ad> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Ad getById(int id) {
        return adDao.findOne(id);
    }

    @Override
    @Transactional
    public Ad deleteById(int id) {
        Ad ad = getById(id);
        adDao.delete(ad);
        return ad;
    }

    @Override
    @Transactional
    public Ad create(Ad ad) {
        adDao.save(ad);
        return ad;
    }

    @Override
    @Transactional
    public Ad update(Ad o) {
        Ad dest = getById(o.getId());
        ClassUtil.copyProperties(dest, o);
        return adDao.save(dest);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Ad> backendPage(int pageNum, int pageSize) {
        Page<Ad> page = adDao.findAll(new Specification<Ad>() {
            @Override
            public Predicate toPredicate(Root<Ad> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (predicateList.size() > 0) {
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }
                if (result != null) {
                    query.where(result);
                }

                return query.getRestriction();
            }

        }, new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "rank"));

        return page;
    }

    @Override
    public List<Ad> pcList() {
        return adDao.pcList();
    }

}