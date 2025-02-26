package com.spring.FoodMate.mypage.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("wishDTO")
public class WishDTO {
	private int wishlist_id; // 찜목록 고유 ID
	private String byr_id;	// 구매자id
	private int pdt_id; // 상품id
	private LocalDateTime created_at; // 찜 날짜
	private String name;
	private String img_path;
	private int qty;
	private int price;
	
	// 생성자 (byr_id를 전달받는 생성자)
    public WishDTO() {
    }
	
    // 생성자 (byr_id를 전달받는 생성자)
    public WishDTO(int pdt_id, String byr_id) {
    	this.pdt_id = pdt_id;
        this.byr_id = byr_id;
    }

	public int getWishlist_id() {
		return wishlist_id;
	}

	public void setWishlist_id(int wishlist_id) {
		this.wishlist_id = wishlist_id;
	}

	public String getByr_id() {
		return byr_id;
	}

	public void setByr_id(String byr_id) {
		this.byr_id = byr_id;
	}

	public int getPdt_id() {
		return pdt_id;
	}

	public void setPdt_id(int pdt_id) {
		this.pdt_id = pdt_id;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
    
    
    
	
}
