package com.spring.FoodMate.mypage.controller;


import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.FoodMate.cart.exception.CartException;
import com.spring.FoodMate.common.SessionDTO;
import com.spring.FoodMate.common.exception.UnhandledException;
import com.spring.FoodMate.member.dto.BuyerDTO;
import com.spring.FoodMate.mypage.dto.DeliveryDTO;
import com.spring.FoodMate.mypage.dto.PointDTO;
import com.spring.FoodMate.mypage.dto.WishDTO;
import com.spring.FoodMate.mypage.service.DeliveryService;
import com.spring.FoodMate.mypage.service.PointService;
import com.spring.FoodMate.mypage.service.WishlistService;
import com.spring.FoodMate.order.dto.OrderDetailDTO;


@Controller
public class WishlistController {
	private static final Logger logger = LoggerFactory.getLogger(WishlistController.class);
	
	@Autowired
	private WishlistService wishlistService;
	
	@RequestMapping(value="/wishProductAdd" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> wishProductAdd(@RequestParam("productId") int pdt_id, @RequestParam("quantity") int qty,
			                HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		BuyerDTO buyerInfo = (BuyerDTO) session.getAttribute("buyerInfo"); // 세션에서 buyerInfo 가져오기
		String byr_id = null;
		Map<String, Object> response = new HashMap<>();
		
		if (buyerInfo != null) {
		    byr_id = buyerInfo.getByr_id(); // byr_id 값 추출
		}
		
		try {
			WishDTO wishDTO = new WishDTO();
			wishDTO.setByr_id(byr_id);
			wishDTO.setPdt_id(pdt_id);
			wishDTO.setQty(qty);
			
			wishlistService.addWishProduct(wishDTO);
			response.put("success", true);
			response.put("message", "성공적으로 찜 했습니다.");
			return response;
			
        } catch (Exception e) {
        	e.printStackTrace();
        	response.put("success", false);
	        response.put("message", "찜 작업을 실패했습니다.");
	        return response;
        }
    }
	
	@RequestMapping(value = "/wishlist/remove", method = RequestMethod.POST)
    @ResponseBody
    public void removeWishProduct(@RequestParam("wishlistId") int wishlist_id,
                                         HttpSession session) {
        try {
            wishlistService.deleteWishItem(wishlist_id);
        } catch (CartException e) {
            throw e;
        } catch (Exception e) {
            throw new UnhandledException("찜 상품을 제거하는 도중에 오류가 발생했습니다.", e);
        }
    }
}
