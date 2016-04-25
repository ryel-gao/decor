package com.bluemobi.decor.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 回复
 * Created by tuyh on 2015/7/10.
 */
public class Reply implements Serializable {

    private Integer id;

    private String content;

    private Date createTime;

    private String headImage;

    private String nickname;

    private Integer praiseNum;

    private String isPraise;

    public String getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(String isPraise) {
        this.isPraise = isPraise;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
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

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
