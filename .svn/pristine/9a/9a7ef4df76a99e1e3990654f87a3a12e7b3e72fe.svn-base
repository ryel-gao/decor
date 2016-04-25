package com.bluemobi.decor.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 收藏的素材
 */
@Entity
@Table(name = "collection_material")
public class CollectionMaterial implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    // 上传用户
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 素材
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
