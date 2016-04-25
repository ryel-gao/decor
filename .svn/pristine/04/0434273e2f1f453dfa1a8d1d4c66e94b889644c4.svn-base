package com.bluemobi.decor.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tuyh on 2015/7/7.
 */
@Entity
@Table(name = "user_third")
public class UserThird implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    // 第三方OpenId
    private String open_id;

    // 关联用户
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 第三方类型(微信:2,QQ:3,,新浪:4)
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
