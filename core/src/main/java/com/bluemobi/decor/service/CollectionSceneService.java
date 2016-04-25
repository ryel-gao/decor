package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.Collection;
import com.bluemobi.decor.entity.CollectionScene;
import com.bluemobi.decor.entity.Favorite;
import com.bluemobi.decor.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by gaoll on 2015/3/13.
 */
public interface CollectionSceneService{
    public Page<CollectionScene> pageCollectionScene(Integer pageNum, Integer pageSize,Integer userId,String spaceTag,String name);


}
