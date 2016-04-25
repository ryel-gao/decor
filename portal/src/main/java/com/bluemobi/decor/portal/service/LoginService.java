package com.bluemobi.decor.portal.service;


import com.bluemobi.decor.entity.User;
import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    public User backendGetUser(HttpServletRequest request);

    public Boolean backendLogin(String username, String password);

    public void backendLogOut(HttpServletRequest request);

    public User login(String mobile, String password);

}
