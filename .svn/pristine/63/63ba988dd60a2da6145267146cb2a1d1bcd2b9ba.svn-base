package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.City;
import com.bluemobi.decor.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by tuyh on 2015/7/7 9:55.
 */
public interface CityDao extends JpaRepository<City, Integer>, JpaSpecificationExecutor<City> {

    @Query("select a from City a where a.province = ?1")
    public List<City> findCityByProvince(Province province);
    @Query("select a from City a where a.name = ?1")
    public List<City> findCityByName(String cityName);
}