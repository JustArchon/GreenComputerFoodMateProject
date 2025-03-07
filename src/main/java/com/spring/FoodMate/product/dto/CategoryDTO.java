package com.spring.FoodMate.product.dto;

import org.springframework.stereotype.Component;

@Component("categoryDTO")
public class CategoryDTO {

	private Integer category_id;
	private String name;
	private Integer parent_id;
	private String recommend_unit;
	
	public Integer getCategory_id() {		return category_id;	}
	public void setCategory_id(Integer category_id) {		this.category_id = category_id;	}
	public String getName() {		return name;	}
	public void setName(String name) {		this.name = name;	}
	public Integer getParent_id() {		return parent_id;	}
	public void setParent_id(Integer parent_id) {		this.parent_id = parent_id;	}
	public String getRecommend_unit() {		return recommend_unit;	}
	public void setRecommend_unit(String recommend_unit) {		this.recommend_unit = recommend_unit;	}
	
	// 예외 로그 기록용
	public String toLogString() {
	    return "CategoryDTO{" +
	            "category_id=" + getCategory_id() +
	            ", name='" + getName() +
	            ", parent_id=" + getParent_id() +
	            ", recommend_unit=" + getRecommend_unit()+
	            '}';
	}
}