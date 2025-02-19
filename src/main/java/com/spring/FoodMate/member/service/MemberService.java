package com.spring.FoodMate.member.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.FoodMate.member.dao.MemberDAO;
import com.spring.FoodMate.member.dto.BuyerDTO;
import com.spring.FoodMate.member.dto.SellerDTO;

@Service("memberService")
@Transactional(propagation=Propagation.REQUIRED)
public class MemberService {
	@Autowired
	private MemberDAO memberDAO;
	
	public BuyerDTO login(Map loginMap) throws Exception{
		return memberDAO.login(loginMap);
	}
	
	public SellerDTO loginslr(Map  loginMap) throws Exception{
		return memberDAO.loginslr(loginMap);
	}
	
	public String buyerIDoverlapped(String id) throws Exception{
		return memberDAO.selectOverlappedBuyerID(id);
	}
	
	public String sellerIDoverlapped(String id) throws Exception{
		return memberDAO.selectOverlappedSellerID(id);
	}
	
	public String findBuyerId(BuyerDTO buyerVO) throws Exception{
		return memberDAO.selectFindBuyerID(buyerVO);
	}
	
	public String findSellerId(SellerDTO sellerVO) throws Exception{
		return memberDAO.selectFindSellerID(sellerVO);
	}
	
	public String findBuyerPW(BuyerDTO buyerVO) throws Exception{
		return memberDAO.selectFindBuyerPW(buyerVO);
	}
	
	public String findSellerPW(SellerDTO sellerVO) throws Exception{
		return memberDAO.selectFindSellerPW(sellerVO);
	}
	
	public void resetBuyerPW(BuyerDTO buyerVO) throws Exception{
		memberDAO.updateResetBuyerPW(buyerVO);
	}
	
	public void resetSellerPW(SellerDTO sellerVO) throws Exception{
		memberDAO.updateResetSellerPW(sellerVO);
	}
	
	public void addBuyer(BuyerDTO buyerDTO) throws Exception{
		
		String ssn1 = buyerDTO.getSsn1();
		if (ssn1 != null && (ssn1.equals("1") || ssn1.equals("3"))) {
			buyerDTO.setSex("M");
        } else if (ssn1 != null && (ssn1.equals("2") || ssn1.equals("4"))) {
        	buyerDTO.setSex("F");
        } else {
        	buyerDTO.setSex("N");
        }
		
		if(buyerDTO.getEmail_domain().equals("custom")) {
			buyerDTO.setEmail_domain(buyerDTO.getCustomMail());
		}
		
		String email = buyerDTO.getEmail_id() + "@" + buyerDTO.getEmail_domain();
		buyerDTO.setEmail(email);
		
		memberDAO.insertNewBuyer(buyerDTO);
	}
	
	public void addSeller(SellerDTO sellerDTO) throws Exception{
		
		sellerDTO.setBusiness_no(sellerDTO.getBsnum1() + sellerDTO.getBsnum2() + sellerDTO.getBsnum3());
		
		if(sellerDTO.getEmail_domain().equals("custom")) {
			sellerDTO.setEmail_domain(sellerDTO.getCustomMail());
		}
		
		String email = sellerDTO.getEmail_id() + "@" + sellerDTO.getEmail_domain();
		sellerDTO.setEmail(email);
		
		memberDAO.insertNewSeller(sellerDTO);
	}
	
	public void updateBuyer(BuyerDTO buyerDTO) throws Exception{
		memberDAO.updateBuyer(buyerDTO);
	}
}
