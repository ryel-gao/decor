package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.PraiseDao;
import com.bluemobi.decor.entity.*;
import com.bluemobi.decor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tuyh on 2015/7/9.
 */
@Service
@Transactional(readOnly = true)
public class PraiseServiceImpl implements PraiseService {

    @Autowired
    private PraiseDao praiseDao;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SceneService sceneService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;


    @Override
    public List<Praise> findAll() {
        return praiseDao.findAll();
    }

    @Override
    public Page<Praise> find(int pageNum, int pageSize) {

        return praiseDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

    }

    @Override
    public Page<Praise> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Praise getById(int id) {
        return praiseDao.findOne(id);
    }

    @Override
    @Transactional
    public Praise deleteById(int id) {
        Praise praise = getById(id);

        Integer objectId = praise.getObjectId();
        String objectType = praise.getObjectType();
        if("goods".equals(objectType)){
            Goods goods = goodsService.getById(objectId);
            Integer num = goods.getPraiseNum();
            if(num == null){
                num = 0;
            }
            num = num - 1;
            if(num < 0){
                num = 0;
            }
            goods.setPraiseNum(num);
            goodsService.update(goods);
        }else if("scene".equals(objectType)){
            Scene scene = sceneService.getById(objectId);
            Integer num = scene.getPraiseNum();
            if(num == null){
                num = 0;
            }
            num = num - 1;
            if(num < 0){
                num = 0;
            }
            scene.setPraiseNum(num);
            sceneService.update(scene);
        }else if("series".equals(objectType)){
            Series series = seriesService.getById(objectId);
            Integer num = series.getPraiseNum();
            if(num == null){
                num = 0;
            }
            num = num - 1;
            if(num < 0){
                num = 0;
            }
            series.setPraiseNum(num);
            seriesService.update(series);
        }else if("user".equals(objectType)){

        }else if("comment".equals(objectType)){
            Comment comment = commentService.getById(objectId);
            Integer num = comment.getPraiseNum();
            if(num == null){
                num = 0;
            }
            num = num - 1;
            if(num < 0){
                num = 0;
            }
            comment.setPraiseNum(num);
            commentService.update(comment);
        }

        praiseDao.delete(praise);
        return praise;
    }

    @Override
    @Transactional
    public Praise create(Praise praise) {
        List<Praise> temp = praiseDao.getByUserIdAndObjectIdAndObjectType(praise.getUser().getId(), praise.getObjectId(), praise.getObjectType());
        if(temp != null && temp.size() > 0){
            return praise;
        }
        praiseDao.save(praise);

        Integer objectId = praise.getObjectId();
        String objectType = praise.getObjectType();
        if("goods".equals(objectType)){
            Goods goods = goodsService.getById(objectId);
            Integer num = goods.getPraiseNum();
            if(num == null){
                num = 0;
            }
            goods.setPraiseNum(num+1);
            goodsService.update(goods);
        }else if("scene".equals(objectType)){
            Scene scene = sceneService.getById(objectId);
            Integer num = scene.getPraiseNum();
            if(num == null){
                num = 0;
            }
            scene.setPraiseNum(num+1);
            sceneService.update(scene);
        }else if("series".equals(objectType)){
            Series series = seriesService.getById(objectId);
            Integer num = series.getPraiseNum();
            if(num == null){
                num = 0;
            }
            series.setPraiseNum(num+1);
            seriesService.update(series);
        }else if("user".equals(objectType)){

        }else if("comment".equals(objectType)){
            Comment comment = commentService.getById(objectId);
            Integer num = comment.getPraiseNum();
            if(num == null){
                num = 0;
            }
            comment.setPraiseNum(num+1);
            commentService.update(comment);
        }

        return praise;
    }

    @Override
    @Transactional
    public Praise update(Praise praise) {
        return praiseDao.save(praise);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Praise iFindByUserAndObjectId(User user, Integer objectId, String objectType) {
        List<Praise> list = praiseDao.iFindByUserAndObjectId(user, objectId, objectType);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public int iGetPraiseNumByUser(User user) {
        List<Praise> list = praiseDao.iGetPraiseNumByUser(user);
        return null == list || list.size() == 0 ? 0 : list.size();
    }

    @Override
    public Boolean isPraise(Integer userId, Integer objectId, String objectType) {
        List<Praise> praiseList = praiseDao.getByUserIdAndObjectIdAndObjectType(userId, objectId, objectType);
        if (praiseList == null || praiseList.size() == 0) {
            return false;
        }
        return true;
    }

    @Override
    public int iGetPraiseNumWithUser(Integer userId) {
        List<Praise> list = praiseDao.iGetPraiseNumWithUser(userId, Constant.PRAISE_TYPE_USER);
        return null == list || list.size() == 0 ? 0 : list.size();
    }

    @Override
    public void deleteByObjectIdAndObjectType(Integer ObjectId, String objectType) {
        praiseDao.deleteByObjectIdAndObjectType(ObjectId, objectType);
    }

    @Override
    @Transactional
    public String cancelPraiseBusiness(Praise praise) {
        List<Praise> praiseList = praiseDao.getByUserIdAndObjectIdAndObjectType(praise.getUser().getId(), praise.getObjectId(), praise.getObjectType());
        for (int i = 0; i < praiseList.size(); i++) {
            deleteById(praiseList.get(i).getId());
        }
        return "success";
    }
}