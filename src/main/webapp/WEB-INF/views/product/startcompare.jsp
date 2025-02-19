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

#wrap_cmp .article_container {
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

</style>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
var contextPath = "${contextPath}";

// 버튼 클릭 시 이벤트 리스너
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
                    '<span class="pdt_Category">상품번호 : ' + product.pdt_id + '</span>' +
                '</div>' +
                '<div>' +
                    product.price + '원 ' +
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
</script>


</head>
<body>
<div id="wrap_cmp">
	<div class="article_container">
		<article id="rcp_intro">
			<section id="rcp_imgAndName">
				<img class="rcp_img" src="${contextPath}/resources/images/<c:out value='${recipe.mainimg_path}'/>">
				<br><c:out value="${recipe.title}" />
			</section>
		</article>
		
		<article id="rcp_step">
		    <div>
		        <span class="step-number">1</span> 이건중요한게아니니가<br>
		    </div>
		    <div>
		        <span class="step-number">2</span> 나중에반복문으로알아서가져오샘<br>
		    </div>
		    <div>
		        <span class="step-number">3</span> ㅇㅋ?<br>
		    </div>
		</article>
	</div>

	<div class="article_container">
		<div id="rcp_spread">
		레시피 펼치기⏬ 이것도 나중에 만들어
		</div>
	</div>

	<div class="article_container">
		<section id="cmp_pdt">
			<article class="pdt_row">
				<div>
				<img class="pdt_img" src="${contextPath}/resources/images/a1.jpg">
				</div>
				<div>
				<span class="pdt_Name">돼지고기 목살 600g</span><br>
				<span class="pdt_Selr">㈜루루축산</span><br>
				<span class="pdt_Rank">⭐⭐⭐⭐⭐(후기 135)</span><br>
				<span class="pdt_Category">식재료 > 축산 > 돼지고기</span>
				</div>
				<div>
				27,000원
				<button class="pdt_chk">담기</button>
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


.Mtrs .rcp_Mtrs_Cart_Amount div:nth-child(2) {
  font-family: "Noto Sans KR", serif;
  font-size: 1.25rem;
  font-weight: 600;
  color: red;
}

.Spices .rcp_Spices_Cart_Amount div:nth-child(2) {
  font-family: "Noto Sans KR", serif;
  font-size: 1.25rem;
  font-weight: 600;
  color: red;
}

#cmp_rcp .cart-button {
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
<!-- 				        <div class="rcp_Mtrs_Cart"> -->
<!-- 				            여기는 선택한 상품이 담기는 부분 -->
<!-- 				        </div> -->
<!-- 				        <div class="rcp_Mtrs_Cart_Amount"> -->
<!-- 				            <div>수량 - 1 +</div> -->
<!-- 				            <div>총가격 : n원</div>  -->
<!-- 				        </div> -->
				    </div>
				</c:forEach>

			</article>
			
			<button class="cart-button" onclick="window.location.href='${contextPath}/cart/cartForm'">장바구니에 담기</button>
		</section>
	</div>
</div>

</body>
</html>