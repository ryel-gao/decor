package com.bluemobi.decor.portal.controller.api;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.core.ERROR_CODE;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.*;
import com.bluemobi.decor.entity.Collection;
import com.bluemobi.decor.portal.cache.CacheService;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.MD5Util;
import com.bluemobi.decor.portal.util.MsgSmsUtil;
import com.bluemobi.decor.portal.util.UploadUtils;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.*;
import com.bluemobi.decor.utils.APIFactory;
import com.bluemobi.decor.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 用户
 * Created by tuyh on 2015/7/6.
 */
@Controller
@RequestMapping("api/user")
public class UsersApi extends CommonController {

    private static String account = "";

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserThirdService userThirdService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CollectionService collectionService;

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

    @Autowired
    private AttentionService attentionService;

    @Autowired
    private MaterialService materialService;

    @Resource(name = "cacheTempCodeServiceImpl")
    private CacheService<String> cacheService;

    /**
     * 用户登录
     *
     * @param request
     * @param response
     */
    @RequestMapping("/login")
    public void login(HttpServletRequest request,
                      HttpServletResponse response,
                      String mobile,
                      String password,
                      String open_id,
                      String type) {
        // 判断是否有openId，如果有，则先通过openId登录，登录失败时调用后台正常登录方法
        if (null != open_id && !open_id.equals("")) {
            // 执行openId登录操作
            UserThird openUser = userThirdService.iFindByOpenId(open_id, type);
            if (null == openUser) {
                // 如果通过openID找不到用户信息，则后台生成一个新用户，同时绑定当前openID，然后返回新生成的用户信息
                User userPlus = new User();
                userPlus.setIsRecommend(Constant.USER_INFO_IsRECOMMEND_NO);
                userPlus.setRoleType(Constant.ROLE_MEMBER);
                userPlus.setStatus(Constant.USER_ENABLE);
                userPlus.setSex(Constant.USER_SEX_UNKNOWN);
                userPlus.setIsShow(Constant.USER_INFO_IsSHOW_YES);
                userPlus.setCreateTime(new Date());
                userPlus.setFans(0);
                userPlus.setOpus(0);
                userPlus.setNickname("第三方用户");
                userPlus.setUserType(Constant.USER_USERTYPE_THIRDPARTY);

                // 随机生成新用户名，u+6位随机数，并且须保证用户名不重复
                User tempMap = userService.iFindByAccount(getRandom(6));
                while (null != tempMap) {
                    tempMap = userService.iFindByAccount(getRandom(6));
                }

                userPlus.setAccount(account);
                userPlus.setPassword(MD5Util.encodeByMD5(account));

                User flagUser = userService.iRegister(userPlus);
                // 判断是否注册成功
                if (null != flagUser) {
                    // 注册成功后，绑定openID，同时返回用户信息
                    // 执行绑定操作
                    userService.bindingOpenId(flagUser, open_id, type);

                    // 返回用户信息
                    flagUser.setIsUsed(0);
                    if (type.equals(Constant.LOGIN_TYPE_WEIXIN)) {
                        flagUser.setWeiXin(Constant.IF_FIRST_LOGIN_YES);
                    } else if (type.equals(Constant.LOGIN_TYPE_QQ)) {
                        flagUser.setQQ(Constant.IF_FIRST_LOGIN_YES);
                    } else if (type.equals(Constant.LOGIN_TYPE_XINLANG)) {
                        flagUser.setXinLang(Constant.IF_FIRST_LOGIN_YES);
                    }
                    flagUser.setIfFirstLogin(Constant.IF_FIRST_LOGIN_YES);
                    String result = JsonUtil.obj2ApiJson(new Result(true).data(createMap("user", flagUser)), "password", "isRecommend", "recommendTime", "roleType", "isShow", "brevitycode");
                    WebUtil.printApi(response, result);
                } else {
                    WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0001));
                    return;
                }
            } else {
                User user = openUser.getUser();
                if (type.equals(Constant.LOGIN_TYPE_WEIXIN)) {
                    user.setWeiXin(Constant.IF_FIRST_LOGIN_YES);
                } else if (type.equals(Constant.LOGIN_TYPE_QQ)) {
                    user.setQQ(Constant.IF_FIRST_LOGIN_YES);
                } else if (type.equals(Constant.LOGIN_TYPE_XINLANG)) {
                    user.setXinLang(Constant.IF_FIRST_LOGIN_YES);
                }
                user.setIfFirstLogin(Constant.IF_FIRST_LOGIN_NO);
                if (user.getStatus().equals(Constant.USER_ENABLE)) {
                    user.setIsUsed(1);
                    String result = JsonUtil.obj2ApiJson(new Result(true).data(createMap("user", user)), "password", "isRecommend", "recommendTime", "roleType", "brevitycode");
                    WebUtil.printApi(response, result);
                    return;
                } else {
                    WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0006));
                    return;
                }
            }
        }

        // 调用后台正常登录方法
        User user = userService.iFindByMobileAndPassword(mobile, password);
        if (null == user) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0005));
            return;
        }

        if (user.getStatus().equals(Constant.USER_UNABLE)) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0006));
            return;
        }

        // 表示没有性别
        if (user.getSex() == null) {
            user.setSex(Constant.USER_SEX_UNKNOWN);
        }

        // 查询第三方绑定信息
        List<UserThird> lists = userThirdService.iFindUserThirdWithUser(user);
        for (UserThird userThird : lists) {
            if (userThird.getType().equals(Constant.LOGIN_TYPE_WEIXIN)) {
                user.setWeiXin(Constant.IF_FIRST_LOGIN_YES);
            } else if (userThird.getType().equals(Constant.LOGIN_TYPE_QQ)) {
                user.setQQ(Constant.IF_FIRST_LOGIN_YES);
            } else if (userThird.getType().equals(Constant.LOGIN_TYPE_XINLANG)) {
                user.setXinLang(Constant.IF_FIRST_LOGIN_YES);
            }
        }

        String result = JsonUtil.obj2ApiJson(new Result(true).data(createMap("user", user)), "password", "isRecommend", "recommendTime", "isLove", "isAttention", "role", "roleType", "brevitycode", "ifFirstLogin");
        WebUtil.printApi(response, result);
    }

    /**
     * 注册接口
     *
     * @param request
     * @param response
     * @param mobile
     * @param password
     */
    @RequestMapping("/register")
    public void register(HttpServletRequest request,
                         HttpServletResponse response,
                         String mobile,
                         String password,
                         String nickname,
                         String content) {
        if (null == mobile || null == password || null == content || StringUtils.isEmpty(nickname)) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        String code = cacheService.get(mobile);

        // 检测验证码是否过期
        if (StringUtils.isEmpty(code)) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0011));
            return;
        }

        // 检测验证码是否正确
        if (!content.equals(code)) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0010));
            return;
        }

        User mobileUser = userService.iFindByMobile(mobile);
        // 手机号存在
        if (mobileUser != null) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0007));
            return;
        }

        User user = new User();
        user.setIsRecommend(Constant.USER_INFO_IsRECOMMEND_NO);
        user.setMobile(mobile);
        user.setAccount(mobile);
        user.setNickname(nickname);
        user.setPassword(password);
        user.setCreateTime(new Date());
        user.setRoleType(Constant.ROLE_MEMBER);
        user.setStatus(Constant.USER_ENABLE);
        user.setSex(Constant.USER_SEX_UNKNOWN);
        user.setIsShow(Constant.USER_INFO_IsSHOW_YES);
        user.setBackgroundImage(Constant.USER_INFO_BACKGROUND_IMAGE);
        user.setOpus(0);
        user.setFans(0);
        user = userService.iRegister(user);
        String result = JsonUtil.obj2ApiJson(new Result(true).data(createMap("user", user)), "isRecommend", "recommendTime", "roleType", "isShow", "isLove", "isAttention", "role", "ifFirstLogin");
        WebUtil.printApi(response, result);
    }

    /**
     * 手机号码验证唯一性
     *
     * @param response
     * @param request
     * @param mobile
     */
    @RequestMapping("/checkMobile")
    public void checkMobile(HttpServletResponse response,
                            HttpServletRequest request,
                            String mobile) {
        User user = userService.iFindByMobile(mobile);
        if (null == user) {
            WebUtil.printApi(response, new Result(true));
        } else {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0007));
        }
    }

    /**
     * 发送验证码
     *
     * @param response
     * @param request
     * @param mobile
     */
    @RequestMapping("/sendMessage")
    public void sendMessage(HttpServletResponse response,
                            HttpServletRequest request,
                            String mobile,
                            String type) {
        if (null == type || null == mobile) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        User user = userService.iFindByMobile(mobile);
        if (type.equals(Constant.SEND_CODE_FINDPASSWORD)) {
            // type为findPassword时，为找回密码，此时需要验证手机号是否存在
            if (null == user) {
                WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0013));
                return;
            }
        } else {
            // type为register时，为用户注册，此时需要验证手机号是否不存在
            if (null != user) {
                WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0024));
                return;
            }
        }

        // 生成验证码
        String code = getCode(6);

        String result = null;
        try {
            result = MsgSmsUtil.sendSms(mobile, "您的验证码是：【" + code + "】。请不要把验证码泄露给其他人。");
            if (null == result) {
                WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0001));
                return;
            }
            cacheService.put(mobile, code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        WebUtil.printApi(response, new Result(true));
    }

    /**
     * 找回密码
     *
     * @param response
     * @param request
     * @param mobile
     */
    @RequestMapping("/findPassword")
    public void findPassword(HttpServletResponse response,
                             HttpServletRequest request,
                             String mobile,
                             String content,
                             String password) {
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(content) || StringUtils.isEmpty(password)) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        String code = cacheService.get(mobile);

        // 检测验证码是否过期
        if (StringUtils.isEmpty(code)) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0011));
            return;
        }

        // 检测验证码是否正确
        if (!content.equals(code)) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0010));
            return;
        }

        User user = userService.iFindByMobile(mobile);
        if (null == user) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0013));
            return;
        } else {
            user.setPassword(password);

            userService.update(user);

            cacheService.remove(mobile);

            WebUtil.printApi(response, new Result(true));
            return;
        }
    }

    /**
     * 查询用户个人信息
     *
     * @param request
     * @param response
     */
    @RequestMapping("/info")
    public void info(HttpServletRequest request,
                     HttpServletResponse response,
                     Integer id,
                     Integer userId) {
        if (null == id) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        User user = userService.getById(id);

        if (null == user) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        if (user.getStatus().equals(Constant.USER_UNABLE)) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0006));
            return;
        }

        // 查询用户的回复数
        int replyNum = commentService.iGetCommentNumByUser(user, 1);
        user.setReply(replyNum);

        // 查询用户的点赞数
        int praiseNum = praiseService.iGetPraiseNumByUser(user);
        user.setLove(praiseNum);

        // 查询用户的收藏数
        int collectNum = 0;
        List<Favorite> list = favoriteService.iFindAllCollects(user);
        for (Favorite fa : list) {
            collectNum += collectionService.iFindCollectsByFavorite(fa).size();
        }
        user.setCollect(collectNum);

        // 查询用户是否绑定了第三方账号
        // isUsed为0时，代表已绑定第三方
        // isUsed为1时，代表没有绑定第三方
        List<UserThird> userThirdList = userThirdService.iFindUserThirdWithUser(user);
        if (null != userThirdList && userThirdList.size() > 0) {
            user.setIsUsed(1);
        } else {
            user.setIsUsed(0);
        }

        // 查询指定用户是否关注当前用户
        if (null != userId) {
            Attention attention = attentionService.iCheckUser(user, userService.getById(userId));
            if (null == attention) {
                user.setIsAttention("N");
            } else {
                user.setIsAttention("Y");
            }
        }

        // 查询当前用户的星级
        // 根据当前用户的点赞次数和收藏次数分配星级
        user.setStar(getStar(praiseService.iGetPraiseNumByUser(user), collectionService.iGetCollectionNum(user.getId(), Constant.PRAISE_TYPE_USER)));

        // 查询用户的关注数
        Page<Attention> page = attentionService.iFindAttentionPage(user, 1, 1);
        user.setAttention(Integer.parseInt(page.getTotalElements() + ""));

        // 查询用户的粉丝数
        Page<Attention> pages = attentionService.iFindFansPage(user, 1, 1);
        user.setFans(Integer.parseInt(pages.getTotalElements() + ""));

        // 查询第三方绑定信息
        List<UserThird> lists = userThirdService.iFindUserThirdWithUser(user);
        for (UserThird userThird : lists) {
            if (userThird.getType().equals(Constant.LOGIN_TYPE_WEIXIN)) {
                user.setWeiXin(Constant.IF_FIRST_LOGIN_YES);
            } else if (userThird.getType().equals(Constant.LOGIN_TYPE_QQ)) {
                user.setQQ(Constant.IF_FIRST_LOGIN_YES);
            } else if (userThird.getType().equals(Constant.LOGIN_TYPE_XINLANG)) {
                user.setXinLang(Constant.IF_FIRST_LOGIN_YES);
            }
        }

        String result = JsonUtil.obj2ApiJson(new Result(true).data(createMap("user", user)), "password", "isRecommend", "recommendTime", "role", "roleType", "brevitycode", "ifFirstLogin");
        WebUtil.printApi(response, result);
    }

    /**
     * 修改个人信息
     */
    @RequestMapping("/update")
    public void update(HttpServletRequest request,
                       HttpServletResponse response,
                       Integer id,
                       String nickname,
                       Integer sex,
                       String mobile,
                       Integer cityId,
                       String headImage,
                       String backgroundImage,
                       String website,
                       String info,
                       String isShow) {
        User user = userService.getById(id);

        // 呢称
        if (StringUtils.isNotEmpty(nickname)) {
            /*User nickNameUser = userService.iFindByNickname(nickname);
              // 用户名存在
            if (null != nickNameUser) {
                WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0008));
                return;
            }*/
            user.setNickname(nickname);
        }

        // 性别
        if (null != sex) {
            user.setSex(sex);
        }

        // 手机号
        if (null != mobile) {
            user.setMobile(mobile);
        }

        // 城市
        if (null != cityId) {
            City city = cityService.getById(cityId);
            user.setCity(city);
        }

        // 头像 （第三方）
        if (null != headImage) {
            user.setHeadImagePath(headImage);
        }

        // 背景图 （第三方）
        if (null != backgroundImage) {
            user.setBackgroundImagePath(backgroundImage);
        }

        // 个人主页
        if (null != website) {
            user.setWebsite(website);
        }

        // 个人简介
        if (null != info) {
            user.setInfo(info);
        }

        // 是否可见
        if (null != isShow) {
            user.setIsShow(isShow);
        }

        userService.update(user);
        String result = JsonUtil.obj2ApiJson(new Result(true).data(createMap("user", user)), "password", "isRecommend", "recommendTime", "role", "roleType", "brevitycode", "isLove", "isAttention", "ifFirstLogin");
        WebUtil.printApi(response, result);
    }

    /**
     * 呢称验证唯一性
     *
     * @param response
     * @param request
     * @param nickname
     */
    @RequestMapping("/checkNickname")
    public void checkNickname(HttpServletResponse response,
                              HttpServletRequest request,
                              String nickname) {
        User user = userService.iFindByNickname(nickname);
        if (null == user) {
            WebUtil.printApi(response, new Result(true));
        } else {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0008));
        }
    }

    /**
     * 上传头像
     */
    @RequestMapping("/updateHead")
    public void uploadHead(HttpServletRequest request,
                           HttpServletResponse response,
                           Integer id,
                           @RequestParam(required = false) MultipartFile file) {
        if (null == id || null == file) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        User user = userService.getById(id);
        user.setHeadImage(UploadUtils.uploadMultipartFile(file));
        userService.update(user);
        WebUtil.printApi(response, new Result(true).data(user));
    }

    /**
     * 上传背景图
     */
    @RequestMapping("/updateBackground")
    public void updateBackground(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Integer id,
                                 @RequestParam(required = false) MultipartFile file) {
        if (null == id || null == file) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        User user = userService.getById(id);
        user.setBackgroundImage(UploadUtils.uploadMultipartFile(file));
        userService.update(user);
        WebUtil.printApi(response, new Result(true).data(user));
    }

    /**
     * 修改密码
     */
    @RequestMapping("/modifyPassword")
    public void modifyPassword(HttpServletRequest request,
                               HttpServletResponse response,
                               Integer userId,
                               String oldPassword,
                               String newPassword) {
        if (userId == null || StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        String flag = userService.iModifyPassword(userId, oldPassword, newPassword);
        if ("passwordWrong".equals(flag)) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0012));
            return;
        }

        WebUtil.printApi(response, new Result(true));
    }

    /**
     * 设计师主页图片查询
     */
    @RequestMapping("/findImgList")
    public void findImgList(HttpServletRequest request,
                            HttpServletResponse response,
                            Integer id) {
        User user = userService.getById(id);
        if (null == user) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0004));
            return;
        }

        // 初始化最新商品图列表
        List<Map<String, Object>> goodLists = new ArrayList<Map<String, Object>>();

        // 初始化基础bean类
        List<GoodsImage> goodsImageList = null;
        Goods goods1 = null;
        Goods goods2 = null;
        Scene scene = null;
        Series series = null;
        Map<String, Object> imagePlus = null;
        Map<String, Object> imagePlus1 = null;
        Map<String, Object> imagePlus2 = null;

        // 获取最新的设计师商品图信息（最新的两张商品图）
        List<Goods> goodsList = goodsService.iFindGoodsByUser(user);
        if (goodsList.size() > 2) {
            goods1 = goodsList.get(goodsList.size() - 1);
            goods2 = goodsList.get(goodsList.size() - 2);
        } else if (goodsList.size() == 2) {
            goods1 = goodsList.get(1);
            goods2 = goodsList.get(0);
        } else if (goodsList.size() == 1) {
            goods2 = goodsList.get(0);
        }
        if (null != goods1) {
            imagePlus = new HashMap<String, Object>();
            imagePlus.put("id", goods1.getId());
            imagePlus.put("name", goods1.getName());
            goodsImageList = goodsImageService.listByGoodsId(goods1.getId());
            if (null == goodsImageList || goodsImageList.size() == 0) {
                imagePlus.put("path", "");
            } else {
                // 循环读取商品所属的图片列表，取出封面图
                for (GoodsImage goodsImage : goodsImageList) {
                    if (goodsImage.getIsHead().equals("yes")) {
                        imagePlus.put("path", goodsImage.getImage());
                    }
                }
            }
            goodLists.add(imagePlus);
        }
        if (null != goods2) {
            imagePlus = new HashMap<String, Object>();
            imagePlus.put("id", goods2.getId());
            imagePlus.put("name", goods2.getName());
            goodsImageList = goodsImageService.listByGoodsId(goods2.getId());
            if (null == goodsImageList || goodsImageList.size() == 0) {
                imagePlus.put("path", "");
            } else {
                // 循环读取商品所属的图片列表，取出封面图
                for (GoodsImage goodsImage : goodsImageList) {
                    if (goodsImage.getIsHead().equals("yes")) {
                        imagePlus.put("path", goodsImage.getImage());
                    }
                }
            }
            goodLists.add(imagePlus);
        }

        // 获取最近场景图（最新的一张场景图）
        List<Scene> sceneList = sceneService.iFindSceneByUser(user);
        if (null != sceneList && sceneList.size() > 0) {
            scene = sceneList.get(sceneList.size() - 1);

            imagePlus1 = new HashMap<String, Object>();
            imagePlus1.put("id", scene.getId());
            imagePlus1.put("name", scene.getName());
            imagePlus1.put("path", scene.getImage());
        }

        // 获取最近系列图（最新的一张系列图）
        List<Series> seriesList = seriesService.iFindSeriesByUser(user);
        if (null != seriesList && seriesList.size() > 0) {
            series = seriesList.get(seriesList.size() - 1);

            imagePlus2 = new HashMap<String, Object>();
            imagePlus2.put("id", series.getId());
            imagePlus2.put("name", series.getSeriesTag().getName());
            List<SeriesScene> list = seriesSceneService.iFindBySeries(series);
            if (null == list || list.size() == 0) {
                imagePlus2.put("path", "");
            } else {
                imagePlus2.put("path", list.get(0).getScene().getImage());
            }
        }

        UserImage userImage = new UserImage();
        // 将最近商品图列表放入返回值中
        userImage.setGoodsList(goodLists);

        // 将最近场景图放入返回值中
        userImage.setScene(imagePlus1);

        // 将最近系列图放入返回值中
        userImage.setSeries(imagePlus2);

        WebUtil.printApi(response, new Result(true).data(userImage));
    }

    /**
     * 最新评论查询
     */
    @RequestMapping("/findNewComment")
    public void findNewComment(HttpServletRequest request,
                               HttpServletResponse response,
                               Integer id) {
        if (null == id) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        Page<Comment> commentPage = commentService.iFindCommentPage(id, 1, 1000000);

        Goods goods = null;
        Scene scene = null;

        // 读取评论的类型，从而查询出对应的评论源对象具体信息
        List<Comment> lists = commentPage.getContent();
        if (null == lists || lists.size() == 0) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0018));
            return;
        }

        Comment comment = lists.get(0);

        // 存入评论用户的呢称和头像
        comment.setNickname(null == (comment.getUser().getNickname()) ? "" : comment.getUser().getNickname());
        comment.setPhoto(null == (comment.getUser().getHeadImage()) ? "" : comment.getUser().getHeadImage());

        // objectType=scene时，为场景图评论
        // objectType=goods时，为商品图评论
        if (comment.getObjectType().equals(Constant.COMMENT_TYPE_GOODS)) {
            // 此时为商品图评论
            goods = goodsService.getById(comment.getObjectId());
            List<GoodsImage> goodsImageList = goodsImageService.listByGoodsId(goods.getId());
            if (null == goodsImageList || goodsImageList.size() == 0) {
                comment.setObjectImage("");
            } else {
                comment.setObjectImage((goodsImageList.get(0)).getImage());
            }
        } else {
            // 此时为场景图评论
            scene = sceneService.getById(comment.getObjectId());
            comment.setObjectImage(scene.getImage());
        }

        // 判断当前评论是否点赞过
        // 查询点赞记录，如果有记录，则表示已点赞；否则表示没有点赞
        Praise praise = praiseService.iFindByUserAndObjectId(comment.getUser(), comment.getId(), Constant.PRAISE_TYPE_COMMENT);
        if (null == praise) {
            comment.setIfLove(1);
        } else {
            comment.setIfLove(0);
        }

        // 获取指定的评论回复列表
        Reply reply = null;
        User user = null;
        List<Reply> list = new ArrayList<Reply>();
        List<Comment> commentList = commentService.iFindReply(comment.getId());
        for (Comment comment1 : commentList) {
            user = comment1.getUser();

            reply = new Reply();
            reply.setNickname(null == (user.getNickname()) ? "" : user.getNickname());
            reply.setHeadImage(user.getHeadImage());
            reply.setContent(comment1.getContent());
            reply.setCreateTime(comment1.getCreateTime());

            list.add(reply);
        }
        comment.setReplyList(list);

        String result = JsonUtil.obj2ApiJson(new Result(true).data(comment), "thumbs", "createDate", "attribute", "brevitycode", "user", "objectType", "pid");
        WebUtil.printApi(response, result);
    }

    // 新建个人收藏夹
    @RequestMapping(value = "/addMyFavorite")
    public void addMyFavorite(HttpServletRequest request,
                              HttpServletResponse response,
                              String name,
                              String info,
                              Integer userId) {
        if (null == userId || null == name || null == info) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        // 检测当前用户的收藏夹，查看是否重名，如果重名，则返回错误信息
        List<Favorite> list = favoriteService.iFindAllCollects(userService.getById(userId));
        for (Favorite favorit : list) {
            if (favorit.getName().equals(name)) {
                WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0022));
                return;
            }
        }

        Favorite favorite = new Favorite();
        favorite.setUser(userService.getById(userId));
        favorite.setName(name);
        favorite.setInfo(info);

        // 执行新建操作
        favoriteService.create(favorite);
        String result = JsonUtil.obj2ApiJson(new Result(true).data(favorite), "user");
        WebUtil.printApi(response, result);
    }

    // 删除个人收藏夹
    @RequestMapping(value = "/delFavorite")
    public void delFavorite(HttpServletRequest request,
                            HttpServletResponse response,
                            Integer favoriteId) {
        if (favoriteId == null) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }
        favoriteService.delFavorite(favoriteId);
        String result = JsonUtil.obj2ApiJson(new Result(true));
        WebUtil.printApi(response, result);
    }

    // 个人收藏夹列表 (分页)
    @RequestMapping(value = "/findMyFavorite")
    public void findMyFavorite(HttpServletRequest request,
                               HttpServletResponse response,
                               Integer pageNum,
                               Integer pageSize,
                               Integer userId) {
        Page<Favorite> page = favoriteService.iPageForFavorite(pageNum, pageSize, userId);

        List<Collection> list = new ArrayList<Collection>();
        Collection collection = null;
        String image = null;

        for (Favorite favorite : page.getContent()) {
            // 查询收藏夹下的第一条收藏记录
            list = collectionService.iFindCollectsByFavorite(favorite);
            if (null != list && list.size() > 0) {
                collection = list.get(0);
                if (collection.getObjectType().equals(Constant.COLLECTION_TYPE_GOODS)) {
                    Goods goods = goodsService.getById(collection.getObjectId());
                    if (goods != null) {
                        image = goods.getCover();
                    } else {
                        image = "";
                        collectionService.deleteById(collection.getId());
                    }
                } else if (collection.getObjectType().equals(Constant.COLLECTION_TYPE_SCENE)) {
                    Scene scene = sceneService.getById(collection.getObjectId());
                    if (scene != null) {
                        image = scene.getImage();
                    } else {
                        image = "";
                        collectionService.deleteById(collection.getId());
                    }
                } else if (collection.getObjectType().equals(Constant.COLLECTION_TYPE_SERIES)) {
                    Series series = seriesService.getById(collection.getObjectId());
                    if (series != null) {
                        image = series.getCover();
                    } else {
                        image = "";
                        collectionService.deleteById(collection.getId());
                    }
                }
                favorite.setCover(image);
            }
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "user");
        WebUtil.printApi(response, result);
    }

    // 设计师列表 (分页) = 用户列表（分页）
    // 注册用户本身就是设计师
    @RequestMapping(value = "/findDesignerList")
    public void findDesignerList(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Integer pageNum,
                                 Integer pageSize,
                                 Integer userId,
                                 Integer cityId,
                                 String name,
                                 String sort) {
        if (null == userId || null == pageNum || null == pageSize) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        Page<User> page = userService.iPageUser(cityId, name, sort, pageNum, pageSize);
        for (User user : page.getContent()) {
            // 查询点赞记录，如果有记录，则表示已点赞；否则表示没有点赞
            Praise praise = praiseService.iFindByUserAndObjectId(userService.getById(userId), user.getId(), Constant.PRAISE_TYPE_USER);
            Map<String, Object> map = new HashMap<String, Object>();
            if (null == praise) {
                user.setIsLove("N");
            } else {
                user.setIsLove("Y");
            }
        }

        Map<String, Object> dataMap = APIFactory.fitting(page);
        Result obj = new Result(true).data(dataMap);
        String result = JsonUtil.obj2ApiJson(obj, "password", "account", "status", "isRecommend", "recommendTime", "roleType", "isShow", "brevitycode", "isUsed", "collect", "ifFirstLogin");
        WebUtil.printApi(response, result);
    }

    /**
     * 第三方绑定
     *
     * @param request
     * @param response
     * @param userId
     * @param openId
     */
    @RequestMapping("/bindingOpenId")
    public void bindingOpenId(HttpServletRequest request,
                              HttpServletResponse response,
                              Integer userId,
                              String openId,
                              String type) {
        if (null == userId || null == openId || null == type) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        UserThird userThird = userService.iCheckBinding(userService.getById(userId), openId, type);
        // 不为空，则表示已经绑定第三方
        if (null != userThird) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0020));
            return;
        }

        UserThird userThird2 = userService.findByOpenidAndType(openId, type);
        // 不为空，则表示已经绑定第三方
        if (null != userThird2) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0025));
            return;
        }

        UserThird newUserThird = new UserThird();
        newUserThird.setUser(userService.getById(userId));
        newUserThird.setOpen_id(openId);
        newUserThird.setType(type);

        userThirdService.create(newUserThird);

        String result = JsonUtil.obj2ApiJson(new Result(true));
        WebUtil.printApi(response, result);
    }

    /**
     * 全局搜索图片数量
     *
     * @param request
     * @param response
     * @param name
     * @param objectType
     */
    @RequestMapping("/mainSearch")
    public void mainSearch(HttpServletRequest request,
                           HttpServletResponse response,
                           String name,
                           String objectType) {
        if (null == objectType) {
            WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            return;
        }

        if (null != name) {
            name = "%" + name + "%";
        }

        Integer count = 0;

        if (objectType.equals("goods")) {
            // goods为商品图
            if (null == name || name.equals("")) {
                count = goodsService.findAll().size();
            } else {
                count = goodsService.findGoodsCount(name);
            }
        } else if (objectType.equals("scene")) {
            // scene为场景图
            if (null == name || name.equals("")) {
                count = sceneService.findAll().size();
            } else {
                count = sceneService.findScenes(name);
            }
        } else if (objectType.equals("material")) {
            // material为素材图
            count = materialService.findMaterialCount(name);
        } else if (objectType.equals("series")) {
            // series为系列图
            if (null == name || name.equals("")) {
                count = seriesService.findAll().size();
            } else {
                count = seriesService.findSeries(name);
            }
        }

        String result = JsonUtil.obj2ApiJson(new Result(true).data(createMap("count", count)));
        WebUtil.printApi(response, result);
    }

    public static int getStar(int collect, int praise) {
        int star = 0;
        int count = collect + praise;
        if (count >= 1 && count < 20) {
            star = 1;
        } else if (count >= 20 && count < 50) {
            star = 2;
        } else if (count >= 50 && count < 100) {
            star = 3;
        } else if (count >= 100 && count < 200) {
            star = 4;
        } else if (count >= 200) {
            star = 5;
        }

        return star;
    }

    public static String getCode(int length) {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < length; i++) {
            result += random.nextInt(10);
        }
        return result;
    }


    public static String getRandom(int length) {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < length; i++) {
            result += random.nextInt(10);
        }

        account = "u" + result;

        return "u" + result;
    }
}