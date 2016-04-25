package com.bluemobi.decor.portal.controller.api;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.core.ERROR_CODE;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.*;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.*;
import com.bluemobi.decor.utils.APIFactory;
import com.bluemobi.decor.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收藏
 * Created by tuyh on 2015/7/9.
 */
@Controller
@RequestMapping("api/collect")
public class CollectApi {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SceneService sceneService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private SeriesSceneService seriesSceneService;

    @Autowired
    private GoodsImageService goodsImageService;

    /**
     * 收藏 or 取消收藏
     */
    @RequestMapping("/addOrDelete")
    public void addOrDelete(HttpServletRequest request,
                            HttpServletResponse response,
                            Integer userId,
                            Integer type,
                            Integer favoriteId,
                            Integer objectId,
                            String objectType) {
        if (null == type || null == favoriteId || null == userId || null == objectId || null == objectType) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        Favorite favorite = favoriteService.getById(favoriteId);
        if (null == favorite) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0016));
            return;
        }

        // 类型
        // 1表示收藏
        // 2表示取消收藏
        Collection collection = collectionService.iFindByUserAndObjectId(favorite, objectId, objectType);
        if (type == 1) {
            if (null != collection) {
                WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0017));
                return;
            }
            Collection collectionTemp = new Collection();
            collectionTemp.setObjectType(objectType);
            collectionTemp.setObjectId(objectId);
            collectionTemp.setFavorite(favorite);
            collectionService.create(collectionTemp);

            // 收藏成功后，被收藏对象的收藏数量加1
            if (objectType.equals(Constant.COLLECTION_TYPE_GOODS)) {
                Goods goods = goodsService.getById(objectId);
                goods.setCollectionNum(goods.getCollectionNum() + 1);
                goodsService.update(goods);
            }
            if (objectType.equals(Constant.COLLECTION_TYPE_SCENE)) {
                Scene scene = sceneService.getById(objectId);
                scene.setCollectionNum(scene.getCollectionNum() + 1);
                sceneService.update(scene);
            }
            if (objectType.equals(Constant.COLLECTION_TYPE_SERIES)) {
                Series series = seriesService.getById(objectId);
                series.setCollectionNum(series.getCollectionNum() + 1);
                seriesService.update(series);
            }
        } else {
            if (null == collection) {
                WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
                return;
            }
            collectionService.deleteById(collection.getId());

            // 取消收藏成功后，被收藏对象的收藏数量减1
            if (objectType.equals(Constant.COLLECTION_TYPE_GOODS)) {
                Goods goods = goodsService.getById(objectId);
                goods.setCollectionNum(goods.getCollectionNum() > 0 ? goods.getCollectionNum() - 1 : 0);
                goodsService.update(goods);
            }
            if (objectType.equals(Constant.COLLECTION_TYPE_SCENE)) {
                Scene scene = sceneService.getById(objectId);
                scene.setCollectionNum(scene.getCollectionNum() > 0 ? scene.getCollectionNum() - 1 : 0);
                sceneService.update(scene);
            }
            if (objectType.equals(Constant.COLLECTION_TYPE_SERIES)) {
                Series series = seriesService.getById(objectId);
                series.setCollectionNum(series.getCollectionNum() > 0 ? series.getCollectionNum() - 1 : 0);
                seriesService.update(series);
            }
        }

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * 判断是否收藏
     */
    @RequestMapping("/check")
    public void check(HttpServletRequest request,
                      HttpServletResponse response,
                      Integer userId,
                      Integer favoriteId,
                      Integer objectId,
                      String objectType) {
        if (null == favoriteId || null == userId || null == objectId || null == objectType) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        Favorite favorite = favoriteService.getById(favoriteId);
        if (null == favorite) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0016));
            return;
        }

        // 查询收藏记录，如果有记录，则表示已收藏；否则表示没有收藏
        Collection collection = collectionService.iFindByUserAndObjectId(favorite, objectId, objectType);
        Map<String, Object> map = new HashMap<String, Object>();
        if (null == collection) {
            map.put("status", "N");
        } else {
            map.put("status", "Y");
        }

        WebUtil.printApi(response, new Result(true).data(map));
    }

    // 我的收藏（分页）
    @RequestMapping(value = "/findMyCollection")
    public void findMyCollection(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Integer pageNum,
                                 Integer pageSize,
                                 Integer userId,
                                 Integer favoriteId,
                                 String type) {
        if (null == favoriteId || null == userId || null == pageNum || null == pageSize) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        // 判断收藏夹是否是对应用户的个人收藏夹
        Boolean flag = true;
        List<Favorite> list = favoriteService.iFindAllCollects(userService.getById(userId));
        for (Favorite favorite : list) {
            if ((favorite.getId() + "").contains(favoriteId + "")) {
                flag = false;
            }
        }

        if (flag) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0016));
            return;
        }

        Page<Collection> page = collectionService.iFindCollectsPageByFavorite(favoriteId, type, pageNum, pageSize);
        List<CollectInfo> collectInfoList = new ArrayList<CollectInfo>();
        String tempImage = null;

        for (Collection collection : page.getContent()) {
            if (collection.getObjectType().equals(Constant.COLLECTION_TYPE_GOODS)) {
                // 当objectType=goods时，代表收藏的是商品图
                Goods goods = goodsService.getById(collection.getObjectId());
                if(goods == null){
                    collectionService.deleteById(collection.getId());
                    continue;
                }
                if (null == goods || goods.getGoodsImageList().size() == 0) {
                    continue;
                }
                tempImage = goods.getGoodsImageList().get(0).getImage();

                CollectInfo collectInfo = new CollectInfo();
                collectInfo.setId(collection.getId());
                collectInfo.setObjectId(goods.getId());
                collectInfo.setUserId(goods.getUser().getId());
                collectInfo.setImage(tempImage);
                collectInfo.setContent(goods.getInfo());

                collectInfoList.add(collectInfo);
            } else if (collection.getObjectType().equals(Constant.COLLECTION_TYPE_SCENE)) {
                // 当objectType=scene时，代表收藏的是场景图
                Scene scene = sceneService.getById(collection.getObjectId());
                if (null == scene || null == scene.getImage()) {
                    collectionService.deleteById(collection.getId());
                    continue;
                }
                tempImage = scene.getImage();

                CollectInfo collectInfo = new CollectInfo();
                collectInfo.setId(collection.getId());
                collectInfo.setObjectId(scene.getId());
                collectInfo.setUserId(scene.getUser().getId());
                collectInfo.setImage(tempImage);
                collectInfo.setContent(scene.getInfo());

                collectInfoList.add(collectInfo);
            } else if (collection.getObjectType().equals(Constant.COLLECTION_TYPE_SERIES)) {
                // 当objectType=series时，代表收藏的是系列图
                Series series = seriesService.getById(collection.getObjectId());
                if(series == null){
                    collectionService.deleteById(collection.getId());
                    continue;
                }
                tempImage = series.getCover();

                CollectInfo collectInfo = new CollectInfo();
                collectInfo.setId(collection.getId());
                collectInfo.setObjectId(series.getId());
                collectInfo.setUserId(series.getUser().getId());
                collectInfo.setImage(tempImage);
                collectInfo.setContent(series.getInfo());

                collectInfoList.add(collectInfo);
            }
        }

        Map<String, Object> dataMap = APIFactory.fitting(page, collectInfoList);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "favorite", "objectType");
        WebUtil.printApi(response, result);
    }

    // 根据收藏夹ID查询收藏夹信息（分页）
    @RequestMapping(value = "/findFavoriteInfo")
    public void findFavoriteInfo(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Integer pageNum,
                                 Integer pageSize,
                                 Integer userId,
                                 Integer favoriteId) {
        if (null == favoriteId || null == userId || null == pageNum || null == pageSize) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        // 判断收藏夹是否是对应用户的个人收藏夹
        Boolean flag = true;
        List<Favorite> list = favoriteService.iFindAllCollects(userService.getById(userId));
        for (Favorite favorite : list) {
            if ((favorite.getId() + "").contains(favoriteId + "")) {
                flag = false;
            }
        }

        if (flag) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0016));
            return;
        }

        Page<Collection> page = collectionService.iFindCollectsPageByFavorite(favoriteId, null, pageNum, pageSize);
        List<CollectInfo> collectInfoList = new ArrayList<CollectInfo>();
        String tempImage = null;

        for (Collection collection : page.getContent()) {
            if (collection.getObjectType().equals(Constant.COLLECTION_TYPE_GOODS)) {
                // 当objectType=goods时，代表收藏的是商品图
                Goods goods = goodsService.getById(collection.getObjectId());
                if(goods == null){
                    collectionService.deleteById(collection.getId());
                    continue;
                }
                List<GoodsImage> listt = goodsImageService.listByGoodsId(goods.getId());
                if (null == listt || listt.size() == 0) {
                    continue;
                }
                tempImage = listt.get(0).getImage();

                CollectInfo collectInfo = new CollectInfo();
                collectInfo.setId(collection.getId());
                collectInfo.setObjectId(goods.getId());
                collectInfo.setUserId(goods.getUser().getId());
                collectInfo.setImage(tempImage);
                collectInfo.setContent(goods.getInfo());
                collectInfo.setType(Constant.COLLECTION_TYPE_GOODS);

                collectInfoList.add(collectInfo);
            } else if (collection.getObjectType().equals(Constant.COLLECTION_TYPE_SCENE)) {
                // 当objectType=scene时，代表收藏的是场景图
                Scene scene = sceneService.getById(collection.getObjectId());
                if (null == scene || null == scene.getImage()) {
                    collectionService.deleteById(collection.getId());
                    continue;
                }
                tempImage = scene.getImage();

                CollectInfo collectInfo = new CollectInfo();
                collectInfo.setId(collection.getId());
                collectInfo.setObjectId(scene.getId());
                collectInfo.setUserId(scene.getUser().getId());
                collectInfo.setImage(tempImage);
                collectInfo.setContent(scene.getInfo());
                collectInfo.setType(Constant.COLLECTION_TYPE_SCENE);

                collectInfoList.add(collectInfo);
            } else if (collection.getObjectType().equals(Constant.COLLECTION_TYPE_SERIES)) {
                // 当objectType=series时，代表收藏的是系列图
                Series series = seriesService.getById(collection.getObjectId());
                if(series == null){
                    collectionService.deleteById(collection.getId());
                    continue;
                }

                tempImage = series.getCover();

                CollectInfo collectInfo = new CollectInfo();
                collectInfo.setId(collection.getId());
                collectInfo.setObjectId(series.getId());
                collectInfo.setUserId(series.getUser().getId());
                collectInfo.setImage(tempImage);
                collectInfo.setContent(series.getInfo());
                collectInfo.setType(Constant.COLLECTION_TYPE_SERIES);

                collectInfoList.add(collectInfo);
            }
        }

        Map<String, Object> dataMap = APIFactory.fitting(page, collectInfoList);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "favorite", "objectType");
        WebUtil.printApi(response, result);
    }
}
