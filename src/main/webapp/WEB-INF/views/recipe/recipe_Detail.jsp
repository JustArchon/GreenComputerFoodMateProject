<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<c:set var="recipe" value="${recipe}" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>레시피 상세</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/recipe_Detail.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body>

<div class="container recipe-container">
    <div class="row">
    	
        <!-- 레시피 정보 (작성자, 이미지, 제목, 시간 등) -->
        <div class="col-md-8">
            <!-- 작성자 닉네임 -->
            <div class="mb-4">
                <span class="badge bg-info text-dark">작성자: ${recipe.nickname}</span>
            </div>
            
            <!-- 레시피 제목 -->
            <div class="mb-4">
                <h2>${recipe.title}</h2>
            </div>

            <!-- 레시피 사진 -->
            <div class="mb-4">
                <img  src="${pageContext.request.contextPath}/resources/images/${recipe.mainimg_path}" alt="Recipe Image">
            </div>

            <!--  음식 이름 -->
            <div class="mb-4">
                <h2>${recipe.food_name}</h2>
            </div>
			
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
			
            <!-- 소요시간 -->
            <div class="mb-4">
                <span class="badge bg-warning text-dark">소요시간: ${recipe.req_time}</span>
            </div>

            <!-- 레시피 소개글 -->
            <div class="mb-4">
                <h2 class="recipe-section-title">레시피 소개</h2>
                <p>${recipe.description}</p>
            </div>
        </div>
        
    </div>
	
    <!-- 재료 목록 불러오기 -->
    <div class="mb-4">
		<h2 class="recipe-section-title">기본재료</h2>
			<div class="ingredients-box">
			    <c:forEach var="ingredient" items="${ingredients}">
			        <div class="ingredient-card">
			            <div class="ingredient-text">
			                ${ingredient.ingrd_name} ${ingredient.ingrd_qty}${ingredient.unit}
			            </div>
			        </div>
			    </c:forEach>
			</div>
	<button type="submit" class="submit-btn" onclick="window.location.href='${contextPath}/product/compare'">식재료 통합 구매</button>
	</div>
	
	<!-- 조리법 -->
	<div class="mb-4">
	    <h2 class="recipe-section-title">조리법</h2><br>
	    <div class="cooking-steps-box">
	        <c:forEach var="step" items="${steps}">
	            <div class="cooking-step-card">
	                <div class="cooking-step-image">
	                    <img src="${pageContext.request.contextPath}/resources/images/${step.stepimg_path}" alt="Step ${step.rcp_step}" class="step-image">
	                </div>
	                <div class="cooking-step-text">
	                    <h5>Step ${step.rcp_step}</h5>                   
	                    <p>${step.step_desc}</p>
	                </div>
	            </div>
	        </c:forEach>
	    </div>
	</div>


    <!-- 공유하기 아이콘 -->
    <div class="share-buttons mb-4">
                <h2 class="recipe-section-title">공유하기</h2>
                <a href="https://www.kakao.com/" target="_blank" class="share-icon ms-3">
                    <img src="${pageContext.request.contextPath}/resources/images/kakaotalk.png" alt="카카오톡" class="social-icon">
                </a>
                <a href="https://www.instagram.com/" target="_blank" class="share-icon ms-3">
                    <img src="${pageContext.request.contextPath}/resources/images/instagram.png" alt="인스타그램" class="social-icon"> 
                </a>
                <a href="https://www.facebook.com/" target="_blank" class="share-icon ms-3">
                    <img src="${pageContext.request.contextPath}/resources/images/facebook.png" alt="페이스북" class="social-icon">
                </a>
            </div>

   	<div class="comment-section">
	    <h2 class="recipe-section-title">레시피 평</h2>
	
	    <!-- 후기 작성하면 메시지로 알려줌 -->
	    <c:if test="${not empty message}">
	        <script>
	            alert("${message}");
	        </script>
	    </c:if>
	    <c:if test="${not empty error}">
	        <script>
	            alert("${error}");
	        </script>
	    </c:if>
	
	    <!-- 후기 작성 폼 -->
	    <form method="post" action="${pageContext.request.contextPath}/recipe/addRecipeRating?rcp_id=${param.rcp_id}">
	        <!-- 평점 숫자로 표시 -->
	        <div class="rating-display">
	            <span id="rating-text">4</span> / <span>5</span>
	        </div>
	
	        <!-- 평점 별 선택 -->
			<div>
			    <c:forEach var="i" begin="1" end="5">
			        <span class="star" onclick="setRating(${i})">★</span>
			    </c:forEach>
			</div>
	
	        <!-- 선택된 평점 값 hidden 처리로 서버에 넘기기 -->
	        <input type="hidden" id="rating" name="rating" value=4 readonly>
	
	        <!-- 댓글 입력 -->
	        <div>         
	            <label for="comments"></label>
	            <textarea id="comments" class="form-control" rows="3" name="comments" placeholder="레시피 후기를 남겨주세요..." required></textarea>                     
	            	<button class="w-btn-outline w-btn-red-outline" type="submit">후기 작성</button> <!-- 후기작성 맵핑이 로그인처리가 되어있어야 가능하도록 해놓음 -->
	        </div>
	    </form>
	    <hr class="comment-separator">
	<!-- ▲여기까지 후기작성 기능 -->

	<!-- ▼여기부터 후기출력 및 수정 기능 -->
	
	    <!-- 후기 리스트 -->
	<div class="comment-list mt-4">
		<!-- ▼전체 후기 데이터 리스트 테이블에서 가져온 것 -->
	    <c:forEach var="rating" items="${ratingList}">
	        <div class="comment">
	            <div class="comment-header">
	                <div class="profile-image">										  <!--▼댓글작성자 프로필사진 -->	
	                    <img src="${pageContext.request.contextPath}/resources/images/${rating.img_path}" alt="Profile Image">
	                </div>
	                <div class="comment-details"> <!--▼댓글작성자 닉네임 -->	
	                    <span class="username">${rating.nickname}</span>
	                    
									<span class="date-time">
									    <!-- ▼수정일이 없으면 작성일 표시 댓글작성 및 수정일자가 출력이 안되면 taglib uri fmt 체크해주세요-->
									    <c:if test="${empty rating.update_date}">
									        <fmt:formatDate value="${rating.create_date}" pattern="yyyy-MM-dd HH:mm" />
									    </c:if>
			
									    <!-- ▼수정일이 존재할 때만 수정일 표시 -->
									    <c:if test="${not empty rating.update_date}">
									        <fmt:formatDate value="${rating.update_date}" pattern="yyyy-MM-dd HH:mm" />
									        (수정됨)
									    </c:if>
									</span>
									
		                    <div class="rating-stars">
							    <c:forEach var="i" begin="1" end="${rating.rating}">
							        <span class="star_filled">★</span> <!-- 채워진 별 -->
							    </c:forEach>
							    <c:forEach var="i" begin="${rating.rating + 1}" end="5">
							        <span class="star_filled">☆</span> <!-- 빈 별 -->
							    </c:forEach>
							</div>			
							<br><br>	
												<!--▼후기 댓글 내용-->
												<p>${rating.comments}</p>
	                </div>
	            </div>
					
	            <div class="comment-content">
				        <!-- 수정 버튼 (작성자만 보이도록 조건 추가) -->
				    <c:if test="${rating.byr_id eq buyerInfo.byr_id}">
				        <button class="w-btn-outline w-btn-red-outline" type="button" onclick="editComment('${rating.cmt_rcp_rating_id}', '${rating.comments}')">후기 수정</button>
				    </c:if>
				    
				</div>
				
				<!-- 댓글 수정 폼 (초기에 숨김 처리) -->
				<div class="edit-form" id="edit-form-${rating.cmt_rcp_rating_id}" style="display: none;">
				    <textarea id="edit-text-${rating.cmt_rcp_rating_id}" class="form-control" rows="3">${rating.comments}</textarea>
				    <button type="button" class="w-btn-red-outline" onclick="submitEdit('${rating.cmt_rcp_rating_id}')">수정 완료</button>
				    <button type="button" class="w-btn-gray-outline" onclick="cancelEdit('${rating.cmt_rcp_rating_id}')">취소</button>
				</div>
				<hr class="comment-separator">
	        </div>
	    </c:forEach>
	</div>
	

