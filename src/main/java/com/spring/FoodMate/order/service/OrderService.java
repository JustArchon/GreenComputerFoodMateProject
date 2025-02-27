package com.spring.FoodMate.order.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.FoodMate.cart.dto.CartDTO;
import com.spring.FoodMate.order.dao.OrderDAO;
import com.spring.FoodMate.order.dto.OrderAddressDTO;
import com.spring.FoodMate.order.dto.OrderDTO;
import com.spring.FoodMate.order.dto.OrderDTOoutput;
import com.spring.FoodMate.order.dto.OrderDetailDTO;
import com.spring.FoodMate.order.dto.OrderDetailDTOoutput;
import com.spring.FoodMate.order.dto.OrderPaymentDTO;

@Service("OrderService")
public class OrderService {		
	@Autowired
	private OrderDAO orderDAO;
	
    public List<CartDTO> getCartItems(List<Integer> cartIdList) throws Exception {
        return orderDAO.getCartItemsByIds(cartIdList);
    }
    
    public void setOrderList(OrderDTO order) throws Exception {
        orderDAO.setOrderList(order);
    }
    
    public int getOrdId(Map<String, Object> order) throws Exception {
        return orderDAO.getOrdId(order);
    }
    
    public void insertOrderDetail(OrderDetailDTO orderdetail) throws Exception {
    	orderDAO.insertOrderDetail(orderdetail);
    }
    
    public void insertOrderAddress(OrderAddressDTO orderAddress) throws Exception {
    	orderDAO.insertOrderAddress(orderAddress);
    }
    
    public void insertOrderPayment(OrderPaymentDTO orderPayment) throws Exception {
    	orderDAO.insertOrderPayment(orderPayment);
    }
	
    // 여기서부터 마이페이지에 쓸거
    // 출력 전용임.
    
    // 
    public List<OrderDTOoutput> getOrdersByByrId(String byr_id) throws Exception {
        return orderDAO.findOrdersByBuyer(byr_id);
    }
    
    public List<OrderDetailDTOoutput> getOrderDetailsByByrId(String byr_id) throws Exception {
    	return orderDAO.findOrderDetailsByBuyer(byr_id);
    }

    public List<OrderDTOoutput> getOrdersBySlrId(String slr_id, int ord_stat) throws Exception {
        return orderDAO.findOrdersBySeller(slr_id, ord_stat);
    }

    public List<OrderDetailDTOoutput> getOrderDetailsByOrderId(int ord_id) throws Exception {
        return orderDAO.findOrderDetailsByOrderId(ord_id);
    }
    
    // 주문번호로 주문 전체 가져오는거.
    // 현재 사용처 : 주문번호를 매개로 배송지테이블 조회하기 전 주문 당사자인지 확인
    public OrderDTOoutput getOrderByOrdId(int ord_id) throws Exception {
    	return orderDAO.findOrderByOrderId(ord_id);
    }
    
    public OrderAddressDTO getOrderAddressByOrdId(int ord_id) {
    	return orderDAO.getOrderAddressByOrdId(ord_id);
    }
    
    // 판매자의 운송장번호 업데이트에 쓰는거
    public void updateWaybill(int ord_id, String del_code, String waybill_num) {
        orderDAO.updateWaybill(ord_id, del_code, waybill_num);
    }
    
}