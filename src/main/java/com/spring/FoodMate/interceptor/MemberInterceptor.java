package com.spring.FoodMate.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.spring.FoodMate.common.SessionDTO;
import com.spring.FoodMate.common.UtilMethod;

public class MemberInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
    

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, org.springframework.web.servlet.ModelAndView mav) throws Exception {
        if (mav != null) {
        	HttpSession session = request.getSession();
        	String viewName = UtilMethod.getViewName(request);
    		mav.setViewName("common/layout");
    		mav.addObject("smallHeader", true);
    		mav.addObject("smallFooter", true);
    		mav.addObject("title", "푸드 메이트");
    		mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}