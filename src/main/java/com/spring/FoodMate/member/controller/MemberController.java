package com.spring.FoodMate.member.controller;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.FoodMate.common.SessionDTO;
import com.spring.FoodMate.member.dto.BuyerDTO;
import com.spring.FoodMate.member.dto.SellerDTO;
import com.spring.FoodMate.member.service.MemberService;
import com.spring.FoodMate.mypage.service.ProfileService;

import io.github.cdimascio.dotenv.Dotenv;


@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private ProfileService profileService;
	@Autowired
	private BuyerDTO buyerDTO;
	@Autowired
	private SellerDTO sellerDTO;
	@Autowired
	private SocialLoginController sociallogincontroller; 

	private final Dotenv dotenv = Dotenv.load();
	
	private final String KAKAO_API_KEY = dotenv.get("KAKAO_REST_API");  //  카카오 REST API 키
	
	@RequestMapping(value="/member/loginForm", method=RequestMethod.GET)
	private ModelAndView Loginform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("kakao_API_key", KAKAO_API_KEY);
		return mav;
	}
	
	@RequestMapping(value="/member/*Form", method=RequestMethod.GET)
	private ModelAndView form(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
	@RequestMapping(value="/member/login" ,method = RequestMethod.POST)
	public ResponseEntity login(@RequestParam Map<String, String> loginMap,
			                  HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		buyerDTO=memberService.login(loginMap);
		if(buyerDTO!= null && buyerDTO.getByr_id()!=null){
			if(buyerDTO.getStatus().equals("DELETING")) {
				message  = "<script>";
			    message +=" alert('회원 탈퇴가 진행중인 아이디입니다. 관리자에게 문의해 주세요. Email : admin@foodmate.com');";
			    message += " location.href='"+request.getContextPath()+"/member/loginForm';";
			    message += " </script>";
				resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
				return resEntity;
			} else {
				HttpSession session=request.getSession();
				session.invalidate(); //한번 싹 지우고가자 
				session = request.getSession();
				SessionDTO sessionDTO = new SessionDTO();
				sessionDTO.setUserId(buyerDTO.getByr_id());
				sessionDTO.setUserRole("buyer");
				session.setAttribute("sessionDTO", sessionDTO);
				
				session.setAttribute("isBuyerLogOn", true);
				session.setAttribute("buyerInfo", buyerDTO);
				
				message  = "<script>";
			    message += " location.href='"+request.getContextPath()+"/main';";
			    message += " </script>";
			}
			
		}else{
		    message  = "<script>";
		    message +=" alert('아이디나 비밀번호가 잘못되었습니다. 다시 로그인해주세요.');";
		    message += " location.href='"+request.getContextPath()+"/member/loginForm';";
		    message += " </script>";
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
	@RequestMapping(value="/login/kakao" ,method = RequestMethod.GET)
	public ResponseEntity kakaologin(@RequestParam String code,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String accessToken = sociallogincontroller.getKakaoAccessToken(code, request);
		Map<String, Object> userInfo = sociallogincontroller.getKakaoUserInfo(accessToken);
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		Map<String, String> loginMap = new HashMap<>();
		// byr_id와 password 저장
        loginMap.put("byr_Id", (String)userInfo.get("id"));
        loginMap.put("password", "sociallogin");
        
		buyerDTO=memberService.login(loginMap);
		if(buyerDTO!= null && buyerDTO.getByr_id()!=null){
			if(buyerDTO.getStatus().equals("DELETING")) {
				message  = "<script>";
			    message +=" alert('회원 탈퇴가 진행중인 아이디입니다. 관리자에게 문의해 주세요. Email : admin@foodmate.com');";
			    message += " location.href='"+request.getContextPath()+"/member/loginForm';";
			    message += " </script>";
				resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
				return resEntity;
			} else {
				HttpSession session=request.getSession();
				session=request.getSession();
				session.setAttribute("isBuyerLogOn", true);
				session.setAttribute("buyerInfo", buyerDTO);
				
				message  = "<script>";
			    message += " location.href='"+request.getContextPath()+"/main';";
			    message += " </script>";
			}
			
		}else{
			HttpSession session = request.getSession();
			session.setAttribute("byr_Id", (String)userInfo.get("id"));
			session.setAttribute("password", "sociallogin");
			session.setAttribute("nickname", (String)userInfo.get("nickname"));
			session.setAttribute("profile_image", (String)userInfo.get("profile_link"));
		    message  = "<script>";
		    message +=" alert('가입된 아이디가 아닙니다. 회원가입 창으로 이동합니다.');";
		    message += " location.href='"+request.getContextPath()+"/member/signUpSocialForm';";
		    message += " </script>";
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
	@RequestMapping(value="/member/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) throws Exception {
	    // 세션 삭제
	    HttpSession session = request.getSession(false);  // 이미 세션이 있으면 가져오기
	    if (session != null) {
	        session.invalidate();  // 세션 무효화
	    }
	    return "redirect:/main";  // 로그인 페이지로 리다이렉트
	}
	
	@RequestMapping(value="/member/addBuyer" ,method = RequestMethod.POST)
	public ResponseEntity addBuyer(@ModelAttribute("buyerDTO") BuyerDTO _buyerDTO,
			                HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
            String byrid = _buyerDTO.getByr_id();
            
            memberService.addBuyer(_buyerDTO);
            profileService.addNewBuyerProfile(byrid);
		    
		    message  = "<script>";
		    message +=" alert('회원 가입을 마쳤습니다.로그인창으로 이동합니다.');";
		    message += " location.href='"+request.getContextPath()+"/member/loginForm';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요.');";
		    message += " location.href='"+request.getContextPath()+"/member/signUpForm';";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
	@RequestMapping(value="/member/overlapped.do" ,method = RequestMethod.POST)
	public ResponseEntity buyIDoverlapped(@RequestParam("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseEntity resEntity = null;
		String result = memberService.buyerIDoverlapped(id);
		resEntity =new ResponseEntity(result, HttpStatus.OK);
		return resEntity;
	}
	
	@RequestMapping(value="/member/updateBuyer" ,method = RequestMethod.POST)
	public ResponseEntity updateBuyer(@ModelAttribute("buyerDTO") BuyerDTO _buyerDTO,
			                HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			
			int birthYear = Integer.parseInt(_buyerDTO.getBirthyy());
			String originalSex = _buyerDTO.getSex();
			String updatedSex;
			if (birthYear >= 2000) {
			    updatedSex = (originalSex == "3" || originalSex == "4") ? originalSex : "3";
			} else {
			    updatedSex = (originalSex == "1" || originalSex == "2") ? originalSex : "1";
			}
			_buyerDTO.setSex(updatedSex);
			
			_buyerDTO.setBirth_6(_buyerDTO.getBirthyy().substring(2,4)+_buyerDTO.getBirthmm()+_buyerDTO.getBirthdd());
			
			_buyerDTO.setEmail(_buyerDTO.getEmail_id() + "@" + _buyerDTO.getEmail_domain());
			
			if (_buyerDTO.getPassword_confirm() == "" || !_buyerDTO.getPassword().equals(_buyerDTO.getPassword_confirm())) {
				message  = "<script>";
			    message +=" alert('비밀번호가 일치하지 않거나, 비밀번호를 입력하지 않았습니다.');";
			    message += " location.href='"+request.getContextPath()+"/mypage/myInfoManage/memberEditForm';";
			    message += " </script>";
			    resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
				return resEntity;
		    }
			memberService.updateBuyer(_buyerDTO);
			Map<String, String> loginMap = new HashMap<>();
			loginMap.put("byr_id", _buyerDTO.getByr_id());
			loginMap.put("password", _buyerDTO.getPassword());
			buyerDTO=memberService.login(loginMap);
			
			HttpSession session=request.getSession();
			session=request.getSession();
			session.setAttribute("buyerInfo", buyerDTO);
		    
		    message  = "<script>";
		    message +=" alert('성공적으로 회원 정보를 수정했습니다.');";
		    message += " location.href='"+request.getContextPath()+"/mypage/myInfoManage/memberEditForm';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요.');";
		    message += " location.href='"+request.getContextPath()+"/mypage/myInfoManage/memberEditForm';";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
	@RequestMapping(value="/member/addSocialBuyer" ,method = RequestMethod.POST)
	public ResponseEntity addSocialBuyer(@ModelAttribute("buyerDTO") BuyerDTO _buyerDTO,
			                HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
	            String baseDir = "C:/FoodMate/users/";
	            
	            String userId = _buyerDTO.getByr_id();
	            String uploadDir = baseDir + userId;
	            
	            File uploadPath = new File(uploadDir);
	            if (!uploadPath.exists()) {
	                uploadPath.mkdirs();
	            }
		    memberService.addBuyer(_buyerDTO);
		    
		    message  = "<script>";
		    message +=" alert('회원 가입을 마쳤습니다. 로그인창으로 이동합니다.');";
		    message += " location.href='"+request.getContextPath()+"/member/loginForm';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요.');";
		    message += " location.href='"+request.getContextPath()+"/member/signUpSocialForm';";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
	@RequestMapping(value="/member/deletebuyer", method = RequestMethod.POST)
	public ResponseEntity deleteBuyer(@RequestParam("password") String password,
			                HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		BuyerDTO buyerInfo = (BuyerDTO) session.getAttribute("buyerInfo"); // 세션에서 buyerInfo 가져오기
		
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			boolean isDeleted = memberService.deleteMember(buyerInfo.getByr_id(), password);
			if(isDeleted) {
				session.invalidate();
				message  = "<script>";
			    message += "alert('회원탈퇴 신청이 완료되었습니다.');";
			    message += " location.href='"+request.getContextPath()+"/main';";
			    message += " </script>";
			}else {
				message  = "<script>";
			    message += "alert('비밀번호가 일치하지 않습니다.');";
			    message += " location.href='"+request.getContextPath()+"/mypage/customerManage/cancleAccountForm';";
			    message += " </script>";
			}
		}catch(Exception e) {
			message  = "<script>";
		    message += "alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요.');";
		    message += " location.href='"+request.getContextPath()+"/mypage/customerManage/cancleAccountForm';";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
	@RequestMapping(value="/member/loginslr" ,method = RequestMethod.POST)
	public ResponseEntity loginslr(@RequestParam Map<String, String> loginMap,
			                  HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		sellerDTO=memberService.loginslr(loginMap);
		if(sellerDTO!= null && sellerDTO.getSlr_id()!=null){			
			if(sellerDTO.getStatus().equals("DELETING")) {
				message  = "<script>";
			    message +=" alert('회원 탈퇴가 진행중인 아이디입니다. 관리자에게 문의해 주세요. Email : admin@foodmate.com');";
			    message += " location.href='"+request.getContextPath()+"/member/loginForm';";
			    message += " </script>";
				resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
				return resEntity;
			} else {
				HttpSession session=request.getSession();
				session.invalidate(); //한번 싹 지우고가자
				session = request.getSession();
				session.setAttribute("isSellerLogOn", true);
				session.setAttribute("sellerInfo",sellerDTO);
				
				SessionDTO sessionDTO = new SessionDTO();
				sessionDTO.setUserId(sellerDTO.getSlr_id());
				sessionDTO.setUserRole("seller");
				session.setAttribute("sessionDTO", sessionDTO);
				
				
				message  = "<script>";
			    message += " location.href='"+request.getContextPath()+"/main';";
			    message += " </script>";
			}
			
		}else{
		    message  = "<script>";
		    message +=" alert('아이디나 비밀번호가 잘못되었습니다. 다시 로그인해주세요.');";
		    message += " location.href='"+request.getContextPath()+"/member/loginForm';";
		    message += " </script>";
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
	@RequestMapping(value="/member/addSeller" ,method = RequestMethod.POST)
	public ResponseEntity addSeller(@ModelAttribute("sellerDTO") SellerDTO _sellerDTO,
			                HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
		    memberService.addSeller(_sellerDTO);
		    
		    message  = "<script>";
		    message +=" alert('회원 가입을 마쳤습니다.로그인창으로 이동합니다.');";
		    message += " location.href='"+request.getContextPath()+"/member/loginForm';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요.');";
		    message += " location.href='"+request.getContextPath()+"/member/signUpSellerForm';";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
	@RequestMapping(value="/member/overlappedseller.do" ,method = RequestMethod.POST)
	public ResponseEntity sellerIDoverlapped(@RequestParam("id") String id,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseEntity resEntity = null;
		String result = memberService.sellerIDoverlapped(id);
		resEntity =new ResponseEntity(result, HttpStatus.OK);
		return resEntity;
	}
	


}
