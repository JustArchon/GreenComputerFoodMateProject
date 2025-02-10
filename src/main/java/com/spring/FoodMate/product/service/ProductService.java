package com.spring.FoodMate.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.FoodMate.product.dao.ProductDAO;
import com.spring.FoodMate.product.dto.CategoryDTO;
import com.spring.FoodMate.product.dto.ProductDTO;

@Service("productService")
public class ProductService {
	@Autowired
	private ProductDAO productDAO;
	
	public List<ProductDTO> pdtList(String keyword) throws Exception{
		if(keyword.equals("")) {
			return productDAO.pdtAllList();
		} else {
			return productDAO.pdtSearchList(keyword);
		}
	}
	
	public ProductDTO select1Pdt(String pdt_id) throws Exception {
		return productDAO.select1Pdt(pdt_id);
	}
	
	public List<CategoryDTO> categoryStep(int category_id) throws Exception {
		return productDAO.getCategoryStep(category_id);
	}
}
