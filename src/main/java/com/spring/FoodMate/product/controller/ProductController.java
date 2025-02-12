package com.spring.FoodMate.product.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.spring.FoodMate.common.SessionDTO;
import com.spring.FoodMate.common.Util;
import com.spring.FoodMate.product.dto.CategoryDTO;
import com.spring.FoodMate.product.dto.ProductDTO;
import com.spring.FoodMate.product.service.ProductService;

@Controller
public class ProductController {
	@Autowired
	private ProductDTO productVO;
	@Autowired
	private CategoryDTO categoryDTO;
	@Autowired
	private ProductService productService;
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@RequestMapping(value="/product/pdtlist", method=RequestMethod.GET)
	public ModelAndView pdtist(
	    @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, //keyword=검색어, 없으면 빈문자열로 바꿔줌
	    HttpServletRequest request, HttpServletResponse response
	) throws Exception {
	    String viewName = Util.getViewName(request);
	    ModelAndView mav = new ModelAndView();
	    List<ProductDTO> searchList = productService.pdtList(keyword);
	    // Service 에 keyword(검색어)를 주고 해당하는 상품VO의 List를 받아옴.
	    mav.setViewName("common/layout");
	    mav.addObject("showNavbar", true);
	    mav.addObject("title", "FoodMate-상품 검색창");
	    mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
	    mav.addObject("list", searchList);
	    return mav;
	}
	
	@RequestMapping(value="/mypage_seller/ms_pdtlist", method=RequestMethod.GET)
	public ModelAndView ms_pdtist(HttpServletRequest request, HttpSession session) throws Exception {
		    String viewName = Util.getViewName(request);
		    // 필터 나중에 해
		    SessionDTO sellerInfo = (SessionDTO)session.getAttribute("sessionDTO");
		    String slr_id = sellerInfo.getUserId();
		    
		    ModelAndView mav = new ModelAndView();
		    List<ProductDTO> searchList = productService.ms_pdtList(slr_id);
		    // Service 에 keyword(검색어)를 주고 해당하는 상품VO의 List를 받아옴.
		    mav.setViewName("common/layout");
		    mav.addObject("showNavbar", true);
		    mav.addObject("title", "FoodMate-상품 검색창");
		    mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
		    mav.addObject("list", searchList);
		    return mav;
		}

	@RequestMapping(value="/product/pdtdetail", method=RequestMethod.GET)
	public ModelAndView pdtdetail(
			@RequestParam(value = "pdt_id", required = true) int pdt_id,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = Util.getViewName(request);
		ModelAndView mav = new ModelAndView();
		
		ProductDTO product = productService.select1PdtByPdtId(pdt_id);
		List<CategoryDTO> categoryStep = productService.categoryStep(product.getCategory_id());

		mav.setViewName("common/layout");
		mav.addObject("showNavbar", true);
		mav.addObject("title", "제품 상세정보");
		mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
		mav.addObject("pdt", product);
		Collections.reverse(categoryStep);
		mav.addObject("category", categoryStep);
		return mav;
	}

	@RequestMapping(value="/product/pdtaddform", method=RequestMethod.GET)
	public ModelAndView pdtaddForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = Util.getViewName(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("common/layout");
		mav.addObject("showNavbar", true);
		mav.addObject("title", "상품 등록");
		mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
		
		List<CategoryDTO> categories = productService.getGrandCategoryList();
		mav.addObject("categories", categories);
		
		return mav;
	}
	
	@RequestMapping(value="/product/pdteditform", method=RequestMethod.GET)
	public ModelAndView pdteditForm(@RequestParam(value = "pdt_id", required = true) int pdt_id,
			HttpServletRequest request, HttpSession session) throws Exception {
		//pdt_id값이 안들어왔을 때 오류처리를 해야함
		String viewName = Util.getViewName(request);
		// 받은 pdt_id로 상품 테이블 뒤져서 있는지 확인하고
		ProductDTO needEdit = productService.select1PdtByPdtId(pdt_id);
		
		// 없으면 오류처리 해야함
		
		SessionDTO sellerInfo = (SessionDTO)session.getAttribute("sessionDTO");
		if( !sellerInfo.getUserId().equals( needEdit.getSlr_id() ) ) {
			// 수정 권한이 없다는 오류 처리
		} else {
			
		}
		
		;
		needEdit.getSlr_id();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("common/layout");
		mav.addObject("showNavbar", true);
		mav.addObject("title", "상품 등록");
		mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
		
		List<CategoryDTO> categories = productService.getGrandCategoryList();
		mav.addObject("categories", categories);
		
		return mav;
	}
	
	@RequestMapping(value="/product/pdtadd", method=RequestMethod.POST)
	public ModelAndView pdtadd(@ModelAttribute ProductDTO newPdt,
	        @RequestParam("lastCategoryId") int lastCategoryId,
	        @RequestParam("pdt_Img") MultipartFile pdtImg, HttpSession session,
	        HttpServletRequest request, HttpServletResponse response) throws Exception {
	    
	    String imagePath = Util.savePdtImage(request, pdtImg);
	    SessionDTO sessionDTO = (SessionDTO)session.getAttribute("sessionDTO");
	    String slr_id = sessionDTO.getUserId();
	    
	    newPdt.setSlr_id(slr_id);
	    newPdt.setImg_path(imagePath);
	    newPdt.setCategory_id(lastCategoryId);
	    
	    int result = productService.insertNewProduct(newPdt);
	    
	    // 결과에 따라서 메시지 출력하기
	    // 성공하면 "상품 추가되었고 마이페이지로 이동합니다" 하면서 마이페이지
	    // 메서드 Model and view 에서 String 같은걸로 바꿔야할수있음
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("common/layout");
	    mav.addObject("showNavbar", true);
	    mav.addObject("title", "푸메");
	    mav.addObject("body", "/WEB-INF/views/main/main.jsp");
	    return mav;
	}

	@RequestMapping(value="/getSubCategories/{category_id}", method=RequestMethod.GET)
	@ResponseBody
	public List<CategoryDTO> getSubCategories(@PathVariable("category_id") int category_id) throws Exception {
	    // 데이터베이스에서 category_id에 해당하는 자식 카테고리 가져오기
	    List<CategoryDTO> subCategories = productService.getChildCategoryList(category_id);
	    return subCategories;
	}

	@RequestMapping(value="/product/compare", method=RequestMethod.GET)
	public ModelAndView compare(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = Util.getViewName(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("common/layout");
		mav.addObject("showNavbar", true);
		mav.addObject("title", "재료 비교");
		mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
		return mav;
	}
}