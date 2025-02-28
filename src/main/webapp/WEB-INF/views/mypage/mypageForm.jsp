<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css"/>
<link href="<c:url value="/resources/css/slide.css" />" rel="stylesheet">
<script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
<link href="<c:url value="/resources/css/profile.css" />" rel="stylesheet">
<style>
		.myrecipy{
			width: 100%;
			margin: 0 auto;
		}
        .slick-slide img {
            width: 100%; /* 이미지 너비 조정 */
            height: 350px; /* 이미지 비율 유지 */
            object-fit: cover;
        }
        .slick-slide {
            margin: 0 10px; /* 슬라이드 간 간격 */
        }
        .slick-prev{
			left: 40px;
		    z-index: 1;
		}
		.slick-next{
			right: 40px;
		    z-index: 1;
		}
		.table-custom {
		    border-top: 2px solid #000 !important;
	 	 }
		/* 헤더 배경색 */
	    .table-header {
		  background-color: #f8f9fa;
	      text-align: center;
	      font-weight: bold;
	    }
	    /* 내용 텍스트 정렬 */
	    .table tbody td {
	      vertical-align: middle;
	      text-align: center;
	    }
</style>
<script>
var contextPath = '${contextPath}';
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
        	
            $("#checkAll").click(function() {
                let allChecked = $(".wish-checkbox").length === $(".wish-checkbox:checked").length;
                $(".wish-checkbox").prop("checked", !allChecked).trigger("change");
            });
            
            $('.myrecipy').slick({
                slidesToShow: 3,
                slidesToScroll: 1,
                autoplay: true,
                autoplaySpeed: 3500
            });
            
            $(".btn-delete-wish").click(function() {
                if (!confirm("정말로 찜 상품을 삭제하시겠습니까?")) return;

                let row = $(this).closest("tr");
                let wishlistId = row.attr("data-wishid");

                $.ajax({
                    url: contextPath + "/wishlist/remove", // 위시리스트에서 삭제 요청을 보낼 서블릿
                    type: "POST",
                    data: { wishlistId: wishlistId },
                    success: function(response) {
                        alert("성공적으로 찜 상품을 삭제했습니다.");
                        row.remove(); // 삭제 후 해당 행 제거
                        location.reload(true);
                    },
                    error: function() {
                        alert("찜 상품을 삭제 실패했습니다.");
                    }
                });
            });
            
            $(".btn-add-cart").click(function() {
            	let row = $(this).closest("tr");
            	var wishlistId = row.attr("data-wishid");
                var productId = row.attr("data-id");
                var quantity = row.attr("data-qty");

                $.ajax({
                    url: contextPath + "/cart/addtocart",  // Spring 컨트롤러의 URL에 맞게 변경
                    type: "POST",
                    data: {
                        "productId": productId,
                        "quantity": quantity
                    },
                    success: function(response) {
                        if (response.success) {
                         // 장바구니 추가 시 찜 목록에서도 제거
                            $.ajax({
                                url: contextPath + "/wishlist/remove",
                                type: "POST",
                                data: { "wishlistId": wishlistId },
                                success: function(removeResponse) {
                                		alert("장바구니 추가에 성공했습니다!");
                                        row.remove(); // UI에서도 삭제
                                        location.reload(true);
                                }
                            });
                            
                        } else {
                            alert("장바구니 추가에 실패했습니다. 다시 시도해주세요.");
                        }
                    },
                    error: function() {
                        alert("장바구니 추가 중 오류가 발생했습니다.");
                    }
                });
            });
        });
