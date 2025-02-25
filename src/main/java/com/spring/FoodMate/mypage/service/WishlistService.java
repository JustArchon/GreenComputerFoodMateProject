package com.spring.FoodMate.mypage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.FoodMate.mypage.dao.DeliveryDAO;
import com.spring.FoodMate.mypage.dto.DeliveryDTO;


@Service("wishlistService")
//@Transactional(propagation=Propagation.REQUIRED)
public class WishlistService {
	
	@Autowired
	private DeliveryDAO deliveryDAO;
	
	public List<DeliveryDAO> getdeliveryList(String id) throws Exception {
		return deliveryDAO.getdeliveryList(id);
	}
	
	public void adddelivery(DeliveryDTO deliveryDTO) {
		deliveryDAO.adddelivery(deliveryDTO);
	}
	
	public void resetdelivery(String id) {
		deliveryDAO.resetdelivery(id);
	}
	
	public void deletedelivery(int id) {
		deliveryDAO.deletedelivery(id);
	}
	
	public void updatedelivery(DeliveryDTO deliveryDTO) {
		deliveryDAO.updatedelivery(deliveryDTO);
	}
		
//		// 주문목록이 없을경우 빈리스트를 반환해서 페이지 500에러 방지
//		if (list == null || list.isEmpty()) {
//		    return new ArrayList<>(); // 빈 리스트 반환하여 SQL 오류 방지
//		}
	}

