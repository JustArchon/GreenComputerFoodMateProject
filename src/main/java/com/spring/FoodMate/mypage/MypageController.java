package com.spring.FoodMate.mypage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MypageController {
	private static final Logger logger = LoggerFactory.getLogger(MypageController.class);
	
	@RequestMapping(value="/mypage/mypageForm", method=RequestMethod.GET)
	private ModelAndView mypageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName("common/layout");
		mav.addObject("showHeadermenu", true);
		mav.addObject("showSidebar",true);
		mav.addObject("sidebar","/WEB-INF/views/mypage/side.jsp");
		mav.addObject("title", "푸드 메이트");
		mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
		return mav;
	}
	
	@RequestMapping(value="/mypage/mateManage/*Form", method=RequestMethod.GET)
	private ModelAndView mateManageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName("common/layout");
		mav.addObject("showHeadermenu", true);
		mav.addObject("showSidebar",true);
		mav.addObject("sidebar","/WEB-INF/views/mypage/side.jsp");
		mav.addObject("title", "푸드 메이트");
		mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
		return mav;
	}
	
	@RequestMapping(value="/mypage/myInfoManage/*Form", method=RequestMethod.GET)
	private ModelAndView myInfoManageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName("common/layout");
		mav.addObject("showHeadermenu", true);
		mav.addObject("showSidebar",true);
		mav.addObject("sidebar","/WEB-INF/views/mypage/side.jsp");
		mav.addObject("title", "푸드 메이트");
		mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
		return mav;
	}
	
	@RequestMapping(value="/mypage/pointManage/*Form", method=RequestMethod.GET)
	private ModelAndView pointManageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName("common/layout");
		mav.addObject("showHeadermenu", true);
		mav.addObject("showSidebar",true);
		mav.addObject("sidebar","/WEB-INF/views/mypage/side.jsp");
		mav.addObject("title", "푸드 메이트");
		mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
		return mav;
	}
	
	@RequestMapping(value="/mypage/ShoppingManage/*Form", method=RequestMethod.GET)
	private ModelAndView ShoppingManageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName("common/layout");
		mav.addObject("showHeadermenu", true);
		mav.addObject("showSidebar",true);
		mav.addObject("sidebar","/WEB-INF/views/mypage/side.jsp");
		mav.addObject("title", "푸드 메이트");
		mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
		return mav;
	}
	
	@RequestMapping(value="/mypage/customerManage/*Form", method=RequestMethod.GET)
	private ModelAndView customerManageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName("common/layout");
		mav.addObject("showHeadermenu", true);
		mav.addObject("showSidebar",true);
		mav.addObject("sidebar","/WEB-INF/views/mypage/side.jsp");
		mav.addObject("title", "푸드 메이트");
		mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
		return mav;
	}
	
	// 여기부터 판매자 마이페이지
	
		@RequestMapping(value="/mypage_seller/mypage_sell_main", method=RequestMethod.GET)
		private ModelAndView seller_Mypage(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String viewName = getViewName(request);
			ModelAndView mav = new ModelAndView();
			mav.setViewName("common/layout");
			mav.addObject("showNavbar", true);
			mav.addObject("showSidebar",true);
			mav.addObject("sidebar","/WEB-INF/views/mypage_seller/side_selr.jsp");
			mav.addObject("title", "판매자 마이페이지");
			mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
			return mav;
		}
		
		@RequestMapping(value="/mypage_seller/mypage_sell_productlist", method=RequestMethod.GET)
		private ModelAndView seller_Mypage_productList(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String viewName = getViewName(request);
			ModelAndView mav = new ModelAndView();
			mav.setViewName("common/layout");
			mav.addObject("showNavbar", true);
			mav.addObject("showSidebar",true);
			mav.addObject("sidebar","/WEB-INF/views/mypage_seller/side_selr.jsp");
			mav.addObject("title", "내 상품 목록");
			mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
			return mav;
		}
		
		@RequestMapping(value="/mypage_seller/mypage_sell_productamount", method=RequestMethod.GET)
		private ModelAndView seller_Mypage_productAmount(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String viewName = getViewName(request);
			ModelAndView mav = new ModelAndView();
			mav.setViewName("common/layout");
			mav.addObject("showNavbar", true);
			mav.addObject("showSidebar",true);
			mav.addObject("sidebar","/WEB-INF/views/mypage_seller/side_selr.jsp");
			mav.addObject("title", "상품 재고 관리");
			mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
			return mav;
		}

	private String getViewName(HttpServletRequest request) throws Exception{
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if(uri == null || uri.trim().equals("")) {
			uri=request.getRequestURI();
		}
		int begin = 0;
		if(!((contextPath==null) || ("".equals(contextPath))))
		{
			begin=contextPath.length();
		}
		int end;
		if(uri.indexOf(";")!= -1) {
			end = uri.indexOf(":");
		} else if(uri.indexOf("?")!= -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}
		
		String fileName = uri.substring(begin, end);
		if (fileName.indexOf(".")!= -1) {
			fileName = fileName.substring(0,fileName.lastIndexOf("."));
		}
		if (fileName.lastIndexOf("/")!=-1) {
			fileName = fileName.substring(fileName.lastIndexOf("/",1),fileName.length());
		}
		return fileName;
	}

}
