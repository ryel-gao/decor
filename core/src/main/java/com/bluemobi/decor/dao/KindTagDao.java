package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.Goods;
import com.bluemobi.decor.entity.KindTag;
import com.bluemobi.decor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by tuyh on 2015/7/8 17:03.
 */
public interface KindTagDao extends JpaRepository<KindTag, Integer>, JpaSpecificationExecutor<KindTag> {

    @Query("select a from KindTag a where a.id in ?1")
    public List<KindTag> listByIds(List<Integer> ids);

    @Query("select a from KindTag a ")
    public List<KindTag> all();
}