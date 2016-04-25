package com.bluemobi.decor.entity;

import com.bluemobi.decor.annotation.MustConvert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评论更新状态表
 *
 * 主要用来查询用户评论，在新增评论的时候，就会更新该表，对应是user_id是被评论对象的创建者id
 */
@Entity
@Table(name = "comment_update_status")
public class CommentUpdateStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "object_id")
    private Integer objectId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "object_type")
    private String objectType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;

    @Transient
    private List<Comment> commentList;

    @Transient
    private String objectCover;

    public String getObjectCover() {
        return objectCover;
    }

    public void setObjectCover(String objectCover) {
        this.objectCover = objectCover;
    }


    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
