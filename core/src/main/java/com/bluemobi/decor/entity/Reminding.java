package com.bluemobi.decor.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by reny on 2015/7/17.
 * 消息提醒
 */
@Entity
@Table(name = "reminding")
public class Reminding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 消息提醒所属用户
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //消息提醒内容
    @Column(name = "content")
    private String content;

    // 创建时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
