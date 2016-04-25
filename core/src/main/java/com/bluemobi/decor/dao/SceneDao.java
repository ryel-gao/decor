package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.Scene;
import com.bluemobi.decor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by gaoll on 2015/3/3.
 */
public interface SceneDao extends JpaRepository<Scene, Integer>, JpaSpecificationExecutor<Scene> {

    @Query("select a from Scene a where a.user = ?1")
    public List<Scene> iFindSceneByUser(User user);

    @Query("select a from Scene a where a.name like ?1")
    public List<Scene> findScenes(String name);

    @Query("select a from Scene a where a.name like ?1")
    public List<Scene> findScenesByName(String name);

    @Query("select a from Scene a where a.isRecommend = 'yes' order by a.recommendTime desc ")
    public List<Scene> recommendList();

}
