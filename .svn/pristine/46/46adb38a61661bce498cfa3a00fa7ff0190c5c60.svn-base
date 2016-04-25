package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.AttentionDao;
import com.bluemobi.decor.entity.Attention;
import com.bluemobi.decor.entity.KindTag;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.service.AttentionService;
import com.bluemobi.decor.service.UserService;
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
 * Created by tuyh on 2015/7/10.
 */
@Service
@Transactional(readOnly = true)
public class AttentionServiceImpl implements AttentionService {

    @Autowired
    private AttentionDao attentionDao;

    @Autowired
    private UserService userService;

    @Override
    public List<Attention> findAll() {
        return attentionDao.findAll();
    }

    @Override
    public Page<Attention> find(int pageNum, int pageSize) {
        return attentionDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Attention> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Attention getById(int id) {
        return attentionDao.findOne(id);
    }

    @Override
    @Transactional
    public Attention deleteById(int id) {
        Attention attention = getById(id);
        attentionDao.delete(attention);
        return attention;
    }

    @Override
    @Transactional
    public Attention create(Attention attention) {
        if (attention.getUserHasUpdate() == null) {
            attention.setUserHasUpdate("0");
        }
        attentionDao.save(attention);
        return attention;
    }

    @Override
    @Transactional
    public Attention update(Attention attention) {
        if (attention.getUserHasUpdate() == null) {
            attention.setUserHasUpdate("0");
        }
        return attentionDao.save(attention);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Attention> iFindFansPage(final User user, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Attention> page = attentionDao.findAll(new Specification<Attention>() {
            @Override
            public Predicate toPredicate(Root<Attention> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (null != user) {
                    Predicate predicate = cb.equal(root.get("attentionUser").get("id").as(Integer.class), user.getId());
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
    public Page<Attention> iFindAttentionPage(final User user, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Attention> page = attentionDao.findAll(new Specification<Attention>() {
            @Override
            public Predicate toPredicate(Root<Attention> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (null != user) {
                    Predicate predicate = cb.equal(root.get("fansUser").get("id").as(Integer.class), user.getId());
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
    public Attention iCheckUser(User user, User user2) {
        List<Attention> attentionList = attentionDao.iCheckUser(user, user2);
        if(attentionList != null && attentionList.size() > 0){
            return attentionList.get(0);
        }
        return null;
    }

    @Override
    @Transactional
    public void userHasUpdate(Integer userId) {
        attentionDao.userHasUpdate(userId);
    }

    @Override
    @Transactional
    public void clearUserUpdate(Integer attentionUserId, Integer userId) {
        attentionDao.clearUserUpdate(attentionUserId, userId);
    }

    @Override
    public List findFansNum(User user) {
        Integer fans = attentionDao.findFansList(user).size();
        Integer attentionNum = attentionDao.findAttentionList(user).size();
        List<Integer> list = new ArrayList();
        list.add(fans);
        list.add(attentionNum);
        return list;
    }

    /*查询关注分页*/
    @Override
    public Page<Attention> pcFindFansPage(final User user, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");
        Page<Attention> page = attentionDao.findAll(new Specification<Attention>() {
            @Override
            public Predicate toPredicate(Root<Attention> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (null != user) {
                    Predicate predicate = cb.equal(root.get("attentionUser").get("id").as(Integer.class), user.getId());
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
        List<Attention> list = page.getContent();
        for (Attention attention : list) {
            User fansUser = attention.getFansUser();
            List<Integer> numList = findFansNum(fansUser);
            Integer collectNum = numList.get(0);
            Integer attentionNum = numList.get(1);
            if (attentionNum == null) {
                fansUser.setAttention(0);
            } else {
                fansUser.setAttention(attentionNum);
            }
            ;
            if (collectNum == null) {
                fansUser.setCollect(0);
            } else {
                fansUser.setCollect(collectNum);
            }
        }

        return page;
    }

    @Override
    public Boolean isAttention(Integer userId, Integer fansId) {
        Integer num = attentionDao.isAttention(userId,fansId);
        if(num > 0){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void pcAttentionBusiness(Integer userId, Integer fansId) {
        if(userId != null && fansId != null){
            Integer num = attentionDao.isAttention(userId,fansId);
            if(num <= 0){
                User user = new User();
                user.setId(userId);
                User fans = new User();
                fans.setId(fansId);
                Attention attention = new Attention();
                attention.setAttentionUser(user);
                attention.setFansUser(fans);
                create(attention);

                userService.fansNumAddOne(userId);
            }
        }
    }

    @Override
    @Transactional
    public void pcCancelAttentionBusiness(Integer userId, Integer fansId) {
        if(userId != null && fansId != null){
            List<Attention> attentionList = attentionDao.listByUserIdAndFansId(userId,fansId);
            for (int i = 0; i < attentionList.size(); i++) {
                attentionDao.delete(attentionList.get(i));
            }
            if(attentionList != null && attentionList.size() > 0){
                userService.fansNumCutOne(userId);
            }
        }
    }

    @Override
    public Page<Attention> pcFindAttentionPage(final User user, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Attention> page = attentionDao.findAll(new Specification<Attention>() {
            @Override
            public Predicate toPredicate(Root<Attention> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (null != user) {
                    Predicate predicate = cb.equal(root.get("fansUser").get("id").as(Integer.class), user.getId());
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
        List<Attention> list = page.getContent();
        for (Attention attention : list) {
            User attentionUser = attention.getAttentionUser();
            List<Integer> numList = findFansNum(attentionUser);
            Integer collectNum = numList.get(0);
            Integer attentionNum = numList.get(1);
            if (attentionNum == null) {
                attentionUser.setAttention(0);
            } else {
                attentionUser.setAttention(attentionNum);
            }
            ;
            if (collectNum == null) {
                attentionUser.setCollect(0);
            } else {
                attentionUser.setCollect(collectNum);
            }
        }

        return page;
    }


}