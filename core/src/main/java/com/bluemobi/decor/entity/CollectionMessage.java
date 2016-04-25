package com.bluemobi.decor.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tuyh on 2015/7/14.
 */
@Entity
@Table(name = "collection_message")
public class CollectionMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    // 收藏用户
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 资讯
    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

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

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
