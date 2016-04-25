package com.bluemobi.decor.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.dao.CityDao;
import com.bluemobi.decor.entity.City;
import com.bluemobi.decor.entity.Province;
import com.bluemobi.decor.service.CityService;
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
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    @Override
    public List<City> findAll() {
        return cityDao.findAll();
    }

    @Override
    public Page<City> find(int pageNum, int pageSize) {

        return cityDao.findAll(new PageRequest(pageNum - 1, pageSize, Sort.Direction.DESC, "id"));

    }

    @Override
    public Page<City> find(int pageNum) {
        return find(pageNum, Constant.PAGE_DEF_SZIE);
    }

    @Override
    public City getById(int id) {
        return cityDao.findOne(id);
    }

    @Override
    @Transactional
    public City deleteById(int id) {
        City city = getById(id);
        cityDao.delete(city);
        return city;
    }

    @Override
    @Transactional
    public City create(City city) {
        cityDao.save(city);
        return city;
    }

    @Override
    @Transactional
    public City update(City city) {
        return cityDao.save(city);
    }

    @Override
    @Transactional
    public void deleteAll(int[] ids) {
        for (int id : ids) {
            deleteById(id);
        }
    }

    @Override
    public List<City> findCityByProvince(Province province) {
        return cityDao.findCityByProvince(province);
    }

    @Override
    public List<City> findCityByName(String cityName) {
        return cityDao.findCityByName(cityName);
    }
}