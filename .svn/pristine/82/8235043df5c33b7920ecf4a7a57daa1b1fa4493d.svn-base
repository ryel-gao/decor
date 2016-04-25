package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.Goods;
import com.bluemobi.decor.entity.Scene;
import com.bluemobi.decor.entity.SceneGoods;
import com.bluemobi.decor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by gaoll on 2015/3/3.
 */
public interface SceneGoodsDao extends JpaRepository<SceneGoods, Integer>,JpaSpecificationExecutor<SceneGoods> {

    // 删除场景的图片
    @Modifying
    @Query("delete from SceneGoods a where a.scene.id =?1")
    public void deleteBySceneId(Integer sceneId);

    // 删除商品的图片
    @Modifying
    @Query("delete from SceneGoods a where a.goods.id =?1")
    public void deleteByGoodsId(Integer goodsId);

    // 查询场景里的所有商品
    @Query("select a.goods from SceneGoods a where a.scene.id = ?1")
    public List<Goods> listGoods(Integer sceneId);

    @Query("select a.scene from SceneGoods a where a.goods.id = ?1")
    public List<Scene> listSceneByGoodsId(Integer goodsId);

    @Query("select a from SceneGoods a where a.scene.id = ?1")
    public List<SceneGoods> listBySceneId(Integer sceneId);


}
