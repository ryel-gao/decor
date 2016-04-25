package com.bluemobi.decor.common.interceptor;

import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by reny on 2015/12/2.
 */
public class MyAcceptHeaderLocaleResolver extends AcceptHeaderLocaleResolver {
    private Locale myLocal;

    public Locale resolveLocale(HttpServletRequest request) {
        Locale l = request.getLocale();
        if (myLocal == null) {
            return request.getLocale();
        }
        return myLocal;
    }

    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        myLocal = locale;
    }

}
