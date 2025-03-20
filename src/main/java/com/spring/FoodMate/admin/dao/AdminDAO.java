package com.spring.FoodMate.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.spring.FoodMate.member.dto.BuyerDTO;
import com.spring.FoodMate.member.dto.SellerDTO;
import com.spring.FoodMate.order.dto.OrderPaymentDTO;
import com.spring.FoodMate.product.dto.ProductDTO;
import com.spring.FoodMate.recipe.dto.RecipeDTO;

@Repository("adminDAO")
public class AdminDAO {
	@Autowired
	private SqlSession sqlSession;	
	
    public List<BuyerDTO> getAdminBuyerInfo(String status, String keyword, String searchType) throws DataAccessException {
    	Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("status", status);
        paramMap.put("keyword", keyword);
        paramMap.put("searchtype", searchType);
        return sqlSession.selectList("mapper.member.selectAdminBuyerInfo", paramMap);
    }
    
    public List<SellerDTO> getAdminSellerInfo(String status, String keyword, String searchType) throws DataAccessException {
    	Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("status", status);
        paramMap.put("keyword", keyword);
        paramMap.put("searchtype", searchType);
        return sqlSession.selectList("mapper.member.selectAdminSellerInfo", paramMap);
    }
    
    public List<RecipeDTO> getAdminRecipeInfo(String status, String keyword, String searchType) throws DataAccessException {
    	Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("status", status);
        paramMap.put("keyword", keyword);
        paramMap.put("searchtype", searchType);
        return sqlSession.selectList("mapper.recipe.selectAdminRecipeList", paramMap);
    }
    
    public List<ProductDTO> getAdminProductInfo(String status, String keyword, String searchType) throws DataAccessException {
    	Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("status", status);
        paramMap.put("keyword", keyword);
        paramMap.put("searchtype", searchType);
        return sqlSession.selectList("mapper.product.selectAdminProductList", paramMap);
    }
    
    public List<OrderPaymentDTO> getAdminPaymentInfo(String type, String keyword, String searchType) throws DataAccessException {
    	Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("type", type);
        paramMap.put("keyword", keyword);
        paramMap.put("searchtype", searchType);
        return sqlSession.selectList("mapper.order.selectAdminPaymentInfo", paramMap);
    }
    
    public List<OrderPaymentDTO> getAdminMonthPaymentInfo(String keyword, String searchType) throws DataAccessException {
    	Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("keyword", keyword);
        paramMap.put("searchtype", searchType);
        return sqlSession.selectList("mapper.order.selectAdminMonthPaymentInfo", paramMap);
    }
    
    public void deleteAdminBuyer(String byr_id) throws DataAccessException {
    	sqlSession.delete("mapper.member.deleteAdminBuyer", byr_id);
    }
    
    public void setActiveAdminBuyer(String byr_id) throws DataAccessException {
    	sqlSession.update("mapper.member.setActiveAdminBuyer", byr_id);
    }
}
