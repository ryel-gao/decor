package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.*;
import com.bluemobi.decor.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by gaoll on 2015/3/13.
 */
public interface CollectionService extends ICommonService<Collection> {

    // 是否收藏商品
    public Boolean isCollectionGoods(Integer userId, Integer goodsId);

    // 是否收藏场景
    public Boolean isCollectionScene(Integer userId, Integer sceneId);

    // 是否收藏系列图
    public Boolean isCollectionSeries(Integer userId, Integer seriesId);

    public Boolean isCollect(Integer userId, Integer objectId,String objectType);

    // 根据用户ID和收藏源ID查询指定收藏信息
    public Collection iFindByUserAndObjectId(Favorite favorite, Integer objectId, String objectType);

    // 查询指定用户收藏夹下的所有收藏信息
    public List<Collection> iFindCollectsByFavorite(Favorite favorite);

    // 查询指定用户收藏夹下的特定收藏信息
    public Page<Collection> iFindCollectsPageByFavorite(Integer favoriteId, String objectType, Integer pageNum, Integer pageSize);

    // 查询指定类型的收藏信息
    public Page<Collection> iPageCollection(String objectType, Integer pageNum, Integer pageSize);

    // 查询指定类型的收藏数
    public int iGetCollectionNum(Integer objectId, String objectType);

    // 查询指定类型的收藏信息
    public List<Collection> iListCollection(Integer userId, String objectType);

    //根据收藏内容ID和收藏内容类型删除收藏
    public void deleteByObjectIdAndObjectType(Integer ObjectId, String objectType);

    public String pcCollectBusiness(Integer objectId,
                                    String objectType,
                                    Integer favoriteId,
                                    String favoriteName,
                                    String favoriteInfo,
                                    String favoriteCover,
                                    Integer userId);

    // 取消收藏
    public void pcCancelCollectBusiness(Integer userId,Integer objectId, String objectType);

    public Page<Collection> pageFavorite(Integer favoriteId, int pageSize, int pageNum);

}
