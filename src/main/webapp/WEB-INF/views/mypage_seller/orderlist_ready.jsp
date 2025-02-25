<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>배송대기중인 주문</title>
<link href="<c:url value="/resources/css/tablepage.css" />" rel="stylesheet">

<style></style>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>

var contextPath = "${contextPath}";
$(document).ready(function(){ //페이지가 준비되면
    $(".view-address-button").click(function(){ //.view-address-btn 클래스가 클릭되면
    	console.log("버튼클릭");
        let ordId = $(this).data("ord-id"); // 버튼에서 ord_id 가져오기

        $.ajax({
            type: "POST",
            url: contextPath + "/order/getAddress",
            data: { ord_id: ordId },
            success: function(response) {
                if (response.status === "success") {
                    $("#toName").text(response.data.toName);
                    $("#toPhonenum").text(response.data.toPhoneNum);
                    $("#addr").text(response.data.addr);
                    $("#addrDetail").text(response.data.addrDetail);
                    $("#delivMessage").text(response.data.toName);
                    $("#addressModal").modal("show"); // 모달 띄우기
                } else if (response.status === "error") {
                    alert(response.message); // 이렇게 해도 되나? response.message가 있는지부터 확인해야하나?
                } else {
                	alert("알 수 없는 오류가 발생했습니다.")
                }
            },
            error: function() {
                alert("서버 오류 발생.");
            }
        });
    });
});
</script>


</head>
<body>
<main>
<div class="container mt-1">
	<div class="d-flex align-items-center border-bottom pb-2 mb-3">
      		<h5 class="mb-0 fw-bold">배송 대기중인 주문</h5>
    </div>
<!--     <div class="border p-4 rounded mb-3" style="margin: 0 auto;"> -->
<!--     	<div class="d-flex align-items-center gap-3 flex-wrap"> -->
<!--     		<p class="mb-0 align-self-center">조회기간</p> -->
<!--     		<div class="btn-group" role="group" aria-label="data filter buttons"> -->
<!-- 		    	<button type="button" class="btn btn-outline-secondary active" data-days="0">오늘</button> -->
<!-- 		        <button type="button" class="btn btn-outline-secondary" data-days="7">7일</button> -->
<!-- 		        <button type="button" class="btn btn-outline-secondary" data-days="15">15일</button> -->
<!-- 		        <button type="button" class="btn btn-outline-secondary" data-days="30">1개월</button> -->
<!-- 		        <button type="button" class="btn btn-outline-secondary" data-days="90">3개월</button> -->
<!-- 		        <button type="button" class="btn btn-outline-secondary" data-days="365">1년</button> -->
<!--     		</div> -->
    		
<!--     		<div class="d-flex align-items-center gap-2"> -->
<!--     			<input type="date" id="start-date" class="form-control w-auto" value="2025-01-06" /> -->
<!--         		<span>~</span> -->
<!--         		<input type="date" id="end-date" class="form-control w-auto" value="2025-01-13" /> -->
    		
<!--     			Search Button -->
<!--       			<button type="button" class="btn btn-success">조회</button> -->
<!--     		</div> -->
<!--     	</div> -->
<!-- 	</div> -->

    <c:forEach var="order" items="${orderList}">
    	<table class="table table-hover table-custom">
        <thead class="table-header table-secondary">
        <tr>
        <th>주문 번호</th>
        <th>주문한 날짜</th>
        <th>총 상품 금액</th>
        <th>배송비</th>
        <th>최종 금액</th>
        <th>주문 상태</th>
        <th>배송지 확인</th>
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
        		<td>
        		<button class="btn btn-outline-secondary btn-sm view-address-button" data-ord-id="${order.ord_id}">
        		배송지 확인</button>
        		<!-- view-address-button 클래스는 Jquery 용이므로 건드리지 마샘 -->
        		<!-- data-ord-id에 ord_id 넣어서 제이쿼리로 이 버튼 클릭할때 바로 ord_id 가져갈수있게할거임 -->
        		</td>
        		<td>
        		<button class="btn btn-outline-secondary btn-sm">운송장번호 입력</button>
        		</td>
        	</tr>
        </tbody>
		</table>
		
        <table class="table table-hover table-custom">
        <thead class="table-header table-secondary">
            <tr>
                <th>상품명</th>
                <th>상품 가격</th>
                <th>수량</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="detail" items="${order.orderDetails}">
                <tr>
                    <td>${detail.pdt_name}</td>
                    <td>${detail.pdt_price}</td>
                    <td>${detail.qty}</td>
                </tr>
            </c:forEach>
        </tbody>
        </table>
        <div style="border:3px solid black; width:100%; margin-bottom: 10px;"></div>
        <!-- 임시 구분선 -->
    </c:forEach>
