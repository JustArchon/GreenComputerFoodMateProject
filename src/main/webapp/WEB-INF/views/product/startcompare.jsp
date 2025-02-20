<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" isELIgnored="false"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />  
<html>
<head>
<style>
@import url('https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Do+Hyeon&family=Noto+Sans+KR:wght@100..900&display=swap');

#wrap_cmp {
    background-color: #f1f3f5;
}

#wrap_cmp #rcp_intro {
	padding: 20px 20px 10px;
	display: flex;
	justify-content: space-between;
}

#wrap_cmp #rcp_intro #rcp_imgAndName {
	font-family: "Do Hyeon", "Noto Sans KR", serif;
	text-align: center;
	font-size: 1.5rem;
}

#wrap_cmp #rcp_step {
    padding: 20px 30px 0 10px;
    font-size: 1.5rem;
    font-family: "Noto Sans KR", serif;
}

#wrap_cmp #rcp_step span.step-number {
    display: inline-block;
    width: 30px;
    height: 30px;
    line-height: 30px;
    border-radius: 50%;
    background-color: #2ecc71; /* 초록색 */
    color: white;
    text-align: center;
    font-weight: bold;
    margin-right: 10px;
}

#wrap_cmp .rcp_img {
	width: 200px;
	border-radius: 10px;
}

#wrap_cmp .section_container {
    background-color: white;
    width: 1200px;
    margin: 0px auto;
    display: flex;
}

#wrap_cmp #cmp_pdt {
    width: 65%;
}

#wrap_cmp #cmp_pdt .pdt_row {
	padding: 15px;
	border-bottom: 1px solid black;
}

#wrap_cmp #cmp_pdt .pdt_Name {
	font-family: "Noto Sans KR", serif;
	font-size: 1.5rem;
}

#wrap_cmp #cmp_pdt .pdt_Selr,
#wrap_cmp #cmp_pdt .pdt_Rank {
	font-family: "Noto Sans KR", serif;
	font-size: 1.2rem;
}

#wrap_cmp .pdt_img {
    width: 100px;
}

#wrap_cmp #cmp_pdt article {
    display: flex;
}

#wrap_cmp #cmp_pdt article div:first-child {
    margin-right: 20px;
}

#wrap_cmp #cmp_pdt article div:nth-child(2) {
    font-weight: bold;
}

#wrap_cmp #cmp_pdt article div:nth-child(3) {
	font-family: "Do Hyeon", "Black Han Sans", "Noto Sans KR", serif;
	font-size: 2rem;
    text-align: right;
    margin-left: auto;
    font-weight: 500;
    color: red;
    align-self: center;
}

#wrap_cmp #cmp_pdt article div:nth-child(3) .pdt_Qty,
#wrap_cmp #cmp_pdt article div:nth-child(3) .pdt_Unit {
	font-family: "Noto Sans KR", serif;
	font-size: 1.2rem;
    text-align: right;
    margin-left: auto;
    color: black;
    align-self: center;
}

#wrap_cmp #cmp_pdt article div:nth-child(3) .pdt_Unit {
	margin-right: 10px;
}

#wrap_cmp .pdt_chk {
	font-family: "Noto Sans KR", serif;
	font-size: 1.25rem;
}

#wrap_cmp #rcp_spread {
	font-family: "Noto Sans KR", serif;
	width: 100%;
	text-align: center;
	font-size: 1.5rem;
	background-color: #fa9820;
	margin-bottom: 20px;
	padding-top: 10px;
	padding-bottom: 10px;
	color: white;
}

<!-- 페이지네이션 부분 css -->

#wrap_cmp #pdt_pagenation {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 20px 0;
    font-family: "Noto Sans KR", sans-serif;
}

#wrap_cmp #pdt_pagenation .page_btn {
    background-color: #fa9820;
    color: white;
    border: none;
    padding: 10px 15px;
    margin: 0 5px;
    font-size: 1rem;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

#wrap_cmp #pdt_pagenation .page_btn:hover {
    background-color: #d9791b;
}

.product-qty {
	width: 30px;
}

</style>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
var contextPath = "${contextPath}";

