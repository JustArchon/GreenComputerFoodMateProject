<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>배송지 관리</title>
<link href="<c:url value="/resources/css/tablepage.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/modal.css" />" rel="stylesheet">
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

			table.table2 {
			    border-collapse: collapse;
			    width: 100%;
			    text-align: left;
			    line-height: 1.5;
			    margin: 20px 0;
			    border-top: 2px solid #333;
			    border-bottom: 2px solid #ccc;
			}
			
			table.table2 tr {
			    border-bottom: 1px solid #ccc;
			}
			
			table.table2 td {
			    padding: 10px;
			    vertical-align: middle;
			    font-size: 14px;
			}
</style>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
	document.addEventListener("DOMContentLoaded", () => {
	    const modal = document.querySelector('.modal');
	    const btnOpenModal = document.querySelector('.btn-open-modal');
	    const btnCloseModal = document.querySelector('.btn-close-modal');
	
	    btnOpenModal.addEventListener("click", () => {
	        modal.style.display = "flex";
	    });
	    
	    btnCloseModal.addEventListener("click", () => {
	        modal.style.display = "none";
	    });
	});
	
	function execDaumPostcode() {
		  new daum.Postcode({
		    oncomplete: function(data) {
		      // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	
		      // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
		      // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
		      var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
		      var extraRoadAddr = ''; // 도로명 조합형 주소 변수
	
		      // 법정동명이 있을 경우 추가한다. (법정리는 제외)
		      // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
		      if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
		        extraRoadAddr += data.bname;
		      }
		      // 건물명이 있고, 공동주택일 경우 추가한다.
		      if(data.buildingName !== '' && data.apartment === 'Y'){
		        extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
		      }
		      // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
		      if(extraRoadAddr !== ''){
		        extraRoadAddr = ' (' + extraRoadAddr + ')';
		      }
		      // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
		      if(fullRoadAddr !== ''){
		        fullRoadAddr += extraRoadAddr;
		      }
	
		      // 우편번호와 주소 정보를 해당 필드에 넣는다.
		      document.getElementById('postal_Code').value = data.zonecode; //5자리 새우편번호 사용
		      document.getElementById('addr').value = fullRoadAddr;
		     
		    }
		  }).open();
		}
	$(document).ready(function() {
		var contextPath = "${pageContext.request.contextPath}";
		let isBaseAddrCheckbox = document.getElementById("is_Base_Addr");

	    // 체크박스 변경 감지 (디버깅용)
	    isBaseAddrCheckbox.addEventListener("change", function() {
	        console.log("체크박스 상태 변경됨:", this.checked ? "Y" : "N");
	    });
	    
	    $(".edit-btn").click(function() {
	        let btn = $(this); // 현재 클릭된 버튼
	        
	        let modal = document.getElementById("deliveryModal");
	        document.getElementById("modalTitle").textContent = "배송지 수정";

	        // 버튼의 data-* 속성에서 값 가져오기
	        let addrId = btn.data("id");
	        let nickname = btn.data("nickname");
	        let name = btn.data("name");
	        let postalCode = btn.data("postal");
	        let addr = btn.data("addr");
	        let detail = btn.data("detail");
	        let phone = btn.data("phone");
	        let isBaseAddr = btn.data("base");

	        // 모달 폼에 데이터 채우기
	        $("input[name='addr_Nickname']").val(nickname);
	        $("input[name='to_Name']").val(name);
	        $("input[name='postal_Code']").val(postalCode);
	        $("input[name='addr']").val(addr);
	        $("input[name='addr_Detail']").val(detail);
	        $("input[name='to_Phone_Num']").val(phone);

	        // 기본 배송지 설정 체크박스 처리
	        $("#is_Base_Addr").prop("checked", isBaseAddr === "Y");
	        
	     // 기존 hidden input이 있으면 삭제 후 추가
	        $("input[name='addr_id']").remove();
	        $("form[name='deliveryAdd']").append('<input type="hidden" name="addr_id" value="' + addrId + '">');

	        // 기존 action 변경 (업데이트 API로 변경)
	        $("form[name='deliveryAdd']").attr("action", contextPath + "/mypage/deliveryUpdate");

	        // 모달 띄우기
	        modal.style.display = "flex";
	    });
	    
	    $(".delete-btn").click(function() {
	        let addrid = $(this).data("id"); // data-id 속성에서 addr_id 값 가져오기

	        if (!confirm("정말 삭제하시겠습니까?")) {
	            return; // 취소하면 삭제 요청 중단
	        }

	        $.ajax({
	            url: contextPath + "/mypage/deliverydelete", // 서버 삭제 API
	            type: "POST",
	            data: { addr_id: addrid }, // 서버에 addr_id 전송
	            success: function(response) {
	                alert("삭제가 완료되었습니다.");
	                location.reload(); // 새로고침
	            },
	            error: function() {
	                alert("삭제에 실패했습니다.");
	            }
	        });
	    });
		
	    $("#deliveryAdd").submit(function(event) {
	        event.preventDefault(); // 기본 제출 방지

	        let formData = new FormData(this); // 폼 데이터 생성
	        let isBaseAddrCheckbox = document.getElementById("is_Base_Addr");

	        // 체크박스 상태 반영 (체크되면 "Y", 해제되면 "N")
	        formData.set("is_Base_Addr", isBaseAddrCheckbox.checked ? "Y" : "N");
	        console.log("폼 데이터 전송:", formData.get("is_Base_Addr")); // 확인용

	        $.ajax({
	            url: contextPath + "/mypage/deliveryAdd",
	            type: "POST",
	            data: formData, // FormData 객체 사용
	            processData: false, // 필수 (폼 데이터 자동 처리 방지)
	            contentType: false, // 필수 (기본 Content-Type 설정 방지)
	            success: function(response) {
	                alert("배송지가 성공적으로 추가되었습니다!");
	                $("#deliveryModal").modal("hide"); // 모달 닫기
	                location.reload(); // 필요시 새로고침
	            },
	            error: function() {
	                alert("배송지 추가 실패!");
	            }
	        });
	    });
	});

	
