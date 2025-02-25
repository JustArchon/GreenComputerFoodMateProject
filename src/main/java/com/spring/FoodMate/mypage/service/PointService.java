package com.spring.FoodMate.mypage.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.FoodMate.mypage.dao.PointDAO;
import com.spring.FoodMate.mypage.dto.PointDTO;


@Service("pointService")
//@Transactional(propagation=Propagation.REQUIRED)
public class PointService {
	
	@Autowired
	private PointDAO pointDAO;
	
	public void addPoint(PointDTO pointDTO) {
		pointDAO.addPointlog(pointDTO);
		Map<String, Object> pointMap = new HashMap<>();
		pointMap.put("byr_id", pointDTO.getByr_id());
		pointMap.put("amount", pointDTO.getAmount());
		pointDAO.plusPoint(pointMap);
	}
	
	public void usePoint(PointDTO pointDTO) {
		pointDAO.addPointlog(pointDTO);
		Map<String, Object> pointMap = new HashMap<>();
		pointMap.put("byr_id", pointDTO.getByr_id());
		pointMap.put("amount", pointDTO.getAmount());
		pointDAO.usePoint(pointMap);
	}
	
		
//		// 주문목록이 없을경우 빈리스트를 반환해서 페이지 500에러 방지
//		if (list == null || list.isEmpty()) {
//		    return new ArrayList<>(); // 빈 리스트 반환하여 SQL 오류 방지
//		}
	}

