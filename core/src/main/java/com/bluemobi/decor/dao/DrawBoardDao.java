package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.DrawBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by tuyh on 2015/7/13 11:12.
 */
public interface DrawBoardDao extends JpaRepository<DrawBoard, Integer>, JpaSpecificationExecutor<DrawBoard> {

}