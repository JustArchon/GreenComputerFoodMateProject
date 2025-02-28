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
<title>주문목록/배송조회</title>

<style>
.pagination .page-item .page-link {
	color: black; /* 기본 글자 색상 */
	border: 1px solid #ddd; /* 기본 테두리 */
}
		
.pagination .page-item.active .page-link {
	color: red; /* 활성화된 페이지 색상 */
	background-color: #fff; /* 배경색 */
	border-color: red; /* 활성화된 테두리 색상 */
}
		
.pagination .page-item .page-link:hover {
	background-color: #f8f9fa; /* 마우스 오버 배경색 */
	color: red; /* 마우스 오버 글자 색상 */
}

.text-truncate-multiline {
	display: -webkit-box; /* Flexbox로 텍스트 블록 설정 */
	-webkit-line-clamp: 2; /* 최대 두 줄까지 표시 */
	-webkit-box-orient: vertical; /* 수직 방향 박스 설정 */
	overflow: hidden; /* 넘치는 텍스트 숨기기 */
	text-overflow: ellipsis; /* 말줄임표 표시 */
	max-height: 3em; /* 최대 높이 (2줄 기준, line-height와 조정) */
	line-height: 1.5em; /* 줄 간격 */
}
		
.review-cell {
	position: relative;
}
		
.review-cell img {
	float: left; /* 이미지를 왼쪽으로 정렬 */
	margin-right: 10px; /* 텍스트와 이미지 간의 간격 */
	width: 50px;
	height: 50px;
	object-fit: cover;
	border-radius: 5px;
}
		
.review-cell {
	overflow: hidden; /* 셀 높이를 텍스트와 이미지에 맞게 조정 */
}

.productImg {
	width: 40px;
	height: 40px;
}
</style>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>

