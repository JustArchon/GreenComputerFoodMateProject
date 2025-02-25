package com.spring.FoodMate.mypage.controller;


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

import com.spring.FoodMate.member.dto.BuyerDTO;
import com.spring.FoodMate.mypage.dto.DeliveryDTO;
import com.spring.FoodMate.mypage.dto.PointDTO;
import com.spring.FoodMate.mypage.service.DeliveryService;
import com.spring.FoodMate.mypage.service.PointService;
import com.spring.FoodMate.order.dto.OrderDetailDTO;


@Controller
public class WishlistController {
	private static final Logger logger = LoggerFactory.getLogger(WishlistController.class);
	
	
	        

}
