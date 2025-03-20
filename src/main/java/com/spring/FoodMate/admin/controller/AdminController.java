package com.spring.FoodMate.admin.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.FoodMate.admin.service.AdminService;
import com.spring.FoodMate.common.UtilMethod;
import com.spring.FoodMate.member.dto.BuyerDTO;
import com.spring.FoodMate.member.dto.SellerDTO;
import com.spring.FoodMate.order.dto.OrderPaymentDTO;
import com.spring.FoodMate.product.dto.ProductDTO;
import com.spring.FoodMate.recipe.dto.RecipeDTO;


@Controller
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value="/admin/adminMain", method=RequestMethod.GET)
	private ModelAndView main(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		
		return mav;
	}
	
	@RequestMapping(value="/admin/AccountManage/*", method=RequestMethod.GET)
	private ModelAndView AdminAccountManage(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, @RequestParam(value="tab", required=false) String tab, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능
		return mav;
	}

	@RequestMapping(value="/admin/AccountManage/adminUsers", method=RequestMethod.GET)
	private ModelAndView AdminAccountAdminUsers(
			@RequestParam(value="result", required=false) String result,
			@RequestParam(value="action",required=false) String action,
			@RequestParam(value="tab", required=false) String tab,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "searchtype", required = false, defaultValue = "") String searchtype,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		List<BuyerDTO> SleepingmemberList = adminService.getAdminBuyerInfo("SLEEP", keyword, searchtype);
		List<BuyerDTO> DeletingmemberList = adminService.getAdminBuyerInfo("DELETING", keyword, searchtype);
		List<BuyerDTO> ActivememberList = adminService.getAdminBuyerInfo("ACTIVE", keyword, searchtype);
		List<BuyerDTO> AllmemberList = adminService.getAdminBuyerInfo("", keyword, searchtype);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능
		mav.addObject("AllmemberList", AllmemberList);
		mav.addObject("ActivememberList", ActivememberList);
		mav.addObject("DeletingmemberList", DeletingmemberList);
		mav.addObject("SleepingmemberList", SleepingmemberList);
		return mav;
	}
	
	@RequestMapping(value="/admin/AccountManage/Admindeletememberprocess" ,method = RequestMethod.POST)
	public ResponseEntity Admindeletememberprocess(
			@RequestParam("byr_id") String byr_id,
	        @RequestParam("action") String action,
			                HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			if(action.equals("delete")) {
				adminService.deleteAdminBuyer(byr_id);
			} else {
				adminService.setActiveAdminBuyer(byr_id);
			}
		    
		    message  = "<script>";
		    message +=" alert('요청된 처리를 완료했습니다.');";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요.');";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
	@RequestMapping(value="/admin/AccountManage/adminSellers", method=RequestMethod.GET)
	private ModelAndView AdminAccountAdminSellers(
			@RequestParam(value="result", required=false) String result,
			@RequestParam(value="action",required=false) String action,
			@RequestParam(value="tab", required=false) String tab,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "searchtype", required = false, defaultValue = "") String searchtype,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		List<SellerDTO> SleepingmemberList = adminService.getAdminSellerInfo("SLEEP", keyword, searchtype);
		List<SellerDTO> AllmemberList = adminService.getAdminSellerInfo("", keyword, searchtype);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능
		mav.addObject("AllmemberList", AllmemberList);
		mav.addObject("SleepingmemberList", SleepingmemberList);
		return mav;
	}
	
	@RequestMapping(value="/admin/AdminManage/*", method=RequestMethod.GET)
	private ModelAndView AdminAdminManage(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, @RequestParam(value="tab", required=false) String tab, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능

		return mav;
	}
	
	@RequestMapping(value="/admin/InquiryManage/*", method=RequestMethod.GET)
	private ModelAndView AdminInquiryManage(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, @RequestParam(value="tab", required=false) String tab, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능

		return mav;
	}
	
	@RequestMapping(value="/admin/PaymentManage/*", method=RequestMethod.GET)
	private ModelAndView AdminPaymentCommon(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, @RequestParam(value="tab", required=false) String tab, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능

		return mav;
	}
	
	@RequestMapping(value="/admin/PaymentManage/adminPayments", method=RequestMethod.GET)
	private ModelAndView AdminPaymentManage(
			@RequestParam(value="result", required=false) String result,
			@RequestParam(value="action",required=false) String action,
			@RequestParam(value="tab", required=false) String tab,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "searchtype", required = false, defaultValue = "") String searchtype,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		List<OrderPaymentDTO> PaymentHistoryList = adminService.getAdminPaymentInfo("All", keyword, searchtype);
		List<OrderPaymentDTO> PaymentMonthStateList = adminService.getAdminMonthPaymentInfo(keyword, searchtype);
		List<OrderPaymentDTO> PaymentRefundList = adminService.getAdminPaymentInfo("Refund", keyword, searchtype);
		
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능
		mav.addObject("PaymentHistoryList", PaymentHistoryList);
		mav.addObject("PaymentMonthStateList", PaymentMonthStateList);
		mav.addObject("PaymentRefundList", PaymentRefundList);

		return mav;
	}
	
	@RequestMapping(value="/admin/RecipeProductManage/*", method=RequestMethod.GET)
	private ModelAndView AdminRecipeProductManage(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, @RequestParam(value="tab", required=false) String tab, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능

		return mav;
	}
	
	@RequestMapping(value="/admin/RecipeProductManage/adminRecipe", method=RequestMethod.GET)
	private ModelAndView AdminRecipeManage(
			@RequestParam(value="result", required=false) String result,
			@RequestParam(value="action",required=false) String action,
			@RequestParam(value="tab", required=false) String tab,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "searchtype", required = false, defaultValue = "") String searchtype,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		List<RecipeDTO> ReportedrecipeList = adminService.getAdminRecipeInfo("Reported", keyword, searchtype);
		List<RecipeDTO> AllrecipeList = adminService.getAdminRecipeInfo("Y", keyword, searchtype);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능
		mav.addObject("AllrecipeList", AllrecipeList);
		mav.addObject("ReportedrecipeList", ReportedrecipeList);
		return mav;
	}
	
	@RequestMapping(value="/admin/RecipeProductManage/adminProducts", method=RequestMethod.GET)
	private ModelAndView AdminProductsManage(
			@RequestParam(value="result", required=false) String result,
			@RequestParam(value="action",required=false) String action,
			@RequestParam(value="tab", required=false) String tab,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "searchtype", required = false, defaultValue = "") String searchtype,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		List<ProductDTO> StoppedProductList = adminService.getAdminProductInfo("N", keyword, searchtype);
		List<ProductDTO> ReportedProductList = adminService.getAdminProductInfo("Reported", keyword, searchtype);
		List<ProductDTO> AllProductList = adminService.getAdminProductInfo("Y", keyword, searchtype);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능
		mav.addObject("AllProductList", AllProductList);
		mav.addObject("ReportedProductList", ReportedProductList);
		mav.addObject("StoppedProductList", StoppedProductList);
		return mav;
	}
	
	@RequestMapping(value="/admin/StatisticsManage/*", method=RequestMethod.GET)
	private ModelAndView AdminStatisticsManage(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, @RequestParam(value="tab", required=false) String tab, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.addObject("tab", tab); // tab 값 추가하여 JSP에서 활용 가능

		return mav;
	}


}
