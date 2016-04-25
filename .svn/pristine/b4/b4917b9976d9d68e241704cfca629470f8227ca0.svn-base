package com.bluemobi.decor.portal.exception;


import com.bluemobi.decor.common.exception.GeneralExceptionHandler;
import com.bluemobi.decor.core.ERROR_CODE;
import com.bluemobi.decor.core.bean.Result;
import com.bluemobi.decor.portal.util.WebUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

        try {
            HandlerMethod handlerMethod =	(HandlerMethod)handler;
            if(handlerMethod!=null){
                String methodName =  handlerMethod.getMethod().getName();
                String beanName = handlerMethod.getBean().getClass().getName();
                GeneralExceptionHandler.log(beanName + "类" + methodName + "方法" + "异常", ex);
                WebUtil.printApi(response, new Result(false).msg(ERROR_CODE.ERROR_CODE_0002));
            }
        }catch (Exception e){
            GeneralExceptionHandler.log(e);
        }

        return  null;
	}
}
