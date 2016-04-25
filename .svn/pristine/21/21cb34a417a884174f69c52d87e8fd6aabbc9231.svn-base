package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.KindTag;
import com.bluemobi.decor.entity.MessageTag;
import com.bluemobi.decor.entity.Series;
import com.bluemobi.decor.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by tuyh on 2015/7/8 17:09.
 */
public interface MessageTagService extends ICommonService<MessageTag> {

    // 资讯标签分类查询
    public Page<MessageTag> iFindMessageSortPage(Integer pageNum, Integer pageSize);

    Page<MessageTag> backendFindByCondition(int pageNum,
                                            int pageSize,
                                            final String id,
                                            final String name,
                                            final String num);
}