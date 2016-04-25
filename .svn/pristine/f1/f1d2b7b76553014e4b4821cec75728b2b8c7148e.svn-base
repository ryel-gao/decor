package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.CollectionDao;
import com.bluemobi.decor.entity.*;
import com.bluemobi.decor.service.*;
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
 * Created by gaoll on 2015/7/6.
 */
@Service
@Transactional(readOnly = true)
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionDao collectionDao;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SceneService sceneService;

    @Autowired
    private SeriesService seriesService;


    @Override
    public List<Collection> findAll() {
        return collectionDao.findAll();
    }

    @Override
    public Page<Collection> find(int pageNum, int pageSize) {

        return collectionDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

    }

    @Override
    public Page<Collection> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Collection getById(int id) {
        return collectionDao.findOne(id);
    }

    @Override
    @Transactional
    public Collection deleteById(int id) {
        Collection collection = getById(id);
        collectionDao.delete(collection);
        return collection;
    }

    @Override
    @Transactional
    public Collection create(Collection collection) {
        collectionDao.save(collection);
        return collection;
    }

    @Override
    @Transactional
    public Collection update(Collection o) {
        Collection dest = getById(o.getId());
        ClassUtil.copyProperties(dest, o);
        return collectionDao.save(dest);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    // 是否收藏商品
    @Override
    public Boolean isCollectionGoods(Integer userId, Integer goodsId) {
        return isCollection(userId, goodsId, "goods");
    }

    // 是否收藏场景
    @Override
    public Boolean isCollectionScene(Integer userId, Integer sceneId) {
        return isCollection(userId, sceneId, "scene");
    }

    // 是否收藏系列图
    @Override
    public Boolean isCollectionSeries(Integer userId, Integer seriesId) {
        return isCollection(userId, seriesId, "series");
    }

    @Override
    public Boolean isCollect(Integer userId, Integer objectId, String objectType) {
        List<Collection> collectionList = collectionDao.findByUserIdAndObjectIdAndObjectType(userId, objectId, objectType);
        if(collectionList != null && collectionList.size() > 0){
            return true;
        }
        return false;
    }

    @Override
    public Collection iFindByUserAndObjectId(Favorite favorite, Integer objectId, String objectType) {
        List<Collection> collectionList = collectionDao.iFindByUserAndObjectId(favorite, objectId, objectType);
        if(collectionList != null && collectionList.size() > 0){
            return collectionList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<Collection> iFindCollectsByFavorite(Favorite favorite) {
        return collectionDao.iFindCollectsByFavorite(favorite);
    }

    @Override
    public Page<Collection> iFindCollectsPageByFavorite(final Integer favoriteId, final String objectType, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Collection> page = collectionDao.findAll(new Specification<Collection>() {
            @Override
            public Predicate toPredicate(Root<Collection> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (null != favoriteId) {
                    Predicate predicate = cb.equal(root.get("favorite").get("id").as(Integer.class), favoriteId);
                    predicateList.add(predicate);
                }

                if (null != objectType) {
                    Predicate predicate = cb.equal(root.get("objectType").as(String.class), objectType);
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
    public Page<Collection> iPageCollection(final String objectType, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<Collection> page = collectionDao.findAll(new Specification<Collection>() {
            @Override
            public Predicate toPredicate(Root<Collection> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (null != objectType) {
                    Predicate predicate = cb.equal(root.get("objectType").as(String.class), objectType);
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
    public int iGetCollectionNum(Integer objectId, String objectType) {
        List<Collection> list = collectionDao.iGetCollectionNum(objectId, objectType);
        if (null == list && list.size() == 0) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public List<Collection> iListCollection(final Integer userId, final String objectType) {
        PageRequest pageRequest = new PageRequest(0, 6, Sort.Direction.DESC, "id");

        Page<Collection> page = collectionDao.findAll(new Specification<Collection>() {
            @Override
            public Predicate toPredicate(Root<Collection> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (null != userId) {
                    Predicate predicate = cb.equal(root.get("favorite").get("user").get("id").as(Integer.class), userId);
                    predicateList.add(predicate);
                }

                if (null != objectType) {
                    Predicate predicate = cb.equal(root.get("objectType").as(String.class), objectType);
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

        return page.getContent();
    }

    @Transactional
    @Override
    public void deleteByObjectIdAndObjectType(Integer ObjectId, String objectType) {
        collectionDao.deleteByObjectIdAndObjectType(ObjectId, objectType);
    }

    @Override
    @Transactional
    public String pcCollectBusiness(Integer objectId, String objectType, Integer favoriteId, String favoriteName, String favoriteInfo, String favoriteCover, Integer userId) {
        Favorite f = new Favorite();
        if (StringUtils.isNotEmpty(favoriteName)) {
            User user = new User();
            user.setId(userId);
            f.setUser(user);
            f.setName(favoriteName);
            f.setInfo(favoriteInfo);
            f.setCover(favoriteCover);
            f = favoriteService.create(f);
        } else {
            f.setId(favoriteId);
        }

        List<Collection> collectionList = collectionDao.findByUserIdAndObjectIdAndObjectType(userId, objectId, objectType);
        if (collectionList.size() == 0) {
            Collection c = new Collection();
            c.setFavorite(f);
            c.setObjectId(objectId);
            c.setObjectType(objectType);
            create(c);

            // 收藏成功后，对象的收藏量+1
            if (Constant.COLLECTION_TYPE_GOODS.equals(c.getObjectType())) {
                Goods goods = goodsService.getById(c.getObjectId());
                if (goods.getCollectionNum() == null) {
                    goods.setCollectionNum(0);
                }
                goods.setCollectionNum(goods.getCollectionNum() + 1);
                goodsService.update(goods);
            } else if (Constant.COLLECTION_TYPE_SCENE.equals(c.getObjectType())) {
                Scene scene = sceneService.getById(c.getObjectId());
                if (scene.getCollectionNum() == null) {
                    scene.setCollectionNum(0);
                }
                scene.setCollectionNum(scene.getCollectionNum() + 1);
                sceneService.update(scene);
            } else if (Constant.COLLECTION_TYPE_SERIES.equals(c.getObjectType())) {
                Series series = seriesService.getById(c.getObjectId());
                if (series.getCollectionNum() == null) {
                    series.setCollectionNum(0);
                }
                series.setCollectionNum(series.getCollectionNum() + 1);
                seriesService.update(series);
            }
        } else {
            return "alreadyCollect";
        }

        return "success";
    }

    // 取消收藏
    @Override
    @Transactional
    public void pcCancelCollectBusiness(Integer userId, Integer objectId, String objectType) {
        // 查询用户的收藏夹列表
        List<Favorite> favoriteList = favoriteService.listByUserId(userId);
        for (int i = 0; i < favoriteList.size(); i++) {
            List<Collection> cList = collectionDao.listByFavoriteIdAndObjectIdAndObjectType(favoriteList.get(i).getId(), objectId, objectType);
            for (int j = 0; j < cList.size(); j++) {
                Collection c = cList.get(j);
                collectionDao.delete(c);

                // 对象的收藏量-1
                if (Constant.COLLECTION_TYPE_GOODS.equals(c.getObjectType())) {
                    Goods goods = goodsService.getById(c.getObjectId());
                    Integer cNum = 0;
                    if (goods.getCollectionNum() != null && goods.getCollectionNum() > 1) {
                        cNum = goods.getCollectionNum() - 1;
                    }
                    goods.setCollectionNum(cNum);
                    goodsService.update(goods);
                } else if (Constant.COLLECTION_TYPE_SCENE.equals(c.getObjectType())) {
                    Scene scene = sceneService.getById(c.getObjectId());
                    Integer cNum = 0;
                    if (scene.getCollectionNum() != null && scene.getCollectionNum() > 1) {
                        cNum = scene.getCollectionNum() - 1;
                    }
                    scene.setCollectionNum(cNum);
                    sceneService.update(scene);
                } else if (Constant.COLLECTION_TYPE_SERIES.equals(c.getObjectType())) {
                    Series series = seriesService.getById(c.getObjectId());
                    Integer cNum = 0;
                    if (series.getCollectionNum() != null && series.getCollectionNum() > 1) {
                        cNum = series.getCollectionNum() - 1;
                    }
                    series.setCollectionNum(cNum);
                    seriesService.update(series);
                }
            }
        }
    }

    @Override
    public Page<Collection> pageFavorite(final Integer favoriteId, int pageSize, int pageNum) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");
        Page<Collection> page = collectionDao.findAll(new Specification<Collection>() {
            @Override
            public Predicate toPredicate(Root<Collection> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;
                if (favoriteId != null) {
                    Predicate predicate = cb.equal(root.get("favorite").get("id").as(Integer.class), favoriteId);
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

    // 是否收藏
    public Boolean isCollection(Integer userId, Integer objectId, String objectType) {
        if (userId == null) {
            return false;
        }
        List<Collection> collectionList = collectionDao.findByUserIdAndObjectIdAndObjectType(userId,objectId,objectType);
        if (collectionList == null || collectionList.size() == 0) {
            return false;
        } else {
            return true;
        }
    }
}