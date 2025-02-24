package com.spring.FoodMate.order.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderDetailDTO {
	private int ord_detail_id;	// 상세고유id[시퀀스]
	private int ord_id;			// 주문id[부모키]
	private int pdt_id;			// 상품id
	private String pdt_name;	// 상품 이름
	private int pdt_price;		// 상품 가격
	private int qty;			// 개수
	
	private String img_path;
	private String ord_stat;
	private String create_date;
	private String ord_code;
	// 조인해서 가져올 짜바리들
	// create_date를 String형으로 지정해도 되나? 얘는 출력용이라 ㄱㅊ을듯?
	
	public int getOrd_detail_id() {
		return ord_detail_id;
	}
	public void setOrd_detail_id(int ord_detail_id) {
		this.ord_detail_id = ord_detail_id;
	}
	public int getOrd_id() {
		return ord_id;
	}
	public void setOrd_id(int ord_id) {
		this.ord_id = ord_id;
	}
	public int getPdt_id() {
		return pdt_id;
	}
	public void setPdt_id(int pdt_id) {
		this.pdt_id = pdt_id;
	}
	public String getPdt_name() {
		return pdt_name;
	}
	public void setPdt_name(String pdt_name) {
		this.pdt_name = pdt_name;
	}
	public int getPdt_price() {
		return pdt_price;
	}
	public void setPdt_price(int pdt_price) {
		this.pdt_price = pdt_price;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	public String getOrd_stat() {
		return ord_stat;
	}
	public void setOrd_stat(String ord_stat) {
		this.ord_stat = ord_stat;
	}	
	public String getCreate_date() { // 기존 메서드 수정
	    return create_date != null ? create_date.substring(0, 10) : ""; // "yyyy-MM-dd" 형식만 반환
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getOrd_code() {
		return ord_code;
	}
	public void setOrd_code(String ord_code) {
		this.ord_code = ord_code;
	}
}