<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
document.addEventListener("DOMContentLoaded", function() {
  const endDateInput = document.getElementById('end-date');
  const startDateInput = document.getElementById('start-date');
  const buttons = document.querySelectorAll('.btn-group button');

  // 오늘 날짜를 "yyyy-MM-dd" 형식으로 리턴하는 함수
  function getTodayFormatted() {
    return new Date().toISOString().substring(0, 10);
  }

  // 페이지 로드 시 end-date에 오늘 날짜 설정
  endDateInput.value = getTodayFormatted();

  // 버튼에 설정된 일수만큼 이전 날짜를 계산하여 start-date에 반영
  function updateStartDate(days) {
    const endDate = new Date(endDateInput.value);
    endDate.setDate(endDate.getDate() - days);
    startDateInput.value = endDate.toISOString().substring(0, 10);
  }

  // 기본으로 "오늘" 버튼 (data-days="0")에 따라 start-date 설정
  updateStartDate(0);

  // 각 버튼 클릭 이벤트 처리
  buttons.forEach(button => {
    button.addEventListener('click', function() {
      // 모든 버튼에서 active 클래스 제거 후, 클릭한 버튼에 active 추가
      buttons.forEach(btn => btn.classList.remove('active'));
      this.classList.add('active');

      const days = parseInt(this.getAttribute('data-days'), 10);
      updateStartDate(days);
    });
  });
});
</script>
<%-- 현재 페이지 정보 가져오기 (기본값: 1페이지) --%>
<c:set var="currentPage" value="${param.page != null ? param.page : 1}" />
<c:set var="itemsPerPage" value="6" />
<c:set var="startIndex" value="${(currentPage - 1) * itemsPerPage}" />
<c:set var="endIndex" value="${currentPage * itemsPerPage}" />

<%-- 전체 데이터 개수 구하기 --%>
<c:set var="totalItems" value="${fn:length(pointLogList)}" />
<c:set var="totalPages" value="${(totalItems + itemsPerPage - 1) / itemsPerPage}" />
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
	<%-- 페이지네이션 --%>
	<nav>
	    <ul class="pagination justify-content-center">
	        <c:if test="${currentPage > 1}">
	            <li class="page-item">
	                <a class="page-link" href="?page=${currentPage - 1}">Prev</a>
	            </li>
	        </c:if>
	
	        <c:forEach var="i" begin="1" end="${totalPages}">
	            <li class="page-item ${i == currentPage ? 'active' : ''}">
	                <a class="page-link" href="?page=${i}">${i}</a>
	            </li>
	        </c:forEach>
	
	        <c:if test="${currentPage < totalPages}">
	            <li class="page-item">
	                <a class="page-link" href="?page=${currentPage + 1}">Next</a>
	            </li>
	        </c:if>
	    </ul>
	</nav>
</div>
</body>
</html>