package com.bluemobi.decor.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by gaoll on 2015/3/3.
 */
@Entity
@Table(name = "province")
public class Province implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String brevitycode;

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

}
