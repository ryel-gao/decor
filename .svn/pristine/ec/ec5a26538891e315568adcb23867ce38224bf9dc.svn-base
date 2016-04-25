package com.bluemobi.decor.common.interceptor;

import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.entity.User;
import com.bluemobi.decor.utils.SessionUtils;
import com.bluemobi.decor.utils.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PcLoginInterceptor extends HandlerInterceptorAdapter {
    private final Logger log = LoggerFactory.getLogger(PcLoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = SessionUtils.getCurrentUser();
        if (user == null) {
            String xRequested = request.getHeader("X-Requested-With");
            if (xRequested != null && xRequested.indexOf("XMLHttpRequest") != -1) {
                WebUtil.print(response, new Result(false).msg("请先登录!"));
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
