package com.spring.FoodMate.member.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;


import com.spring.FoodMate.member.dto.BuyerDTO;
import com.spring.FoodMate.member.dto.SellerDTO;
import com.spring.FoodMate.member.service.MemberService;



@Controller
public class MemberFindController {
	private static final Logger logger = LoggerFactory.getLogger(MemberFindController.class);
	@Autowired
	private MemberService memberService;
	
	
	@RequestMapping(value="/memberfind/byrIdFind", method=RequestMethod.POST)
	public ModelAndView byrIdFind(@ModelAttribute("buyerVO") BuyerDTO _buyerVO,
	                              HttpServletRequest request, HttpServletResponse response) throws Exception {
	    request.setCharacterEncoding("utf-8");
	    
	    ModelAndView mav = new ModelAndView();
	    String foundId = null;

	    try {
	        // 아이디 찾기 로직 (예: memberService.findBuyerId 메서드 호출)
	        foundId = memberService.findBuyerId(_buyerVO);
	        
	        if (foundId != null && !foundId.isEmpty()) {
	            mav.setViewName("common/layout");  // 공통 레이아웃 적용
	            mav.addObject("body", "/WEB-INF/views/member/idFindResultForm.jsp"); // 실제 페이지 경로
	            mav.addObject("name", _buyerVO.getName());
	            mav.addObject("foundId", foundId); // 찾은 아이디를 뷰로 전달
	    		mav.addObject("smallHeader", true);
	    		mav.addObject("smallFooter", true);
	    		mav.addObject("title", "푸드 메이트");
	        } else {
	            // 아이디를 찾지 못했을 경우 메시지 처리
	            mav.setViewName("redirect:/member/buyeridFindForm?error=notfound");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        mav.setViewName("redirect:/member/buyeridFindForm?error=exception");
	    }

	    return mav;
	}
	
	@RequestMapping(value="/memberfind/ByrPasswordFind", method=RequestMethod.POST)
	public ModelAndView ByrPasswordFind(@ModelAttribute("buyerVO") BuyerDTO _buyerVO,
	                              HttpServletRequest request, HttpServletResponse response) throws Exception {
	    request.setCharacterEncoding("utf-8");
	    
	    ModelAndView mav = new ModelAndView();
	    String foundInfo = null;

	    try {
	        // 아이디 찾기 로직 (예: memberService.findBuyerId 메서드 호출)
	    	foundInfo = memberService.findBuyerId(_buyerVO);
	        
	        if (foundInfo != null && !foundInfo.isEmpty()) {
	        	HttpSession session = request.getSession();
	        	session.setAttribute("byr_Id", _buyerVO.getByr_id());
	            session.setAttribute("name", _buyerVO.getName());
	            session.setAttribute("email", _buyerVO.getEmail());
	            
	            mav.setViewName("common/layout");  // 공통 레이아웃 적용
	            mav.addObject("body", "/WEB-INF/views/member/buyerpasswdResetForm.jsp"); // 실제 페이지 경로
	    		mav.addObject("smallHeader", true);
	    		mav.addObject("smallFooter", true);
	    		mav.addObject("title", "푸드 메이트");
	        } else {
	            // 패스워드 리셋정보를 찾지 못했을 경우 메시지 처리
	        	mav.setViewName("redirect:/member/buyerpasswdFindForm?error=notfound");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        mav.setViewName("redirect:/member/buyerpasswdFindForm?error=exception");
	    }

	    return mav;
	}
	
	@RequestMapping(value="/memberfind/buyerPasswordReset" ,method = RequestMethod.POST)
	public ResponseEntity buyerPasswordReset(@ModelAttribute("buyerVO") BuyerDTO _buyerVO,
			                HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			HttpSession session = request.getSession();
			
			_buyerVO.setByr_id((String)session.getAttribute("byr_Id"));
			_buyerVO.setName((String)session.getAttribute("name"));
			_buyerVO.setEmail((String)session.getAttribute("email"));
		    memberService.resetBuyerPW(_buyerVO);
		    
		    
		    message  = "<script>";
		    message +=" alert('정상적으로 비밀번호가 변경되었습니다.');";
		    message += " location.href='"+request.getContextPath()+"/member/loginForm';";
		    message += " </script>";
		    session.invalidate();
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요.');";
		    message += " location.href='"+request.getContextPath()+"/member/ByrPasswordFind';";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
	@RequestMapping(value="/memberfind/slrIdFind", method=RequestMethod.POST)
	public ModelAndView slrIdFind(@ModelAttribute("sellerVO") SellerDTO _sellerVO,
	                              HttpServletRequest request, HttpServletResponse response) throws Exception {
	    request.setCharacterEncoding("utf-8");
	    
	    ModelAndView mav = new ModelAndView();
	    String foundId = null;

	    try {
	        // 아이디 찾기 로직 (예: memberService.findBuyerId 메서드 호출)
	        foundId = memberService.findSellerId(_sellerVO);
	        
	        if (foundId != null && !foundId.isEmpty()) {
	            mav.setViewName("common/layout");  // 공통 레이아웃 적용
	            mav.addObject("body", "/WEB-INF/views/member/idFindResultForm.jsp"); // 실제 페이지 경로
	            mav.addObject("name", _sellerVO.getName());
	            mav.addObject("foundId", foundId); // 찾은 아이디를 뷰로 전달
	    		mav.addObject("smallHeader", true);
	    		mav.addObject("smallFooter", true);
	    		mav.addObject("title", "푸드 메이트");
	        } else {
	            // 아이디를 찾지 못했을 경우 메시지 처리
	            mav.setViewName("redirect:/member/selleridFindForm?error=notfound");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        mav.setViewName("redirect:/member/selleridFindForm?error=exception");
	    }

	    return mav;
	}
	
	@RequestMapping(value="/memberfind/SlrPasswordFind", method=RequestMethod.POST)
	public ModelAndView ByrPasswordFind(@ModelAttribute("sellerVO") SellerDTO _sellerVO,
	                              HttpServletRequest request, HttpServletResponse response) throws Exception {
	    request.setCharacterEncoding("utf-8");
	    
	    ModelAndView mav = new ModelAndView();
	    String foundInfo = null;

	    try {
	        // 아이디 찾기 로직 (예: memberService.findBuyerId 메서드 호출)
	    	foundInfo = memberService.findSellerId(_sellerVO);
	        
	        if (foundInfo != null && !foundInfo.isEmpty()) {
	        	HttpSession session = request.getSession();
	        	session.setAttribute("slr_id", _sellerVO.getSlr_id());
	            session.setAttribute("name", _sellerVO.getName());
	            session.setAttribute("email", _sellerVO.getEmail());
	            
	            mav.setViewName("common/layout");  // 공통 레이아웃 적용
	            mav.addObject("body", "/WEB-INF/views/member/sellerpasswdResetForm.jsp"); // 실제 페이지 경로
	    		mav.addObject("smallHeader", true);
	    		mav.addObject("smallFooter", true);
	    		mav.addObject("title", "푸드 메이트");
	        } else {
	            // 패스워드 리셋정보를 찾지 못했을 경우 메시지 처리
	        	mav.setViewName("redirect:/member/sellerpasswdFindForm?error=notfound");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        mav.setViewName("redirect:/member/sellerpasswdFindForm?error=exception");
	    }

	    return mav;
	}
	

	
	@RequestMapping(value="/member/sellerPasswordReset" ,method = RequestMethod.POST)
	public ResponseEntity sellerPasswordReset(@ModelAttribute("sellerVO") SellerDTO _sellerVO,
			                HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			HttpSession session = request.getSession();
			
			_sellerVO.setSlr_id((String)session.getAttribute("slr_id"));
			_sellerVO.setName((String)session.getAttribute("name"));
			_sellerVO.setEmail((String)session.getAttribute("email"));
		    memberService.resetSellerPW(_sellerVO);
		    
		    message  = "<script>";
		    message +=" alert('정상적으로 비밀번호가 변경되었습니다.');";
		    message += " location.href='"+request.getContextPath()+"/member/loginForm';";
		    message += " </script>";
		    session.invalidate();
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요.');";
		    message += " location.href='"+request.getContextPath()+"/member/sellerPasswordReset';";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

}
