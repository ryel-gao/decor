package com.bluemobi.decor.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 收藏场景
 */
@Entity
@Table(name = "collection")
public class CollectionScene implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 收藏夹
    @ManyToOne
    @JoinColumn(name = "favorite_id")
    private Favorite favorite;

    @ManyToOne
    @JoinColumn(name = "object_id")
    private Scene scene;

    // 收藏对象的类型
    @Column(name = "object_type")
    private String objectType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Favorite getFavorite() {
        return favorite;
    }

    public void setFavorite(Favorite favorite) {
        this.favorite = favorite;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
