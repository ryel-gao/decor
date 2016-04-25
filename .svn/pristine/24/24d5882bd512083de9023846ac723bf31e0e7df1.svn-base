package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.ProvinceDao;
import com.bluemobi.decor.entity.Province;
import com.bluemobi.decor.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tuyh on 2015/7/7.
 */
@Service
@Transactional(readOnly = true)
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceDao provinceDao;


    @Override
    public List<Province> findAll() {
        return provinceDao.findAll();
    }

    @Override
    public Page<Province> find(int pageNum, int pageSize) {

        return provinceDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

    }

    @Override
    public Page<Province> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public Province getById(int id) {
        return provinceDao.findOne(id);
    }

    @Override
    @Transactional
    public Province deleteById(int id) {
        Province province = getById(id);
        provinceDao.delete(province);
        return province;
    }

    @Override
    @Transactional
    public Province create(Province province) {
        provinceDao.save(province);
        return province;
    }

    @Override
    @Transactional
    public Province update(Province province) {
        return provinceDao.save(province);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<Province> findProvinceList() {
        return provinceDao.findAll();
    }
}