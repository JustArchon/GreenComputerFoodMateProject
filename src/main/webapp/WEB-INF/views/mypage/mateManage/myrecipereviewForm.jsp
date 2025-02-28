<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</style>
</head>
<body>
<div class="container mt-1">
	<div class="d-flex justify-content-between align-items-center border-bottom pb-2 mb-3">
      		<h5 class="mb-0 fw-bold">레시피 리뷰</h5>
    </div>
	<table class="table table-hover table-custom">
		<thead class="table-header table-secondary">
			<tr>
				<td>리뷰</td>
				<td>별점</td>
				<td>작성날짜</td>
				<td>바로가기</td>
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