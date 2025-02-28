package com.spring.FoodMate.order.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.FoodMate.cart.dto.CartDTO;
import com.spring.FoodMate.common.SessionDTO;
import com.spring.FoodMate.common.exception.UnauthorizedException;
import com.spring.FoodMate.order.dao.OrderDAO;
import com.spring.FoodMate.order.dto.OrderAddressDTO;
import com.spring.FoodMate.order.dto.OrderDTO;
import com.spring.FoodMate.order.dto.OrderDTOoutput;
import com.spring.FoodMate.order.dto.OrderDetailDTO;
import com.spring.FoodMate.order.dto.OrderDetailDTOoutput;
import com.spring.FoodMate.order.dto.OrderPaymentDTO;
import com.spring.FoodMate.product.dto.ProductDTO;

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
    
    public List<OrderDTOoutput> getCancelOrdersByByrId(String byr_id) throws Exception {
        return orderDAO.findCancelOrdersByBuyer(byr_id);
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
    
    public OrderAddressDTO getOrderAddressByOrdId(int ord_id) throws Exception {
    	return orderDAO.getOrderAddressByOrdId(ord_id);
    }
    
    // 판매자의 운송장번호 업데이트에 쓰는거
    public void updateWaybill(int ord_id, String del_code, String waybill_num) throws Exception {
        orderDAO.updateWaybill(ord_id, del_code, waybill_num);
    }
    
    // 사용자가 주문한 상품 목록을 가져오는 메서드
    public List<ProductDTO> getOrderedProducts(String buyerId) throws Exception {
        return orderDAO.getOrderedProductsByBuyerId(buyerId);  // OrderDAO에서 가져오는 메소드
    }
    
    // 배송 상태 수정하려할때 그 주문이 요청자와 관계 있는 주문인지 확인하는 메서드
    public boolean isOwnOrder(String userId, int ord_id, String del_code, String waybill_num) throws Exception {
    	int result = orderDAO.isOwnOrder(userId, ord_id, del_code, waybill_num);
    	System.out.println("OrderService의 isOwnOrder에서 디버깅중 : 요청자와 관련있으면 0쵸과임 =" + result);
    	return result > 0;
    }
    
    // 택배사와 운송장번호를 받아서 그거랑 일치하는 행의 상태코드를 ord_stat으로 바꿔주는 메서드
    public boolean updateOrderStatus(int ord_id, String del_code, String waybill_num, int ord_stat) throws Exception {
    	return orderDAO.updateOrderStatus(ord_id, del_code, waybill_num, ord_stat) > 0;
    }
    
    public boolean updateOrdStatProcess(SessionDTO userInfo, OrderDTO deliInfo) throws Exception {
        // 해당 주문이 이 사용자의 주문인지 확인하는 로직 추가
        String userId = userInfo.getUserId();
        String userRole = userInfo.getUserRole();
        
        int ordId = deliInfo.getOrd_id();
        String delCode = deliInfo.getDel_Code();
        String waybillNum = deliInfo.getWaybill_Num();
        int ordStat = deliInfo.getOrd_Stat();

        if ("admin".equals(userRole) || userRole == null || !isOwnOrder(userId, ordId, delCode, waybillNum)) {
            throw new UnauthorizedException(106); // 주문이랑 관련없는 사람이 주문상태 수정하려고 하면 권한106오류
        }
        
        return updateOrderStatus(ordId, delCode, waybillNum, ordStat);
    }
}