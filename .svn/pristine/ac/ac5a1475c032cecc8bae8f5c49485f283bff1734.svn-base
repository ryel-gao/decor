package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.DrawBoardDao;
import com.bluemobi.decor.dao.SceneDao;
import com.bluemobi.decor.entity.DrawBoard;
import com.bluemobi.decor.entity.Material;
import com.bluemobi.decor.entity.Scene;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.service.DrawBoardService;
import com.bluemobi.decor.service.MaterialService;
import com.bluemobi.decor.service.SceneService;
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
import java.util.*;

/**
 * Created by tuyh on 2015/7/13.
 */
@Service
@Transactional(readOnly = true)
public class DrawBoardServiceImpl implements DrawBoardService {

    private static Integer DATA_NUM = 6;

    @Autowired
    private DrawBoardDao drawBoardDao;

    @Autowired
    private SceneService sceneService;

    @Autowired
    private MaterialService materialService;

    @Override
    public List<DrawBoard> findAll() {
        return drawBoardDao.findAll();
    }

    @Override
    public Page<DrawBoard> find(int pageNum, int pageSize) {
        return drawBoardDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id"));
    }

    @Override
    public Page<DrawBoard> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public DrawBoard getById(int id) {
        return drawBoardDao.findOne(id);
    }

    @Override
    @Transactional
    public DrawBoard deleteById(int id) {
        DrawBoard drawBoard = getById(id);
        drawBoardDao.delete(drawBoard);
        return drawBoard;
    }

    @Override
    @Transactional
    public DrawBoard create(DrawBoard drawBoard) {
        drawBoard.setCreateTime(new Date());
        drawBoardDao.save(drawBoard);
        return drawBoard;
    }

    @Override
    @Transactional
    public DrawBoard update(DrawBoard drawBoard) {
        return drawBoardDao.save(drawBoard);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Page<DrawBoard> iPageWithUser(final Integer userId, Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.ASC, "id");

        Page<DrawBoard> page = drawBoardDao.findAll(new Specification<DrawBoard>() {
            @Override
            public Predicate toPredicate(Root<DrawBoard> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (null != userId) {
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
    public Page<DrawBoard> findDrawBoards(final Integer id,final String userName,Integer pageNum, Integer pageSize) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id");

        Page<DrawBoard> page = drawBoardDao.findAll(new Specification<DrawBoard>() {
            @Override
            public Predicate toPredicate(Root<DrawBoard> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                Predicate result = null;

                if (null != id) {
                    Predicate predicate = cb.equal(root.get("id").as(Integer.class), id);
                    predicateList.add(predicate);
                }

                if (StringUtils.isNotEmpty(userName)) {
                    Predicate predicate = cb.like(root.get("user").get("nickname").as(String.class), "%" + userName + "%");
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
    @Transactional
    public void save(Integer userId, String image) {
        DrawBoard drawBoard = new DrawBoard();
        User user = new User();
        user.setId(userId);
        drawBoard.setUser(user);
        drawBoard.setImage(image);
        create(drawBoard);
    }

    @Override
    public Map<String, Object> iImageData(final Integer userId) {
        Map<String,Object> map = new HashMap<String, Object>();
        // 获取6个场景图
        List<Scene> sceneList = new ArrayList<Scene>();
        // 获取我的场景图
        {
            List<Scene> list1 = sceneService.listMy(userId,DATA_NUM);
            if(list1 != null && list1.size()>0){
                sceneList = new ArrayList<Scene>();
                sceneList.addAll(list1);
            }
        }
        // 不足6条，则从全部场景图中继续获取
        if(sceneList.size() < DATA_NUM){
            Integer number = DATA_NUM - sceneList.size();
            List<Scene> list = sceneService.list(number);
            sceneList.addAll(list);
        }

        // 获取6个素材图
        List<Material> materialList = new ArrayList<Material>();
        // 获取我的素材图
        {
            List<Material> list = materialService.listMy(userId,DATA_NUM);
            if(materialList != null && list.size()>0){
                materialList.addAll(list);
            }
        }
        // 不足6条，则从我收藏的素材图中继续获取
        if(materialList.size() < DATA_NUM){
            Integer number = DATA_NUM - materialList.size();
            List<Material> list = materialService.listMyCollection(userId, number);
            materialList.addAll(list);
        }
        // 不足6条，则从全部素材图中继续获取
        if(materialList.size() < DATA_NUM){
            Integer number = DATA_NUM - materialList.size();
            List<Material> list = materialService.list(number);
            materialList.addAll(list);
        }

        map.put("sceneList",sceneList);
        map.put("materialList",materialList);

        return map;
    }
}