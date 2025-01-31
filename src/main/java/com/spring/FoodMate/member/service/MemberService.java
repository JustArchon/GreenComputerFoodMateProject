package com.spring.FoodMate.member.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.FoodMate.member.vo.MemberVO;
import com.spring.FoodMate.member.dao.MemberDAO;

@Service("memberService")
@Transactional(propagation=Propagation.REQUIRED)
public class MemberService {
	@Autowired
	private MemberDAO memberDAO;
	
	public MemberVO login(Map  loginMap) throws Exception{
		return memberDAO.login(loginMap);
	}
	
	public void addMember(MemberVO memberVO) throws Exception{
		
		String ssn1 = memberVO.getSsn1();
		if (ssn1 != null && (ssn1.equals("1") || ssn1.equals("3"))) {
            memberVO.setGender("M");
        } else if (ssn1 != null && (ssn1.equals("2") || ssn1.equals("4"))) {
            memberVO.setGender("F");
        } else {
            memberVO.setGender("N");
        }
		
		memberDAO.insertNewCustomer(memberVO);
	}
}
