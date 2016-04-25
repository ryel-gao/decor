package com.bluemobi.decor.portal.controller.api;


import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.core.ERROR_CODE;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.*;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.UploadUtils;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.*;
import com.bluemobi.decor.utils.APIFactory;
import com.bluemobi.decor.utils.ComFun;
import com.bluemobi.decor.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 场景
 * Created by gaoll on 2015/3/12.
 */
@Controller
@RequestMapping("api/scene")
public class SceneApi extends CommonController {

    @Autowired
    private SceneService sceneService;

    @Autowired
    private SceneGoodsService sceneGoodsService;

    @Autowired
    private SeriesSceneService seriesSceneService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private AttentionService attentionService;

    @Autowired
    private UserService userService;

    @Autowired
    private SpaceTagService spaceTagService;

    @Autowired
    private CollectionSceneService collectionSceneService;

    @Autowired
    private UploadImageService uploadImageService;

    // 场景列表查询
    @RequestMapping(value = "/findSceneList")
    public void findSceneList(HttpServletRequest request,
                              HttpServletResponse response,
                              Integer pageNum,
                              Integer pageSize,
                              Integer userId,
                              String name,
                              Integer spaceTagId,
                              Integer styleTagId,
                              String sort) {
        Page<Scene> page = sceneService.iPage(pageNum, pageSize, name, spaceTagId, styleTagId, sort);
        for (Scene scene : page.getContent()) {
            // 是否收藏
            if (collectionService.isCollectionScene(userId, scene.getId())) {
                scene.setIsCollection("yes");
            } else {
                scene.setIsCollection("no");
            }

            // 是否点赞
            if (praiseService.isPraise(userId, scene.getId(), Constant.PRAISE_TYPE_SCENE)) {
                scene.setIsPraise("yes");
            } else {
                scene.setIsPraise("no");
            }

            if(scene.getThumbnailImage() == null){
                scene.setThumbnailImage(scene.getImage());
            }
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj);
        WebUtil.printApi(response, result);
    }

    // 我发布的场景图
    @RequestMapping(value = "/findMyScenesList")
    public void findMyScenesList(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Integer pageNum,
                                 Integer pageSize,
                                 Integer userId, String name, Integer spaceTagId) {
        Page<Scene> page = sceneService.iPageMy(pageNum, pageSize, userId, name, spaceTagId);
        for (Scene scene : page.getContent()) {
            // 去掉tag里的@
            scene.setSpaceTagIds(ComFun.tagsThin(scene.getSpaceTagIds()));
            scene.setStyleTagIds(ComFun.tagsThin(scene.getStyleTagIds()));

            if(scene.getThumbnailImage() == null){
                scene.setThumbnailImage(scene.getImage());
            }
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj);
        WebUtil.printApi(response, result);
    }

