package com.spring.FoodMate.mypage.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.FoodMate.member.dto.BuyerDTO;
import com.spring.FoodMate.mypage.dto.DeliveryDTO;
import com.spring.FoodMate.mypage.service.DeliveryService;



@Controller
public class DeliveryController {
	private static final Logger logger = LoggerFactory.getLogger(DeliveryController.class);
	
	@Autowired
	private DeliveryService deliveryService;
	
	@RequestMapping(value="/mypage/deliveryAdd" ,method = RequestMethod.POST)
	public ResponseEntity deliveryAdd(@ModelAttribute("DeliveryDTO") DeliveryDTO _deliveryDTO,
			                HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		BuyerDTO buyerInfo = (BuyerDTO) session.getAttribute("buyerInfo"); // 세션에서 buyerInfo 가져오기
		String byr_id = null;
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		if (buyerInfo != null) {
		    byr_id = buyerInfo.getByr_id(); // byr_id 값 추출
		    _deliveryDTO.setByr_id(byr_id);
		}
		try {
			
			if ("Y".equals(_deliveryDTO.getIs_Base_Addr())) {
                deliveryService.resetdelivery(byr_id);
            }
			
			deliveryService.adddelivery(_deliveryDTO);
			
			message  = "<script>";
		    message += " alert('배송지 저장에 성공했습니다.');";
		    message += " location.href='"+request.getContextPath()+"/mypage/myInfoManage/deliveryManageForm';";
		    message += " </script>";
			
        } catch (Exception e) {
        	e.printStackTrace();
        	message  = "<script>";
		    message += " alert('배송지 저장에 실패했습니다.');";
		    message += " location.href='"+request.getContextPath()+"/mypage/myInfoManage/deliveryManageForm';";
		    message += " </script>";
        }
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
    }
	
	@RequestMapping(value="/mypage/deliveryUpdate" ,method = RequestMethod.POST)
	public ResponseEntity deliveryUpdate(@ModelAttribute("DeliveryDTO") DeliveryDTO _deliveryDTO,
			                HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		BuyerDTO buyerInfo = (BuyerDTO) session.getAttribute("buyerInfo"); // 세션에서 buyerInfo 가져오기
		String byr_id = buyerInfo.getByr_id();
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			
			if ("Y".equals(_deliveryDTO.getIs_Base_Addr())) {
                deliveryService.resetdelivery(byr_id);
            }
			
			if(_deliveryDTO.getIs_Base_Addr() == null) {
				_deliveryDTO.setIs_Base_Addr("N");
			}
			
			deliveryService.updatedelivery(_deliveryDTO);
			
			message  = "<script>";
		    message += " alert('배송지를 성공적으로 수정했습니다.');";
		    message += " location.href='"+request.getContextPath()+"/mypage/myInfoManage/deliveryManageForm';";
		    message += " </script>";
			
        } catch (Exception e) {
        	e.printStackTrace();
        	message  = "<script>";
		    message += " alert('배송지 수정을 실패했습니다.');";
		    message += " location.href='"+request.getContextPath()+"/mypage/myInfoManage/deliveryManageForm';";
		    message += " </script>";
        }
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
    }
	
	@RequestMapping(value="/mypage/deliverydelete" ,method = RequestMethod.POST)
	public ResponseEntity deliveryDelete(@RequestParam("addr_id") int addrId,
			                HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			
			System.out.println(addrId);
			
			deliveryService.deletedelivery(addrId);
			
			message  = "<script>";
		    message += " alert('선택한 배송지를 성공적으로 삭제했습니다.');";
		    message += " location.href='"+request.getContextPath()+"/mypage/myInfoManage/deliveryManageForm';";
		    message += " </script>";
			
        } catch (Exception e) {
        	e.printStackTrace();
        	message  = "<script>";
		    message += " alert('배송지 삭제를 실패했습니다.');";
		    message += " location.href='"+request.getContextPath()+"/mypage/myInfoManage/deliveryManageForm';";
		    message += " </script>";
        }
		resEntity = new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
    }
	        

}