<script>
    function setRating(value, isEdit = false) {
        let stars = document.querySelectorAll('.star'); // 모든 별(span) 요소 가져오기

        // 별 색상 업데이트
        stars.forEach((star, index) => {
            star.style.color = index < value ? "gold" : "gray";
        });

        // 별점 숫자 및 hidden input 업데이트 (수정 모드가 아닐 때만)
        if (!isEdit) {
            document.getElementById('rating-text').textContent = value; // 별점 숫자 업데이트
            document.getElementById('rating').value = value; // hidden input 값 저장
        }
    }

 // 댓글 수정 폼을 보이도록 하는 함수
    function editComment(commentId, currentComment) {
    	console.log(commentId, );
		 // 기존 댓글 내용 숨기기
        document.getElementById(`content-`).style.display = 'none';
        // 수정 폼 보이기
        const editForm = document.getElementById(`edit-form-`);
        editForm.style.display = 'block';
        // 수정할 댓글 내용 미리 넣어주기
        document.getElementById(`edit-text-`).value = currentComment;
    }

    // 댓글 수정 완료 처리 (AJAX로 서버에 전달)
    function submitEdit(commentId) {
        const commentText = document.getElementById(`edit-text-`).value;

        // AJAX 요청 보내기 (jQuery의 $.ajax() 사용)
        $.ajax({
            url: "${pageContext.request.contextPath}/recipe/updateRecipeRating",
            type: "POST",
            data: {
                cmt_rcp_rating_id: commentId,
                comments: commentText
            },
            success: function(response) {
                if (response === "success") {
                    // 서버 응답이 "success"라면 댓글 수정
                    document.getElementById(`content`).querySelector('p').textContent = commentText;

                    // 수정 폼 숨기기
                    document.getElementById(`edit-form`).style.display = 'none';
                    // 기존 댓글 내용 보이기
                    document.getElementById(`content`).style.display = 'block';
                } else {
                    alert("수정에 실패했습니다. 다시 시도해주세요.");
                }
            },
            error: function() {
                alert("서버와의 연결에 문제가 발생했습니다. 다시 시도해주세요.");
            }
        });
    }

    // 수정 취소 시 기존 내용 보이기
    function cancelEdit(commentId) {
        // 기존 내용 보이기
        document.getElementById(`content-${commentId}`).style.display = 'block';
        // 수정 폼 숨기기
        document.getElementById(`edit-form-${commentId}`).style.display = 'none';
    }
    // 페이지 로드 시 기존 별점 반영 (수정 모드)
    window.onload = function () {
        let currentRating = document.getElementById("rating").value;
        if (currentRating) {
            setRating(currentRating, true);
        }
    }