// 레시피 재료 누르면 비교 시작하게 상품들 띄워주는 스크립트
$(document).on('click', '.rcp_Mtrs', function() {
    var categoryId = $(this).find('input[name="category_id"]').val();
    // 히든input에서 category_id 값 가져오기
    console.log(categoryId);
    
    $.ajax({
        url: contextPath + '/product/categorycompare',  // 요청할 URL
        method: 'GET',
        data: { category_id: categoryId },  // category_id를 GET 파라미터로 전달
        success: function(response) {
	        var products = response.products;  // 상품 목록 받기
	        var productHtml = '';
	
	        products.forEach(function(product) {
	        	
            productHtml += '<article class="pdt_row">' +
                '<div>' +
                    '<img class="pdt_img" src="' + contextPath + '/resources/images/' + product.img_path + '">' +
                '</div>' +
                '<div>' +
                    '<span class="pdt_Name">' + product.name + '</span><br>' +
                    '<span class="pdt_Selr">판매자 : ' + product.slr_nickname + '</span><br>' +
                    '<span class="pdt_Id">상품번호 : ' + product.pdt_id + '</span><br>' +
                    '<span class="pdt_Rank">' + '⭐⭐⭐⭐⭐' + '(후기 135)' + '</span><br>' +
                '</div>' +
                '<div>' +
                	'<span class="pdt_Qty">' + product.qty + '</span>' + '<span class="pdt_Unit">' + product.unit + '</span>' +
                	'<span class="pdt_Price">' + product.price + '</span>원 ' +
                    '<button class="pdt_chk" data-category-id="' + product.category_id + '">담기</button>' +
                '</div>' +
            '</article>';
            
	        });
	
	        $('#cmp_pdt').html(productHtml);  // 결과를 페이지에 반영
            
        } ,
        error: function(xhr, status, error) {
            console.error('AJAX 요청 실패', error);
            alert('상품 비교 중 오류가 발생했습니다.');
        }
    });
});

// 담기 누르면 재료목록에 담는 기능
$(document).on('click', '.pdt_chk', function() {
    var categoryId = $(this).data('category-id'); // 상품의 category_id 가져오기
    var productId = $(this).closest('.pdt_row').find('.pdt_Id').text().replace("상품번호 : ", "").trim();
    var productName = $(this).closest('.pdt_row').find('.pdt_Name').text(); // 상품 이름
    var price = parseInt($(this).closest('.pdt_row').find('.pdt_Price').text().trim());
    var productUnit = $(this).closest('.pdt_row').find('.pdt_Unit').text();

    var existingItem = $("#" + categoryId).find('.rcp_Mtrs_Product input[name="product-id"][value="' + productId + '"]').closest('.rcp_Mtrs_Product');
    
    if (existingItem.length > 0) {
        // 같은 상품이 이미 존재하면 수량 증가
        var qtyInput = existingItem.find('.product-qty');
        qtyInput.val(parseInt(qtyInput.val()) + 1);

        // 총 가격 업데이트
        updateTotalPrice(existingItem, price);
    } else {
        // 새로운 상품 추가
        var newItem = `
        	<div class="rcp_Mtrs_Product">
	            <div class="rcp_Mtrs_Cart">` + productName + `</div>
	            <div class="rcp_Mtrs_Cart_Amount">
	                <input type="number" class="product-qty" value="1" min="1">
	                <button class="remove-item">X</button>
	                <div class="total-price">` + price + `원</div>
	            </div>
	            <input type="hidden" name="product-id" value='` + productId + `'>
	            <input type="hidden" name="price" value='` + price + `'>
            </div>
        `;
        $("#" + categoryId).append(newItem);
    }
    updateLastTotalPrice();
});

//개별 상품 총 가격 업데이트 함수
function updateTotalPrice(item, price) {
    var qty = parseInt(item.find('.product-qty').val());
    var totalPriceElem = item.find('.total-price');
    var totalPrice = price * qty;
    
    totalPriceElem.text(totalPrice + "원");

    // 전체 최종 가격 업데이트
    updateLastTotalPrice();
}

// 전체 최종 가격 업데이트 함수
function updateLastTotalPrice() {
    var lastTotalPrice = 0;

    $(".rcp_Mtrs_Product").each(function() {
        var totalPriceText = $(this).find('.total-price').text().replace("원", "").trim();
        lastTotalPrice += parseInt(totalPriceText);
    });

    $("#last_total_price").text(lastTotalPrice);
}

// 수량 인풋 변경 시 총 가격 업데이트
$(document).on('input', '.product-qty', function() {
    var item = $(this).closest('.rcp_Mtrs_Product');
    var price = parseInt(item.find('input[name="price"]').val());
    updateTotalPrice(item, price);
});

// 삭제 버튼 클릭 시 해당 상품 삭제
$(document).on('click', '.remove-item', function() {
    $(this).closest('.rcp_Mtrs_Product').remove();
    updateLastTotalPrice();
});

// 담은 상품 모아서 장바구니로 ajax 던지기
$(document).on("click", "#cart-button", function() {
    console.log("장바구니 버튼 클릭됨!"); // 버튼 클릭 확인

    var dataToSend = [];

    $(".rcp_Mtrs_Product").each(function() {
        var productId = $(this).find('input[name="product-id"]').val();
        var quantity = $(this).find('.product-qty').val();

        dataToSend.push({
            pdt_id: productId,
            qty: quantity
        });
    });
    
    console.log('보낼데이터는');
    console.log(dataToSend);

    $.ajax({
    	url: contextPath + "/cart/compareaddtocart",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(dataToSend),
        success: function(response) {
            alert("장바구니에 상품들을 추가했습니다. 장바구니로 이동합니다.");
            window.location.href = contextPath + "/cart/cartlist";
        },
        error: function(error) {
            alert("장바구니에 상품들을 담는 중 오류가 발생하였습니다. 다시 시도해 주세요.");
        }
    });
});

