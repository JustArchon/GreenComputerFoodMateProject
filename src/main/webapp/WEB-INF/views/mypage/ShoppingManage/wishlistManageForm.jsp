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
<title>찜 리스트</title>
<link href="<c:url value="/resources/css/tablepage.css" />" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
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
<%-- 현재 페이지 정보 가져오기 (기본값: 1페이지) --%>
<c:set var="currentPage" value="${param.page != null ? param.page : 1}" />
<c:set var="itemsPerPage" value="10" />
<c:set var="startIndex" value="${(currentPage - 1) * itemsPerPage}" />
<c:set var="endIndex" value="${currentPage * itemsPerPage}" />

<%-- 전체 데이터 개수 구하기 --%>
<c:set var="totalItems" value="${fn:length(wishList)}" />
<fmt:parseNumber var="parsedTotalPages" value="${(totalItems + itemsPerPage - 1) / itemsPerPage}" integerOnly="true" />
<c:set var="totalPages" value="${parsedTotalPages}" />
<script>
var contextPath = '${contextPath}';
$(document).ready(function() {
    $("#checkAll").click(function() {
        let allChecked = $(".wish-checkbox").length === $(".wish-checkbox:checked").length;
        $(".wish-checkbox").prop("checked", !allChecked).trigger("change");
    });
    
 // 장바구니 추가 버튼 클릭 이벤트
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
                    alert(response.message); // 성공 메시지 표시
                 // 장바구니 추가 시 찜 목록에서도 제거
                    $.ajax({
                        url: contextPath + "/wishlist/remove",
                        type: "POST",
                        data: { "wishlistId": wishlistId },
                        success: function(removeResponse) {
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
});
</script>
<body>
<div class="container mt-1">
	<div class="d-flex justify-content-between align-items-center border-bottom pb-2 mb-3">
      		<h5 class="mb-0 fw-bold">찜 리스트</h5>
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
	      	<c:forEach var="wish" items="${wishList}" varStatus="status">
	      		<c:if test="${status.index >= startIndex && status.index < endIndex}">
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
	      		</c:if>
		    </c:forEach>
		</tbody>
	</table>
	<div class="text-start mb-1">
		<button class="btn btn-outline-danger btn-sm">선택 상품 삭제</button>
		<button class="btn btn-outline-success btn-sm">선택 상품 장바구니</button>
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
</body>
</html>