package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.DrawBoard;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * Created by tuyh on 2015/7/13 11:12.
 */
public interface DrawBoardService extends ICommonService<DrawBoard> {

    // 查询指定用户的画板素材列表
    public Page<DrawBoard> iPageWithUser(Integer userId, Integer pageNum, Integer pageSize);

    //根据ID，发布者获取画板作品
    public Page<DrawBoard> findDrawBoards(Integer id,String userName,Integer pageNum, Integer pageSize);

    public void save(Integer userId,String image);

    public Map<String,Object> iImageData(Integer userId);
}