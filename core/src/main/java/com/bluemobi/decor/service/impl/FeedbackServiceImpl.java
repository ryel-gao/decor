package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.FeedbackDao;
import com.bluemobi.decor.entity.Feedback;
import com.bluemobi.decor.service.FeedbackService;
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
        * Created by liuhm on 2015/7/3.
        */
@Service
@Transactional(readOnly = true)
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;


    @Override
    public List<Feedback> findAll() {
        return feedbackDao.findAll();
    }

    @Override
    public Page<Feedback> find(int pageNum, int pageSize) {

        return feedbackDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

    }

    @Override
    public Page<Feedback> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Feedback getById(int id) {
        return feedbackDao.findOne(id);
    }

    @Override
    @Transactional
    public Feedback deleteById(int id) {
        Feedback feedback = getById(id);
        feedbackDao.delete(feedback);
        return feedback;
    }

    @Override
    @Transactional
    public Feedback create(Feedback feedback) {
        feedbackDao.save(feedback);
        return feedback;
    }

    @Override
    @Transactional
    public Feedback update(Feedback feedback) {
        return feedbackDao.save(feedback);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    //意见反馈列表
    @Override
    public Page<Feedback> backendFindByCondition(int pageNum, int pageSize, final String id, final String username) {
        Page<Feedback> page = feedbackDao.findAll(new Specification<Feedback>() {
            @Override
            public Predicate toPredicate(Root<Feedback> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (StringUtils.isNotEmpty(id)) {
                    Predicate predicate = cb.like(root.get("id").as(String.class), "%" + id + "%");
                    predicateList.add(predicate);
                }
                if (StringUtils.isNotEmpty(username)) {
                    Predicate predicate = cb.like(root.get("user").get("account").as(String.class), "%"+username+"%");
                    predicateList.add(predicate);
                }

                if(predicateList.size()>0){
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

}