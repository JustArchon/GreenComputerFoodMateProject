package com.spring.FoodMate.order.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.spring.FoodMate.order.dto.OrderAddressDTO;
import com.spring.FoodMate.order.dto.OrderDTO;
import com.spring.FoodMate.order.dto.OrderDetailDTO;
import com.spring.FoodMate.order.dto.OrderPaymentDTO;
import com.spring.FoodMate.order.dto.OrderRequestDTO;
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
	public String setOrderItems(@RequestBody OrderRequestDTO orderRequestDTO, HttpSession session) {
		
//		 System.out.println("받은 주문 ID 리스트: " + cartIds); // 디버깅용
		BuyerDTO buyerInfo = (BuyerDTO) session.getAttribute("buyerInfo");
		 
		// 받은 cartIds를 이용해 실제 주문 아이템 가져오기
	    List<CartDTO> cartItems = orderService.getCartItems(orderRequestDTO.getCartItems());

	    // FlashAttribute에 저장하여 order2에서도 사용 가능
	    session.setAttribute("orderItems", cartItems);
	    
	    Map<String, List<CartDTO>> groupedBySeller = cartItems.stream()
	    	    .collect(Collectors.groupingBy(cart -> cart.getSlr_id()));
	    
	    System.out.println(groupedBySeller);
	    
	    List<OrderDTO> orderList = new ArrayList<>();
	    List<Integer> orderNumberList = new ArrayList<>();
	    
	    // 주문 정보 등록
	    for (String slrId : groupedBySeller.keySet()) {
	        List<CartDTO> sellerCartList = groupedBySeller.get(slrId);

	        int totalProductPrice = sellerCartList.stream()
	            .mapToInt(cart -> cart.getPrice() * cart.getQty())
	            .sum();

	        int shippingFee = 3000;
	        
//	        System.out.println(totalProductPrice);

	        OrderDTO order = new OrderDTO();
	        order.setOrd_code(orderRequestDTO.getMerchantUid());
	        order.setByr_id(buyerInfo.getByr_id());
	        order.setSlr_id(slrId);
	        order.setTot_Pdt_Price(totalProductPrice);
	        order.setShip_Fee(shippingFee);
	        order.setOrd_Stat('1'); // '1' = 결제완료 상태
	        order.setCreate_Date(LocalDateTime.now());

	        orderList.add(order);
	    }
	    for (OrderDTO order : orderList) {
	    	orderService.setOrderList(order);
	    }
	    
	    // 주문 상세정보 등록 [sellerid, buyerid, ord_code활용]
	    for (String slrId : groupedBySeller.keySet()) {
	    	Map<String, Object> ordMap = new HashMap<>();
	    	ordMap.put("slr_id", slrId);
	    	ordMap.put("byr_id", buyerInfo.getByr_id());
	    	ordMap.put("ord_code", orderRequestDTO.getMerchantUid());
	    	int ordid = orderService.getOrdId(ordMap);
	    	
	    	// OrderDetail 추가
	    	List<CartDTO> filteredCartItems = cartItems.stream()
	    	        .filter(cart -> cart.getSlr_id().equals(slrId))
	    	        .collect(Collectors.toList());
	    	
	    	for (CartDTO cartItem : filteredCartItems) {
	    		OrderDetailDTO orderDetail = new OrderDetailDTO();
	            orderDetail.setOrd_id(ordid);
	            orderDetail.setPdt_id(cartItem.getPdt_id());
	            orderDetail.setPdt_name(cartItem.getPdt_name());
	            orderDetail.setPdt_price(cartItem.getPrice());
	            orderDetail.setQty(cartItem.getQty());

	            // 5️ DB에 삽입
	            orderService.insertOrderDetail(orderDetail);
	            
	            // 해야할것 payment, address 추가 ordid는 그대로for돌리면될듯? 어떻게든 form에있는거 가져올거생각해야할듯
	        }
	      // Address 정보 추가
	    	OrderAddressDTO orderAddress = orderRequestDTO.getOrderAddress();
	    	orderAddress.setOrdId(ordid);
	    	
	    	orderService.insertOrderAddress(orderAddress);
	    	
	    	
	      // Payment 정보 추가
	    	OrderPaymentDTO orderPayment = new OrderPaymentDTO();
	    	orderPayment.setOrd_id(ordid);
	    	orderPayment.setPay_Method(orderRequestDTO.getPayMethod());
	    	orderPayment.setPay_Status('1');
	    	orderPayment.setPg_id(orderRequestDTO.getPgId());
	    	
	    	orderService.insertOrderPayment(orderPayment);
	    }
	    
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
