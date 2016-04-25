package com.bluemobi.decor.entity;

import com.bluemobi.decor.annotation.MustConvert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 咨询类型
 */
@Entity
@Table(name = "message_tag")
public class MessageTag implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer num;

    @Column(name = "english_name")
    private String englishName;

    @Column(name = "create_date")
    private Date createDate;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
