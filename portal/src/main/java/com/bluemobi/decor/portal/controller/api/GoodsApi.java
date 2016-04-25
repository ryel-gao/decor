package com.bluemobi.decor.portal.controller.api;


import com.bluemobi.decor.core.ERROR_CODE;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.Goods;
import com.bluemobi.decor.entity.GoodsImage;
import com.bluemobi.decor.entity.Scene;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.UploadUtils;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.*;
import com.bluemobi.decor.utils.APIFactory;
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
 * 商品
 * Created by gaoll on 2015/3/12.
 */
@Controller
@RequestMapping("api/goods")
public class GoodsApi extends CommonController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private SceneGoodsService sceneGoodsService;

    @Autowired
    private GoodsImageService goodsImageService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private AttentionService attentionService;

    @Autowired
    private UserService userService;

    @Autowired
    private UploadImageService uploadImageService;

    // 商品列表查询
    @RequestMapping(value = "/findGoodsList")
    public void findGoodsList(HttpServletRequest request,
                              HttpServletResponse response,
                              Integer pageNum,
                              Integer pageSize,
                              Integer userId,
                              String name,
                              Integer kindTagId,
                              Integer spaceTagId,
                              Integer styleTagId,
                              String sort) {
        Page<Goods> page = goodsService.iPage(pageNum, pageSize, name, kindTagId, spaceTagId, styleTagId, sort);
        for (Goods goods : page.getContent()) {
            List<GoodsImage> goodsImageList = goodsImageService.listByGoodsId(goods.getId());
            for(GoodsImage goodsImage : goodsImageList){
                if(goodsImage.getThumbnailImage() == null){
                    goodsImage.setThumbnailImage(goodsImage.getImage());
                }
            }
            goods.setGoodsImageList(goodsImageList);

            if(goods.getThumbnailCover() == null){
                goods.setThumbnailCover(goods.getCover());
            }

            // 是否收藏
            Boolean isCollection = collectionService.isCollectionGoods(userId, goods.getId());
            if (isCollection) {
                goods.setIsCollection("yes");
            } else {
                goods.setIsCollection("no");
            }
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "goods");
        WebUtil.printApi(response, result);
    }

    // 我发布的商品
    @RequestMapping(value = "/findMyGoodsList")
    public void findMyGoodsList(HttpServletRequest request,
                                HttpServletResponse response,
                                Integer pageNum,
                                Integer pageSize,
                                Integer userId) {
        Page<Goods> page = goodsService.iPageMy(pageNum, pageSize, userId);
        for (Goods goods : page.getContent()) {
            List<GoodsImage> goodsImageList = goodsImageService.listByGoodsId(goods.getId());
            for(GoodsImage goodsImage : goodsImageList){
                if(goodsImage.getThumbnailImage() == null){
                    goodsImage.setThumbnailImage(goodsImage.getImage());
                }
            }
            goods.setGoodsImageList(goodsImageList);
            if(goods.getThumbnailCover() == null){
                goods.setThumbnailCover(goods.getCover());
            }
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj);
        WebUtil.printApi(response, result);
    }

    /**
     * 商品详情接口
     *
     * @param request
     * @param response
     * @param goodsId
     */
    @RequestMapping(value = "/detail")
    public void detail(HttpServletRequest request,
                       HttpServletResponse response,
                       Integer goodsId,
                       Integer userId) {
        if (null == goodsId) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        Goods goods = goodsService.getById(goodsId);
        List<GoodsImage> goodsImageList = goodsImageService.listByGoodsId(goodsId);
        if (null != goodsImageList && goodsImageList.size() > 0) {
            for(GoodsImage goodsImage : goodsImageList){
                if(goodsImage.getThumbnailImage() == null){
                    goodsImage.setThumbnailImage(goodsImage.getImage());
                }
            }
            goods.setGoodsImageList(goodsImageList);
        }
        if(goods.getThumbnailCover() == null){
            goods.setThumbnailCover(goods.getCover());
        }

        // 调用商品的场景图
        List<Scene> sceneList = sceneGoodsService.listSceneByGoodsId(goodsId);
        for(Scene scene : sceneList){
            if(scene.getThumbnailImage() == null){
                scene.setThumbnailImage(scene.getImage());
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sceneList", sceneList);
        map.put("goodInfo", goods);
        if (null != userId) {
            map.put("isAttention", null != attentionService.iCheckUser(goodsService.getById(goodsId).getUser(), userService.getById(userId)) ? "Y" : "N");
            goods.setIsCollection(collectionService.isCollectionGoods(userId, goodsId) ? "yes" : "no");
        }
        Result obj = new Result(true).data(map);
        String result = JsonUtil.obj2ApiJson(obj, "password", "status", "isRecommend", "recommendTime", "roleType", "brevitycode");
        WebUtil.printApi(response, result);
    }

    /**
     * 新增商品图
     *
     * @param request
     * @param response
     * @param userId      用户id
     * @param name        商品名称
     * @param kindTagIds  种类
     * @param spaceTagIds 空间类型，如果有多个，中间用逗号隔开
     * @param styleTagIds 风格类型，如果有多个，中间用逗号隔开
     * @param price       价格
     * @param size        尺寸
     * @param texture     材质
     * @param link        链接
     * @param info        描述信息
     * @param files       图片文件
     * @param isHeads     每个文件是否设置为封面，是=yes，否=no，多个中间用逗号隔开
     * @param isTurns     每个文件是否转为素材，是=yes，否=no，多个中间用逗号隔开
     */
    @RequestMapping(value = "/addGoodsInfo")
    public void addGoodsInfo(HttpServletRequest request,
                             HttpServletResponse response,
                             Integer userId,
                             String name,
                             String kindTagIds,
                             String spaceTagIds,
                             String styleTagIds,
                             String price,
                             String size,
                             String texture,
                             String link,
                             String info,
                             @RequestParam(required = false) MultipartFile[] files,
                             String isHeads,
                             String isTurns) {
        List<String> imageList = new ArrayList<String>();
        List<String> thumbnailImageList = new ArrayList<String>();
        for (MultipartFile file : files) {
            if (null != file) {
                String iPath = UploadUtils.uploadMultipartFile(file);
                imageList.add(iPath);
                String thumbnailPath = uploadImageService.uploadThumbImage2Qiniu(file);
                if(thumbnailPath==null){
                    thumbnailImageList.add(iPath);
                }else {
                    thumbnailImageList.add(thumbnailPath);
                }
            }
        }
        String images = "";
        String thumbnailImages = "";
        if (imageList != null && imageList.size() > 0) {
            for (int i = 0; i < imageList.size(); i++) {
                if (i != 0) {
                    images += ",";
                    thumbnailImages += ",";
                }
                images += imageList.get(i);
                thumbnailImages += thumbnailImageList.get(i);
            }
        }

        goodsService.addGoods(userId, name, kindTagIds, spaceTagIds, styleTagIds, price, size, texture, link, info, images,thumbnailImages, isHeads, isTurns);
        WebUtil.printApi(response, new Result(true));
    }
}
