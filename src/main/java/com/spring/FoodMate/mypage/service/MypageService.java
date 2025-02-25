package com.spring.FoodMate.mypage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.FoodMate.mypage.dao.MypageDAO;

@Service("MypageService")
public class MypageService {		
	@Autowired
	private MypageDAO mypageDAO;
	
	
}