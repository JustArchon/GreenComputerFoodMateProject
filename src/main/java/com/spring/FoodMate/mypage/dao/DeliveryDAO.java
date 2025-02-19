package com.spring.FoodMate.mypage.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.FoodMate.mypage.dto.DeliveryDTO;



@Repository("deliveryDAO")
public class DeliveryDAO {
	@Autowired
	private SqlSession sqlSession;	
	
    public List<DeliveryDAO> getdeliveryList(String id) {
        return sqlSession.selectList("mapper.delivery.getdeliveryList", id);
    }
	
	public void adddelivery(DeliveryDTO _deliveryDAO) throws DataAccessException{
		sqlSession.insert("mapper.delivery.adddelivery", _deliveryDAO);
	}
	
	public void resetdelivery(String id) throws DataAccessException{
		sqlSession.update("mapper.delivery.resetDelivery", id);
	}
	
	public int updateUserProfile(DeliveryDAO _profileDTO) throws DataAccessException{
		return sqlSession.update("mapper.profile.updateUserProfile", _profileDTO);
	}

}