    // 推荐的场景图列表 12张
    @RequestMapping(value = "/recommend")
    public void recommend(HttpServletRequest request,
                          HttpServletResponse response,
                          Integer pageNum,
                          Integer pageSize) {
        Page<Scene> page = sceneService.iPageRecommend(pageNum, pageSize);
        for (Scene scene : page.getContent()) {
            if(scene.getThumbnailImage() == null){
                scene.setThumbnailImage(scene.getImage());
            }
        }
        Map<String, Object> dataMap = APIFactory.fitting(page);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj);
        WebUtil.printApi(response, result);
    }

    /**
     * 场景详情信息
     * 包括场景中的商品，场景所属系列
     *
     * @param request
     * @param response
     * @param sceneId
     */
    @RequestMapping(value = "/detail")
    public void detail(HttpServletRequest request,
                       HttpServletResponse response,
                       Integer sceneId,
                       Integer userId) {
        // 场景中的商品
        List<Goods> goodsList = sceneGoodsService.listGoods(sceneId);
        for (Goods goods : goodsList) {
            if(goods.getThumbnailCover() == null){
                goods.setThumbnailCover(goods.getCover());
            }
        }

        // 场景所属的系列
        List<Series> seriesList = seriesSceneService.findSeriesListBySceneId(sceneId);
        for (Series series : seriesList) {
            if(series.getThumbnailCover() == null){
                series.setThumbnailCover(series.getCover());
            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("authorId", sceneService.getById(sceneId).getUser().getId());
        map.put("goodsList", goodsList);
        map.put("seriesList", seriesList);
        map.put("user", sceneService.getById(sceneId).getUser());

        // 判断是否收藏场景图
        map.put("isCollect", collectionService.isCollectionScene(userId, sceneId) ? "Y" : "N");

        // 判断是否点赞场景图
        map.put("isPraise", praiseService.isPraise(userId, sceneId, Constant.PRAISE_TYPE_SCENE) ? "Y" : "N");

        if (userId == null) {
            map.put("isAttention", "N");
        } else {
            // 判断是否关注该场景图的作者
            map.put("isAttention", null != attentionService.iCheckUser(sceneService.getById(sceneId).getUser(), userService.getById(userId)) ? "Y" : "N");
        }

        Result obj = new Result(true).data(map);
        String result = JsonUtil.obj2ApiJson(obj);
        WebUtil.printApi(response, result);
    }

    /**
     * 查询场景中的商品，包括商品位置信息等
     */
    @RequestMapping(value = "/sceneGoods")
    public void sceneGoods(HttpServletRequest request,
                           HttpServletResponse response,
                           Integer sceneId) {
        // 场景中的商品
        List<SceneGoods> list = sceneGoodsService.listBySceneId(sceneId);

        Result obj = new Result(true).data(list);
        String result = JsonUtil.obj2ApiJson(obj, "user");
        WebUtil.printApi(response, result);
    }

    /**
     * 新增场景图
     *
     * @param request
     * @param response
     * @param userId      用户id
     * @param name        场景名称
     * @param spaceTagIds 场景空间类型
     * @param styleTagIds 场景风格类型
     * @param info        描述信息
     * @param isShow      是否公开 yes/no
     * @param file        场景图片
     * @param goodsIds    商品id，多个@隔开
     * @param positions   商品在场景上的位置，多个@隔开，跟商品id的index要对应
     */
    @RequestMapping(value = "/addSceneInfo")
    public void addSceneInfo(HttpServletRequest request,
                             HttpServletResponse response,
                             Integer userId,
                             String name,
                             String spaceTagIds,
                             String styleTagIds,
                             String info,
                             String isShow,
                             @RequestParam(required = false) MultipartFile file,
                             String goodsIds,
                             String positions) {
        String image = "";
        String thumbnailImage = "";
        if (file != null) {
            image = UploadUtils.uploadMultipartFile(file);
            thumbnailImage = uploadImageService.uploadThumbImage2Qiniu(file);
        } else {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        sceneService.addScene(userId, name, spaceTagIds, styleTagIds, info, isShow, image,thumbnailImage, goodsIds, positions);
        WebUtil.printApi(response, new Result(true));
    }

    /**
     * 修改场景图
     *
     * @param request
     * @param response
     * @param sceneId     场景id
     * @param name        场景名称
     * @param spaceTagIds 场景空间类型
     * @param styleTagIds 场景风格类型
     * @param info        描述信息
     * @param isShow      是否公开 yes/no
     * @param file        场景图片
     * @param goodsIds    商品id，多个@隔开
     * @param positions   商品在场景上的位置，多个@隔开，跟商品id的index要对应
     */
    @RequestMapping(value = "/updateSceneInfo")
    public void updateSceneInfo(HttpServletRequest request,
                                HttpServletResponse response,
                                Integer sceneId,
                                String name,
                                String spaceTagIds,
                                String styleTagIds,
                                String info,
                                String isShow,
                                @RequestParam(required = false) MultipartFile file,
                                String goodsIds,
                                String positions) {
        String image = "";
        if (file != null) {
            image = UploadUtils.uploadMultipartFile(file);
        } else {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        sceneService.updateScene(sceneId, name, spaceTagIds, styleTagIds, info, isShow, image, goodsIds, positions);
        WebUtil.printApi(response, new Result(true));
    }

    // 画板界面图库列表（分页） 每个类型最多显示6条
    // 数据逻辑：先查出所以空间标签，根据标签去查询我发布的场景图，如果不满6条就再去查询我收藏的场景图
    // by gaolei
    @RequestMapping(value = "/findScenes")
    public void findScenes(HttpServletRequest request,
                           HttpServletResponse response,
                           Integer userId) {
        //传输数据总Map
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        //获取所有空间风格
        List<SpaceTag> spaceTagList = spaceTagService.findAll();
        for (SpaceTag spaceTag : spaceTagList) {
            // 查询我发布的场景图列表
            List<Scene> list = sceneService.iListMy(userId, spaceTag.getId() + "");
            if (list == null || list.size() == 0) {
                list = new ArrayList<Scene>();
            }

            List<Scene> arrayList = new ArrayList<Scene>(list);

            // 不足6条的继续查询我收藏的
            Integer num = 6 - arrayList.size();
            if(num > 0){
                Page<CollectionScene> colScenePage = collectionSceneService.pageCollectionScene(1, num, userId, spaceTag.getId()+"",null);
                for (CollectionScene colScene : colScenePage.getContent()) {
                    Scene scene = colScene.getScene();
                    arrayList.add(scene);
                }
            }

            for (Scene scene : arrayList){
                if(scene.getThumbnailImage() == null){
                    scene.setThumbnailImage(scene.getImage());
                }
            }

            Map<String, Object>  map = new HashMap<String, Object>();
            map.put("spaceTagId", spaceTag.getId());
            map.put("spaceTagName", spaceTag.getName());
            map.put("sceneList", arrayList);
            maps.add(map);
        }

        Result obj = new Result(true).data(maps);
        String result = JsonUtil.obj2ApiJson(obj, "user", "isRecommend", "recommendTime", "createTime", "isCollection", "isPraise", "spaceTagName", "spaceTagIds", "styleTagIds", "isShow");
        WebUtil.printApi(response, result);
    }

    // 我收藏的场景图
    // 画板里面查询场景图用到
    // by gaolei
    @RequestMapping(value = "/findMyCollectionScenesList")
    public void findMyCollectionScenesList(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Integer pageNum,
                                 Integer pageSize,
                                 Integer userId, String name, Integer spaceTagId) {
        String spaceTagIdStr = "";
        if(spaceTagId != null){
            spaceTagIdStr = spaceTagId+"";
        }
        Page<CollectionScene> colScenePage = collectionSceneService.pageCollectionScene(pageNum, pageSize, userId, spaceTagIdStr, name);
        List<Scene> sceneList = new ArrayList<Scene>();
        for (CollectionScene colScene : colScenePage.getContent()) {
            sceneList.add(colScene.getScene());
        }

        for (Scene scene : sceneList){
            if(scene.getThumbnailImage() == null){
                scene.setThumbnailImage(scene.getImage());
            }
        }

        Map<String, Object> dataMap = APIFactory.fitting(colScenePage,sceneList);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj,"user");
        WebUtil.printApi(response, result);
    }

}
