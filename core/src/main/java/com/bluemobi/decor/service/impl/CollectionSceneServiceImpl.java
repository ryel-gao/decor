package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.CollectionSceneDao;
import com.bluemobi.decor.entity.Collection;
import com.bluemobi.decor.entity.CollectionScene;
import com.bluemobi.decor.entity.Scene;
import com.bluemobi.decor.utils.ClassUtil;
import com.bluemobi.decor.service.CollectionSceneService;
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
 * Created by gaoll on 2015/8/10.
 */
@Service
@Transactional(readOnly = true)
public class CollectionSceneServiceImpl implements CollectionSceneService {

    @Autowired
    private CollectionSceneDao collectionSceneDao;

    @Override
    public Page<CollectionScene> pageCollectionScene(Integer pageNum, Integer pageSize,final Integer userId,final String spaceTag,
                                                     final String name) {
        Page<CollectionScene> page = collectionSceneDao.findAll(new Specification<CollectionScene>() {
            @Override
            public Predicate toPredicate(Root<CollectionScene> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (userId != null) {
                    Predicate predicate = cb.equal(root.get("favorite").get("user").get("id").as(Integer.class), userId);
                    predicateList.add(predicate);
                }

                if (userId != null) {
                    Predicate predicate = cb.notEqual(root.get("scene").get("user").get("id").as(Integer.class), userId);
                    predicateList.add(predicate);
                }

                if (StringUtils.isNotEmpty(spaceTag)) {
                    Predicate predicate = cb.like(root.get("scene").get("spaceTagIds").as(String.class), "%@" + spaceTag+"@%");
                    predicateList.add(predicate);
                }

                if (StringUtils.isNotEmpty(name)) {
                    Predicate predicate = cb.like(root.get("scene").get("name").as(String.class), "%" + name+"%");
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
}