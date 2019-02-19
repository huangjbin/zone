package com.zone.quartz_module.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone.quartz_module.exception.CustomException;
import com.zone.quartz_module.service.impl.HttpAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 鉴权拦截器
 */
public class UrlInterceptor implements HandlerInterceptor {

    @Autowired
    HttpAPIService httpAPIService;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        CustomException customException = (CustomException) request.getAttribute("CustomException");
        if(customException!=null){
            throw customException;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

    }

}