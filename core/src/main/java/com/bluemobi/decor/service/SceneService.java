package com.bluemobi.decor.service;

import com.bluemobi.decor.entity.Scene;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.service.common.ICommonService;
import com.bluemobi.decor.service.moduleHandler.SeeNumHandler;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by gaoll on 2015/3/13.
 */
public interface SceneService extends ICommonService<Scene>,SeeNumHandler {

    // 场景列表
    public Page<Scene> iPage(int pageNum,
                             int pageSize,
                             String name,
                             Integer spaceTagId,
                             Integer styleTagId,
                             String sort);

    // 我发布的场景
    public Page<Scene> iPageMy(int pageNum,
                               int pageSize,
                               Integer userId, String name, Integer spaceTagId);

    // 推荐的场景
    public Page<Scene> iPageRecommend(int pageNum,
                                      int pageSize);

    // 添加场景
    public void addScene(Integer userId,
                         String name,
                         String spaceTagIds,
                         String styleTagIds,
                         String info,
                         String isShow,
                         String image,
                         String thumbnailImage,
                         String goodsIds,
                         String positions);

    // 添加场景
    public void pcAddScene(Integer userId,
                         String name,
                         String spaceTagIds,
                         String styleTagIds,
                         String info,
                         String isShow,
                         String image,
                         String thumbnailImage,
                         String goodsIds,
                         String positions);

    // 修改场景
    public void updateScene(Integer sceneId,
                            String name,
                            String spaceTagIds,
                            String styleTagIds,
                            String info,
                            String isShow,
                            String image,
                            String goodsIds,
                            String positions);

    // 查询指定用户的场景图列表
    public List<Scene> iFindSceneByUser(User user);

    // 查询场景图列表
    public int findScenes(String name);

    // 查询场景图列表
    public List<Scene> findScenesByName(String name);

    //根据ID，场景图名称，场景图发布者,推荐状态筛选场景图列表
    public Page<Scene> findScenes(Integer id, String name, String author, String styleId, String spaceId, String isRecommend, int pageNum, int pageSize);

    //根据场景图ID删除场景图与场景系列图
    public void deleteScenes(Integer scenesId);

    //根据场景图ID删除多个场景图与场景系列图
    public void deleteScenes(int[] scenesId);

    // 推荐 or 取消推荐场景图
    public void changeRecommend(Integer id, String isRecommend);

    // 我发布的场景
    public List<Scene> iListMy(Integer userId, String spaceTag);

    // 查询我发布的场景
    public List<Scene> listMy(Integer userId, Integer number);

    // 查询一定数量的场景图
    public List<Scene> list(Integer number);

    //新增场景图，含商品图关系
    public void insert(Integer userId, String sceneName, String styleTagIds, String spaceTagIds, String info, String image, String isShow, String isRecommend, List<Map<String, Object>> goodsMap);

    public Scene createAndRecommend(Scene scene);

    public void updata(Integer sceneId, String sceneName, String styleTagIds, String spaceTagIds, String info, String image, String isShow, String isRecommend, List<Map<String, Object>> goodsMap);

    List<Scene> pcRecommendList();

    // 场景分页
    public Page<Scene> pcPage(int pageNum,
                             int pageSize,
                             String name,
                             Integer spaceTagId,
                             Integer styleTagId);

    // 同类型
    List<Scene> sameTypeScene(Integer sceneId);
    //根据ID查询分页
    public Page<Scene> pcFindScenePage(final User user, int pageNum, int pageSize);
}
