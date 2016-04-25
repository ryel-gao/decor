package com.bluemobi.decor.entity;

import com.bluemobi.decor.annotation.MustConvert;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 点赞
 * Created by tuyh on 2015/7/9.
 */
@Entity
@Table(name = "praise")
public class Praise implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    // 关联表ID
    @Column(name = "object_id")
    private Integer objectId;

    // 点赞用户
    @MustConvert
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 关联表类型
    @Column(name = "object_type")
    private String objectType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
}
