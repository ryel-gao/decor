package com.bluemobi.decor.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 素材
 */
@Entity
@Table(name = "material")
public class Material implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 上传用户
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "kind_tag_ids")
    private String kindTagIds;

    @Column(name = "space_tag_ids")
    private String spaceTagIds;

    @Column(name = "style_tag_ids")
    private String styleTagIds;

    @Column(name = "kind_tag")
    private String kindTag;

    @Column(name = "space_tag")
    private String spaceTag;

    @Column(name = "style_tag")
    private String styleTag;

    // 素材图片
    @Column(name = "image")
    private String image;

    // 收藏数量
    private Integer collect;

    // 上传时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @Transient
    private String isCollection;

    //种类名称
    @Transient
    private String kindTagName;

    //种类ID
    @Transient
    private Integer kindTagId;

    //该场景图下的所有空间标签名
    @Transient
    private List<String> spaceTagName;

    //该场景图下的所有风格标签名
    @Transient
    private List<String> styleTagName;

    //space标签信息集合
    @Transient
    private List<Map<String, Object>> spaceTagMap;

    //style标签信息集合
    @Transient
    private List<Map<String, Object>> styleTagMap;

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

    public String getKindTagIds() {
        return kindTagIds;
    }

    public void setKindTagIds(String kindTagIds) {
        this.kindTagIds = kindTagIds;
    }

    public String getSpaceTagIds() {
        return spaceTagIds;
    }

    public void setSpaceTagIds(String spaceTagIds) {
        this.spaceTagIds = spaceTagIds;
    }

    public String getStyleTagIds() {
        return styleTagIds;
    }

    public void setStyleTagIds(String styleTagIds) {
        this.styleTagIds = styleTagIds;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(String isCollection) {
        this.isCollection = isCollection;
    }

    public String getKindTagName() {
        return kindTagName;
    }

    public void setKindTagName(String kindTagName) {
        this.kindTagName = kindTagName;
    }

    public List<String> getSpaceTagName() {
        return spaceTagName;
    }

    public void setSpaceTagName(List<String> spaceTagName) {
        this.spaceTagName = spaceTagName;
    }

    public List<String> getStyleTagName() {
        return styleTagName;
    }

    public void setStyleTagName(List<String> styleTagName) {
        this.styleTagName = styleTagName;
    }

    public String getKindTag() {
        return kindTag;
    }

    public void setKindTag(String kindTag) {
        this.kindTag = kindTag;
    }

    public String getSpaceTag() {
        return spaceTag;
    }

    public void setSpaceTag(String spaceTag) {
        this.spaceTag = spaceTag;
    }

    public String getStyleTag() {
        return styleTag;
    }

    public void setStyleTag(String styleTag) {
        this.styleTag = styleTag;
    }

    public Integer getKindTagId() {
        return kindTagId;
    }

    public void setKindTagId(Integer kindTagId) {
        this.kindTagId = kindTagId;
    }

    public List<Map<String, Object>> getSpaceTagMap() {
        return spaceTagMap;
    }

    public void setSpaceTagMap(List<Map<String, Object>> spaceTagMap) {
        this.spaceTagMap = spaceTagMap;
    }

    public List<Map<String, Object>> getStyleTagMap() {
        return styleTagMap;
    }

    public void setStyleTagMap(List<Map<String, Object>> styleTagMap) {
        this.styleTagMap = styleTagMap;
    }
}
