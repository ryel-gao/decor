package com.bluemobi.decor.portal.filter;


import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.portal.util.WebUtil;
import com.bluemobi.decor.entity.User;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gaoll on 14-10-28.
 */
public class GlobalFilter implements Filter {

    private static String[] INTERCEPT_URLS = new String[]{};

    public GlobalFilter() {

    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String interceptUrls = filterConfig.getInitParameter("interceptUrls");
        if (StringUtils.isNotBlank(interceptUrls)) {
            INTERCEPT_URLS = interceptUrls.split(",");
        }
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String url = httpRequest.getRequestURI().toString();
        String contextPath = httpRequest.getContextPath();
        url = url.substring(contextPath.length());

        // 总后台
        if (url.indexOf("backend") >= 0){

        }
        // pc端
        else {
            User user = (User)httpRequest.getSession().getAttribute("user");

            if (INTERCEPT_URLS != null) {
                for (String interceptUrl : INTERCEPT_URLS) {
                    // 进行拦截
                    if (url.indexOf(interceptUrl) >= 0 && user == null) {
                        String xRequested = httpRequest.getHeader("X-Requested-With");
                        if (xRequested != null && xRequested.indexOf("XMLHttpRequest") != -1) {
                            //httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            WebUtil.print(httpResponse, new Result(true).msg("请先登录!"));
                        } else {
                            httpResponse.sendRedirect(contextPath+"/pc/user/loginPage");
                        }
                    }
                }
            }

            chain.doFilter(request, response);
            return;
        }
    }

    @Override
    public void destroy() {

    }


}
