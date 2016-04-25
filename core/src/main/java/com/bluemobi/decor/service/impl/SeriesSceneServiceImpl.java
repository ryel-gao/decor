package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.SeriesSceneDao;
import com.bluemobi.decor.entity.*;
import com.bluemobi.decor.utils.ClassUtil;
import com.bluemobi.decor.service.SeriesSceneService;
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
 * Created by gaoll on 2015/7/9.
 */
@Service
@Transactional(readOnly = true)
public class SeriesSceneServiceImpl implements SeriesSceneService {

    @Autowired
    private SeriesSceneDao seriesSceneDao;

    @Override
    public List<SeriesScene> findAll() {
        return seriesSceneDao.findAll();
    }

    @Override
    public Page<SeriesScene> find(int pageNum, int pageSize) {
        return seriesSceneDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<SeriesScene> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public SeriesScene getById(int id) {
        return seriesSceneDao.findOne(id);
    }

    @Override
    @Transactional
    public SeriesScene deleteById(int id) {
        SeriesScene seriesScene = getById(id);
        seriesSceneDao.delete(seriesScene);
        return seriesScene;
    }

    @Override
    @Transactional
    public SeriesScene create(SeriesScene seriesScene) {
        seriesSceneDao.save(seriesScene);
        return seriesScene;
    }

    @Override
    @Transactional
    public SeriesScene update(SeriesScene o) {
        SeriesScene dest = getById(o.getId());
        ClassUtil.copyProperties(dest, o);
        return seriesSceneDao.save(dest);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<SeriesScene> iFindBySeries(Series series) {
        return seriesSceneDao.iFindBySeries(series);
    }

    @Override
    public List<SeriesScene> pcFindBySeries(Series series) {
        return seriesSceneDao.pcFindBySeries(series);
    }

    @Override
    public List<Scene> findSceneListBySeriesId(Integer seriesId) {
        return seriesSceneDao.findSceneListBySeriesId(seriesId);
    }

    // 查询场景所属系列图，最多显示6条
    @Override
    public List<Series> findSeriesListBySceneId(final Integer sceneId) {
        Page<SeriesScene> page = seriesSceneDao.findAll(new Specification<SeriesScene>() {
            @Override
            public Predicate toPredicate(Root<SeriesScene> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (sceneId != null) {
                    Predicate predicate = cb.equal(root.get("scene").get("id").as(Integer.class), sceneId);
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

        }, new PageRequest(1 - 1, 6, Sort.Direction.DESC, "id"));

        List<SeriesScene> list = page.getContent();
        List<Series> seriesList = new ArrayList<Series>();
        for (int i = 0; i < list.size(); i++) {
            seriesList.add(list.get(i).getSeries());
        }
        return seriesList;
    }
}