package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
 * Created by liuhm on 2015/7/3.
 */
public interface FeedbackDao extends JpaRepository<Feedback, Integer>,JpaSpecificationExecutor<Feedback> {







}
