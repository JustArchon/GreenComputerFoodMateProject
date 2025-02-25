package com.spring.FoodMate.order.dto;

import java.time.LocalDateTime;

public class OrderDTO {
	private String ord_code; // 주문번호
    private String byr_id; // 구매자 ID
    private String slr_id; // 판매자 ID
    private int tot_Pdt_Price; // 총 상품 가격
    private int ship_Fee; // 배송비
    private char ord_Stat; // 주문 상태 0 : 결제 대기 / 1 : 결제 완료, 배송 준비 / 2 : 배송 중 / 3 : 배송 완료 / 4 : 구매 확정 / 5 : 주문 취소 / 6 : 반품
    private LocalDateTime create_Date; // 생성 날짜
    private String del_Code; // 배송 코드
    private String waybill_Num; // 운송장 번호
    
    // 차후 쿠폰, 포인트 시스템때 사용
//    private int coupon_price;
//    private int point_price;

    // 기본 생성자
    public OrderDTO() {}

    // 전체 필드를 포함한 생성자
    public OrderDTO(String ord_code, String byrId, String slrId, int totPdtPrice, int shipFee, char ordStat, LocalDateTime createDate,
                    String delCode, String waybillNum) {
        this.ord_code = ord_code;
    	this.byr_id = byrId;
        this.slr_id = slrId;
        this.tot_Pdt_Price = totPdtPrice;
        this.ship_Fee = shipFee;
        this.ord_Stat = ordStat;
        this.create_Date = createDate;
        this.del_Code = delCode;
        this.waybill_Num = waybillNum;
    }

    // Getter 및 Setter
    
	public String getOrd_code() {
		return ord_code;
	}

	public void setOrd_code(String ord_code) {
		this.ord_code = ord_code;
	}
    
	public String getByr_id() {
		return byr_id;
	}

	public void setByr_id(String byr_id) {
		this.byr_id = byr_id;
	}

	public String getSlr_id() {
		return slr_id;
	}

	public void setSlr_id(String slr_id) {
		this.slr_id = slr_id;
	}

	public int getTot_Pdt_Price() {
		return tot_Pdt_Price;
	}

	public void setTot_Pdt_Price(int tot_Pdt_Price) {
		this.tot_Pdt_Price = tot_Pdt_Price;
	}

	public int getShip_Fee() {
		return ship_Fee;
	}

	public void setShip_Fee(int ship_Fee) {
		this.ship_Fee = ship_Fee;
	}

	public char getOrd_Stat() {
		return ord_Stat;
	}

	public void setOrd_Stat(char ord_Stat) {
		this.ord_Stat = ord_Stat;
	}

	public LocalDateTime getCreate_Date() {
		return create_Date;
	}

	public void setCreate_Date(LocalDateTime localDateTime) {
		this.create_Date = localDateTime;
	}

	public String getDel_Code() {
		return del_Code;
	}

	public void setDel_Code(String del_Code) {
		this.del_Code = del_Code;
	}

	public String getWaybill_Num() {
		return waybill_Num;
	}

	public void setWaybill_Num(String waybill_Num) {
		this.waybill_Num = waybill_Num;
	}


    @Override
    public String toString() {
        return "OrderDTO{" +
                "byrId='" + byr_id + '\'' +
                ", slrId='" + slr_id + '\'' +
                ", totPdtPrice=" + tot_Pdt_Price +
                ", shipFee=" + ship_Fee +
                ", ordStat=" + ord_Stat +
                ", createDate=" + create_Date +
                ", delCode='" + del_Code + '\'' +
                ", waybillNum='" + waybill_Num + '\'' +
                '}';
    }

}
