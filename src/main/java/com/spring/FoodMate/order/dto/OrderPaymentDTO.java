package com.spring.FoodMate.order.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component("orderpaymentDTO")
public class OrderPaymentDTO {
	private int pay_id; // 결제 ID (기본키, 시퀀스 사용)
    private int ord_id; // 주문 ID (주문 테이블의 주문ID 외래키, 삭제 시 null)
    private String pay_Method; // 결제 수단
    private char pay_Status; // 결제 상태 (0 = 대기중, 1 = 성공, 2 = 취소, 3 = 환불)
    private String pg_id;	// PG사에서 발급하는 결제 ID
    private Date pay_Date; // 결제일 (디폴트 SYSDATE)
    private int used_point;
    
    // Admin 옵션
    private int ord_stat; // 주문 상태 0 : 결제 대기 / 1 : 결제 완료, 배송 준비 / 2 : 배송 중 / 3 : 배송 완료 / 4 : 구매 확정 / 5 : 주문 취소 / 6 : 반품
    private int tot_Pdt_Price; // 총 상품 가격
    private int ship_Fee; // 배송비
    private String name;
    
    private String orderMonth;    // "2024-11"
    private String period;        // "2024-11-01 ~ 2024-11-30"
    private int total_payments;    // 3
    private int total_amount; // 500
    
    // 기본 생성자
    public OrderPaymentDTO() {}

    // 전체 필드를 포함한 생성자
    public OrderPaymentDTO(int payId, int ordId, String payMethod, char payStatus, Date payDate, String pg_id) {
        this.pay_id = payId;
        this.ord_id = ordId;
        this.pay_Method = payMethod;
        this.pay_Status = payStatus;
        this.pay_Date = payDate;
        this.pg_id = pg_id;
    }

    // Getter 및 Setter
	public int getPay_id() {
		return pay_id;
	}

	public void setPay_id(int pay_id) {
		this.pay_id = pay_id;
	}

	public int getOrd_id() {
		return ord_id;
	}

	public void setOrd_id(int ord_id) {
		this.ord_id = ord_id;
	}

	public String getPay_Method() {
		return pay_Method;
	}

	public void setPay_Method(String pay_Method) {
		this.pay_Method = pay_Method;
	}

	public int getPay_Status() {
		return Character.getNumericValue(pay_Status);
	}

	public void setPay_Status(char pay_Status) {
		this.pay_Status = pay_Status;
	}

	public String getPg_id() {
		return pg_id;
	}

	public void setPg_id(String pg_id) {
		this.pg_id = pg_id;
	}

	public Date getPay_Date() {
		return pay_Date;
	}

	public void setPay_Date(Date pay_Date) {
		this.pay_Date = pay_Date;
	}

    public int getUsed_point() {
		return used_point;
	}

	public void setUsed_point(int used_point) {
		this.used_point = used_point;
	}

	public int getOrd_stat() {
		return ord_stat;
	}

	public void setOrd_stat(int ord_stat) {
		this.ord_stat = ord_stat;
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
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getOrderMonth() {
		return orderMonth;
	}

	public void setOrderMonth(String orderMonth) {
		this.orderMonth = orderMonth;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	

	public int getTotal_payments() {
		return total_payments;
	}

	public void setTotal_payments(int total_payments) {
		this.total_payments = total_payments;
	}

	public int getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(int total_amount) {
		this.total_amount = total_amount;
	}

	@Override
    public String toString() {
        return "PaymentDTO{" +
                "payId=" + pay_id +
                ", ordId=" + ord_id +
                ", payMethod='" + pay_Method + '\'' +
                ", payStatus=" + pay_Status +
                ", payDate=" + pay_Date +
                '}';
    }


}