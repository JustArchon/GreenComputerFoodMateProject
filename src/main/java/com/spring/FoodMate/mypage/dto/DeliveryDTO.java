package com.spring.FoodMate.mypage.dto;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component("deliveryDTO")
public class DeliveryDTO {
	private int addr_id; // 주소 고유 ID
	private String byr_id;
    private String addr_Nickname; // 주소 별칭
    private String postal_Code;   // 우편번호
    private String addr;         // 기본 주소
    private String addr_Detail;   // 상세 주소
    private String to_Name;       // 받는 사람 이름
    private String to_Phone_Num;   // 받는 사람 전화번호
    private String is_Base_Addr;   // 기본 주소 여부 ('Y' or 'N')

	public int getAddr_id() {
		return addr_id;
	}
	public void setAddr_id(int addr_id) {
		this.addr_id = addr_id;
	}
	public String getByr_id() {
		return byr_id;
	}
	public void setByr_id(String byr_id) {
		this.byr_id = byr_id;
	}
	public String getAddr_Nickname() {
		return addr_Nickname;
	}
	public void setAddr_Nickname(String addr_Nickname) {
		this.addr_Nickname = addr_Nickname;
	}
	public String getPostal_Code() {
		return postal_Code;
	}
	public void setPostal_Code(String postal_Code) {
		this.postal_Code = postal_Code;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAddr_Detail() {
		return addr_Detail;
	}
	public void setAddr_Detail(String addr_Detail) {
		this.addr_Detail = addr_Detail;
	}
	public String getTo_Name() {
		return to_Name;
	}
	public void setTo_Name(String to_Name) {
		this.to_Name = to_Name;
	}
	public String getTo_Phone_Num() {
		return to_Phone_Num;
	}
	public void setTo_Phone_Num(String to_Phone_Num) {
		this.to_Phone_Num = to_Phone_Num;
	}
	public String getIs_Base_Addr() {
		return is_Base_Addr;
	}
	public void setIs_Base_Addr(String is_Base_Addr) {
		this.is_Base_Addr = is_Base_Addr;
	}

	
}
