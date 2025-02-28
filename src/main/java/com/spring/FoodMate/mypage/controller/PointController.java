package com.spring.FoodMate.mypage.controller;


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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.FoodMate.member.dto.BuyerDTO;
import com.spring.FoodMate.mypage.dto.DeliveryDTO;
import com.spring.FoodMate.mypage.dto.PointDTO;
import com.spring.FoodMate.mypage.service.DeliveryService;
import com.spring.FoodMate.mypage.service.PointService;
import com.spring.FoodMate.order.dto.OrderDetailDTO;


@Controller
public class PointController {
	private static final Logger logger = LoggerFactory.getLogger(PointController.class);
	
	@Autowired
	private PointService pointService;
	
	@RequestMapping(value="/mypage/pointAdd" ,method = RequestMethod.POST)
	public ResponseEntity pointAdd(@RequestBody Map<String, Object> pointData,
			                HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		BuyerDTO buyerInfo = (BuyerDTO) session.getAttribute("buyerInfo"); // 세션에서 buyerInfo 가져오기
		String byr_id = null;
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		if (buyerInfo != null) {
		    byr_id = buyerInfo.getByr_id(); // byr_id 값 추출
		}
		
		try {
			int point = (int) Math.round((int) pointData.get("amount") * 0.05);
			PointDTO pointDTO = new PointDTO();
			pointDTO.setByr_id(byr_id);
			pointDTO.setAmount(point);
			pointDTO.setDescription((int) pointData.get("amount") + "원 구매");
			pointDTO.setPoint_type((String) pointData.get("point_type"));
			
			pointService.addPoint(pointDTO);
			
			message  = "<script>";
		    message += " alert('포인트 적립을 성공했습니다.');";
		    message += " </script>";
			
        } catch (Exception e) {
        	e.printStackTrace();
        	message  = "<script>";
		    message += " alert('포인트 적립을 실패했습니다.');";
		    message += " </script>";
        }
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
    }
	        

}
