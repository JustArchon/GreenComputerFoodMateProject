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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.FoodMate.cart.dto.CartDTO;
import com.spring.FoodMate.cart.service.CartService;
import com.spring.FoodMate.common.SessionDTO;
import com.spring.FoodMate.common.UtilMethod;
import com.spring.FoodMate.common.exception.UnauthorizedException;
import com.spring.FoodMate.member.dto.BuyerDTO;
import com.spring.FoodMate.mypage.dto.PointDTO;
import com.spring.FoodMate.mypage.service.DeliveryService;
import com.spring.FoodMate.mypage.service.PointService;
import com.spring.FoodMate.order.dto.OrderAddressDTO;
import com.spring.FoodMate.order.dto.OrderDTO;
import com.spring.FoodMate.order.dto.OrderDTOoutput;
import com.spring.FoodMate.order.dto.OrderDetailDTO;
import com.spring.FoodMate.order.dto.OrderPaymentDTO;
import com.spring.FoodMate.order.dto.OrderRequestDTO;
import com.spring.FoodMate.order.exception.OrderException;
import com.spring.FoodMate.order.service.OrderService;

import io.github.cdimascio.dotenv.Dotenv;

@Controller
public class OrderController {
	@Autowired
	OrderService orderService; 
	@Autowired
	DeliveryService deliveryService;
	@Autowired
	CartService cartService;
	@Autowired
	PointService pointService;
	
	
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
	public String setOrderItems(@RequestBody OrderRequestDTO orderRequestDTO, HttpSession session) throws Exception {
		
//		 System.out.println("받은 주문 ID 리스트: " + cartIds); // 디버깅용
		BuyerDTO buyerInfo = (BuyerDTO) session.getAttribute("buyerInfo");
		 
		// 받은 cartIds를 이용해 실제 주문 아이템 가져오기
	    List<CartDTO> cartItems = orderService.getCartItems(orderRequestDTO.getCartItems());
	    int used_point = orderRequestDTO.getUsed_point();
	    // FlashAttribute에 저장하여 order2에서도 사용 가능
	    session.setAttribute("orderItems", cartItems);
	    session.setAttribute("used_point", used_point);
	    
	    Map<String, List<CartDTO>> groupedBySeller = cartItems.stream()
	    	    .collect(Collectors.groupingBy(cart -> cart.getSlr_id()));
	    
	    List<OrderDTO> orderList = new ArrayList<>();
	    List<Integer> orderNumberList = new ArrayList<>();
	    String byrId = buyerInfo.getByr_id();
	    
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
	        order.setByr_id(byrId);
	        order.setSlr_id(slrId);
	        order.setTot_Pdt_Price(totalProductPrice - used_point);
	        order.setUsed_point(used_point);
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
	    	ordMap.put("byr_id", byrId);
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
	    
	    cartService.deleteCartByrID(buyerInfo.getByr_id());
	    
		PointDTO pointDTO = new PointDTO();
		pointDTO.setByr_id(byrId);
		pointDTO.setAmount(used_point);
		pointDTO.setDescription(used_point + "원 사용");
		pointDTO.setPoint_type("상품 구매 사용");
	    pointService.usePoint(pointDTO);
	    
	    
	    buyerInfo.setPoints(pointService.inquiryPoints(byrId));
	    session.setAttribute("buyerInfo", buyerInfo);
	    
	    // 주문 완료 페이지로 리다이렉트
	    return "redirect:/order/order2";
	}
	
	@RequestMapping(value="/order/order2")
	public ModelAndView orderResultPage(@RequestParam(value="result", required=false) String result, @RequestParam(value="action",required=false) String action, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
	
	@RequestMapping(value="/order/getAddress", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> getOrderAddress(@RequestParam("ord_id") int ord_id, HttpSession session) throws Exception {
        Map<String, Object> result = new HashMap<>();
        // 현재 로그인한 이용자 정보 가져오기
        SessionDTO userInfo = (SessionDTO) session.getAttribute("sessionDTO");
        if (userInfo == null) {
        	throw new UnauthorizedException(101);
        }
        String userId = userInfo.getUserId();
        String userRole = userInfo.getUserRole();
        
        // 버튼이 준 ord_id로 주문 테이블에서 모든 정보를 가져옴
        OrderDTOoutput order = orderService.getOrderByOrdId(ord_id);
        
        // 주문정보가 없으면 주문없음 예외 보내기
        if (order == null) {throw new OrderException(101);}
        
        // 주문 정보 조회 (유저 권한 검증)
        if(userRole.equals("buyer")) { // 구매자로 로그인했을 때는
            if(!order.getByr_id().equals(userId)) {throw new UnauthorizedException(106);}
            // 니 ID가 주문테이블에서 가져온 주문의 구매자 id와 다르면 접근권한없음 오류 던지기
        } else if(userRole.equals("seller")) { // 판매자로 로그인했을 때는
        	if(!order.getSlr_id().equals(userId)) {throw new UnauthorizedException(106);}
        	// 니 ID가 주문테이블에서 가져온 주문의 판매자 id와 다르면 접근권한없음 오류 던지기
        }

        // 배송지 정보 조회
        OrderAddressDTO address = orderService.getOrderAddressByOrdId(ord_id);
        if (address != null) {
            result.put("status", "success");
            result.put("data", address);
        } else {
            result.put("status", "error");
            result.put("message", "배송지 정보가 없습니다.");
        }
        return result;
    }
	
	// 판매자의 운송장번호 저장
	@RequestMapping(value="/order/updateWaybill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateWaybill(@RequestParam("ord_id") int ordId,
	                                         @RequestParam("del_code") String delCode,
	                                         @RequestParam("waybill_num") String waybillNum) {
	    Map<String, Object> result = new HashMap<>();
	    try {
	        orderService.updateWaybill(ordId, delCode, waybillNum);
	        result.put("success", true);
	    } catch (Exception e) {
	        result.put("success", false);
	        result.put("message", e.getMessage());
	    }
	    return result;
	}
	
	@RequestMapping(value = "/order/updateStatus", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> updateOrderStatusTo3(@RequestBody OrderDTO deliInfo, HttpSession session) throws Exception {
        Map<String, Object> response = new HashMap<>();
        System.out.println("오더컨트롤러에서 디버깅중 : " + deliInfo.toString());
        
    	SessionDTO userInfo = (SessionDTO) session.getAttribute("sessionDTO");
    	boolean result = orderService.updateOrdStatProcess(userInfo, deliInfo, 3); // 추가값을 받아오는지 아닌지 여기서 판단해서 할것
    	
        if (result) {
            response.put("status", "success");
            response.put("message", "주문 상태가 업데이트되었습니다.");
        } else {
            response.put("status", "error");
            response.put("message", "주문 상태 업데이트 실패.");
        }

        return response;  // 응답으로 Map을 반환
    }
	
	@RequestMapping(value = "/order/updateStatusajax", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> updateOrderStatusAjax(@RequestBody Map<String, Object> requestData, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
        	boolean result = orderService.updateOrderStatusByOrdCode((String) requestData.get("slr_id"), (String) requestData.get("ord_code"), (int) requestData.get("status")); // 3은 배송중

            if (result) {
                response.put("status", "success");
                response.put("message", "주문 상태가 업데이트되었습니다.");
                return ResponseEntity.ok(response); // ✅ HTTP 200 응답 보장
            } else {
                response.put("status", "error");
                response.put("message", "주문 상태 업데이트 실패.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // ✅ HTTP 400 응답
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "서버 오류 발생");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // ✅ HTTP 500 응답
        }
    }
}