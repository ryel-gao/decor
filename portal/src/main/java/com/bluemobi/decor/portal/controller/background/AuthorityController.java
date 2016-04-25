package com.bluemobi.decor.portal.controller.background;


import com.bluemobi.decor.common.factory.DataTableFactory;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.*;
import com.bluemobi.decor.portal.controller.CommonController;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.service.*;
import com.bluemobi.decor.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 权限
 * Created by gaoll on 2015/3/11.
 */
@Controller
@RequestMapping(value = "/backend/authority")
public class AuthorityController extends CommonController {

    @Autowired
    private UserService userService;

    @Autowired
    private TRoleService tRoleService;

    @Autowired
    private TRoleResourceService tRoleResourceService;

    @Autowired
    private TUserRoleService tUserRoleService;

    @RequestMapping(value = "/adminPage")
    public String adminPage() {
        return "系统管理-管理员列表";
    }

    // ------------------管理员-----------------------

    /**
     * 管理员列表
     *
     * @param request
     * @param response
     * @param draw
     * @param start
     * @param length
     * @param nickname
     * @param account
     * @param model
     */
    @RequestMapping(value = "/adminList")
    public void adminList(HttpServletRequest request,
                          HttpServletResponse response,
                          Integer draw,
                          Integer start,
                          Integer length,
                          String nickname,
                          String account,
                          ModelMap model) {
        try {
            int pageNum = getPageNum(start, length);
            Page<User> page = userService.bAdmin(pageNum, length, nickname, account);
            for (User user : page.getContent()) {
                TUserRole tUserRole = tUserRoleService.findByUserId(user.getId());
                if (tUserRole != null) {
                    user.setRole(tUserRole.getRole());
                }
            }
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/listRole")
    public void listRole(HttpServletRequest request,
                         HttpServletResponse response,
                         ModelMap model) {
        try {
            List<TRole> list = tRoleService.findAll();
            WebUtil.print(response, new Result(true).data(list));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @RequestMapping(value = "/saveAdmin")
    public void saveAdmin(HttpServletRequest request,
                          HttpServletResponse response,
                          ModelMap model,
                          User user,
                          Integer roleId) {
        try {
            if (user.getId() == null) {
                if (userService.isAccountExist(user.getAccount())) {
                    WebUtil.print(response, new Result(false).msg("账号已经存在!"));
                    return;
                }
            }
            if (roleId != null) {
                if (null == user.getRole()) {
                    // 当前用户没有权限时，判断是否存在超级管理员，如果存在，返回错误信息
                    TUserRole tUserRoleTemp = tUserRoleService.findMaster();
                    if (roleId == 11 && null != tUserRoleTemp && tUserRoleTemp.getUser().getId() != user.getId()) {
                        WebUtil.print(response, new Result(false).msg("超级管理员只能存在一个！"));
                        return;
                    }
                } else if (user.getRole().getId() != 11 && roleId == 11) {
                    WebUtil.print(response, new Result(false).msg("超级管理员只能存在一个！"));
                    return;
                }
            }
            TUserRole tUserRole = tUserRoleService.findByUserId(user.getId());
            if (tUserRole != null) {
                if (tUserRole.getRole().getId() == 11 && roleId != tUserRole.getRole().getId()) { //11是超级管理员
                    WebUtil.print(response, new Result(false).msg("超级管理员不允许修改权限！"));
                    return;
                }
                if (roleId != null) {
                    if (roleId == 11 && roleId != tUserRole.getRole().getId()) { //11是超级管理员
                        WebUtil.print(response, new Result(false).msg("不允许分配超级管理员权限！"));
                        return;
                    }
                }
            }
            userService.saveAdmin(user, roleId);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @RequestMapping(value = "/deleteAdmin")
    public void deleteAdmin(HttpServletRequest request,
                            HttpServletResponse response,
                            ModelMap model,
                            Integer roleId) {
        try {
            List<TUserRole> tUserRoleList = tUserRoleService.list(roleId);
            if (tUserRoleList != null && tUserRoleList.size() > 0) {
                WebUtil.print(response, new Result(false).msg("该权限已经被使用，无法删除。"));
                return;
            }
            tRoleService.deleteRole(roleId);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    // ------------------权限管理-----------------------
    @RequestMapping(value = "/rolePage")
    public String rolePage() {
        return "系统管理-权限列表";
    }

    /**
     * 权限列表
     *
     * @param request
     * @param response
     * @param draw
     * @param start
     * @param length
     * @param name
     * @param model
     */
    @RequestMapping(value = "/roleList")
    public void roleList(HttpServletRequest request,
                         HttpServletResponse response,
                         Integer draw,
                         Integer start,
                         Integer length,
                         String name,
                         ModelMap model) {
        try {
            int pageNum = getPageNum(start, length);
            Page<TRole> page = tRoleService.page(pageNum, length, name);
            for (TRole role : page.getContent()) {
                List<TResource> resourceList = tRoleResourceService.resourceList(role.getId());
                role.setResourceList(resourceList);
            }
            Map<String, Object> result = DataTableFactory.fitting(draw, page);
            WebUtil.print(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/resourceList")
    public void resourceList(HttpServletRequest request,
                             HttpServletResponse response,
                             ModelMap model) {
        try {
            List<TResource> list = tRoleResourceService.resourceList(11);//超级管理员权限，也就是查询所有一级权限的意思
            WebUtil.print(response, new Result(true).data(list));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @RequestMapping(value = "/saveRole")
    public void saveRole(HttpServletRequest request,
                         HttpServletResponse response,
                         ModelMap model,
                         TRole tRole,
                         String resourceIds) {
        try {
            if (tRoleService.isNameExist(tRole)) {
                WebUtil.print(response, new Result(false).msg("名称已经存在!"));
                return;
            }
            if (StringUtils.isNotEmpty(resourceIds)) {
                String[] arr = resourceIds.split(",");
                if (tRole != null && tRole.getId() != null) {
                    if (tRole.getId() == 11) {
                        WebUtil.print(response, new Result(false).msg("超级管理员权限不允许修改!"));
                        return;
                    }
                }
                tRoleService.saveRole(tRole, arr);
                WebUtil.print(response, new Result(true).msg("操作成功!"));
            } else {
                WebUtil.print(response, new Result(false).msg("权限不能为空!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }

    @RequestMapping(value = "/deleteRole")
    public void deleteRole(HttpServletRequest request,
                           HttpServletResponse response,
                           ModelMap model,
                           Integer roleId) {
        try {
            List<TUserRole> tUserRoleList = tUserRoleService.list(roleId);
            if (roleId == 11) {//11为超级管理员
                WebUtil.print(response, new Result(false).msg("不可修改超级管理员权限!"));
                return;
            }
            if (tUserRoleList != null && tUserRoleList.size() > 0) {
                WebUtil.print(response, new Result(false).msg("该权限已经被使用，无法删除。"));
                return;
            }
            tRoleService.deleteRole(roleId);
            WebUtil.print(response, new Result(true).msg("操作成功!"));
        } catch (Exception e) {
            e.printStackTrace();
            WebUtil.print(response, new Result(false).msg("操作失败!"));
        }
    }


}
