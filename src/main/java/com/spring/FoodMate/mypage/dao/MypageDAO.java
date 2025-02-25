package com.spring.FoodMate.mypage.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("mypageDAO")
public class MypageDAO {
	@Autowired
	private SqlSession sqlSession;
	
}