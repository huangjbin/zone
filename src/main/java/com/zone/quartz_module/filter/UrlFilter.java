package com.zone.quartz_module.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zone.quartz_module.common.ResLanguage;
import com.zone.quartz_module.exception.CustomException;
import com.zone.quartz_module.pojo.HttpResult;
import com.zone.quartz_module.pojo.SystemResponseBean;
import com.zone.quartz_module.service.impl.HttpAPIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@WebFilter(filterName = "tokenFilter", urlPatterns = "/*")
public class UrlFilter implements Filter {

    @Autowired
    HttpAPIService httpAPIService;//n1 n2

    @Autowired
    ObjectMapper objectMapper;

    @Value("${module_net.find_auth}")
    public String url_findAuth;

    public static Logger log = LoggerFactory.getLogger(UrlFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }





    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);

        String requestMethord = request.getRequestURI();//请求方法
        String action = request.getServletPath();//菜单
        String moduleStr = action.substring(0, "/task/module".length());
        String moduleLogStr = action.substring(0, "/taskLog/module".length());

        log.info("url_findAuth：" + url_findAuth);

        if (requestMethord == null) {
            return;
        }

        if (moduleStr.equals("/task/module")) {
            filterChain.doFilter(requestWrapper, servletResponse);//不拦截
            return;
        }
        if (moduleLogStr.equals("/taskLog/module")) {
            filterChain.doFilter(requestWrapper, servletResponse);//不拦截
            return;
        }

        //获取请求参数
        String data = ((BodyReaderHttpServletRequestWrapper) requestWrapper).bodyStr;
        Map paramList = objectMapper.readValue(data, Map.class);
        String language = (String) paramList.get("language");
        String token = (String) paramList.get("token");
        Integer appTypeId = (Integer) paramList.get("appTypeId");

        //app类型验证(非空验证)
        if (!paramList.containsKey("appTypeId")) {
            requestWrapper.setAttribute("CustomException", new CustomException(ResLanguage.getRes_param_no_mes(language, "appTypeId"), ResLanguage.RES_PARAM_NO_CODE));
            filterChain.doFilter(requestWrapper, servletResponse);//不拦截
            return;
        }
        //token验证(非空验证)
        if (!paramList.containsKey("token")) {
            requestWrapper.setAttribute("CustomException", new CustomException(ResLanguage.getRes_param_no_mes(language, "token"), ResLanguage.RES_PARAM_NO_CODE));
            filterChain.doFilter(requestWrapper, servletResponse);//不拦截
            return;
        }

        Map reqMap = new HashMap();
        reqMap.put("language", language);
        reqMap.put("appTypeId", appTypeId);
        reqMap.put("token", token);
        reqMap.put("action", request.getRequestURI());
        HttpResult httpResult = null;
        try {
            log.info("url_findAuth:"+url_findAuth);
            httpResult = httpAPIService.doPost(url_findAuth, reqMap);
            if (httpResult == null || httpResult.getCode() != 200 || httpResult.getBody() == null) {
                log.info(ResLanguage.getRes_system_err_mes(language) + "errCode：" + httpResult.getCode());
                requestWrapper.setAttribute("CustomException", new CustomException(ResLanguage.getRes_system_err_mes(language), ResLanguage.RES_SYSTEM_ERR_CODE));
                filterChain.doFilter(requestWrapper, servletResponse);//不拦截
                return;
            }
            String body = httpResult.getBody();
            SystemResponseBean systemResponseBean = objectMapper.readValue(body, SystemResponseBean.class);
            if (systemResponseBean.getCode() != 200) {
//                throw new CustomException((String) systemResponseBean.getMessage(), systemResponseBean.getCode());
                requestWrapper.setAttribute("CustomException", new CustomException((String) systemResponseBean.getMessage(), systemResponseBean.getCode()));
                filterChain.doFilter(requestWrapper, servletResponse);//不拦截
                return;
            } else {
                Boolean includeMenu = systemResponseBean.getData().getIncludeMenu();
                Boolean includeApp = systemResponseBean.getData().getIncludeApp();
                List<Long> dataScopeOrgIds = systemResponseBean.getData().getDataScopeOrgIds();
                Long userId = systemResponseBean.getData().getUserId();
                Long orgId = systemResponseBean.getData().getOrgId();
//                if (includeApp != null && includeApp == true && includeMenu != null && includeMenu == true) {//有权限
//                    if (action.equals("/task/findTasks")) {
//                        requestWrapper.setAttribute("userId", userId);
//                        requestWrapper.setAttribute("dataScopeOrgIds", dataScopeOrgIds);
//                    }
//                    if (action.equals("/task/addScheduleTask")) {
//                        requestWrapper.setAttribute("userId", userId);
//                        requestWrapper.setAttribute("orgId", orgId);
//                    }
//                    if (action.equals("/task/findDevLog")) {
//                        requestWrapper.setAttribute("userId", userId);
//                        requestWrapper.setAttribute("dataScopeOrgIds", dataScopeOrgIds);
//                    }
//                    if (action.equals("/app/task/findDevLog")) {
//                        requestWrapper.setAttribute("userId", userId);
//                        requestWrapper.setAttribute("dataScopeOrgIds", dataScopeOrgIds);
//                    }
//                    //不拦截
//                    filterChain.doFilter(requestWrapper, servletResponse);
//                    return;
//                } else {//无权限
//                    requestWrapper.setAttribute("CustomException", new CustomException(ResLanguage.getRes_auth_no_mes(language), ResLanguage.RES_AUTH_NO_CODE));
//                    filterChain.doFilter(requestWrapper, servletResponse);//不拦截
//                    return;
//                }

                if (requestMethord.equals("/quartz_module/task/app/findDevLog")) {
                    if (includeApp != null && includeApp == true) {//有权限
                        requestWrapper.setAttribute("userId", userId);
                        requestWrapper.setAttribute("dataScopeOrgIds", dataScopeOrgIds);
                        filterChain.doFilter(requestWrapper, servletResponse);
                        return;
                    } else {//无权限
                        requestWrapper.setAttribute("CustomException", new CustomException(ResLanguage.getRes_auth_no_mes(language), ResLanguage.RES_AUTH_NO_CODE));
                        filterChain.doFilter(requestWrapper, servletResponse);//不拦截
                        return;
                    }
                }

                if (includeApp != null && includeApp == true) {//有权限
                    if (includeMenu != null && includeMenu == true) {//有权限
                        if(action.equals("/task/findDevLog")){
                            requestWrapper.setAttribute("userId", userId);
                            requestWrapper.setAttribute("dataScopeOrgIds", dataScopeOrgIds);
                            filterChain.doFilter(requestWrapper, servletResponse);
                            return;
                        }
                        if(action.equals("/task/addScheduleTask")){
                            requestWrapper.setAttribute("userId", userId);
                            requestWrapper.setAttribute("orgId", orgId);
                            filterChain.doFilter(requestWrapper, servletResponse);
                            return;
                        }
                        if(action.equals("/task/findTasks")){
                            requestWrapper.setAttribute("userId", userId);
                            requestWrapper.setAttribute("dataScopeOrgIds", dataScopeOrgIds);
                            filterChain.doFilter(requestWrapper, servletResponse);
                            return;
                        }
                        if (action.equals("/task/app/findDevLog")) {
                            requestWrapper.setAttribute("userId", userId);
                            requestWrapper.setAttribute("dataScopeOrgIds", dataScopeOrgIds);
                            filterChain.doFilter(requestWrapper, servletResponse);
                            return;
                        }
                        filterChain.doFilter(requestWrapper, servletResponse);
                        return;
                    } else {//无权限
                        if (action.equals("/task/app/findDevLog")) {
                            requestWrapper.setAttribute("userId", userId);
                            requestWrapper.setAttribute("dataScopeOrgIds", dataScopeOrgIds);
                            filterChain.doFilter(requestWrapper, servletResponse);
                            return;
                        }
                        else {
                            requestWrapper.setAttribute("CustomException", new CustomException(ResLanguage.getRes_auth_no_mes(language), ResLanguage.RES_AUTH_NO_CODE));
                            filterChain.doFilter(requestWrapper, servletResponse);//不拦截
                            return;
                        }
                    }
                } else {//无权限
                    requestWrapper.setAttribute("CustomException", new CustomException(ResLanguage.getRes_auth_no_mes(language), ResLanguage.RES_AUTH_NO_CODE));
                    filterChain.doFilter(requestWrapper, servletResponse);//不拦截
                    return;
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
            requestWrapper.setAttribute("CustomException", new CustomException(ResLanguage.getSystemModule_err_mes(language), ResLanguage.RES_SYS_MODULE_ERR_CODE));
            filterChain.doFilter(requestWrapper, servletResponse);//不拦截
            return;
        }
    }

    @Override
    public void destroy() {
    }
}