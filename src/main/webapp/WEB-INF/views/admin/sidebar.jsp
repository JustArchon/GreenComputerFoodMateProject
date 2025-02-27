<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" />

<meta charset="UTF-8">
<title>상단부</title>
<style>
.sidebar {
            width: 250px;
            background-color: #2c3e50;
            color: #fff;
            padding: 20px;
            height: 100vh; /* 화면 전체 높이 */
            position: fixed;
            top: 0;
            left: 0;
            box-sizing: border-box;
        }

        .sidebar h2 {
            text-align: center;
            font-size: 24px;
            margin-bottom: 30px;
        }
                .admin-area {
            margin-bottom: 30px;
        }

        .admin-area h5 {
            font-size: 18px;
            margin-bottom: 10px;
            text-transform: uppercase;
        }

        .admin-area ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .admin-area ul li {
            margin-bottom: 10px;
        }

        .admin-area ul li a {
            text-decoration: none;
            color: #fff;
            font-size: 16px;
            display: block;
            padding: 8px;
            background-color: #34495e;
            border-radius: 4px;
            transition: background-color 0.3s;
        }

        .admin-area ul li a:hover {
            background-color: #16a085;
        }
</style>
</head>
<!-- sidebar.jsp -->
<!-- 사이드바 -->
        <div class="sidebar">
            <h2>관리자 메뉴</h2>
            <div class="admin-area">
                <h5><strong>계정 관리</strong></h5>
                <ul>
                    <li><a href="${contextPath}/admin/adminUsers">사용자 관리</a></li>
                    <li><a href="#">탈퇴 신청 사용자</a></li>
                    <li><a href="#">휴면 상태 사용자</a></li>
                    <li><a href="${contextPath}/admin/adminSellers">판매자 관리</a></li>
                </ul>
            </div>

            <div class="admin-area">
                <h5><strong>신고/ 문의 관리</strong></h5>
                <ul>
                    <li><a href="${contextPath}/admin/adminUsersInquiry">사용자 신고 내역</a></li>
                    <li><a href="${contextPath}/admin/adminSellersInquiry">판매자 문의 내역</a></li>
                    <li><a href="#">1:1 문의</a></li>
                </ul>
            </div>

            <div class="admin-area">
                <h5><strong>레시피/상품 관리</strong></h5>
                <ul>
                    <li><a href="${contextPath}/admin/adminRecipe">등록된 레시피</a></li>
                    <li><a href="${contextPath}/admin/adminIngredients">등록된 상품</a></li>
                </ul>
            </div>

            <div class="admin-area">
                <h5><strong>결제 내역 관리</strong></h5>
                <ul>
                    <li><a href="${contextPath}/admin/adminPayments">전체 결제 내역</a></li>
                    <li><a href="#">결제 금액 관리</a></li>
                    <li><a href="#">환불 관리</a></li>
                </ul>
            </div>
			
			<div class="admin-area">
                <h5><strong>통계 관리</strong></h5>
                <ul>
                    <li><a href="${contextPath}/admin/adminStatistics">일반 통계</a></li>
                    <li><a href="${contextPath}/admin/adminStatistics">사용자 통계</a></li>
                    <li><a href="${contextPath}/admin/adminStatistics">매출 통계</a></li>
                </ul>
            </div>
			
            <div class="admin-area">
                <h5><strong>관리자 관리</strong></h5>
                <ul>
                    <li><a href="${contextPath}/admin/adminAdminControll">전체 관리자</a></li>
                    <li><a href="${contextPath}/admin/adminSignUp">관리자 회원가입</a></li>
                </ul>
            </div>
        </div>

</html>