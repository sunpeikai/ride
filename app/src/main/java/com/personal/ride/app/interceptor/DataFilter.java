package com.personal.ride.app.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.personal.core.common.utils.AesCBC;
import com.personal.core.common.utils.SpringUtil;
import com.personal.ride.app.config.ParameterRequestWrapper;
import com.personal.ride.app.config.SystemConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/**
 * @author zhangqingqing
 * @version DataFilter, v0.1 2019/4/4 16:11
 */
//@Component
@Slf4j
//@WebFilter(filterName = "DataFilter", urlPatterns = "/*")
public class DataFilter implements Filter {

    private SystemConfig systemConfig = SpringUtil.getBean(SystemConfig.class);


    public static final String PARAM_NAME_EXCLUSIONS = "exclusions";
    private Set<String> excludesPattern;
    protected String contextPath;
    protected PathMatcher matcher = new AntPathMatcher();

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String param = filterConfig.getInitParameter(PARAM_NAME_EXCLUSIONS);
        if (param != null && param.trim().length() != 0) {
            this.excludesPattern = new HashSet(Arrays.asList(param.split("\\s*,\\s*")));
        }

    }


    @Override
    public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain) throws IOException {
        PrintWriter out = null;
        try {
            HttpServletRequest request = (HttpServletRequest) srequest;
            if (!this.isExclusion(request.getRequestURI())) {
                HashMap m = new HashMap(request.getParameterMap());
                //---------解密data------------------
                String data = request.getParameter("data");
                String randomKey = request.getParameter("randomKey");
                if (data != null && randomKey != null) {
                    try {
                        //解密randomKey,要使⽤私钥解密
                        randomKey = AesCBC.decrypt(randomKey, systemConfig.getLocalKey(), systemConfig.getLocalIV());
                        //使用randomKey解密data
                        log.info("待解密data:" + data);
                        data = AesCBC.decrypt(data, randomKey, systemConfig.getLocalIV());
                    } catch (Exception e) {
                        this.isTrue(false, "data解密失败");
                    }
                    //data存进param
                    JSONObject myJson = JSONObject.parseObject(data);
                    Set<String> keys = myJson.keySet();
                    for (String key : keys) {
                        String value = myJson.getString(key);
                        m.put(key, value);
                    }
                }

                HttpServletRequest req = request;
                ParameterRequestWrapper wrapRequest = new ParameterRequestWrapper(req, m);
                request.getParameterMap();
                request = wrapRequest;
            }


            filterChain.doFilter(request, sresponse);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            sresponse.setCharacterEncoding("UTF-8");
            sresponse.setContentType("application/json; charset=utf-8");
            out = sresponse.getWriter();
            JSONObject res = new JSONObject();
            res.put("status", 99);
            res.put("statusDesc", e.getMessage());
            res.put("data", "");
            out.append(res.toString());
            out.flush();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                out.close();
            }
        }
    }


    @Override
    public void destroy() {


    }


    public boolean isExclusion(String requestURI) {
        if (this.excludesPattern == null) {
            return false;
        } else {
            if (this.contextPath != null && requestURI.startsWith(this.contextPath)) {
                requestURI = requestURI.substring(this.contextPath.length());
                if (!requestURI.startsWith("/")) {
                    requestURI = "/" + requestURI;
                }
            }

            Iterator i$ = this.excludesPattern.iterator();

            String pattern;
            do {
                if (!i$.hasNext()) {
                    return false;
                }

                pattern = (String) i$.next();
            } while (!this.matcher.match(pattern, requestURI));

            return true;
        }
    }

    /**
     * 替代Assert.isTrue - 因为前端要求统一返回格式
     */
    private void isTrue(boolean condition, String statusDesc) throws Exception {
        if (!condition) {
            throw new Exception(statusDesc);
        }
    }
}