package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.CollectionMessageDao;
import com.bluemobi.decor.entity.CollectionMessage;
import com.bluemobi.decor.entity.Message;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.service.CollectionMessageService;
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
 * Created by tuyh on 2015/7/14.
 */
@Service
@Transactional(readOnly = true)
public class CollectionMessageServiceImpl implements CollectionMessageService {

    @Autowired
    private CollectionMessageDao collectionMessageDao;

    @Override
    public List<CollectionMessage> findAll() {
        return collectionMessageDao.findAll();
    }

    @Override
    public Page<CollectionMessage> find(int pageNum, int pageSize) {

        return collectionMessageDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

    }

    @Override
    public Page<CollectionMessage> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public CollectionMessage getById(int id) {
        return collectionMessageDao.findOne(id);
    }

    @Override
    @Transactional
    public CollectionMessage deleteById(int id) {
        CollectionMessage collectionMessage = getById(id);
        collectionMessageDao.delete(collectionMessage);
        return collectionMessage;
    }

    @Override
    @Transactional
    public CollectionMessage create(CollectionMessage collectionMessage) {
        collectionMessageDao.save(collectionMessage);
        return collectionMessage;
    }

    @Override
    @Transactional
    public CollectionMessage update(CollectionMessage collectionMessage) {
        return collectionMessageDao.save(collectionMessage);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public CollectionMessage iFindCollectionWithUser(User user, Message message) {
        List<CollectionMessage> collectionMessageList = collectionMessageDao.iFindCollectionWithUser(user, message);
        if(collectionMessageList != null && collectionMessageList.size() > 0){
            return collectionMessageList.get(0);
        }
        return null;
    }

    @Override
    public List<CollectionMessage> pcFindCollectionWithUser(User user, Message message) {
        return collectionMessageDao.pcFindCollectionWithUser(user, message);
    }

    @Override
    public Page<CollectionMessage> pcPageCollectionWithUser(final Integer userId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<CollectionMessage> page = collectionMessageDao.findAll(new Specification<CollectionMessage>() {
            @Override
            public Predicate toPredicate(Root<CollectionMessage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (null != userId){
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

    @Override
    public Page<CollectionMessage> iPageCollectionWithUser(final Integer userId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<CollectionMessage> page = collectionMessageDao.findAll(new Specification<CollectionMessage>() {
            @Override
            public Predicate toPredicate(Root<CollectionMessage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (null != userId){
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

    @Override
    public List<CollectionMessage> findInfoWithMessage(Message message) {
        return collectionMessageDao.findInfoWithMessage(message);
    }
}