<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>포인트</title>
<link href="<c:url value="/resources/css/tablepage.css" />" rel="stylesheet">
<style>
		.table-custom {
		    border-top: 2px solid #b5b6b7 !important;
	 	 }
		/* 헤더 배경색 */
	    .table-header {
		  background-color: #f8f9fa;
	      text-align: center;
	      font-weight: bold;
	      min-width: 20%;
	    }
	    .table td:last-child {
		  min-width: 100px; /* 최소 너비 지정 */
		  text-align: center; /* 텍스트 중앙 정렬 */
		}
	    /* 내용 텍스트 정렬 */
	    .table tbody td {
	      vertical-align: middle;
	      text-align: center;
	    }
</style>
<script>
document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll(".btn-group button");
    const startDateInput = document.getElementById("start-date");
    const endDateInput = document.getElementById("end-date");

    function updateDates(days) {
        const today = new Date();
        const startDate = new Date(today); // 오늘 날짜 복사

        // 선택한 일 수만큼 과거로 이동
        startDate.setDate(today.getDate() - days);

        // 날짜를 YYYY-MM-DD 형식으로 변환하는 함수
        function formatDate(date) {
            if (!(date instanceof Date) || isNaN(date.getTime())) {
                return ""; // 빈 값 방지
            }
            let year = date.getFullYear();
            let month = String(date.getMonth() + 1).padStart(2, "0"); // 두 자리 유지
            let day = String(date.getDate()).padStart(2, "0"); // 두 자리 유지
            return `${year}-${month}-${day}`;
        }

        // 날짜 값이 즉시 반영되도록 `setTimeout` 사용
        const formattedToday = formatDate(today);
        const formattedStartDate = formatDate(startDate);

        if (formattedToday.match(/^\d{4}-\d{2}-\d{2}$/)) {
            setTimeout(() => {
                endDateInput.value = formattedToday;
            }, 10);
        }

        if (formattedStartDate.match(/^\d{4}-\d{2}-\d{2}$/)) {
            setTimeout(() => {
                startDateInput.value = formattedStartDate;
            }, 10);
        }
    }

    // 초기 설정: "오늘" 버튼을 클릭한 상태로 시작
    updateDates(0);

    buttons.forEach(button => {
        button.addEventListener("click", function () {
            // 모든 버튼에서 active 제거 후 클릭한 버튼에 active 추가
            buttons.forEach(btn => btn.classList.remove("active"));
            this.classList.add("active");

            // data-days 속성 값을 가져와 숫자로 변환
            const days = parseInt(this.getAttribute("data-days"), 10);
            updateDates(days);
        });
    });
});
</script>




</head>
<body>
<div class="container mt-1">
	<div class="d-flex justify-content-between align-items-center border-bottom pb-2 mb-3">
      		<h5 class="mb-0 fw-bold">포인트 적립내역</h5>
    </div>
    <div class="border p-4 rounded mb-3" style="margin: 0 auto;">
    	<div class="d-flex align-items-center gap-3 flex-wrap">
    		<p class="mb-0 align-self-center">조회기간</p>
    		<div class="btn-group" role="group" aria-label="data filter buttons">
		    	<button type="button" class="btn btn-outline-secondary active" data-days="0">오늘</button>
		        <button type="button" class="btn btn-outline-secondary" data-days="7">7일</button>
		        <button type="button" class="btn btn-outline-secondary" data-days="15">15일</button>
		        <button type="button" class="btn btn-outline-secondary" data-days="30">1개월</button>
		        <button type="button" class="btn btn-outline-secondary" data-days="90">3개월</button>
		        <button type="button" class="btn btn-outline-secondary" data-days="365">1년</button>
    		</div>
    		
    		<div class="d-flex align-items-center gap-2">
    			<input type="date" id="start-date" class="form-control w-auto"/>
        		<span>~</span>
        		<input type="date" id="end-date" class="form-control w-auto"/>
    		
    			<!-- Search Button -->
      			<button type="button" class="btn btn-success">조회</button>
    		</div>
    	</div>
	</div>
	<table class="table table-hover table-custom">
		<thead class="table-header table-secondary">
			<tr>
				<td>날짜</td>
				<td>유형</td>
				<td>내용</td>
				<td>유효기간</td>
				<td>적립금 내역</td>
			</tr>
		</thead>
      	<tbody>
      	<c:forEach var="pointlog" items="${pointLogList}">
	      	<tr>
	      	  <td>${pointlog.created_at}</td>
	          <td>${pointlog.point_type}</td>
	          <td>${pointlog.description}</td>
	          <td>${pointlog.valid_until} 까지</td>
	          <td>${pointlog.amount}원</td>
	        </tr>
        </c:forEach>
		</tbody>
	</table>
	<nav aria-label="Page navigation">
	  <ul class="pagination justify-content-center">
	    <li class="page-item">
	      <a class="page-link" href="#" aria-label="Previous">
	        <span aria-hidden="true">Prev</span>
	      </a>
	    </li>
	    <li class="page-item active" aria-current="page">
	      <a class="page-link" href="#">1</a>
	    </li>
	    <li class="page-item"><a class="page-link" href="#">2</a></li>
	    <li class="page-item"><a class="page-link" href="#">3</a></li>
	    <li class="page-item"><a class="page-link" href="#">4</a></li>
	    <li class="page-item"><a class="page-link" href="#">5</a></li>
	    <li class="page-item"><a class="page-link" href="#">6</a></li>
	    <li class="page-item"><a class="page-link" href="#">7</a></li>
	    <li class="page-item"><a class="page-link" href="#">8</a></li>
	    <li class="page-item"><a class="page-link" href="#">9</a></li>
	    <li class="page-item"><a class="page-link" href="#">10</a></li>
	    <li class="page-item">
	      <a class="page-link" href="#" aria-label="Next">
	        <span aria-hidden="true">Next</span>
	      </a>
	    </li>
	  </ul>
	</nav>
</div>
</body>
</html>