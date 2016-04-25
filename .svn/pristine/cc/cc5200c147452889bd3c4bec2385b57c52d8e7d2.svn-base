package com.bluemobi.decor.entity;

import com.bluemobi.decor.annotation.MustConvert;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色资源
 */
@Entity
@Table(name = "t_role_resource")
public class TRoleResource implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private TRole role;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private TResource resource;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TRole getRole() {
        return role;
    }

    public void setRole(TRole role) {
        this.role = role;
    }

    public TResource getResource() {
        return resource;
    }

    public void setResource(TResource resource) {
        this.resource = resource;
    }
}
