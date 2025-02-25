package com.spring.FoodMate.mypage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.FoodMate.common.SessionDTO;
import com.spring.FoodMate.member.dto.BuyerDTO;
import com.spring.FoodMate.mypage.dto.ProfileDTO;
import com.spring.FoodMate.mypage.service.DeliveryService;
import com.spring.FoodMate.mypage.service.MypageService;
import com.spring.FoodMate.mypage.service.PointService;
import com.spring.FoodMate.mypage.service.ProfileService;
import com.spring.FoodMate.order.dto.OrderDTOoutput;
import com.spring.FoodMate.order.dto.OrderDetailDTOoutput;
import com.spring.FoodMate.order.service.OrderService;
import com.spring.FoodMate.product.dto.ProductDTO;
import com.spring.FoodMate.product.service.ProductService;
import com.spring.FoodMate.recipe.service.RecipeService;


@Controller
public class MypageController {
	@Autowired
	private MypageService mypageService;
	@Autowired
	private ProductService productService;
	@Autowired
    private RecipeService recipeService;
	@Autowired
    private ProfileService profileService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private PointService pointService;
	
	@RequestMapping(value="/mypage/mypageForm", method=RequestMethod.GET)
	private ModelAndView mypageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		
		BuyerDTO buyerInfo = (BuyerDTO) session.getAttribute("buyerInfo"); // 세션에서 buyerInfo 가져오기
		String byr_id = null;

		if (buyerInfo != null) {
		    byr_id = buyerInfo.getByr_id(); // byr_id 값 추출
		}
		
		ProfileDTO profileDTO = profileService.getBuyerProfile(byr_id);
		
