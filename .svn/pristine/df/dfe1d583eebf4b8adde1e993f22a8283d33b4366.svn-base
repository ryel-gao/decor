package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.SeriesTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by tuyh on 2015/7/8 17:07.
 */
public interface SeriesTagDao extends JpaRepository<SeriesTag, Integer>, JpaSpecificationExecutor<SeriesTag> {

    // 系列图标签查询
    @Query("select a from SeriesTag a")
    public List<SeriesTag> iFindSeriesSortList();

    @Query("select a from SeriesTag a")
    public List<SeriesTag> all();
}