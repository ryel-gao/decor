package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.CollectionDao;
import com.bluemobi.decor.dao.FavoriteDao;
import com.bluemobi.decor.entity.*;
import com.bluemobi.decor.service.FavoriteService;
import com.bluemobi.decor.service.GoodsService;
import com.bluemobi.decor.service.SceneService;
import com.bluemobi.decor.service.SeriesService;
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
 * Created by tuyh on 2015/7/9.
 */
@Service
@Transactional(readOnly = true)
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteDao favoriteDao;

    @Autowired
    private CollectionDao collectionDao;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SceneService sceneService;

    @Autowired
    private SeriesService seriesService;

    @Override
    public List<Favorite> findAll() {
        return favoriteDao.findAll();
    }

    @Override
    public Page<Favorite> find(int pageNum, int pageSize) {

        return favoriteDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

    }

    @Override
    public Page<Favorite> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Favorite getById(int id) {
        return favoriteDao.findOne(id);
    }

    @Override
    @Transactional
    public Favorite deleteById(int id) {
        Favorite favorite = getById(id);
        favoriteDao.delete(favorite);
        return favorite;
    }

    @Override
    @Transactional
    public Favorite create(Favorite favorite) {
        if(favorite.getInfo()==null){
            favorite.setInfo("");
        }
        if(favorite.getCover()==null){
            favorite.setCover("");
        }
        favoriteDao.save(favorite);
        return favorite;
    }

    @Override
    @Transactional
    public Favorite update(Favorite favorite) {
        return favoriteDao.save(favorite);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<Favorite> iPageForFavorite(int pageNum, int pageSize, final Integer userId) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<Favorite> page = favoriteDao.findAll(new Specification<Favorite>() {
            @Override
            public Predicate toPredicate(Root<Favorite> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (null != userId){
                    Predicate predicate = cb.equal(root.get("user").get("id").as(Integer.class), userId);
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

        },pageRequest);

        return page;
    }

    @Override
    public List<Favorite> iFindAllCollects(User user) {
        return favoriteDao.iFindAllCollects(user);
    }

    @Override
    @Transactional
    public void delFavorite(Integer favoriteId) {
        collectionDao.delByFavoriteId(favoriteId);
        deleteById(favoriteId);
    }

    @Override
    public List<Favorite> listByUserId(Integer userId) {
        return favoriteDao.listByUserId(userId);
    }

    @Override
    public String cover(Integer favoriteId) {
        String cover = "";
        Collection collection = firstCollection(favoriteId);
        if (collection == null) {
            return cover;
        }

        if (Constant.COLLECTION_TYPE_GOODS.equals(collection.getObjectType())) {
            Goods goods = goodsService.getById(collection.getObjectId());
            if (goods != null) {
                cover = goods.getCover();
            } else {
                collectionDao.delete(collection);
            }
        } else if (collection.getObjectType().equals(Constant.COLLECTION_TYPE_SCENE)) {
            Scene scene = sceneService.getById(collection.getObjectId());
            if (scene != null) {
                cover = scene.getImage();
            } else {
                collectionDao.delete(collection);
            }
        } else if (collection.getObjectType().equals(Constant.COLLECTION_TYPE_SERIES)) {
            Series series = seriesService.getById(collection.getObjectId());
            if (series != null) {
                cover = series.getCover();
            } else {
                collectionDao.delete(collection);
            }
        }
        return cover;
    }

    @Override
    public Page<Favorite> pageFavorite(final Integer userId, int pageSize, int pageNum) {
        Page<Favorite> page = favoriteDao.findAll(new Specification<Favorite>() {
            @Override
            public Predicate toPredicate(Root<Favorite> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (userId != null){
                    Predicate predicate = cb.equal(root.get("user").get("id").as(Integer.class), userId);
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

        },new PageRequest(pageSize - 1, pageNum, Sort.Direction.ASC, "id"));
       return page;
    }

    // 获取收藏夹的第一个收藏
    public Collection firstCollection(final Integer favoriteId) {
        Page<Collection> page = collectionDao.findAll(new Specification<Collection>() {
            @Override
            public Predicate toPredicate(Root<Collection> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (favoriteId != null){
                    Predicate predicate = cb.equal(root.get("favorite").get("id").as(Integer.class), favoriteId);
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

        },new PageRequest(1 - 1, 1, Sort.Direction.ASC, "id"));
        if(page.getContent() != null && page.getContent().size() > 0){
            return page.getContent().get(0);
        }else {
            return null;
        }
    }
}