</script>


</head>
<body>
<main id="wrap_cmp">
	<div class="section_container">
		<section id="rcp_intro">
			<article id="rcp_imgAndName">
				<img class="rcp_img" src="${contextPath}/resources/images/<c:out value='${recipe.mainimg_path}'/>">
				<br><c:out value="${recipe.title}" />
			</article>
		</section>
		
		<section id="rcp_step">
		    <div>
		        <span class="step-number">1</span> 이건중요한게아니니가<br>
		    </div>
		    <div>
		        <span class="step-number">2</span> 나중에반복문으로알아서가져오샘<br>
		    </div>
		    <div>
		        <span class="step-number">3</span> ㅇㅋ?<br>
		    </div>
		</section>
	</div>

	<div class="section_container">
		<section id="rcp_spread">
		레시피 펼치기⏬ 이것도 나중에 만들어
		</section>
	</div>

	<div class="section_container">
		<section id="cmp_pdt">
			<article class="pdt_row">
				<div>
					구매할 재료를 선택하세요
				</div>
			</article>
		</section>
		
<style>

#wrap_cmp #cmp_rcp {
    width: 35%;
    border: 1px solid black;
    background-color: #f1f3f5;
    font-family: "Noto Sans KR", sans-serif;
    padding: 20px 20px;
}

#cmp_rcp #rcp_Name,
#cmp_rcp #rcp_Writer,
#cmp_rcp #rcp_Rq_Mtr,
#cmp_rcp #rcp_Rq_Spice {
	text-align: center;
	font-size: 1.5rem;
}

#cmp_rcp #rcp_Rq_Mtr,
#cmp_rcp #rcp_Rq_Spice {
	font-size: 1.25rem;
	font-weight: 600;
}

#cmp_rcp .Mtrs,
#cmp_rcp .Spices {
	border: 1px solid black;
	background-color: white;
	margin-bottom: 20px;
}

#cmp_rcp .rcp_Mtrs,
#cmp_rcp .rcp_Spices {
	width: 100%;
	background-color: #fa9820;
	color: white;
	text-align: left;
	font-size: 1.1rem;
	font-weight: 600;
	border: none;
}

#cmp_rcp .rcp_Mtrs_Cart,
#cmp_rcp .rcp_Mtrs_Cart_Amount,
#cmp_rcp .rcp_Spices_Cart,
#cmp_rcp .rcp_Spices_Cart_Amount {	
	text-align: left;
	font-size: 1rem;
	padding-left: 20px;
	padding-right: 20px;
}

#cmp_rcp .rcp_Mtrs,
#cmp_rcp .rcp_Spices {
	padding: 5px 10px 5px;
	border-bottom: 1px solid gray;
}

#cmp_rcp .rcp_Mtrs_Cart,
#cmp_rcp .rcp_Spices_Cart {
	margin-top: 10px;
}

#cmp_rcp .rcp_Mtrs_Cart_Amount,
#cmp_rcp .rcp_Spices_Cart_Amount {
	margin-bottom: 10px;
	display: flex;
	justify-content: space-between;
	align-items: center
}

.Mtrs {
	border-bottom: 1px solid black !important; 
}

.Mtrs .rcp_Mtrs_Cart_Amount div:nth-child(2) {
  font-family: "Noto Sans KR", serif;
  font-size: 1.25rem;
  font-weight: 600;
}

.Mtrs .rcp_Mtrs_Cart_Amount div:last-child {
	margin-left: auto;
}

#cmp_rcp #cart-button {
    display: block;
    width: 250px;
    text-align: center;
    margin: 20px auto;
    padding: 10px 0;
    font-size: 1.5rem;
    font-weight: 600;
    color: white;
    background-color: #fa9820;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

#cmp_rcp .cart-button:hover {
    background-color: #d9791b;
}

#last_total_price_container {
	font-family: "Noto Sans KR", serif;
	font-size: 1.5rem;
	color: red;
}

</style>
		
		<section id="cmp_rcp">
			<article id="rcp_Rq_Mtr">
				재료 목록
				
				<c:forEach var="ingredient" items="${ingredients}">
				    <div class="Mtrs" id="<c:out value='${ingredient.category_id}' />">
				        <button class="rcp_Mtrs">
				            <c:out value="${ingredient.ingrd_name}" /> 
				            <c:out value="${ingredient.ingrd_qty}" /> 
				            <c:out value="${ingredient.unit}" />
				            <input type="hidden" name="category_id" value="<c:out value='${ingredient.category_id}' />">
				        </button>
				    </div>
				</c:forEach>

			</article>
			
			<article id="last_total_price_container">
			총 가격 : <span id="last_total_price"></span>원
			</article>
			
			<button id="cart-button">장바구니에 담기</button>
		</section>
	</div>
</main>

</body>
</html>