package com.spring.FoodMate.order.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.FoodMate.cart.dto.CartDTO;
import com.spring.FoodMate.common.UtilMethod;
import com.spring.FoodMate.member.dto.BuyerDTO;
import com.spring.FoodMate.mypage.service.DeliveryService;
import com.spring.FoodMate.order.service.OrderService;

import io.github.cdimascio.dotenv.Dotenv;

@Controller
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	OrderService orderService; 
	@Autowired
	DeliveryService deliveryService;
	
	private final Dotenv dotenv = Dotenv.load();
    private final String PimpUid = dotenv.get("PORTONE_IMP_UID");
	
	@RequestMapping(value="/order/order1")
	public ModelAndView orderPage(@RequestParam(value = "cartIds", required = false) String cartIds, @RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		BuyerDTO buyerInfo = (BuyerDTO) session.getAttribute("buyerInfo"); // 세션에서 buyerInfo 가져오기
		String byr_id = null;

		if (buyerInfo != null) {
		    byr_id = buyerInfo.getByr_id(); // byr_id 값 추출
		}
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName("common/layout");
		mav.addObject("impUid", PimpUid);
		mav.addObject("showNavbar", true);
		mav.addObject("title", "푸드 메이트");
		mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
		
		mav.addObject("deliveryList", deliveryService.getdeliveryList(byr_id)); // 배송지
		mav.addObject("buyerInfo", (BuyerDTO) session.getAttribute("buyerInfo"));
		if (cartIds != null && !cartIds.isEmpty()) {
	        List<Integer> cartIdList = Arrays.stream(cartIds.split(","))
	                                         .map(Integer::parseInt)
	                                         .collect(Collectors.toList());
	        List<CartDTO> cartItems = orderService.getCartItems(cartIdList);
	        mav.addObject("orderItems", cartItems);
	        mav.addObject("orderItemList", cartIdList);
	    }
		return mav;
	}
	
	@RequestMapping("/order/setOrderItems")
	public String setOrderItems(@RequestBody List<Integer> cartIds, HttpSession session) {
		
		 System.out.println("받은 주문 ID 리스트: " + cartIds); // 디버깅용
		 
		// 받은 cartIds를 이용해 실제 주문 아이템 가져오기
	    List<CartDTO> cartItems = orderService.getCartItems(cartIds);

	    // FlashAttribute에 저장하여 order2에서도 사용 가능
	    session.setAttribute("orderItems", cartItems);
	    
	    // 주문 완료 페이지로 리다이렉트
	    return "redirect:/order/order2";
	}
	
	@RequestMapping(value="/order/order2")
	public ModelAndView newsPage(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = UtilMethod.getViewName(request);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);
		List<CartDTO> orderItems = (List<CartDTO>) session.getAttribute("orderItems");
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName("common/layout");
		mav.addObject("impUid", PimpUid);
		mav.addObject("showNavbar", true);
		mav.addObject("title", "푸드 메이트");
		mav.addObject("body", "/WEB-INF/views" + viewName + ".jsp");
		
		// 주문 아이템 추가
	    mav.addObject("orderItems", orderItems);
		
		return mav;
	}
	
}
