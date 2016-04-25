package com.bluemobi.decor.comparator;

import com.bluemobi.decor.entity.PicObj;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by gaoll on 2015/10/26.
 */
public class CreateTimeComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        PicObj p1 = (PicObj)o1;
        PicObj p2 = (PicObj)o2;
        if(p1 == null || p1.getCreateTime() == null){
            return 1;
        }
        if(p2 == null || p2.getCreateTime() == null){
            return -1;
        }
        return -p1.getCreateTime().compareTo(p2.getCreateTime());
    }
}
