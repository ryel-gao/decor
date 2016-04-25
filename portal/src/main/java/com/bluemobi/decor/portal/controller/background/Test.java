package com.bluemobi.decor.portal.controller.background;

import com.bluemobi.decor.comparator.CreateTimeComparator;
import com.bluemobi.decor.entity.PicObj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by gaoll on 2015/8/13.
 */
public class Test {
    public static void main(String[] args){
        List<PicObj> list = new ArrayList<PicObj>();
        PicObj p1 = new PicObj();
        PicObj p2 = new PicObj();
        PicObj p3 = new PicObj();
        PicObj p4 = new PicObj();
        PicObj p5 = new PicObj();
        PicObj p6 = new PicObj();

        p1.setId(1);
        p2.setId(2);
        p3.setId(3);
        p4.setId(4);
        p5.setId(5);
        p6.setId(6);

        p1.setCreateTime(new Date());
        p6.setCreateTime(new Date());

        p5.setCreateTime(new Date());

        p2.setCreateTime(new Date());

        p4.setCreateTime(new Date());

        p3.setCreateTime(new Date());

        list.add(p1);
        list.add(p2);
        list.add(p3);

        list.add(p4);
        list.add(p5);
        list.add(p6);

        Collections.sort(list,new CreateTimeComparator());

        System.err.println(p1.getCreateTime().getTime());
        System.err.println(p3.getCreateTime().getTime());
        System.err.println(p1.getCreateTime().compareTo(p2.getCreateTime()));
       // System.err.println(list);

    }
}
