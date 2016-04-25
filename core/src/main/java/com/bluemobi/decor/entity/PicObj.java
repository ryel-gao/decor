package com.bluemobi.decor.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by gaoll on 2015/10/26.
 */
public class PicObj implements Serializable {

    private Integer id;

    private String objectType;

    private Date createTime;

    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
