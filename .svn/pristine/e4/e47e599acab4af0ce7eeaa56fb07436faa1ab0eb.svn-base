package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.Goods;
import com.bluemobi.decor.entity.Material;
import com.bluemobi.decor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by gaoll on 2015/3/3.
 */
public interface MaterialDao extends JpaRepository<Material, Integer>, JpaSpecificationExecutor<Material> {

    @Query("select a from Material a where a.user = ?1 and a.id = ?2")
    public List<Material> iCheckMaterialByUser(User user, Integer materialId);

    @Query("select count (a) from Material a where a.kindTag LIKE ?1 OR a.spaceTag LIKE ?1 OR a.styleTag LIKE ?1")
    public Integer count(String name);
}
