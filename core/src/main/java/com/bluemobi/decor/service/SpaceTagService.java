package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.KindTag;
import com.bluemobi.decor.entity.SpaceTag;
import com.bluemobi.decor.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by tuyh on 2015/7/8 17:08.
 */
public interface SpaceTagService extends ICommonService<SpaceTag> {

    // 空间分类查询
    public Page<SpaceTag> iFindSpaceSortPage(Integer pageNum, Integer pageSize);

    // 查询空间分类（分页）
    public Page<SpaceTag> iPage(int pageNum, int pageSize);

    // 根据id查询
    public List<SpaceTag> listByIds(List<Integer> ids);


    Page<SpaceTag> backendFindByCondition(int pageNum,
                                          int pageSize,
                                          final String name,
                                          final String image);

    //修改标签的图片数,num为1就+1,num为-1就减1
    public void updateSpaceTagSeviceNum(Integer spaceTagId, int num);

    public String getTagStr(String spaceTagIds);

    public List<SpaceTag> all();
}