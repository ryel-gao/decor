package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.Favorite;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by tuyh on 2015/7/9 10:40.
 */
public interface FavoriteService extends ICommonService<Favorite> {

    // 查询用户收藏夹列表信息（分页）
    public Page<Favorite> iPageForFavorite(int pageNum, int pageSize, final Integer userId);

    // 查询用户的所有收藏夹信息
    public List<Favorite> iFindAllCollects(User user);

    // 删除收藏夹
    public void delFavorite(Integer favoriteId);

    public List<Favorite> listByUserId(Integer userId);

    // 获取收藏夹的封面图
    public String cover(Integer favoriteId);

    //分页展示所有用户的收藏
    public Page<Favorite> pageFavorite(Integer userId, int pageSize, int pageNum);

}