package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.MessageTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by tuyh on 2015/7/8 17:04.
 */
public interface MessageTagDao extends JpaRepository<MessageTag, Integer>, JpaSpecificationExecutor<MessageTag> {

    // 资讯标签分类查询
    @Query("select a from MessageTag a")
    public List<MessageTag> iFindMessageSortList();
}