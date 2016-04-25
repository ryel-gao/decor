package com.bluemobi.decor.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系列图
 */
@Entity
@Table(name = "series")
public class Series implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    // 上传用户
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 标签id
    @ManyToOne
    @JoinColumn(name = "series_tag_id")
    private SeriesTag seriesTag;

    // 描述
    private String info;

    // 创建时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    // 是否收藏
    @Transient
    private String isCollection;

    // 系列图里面的场景图
    @Transient
    private List<Scene> sceneList;

    // 封面图
    @Column(name = "cover")
    private String cover;

    @Column(name = "thumbnail_cover")
    private String thumbnailCover;

    // 查看量
    @Column(name = "see_num")
    private Integer seeNum;

    // 收藏量
    @Column(name = "collection_num")
    private Integer collectionNum;

    // 点赞量
    @Column(name = "praise_num")
    private Integer praiseNum;

    // 系列图发布者
    @Transient
    private String author;

    // 分类
    @Transient
    private String sort;

    // 场景图数量
    @Transient
    private Integer sceneNum;

    // 图片
    @Transient
    private String image;


    public String getThumbnailCover() {
        return thumbnailCover;
    }

    public void setThumbnailCover(String thumbnailCover) {
        this.thumbnailCover = thumbnailCover;
    }

    public Integer getSceneNum() {
        return sceneNum;
    }

    public void setSceneNum(Integer sceneNum) {
        this.sceneNum = sceneNum;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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

    public SeriesTag getSeriesTag() {
        return seriesTag;
    }

    public void setSeriesTag(SeriesTag seriesTag) {
        this.seriesTag = seriesTag;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(String isCollection) {
        this.isCollection = isCollection;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Scene> getSceneList() {
        return sceneList;
    }

    public void setSceneList(List<Scene> sceneList) {
        this.sceneList = sceneList;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Integer getSeeNum() {
        return seeNum;
    }

    public void setSeeNum(Integer seeNum) {
        this.seeNum = seeNum;
    }

    public Integer getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
    }
}
