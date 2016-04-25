package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.Scene;
import com.bluemobi.decor.entity.Series;
import com.bluemobi.decor.entity.SeriesScene;
import com.bluemobi.decor.service.common.ICommonService;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by gaoll on 2015/3/13.
 */
public interface SeriesSceneService extends ICommonService<SeriesScene> {

    // 根据系列图ID获取关联的场景图列表
    public List<SeriesScene> iFindBySeries(Series series);

    // 根据系列图ID获取关联的场景图列表PC
    public List<SeriesScene> pcFindBySeries(Series series);


    public List<Scene> findSceneListBySeriesId(Integer seriesId);

    public List<Series> findSeriesListBySceneId(Integer sceneId);

}
