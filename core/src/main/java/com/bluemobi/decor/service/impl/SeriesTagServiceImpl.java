package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.SeriesTagDao;
import com.bluemobi.decor.entity.KindTag;
import com.bluemobi.decor.entity.MessageTag;
import com.bluemobi.decor.entity.SeriesTag;
import com.bluemobi.decor.service.SeriesTagService;
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
import java.util.Date;
import java.util.List;

/**
 * Created by tuyh on 2015/7/8.
 */
@Service
@Transactional(readOnly = true)
public class SeriesTagServiceImpl implements SeriesTagService {

    @Autowired
    private SeriesTagDao seriesTagDao;


    @Override
    public List<SeriesTag> findAll() {
        return seriesTagDao.findAll(new PageRequest(0, 100, Sort.Direction.DESC, "createDate")).getContent();
    }

    @Override
    public Page<SeriesTag> find(int pageNum, int pageSize) {
        return seriesTagDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "createDate"));
    }

    @Override
    public Page<SeriesTag> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public SeriesTag getById(int id) {
        return seriesTagDao.findOne(id);
    }

    @Override
    @Transactional
    public SeriesTag deleteById(int id) {
        SeriesTag seriesTag = getById(id);
        seriesTagDao.delete(seriesTag);
        return seriesTag;
    }

    @Override
    @Transactional
    public SeriesTag create(SeriesTag seriesTag) {
        if(seriesTag.getNum()==null){
            seriesTag.setNum(0);
        }
//        seriesTag.setCreateDate(new Date());
        seriesTagDao.save(seriesTag);
        return seriesTag;
    }

    @Override
    @Transactional
    public SeriesTag update(SeriesTag o) {
//        o.setCreateDate(new Date());
        SeriesTag dest = getById(o.getId());
        ClassUtil.copyProperties(dest, o);
        return seriesTagDao.save(dest);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<SeriesTag> iFindSeriesSortPage(Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "createDate");

        Page<SeriesTag> page = seriesTagDao.findAll(new Specification<SeriesTag>() {
            @Override
            public Predicate toPredicate(Root<SeriesTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
    public Page<SeriesTag> backendFindByCondition(int pageNum,int pageSize, final String id, final String name, final String num) {

        Page<SeriesTag> page = seriesTagDao.findAll(new Specification<SeriesTag>() {
            @Override
            public Predicate toPredicate(Root<SeriesTag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

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

    @Override
    public List<SeriesTag> all() {
        return seriesTagDao.all();
    }


}