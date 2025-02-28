<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<link href="<c:url value="/resources/css/slide.css" />" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<style>
	.item-img img{
		width: 100%;
		height: 350px;
		object-fit: cover;
	}
	
	.recipe-item {
    display: none; /* 기본적으로 숨김 */
}
</style>
<meta charset="UTF-8">
<title>내 레시피</title>
</head>
<script>
$(document).ready(function () {
    let items = $(".recipe-item"); // 모든 레시피 가져오기
    let totalItems = items.length;
    let itemsPerPage = 6; // 한 번에 표시할 개수
    let currentIndex = 0;

    // 처음 6개만 보이도록 설정
    showItems();

    // "다음" 버튼 클릭 이벤트
    $("#next").click(function () {
        if (currentIndex + itemsPerPage < totalItems) {
            currentIndex += itemsPerPage;
            showItems();
        }
    });

    // "이전" 버튼 클릭 이벤트
    $("#prev").click(function () {
        if (currentIndex - itemsPerPage >= 0) {
            currentIndex -= itemsPerPage;
            showItems();
        }
    });

    function showItems() {
        items.hide(); // 모든 아이템 숨김
        for (let i = currentIndex; i < currentIndex + itemsPerPage && i < totalItems; i++) {
            $(items[i]).show(); // 현재 페이지의 아이템만 표시
        }
        updateButtons();
    }

    function updateButtons() {
        $("#prev").prop("disabled", currentIndex === 0);
        $("#next").prop("disabled", currentIndex + itemsPerPage >= totalItems);
    }
});

</script>
<body>
<div class="container mt-1">
	<div class="d-flex justify-content-between align-items-center border-bottom pb-2 mb-3">
      		<h5 class="mb-0 fw-bold">내 레시피</h5>
    </div>
	<div class="grid_container">
	    <c:forEach var="recipe" items="${myrecipeList}">
		    <div class="recipe-item"> <!-- 변경된 부분: a 태그가 아니라 div에 class 추가 -->
		        <a href="${contextPath}/recipe/recipe_Detail?rcp_id=${recipe.rcp_id}">
		            <div class="item-img position-relative">
		                <img src="${contextPath}/resources/images/${recipe.mainimg_path}" alt="Recipe Image" class="img-fluid">
		                <div class="item-title d-flex flex-column justify-content-center align-items-center">
		                    <h5><strong>${recipe.title}</strong></h5>
		                    <div class="item_etc">
		                        <p><span>${recipe.create_date}</span> 리뷰 <span>${recipe.review_count}</span>개 조회수: <span>0</span></p>
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
		    </div> <!-- .recipe-item 닫는 태그 -->
		</c:forEach>

		  
	</div>
	<div class="slider-controls">
    	<button type="button" class="btn btn-dark" id="prev" disabled>이전</button>
    	<button type="button" class="btn btn-dark" id="next" disabled>다음</button>
	</div>
</div>
</body>
</html>