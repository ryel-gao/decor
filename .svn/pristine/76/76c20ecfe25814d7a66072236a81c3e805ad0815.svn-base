package com.bluemobi.decor.entity;

import com.bluemobi.decor.annotation.MustConvert;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by gaoll on 2015/3/3.
 */
@Entity
@Table(name = "city")
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String brevitycode;

    @ManyToOne
    @JoinColumn(name = "provinceId")
    private Province province;

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

    public String getBrevitycode() {
        return brevitycode;
    }

    public void setBrevitycode(String brevitycode) {
        this.brevitycode = brevitycode;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }
}
