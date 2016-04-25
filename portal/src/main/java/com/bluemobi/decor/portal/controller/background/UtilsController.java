package com.bluemobi.decor.portal.controller.background;

import com.bluemobi.decor.entity.*;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.SessionUtils;
import com.bluemobi.decor.portal.util.UploadUtils;
import com.bluemobi.decor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共工具
 * Created by tuyh on 2015/7/17
 */
@Controller
@RequestMapping(value = "/backend/utils")
public class UtilsController extends CommonController {
    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private SeriesTagService seriesTagService;

    @Autowired
    private KindTagService kindTagService;

    @Autowired
    private SpaceTagService spaceTagService;

    @Autowired
    private StyleTagService styleTagService;

    @Autowired
    private MessageTagService messageTagService;

    @Autowired
    private TUserRoleService tUserRoleService;

    @Autowired
    private TRoleResourceService tRoleResourceService;

    @Autowired
    private TResourceService tResourceService;

    @RequestMapping(value = "/province/list")
    @ResponseBody
    public List<Province> provinceList() {
        try {
            List<Province> list = provinceService.findProvinceList();
            if (null == list || list.size() == 0) {
                Province province = new Province();
                list.add(province);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/city/list")
    @ResponseBody
    public List<City> cityList(Integer provinceId) {
        try {
            List<City> list = cityService.findCityByProvince(provinceService.getById(provinceId));
            if (null == list || list.size() == 0) {
                City city = new City();
                list.add(city);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/kindTag/list")
    @ResponseBody
    public List<KindTag> kindTagList() {
        try {
            List<KindTag> list = kindTagService.findAll();
            if (null == list || list.size() == 0) {
                KindTag kindTag = new KindTag();
                list.add(kindTag);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/seriesTag/list")
    @ResponseBody
    public List<SeriesTag> seriesTagList() {
        try {
            List<SeriesTag> list = seriesTagService.findAll();
            if (null == list || list.size() == 0) {
                SeriesTag series = new SeriesTag();
                list.add(series);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/messageTag/list")
    @ResponseBody
    public List<MessageTag> messageTagList() {
        try {
            List<MessageTag> list = messageTagService.findAll();
            if (null == list || list.size() == 0) {
                MessageTag messageTag = new MessageTag();
                list.add(messageTag);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取空间标签集合
    @RequestMapping(value = "/spaceTag/list")
    @ResponseBody
    public List<SpaceTag> spaceTagList() {
        try {
            List<SpaceTag> list = spaceTagService.findAll();
            if (null == list || list.size() == 0) {
                SpaceTag spaceTag = new SpaceTag();
                list.add(spaceTag);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取空间标签集合(封装)
    @RequestMapping(value = "/spaceTag/mapList")
    @ResponseBody
    public List<Map<String, Object>> mapSpaceTagList() {
        try {
            List<SpaceTag> list = spaceTagService.findAll();
            if (null == list || list.size() == 0) {
                SpaceTag spaceTag = new SpaceTag();
                list.add(spaceTag);
            }
            List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
            for (SpaceTag spaceTag : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", spaceTag.getId());
                map.put("name", spaceTag.getName());
                map.put("type", "spaceLx");
                maps.add(map);
            }

            return maps;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取风格标签集合
    @RequestMapping(value = "/styleTag/list")
    @ResponseBody
    public List<StyleTag> styleTagList() {
        try {
            List<StyleTag> list = styleTagService.findAll();
            if (null == list || list.size() == 0) {
                StyleTag styleTag = new StyleTag();
                list.add(styleTag);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //获取风格标签集合(封装)
    @RequestMapping(value = "/styleTag/mapList")
    @ResponseBody
    public List<Map<String, Object>> mapStyleTagList() {
        try {
            List<StyleTag> list = styleTagService.findAll();
            if (null == list || list.size() == 0) {
                StyleTag styleTag = new StyleTag();
                list.add(styleTag);
            }

            List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
            for (StyleTag styleTag : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", styleTag.getId());
                map.put("name", styleTag.getName());
                map.put("type", "styleLx");
                maps.add(map);
            }

            return maps;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/saveImage")
    @ResponseBody
    public String saveImage(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam(required = false) MultipartFile file) {
        String image = "";
        if (file != null) {
            image = UploadUtils.uploadMultipartFile(file);
        }
        return image;
    }

    @RequestMapping(value = "/deleteImage")
    @ResponseBody
    public int deleteImage(HttpServletRequest request,
                           HttpServletResponse response,
                           String path) {
        if (path != null) {
//            UploadUtils.deleteFile(path);
            return 1;//请求成功
        }
        return 0;//请求失败
    }

    @RequestMapping(value = "/saveFileImage")
    @ResponseBody
    public String saveFileImage(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestParam(required = false) File file) {
        String image = "";
        if (file != null) {
            image = UploadUtils.uploadFile(file);
        }
        return image;
    }

    //获取风格标签和空间标签的id,name,type集合
    @RequestMapping(value = "/styleAndSpaceTag/list")
    @ResponseBody
    public List<Map<String, Object>> styleAndSpaceTagList() {
        try {
            List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();

            List<StyleTag> stList = styleTagService.findAll();
            if (null != stList || stList.size() > 0) {
                for (StyleTag styleTag : stList) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", styleTag.getId());
                    map.put("name", styleTag.getName());
                    map.put("type", "styleLx");
                    maps.add(map);
                }
            }

            List<SpaceTag> spList = spaceTagService.findAll();
            if (null != spList || spList.size() > 0) {
                for (SpaceTag spaceTag : spList) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", spaceTag.getId());
                    map.put("name", spaceTag.getName());
                    map.put("type", "spaceLx");
                    maps.add(map);
                }
            }

            return maps;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //根据用户ID获取对应菜单列表
    @RequestMapping(value = "/home/list")
    @ResponseBody
    public List<Map<String, Object>> home() {
        Integer userId = null == SessionUtils.getCurrentUser() ? 0 : SessionUtils.getCurrentUser().getId();
        List<Map<String, Object>> fMaps = new ArrayList<Map<String, Object>>();
        TUserRole tUserRole = tUserRoleService.findByUserId(userId);
        //如果用户没有权限，则不显示菜单
        if (tUserRole == null) {
            return fMaps;
        }
        if (tUserRole.getRole() == null) {
            return fMaps;
        }
        //有权限则取出权限
        TRole tRole = tUserRole.getRole();
        //取出权限可读取父菜单列表
        List<TResource> fList = tRoleResourceService.resourceList(tRole.getId());
        if (fList != null && fList.size() > 0) {
            Boolean ifUser = false;
            Boolean ifMessage = false;
            Boolean ifGoods = false;

            for (TResource tResource : fList) {
                // 查看是否拥有会员权限
                if (tResource.getId() == 2) {
                    ifUser = true;
                }
                // 查看是否拥有商品图权限
                if (tResource.getId() == 3) {
                    ifGoods = true;
                }
                // 查看是否拥有资讯权限
                if (tResource.getId() == 4) {
                    ifMessage = true;
                }

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", tResource.getName());
                map.put("url", tResource.getUrl());
                map.put("style", tResource.getStyle());
                List<Map<String, Object>> zMaps = new ArrayList<Map<String, Object>>();
                List<TResource> zList = tResourceService.findTResourceByPid(tResource.getId());
                if (zList != null && zList.size() > 0) {
                    for (TResource zTResource : zList) {
                        Map<String, Object> map1 = new HashMap<String, Object>();
                        map1.put("name", zTResource.getName());
                        map1.put("url", zTResource.getUrl());
                        map1.put("style", zTResource.getStyle());
                        zMaps.add(map1);
                    }
                    map.put("zMaps", zMaps);
                }
                fMaps.add(map);
            }

            // 查看是否拥有会员权限
            if (ifUser) {
                SessionUtils.put("userControl", "0");
            } else {
                SessionUtils.put("userControl", "1");
            }
            // 查看是否拥有商品图权限
            if (ifGoods) {
                SessionUtils.put("goodsControl", "0");
            } else {
                SessionUtils.put("goodsControl", "1");
            }
            // 查看是否拥有资讯权限
            if (ifMessage) {
                SessionUtils.put("messageControl", "0");
            } else {
                SessionUtils.put("messageControl", "1");
            }
        } else {
            return fMaps;
        }

        return fMaps;
    }

}