var contextPath = "${contextPath}";
$(document).ready(function(){ //페이지가 준비되면
	let orderList = [
	    <c:forEach var="order" items="${orderList}" varStatus="loop">
	        {
	            ord_id: "${order.ord_id}",
	            create_Date: "${order.create_Date}",
	            tot_Pdt_Price: parseFloat("${order.tot_Pdt_Price}"),
	            ship_Fee: parseFloat("${order.ship_Fee}"),
	            total: parseFloat("${order.tot_Pdt_Price}") + parseFloat("${order.ship_Fee}"),
	            ord_stat_msg: "${order.ord_stat_msg}"
	        }<c:if test="${!loop.last}">,</c:if>
	    </c:forEach>
	];
    $(".view-address-button").click(function(){ //.view-address-btn 클래스가 클릭되면
        let ordId = $(this).data("ord-id"); // 버튼에서 ord_id 가져오기
		
        $.ajax({
            type: "POST",
            url: contextPath + "/order/getAddress",
            data: { ord_id: ordId },
            success: function(response) {
                if (response.status === "success") {
                    $("#toName").text(response.data.toName);
                    $("#toPhoneNum").text(response.data.toPhoneNum);
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
	  
	  function renderTable(data) { 
		    let tbody = $("#order-list-body");
		    tbody.empty(); // 기존 데이터 삭제

		    data.forEach(item => {
		        tbody.append(`
		            <tr>
		                <td>${item.ord_id}</td>
		                <td>${item.create_Date}</td>
		                <td>${item.tot_Pdt_Price}</td>
		                <td>${item.ship_Fee}</td>
		                <td>${item.total}</td>
		                <td>${item.ord_stat_msg}</td>
		                <td><button class="btn btn-outline-secondary btn-sm view-address-button" data-ord-id="${item.ord_id}">
		                배송지 확인</button></td>
		                <td><button class="btn btn-outline-secondary btn-sm view-shipping-button" data-ord-id="${item.ord_id}">
		                배송상태조회</button></td>
		            </tr>
		        `);
		    });
		}

	  function filterByDate() {
		    let startDate = $("#start-date").val();
		    let endDate = $("#end-date").val();

		    if (!startDate || !endDate) {
		        alert("시작 날짜와 종료 날짜를 선택하세요.");
		        return;
		    }

		    let filteredData = orderList.filter(item => {
		        let orderDate = new Date(item.create_Date);
		        
		        return orderDate >= new Date(startDate) && orderDate <= new Date(endDate);
		    });

		    renderTable(filteredData);
		}

	    $(document).ready(function () {

	        // 조회 버튼 클릭 이벤트
	        $("#search-date").on("click", );
	    });
});

$(document).ready(function() {
	  // 페이지 로딩 시, 배송 상태를 자동으로 조회
	  $('.shipping-status').each(function() {
	    var del_code = $(this).data('delcode');  // data-delcode에서 배송사 코드 가져오기
	    var waybill_num = $(this).data('waybillnum');  // data-waybillnum에서 운송장 번호 가져오기

	    var $div = $(this);
	    // 내 서버로 배송 조회 요청 보내기
	    $.ajax({
	      url: contextPath + "/api/tracking",  // 내 서버의 TrackingController 엔드포인트로 요청
	      method: "POST",
	      contentType: "application/json",
	      data: JSON.stringify({
	        "carrierId": del_code,  // del_code를 carrierId로 사용
	        "trackingNumber": waybill_num  // waybill_num을 trackingNumber로 사용
	      }),
	      success: function(trackResponse) {
	        console.log(trackResponse);
	        // 응답을 받아서 배송 상태를 페이지에 표시하는 로직
	        var statusCode = trackResponse.statusCode;  // 서버에서 받은 상태 코드
	        if (statusCode === "DELIVERED") {
	          updateOrderStatusTo3(del_code, waybill_num);  // 상태가 'DELIVERED'면 주문 상태를 갱신하는 함수 호출
	        }
	        // 문자열 연결 방식으로 텍스트 업데이트
	        $div.text("배송상태: " + statusCode);
	      },
	      error: function(xhr, status, error) {
	        console.error("Request failed:", status, error);
	        $div.text("배송 조회에 실패했습니다.");
	      }
	    });
	  });
	});

// 위에서 구현된, 이용자로 인해 주문내역 페이지가 로딩되었을때 자동으로 실행되는 배송조회 기능에서
// 배송상태가 "배송완료" 였을 경우 택배사코드랑 운송장번호만 넘겨서 db의 ord_stat을 배송완료로 바꿔주는 ajax 통신 
function updateOrderStatusTo3(del_code, waybill_num) {
	  $.ajax({
	    url: contextPath + "/order/updateStatusTo3",
	    method: "POST",
	    contentType: "application/json",
	    data: JSON.stringify({
	    	"del_Code": del_code,
	        "waybill_Num": waybill_num
	    }),
	    success: function(response) {
	      console.log("주문 상태가 업데이트되었습니다.", response);
	    },
	    error: function(xhr, status, error) {
	      console.error("주문 상태 업데이트 실패:", status, error);
	    }
	  });
	}

</script>
<%-- 현재 페이지 정보 가져오기 (기본값: 1페이지) --%>
<c:set var="currentPage" value="${param.page != null ? param.page : 1}" />
<c:set var="itemsPerPage" value="3" />
<c:set var="startIndex" value="${(currentPage - 1) * itemsPerPage}" />
<c:set var="endIndex" value="${currentPage * itemsPerPage}" />

<%-- 전체 데이터 개수 구하기 --%>
<c:set var="totalItems" value="${fn:length(orderList)}" />
<fmt:parseNumber var="parsedTotalPages" value="${(totalItems + itemsPerPage - 1) / itemsPerPage}" integerOnly="true" />
<c:set var="totalPages" value="${parsedTotalPages}" />
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
      			<button type="button" id="search-date" class="btn btn-success">조회</button>
    		</div>
    	</div>
	</div>
	
	<c:forEach var="order" items="${orderList}" varStatus="status">
	<c:if test="${status.index >= startIndex && status.index < endIndex}">
    	<table class="table table-hover table-custom">
        <thead class="table-header table-secondary">
        <tr>
        <th>주문 번호</th>
        <th>주문한 날짜</th>
        <th>총 상품 금액</th>
        <th>배송비</th>
        <th>최종 금액</th>
        <th>주문 상태</th>
        <th>배송지 조회</th>
        <th></th>
        </tr>
        </thead>
        <tbody id="order-list-body">
        	<tr>
        		<td>${order.ord_id}</td>
        		<td>${order.create_Date}</td>
        		<td><fmt:formatNumber value="${order.tot_Pdt_Price}" type="number" groupingUsed="true" />원</td>
        		<td><fmt:formatNumber value="${order.ship_Fee}" type="number" groupingUsed="true" />원</td>
        		<td><fmt:formatNumber value="${order.tot_Pdt_Price + order.ship_Fee}" type="number" groupingUsed="true" />원</td>
        		<td>${order.ord_stat_msg}</td>
        		<td><button class="btn btn-outline-secondary btn-sm view-address-button" data-ord-id="${order.ord_id}">
        		배송지 확인</button></td>
        		<td>
        		<div class="d-flex flex-column gap-1">
				    <c:choose>
				        <c:when test="${order.ord_stat == 1}">
				            <button class="btn btn-outline-danger btn-sm">주문취소</button>
				        </c:when>
				        <c:when test="${order.ord_stat == 2}">
				            <div class="shipping-status" data-delcode="${order.del_Code}" data-waybillnum="${order.waybill_Num}">조회 중...</div>
				        </c:when>
				        <c:when test="${order.ord_stat == 3}">
				            <button class="btn btn-outline-primary btn-sm">구매확정</button>
				        </c:when>
				        <c:when test="${order.ord_stat == 4}">
				            <button class="btn btn-outline-success btn-sm">리뷰하기</button>
				        </c:when>
				    </c:choose>
				</div>
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
                    <td><img class="productImg" src=${contextPath}/resources/images/${detail.img_path}>${detail.pdt_name}</td>
                    <td><fmt:formatNumber value="${detail.pdt_price}" type="number" groupingUsed="true" />원</td>
                    <td>${detail.qty}</td>
                </tr>
            </c:forEach>
        </tbody>
        </table>
        <div style="border:3px solid black; width:100%; margin-bottom: 10px;"></div>
        <!-- 임시 구분선 -->
        </c:if>
    </c:forEach>
	
	
<!-- 배송주소 보는 모달 창 -->
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
</main>
</body>
</html>