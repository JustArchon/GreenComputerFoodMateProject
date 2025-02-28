package com.spring.FoodMate.mypage.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("pointDTO")
public class PointDTO {
	private int point_id; // 포인트 고유 ID
	private String byr_id;	// 구매자id
	private String point_type; // 포인트 적립유형
	private String description; // 설명
	private int amount; // 적립,소모 금액
	private Date valid_until; // 유효기간
	private Date created_at; // 적립일자
	
	// 생성자 (byr_id를 전달받는 생성자)
    public PointDTO() {
    }
	
    // 생성자 (byr_id를 전달받는 생성자)
    public PointDTO(String byr_id) {
        this.byr_id = byr_id;
    }

	public int getPoint_id() {
		return point_id;
	}

	public void setPoint_id(int point_id) {
		this.point_id = point_id;
	}

	public String getByr_id() {
		return byr_id;
	}

	public void setByr_id(String byr_id) {
		this.byr_id = byr_id;
	}

	public String getPoint_type() {
		return point_type;
	}

	public void setPoint_type(String point_type) {
		this.point_type = point_type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getValid_until() {
		return valid_until;
	}

	public void setValid_until(Date valid_until) {
		this.valid_until = valid_until;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
    
	
}
