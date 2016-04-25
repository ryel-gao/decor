package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.CollectionMaterial;
import com.bluemobi.decor.entity.Material;
import com.bluemobi.decor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by gaoll on 2015/3/3.
 */
public interface CollectionMaterialDao extends JpaRepository<CollectionMaterial, Integer>,JpaSpecificationExecutor<CollectionMaterial> {
    @Query("select a from CollectionMaterial a where a.user.id =?1 and a.material.id = ?2")
    public List<CollectionMaterial> findByUserIdAndMaterialId(Integer userId,Integer materialId);

    @Query("select a from CollectionMaterial a where a.material.id = ?1")
    public List<CollectionMaterial> getInfoByMaterial(Integer materialId);

    //根据素材ID删除收藏
    @Modifying
    @Query("delete from CollectionMaterial a where a.material.id =?1")
    public void deleteByMaterialId(Integer materialId);
}
