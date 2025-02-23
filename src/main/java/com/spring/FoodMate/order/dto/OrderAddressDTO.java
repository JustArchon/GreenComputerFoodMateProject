package com.spring.FoodMate.order.dto;

public class OrderAddressDTO {
    private int ordId; // 주문 ID (기본키, 주문 테이블의 주문ID 외래키)
    private String postalCode; // 우편번호
    private String addr; // 배송지 주소
    private String addrDetail; // 배송지 상세 주소
    private String toName; // 수령인 성함
    private String toPhoneNum; // 수령인 전화번호

    // 기본 생성자
    public OrderAddressDTO() {}

    // 전체 필드를 포함한 생성자
    public OrderAddressDTO(int ordId, String postalCode, String addr, String addrDetail, String toName, String toPhoneNum) {
        this.ordId = ordId;
        this.postalCode = postalCode;
        this.addr = addr;
        this.addrDetail = addrDetail;
        this.toName = toName;
        this.toPhoneNum = toPhoneNum;
    }

    // Getter 및 Setter
    public int getOrdId() { return ordId; }
    public void setOrdId(int ordId) { this.ordId = ordId; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getAddr() { return addr; }
    public void setAddr(String addr) { this.addr = addr; }

    public String getAddrDetail() { return addrDetail; }
    public void setAddrDetail(String addrDetail) { this.addrDetail = addrDetail; }

    public String getToName() { return toName; }
    public void setToName(String toName) { this.toName = toName; }

    public String getToPhoneNum() { return toPhoneNum; }
    public void setToPhoneNum(String toPhoneNum) { this.toPhoneNum = toPhoneNum; }

    @Override
    public String toString() {
        return "ShippingInfoDTO{" +
                "ordId=" + ordId +
                ", postalCode='" + postalCode + '\'' +
                ", addr='" + addr + '\'' +
                ", addrDetail='" + addrDetail + '\'' +
                ", toName='" + toName + '\'' +
                ", toPhoneNum='" + toPhoneNum + '\'' +
                '}';
    }
}
