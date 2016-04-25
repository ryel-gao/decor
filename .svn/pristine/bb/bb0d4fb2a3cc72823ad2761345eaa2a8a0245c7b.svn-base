package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.CollectionMaterialDao;
import com.bluemobi.decor.entity.CollectionMaterial;
import com.bluemobi.decor.entity.Material;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.utils.ClassUtil;
import com.bluemobi.decor.service.CollectionMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gaoll on 2015/7/10.
 */
@Service
@Transactional(readOnly = true)
public class CollectionMaterialServiceImpl implements CollectionMaterialService {

    @Autowired
    private CollectionMaterialDao collectionMaterialDao;


    @Override
    public List<CollectionMaterial> findAll() {
        return collectionMaterialDao.findAll();
    }

    @Override
    public Page<CollectionMaterial> find(int pageNum, int pageSize) {

        return collectionMaterialDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

    }

    @Override
    public Page<CollectionMaterial> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public CollectionMaterial getById(int id) {
        return collectionMaterialDao.findOne(id);
    }

    @Override
    @Transactional
    public CollectionMaterial deleteById(int id) {
        CollectionMaterial collectionMaterial = getById(id);
        collectionMaterialDao.delete(collectionMaterial);
        return collectionMaterial;
    }

    @Override
    @Transactional
    public CollectionMaterial create(CollectionMaterial collectionMaterial) {
        collectionMaterialDao.save(collectionMaterial);
        return collectionMaterial;
    }

    @Override
    @Transactional
    public CollectionMaterial update(CollectionMaterial o) {
        CollectionMaterial dest = getById(o.getId());
        ClassUtil.copyProperties(dest, o);
        return collectionMaterialDao.save(dest);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public Boolean isCollectionMaterial(Integer userId, Integer materialId) {
        List<CollectionMaterial> list = collectionMaterialDao.findByUserIdAndMaterialId(userId,materialId);
        if(list != null && list.size() > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<CollectionMaterial> getInfoByMaterial(Integer materialId) {
        return collectionMaterialDao.getInfoByMaterial(materialId);
    }

    @Override
    @Transactional
    public void collectionMaterial(Integer userId, Integer materialId) {
        if(!isCollectionMaterial(userId,materialId)){
            CollectionMaterial cm = new CollectionMaterial();
            User user = new User();
            user.setId(userId);
            Material material = new Material();
            material.setId(materialId);
            cm.setUser(user);
            cm.setMaterial(material);
            create(cm);
        }
    }

    @Override
    @Transactional
    public void cancelCollectionMaterial(Integer userId, Integer materialId) {
        List<CollectionMaterial> collectionMaterialList = collectionMaterialDao.findByUserIdAndMaterialId(userId,materialId);
        for (int i = 0; i < collectionMaterialList.size(); i++) {
            collectionMaterialDao.delete(collectionMaterialList.get(i));
        }

    }
}