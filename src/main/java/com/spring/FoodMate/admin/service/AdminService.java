package com.spring.FoodMate.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.FoodMate.admin.dao.AdminDAO;
import com.spring.FoodMate.cart.dto.CartDTO;
import com.spring.FoodMate.member.dao.MemberDAO;
import com.spring.FoodMate.member.dto.BuyerDTO;
import com.spring.FoodMate.member.dto.SellerDTO;
import com.spring.FoodMate.order.dto.OrderPaymentDTO;
import com.spring.FoodMate.product.dto.ProductDTO;
import com.spring.FoodMate.recipe.dto.RecipeDTO;

@Service("adminService")
@Transactional(propagation=Propagation.REQUIRED)
public class AdminService {
	@Autowired
	private AdminDAO adminDAO;
	
    public List<BuyerDTO> getAdminBuyerInfo(String status, String keyword, String searchType) throws Exception {
        return adminDAO.getAdminBuyerInfo(status, keyword, searchType);
    }
    
    public List<SellerDTO> getAdminSellerInfo(String status, String keyword, String searchType) throws Exception {
        return adminDAO.getAdminSellerInfo(status, keyword, searchType);
    }
    
    public List<RecipeDTO> getAdminRecipeInfo(String status, String keyword, String searchType) throws Exception {
        return adminDAO.getAdminRecipeInfo(status, keyword, searchType);
    }
    
    public List<ProductDTO> getAdminProductInfo(String status, String keyword, String searchType) throws Exception {
        return adminDAO.getAdminProductInfo(status, keyword, searchType);
    }
    
    public List<OrderPaymentDTO> getAdminPaymentInfo(String type, String keyword, String searchType) throws Exception {
        return adminDAO.getAdminPaymentInfo(type, keyword, searchType);
    }
    
    public List<OrderPaymentDTO> getAdminMonthPaymentInfo(String keyword, String searchType) throws Exception {
        return adminDAO.getAdminMonthPaymentInfo(keyword, searchType);
    }
    
    public void deleteAdminBuyer(String byr_id) throws Exception{
    	adminDAO.deleteAdminBuyer(byr_id);
    }
    
    public void setActiveAdminBuyer(String byr_id) throws Exception{
    	adminDAO.setActiveAdminBuyer(byr_id);
    }
}
