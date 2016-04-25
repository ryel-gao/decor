package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by gaoll on 2015/3/3.
 */
public interface CollectionDao extends JpaRepository<Collection, Integer>, JpaSpecificationExecutor<Collection> {

    @Query("select a from Collection a where a.favorite = ?1 and a.objectId = ?2 and a.objectType = ?3")
    public List<Collection> iFindByUserAndObjectId(Favorite favorite, Integer objectId, String objectType);

    @Query("select a from Collection a where a.favorite = ?1")
    public List<Collection> iFindCollectsByFavorite(Favorite favorite);

    @Query("select a from Collection a where a.objectId = ?1 and a.objectType = ?2")
    public List<Collection> iGetCollectionNum(Integer objectId, String objectType);

    @Query("select a from Collection a where a.objectType = ?1")
    public List<Collection> iListCollection(String objectType);

    //根据收藏内容ID和收藏内容类型删除收藏
    @Modifying
    @Query("delete from Collection a where a.objectId =?1 and a.objectType =?2")
    public void deleteByObjectIdAndObjectType(Integer ObjectId, String objectType);

    // 根据收藏夹id删除
    @Modifying
    @Query("delete from Collection a where a.favorite.id =?1")
    public void delByFavoriteId(Integer favoriteId);

    @Query("select a from Collection a where a.favorite.user.id =?1 and a.objectId = ?2 and a.objectType = ?3")
    public List<Collection> findByUserIdAndObjectIdAndObjectType(Integer userId, Integer objectId, String objectType);

    // 取消收藏
    @Modifying
    @Query("select a from Collection a where a.favorite.id =?1 and a.objectId = ?2 and a.objectType = ?3")
    public List<Collection> listByFavoriteIdAndObjectIdAndObjectType(Integer favoriteId,Integer objectId,String objectType);
}
