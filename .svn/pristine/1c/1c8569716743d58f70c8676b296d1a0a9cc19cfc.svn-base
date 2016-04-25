package com.bluemobi.decor.entity;

import javax.persistence.*;
import javax.persistence.metamodel.ManagedType;
import java.io.Serializable;

/**
 * 商品图片
 */
@Entity
@Table(name = "goods_image")
public class GoodsImage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @Column(name = "image")
    private String image;

    @Column(name = "thumbnail_image")
    private String thumbnailImage;

    // 是否设置为封面,no=不是，yes=是
    @Column(name = "is_head")
    private String isHead;

    // 是否转为素材,no=不是，yes=是
    @Column(name = "is_turn_material")
    private String isTurnMaterial;

    // 图片的素材，可以为null
    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsHead() {
        return isHead;
    }

    public void setIsHead(String isHead) {
        this.isHead = isHead;
    }

    public String getIsTurnMaterial() {
        return isTurnMaterial;
    }

    public void setIsTurnMaterial(String isTurnMaterial) {
        this.isTurnMaterial = isTurnMaterial;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }
}
