package com.spring.FoodMate.product.dto;

import org.springframework.stereotype.Component;

@Component("productDTO")
public class ProductDTO {
	private int pdt_id; // 내가 시퀀스대로 넣을거임
	private String slr_id; //세션에서 가져올거임
	private String name; //
	private String img_path; 
	private int price; //
	private int qty; //
	private String unit; //
	private int category_id; // ?
	private int stock; //
	private String status; // 내가넣을거임
	private String description; //
		
	public int getPdt_id() { return pdt_id; }
	public void setPdt_id(int pdt_id) { this.pdt_id = pdt_id; }
	public String getSlr_id() { return slr_id; }
	public void setSlr_id(String slr_id) { this.slr_id = slr_id; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getImg_path() { return img_path; }
	public void setImg_path(String img_path) { this.img_path = img_path; }
	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }
	public int getQty() { return qty; }
	public void setQty(int qty) { this.qty = qty; }
	public String getUnit() { return unit; }
	public void setUnit(String unit) { this.unit = unit; }
	public int getCategory_id() { return category_id; }
	public void setCategory_id(int category_id) { this.category_id = category_id; }
	public int getStock() { return stock; }
	public void setStock(int stock) { this.stock = stock; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}

	// 여기까지 FoodMate_product의 테이블과 일치함
	
	private String slr_nickname;
	 //
	
	public String getSlr_nickname() {return slr_nickname;}
	public void setSlr_nickname(String slr_nickname) {this.slr_nickname = slr_nickname;}
	
	
}