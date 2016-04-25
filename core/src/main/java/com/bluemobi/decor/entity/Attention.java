package com.bluemobi.decor.entity;

import com.bluemobi.decor.annotation.MustConvert;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 关注 or 粉丝
 * Created by tuyh on 2015/7/10.
 */
@Entity
@Table(name = "attention")
public class Attention implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 关注人
    @MustConvert
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User attentionUser;

    // 粉丝
    @MustConvert
    @ManyToOne
    @JoinColumn(name = "fans_id")
    private User fansUser;

    // 用户是否有更新
    @Column(name = "user_has_update")
    private String userHasUpdate;

    @Transient
    // 用户ID
    private Integer userId;

    @Transient
    // 名称
    private String name;

    @Transient
    // 头像
    private String photo;

    @Transient
    // 个人介绍
    private String info;

    @Transient
    // 是否关注
    private String isAttention;

    @Transient
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getAttentionUser() {
        return attentionUser;
    }

    public void setAttentionUser(User attentionUser) {
        this.attentionUser = attentionUser;
    }

    public User getFansUser() {
        return fansUser;
    }

    public void setFansUser(User fansUser) {
        this.fansUser = fansUser;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(String isAttention) {
        this.isAttention = isAttention;
    }

    public String getUserHasUpdate() {
        return userHasUpdate;
    }

    public void setUserHasUpdate(String userHasUpdate) {
        this.userHasUpdate = userHasUpdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
