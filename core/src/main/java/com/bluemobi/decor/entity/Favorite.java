package com.bluemobi.decor.entity;

import com.bluemobi.decor.annotation.MustConvert;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 收藏夹
 */
@Entity
@Table(name = "favorite")
public class Favorite implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    // 创建者
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 收藏夹名称
    private String name;

    // 收藏夹描述信息
    private String info;

    // 收藏夹封面图路径
    @Transient
    private String cover;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
