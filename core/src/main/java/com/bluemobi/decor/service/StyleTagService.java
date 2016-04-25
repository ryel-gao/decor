package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.KindTag;
import com.bluemobi.decor.entity.SpaceTag;
import com.bluemobi.decor.entity.StyleTag;
import com.bluemobi.decor.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by tuyh on 2015/7/8 17:10.
 */
public interface StyleTagService extends ICommonService<StyleTag> {

    // 风格分类查询
    public Page<StyleTag> iFindStyleSortPage(Integer pageNum, Integer pageSize);

    // 根据id查询
    public List<StyleTag> listByIds(List<Integer> ids);

    Page<StyleTag> backendFindByCondition(int pageNum,
                                          int pageSize,
                                          final String id,
                                          final String name,
                                          final String num);

    //修改标签的图片数,num为1就+1,num为-1就减1
    public void updateStyleTagSeviceNum(Integer styleTagId, int num);

    public String getTagStr(String styleTagIds);

    public List<StyleTag> all();
}