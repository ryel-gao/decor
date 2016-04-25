package com.bluemobi.decor.dao;

import com.bluemobi.decor.entity.AboutWeb;
import com.bluemobi.decor.entity.Ad;
import com.bluemobi.decor.entity.Goods;
import com.bluemobi.decor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by gaoll on 2015/3/3.
 */
public interface AdDao extends JpaRepository<Ad, Integer>,JpaSpecificationExecutor<Ad> {
    @Query("select a from Ad a order by a.rank asc ")
    public List<Ad> pcList();

}
