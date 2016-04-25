package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by tuyh on 2015/7/7 9:56.
 */
public interface ProvinceDao extends JpaRepository<Province, Integer>, JpaSpecificationExecutor<Province> {

}