package com.spring.FoodMate.order.dto;

public class OrderDetailDTOoutput {
	private int ord_detail_id;	// 상세고유id[시퀀스]
	private int ord_id;			// 주문id[부모키]
	private int pdt_id;			// 상품id
	private String pdt_name;	// 상품 이름
	private int pdt_price;		// 상품 가격
	private int qty;			// 개수
	
	private String slr_nickname; // 조인해서 가져올 판매자 상점 별명
	private String img_path; // 조인해서 가져올 상품 이미지 경로
	private int ord_stat; // 주문 상태 0 : 결제 대기 / 1 : 결제 완료, 배송 준비 / 2 : 배송 중 / 3 : 배송 완료 / 4 : 구매 확정 / 5 : 주문 취소 / 6 : 반품
	private String create_Date; // 주문한 날짜
	
	public int getOrd_detail_id() {		return ord_detail_id;	}
	public void setOrd_detail_id(int ord_detail_id) {		this.ord_detail_id = ord_detail_id;	}
	public int getOrd_id() {		return ord_id;	}
	public void setOrd_id(int ord_id) {		this.ord_id = ord_id;	}
	public int getPdt_id() {		return pdt_id;	}
	public void setPdt_id(int pdt_id) {		this.pdt_id = pdt_id;	}
	public String getPdt_name() {		return pdt_name;	}
	public void setPdt_name(String pdt_name) {		this.pdt_name = pdt_name;	}
	public int getPdt_price() {		return pdt_price;	}
	public void setPdt_price(int pdt_price) {		this.pdt_price = pdt_price;	}
	public int getQty() {		return qty;	}
	public void setQty(int qty) {		this.qty = qty;	}
	public String getSlr_nickname() {		return slr_nickname;	}
	public void setSlr_nickname(String slr_nickname) {		this.slr_nickname = slr_nickname;	}
	public String getImg_path() {		return img_path;	}
	public void setImg_path(String img_path) {		this.img_path = img_path;	}
	public int getOrd_stat() { return ord_stat; } 
    public void setOrd_stat(int ord_stat) { this.ord_stat = ord_stat; }
    public String getCreate_Date() { return create_Date; } 
    public void setCreate_Date(String create_Date) { this.create_Date = create_Date; }
    
    public String getOrd_stat_msg() {
		switch(getOrd_stat()) {
		case 0 : return "결제 대기";
		case 1 : return "배송 대기중";
		case 2 : return "배송중";
		case 3 : return "배송 완료";
		case 4 : return "구매 확정";
		case 5 : return "주문 취소됨";
		case 6 : return "반품 및 교환";
		default: return "상태 불명";
		}
	}
}