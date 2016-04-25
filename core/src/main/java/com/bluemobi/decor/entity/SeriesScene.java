package com.bluemobi.decor.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 系列图场景图关系
 */
@Entity
@Table(name = "series_scene")
public class SeriesScene implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    // 系列图
    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    // 场景图
    @ManyToOne
    @JoinColumn(name = "scene_id")
    private Scene scene;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
