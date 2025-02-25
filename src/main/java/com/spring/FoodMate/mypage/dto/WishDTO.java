package com.spring.FoodMate.mypage.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("wishDTO")
public class WishDTO {
	private int wishlist_id; // 찜목록 고유 ID
	private String byr_id;	// 구매자id
	private String pdt_id; // 포인트 적립유형
	private LocalDateTime created_at; // 적립일자
	private String name;
	private String img_path;
	private int qty;
	
	// 생성자 (byr_id를 전달받는 생성자)
    public WishDTO() {
    }
	
    // 생성자 (byr_id를 전달받는 생성자)
    public WishDTO(String byr_id) {
        this.byr_id = byr_id;
    }

    
	
}
