package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.Feedback;
import com.bluemobi.decor.service.common.ICommonService;
import org.springframework.data.domain.Page;

/**
 * Created by liuhm on 2015/7/3.
 */
public interface FeedbackService extends ICommonService<Feedback> {
    public Page<Feedback> backendFindByCondition(int pageNum,
                                                 int pageSize,
                                                 final String id,
                                                 final String username);

}
