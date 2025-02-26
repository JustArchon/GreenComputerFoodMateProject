package com.spring.FoodMate.mypage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.FoodMate.cart.dto.CartDTO;
import com.spring.FoodMate.common.exception.DBException;
import com.spring.FoodMate.mypage.dto.DeliveryDTO;
import com.spring.FoodMate.mypage.dto.PointDTO;
import com.spring.FoodMate.mypage.dto.WishDTO;


@Repository("wishlistDAO")
public class WishlistDAO {
	@Autowired
	private SqlSession sqlSession;	
	
    public List<WishDTO> getwishList(String byr_id) {
        return sqlSession.selectList("mapper.wishlist.getwishList", byr_id);
    }
	
	public void addWishProduct(WishDTO wishDTO) throws DataAccessException{
		sqlSession.insert("mapper.wishlist.addWishProduct", wishDTO);
	}
	
	// 찜 목록 검사 
	public WishDTO isInMyWish(int pdt_id, String byr_id) {
		try {
			WishDTO wishDTO = new WishDTO(pdt_id, byr_id);
			WishDTO resultDTO = sqlSession.selectOne("mapper.wishlist.isInMyWish", wishDTO);
			return resultDTO;
		} catch (DataAccessException e) {
			throw new DBException("WishlistDAO.isInMyWish 에러! pdt_id =" + pdt_id + ", byr_id = " + byr_id , e);
		}
	}
	
	public int updateWishQuantity(int wishlist_id, int qty) {
	    Map<String, Object> params = new HashMap<>();
	    try {
	        params.put("wishlist_id", wishlist_id);
	        params.put("qty", qty);
	        return sqlSession.update("mapper.wishlist.updateWishQuantity", params);
	    } catch (DataAccessException e) {
	        throw new DBException("WishlistDAO.updateWishQuantity 에러!" + params.toString(), e);
	    }
	}
	
	public int deleteWishItem(int wishlist_id) {
		try {
			return sqlSession.delete("mapper.wishlist.deleteWishItem", wishlist_id);
		} catch (DataAccessException e) {
			throw new DBException("WishlistDAO.deletewishItem 에러! wishlist_id=" + wishlist_id, e);
		}
    }

}
