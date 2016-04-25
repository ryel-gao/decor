package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.KindTag;
import com.bluemobi.decor.entity.SpaceTag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by tuyh on 2015/7/8 17:05.
 */
public interface SpaceTagDao extends JpaRepository<SpaceTag, Integer>, JpaSpecificationExecutor<SpaceTag> {

    // 空间分类查询
    @Query("select a from SpaceTag a")
    public List<SpaceTag> iFindSpaceSortList();

    @Query("select a from SpaceTag a where a.id in ?1")
    public List<SpaceTag> listByIds(List<Integer> ids);

    @Query("select a from SpaceTag a")
    public List<SpaceTag> all();



}