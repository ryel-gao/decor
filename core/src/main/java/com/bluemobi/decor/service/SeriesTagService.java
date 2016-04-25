package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.KindTag;
import com.bluemobi.decor.entity.SeriesTag;
import com.bluemobi.decor.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by tuyh on 2015/7/8 17:09.
 */
public interface SeriesTagService extends ICommonService<SeriesTag> {

    // 系列图标签查询
    public Page<SeriesTag> iFindSeriesSortPage(Integer pageNum, Integer pageSize);

    Page<SeriesTag> backendFindByCondition(int pageNum,
                                           int pageSize,
                                           final String id,
                                           final String name,
                                           final String num);

    List<SeriesTag> all();
}