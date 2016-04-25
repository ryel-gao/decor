package com.bluemobi.decor.portal.controller.api;


import com.bluemobi.decor.core.ERROR_CODE;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.CollectionMaterial;
import com.bluemobi.decor.entity.Material;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.UploadUtils;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.CollectionMaterialService;
import com.bluemobi.decor.service.MaterialService;
import com.bluemobi.decor.service.UserService;
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
 * 素材
 * Created by gaoll on 2015/3/12.
 */
@Controller
@RequestMapping("api/material")
public class MaterialApi extends CommonController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private UserService userService;

    @Autowired
    private CollectionMaterialService collectionMaterialService;

    // 全部素材
    @RequestMapping(value = "/all")
    public void all(HttpServletRequest request,
                    HttpServletResponse response,
                    Integer pageNum,
                    Integer pageSize,
                    Integer userId,
                    String name,
                    Integer kindTagId,
                    Integer spaceTagId,
                    Integer styleTagId,
                    String sort) {
        Page<Material> page = materialService.iPageAll(pageNum, pageSize, name, kindTagId, spaceTagId, styleTagId, sort);
        for (Material material : page.getContent()) {
            // 是否收藏
            Boolean isCollection = collectionMaterialService.isCollectionMaterial(userId, material.getId());
            if (isCollection) {
                material.setIsCollection("yes");
            } else {
                material.setIsCollection("no");
            }
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "goods");
        WebUtil.printApi(response, result);
    }

    // 我发布的商品
    @RequestMapping(value = "/my")
    public void my(HttpServletRequest request,
                   HttpServletResponse response,
                   Integer pageNum,
                   Integer pageSize,
                   Integer userId) {
        Page<Material> page = materialService.iPageMy(pageNum, pageSize, userId);
        Map<String, Object> dataMap = APIFactory.fitting(page);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj);
        WebUtil.printApi(response, result);
    }

    // 我的
    @RequestMapping(value = "/myCollection")
    public void myCollection(HttpServletRequest request,
                             HttpServletResponse response,
                             Integer pageNum,
                             Integer pageSize,
                             Integer userId) {
        Page<Material> page = materialService.iPageMy(pageNum, pageSize, userId);
        Map<String, Object> dataMap = APIFactory.fitting(page);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj);
        WebUtil.printApi(response, result);
    }

    // 素材图列表
    @RequestMapping(value = "/findMaterialsList")
    public void findMaterialsList(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Integer pageNum,
                                  Integer pageSize,
                                  Integer userId,
                                  Integer typeId,
                                  Integer spaceId,
                                  Integer styleId,
                                  String sort,
                                  String name) {
        if (null == sort || null == userId || null == pageNum || null == pageSize) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        Page<Material> page = materialService.iPageWithUser(pageNum, pageSize, typeId, spaceId, styleId, sort,name);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> imagePlus = null;

        for (Material material : page.getContent()) {
            imagePlus = new HashMap<String, Object>();
            imagePlus.put("id", material.getId());
            imagePlus.put("path", material.getImage());

            if (collectionMaterialService.isCollectionMaterial(userId, material.getId())) {
                imagePlus.put("isCollect", "Y");
            } else {
                imagePlus.put("isCollect", "N");
            }

            list.add(imagePlus);
        }

        Map<String, Object> dataMap = APIFactory.fitting(page, list);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "name");
        WebUtil.printApi(response, result);
    }

    // 我（发布）的素材图列表
    @RequestMapping(value = "/findMyMaterialsList")
    public void findMyMaterialsList(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Integer pageNum,
                                    Integer pageSize,
                                    Integer userId) {
        if (null == userId || null == pageNum || null == pageSize) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        Page<Material> page = materialService.iPageMy(pageNum, pageSize, userId);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> imagePlus = null;

        for (Material material : page.getContent()) {
            imagePlus = new HashMap<String, Object>();
            imagePlus.put("id", material.getId());
            imagePlus.put("path", material.getImage());

            list.add(imagePlus);
        }

        Map<String, Object> dataMap = APIFactory.fitting(page, list);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "name", "isCollect");
        WebUtil.printApi(response, result);
    }

    // 我（收藏）的素材图列表
    @RequestMapping(value = "/findMyCollectMaterialsList")
    public void findMyCollectMaterialsList(HttpServletRequest request,
                                           HttpServletResponse response,
                                           Integer pageNum,
                                           Integer pageSize,
                                           Integer userId) {
        if (null == userId || null == pageNum || null == pageSize) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        Page<CollectionMaterial> page = materialService.iPageMyCollection(pageNum, pageSize, userId);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> imagePlus = null;

        for (CollectionMaterial collectionMaterial : page.getContent()) {
            imagePlus = new HashMap<String, Object>();
            imagePlus.put("id", collectionMaterial.getMaterial().getId());
            imagePlus.put("path", collectionMaterial.getMaterial().getImage());

            list.add(imagePlus);
        }

        Map<String, Object> dataMap = APIFactory.fitting(page, list);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "name", "isCollect");
        WebUtil.printApi(response, result);
    }

    // 移除素材图
    @RequestMapping(value = "/delMaterial")
    public void delMaterial(HttpServletRequest request,
                            HttpServletResponse response,
                            Integer userId,
                            String materialList) {
        if (null == userId || null == materialList) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        int[] arrayId = JsonUtil.json2Obj(materialList, int[].class);
        for (int id : arrayId) {
            Material material = materialService.getById(id);
            if (null == material) {
                WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
                return;
            }

            material = materialService.iCheckMaterialByUser(userService.getById(userId), id);
            if (null == material) {
                WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0019));
                return;
            }

            materialService.deleteById(id);
        }

        String result = JsonUtil.obj2ApiJson(new Result(true));
        WebUtil.printApi(response, result);
    }

    /**
     * 收藏素材图
     * by gaolei
     */
    @RequestMapping(value = "/collectionMaterial")
    public void collectionMaterial(HttpServletRequest request,
                             HttpServletResponse response,
                             Integer userId,
                             Integer materialId){
        collectionMaterialService.collectionMaterial(userId,materialId);
        WebUtil.printApi(response, new Result(true));
    }

    /**
     * 取消收藏素材图
     * by gaolei
     */
    @RequestMapping(value = "/cancelCollectionMaterial")
    public void cancelCollectionMaterial(HttpServletRequest request,
                                   HttpServletResponse response,
                                   Integer userId,
                                   Integer materialId){
        collectionMaterialService.cancelCollectionMaterial(userId,materialId);
        WebUtil.printApi(response, new Result(true));
    }
}
