package com.spring.FoodMate.mypage.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.FoodMate.order.dto.OrderDetailDTO;

@Repository("mypageDAO")
public class MypageDAO {
	@Autowired
	private SqlSession sqlSession;
	
	public List<Integer> getOrdIdById(String id) throws DataAccessException{
		List<Integer> list = sqlSession.selectList("mapper.order.OrdIdById", id);
		System.out.println(list.toString());
		return list;
	}
	
	public List<OrderDetailDTO> getAllOrdByOrdIds(List<Integer> ord_idList) throws DataAccessException {
	    List<OrderDetailDTO> orderDetails = sqlSession.selectList("mapper.order.AllOrdByOrdIds", ord_idList);
	    return orderDetails;
	}
	
}