<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
  var contextPath = "${contextPath}";

  // 페이지 준비 시 실행
  $(document).ready(function() {
    // 주소 보기 버튼 클릭 시
    $(".view-address-button").click(function() {
      var ordId = $(this).data("ord-id"); // 버튼에서 ord_id 가져오기
      getAddress(ordId);  // 주소를 가져오는 함수 호출
    });
    
    // 배송상태-1인 버튼(취소버튼) 클릭 시
    $(".shipping-status-1").click(function() {
	   	var ordId = $(this).data("ord-id"); // 버튼에서 ord_id 가져오기
	   	if (confirm("주문을 취소하시겠습니까?")) {
	   	    updateOrderStatus(ordId, null, null, 5);
	   	}
    });
    
	// 배송상태-3인 버튼(구매확정버튼) 클릭 시
    $(".shipping-status-3").click(function() {
	   	var ordId = $(this).data("ord-id"); // 버튼에서 ord_id 가져오기
	   	if (confirm("구매를 확정하시겠습니까?")) {
	   	    updateOrderStatus(ordId, null, null, 4);
	   	}
    });

    // 배송 상태 자동 조회
    $('.shipping-status-2').each(function() {
      var delCode = $(this).data('delcode');  // 배송사 코드
      var waybillNum = $(this).data('waybillnum');  // 운송장 번호
      getDeliveryStatus(delCode, waybillNum, $(this));  // 배송 조회 함수 호출
    });
  });

  // 주소 가져오기 AJAX 요청
  function getAddress(ordId) {
    $.ajax({
      type: "POST",
      url: contextPath + "/order/getAddress",
      data: { ord_id: ordId },
      success: function(response) {
        if (response.status === "success") {
          updateAddressModal(response.data);
        } else {
          handleError(response);
        }
      },
      error: function() {
        alert("서버 오류 발생.");
      }
    });
  }

  // 배송 상태 조회 AJAX 요청
  function getDeliveryStatus(delCode, waybillNum, $div) {
    $.ajax({
      url: contextPath + "/api/tracking",
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify({
        "carrierId": delCode,
        "trackingNumber": waybillNum
      }),
      success: function(trackResponse) {
        console.log(trackResponse);
        var statusCode = trackResponse.statusCode;
        if (statusCode === "DELIVERED") {
          updateOrderStatus(0, delCode, waybillNum, 3);  // 배송 완료 확인시 상태 갱신
        }
        $div.text("배송상태: " + statusCode);
      },
      error: function(xhr, status, error) {
        console.error("Request failed:", status, error);
        $div.text("배송 조회에 실패했습니다.");
      }
    });
  }

  // 주문 상태를 전달한 값으로 검색하고 업데이트
  function updateOrderStatus(ordId, delCode, waybillNum, ordStat) {
    $.ajax({
      url: contextPath + "/order/updateStatus",
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify({
    	"ord_id": ordId,
        "del_Code": delCode,
        "waybill_Num": waybillNum,
        "ord_Stat": ordStat
      }),
      success: function(response) {
        if (response.status === "success") {
          console.log("주문 상태 업데이트", response.message);
          location.reload();  // 페이지 새로 고침
        } else {
          console.error("업데이트 실패:", response.message);
        }
      },
      error: function(xhr, status, error) {
        console.error("업데이트 실패:", status, error);
      }
    });
  }

  // 주소 모달에 정보 업데이트
  function updateAddressModal(data) {
    $("#toName").text(data.toName);
    $("#toPhoneNum").text(data.toPhoneNum);
    $("#addr").text(data.addr);
    $("#addrDetail").text(data.addrDetail);
    $("#delivMessage").text(data.toName);
    $("#addressModal").modal("show"); // 모달 띄우기
  }

  // 오류 처리
  function handleError(response) {
    if (response.status === "error") {
      alert(response.message);
    } else {
      alert("알 수 없는 오류가 발생했습니다.");
    }
  }
</script>

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
        <tbody>
        	<tr>
        		<td>${order.ord_id}</td>
        		<td>${order.create_Date}</td>
        		<td>${order.tot_Pdt_Price}</td>
        		<td>${order.ship_Fee}</td>
        		<td>${order.tot_Pdt_Price + order.ship_Fee}</td>
        		<td>${order.ord_stat_msg}</td>
        		<td><button class="btn btn-outline-secondary btn-sm view-address-button" data-ord-id="${order.ord_id}">
        		배송지 확인</button></td>
        		<td>
        		<div class="d-flex flex-column gap-1">
				    <c:choose>
				        <c:when test="${order.ord_stat == 1}">
				            <button class="btn btn-outline-danger btn-sm shipping-status-${order.ord_stat}" data-ord-id="${order.ord_id}">주문취소</button>
				        </c:when>
				        <c:when test="${order.ord_stat == 2}">
				            <div class="shipping-status-${order.ord_stat}" data-delcode="${order.del_Code}" data-waybillnum="${order.waybill_Num}">조회 중...</div>
				        </c:when>
				        <c:when test="${order.ord_stat == 3}">
				            <button class="btn btn-outline-primary btn-sm shipping-status-${order.ord_stat}" data-ord-id="${order.ord_id}">구매확정</button>
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
                    <td>${detail.pdt_price}</td>
                    <td>${detail.qty}</td>
                </tr>
            </c:forEach>
        </tbody>
        </table>
        <div style="border:3px solid black; width:100%; margin-bottom: 10px;"></div>
        <!-- 임시 구분선 -->
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