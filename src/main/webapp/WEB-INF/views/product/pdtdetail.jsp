<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />  
<html>
<head>
<link href="<c:url value="/resources/css/pdtdetail.css" />" rel="stylesheet">

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>

function addToCart() {
    var productId = $("#pdt_ID").text();
    var quantity = $("#quantity").text();
    var contextPath = "${contextPath}";

    $.ajax({
        type: "POST",
        url: contextPath + "/cart/addtocart",
        data: {
            "productId": productId,
            "quantity": quantity
        },
        success: function(response) {
            if (response.success) {
                var continueShopping = confirm(response.message);
                if (!continueShopping) {
                    window.location.href = contextPath + "/cart/cartlist";
                }
            }
        },
        error: function(jqXHR) { // 요청이 실패했을 때 실행
            if (jqXHR.status === 401 && jqXHR.responseJSON) { 
                // 401(Unauthorized) 상태 코드이며 JSON 응답이 존재할 경우
                var loginToShopping = confirm(jqXHR.responseJSON.alertMsg); 
                // 로그인 필요 메시지를 확인 창으로 띄움
                if (loginToShopping) { 
                    // "예" 선택 시 로그인 페이지로 이동
                    window.location.href = contextPath + "/member/loginForm";
                }
            } else { 
                // 그 외의 에러 발생 시 기본적인 오류 메시지 출력
                alert("장바구니에 담는 중 오류가 발생했습니다.");
            }
        }
    });

}
</script>

</head>

<c:set var="product" value="${pdt}"/>

<body>
<main id="product_detail">

<article class="category">
    <div id="category_text">
        카테고리 : 
        <c:choose>
            <c:when test="${not empty category}">
                <c:forEach var="cat" items="${category}" varStatus="status">
                    ${cat.name} 
                    <c:if test="${not status.last}"> > </c:if> 
                    <!-- 마지막 요소가 아니면 ">" 추가 -->
                </c:forEach>
            </c:when>
            <c:otherwise>
                전체
            </c:otherwise>
        </c:choose>
    </div> 
</article>

<article class="container_pdt">
	<img id="pdt_Img" src="${contextPath}/resources/images/${product.img_path}" alt="productImg">
	<div class="info">
		<div class="info1">
			상품번호 : <span id="pdt_ID">${product.pdt_id}</span>
		</div>
		<div class="info2">
			<div id="pdt_Name">${product.name}</div>
			<div id="pdt_Selr">판매자 : ${product.slr_nickname}</div>
			<div id="pdt_Dsc">${product.description}</div>
		</div>
		<div class="info3">
			<div id="text_Price">판매가</div>
			
<%-- 			 <c:choose> --%>
<%--                 <c:when test="${isSale}"> --%>
<!--                     <div id="pdt_DiscountedPrice"> -->
<!--                         <span id="pdt_DsctPer">-10&#37;&#32;</span> -->
<%--                         <span id="pdt_Price">${product.discountedPrice}&#32;</span> <!-- 할인 가격 --> --%>
<!--                     </div> -->
<%--                 </c:when> --%>
<%--                 <c:otherwise> --%>
<!--                     <div id="pdt_DiscountedPrice"> -->
<%--                         <span id="pdt_DsctPer">${product.price}&#32;</span>원 --%>
<!--                     </div> -->
<%--                 </c:otherwise> --%>
<%--             </c:choose> --%>
<!--             이부분은 나중에 할인기능 구현할때 넣을거임 -->
            
            <div id="pdt_DiscountedPrice">
                <span id="pdt_DsctPer">${product.price}&#32;</span>원
            </div>

			<div id="text_DelivFee">배송비</div>
			<div id="pdt_DelivPee">3,000원(40,000원 이상 구매 시 무료)</div>
			<div id="pdt_Rank">⭐⭐⭐⭐⭐</div><div id="pdt_ReviewAmt">(상품평 n개)</div>
		</div>
		<div class="info4">
			<div id="pdt_Amount">수량 : 
			    <button id="btn_decrease">-</button>
			    <span id="quantity">1</span>
			    <button id="btn_increase">+</button>
			</div>
			<div id="price_final">총 <span id="final_price">${product.price}</span> 원</div>
		</div>
		<div class="info5">
			<button id="btn_buy" onclick="window.location.href='${contextPath}/order/order1'">바로구매</button>
			<button id="btn_cart" onclick="addToCart()">장바구니 담기</button>
		</div>
	</div>
</article>

<jsp:include page="pdtdetail_misc.jsp">
	<jsp:param name="pdt_ID" value="${product.pdt_id}"/>
</jsp:include>

</main>



</body>

<script>
    var productPrice = ${product.price};  // 원래 가격

    var quantity = 1;  // 초기 수량

    function changeQuantity(change) {
        quantity += change;
        if (quantity < 1) quantity = 1;  // 수량 최소 1로 제한

        var totalPrice = productPrice * quantity;  // 총 가격 계산

        // 화면에 표시된 수량과 가격 갱신
        document.getElementById('quantity').textContent = quantity;
        document.getElementById('final_price').textContent = totalPrice.toLocaleString();
    }

    document.addEventListener("DOMContentLoaded", function() {
        // 버튼 클릭 시 수량 변경
        document.getElementById("btn_decrease").addEventListener("click", function() {
            changeQuantity(-1);
        });
        document.getElementById("btn_increase").addEventListener("click", function() {
            changeQuantity(1);
        });
    });
</script>


</html>
