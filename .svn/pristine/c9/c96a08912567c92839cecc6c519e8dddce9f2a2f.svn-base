package com.bluemobi.decor.entity;

import com.bluemobi.decor.annotation.MustConvert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 资讯
 * Created by tuyh on 2015/7/14.
 */
@Entity
@Table(name = "message")
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    // 资讯标题
    private String title;

    // 资讯所属分类
    @MustConvert
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private MessageTag messageTag;

    // 资讯排序
    @Column(name = "show_turn")
    private Integer showTurn;

    // 资讯封面图片
    private String image;

    // 纯文本内容
    @Column(name = "sub_content")
    private String subContent;

    // 资讯内容
    private String content;

    // 资讯标签
    @Column(name = "show_tags")
    private String showTags;

    // 是否推荐，no=不推荐，yes=推荐
    @Column(name = "is_recommend")
    private String isRecommend;

    //推荐时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "recommend_time")
    private Date recommendTime;

    // 发布时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    // 收藏量
    @Column(name = "collection_num")
    private Integer collectionNum;

    // 资讯封面图片
    @Transient
    private String path;

    // 是否收藏
    @Transient
    private Integer ifCollect;

    //资讯图片
    private String intro_image;

    // 栏目
    @Transient
    private String tagName;

    public Date getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(Date recommendTime) {
        this.recommendTime = recommendTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MessageTag getMessageTag() {
        return messageTag;
    }

    public void setMessageTag(MessageTag messageTag) {
        this.messageTag = messageTag;
    }

    public String getIntro_image() {
        return intro_image;
    }

    public void setIntro_image(String intro_image) {
        this.intro_image = intro_image;
    }

    public Integer getShowTurn() {
        return showTurn;
    }

    public void setShowTurn(Integer showTurn) {
        this.showTurn = showTurn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getShowTags() {
        return showTags;
    }

    public void setShowTags(String showTags) {
        this.showTags = showTags;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getIfCollect() {
        return ifCollect;
    }

    public void setIfCollect(Integer ifCollect) {
        this.ifCollect = ifCollect;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getSubContent() {
        return subContent;
    }

    public void setSubContent(String subContent) {
        this.subContent = subContent;
    }

    public Integer getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
    }
}
