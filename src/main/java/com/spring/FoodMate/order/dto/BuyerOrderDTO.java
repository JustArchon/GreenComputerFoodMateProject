package com.spring.FoodMate.order.dto;

public class BuyerOrderDTO {
	private int ord_detail_id;	// 상세고유id[시퀀스]
	private int ord_id;			// 주문id[부모키]
	private int pdt_id;			// 상품id
	private String pdt_name;	// 상품 이름
	private int pdt_price;		// 상품 가격
	private int qty;			// 개수
	
//	BYR_ID VARCHAR2(50),
//    SLR_ID VARCHAR2(50),
//    TOT_PDT_PRICE NUMBER,
//    SHIP_FEE NUMBER,
//    ORD_STAT CHAR(1),
//    CREATE_DATE DATE DEFAULT SYSDATE,
//    DEL_CODE VARCHAR2(50),
//    WAYBILL_NUM VARCHAR2(50),
//	POSTAL_CODE CHAR(5),  -- 우편번호
//    ADDR VARCHAR2(255),  -- 배송지 주소
//    ADDR_DETAIL VARCHAR2(255),  -- 배송지 상세 주소
//    TO_NAME VARCHAR2(50),  -- 수령인 성함
//    TO_PHONE_NUM VARCHAR2(15),  -- 수령인 전화번호



}