</script>
<meta charset="UTF-8">
<title>마이페이지</title>
</head>
<body>
	<div class="container mt-2">
		<div class="row">
			<div class="col-md-3 mt-3">
				<div class="mainbox">
					<div class="name_icon_btn">
						<a href="${contextPath}/mypage/myInfoManage/profileEditForm">
							<img src="${contextPath}/resources/images/${profile.img_path}" alt="" />
						</a>
						<p> ${sessionScope.buyerInfo.name} 회원님 </p>
						<p> 보유포인트 </p>
						<p> ${sessionScope.buyerInfo.points} 포인트 </p>
						<p> 보유쿠폰 </p>
						<p> 0개 </p>
					</div>
				</div>
			</div>
			<div class="col-md-9">
			<h5><strong>내 레시피</strong></h5>
    			<div class="myrecipy">
					<c:forEach var="recipe" items="${myrecipeList}">
			    		<a href="${contextPath}/recipe/recipe_Detail?rcp_id=${recipe.rcp_id}">
						    <div class="item-img position-relative">
						        <img src="${contextPath}/resources/images/${recipe.mainimg_path}" alt="Recipe Image" class="img-fluid">
						        <div class="item-title d-flex flex-column justify-content-center align-items-center">
						            <h5><strong>${recipe.title}</strong></h5>
						            <div class="item_etc">
					                	<p><span>${recipe.create_date}</span>  리뷰 <span>${recipe.review_count}</span>개  조회수: <span>0</span></p>
					                </div>
					               	<div class="item_review_star">
					                	<p><span>
					                	<c:choose>
							                <c:when test="${recipe.rating == 5}">⭐⭐⭐⭐⭐</c:when>
							                <c:when test="${recipe.rating == 4}">⭐⭐⭐⭐</c:when>
							                <c:when test="${recipe.rating == 3}">⭐⭐⭐</c:when>
							                <c:when test="${recipe.rating == 2}">⭐⭐</c:when>
							                <c:when test="${recipe.rating == 1}">⭐</c:when>
							                <c:otherwise></c:otherwise>
							            </c:choose>
							            </span></p>
					                </div>
					                <div class="item-footer">
					              		<p><span>작성자: ${recipe.nickname}</span></p>
					            	</div>
						        </div>
						    </div>
			    		</a>
					 </c:forEach>
				</div>
			</div>
		</div>
	  <div class="d-flex justify-content-between align-items-center mt-2 mb-2">
	  		<h5 class="mb-0 fw-bold">최근 주문 내역</h5>
	  		<p class="mb-0"><a href="${contextPath}/mypage/ShoppingManage/orderlist">더보기</a></p>
      </div>
      <table class="table table-hover table-custom">
		<thead class="table-header table-secondary">
			<tr>
				<td>주문번호</td>
				<td>주문일자</td>
				<td>상품</td>
				<td>수량</td>
				<td>주문상태</td>
				<td>확인/리뷰</td>
			</tr>
		</thead>
      	<tbody>
      	<c:set var="count1" value="0" />
			<c:forEach var="order" items="${orderList}">
			<c:if test="${count1 < 3}">
	            <tr>
	                <!-- 주문 날짜 및 주문번호 -->
	                <td>(${order.ord_id})</td>
	                
	                <td>${order.create_Date}</td>
	
	                <td>
	                    <img src="${contextPath}/resources/images/${order.img_path}" alt="${order.pdt_name}" class="img-fluid rounded" style="width: 50px; height: 50px; object-fit: cover;">
	                    ${order.pdt_name}
	                </td>
	
	                <!-- 상품 금액 및 수량 -->
	                <td><fmt:formatNumber value="${order.pdt_price}" type="number" groupingUsed="true" />원 (${order.qty}개)</td>
	
	                <!-- 주문 상태 -->
	                <td>${order.ord_stat_msg}</td>
	
	                <!-- 확인/리뷰 -->
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
	            <c:set var="count1" value="${count1 + 1}" />
    		</c:if>
	        </c:forEach>
		</tbody>
	</table>
	<div class="d-flex justify-content-between align-items-center mb-2">
      		<h5 class="mb-0 fw-bold">찜 목록</h5>
      		<p class="mb-0"><a href="${contextPath}/mypage/ShoppingManage/wishlistManageForm">더보기</a></p>
      </div>
      <table class="table table-hover table-custom">
		<thead class="table-header table-primary">
			<tr>
				<td><input type="checkbox" id="checkAll"></td>
				<td>상품명</td>
				<td>상품금액/수량</td>
				<td>혜택</td>
				<td>주문</td>
			</tr>
		</thead>
      	<tbody>
      	<c:set var="count2" value="0" />
	      	<c:forEach var="wish" items="${wishList}" varStatus="status">
	      		<c:if test="${count2 < 3}">
			        <tr data-wishid="${wish.wishlist_id}" data-id="${wish.pdt_id}" data-name="${wish.name}" data-price="${wish.price}" data-qty="${wish.qty}">
			          <td><input type="checkbox" id="wish-check" class="wish-checkbox"></td>
			          <td>
			          <img src="${contextPath}/resources/images/${wish.img_path}" alt="${wish.name}" class="img-fluid rounded" style="width: 50px; height: 50px; object-fit: cover;">
			          ${wish.name}
			          </td>
			          <td>
			            <div class="d-flex flex-column gap-1 align-items-center">
					    	<span><fmt:formatNumber value="${wish.qty * wish.price}" type="number" groupingUsed="true" />원 / ${wish.qty}개</span>
					  	</div>
			          </td>
			          <td>적립<br>
			          <td>
			            <div class="d-flex flex-column gap-1 align-items-center">
			            	<button class="btn btn-outline-success btn-sm btn-add-cart">장바구니</button>
			            	<button class="btn btn-outline-danger btn-sm btn-delete-wish">삭제하기</button>
			           </div>
			          </td>
			        </tr>
			        <c:set var="count2" value="${count2 + 1}" />
	      		</c:if>
		    </c:forEach>
		</tbody>
	</table>
		<div class="d-flex justify-content-between align-items-center border-bottom pb-2 mb-3">
	      		<h5 class="mb-0 fw-bold">최근 본 상품</h5>
	    </div>
	</div>
	
</body>
</html>