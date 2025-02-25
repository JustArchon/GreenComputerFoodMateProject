package com.spring.FoodMate.mypage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.FoodMate.mypage.dto.DeliveryDTO;
import com.spring.FoodMate.mypage.dto.PointDTO;


@Repository("pointDAO")
public class PointDAO {
	@Autowired
	private SqlSession sqlSession;	
	
	public void addPointlog(PointDTO pointDTO) throws DataAccessException{
		sqlSession.insert("mapper.point.addPointlog", pointDTO);
	}
	
	public void plusPoint(Map<String, Object> point) throws DataAccessException{
		sqlSession.update("mapper.point.plusPoint", point);
	}
	
	public void usePoint(Map<String, Object> point) throws DataAccessException{
		sqlSession.update("mapper.point.usePoint", point);
	}
	
	public void deletedelivery(int addr_id) throws DataAccessException{
		sqlSession.delete("mapper.delivery.deletedelivery", addr_id);
	}
	
	public void updatedelivery(DeliveryDTO deliveryDTO) throws DataAccessException{
		sqlSession.update("mapper.delivery.updatedelivery", deliveryDTO);
	}

}
