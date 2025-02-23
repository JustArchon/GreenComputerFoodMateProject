package com.spring.FoodMate.order.dto;

import java.util.Date;

public class OrderPaymentDTO {
	private int pay_id; // 결제 ID (기본키, 시퀀스 사용)
    private int ord_id; // 주문 ID (주문 테이블의 주문ID 외래키, 삭제 시 null)
    private String pay_Method; // 결제 수단
    private char pay_Status; // 결제 상태 (0 = 대기중, 1 = 성공, 2 = 취소, 3 = 환불)
    private String pg_id;	// PG사에서 발급하는 결제 ID
    private Date pay_Date; // 결제일 (디폴트 SYSDATE)

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

	public char getPay_Status() {
		return pay_Status;
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