		List<OrderDTOoutput> orders = orderService.getOrdersByByrId(byr_id);
		
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("myrecipeList", recipeService.selectRecipeListByrID(byr_id)); //서비스에 selectRecipeList메소드있어야함
		mav.addObject("orderList", orderService.getOrdersByByrId(byr_id));
		mav.addObject("profile", profileDTO);
		return mav;
	}
	
	@RequestMapping(value="/mypage/mateManage/myrecipeForm", method=RequestMethod.GET)
	private ModelAndView mateManagemyrecipeform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		
		BuyerDTO buyerInfo = (BuyerDTO) session.getAttribute("buyerInfo"); // 세션에서 buyerInfo 가져오기
		String byr_id = null;

		if (buyerInfo != null) {
		    byr_id = buyerInfo.getByr_id(); // byr_id 값 추출
		}
		
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("myrecipeList", recipeService.selectRecipeListByrID(byr_id)); //서비스에 selectRecipeList메소드있어야함
		return mav;
	}
	
	@RequestMapping(value="/mypage/mateManage/*Form", method=RequestMethod.GET)
	private ModelAndView mateManageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		return new ModelAndView();
	}
	
	@RequestMapping(value="/mypage/myInfoManage/memberEditForm", method=RequestMethod.GET)
	private ModelAndView myInfoManageMemberEditform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ModelAndView mav = new ModelAndView();
		mav.addObject("buyerInfo", (BuyerDTO) session.getAttribute("buyerInfo"));
		return mav;
	}
	
	@RequestMapping(value="/mypage/myInfoManage/profileEditForm", method=RequestMethod.GET)
	private ModelAndView profileEditform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		BuyerDTO buyerInfo = (BuyerDTO) session.getAttribute("buyerInfo"); // 세션에서 buyerInfo 가져오기
		String byr_id = null;

		if (buyerInfo != null) {
		    byr_id = buyerInfo.getByr_id(); // byr_id 값 추출
		}
		
		ProfileDTO profileDTO = profileService.getBuyerProfile(byr_id);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("profile", profileDTO);
		return mav;
	}
	
	@RequestMapping(value="/mypage/myInfoManage/deliveryManageForm", method=RequestMethod.GET)
	private ModelAndView deliveryManageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		BuyerDTO buyerInfo = (BuyerDTO) session.getAttribute("buyerInfo"); // 세션에서 buyerInfo 가져오기
		String byr_id = null;

		if (buyerInfo != null) {
		    byr_id = buyerInfo.getByr_id(); // byr_id 값 추출
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("deliveryList", deliveryService.getdeliveryList(byr_id));
		return mav;
	}
	
	@RequestMapping(value="/mypage/pointManage/*Form", method=RequestMethod.GET)
	private ModelAndView PManageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		return new ModelAndView();
	}
	
	@RequestMapping(value="/mypage/pointManage/pointManageForm", method=RequestMethod.GET)
	private ModelAndView pointManageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		BuyerDTO buyerInfo = (BuyerDTO) session.getAttribute("buyerInfo"); // 세션에서 buyerInfo 가져오기
		String byr_id = null;

		if (buyerInfo != null) {
		    byr_id = buyerInfo.getByr_id(); // byr_id 값 추출
		}
		session.setAttribute("action", action);
		ModelAndView mav = new ModelAndView();
		mav.addObject("pointLogList", pointService.getpointLogList(byr_id));
		return new ModelAndView();
	}
	
	@RequestMapping(value="/mypage/ShoppingManage/*Form", method=RequestMethod.GET)
	private ModelAndView ShoppingManageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("action", action);	
		return new ModelAndView();
	}
	
	// 구매자의 주문내역 확인
	@RequestMapping(value="/mypage/ShoppingManage/orderlist", method=RequestMethod.GET)
	private ModelAndView orderlist(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		SessionDTO sessionDTO = (SessionDTO) session.getAttribute("sessionDTO");
		// 아이디 꺼내와서
		
		List<OrderDTOoutput> orders = orderService.getOrdersByByrId(sessionDTO.getUserId());
		// 아이디 넣어서 오더들 output전용으로 가져오기

		for (OrderDTOoutput order : orders) { // List인 orders에서 하나씩 OrderDTOoutput을 꺼내서 order에 저장, 자동반복
            List<OrderDetailDTOoutput> orderDetails = orderService.getOrderDetailsByOrderId(order.getOrd_id());
            // 현재 ord_id 갖고와서 orderService의 그 긴거 메서드로 주문상세정보 리스트로 가져옴
            order.setOrderDetails(orderDetails);
            //orderDTOoutput에 그 리스트 저장함
        }
		mav.addObject("orderList", orders);
		// orderLilst 속성으로 그 긴거 전달
		return mav;
	}
	
	// 판매자의 주문내역 확인
	@RequestMapping(value="/mypage_seller/orderlist_ready", method=RequestMethod.GET)
	private ModelAndView slrOrderList(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		SessionDTO sessionDTO = (SessionDTO) session.getAttribute("sessionDTO");
		// 아이디 꺼내올준비
		
		List<OrderDTOoutput> orders = orderService.getOrdersBySlrId(sessionDTO.getUserId());
		// 아이디 넣어서 오더들 output전용으로 가져오기

		for (OrderDTOoutput order : orders) { // List인 orders에서 하나씩 OrderDTOoutput을 꺼내서 order에 저장, 자동반복
            List<OrderDetailDTOoutput> orderDetails = orderService.getOrderDetailsByOrderId(order.getOrd_id());
            // 현재 ord_id 갖고와서 orderService의 그 긴거 메서드로 주문상세정보 리스트로 가져옴
            order.setOrderDetails(orderDetails);
            //orderDTOoutput에 그 리스트 저장함
        }
		mav.addObject("title", "FoodMate - 배송대기 주문");
		mav.addObject("orderList", orders);
		// orderLilst 속성으로 그 긴거 전달
		return mav;
	}
	
	@RequestMapping(value="/mypage/customerManage/*Form", method=RequestMethod.GET)
	private ModelAndView customerManageform(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		return new ModelAndView();
	}
	
	// 여기부터 판매자 마이페이지
	
	@RequestMapping(value="/mypage_seller/mypage_sell_main", method=RequestMethod.GET)
	private ModelAndView seller_Mypage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", "FoodMate - 판매자 마이페이지");
		return mav;
	}
	
	@RequestMapping(value="/mypage_seller/mypage_sell_productlist", method=RequestMethod.GET)
	public ModelAndView seller_Mypage_productList(HttpServletRequest request, HttpSession session) throws Exception {
		    SessionDTO sellerInfo = (SessionDTO)session.getAttribute("sessionDTO");
		    List<ProductDTO> searchList = productService.ms_pdtList(sellerInfo.getUserId());
		    
		    ModelAndView mav = new ModelAndView();
            mav.addObject("title", "FoodMate - 내 상품 목록");
		    mav.addObject("list", searchList);
		    // Service 에 판매자 ID를 주고 해당하는 상품VO들의 List를 받아옴.
		    return mav;
	}
	
	
	
	@RequestMapping(value="/mypage_seller/mypage_sell_productamount", method=RequestMethod.GET)
	private ModelAndView seller_Mypage_productAmount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("title", "FoodMate - 상품 재고 관리");
		return mav;
	}

}
