package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.SpaceTag;
import com.bluemobi.decor.entity.StyleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by tuyh on 2015/7/8 17:06.
 */
public interface StyleTagDao extends JpaRepository<StyleTag, Integer>, JpaSpecificationExecutor<StyleTag> {

    // 风格分类查询
    @Query("select a from StyleTag a")
    public List<StyleTag> iFindStyleSortList();

    @Query("select a from StyleTag a where a.id in ?1")
    public List<StyleTag> listByIds(List<Integer> ids);

    @Query("select a from StyleTag a ")
    public List<StyleTag> all();
}