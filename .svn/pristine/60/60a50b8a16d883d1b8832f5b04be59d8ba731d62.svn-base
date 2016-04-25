package com.bluemobi.decor.portal.service.impl;

import com.bluemobi.decor.core.Constant;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.portal.service.LoginService;
import com.bluemobi.decor.portal.util.SessionUtils;
import com.bluemobi.decor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by liuhm on 2015/7/24.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    @Override
    public User backendGetUser(HttpServletRequest request) {
        User User = (User)request.getSession().getAttribute(Constant.SESSION_ADMIN_USER);
        return User;

    }

    //总后台登录
    @Override
    public Boolean backendLogin(String account, String password) {
        User User = userService.iFindByMobileAndPassword(account, password);
        if(User!=null){
            SessionUtils.put(Constant.SESSION_ADMIN_USER, User);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void backendLogOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.removeAttribute(Constant.SESSION_ADMIN_USER);
    }

    @Override
    public User login(String mobile, String password) {
        return null;
    }
}
