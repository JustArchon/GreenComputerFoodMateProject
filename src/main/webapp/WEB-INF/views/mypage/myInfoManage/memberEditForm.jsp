<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 수정</title>
<style>
	   .signUp-container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            width: 100%;
        }
</style>
</head>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
</script>
<body>
<div class="container mt-1">
	<div class="d-flex justify-content-between align-items-center border-bottom pb-2 mb-3">
      		<h5 class="mb-0 fw-bold">회원정보 수정</h5>
      		</div>
      		<div class="d-flex justify-content-start align-items-center">
				<div class="signUp-container">
				    <div class="row justify-content-start">
				        <div class="col-md-12">
				        	<form name="newBuyer" action="${contextPath}/member/updateBuyer" method="post">
				                <div class="mb-3 row">
				                    <label class="col-sm-3 col-form-label text-end">아이디</label>
				                    <div class="col-sm-5">
				                        <input name="id" type="text" class="form-control" placeholder="id" value="${buyerInfo.getByr_id()}" disabled>
				                    </div>
				                </div>
				                <div class="mb-3 row">
				                    <label class="col-sm-3 col-form-label text-end">비밀번호</label>
				                    <div class="col-sm-5">
				                        <input name="password" type="password" class="form-control" placeholder="password">
				                    </div>
				                </div>
				                <div class="mb-3 row">
				                    <label class="col-sm-3 col-form-label text-end">비밀번호 확인</label>
				                    <div class="col-sm-5">
				                        <input name="password_confirm" type="password" class="form-control" placeholder="password confirm">
				                    </div>
				                </div>
				                <div class="mb-3 row">
				                    <label class="col-sm-3 col-form-label text-end">성명</label>
				                    <div class="col-sm-5">
				                        <input name="name" type="text" class="form-control" placeholder="name" value="${buyerInfo.getName()}" required>
				                    </div>
				                </div>
				                <div class="mb-3 row">
				                    <label class="col-sm-3 col-form-label text-end">성별</label>
				                    <div class="col-sm-6 d-flex align-items-center">
				                        <input name="gender" type="radio" value="남" class="me-2" disabled> 남
				                        <input name="gender" type="radio" value="여" class="ms-4 me-2" disabled> 여
				                    </div>
				                </div>
				                <div class="mb-3 row">
				                    <label class="col-sm-3 col-form-label text-end">생일</label>
				                    <div class="col-sm-8">
				                        <div class="row">
				                            <div class="col-sm-3">
				                                <input type="text" name="birthyy" maxlength="4" class="form-control" placeholder="년(4자)" required>
				                            </div>
				                            <div class="col-sm-3" >
				                                <select name="birthmm" class="form-select">
				                                    <option value="">월</option>
				                                    <option value="01">1</option>
				                                    <option value="02">2</option>
				                                    <option value="03">3</option>
				                                    <option value="04">4</option>
				                                    <option value="05">5</option>
				                                    <option value="06">6</option>
				                                    <option value="07">7</option>
				                                    <option value="08">8</option>
				                                    <option value="09">9</option>
				                                    <option value="10">10</option>
				                                    <option value="11">11</option>
				                                    <option value="12">12</option>
				                                </select>
				                            </div>
				                            <div class="col-sm-3">
				                                <input type="text" name="birthdd" maxlength="2" class="form-control" placeholder="일" style="width: 50px;" required >
				                            </div>
				                        </div>
				                    </div>
				                </div>
								<div class="mb-3 row">
								    <label class="col-sm-3 col-form-label text-end">이메일</label>
								    <div class="col-sm-8">
								        <div class="d-flex align-items-center">
								            <!-- 이메일 입력 -->
								            <input type="text" id="email_id" name="emailId" maxlength="50" class="form-control me-2" placeholder="email" required>
								            <div class="col-sm-1 d-flex justify-content-center align-items-center me-2" style="width: auto; padding: 0 5px;">
			            							<span>@</span>
			        						</div>
											<input type="text" id="email_domain" name="emailDomain" maxlength="50" class="form-control" placeholder="email" required>
								        </div>
								    </div>
								</div>
				                <div class="mb-3 row">
				                    <label class="col-sm-3 col-form-label text-end">전화번호</label>
				                    <div class="col-sm-5">
				                        <input name="phone" type="text" class="form-control" placeholder="phone" value="${buyerInfo.getPhone_num()}" required>
				                    </div>
				                </div>
									<div class="mb-3 row">
									    <div class="col-12 text-center">
									        <input type="submit" class="btn btn-success" value="정보수정">
									        <input type="reset" class="btn btn-secondary" value="취소">
									    </div>
									</div>
				            </form>
			    		</div>
					</div>
				</div>
    		</div>
</div>
</body>
<script>
	document.addEventListener("DOMContentLoaded", function () {
	    function setGenderFromSSN() {
	        let sex = "<c:out value='${buyerInfo.getSex()}'/>"; // 서버에서 받은 성별 값
	
	        if (sex === "1" || sex === "3") {
	            let maleRadio = document.querySelector('input[name="gender"][value="남"]');
	            if (maleRadio) maleRadio.checked = true;
	        } else if (sex === "2" || sex === "4") {
	            let femaleRadio = document.querySelector('input[name="gender"][value="여"]');
	            if (femaleRadio) femaleRadio.checked = true;
	        } else {
	            console.error("잘못된 성별 값:", sex);
	        }
	    }
	    
	    function setBirthFromSSN() {
	    	let sex = "<c:out value='${buyerInfo.getSex()}'/>"; // 서버에서 받은 성별 값
	    	let birth6 = "<c:out value='${buyerInfo.getBirth_6()}'/>"; // 서버에서 받은 생년월일 값
	        let birthYearPrefix = (sex === "1" || sex === "2") ? "19" : "20"; // 성별에 따른 년도 앞자리 설정
	    	
	        let birthYear = birthYearPrefix + birth6.substring(0,2);
	    	let birthMonth = birth6.substring(2, 4);
	    	let birthDay = birth6.substring(4, 6);
	    	
	    	let birthYearSelect = document.querySelector('input[name="birthyy"]');
	        let birthMonthSelect = document.querySelector('select[name="birthmm"]');
	        let birthDaySelect = document.querySelector('input[name="birthdd"]');
	        
	        birthYearSelect.value = birthYear;
	        birthMonthSelect.value = birthMonth;
	        birthDaySelect.value = birthDay;
	    }
	    
	    function setEmail() {
	    	let email = "<c:out value='${buyerInfo.getEmail()}'/>"; // 서버에서 받은 이메일 값
	    	
	    	if (email.includes("@")) {
	            let emailParts = email.split("@");
	            document.getElementById("email_id").value = emailParts[0]; // 아이디 부분
	            document.getElementById("email_domain").value = emailParts[1]; // 도메인 부분
	        }
	    }
	
	    setGenderFromSSN();
	    setBirthFromSSN();
	    setEmail();
	});
</script>
</html>