</script>
</head>
<%-- 현재 페이지 정보 가져오기 (기본값: 1페이지) --%>
<c:set var="currentPage" value="${param.page != null ? param.page : 1}" />
<c:set var="itemsPerPage" value="6" />
<c:set var="startIndex" value="${(currentPage - 1) * itemsPerPage}" />
<c:set var="endIndex" value="${currentPage * itemsPerPage}" />

<%-- 전체 데이터 개수 구하기 --%>
<c:set var="totalItems" value="${fn:length(deliveryList)}" />
<c:set var="totalPages" value="${(totalItems + itemsPerPage - 1) / itemsPerPage}" />
<body>
<div class="container mt-1">
	<div class="d-flex justify-content-between align-items-center border-bottom pb-2 mb-3">
      		<h5 class="mb-0 fw-bold">배송지 관리</h5>
    </div>
       <div class="text-end mb-1">
	  <a href="#" class="btn btn-dark btn-open-modal">+ 새 배송지 추가</a>
	</div>
    	<table class="table table-hover table-custom">
		<thead class="table-header table-secondary">
			<tr>
				<td>배송지이름</td>
				<td>받으실 분</td>
				<td>주소</td>
				<td>연락처</td>
				<td>수정/삭제</td>
			</tr>
		</thead>
      	<tbody>
      	<c:forEach var="delivery" items="${deliveryList}" varStatus="status">
      		<c:if test="${status.index >= startIndex && status.index < endIndex}">
		      	<tr>
		      	  <td>${delivery.is_Base_Addr == 'Y' ? '(기본배송지)<br>' : ''}
		      	  ${delivery.addr_Nickname}</td>
		          <td>${delivery.to_Name}</td>
		          <td>(${delivery.postal_Code})<br>
		          ${delivery.addr} ${delivery.addr_Detail}
		          </td>
		          <td>전화번호 : ${delivery.to_Phone_Num}
		          </td>
		          <td>
		          <div class="d-flex flex-column gap-1">
				    	<button class="btn btn-outline-secondary btn-sm edit-btn" 
					    	data-id="${delivery.addr_id}"
			                data-nickname="${delivery.addr_Nickname}"
			                data-name="${delivery.to_Name}"
			                data-postal="${delivery.postal_Code}"
			                data-addr="${delivery.addr}"
			                data-detail="${delivery.addr_Detail}"
			                data-phone="${delivery.to_Phone_Num}"
			                data-base="${delivery.is_Base_Addr}">
			                수정
			            </button>
				    	<button class="btn btn-outline-secondary btn-sm delete-btn" data-id="${delivery.addr_id}">삭제</button>
				  </div>
		          </td>
		        </tr>
      		</c:if>
        </c:forEach>
		</tbody>
	</table>
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
	<div class="modal" id="deliveryModal">
		<div class="modal_body">
			<div class="container mt-1">
				<div class="d-flex justify-content-between align-items-center border-bottom pb-2 mb-3">
			      		<h5 class="mb-0 fw-bold">나의 배송지 관리</h5>
			    </div>
			  	<p class="mb-3" id="modalTitle">배송지 추가</p>
			  	<form name="deliveryAdd" method="post" action="${contextPath}/mypage/deliveryAdd">
			  	<table class="table2">
					<tr>
				        <td>배송지 이름</td>
				        <td>
				        <input type="text" class="form-control" name="addr_Nickname" size="20" required>
				        </td>
				    </tr>
				    <tr>
				        <td>받으실분</td>
				        <td><input type="text" class="form-control" name="to_Name" size="40" required></td>
				    </tr>
				    <tr>
				        <td>받으실 곳</td>
				        <td>
							<div class="col-sm-6 mb-1">
				                   <div class="d-flex align-items-center">
				                        <input name="postal_Code" id="postal_Code" type="text" class="form-control me-2" required>
				                        <p class="mb-0"><a href="javascript:execDaumPostcode()">우편번호 찾기</a></p>
				                    	</div>
				                    </div>
				                    <div class="col-sm-9 mb-1">
				                        <input name="addr" id="addr" type="text" class="form-control" required>
				                    </div>
				                    <div class="col-sm-9 mb-1">
				                        <input name="addr_Detail" id="addr_Detail" type="text" class="form-control">
				                    </div>
				        </td>
				    </tr>
				    <tr>
				        <td>전화번호</td>
				        <td><input type="text" class="form-control" name="to_Phone_Num" size="40" required></td>
				    </tr>
				</table>
				<div class="d-flex justify-content-center align-items-center form-check mb-1">
				  <input type="checkbox" class="form-check-input me-2 mb-1" value="Y" name="is_Base_Addr" id="is_Base_Addr" ${delivery.is_Base_Addr == 'Y' ? 'checked' : ''}>
				  <label class="form-check-label" for="flexCheckDefault">
				    기본 배송지로 설정
				  </label>
				</div>
			  	<div class="d-flex justify-content-center gap-2">
			  		<button type="submit" class="btn btn-success">저장</button>
	                <button type="reset" class="btn btn-outline-secondary btn-close-modal">취소</button>
	            </div>
	           	</form>
			</div>
		</div>
	</div>
</div>
</body>
</html>