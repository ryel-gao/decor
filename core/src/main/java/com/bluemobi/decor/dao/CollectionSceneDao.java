package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.Collection;
import com.bluemobi.decor.entity.CollectionScene;
import com.bluemobi.decor.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by gaoll on 2015/3/3.
 */
public interface CollectionSceneDao extends JpaRepository<CollectionScene, Integer>, JpaSpecificationExecutor<CollectionScene> {

}
