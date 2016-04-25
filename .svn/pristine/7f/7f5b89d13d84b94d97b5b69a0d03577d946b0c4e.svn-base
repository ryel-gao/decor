package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.City;
import com.bluemobi.decor.entity.Province;
import com.bluemobi.decor.service.common.ICommonService;

import java.util.List;

/**
 * Created by tuyh on 2015/7/7 9:52.
 */
public interface CityService extends ICommonService<City> {

    // 根据省份ID查询该省份所属的城市集合
    public List<City> findCityByProvince(Province province);

    //根据名字查询ID
    public  List<City> findCityByName(String cityName);
}