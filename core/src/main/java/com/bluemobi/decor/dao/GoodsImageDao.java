package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.Goods;
import com.bluemobi.decor.entity.GoodsImage;
import com.bluemobi.decor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by gaoll on 2015/3/3.
 */
public interface GoodsImageDao extends JpaRepository<GoodsImage, Integer>, JpaSpecificationExecutor<GoodsImage> {

    @Query("select a from GoodsImage a where a.goods.id =?1 order by a.id asc")
    public List<GoodsImage> listByGoodsId(Integer goodsId);

    // 根据商品ID删除商品图片
    @Modifying
    @Query("delete from GoodsImage a where a.goods.id =?1")
    public void deleteByGoodsId(Integer goodsId);

}
