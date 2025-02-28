<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 레시피</title>
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
	    
	    .review { min-width: 600px; max-width: 600px; }
		.rating { min-width: 80px; max-width: 80px; }
		.date { min-width: 80px; max-width: 80px; }
		.link { min-width: 80px; max-width: 80px; }
</style>
</head>
<%-- 현재 페이지 정보 가져오기 (기본값: 1페이지) --%>
<c:set var="currentPage" value="${param.page != null ? param.page : 1}" />
<c:set var="itemsPerPage" value="8" />
<c:set var="startIndex" value="${(currentPage - 1) * itemsPerPage}" />
<c:set var="endIndex" value="${currentPage * itemsPerPage}" />

<%-- 전체 데이터 개수 구하기 --%>
<c:set var="totalItems" value="${fn:length(myrecipeReviewList)}" />
<fmt:parseNumber var="parsedTotalPages" value="${(totalItems + itemsPerPage - 1) / itemsPerPage}" integerOnly="true" />
<c:set var="totalPages" value="${parsedTotalPages}" />
<body>
<div class="container mt-1">
	<div class="d-flex justify-content-between align-items-center border-bottom pb-2 mb-3">
      		<h5 class="mb-0 fw-bold">레시피 리뷰</h5>
    </div>
	<table class="table table-hover table-custom">
		<thead class="table-header table-secondary">
			<tr>
				<td class="review">리뷰</td>
		        <td class="rating">별점</td>
		        <td class="date">작성날짜</td>
		        <td class="link">바로가기</td>
			</tr>
		</thead>
      	<tbody>
      	<c:forEach var="review" items="${myrecipeReviewList}">
	      	<tr>
	          <td class="review-cell">
	          	<img src="${contextPath}/resources/images/${review.mainimg_path}" alt="${review.mainimg_path}" class="img-fluid rounded" style="width: 50px; height: 50px; object-fit: cover;">
	          	<span class="text-truncate-multiline">
	          		${review.comments}
	          	</span>
	          </td>
	          <td><c:choose>
	          <c:when test="${review.rating == 5}">⭐⭐⭐⭐⭐</c:when>
	          <c:when test="${review.rating == 4}">⭐⭐⭐⭐</c:when>
	          <c:when test="${review.rating == 3}">⭐⭐⭐</c:when>
	          <c:when test="${review.rating == 2}">⭐⭐</c:when>
	          <c:when test="${review.rating == 1}">⭐</c:when>
	          <c:otherwise></c:otherwise>
	          </c:choose></td>
	          <td>${review.create_date}</td>
	          <td>
	            <button class="btn btn-outline-secondary btn-sm" onclick="location.href='${contextPath}/recipe/recipe_Detail?rcp_id=${review.rcp_id}';">바로가기</button>
	          </td>
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