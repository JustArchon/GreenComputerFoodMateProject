package com.spring.FoodMate.product.dto;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("productDTO")
public class ProductDTO {
	private int pdt_id;
	private String slr_id;
	private String name;
	private String img_path; 
	private int price;
	private int qty;
	private String unit;
	private Integer category_id;
	private int stock; 
	private String status;
	private String description;
	// 여기까지 FoodMate_Product 테이블
	
	private String slr_nickname; // pdt_id 매개로 Join 해서 쓰는 판매자 별명
	private MultipartFile pdt_img; // 실제 이미지 전달용
	private Double unit_price;
	private char ort_stat;
	private int ord_count;
	private List<MultipartFile> pdt_descimg;
	private Double avg_rating;
	private int review_count;
	
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
	public Integer getCategory_id() { return category_id; }
	public void setCategory_id(Integer category_id) { this.category_id = category_id; }
	public int getStock() { return stock; }
	public void setStock(int stock) { this.stock = stock; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	public String getSlr_nickname() {return slr_nickname;}
	public void setSlr_nickname(String slr_nickname) {this.slr_nickname = slr_nickname;}
	public int getPdt_id() { return pdt_id; }
	public MultipartFile getPdt_img() {return pdt_img;}
	public void setPdt_img(MultipartFile pdt_img) {this.pdt_img = pdt_img;}
	public Double getUnit_price() {return unit_price;}
	public void setUnit_price(Double unit_price) {this.unit_price = unit_price;}
	public char getOrt_stat() {return ort_stat;}
	public void setOrt_stat(char ort_stat) {this.ort_stat = ort_stat;}
	public int getOrd_count() {return ord_count;}
	public void setOrd_count(int ord_count) {this.ord_count = ord_count;}
	public List<MultipartFile> getPdt_descimg() {return pdt_descimg;}
	public void setPdt_descimg(List<MultipartFile> pdt_descimg) {this.pdt_descimg = pdt_descimg;}
	public Double getAvg_rating() {		return avg_rating;	}
	public void setAvg_rating(Double avg_rating) {		this.avg_rating = avg_rating;	}
	public int getReview_count() {		return review_count;	}
	public void setReview_count(int review_count) {		this.review_count = review_count;	}
	
	// 예외 로그 기록용
	public String toLogString() {
	    return "ProductDTO{" +
	            "pdt_id=" + getPdt_id() +
	            ", slr_id='" + getSlr_id() +
	            ", name='" + getName() +
	            ", img_path='" + getImg_path() +
	            ", price=" + getPrice() +
	            ", qty=" + getQty() +
	            ", unit='" + getUnit() +
	            ", category_id=" + getCategory_id() +
	            ", stock=" + getStock() +
	            ", status='" + getStatus() +
	            ", description='" + getDescription() +
	            ", slr_nickname='" + getSlr_nickname() +
	            ", slr_nickname='" + getSlr_nickname() +
	            ", unit_price=" + getUnit_price() + 
	            '}';
	}
}