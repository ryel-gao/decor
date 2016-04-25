package com.bluemobi.decor.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户角色
 */
@Entity
@Table(name = "t_user_role")
public class TUserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private TRole role;

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

    public TRole getRole() {
        return role;
    }

    public void setRole(TRole role) {
        this.role = role;
    }
}
