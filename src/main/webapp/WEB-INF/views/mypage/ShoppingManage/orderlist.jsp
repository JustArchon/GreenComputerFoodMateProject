<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문목록/배송조회</title>
<link href="<c:url value="/resources/css/tablepage.css" />" rel="stylesheet">

</head>
<body>
<main>
<div class="container mt-1">
	<div class="d-flex align-items-center border-bottom pb-2 mb-3">
      		<h5 class="mb-0 fw-bold">주문목록/배송조회</h5>
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
    			<input type="date" id="start-date" class="form-control w-auto" value="2025-01-06" />
        		<span>~</span>
        		<input type="date" id="end-date" class="form-control w-auto" value="2025-01-13" />
    		
    			<!-- Search Button -->
      			<button type="button" class="btn btn-success">조회</button>
    		</div>
    	</div>
	</div>
	
	<c:forEach var="order" items="${orderList}">
	<c:forEach var="detail" items="${order.orderDetails}">
    	<table class="table table-hover table-custom">
        <thead class="table-header table-secondary">
        <tr>
        <th>주문 번호</th>
        <th>주문한 날짜</th>
        <th>총 상품 금액</th>
        <th>배송비</th>
        <th>최종 금액</th>
        <th>주문 상태</th>
        
        <!-- 여기서도 주문지 확인할수있도록 작업할것 -->
        
        <th>운송장 번호</th>
        </tr>
        </thead>
        <tbody>
        	<tr>
        		<td>${order.ord_id}</td>
        		<td>${order.create_Date}</td>
        		<td>${order.tot_Pdt_Price}</td>
        		<td>${order.ship_Fee}</td>
        		<td>${order.tot_Pdt_Price + order.ship_Fee}</td>
        		<td>${order.ord_stat_msg}</td>
        		<td>${order.del_Code}, ${order.waybill_Num}</td>
        	</tr>
        </tbody>
		</table>
		
        <table class="table table-hover table-custom">
        <thead class="table-header table-secondary">
            <tr>
                <!-- 주문 날짜 및 주문번호 -->
                <td>${order.create_Date}(${order.ord_id})</td>

                <!-- 상품명과 옵션 -->
                <td>
                    <img src="${contextPath}/resources/images/${detail.img_path}" alt="${detail.pdt_name}" class="img-fluid rounded" style="width: 50px; height: 50px; object-fit: cover;">
                    ${detail.pdt_name}
                </td>

                <!-- 상품 금액 및 수량 -->
                <td>${detail.pdt_price}원 (${detail.qty}개)</td>

                <!-- 주문 상태 -->
                <td>
	                <c:choose> 
	                	<c:when test="${order.ord_stat == 0}">결제 대기</c:when>
				        <c:when test="${order.ord_stat == 1}">배송 준비중</c:when>
				        <c:when test="${order.ord_stat == 2}">배송 중</c:when>
				        <c:when test="${order.ord_stat == 3}">배송 완료</c:when>
				        <c:when test="${order.ord_stat == 4}">구매 확정</c:when>
				        <c:when test="${order.ord_stat == 5}">주문 취소</c:when>
				        <c:when test="${order.ord_stat == 6}">반품</c:when>
				        <c:otherwise>알 수 없음</c:otherwise>
				    </c:choose>
                </td>
				<!-- 주문상태 하드코딩함. 나중에 주문 코드-설명 테이블 만들고 조인해서 갖고와 -->
                <!-- 확인/리뷰 -->
                <td>
                    <div class="d-flex flex-column gap-1">
                        <button class="btn btn-outline-secondary btn-sm" disabled>배송조회</button>
                        <button class="btn btn-outline-success btn-sm" disabled>리뷰하기</button>
                    </div>
                </td>
                <th>상품명</th>
                <th>상품 가격</th>
                <th>수량</th>
            </tr>
        </thead>
        <tbody>
            
                <tr>
                    <td>${detail.pdt_name}</td>
                    <td>${detail.pdt_price}</td>
                    <td>${detail.qty}</td>
                </tr>
        </tbody>
        </table>
        <div style="border:3px solid black; width:100%; margin-bottom: 10px;"></div>
        <!-- 임시 구분선 -->
    </c:forEach>
    </c:forEach>
	
	
<!-- 	<table class="table table-hover table-custom"> -->
<!--     <thead class="table-header table-secondary"> -->
<!--         <tr> -->
<!--             <td>날짜/주문번호</td> -->
<!--             <td>상품명/옵션</td> -->
<!--             <td>상품금액/수량</td> -->
<!--             <td>주문상태</td> -->
<!--             <td>확인/리뷰</td> -->
<!--         </tr> -->
<!--     </thead> -->
    
<!--     <tbody> -->
<%--         <c:forEach var="order" items="${orderList}"> --%>
<!--             <tr> -->
<!--                 주문 날짜 및 주문번호 -->
<%--                 <td>${order.create_date}(${order.ord_code})</td> --%>

<!--                 상품명과 옵션 -->
<!--                 <td> -->
<!--                 	<a href=${contextPath}/product/pdtdetail?pdt_id=${order.pdt_id}> -->
<%-- 	                    <img src="${contextPath}/resources/images/${order.img_path}" alt="${order.pdt_name}" class="img-fluid rounded" style="width: 50px; height: 50px; object-fit: cover;"> --%>
<%-- 	                    ${order.pdt_name} --%>
<!--                     </a> -->
<!--                 </td> -->

<!--                 상품 금액 및 수량 -->
<%--                 <td>${order.pdt_price}원 (${order.qty}개)</td> --%>

<!--                 주문 상태 -->
<!--                 <td> -->
<%-- 	                ${order.ord_stat} --%>
<!--                 </td> -->
<!-- 				주문상태 하드코딩함. 나중에 주문 코드-설명 테이블 만들고 조인해서 갖고와 -->
<!--                 확인/리뷰 -->
<!--                 <td> -->
<!--                     <div class="d-flex flex-column gap-1"> -->
<!--                         <button class="btn btn-outline-secondary btn-sm" disabled>배송조회</button> -->
<!--                         <button class="btn btn-outline-success btn-sm" disabled>리뷰하기</button> -->
<!--                     </div> -->
<!--                 </td> -->
<!--             </tr> -->
<%--         </c:forEach> --%>
<!--     </tbody> -->
    
<!-- </table> -->

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
</main>
</body>
</html>