</script>

            
            <!-- 레시피QNA -->
            <div class="comment-section">
                <h2 class="recipe-section-title">레시피 관련 질문</h2> 
                <br>             
                <textarea class="form-control" rows="3" placeholder="질문을 남겨주세요..."></textarea>                      
                <button class="w-btn-outline w-btn-red-outline" type="button">질문 작성</button>

                <div class="mt-4">
                    <!-- 댓글 1 -->
                    <div class="comment">
                        <div class="comment-header">
                            <div class="profile-image">
                                <img src="${pageContext.request.contextPath}/resources/images/profile1.png" alt="Profile Image">
                            </div>
                            <div class="comment-details">
                                <span class="username">양초딩</span>
                                <span class="date-time">2025-01-06 10:10:56</span>
                            </div>
                        </div>
                        <div class="comment-content">
                            <p>양념장 더넣어도되나요?</p>
                        </div>
                        <hr class="comment-separator">
                    </div>
            </div>   
        </div>    
</div>

    <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
    <script>
        // Swiper 초기화
        const swiper = new Swiper(".swiper-container", {
            slidesPerView: 3, 
            spaceBetween: 20, 
            navigation: {
                nextEl: ".swiper-button-next", 
                prevEl: ".swiper-button-prev"
            },
            breakpoints: {
                1024: {
                    slidesPerView: 4,
                },
                768: {
                    slidesPerView: 2,
                },
                480: {
                    slidesPerView: 1,
                }
            }
        });
    </script>
</div>
</body>
</html>