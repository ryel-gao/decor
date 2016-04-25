package com.bluemobi.decor.portal.controller.background;

import com.bluemobi.decor.common.factory.DataTableFactory;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.*;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.SessionUtils;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.*;
import com.bluemobi.decor.utils.ComFun;
import com.bluemobi.decor.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 素材Controller
 * Created by reny on 2015/7/21.
 */
@Controller
@RequestMapping(value = "/backend/material")
public class MaterialController extends CommonController {
    @Autowired
    private MaterialService materialService;
    @Autowired
    private GoodsImageService goodsImageService;
    @Autowired
    private KindTagService kindTagService;
    @Autowired
    private SpaceTagService spaceTagService;
    @Autowired
    private StyleTagService styleTagService;

    @RequestMapping(value = "/index")
    public String index() {
        return "素材图片表";
    }

    /**
     * 分页获取素材图片列表
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer draw,
                     Integer start,
                     Integer length,
                     Integer id, String spaceId,
                     String styleId, String tagtagId,
                     ModelMap model) {

        try {
            int pageNum = getPageNum(start, length);
            Page<GoodsImage> page = goodsImageService.findMaterial(id, styleId, spaceId, tagtagId, pageNum, length);
            for (GoodsImage goodsImage : page.getContent()) {
                Material material = goodsImage.getMaterial();
                if (material != null) {
                    KindTag kindTag = null;
                    if (StringUtils.isNotEmpty(material.getKindTagIds())) {
                        Integer kTagId = Integer.parseInt(ComFun.tagsThin(material.getKindTagIds()));
                        kindTag = kindTagService.getById(kTagId);
                        material.setKindTagId(kTagId);
                    }
                    if (kindTag != null) {
                        material.setKindTagName(kindTag.getName());
                    } else {
                        material.setKindTagName("暂无数据");
                    }

                    //空间标签名称的集合
                    List<String> spaceTagNames = new ArrayList<String>();
                    //获取空间标签
                    String spTagIdsT = material.getSpaceTagIds();
                    if (StringUtils.isNotEmpty(spTagIdsT)) {
                        //把@1@,@2@,@3@类型的ids转换成1,2,3
                        List<Integer> tagIds = ComFun.tagsToList(material.getSpaceTagIds());
                        List<Map<String, Object>> spaceMap = new ArrayList<Map<String, Object>>();
                        //循环给tagNames集合添加标签名称
                        for (Integer tagId : tagIds) {
                            SpaceTag spaceTag = spaceTagService.getById(tagId);
                            Map<String, Object> m = new HashMap<String, Object>();
                            m.put("id", tagId);
                            if (spaceTag != null) {
                                spaceTagNames.add(spaceTag.getName());
                                m.put("name", spaceTag.getName());
                            } else {
                                spaceTagNames.add("数据问题");
                                m.put("name", "数据问题");
                            }
                            m.put("type", "spaceLx");
                            spaceMap.add(m);
                        }
                        material.setSpaceTagMap(spaceMap);
                    } else {
                        spaceTagNames.add("暂无空间标签");
                    }
                    material.setSpaceTagName(spaceTagNames);

                    //风格标签名称的集合
                    List<String> styleTagNames = new ArrayList<String>();
                    //获取空间标签
                    String stTagIdsT = material.getStyleTagIds();
                    if (StringUtils.isNotEmpty(stTagIdsT)) {
                        //把@1@,@2@,@3@类型的ids转换成1,2,3
                        List<Integer> tagIds = ComFun.tagsToList(material.getStyleTagIds());
                        List<Map<String, Object>> styleMap = new ArrayList<Map<String, Object>>();
                        //循环给tagNames集合添加标签名称
                        for (Integer tagId : tagIds) {
                            StyleTag styleTag = styleTagService.getById(tagId);
                            Map<String, Object> m = new HashMap<String, Object>();
                            m.put("id", tagId);
                            if (styleTag != null) {
                                styleTagNames.add(styleTag.getName());
                                m.put("name", styleTag.getName());
                            } else {
                                styleTagNames.add("数据问题");
                                m.put("name", "数据问题");
                            }
                            m.put("type", "styleLx");
                            styleMap.add(m);
                        }
                        material.setStyleTagMap(styleMap);
                    } else {
                        styleTagNames.add("暂无风格标签");
                    }
                    material.setStyleTagName(styleTagNames);

                }

                Goods goods = goodsImage.getGoods();
                if (goods != null) {
                    KindTag kindTag = null;
                    if (StringUtils.isNotEmpty(goods.getKindTagIds())) {
                        kindTag = kindTagService.getById(Integer.parseInt(ComFun.tagsThin(goods.getKindTagIds())));
                    }
                    if (kindTag != null) {
                        goods.setKindTagName(kindTag.getName());
                    } else {
                        goods.setKindTagName("暂无数据");
                    }

                    //空间标签名称的集合
                    List<String> spaceTagNames = new ArrayList<String>();
                    //获取空间标签
                    String spTagIdsT = goods.getSpaceTagIds();
                    if (StringUtils.isNotEmpty(spTagIdsT)) {
                        //把@1@,@2@,@3@类型的ids转换成1,2,3
                        List<Integer> tagIds = ComFun.tagsToList(goods.getSpaceTagIds());
                        //循环给tagNames集合添加标签名称
                        for (Integer tagId : tagIds) {
                            SpaceTag spaceTag = spaceTagService.getById(tagId);
                            if (spaceTag != null) {
                                spaceTagNames.add(spaceTag.getName());
                            } else {
                                spaceTagNames.add("数据问题");
                            }
                        }
                    } else {
                        spaceTagNames.add("暂无空间标签");
                    }
                    goods.setSpaceTagName(spaceTagNames);

                    //风格标签名称的集合
                    List<String> styleTagNames = new ArrayList<String>();
                    //获取空间标签
                    String stTagIdsT = goods.getStyleTagIds();
                    if (StringUtils.isNotEmpty(stTagIdsT)) {
                        //把@1@,@2@,@3@类型的ids转换成1,2,3
                        List<Integer> tagIds = ComFun.tagsToList(goods.getStyleTagIds());
                        //循环给tagNames集合添加标签名称
                        for (Integer tagId : tagIds) {
                            StyleTag styleTag = styleTagService.getById(tagId);
                            if (styleTag != null) {
                                styleTagNames.add(styleTag.getName());
                            } else {
                                styleTagNames.add("数据问题");
                            }
                        }
                    } else {
                        styleTagNames.add("暂无风格标签");
                    }
                    goods.setStyleTagName(styleTagNames);


                }
            }
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除单个素材图片
     */
    @RequestMapping(value = "/delete")
    public void delete(HttpServletRequest request,
                       HttpServletResponse response,
                       Integer id) {
        try {
            materialService.deleteMaterial(id);
            WebUtil.print(response, new Result(true).msg("操作成功！"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败！"));
        }
    }

    /**
     * 删除多个素材图片
     */
    @RequestMapping(value = "/deletes")
    public void deletes(HttpServletRequest request,
                        HttpServletResponse response,
                        String ids) {
        try {
            int[] arrayId = JsonUtil.json2Obj(ids, int[].class);
            materialService.deleteMaterial(arrayId);
            WebUtil.print(response, new Result(true).msg("操作成功！"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败！"));
        }
    }

    /**
     * 上传素材图
     */
    @RequestMapping(value = "/save")
    public void save(HttpServletRequest request,
                     HttpServletResponse response,
                     String image, String kingTag,
                     Integer goodsImageId, String spaceTagIds,
                     String styleTagIds) {
        Integer userId = null == SessionUtils.getCurrentUser() ? 0 : SessionUtils.getCurrentUser().getId();
        if (userId == 0) {
            WebUtil.print(response, new Result(false).msg("暂未登录！"));
            return;
        }
        if (StringUtils.isEmpty(image)) {
            WebUtil.print(response, new Result(false).msg("请选择一张图片！"));
            return;
        }
        if (goodsImageId == null) {
            if (StringUtils.isEmpty(kingTag)) {
                WebUtil.print(response, new Result(false).msg("请选择一个种类！"));
                return;
            }
        }

        try {
            materialService.insertMaterial(userId, image, goodsImageId, kingTag, spaceTagIds, styleTagIds);
            WebUtil.print(response, new Result(true).msg("操作成功！"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败！"));
        }
    }

    /**
     * 编辑素材图
     */
    @RequestMapping(value = "/updata")
    public void updata(HttpServletRequest request,
                       HttpServletResponse response,
                       String image, String kingTag,
                       Integer updateMaterialId, String spaceTagIds,
                       String styleTagIds) {
        Integer userId = null == SessionUtils.getCurrentUser() ? 0 : SessionUtils.getCurrentUser().getId();
        if (userId == 0) {
            WebUtil.print(response, new Result(false).msg("暂未登录！"));
            return;
        }

        if (updateMaterialId == null) {
            WebUtil.print(response, new Result(false).msg("请输入正确参数！"));
            return;
        }
        if (StringUtils.isEmpty(image)) {
            WebUtil.print(response, new Result(false).msg("请上传一张图片！"));
            return;
        }

        if (StringUtils.isEmpty(kingTag)) {
            WebUtil.print(response, new Result(false).msg("请选择一个种类！"));
            return;
        }

        if (StringUtils.isEmpty(spaceTagIds)) {
            WebUtil.print(response, new Result(false).msg("请选择一个空间类型！"));
            return;
        }

        if (StringUtils.isEmpty(styleTagIds)) {
            WebUtil.print(response, new Result(false).msg("请选择一个风格类型！"));
            return;
        }

        try {
            materialService.updataMaterial(image, updateMaterialId, kingTag, spaceTagIds, styleTagIds);
            WebUtil.print(response, new Result(true).msg("操作成功！"));
        } catch (Exception e) {
            WebUtil.print(response, new Result(false).msg("操作失败！"));
        }
    }

}
