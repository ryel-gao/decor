package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.MessageTagDao;
import com.bluemobi.decor.entity.*;
import com.bluemobi.decor.service.MessageTagService;
import com.bluemobi.decor.utils.ClassUtil;
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
 * Created by tuyh on 2015/7/8.
 */
@Service
@Transactional(readOnly = true)
public class MessageTagServiceImpl implements MessageTagService {

    @Autowired
    private MessageTagDao messageTagDao;


    @Override
    public List<MessageTag> findAll() {
        return messageTagDao.findAll(new PageRequest(0, 100, Sort.Direction.DESC, "createDate")).getContent();
    }

    @Override
    public Page<MessageTag> find(int pageNum, int pageSize) {

        return messageTagDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

    }

    @Override
    public Page<MessageTag> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public MessageTag getById(int id) {
        return messageTagDao.findOne(id);
    }

    @Override
    @Transactional
    public MessageTag deleteById(int id) {
        MessageTag messageTag = getById(id);
        messageTagDao.delete(messageTag);
        return messageTag;
    }

    @Override
    @Transactional
    public MessageTag create(MessageTag messageTag) {
        if(messageTag.getNum()==null){
            messageTag.setNum(0);
        }
        messageTagDao.save(messageTag);
        return messageTag;
    }

    @Override
    @Transactional
    public MessageTag update(MessageTag o) {
        MessageTag dest = getById(o.getId());
        ClassUtil.copyProperties(dest, o);
        return messageTagDao.save(dest);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<MessageTag> iFindMessageSortPage(Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<MessageTag> page = messageTagDao.findAll(new Specification<MessageTag>() {
            @Override
            public Predicate toPredicate(Root<MessageTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if(predicateList.size()>0){
                    result = cb.and(predicateList.toArray(new Predicate[]{}));
                }

                if (result != null) {
                    query.where(result);
                }

                return query.getRestriction();
            }

        },pageRequest);

        return page;
    }

    @Override
    public Page<MessageTag> backendFindByCondition(int pageNum, int pageSize, final String id, final String name, final String num) {
        Page<MessageTag> page = messageTagDao.findAll(new Specification<MessageTag>() {
            @Override
            public Predicate toPredicate(Root<MessageTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (StringUtils.isNotEmpty(id)) {
                    Predicate predicate = cb.greaterThanOrEqualTo(root.get("id").as(String.class), id);
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

        }, new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "createDate"));

        return page;
    }
}