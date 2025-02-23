package com.spring.FoodMate.order.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.FoodMate.cart.dto.CartDTO;
import com.spring.FoodMate.member.dto.BuyerDTO;
import com.spring.FoodMate.member.dto.SellerDTO;
import com.spring.FoodMate.order.dto.OrderAddressDTO;
import com.spring.FoodMate.order.dto.OrderDTO;
import com.spring.FoodMate.order.dto.OrderDetailDTO;
import com.spring.FoodMate.order.dto.OrderPaymentDTO;

@Repository("orderDAO")
public class OrderDAO {
	@Autowired
	private SqlSession sqlSession;	
	
    public List<CartDTO> getCartItemsByIds(List<Integer> cartIdList) {
        return sqlSession.selectList("mapper.order.getCartItemsByIds", cartIdList);
    }
    
    public void setOrderList(OrderDTO order) {
    	sqlSession.insert("mapper.order.insertOrderList", order);
    }
    
    public int getOrdId(Map<String, Object> order) {
    	return sqlSession.selectOne("mapper.order.selectOrdId",order);
    }
    
    public void insertOrderDetail(OrderDetailDTO orderdetail) {
    	sqlSession.insert("mapper.order.insertOrderDetail", orderdetail);
    }
    
    public void insertOrderAddress(OrderAddressDTO orderAddress) {
    	sqlSession.insert("mapper.order.insertOrderAddress", orderAddress);
    }
    
    public void insertOrderPayment(OrderPaymentDTO orderPayment) {
    	sqlSession.insert("mapper.order.insertOrderPayment", orderPayment);
    }
}
