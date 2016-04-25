package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.RemindingDao;
import com.bluemobi.decor.entity.Reminding;
import com.bluemobi.decor.utils.ClassUtil;
import com.bluemobi.decor.service.RemindingService;
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
 * Created by reny on 2015/7/17.
 */
@Service
@Transactional(readOnly = true)
public class RemindingServiceImpl implements RemindingService {

    @Autowired
    private RemindingDao remindingDao;


    @Override
    public List<Reminding> findAll() {
        return remindingDao.findAll();
    }

    @Override
    public Page<Reminding> find(int pageNum, int pageSize) {

        return remindingDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id"));

    }

    @Override
    public Page<Reminding> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Reminding getById(int id) {
        return remindingDao.findOne(id);
    }

    @Override
    @Transactional
    public Reminding deleteById(int id) {
        Reminding reminding = getById(id);
        remindingDao.delete(reminding);
        return reminding;
    }

    @Override
    @Transactional
    public Reminding create(Reminding reminding) {
        remindingDao.save(reminding);
        return reminding;
    }

    @Override
    @Transactional
    public Reminding update(Reminding reminding) {
        return remindingDao.save(reminding);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Reminding> iPage(int pageNum, int pageSize, final Integer userId) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Reminding> page = remindingDao.findAll(new Specification<Reminding>() {
            @Override
            public Predicate toPredicate(Root<Reminding> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (null != userId) {
                    Predicate predicate = cb.equal(root.get("user").get("id").as(Integer.class), userId);
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

        }, pageRequest);

        return page;
    }
}