<div id="addressModal" class="modal fade" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">배송지 정보</h5>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <p><strong>수령인 : </strong> <span id="toName"></span></p>
                <p><strong>수령인 전화번호 : </strong> <span id="toPhoneNum"></span></p>
                <p><strong>배송지 주소 : </strong> <span id="addr"></span></p>
                <p><strong>배송지 상세주소 : </strong> <span id="addrDetail"></span></p>
                <p><strong>배송 메시지 : </strong> <span id="delivMessage"></span></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>

<!-- 얘네는 옛날에 쓰던거임 -->
<!-- 	<table class="table table-hover table-custom"> -->
<!--     <thead class="table-header table-secondary"> -->
<!--         <tr> -->
<!--             <td>주문일</td> -->
<!--             <td>주문번호</td> -->
<!--             <td>상품명</td> -->
<!--             <td>상품금액/수량</td> -->
<!--             <td>주문상태</td> -->
<!--             <td>배송지 정보</td> -->
<!--             <td>운송장번호</td> -->
<!--         </tr> -->
<!--     </thead> -->
    
<!--     <tbody> -->
<%--         <c:forEach var="order" items="${orderList}"> --%>
<!--             <tr> -->
<!--                 주문 날짜 및 주문번호 -->
<%--                 <td>${order.create_date}</td> --%>
<%-- 				<td>${order.ord_id}</td> --%>
<!--                 <td class="ImgAndName"> -->
<!--                     <a href=${contextPath}/product/pdtdetail?pdt_id=${order.pdt_id}> -->
<%-- 	                    <img src="${contextPath}/resources/images/${order.img_path}" alt="${order.pdt_name}" class="img-fluid rounded" style="width: 50px; height: 50px; object-fit: cover;"> --%>
<%-- 	                    ${order.pdt_name} --%>
<!--                     </a> -->
<!--                 </td> -->

<!--                 상품 금액 및 수량 -->
<%--                 <td>${order.qty}개 (${order.pdt_price}원)</td> --%>

<!--                 주문 상태 -->
<!--                 <td> -->
<%-- 	                ${order.ord_stat_msg} --%>
<!--                 </td> -->
				
<!--                 <td><button class="btn btn-outline-secondary btn-sm" disabled>배송지정보 조회</button></td> -->
                
<!--                 <td> -->
<%-- 					<c:choose>    	 --%>
<%-- 					<c:when test="${order.ord_stat == 1}"><button class="btn btn-outline-secondary btn-sm" disabled>운송장번호 입력</button></c:when> --%>
<%-- 					<c:when test="${order.ord_stat == 2 || order.ord_stat == 3 || order.ord_stat == 4 || order.ord_stat == 5}">${order.del_code}${order.waybill_num}</c:when> --%>
<%-- 					<c:otherwise>없음</c:otherwise> --%>
<%-- 					</c:choose> --%>
<!--                 </td> -->
<!--             </tr> -->
<%--         </c:forEach> --%>
<!--     </tbody> -->
    
<!-- </table> -->

<!-- 	<nav aria-label="Page navigation"> -->
<!-- 	  <ul class="pagination justify-content-center"> -->
<!-- 	    <li class="page-item"> -->
<!-- 	      <a class="page-link" href="#" aria-label="Previous"> -->
<!-- 	        <span aria-hidden="true">Prev</span> -->
<!-- 	      </a> -->
<!-- 	    </li> -->
<!-- 	    <li class="page-item active" aria-current="page"> -->
<!-- 	      <a class="page-link" href="#">1</a> -->
<!-- 	    </li> -->
<!-- 	    <li class="page-item"><a class="page-link" href="#">2</a></li> -->
<!-- 	    <li class="page-item"><a class="page-link" href="#">3</a></li> -->
<!-- 	    <li class="page-item"><a class="page-link" href="#">4</a></li> -->
<!-- 	    <li class="page-item"><a class="page-link" href="#">5</a></li> -->
<!-- 	    <li class="page-item"><a class="page-link" href="#">6</a></li> -->
<!-- 	    <li class="page-item"><a class="page-link" href="#">7</a></li> -->
<!-- 	    <li class="page-item"><a class="page-link" href="#">8</a></li> -->
<!-- 	    <li class="page-item"><a class="page-link" href="#">9</a></li> -->
<!-- 	    <li class="page-item"><a class="page-link" href="#">10</a></li> -->
<!-- 	    <li class="page-item"> -->
<!-- 	      <a class="page-link" href="#" aria-label="Next"> -->
<!-- 	        <span aria-hidden="true">Next</span> -->
<!-- 	      </a> -->
<!-- 	    </li> -->
<!-- 	  </ul> -->
<!-- 	</nav> -->
</div>
</main>
</body>
</html>