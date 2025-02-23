package com.spring.FoodMate.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.FoodMate.cart.dto.CartDTO;
import com.spring.FoodMate.mypage.dao.MypageDAO;
import com.spring.FoodMate.order.dao.OrderDAO;
import com.spring.FoodMate.order.dto.OrderAddressDTO;
import com.spring.FoodMate.order.dto.OrderDTO;
import com.spring.FoodMate.order.dto.OrderDetailDTO;
import com.spring.FoodMate.order.dto.OrderPaymentDTO;

@Service("OrderService")
public class OrderService {		
	@Autowired
	private OrderDAO orderDAO;
	
    public List<CartDTO> getCartItems(List<Integer> cartIdList) {
        return orderDAO.getCartItemsByIds(cartIdList);
    }
    
    public void setOrderList(OrderDTO order) {
        orderDAO.setOrderList(order);
    }
    
    public int getOrdId(Map<String, Object> order) {
        return orderDAO.getOrdId(order);
    }
    
    public void insertOrderDetail(OrderDetailDTO orderdetail) {
    	orderDAO.insertOrderDetail(orderdetail);
    }
    
    public void insertOrderAddress(OrderAddressDTO orderAddress) {
    	orderDAO.insertOrderAddress(orderAddress);
    }
    
    public void insertOrderPayment(OrderPaymentDTO orderPayment) {
    	orderDAO.insertOrderPayment(orderPayment);
    }
	
}
