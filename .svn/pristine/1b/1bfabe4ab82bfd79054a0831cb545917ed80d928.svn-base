package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.CollectionMaterial;
import com.bluemobi.decor.entity.Material;
import com.bluemobi.decor.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by gaoll on 2015/3/13.
 */
public interface CollectionMaterialService extends ICommonService<CollectionMaterial> {

    // 是否抽查了素材
    public Boolean isCollectionMaterial(Integer userId,Integer materialId);

    // 根据素材ID查询相关联的收藏
    public List<CollectionMaterial> getInfoByMaterial(Integer materialId);

    // 收藏素材图
    public void collectionMaterial(Integer userId,Integer materialId);

    // 收藏素材图
    public void cancelCollectionMaterial(Integer userId,Integer materialId);
}
