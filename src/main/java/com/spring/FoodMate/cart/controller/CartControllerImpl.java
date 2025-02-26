package com.spring.FoodMate.cart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.FoodMate.cart.dto.CartDTO;
import com.spring.FoodMate.cart.exception.CartException;
import com.spring.FoodMate.cart.service.CartService;
import com.spring.FoodMate.common.SessionDTO;
import com.spring.FoodMate.common.UtilMethod;
import com.spring.FoodMate.common.exception.UnhandledException;

@Controller
public class CartControllerImpl implements CartController {
    @Autowired
    CartService cartService;
    
    @Override
    @RequestMapping(value="/cart/cartlist", method=RequestMethod.GET)
    public ModelAndView cartlist(HttpServletRequest request, HttpSession session) {
        try {
        	SessionDTO userInfo = (SessionDTO) session.getAttribute("sessionDTO");
            Map<String, List<CartDTO>> groupedCart = cartService.getGroupedCartList(userInfo.getUserId());
            ModelAndView mav = new ModelAndView();
            mav.setViewName("common/layout");
            mav.addObject("title", "FoodMate - 장바구니");
            mav.addObject("showNavbar", true);
            mav.addObject("body", "/WEB-INF/views" + UtilMethod.getViewName(request) + ".jsp");
            mav.addObject("groupedCart", groupedCart);
            return mav;
        } catch (CartException e) {
            throw e;
        } catch (Exception e) {
            throw new UnhandledException("장바구니를 불러오는 중에 오류가 발생했습니다.", e);
        }
    }
    
    @Override
    @RequestMapping(value = "/cart/addtocart", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addToCart(@RequestParam("productId") int pdt_id,
                                         @RequestParam("quantity") int qty,
                                         HttpSession session) {
        try {
        	SessionDTO userInfo = (SessionDTO) session.getAttribute("sessionDTO");
            boolean isAdded = cartService.addToCart(pdt_id, qty, userInfo.getUserId());
            Map<String, Object> response = new HashMap<>();
            response.put("success", isAdded);
            response.put("message", isAdded ? "장바구니에 상품을 " + qty + "개 담았습니다. 계속 쇼핑하시겠습니까?" : "장바구니에 상품을 추가 중 오류가 발생했습니다. 잠시 후 다시 시도해 보세요.");
            return response;
        } catch (CartException e) {
            throw e;
        } catch (Exception e) {
            throw new UnhandledException("장바구니에 상품을 담는 중에 오류가 발생했습니다.", e);
        }
    }
    
    @RequestMapping(value = "/cart/addtocartmultiple", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addToCartMultipe(@RequestBody List<Map<String, Integer>> products, HttpSession session) {
        
    	Map<String, Object> response = new HashMap<>();
    	try {
        	SessionDTO userInfo = (SessionDTO) session.getAttribute("sessionDTO");
        	boolean allSuccess = true;
        	int allqty = 0;
            
            for (Map<String, Integer> product : products) {
                int pdt_id = product.get("productId");
                int qty = product.get("quantity");
                allqty += qty;

                boolean isAdded = cartService.addToCart(pdt_id, qty, userInfo.getUserId());
                if (!isAdded) {
                    allSuccess = false;
                }
            }
            
            response.put("success", allSuccess);
            response.put("message", allSuccess ? "장바구니에 상품을 " + allqty + "개 담았습니다. 계속 쇼핑하시겠습니까?" : "장바구니에 상품을 추가 중 오류가 발생했습니다. 잠시 후 다시 시도해 보세요.");
            return response;
        } catch (CartException e) {
            throw e;
        } catch (Exception e) {
            throw new UnhandledException("장바구니에 상품을 담는 중에 오류가 발생했습니다.", e);
        }
    }
    
    @RequestMapping(value = "/cart/compareaddtocart", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> compareAddToCart(@RequestBody List<Map<String, Object>> productList, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        try {
        	SessionDTO userInfo = (SessionDTO) session.getAttribute("sessionDTO");
            boolean allAdded = true;
            for (Map<String, Object> product : productList) {
            	int pdt_id = Integer.parseInt(String.valueOf(product.get("pdt_id")));
            	int qty = Integer.parseInt(String.valueOf(product.get("qty")));

                boolean isAdded = cartService.addToCart(pdt_id, qty, userInfo.getUserId());
                if (!isAdded) {
                    allAdded = false;
                }
            }

            response.put("success", allAdded);
            response.put("message", allAdded ? "장바구니에 상품을 추가했습니다. 계속 쇼핑하시겠습니까?" : "일부 상품을 추가하는 데 실패했습니다.");
            return response;

        } catch (CartException e) {
            throw e;
        } catch (Exception e) {
            throw new UnhandledException("장바구니에 상품을 담는 중에 오류가 발생했습니다.", e);
        }
    }

    @Override
    @RequestMapping(value = "/cart/updateQty", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateCartQuantity(@RequestParam("cart_id") int cart_id,
                                                  @RequestParam("qty") int qty) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("success", cartService.updateCartQuantity(cart_id, qty));
            return response;
        } catch (CartException e) {
            throw e;
        } catch (Exception e) {
            throw new UnhandledException("장바구니 상품 수량을 변경하는 중 오류가 발생했습니다.", e);
        }
    }
    
    @Override
    @RequestMapping(value = "/cart/deleteselected", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteSelected(@RequestBody Map<String, List<Integer>> request) {
        Map<String, Object> response = new HashMap<>();
        List<Integer> cartIds = request.get("cartIds");
        try {
            boolean success = true;
            for (int cartId : cartIds) {
                success &= cartService.deleteCartItem(cartId);
            }
            response.put("success", success);
            return response;
        } catch (CartException e) {
            throw e;
        } catch (Exception e) {
            throw new UnhandledException("장바구니 항목 삭제 중 오류가 발생했습니다.", e);
        }
    }
}