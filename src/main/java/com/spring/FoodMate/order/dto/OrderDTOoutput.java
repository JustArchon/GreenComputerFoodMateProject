package com.spring.FoodMate.order.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderDTOoutput {
	private int ord_id;
	private String ord_code; // 주문번호
    private String byr_id; // 구매자 ID
    private String slr_id; // 판매자 ID
    private int tot_Pdt_Price; // 총 상품 가격
    private int ship_Fee; // 배송비
	private char ord_stat; // 주문 상태 0 : 결제 대기 / 1 : 결제 완료, 배송 준비 / 2 : 배송 중 / 3 : 배송 완료 / 4 : 구매 확정 / 5 : 주문 취소 / 6 : 반품
    private String create_Date; // 생성 날짜
    private String del_Code; // 배송 코드
    private String waybill_Num; // 운송장 번호
    
    private List<OrderDetailDTOoutput> orderDetails;

    // 기본 생성자
    public OrderDTOoutput() {}

    // Getter, Setter
    public int getOrd_id() {		return ord_id;	}
	public void setOrd_id(int ord_id) {		this.ord_id = ord_id;	}
	public String getOrd_code() { return ord_code; } 
    public void setOrd_code(String ord_code) { this.ord_code = ord_code; } 
    public String getByr_id() { return byr_id; } 
    public void setByr_id(String byr_id) { this.byr_id = byr_id; } 
    public String getSlr_id() { return slr_id; } 
    public void setSlr_id(String slr_id) { this.slr_id = slr_id; } 
    public int getTot_Pdt_Price() { return tot_Pdt_Price; } 
    public void setTot_Pdt_Price(int tot_Pdt_Price) { this.tot_Pdt_Price = tot_Pdt_Price; } 
    public int getShip_Fee() { return ship_Fee; } 
    public void setShip_Fee(int ship_Fee) { this.ship_Fee = ship_Fee; } 
    public char getOrd_stat() { return ord_stat; } 
    public void setOrd_stat(char ord_stat) { this.ord_stat = ord_stat; } 
    public String getCreate_Date() { return create_Date; } 
    public void setCreate_Date(String create_Date) { this.create_Date = create_Date; } 
    public String getDel_Code() { return del_Code; } 
    public void setDel_Code(String del_Code) { this.del_Code = del_Code; } 
    public String getWaybill_Num() { return waybill_Num; } 
    public void setWaybill_Num(String waybill_Num) { this.waybill_Num = waybill_Num; }
    public List<OrderDetailDTOoutput> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetailDTOoutput> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	public String getDel_company_name() {
		if(getDel_Code() == null) { return "없음"; }
		else {
			switch(getDel_Code()) {
			case "kr.cjlogistics" : return "CJ대한통운";
			case "kr.lotte" : return "롯데택배";
			case "kr.hanjin" : return "한진택배";
			default: return "불명";
			}			
		}
	}

	public String getOrd_stat_msg() {
		switch(getOrd_stat()) {
		case '0' : return "결제 대기";
		case '1' : return "배송 대기중";
		case '2' : return "배송중";
		case '3' : return "배송 완료";
		case '4' : return "구매 확정";
		case '5' : return "주문 취소됨";
		case '6' : return "반품 및 교환";
		default: return "상태 불명";
		}
	}

    @Override
    public String toString() {
        return "OrderDTO{" +
                "byrId='" + byr_id + '\'' +
                ", slrId='" + slr_id + '\'' +
                ", totPdtPrice=" + tot_Pdt_Price +
                ", shipFee=" + ship_Fee +
                ", ordStat=" + ord_stat +
                ", createDate=" + create_Date +
                ", delCode='" + del_Code + '\'' +
                ", waybillNum='" + waybill_Num + '\'' +
                '}';
    }
}