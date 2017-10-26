package com.am.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * Created by jws on 2017/5/9.
 */
public class Interceptor implements HandlerInterceptor{
    private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");
    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (logger.isDebugEnabled()){
            long beginTime = System.currentTimeMillis();//1、开始时间
            startTimeThreadLocal.set(beginTime);		//线程绑定变量（该数据只有当前请求的线程可见
            String token = new String();
            if(httpServletRequest.getCookies()!=null){
                Cookie[] cookies = httpServletRequest.getCookies();
                cookies.hashCode();
                Cookie[] cookie = httpServletRequest.getCookies();
                for (int i = 0; i < cookie.length; i++) {
                    Cookie cook = cookie[i];
                    if(cook.getName().equalsIgnoreCase("token")){ //获取键
                        token = cook.getValue();    //获取值
                    }
                }
                logger.debug("开始计时: {}  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS").format(beginTime), httpServletRequest.getRequestURI()+"***"+httpServletRequest.getCookies().toString()+"***"+token);
            }else{
                logger.debug("开始计时: {}  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS")
                        .format(beginTime), httpServletRequest.getRequestURI());
            }
        }
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
