<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${title}</title>
<style>
		html, body {
		    height: 100%;
		    margin: 0;
		    display: flex;
		    flex-direction: column;
		}
		
		/* 전체 컨테이너 */
        .container-fluid {
            display: flex;
            flex-direction: column;
            height: 100%;
        }
		
        .sidebar {
            background-color: #f8f9fa;
            width: 250px;
            height: 100vh; /* 화면 전체 높이 */
            padding: 20px;
            position: fixed;
            top: 60px; /* 헤더 밑에 배치 */
            left: 0;
            z-index: 100;
            overflow-y: auto; /* 사이드바 내부에 스크롤 추가 */
		    scrollbar-width: none; /* Firefox에서 스크롤바 숨기기 */
		    -ms-overflow-style: none; /* IE 및 Edge에서 스크롤바 숨기기 */
        }
        
        /* 크롬, 사파리에서 스크롤바 숨기기 */
		.sidebar::-webkit-scrollbar {
			 display: none;
		}

        .content {
		    flex: 1; /* 남은 공간을 차지 */
		    display: flex;
		    flex-direction: column; /* 요소들을 세로로 정렬 */
		    align-items: center; /* 가로 중앙 정렬 */
		    justify-content: center; /* 세로 중앙 정렬 (필요할 경우) */
		    text-align: center; /* 텍스트 중앙 정렬 */
		    padding: 20px;
		}

        
        .container-fluid {
		    flex: 1;
		}
		
        .header, .footer {
        	width: 100%;
            background-color: #ffffff;
            color: white;
            text-align: center;
            padding: 10px;
            flex-shrink: 0; /* 푸터 크기가 줄어들지 않음 */
            width: calc(100% - 250px); /* 사이드바만큼 너비 빼기 */
        }
        
        .header {
            top: 0;
            left: 250px; /* 사이드바 만큼 왼쪽으로 밀기 */
        }

        .footer {
            bottom: 0;
            left: 250px; /* 사이드바 만큼 왼쪽으로 밀기 */
        }
    </style>
</head>
<header>
<!-- Header -->
        <div class="row">
            <div class="col-12 header">
            <c:choose>
	            <c:when test="${smallHeader}">
	            	<%@ include file="header_small.jsp" %>
	            </c:when>
	            <c:otherwise>
                	<%@ include file="adminheader.jsp" %>
                </c:otherwise>
            </c:choose>
            </div>
        </div>
        <c:if test="${showHeadermenu}">
	        <div class="row">
	           <%@ include file="adminheadermenu.jsp" %>
	        </div>
	    </c:if>
</header>
<body>
    <div class="container-fluid">
        
        <div class="row">
            <c:if test="${showSidebar}">
            	<!-- Sidebar -->
	            <div class="col-md-2 sidebar">
	            	<jsp:include page="${sidebar}" />
	            </div>
            </c:if>
            <!-- Main Content -->
            <c:choose>
            	<c:when test="${showSidebar}">
            		<div class="col-md-12 content">
            	</c:when>
            	<c:otherwise>
            		<div class="col-md-12 content">
            	</c:otherwise>
            </c:choose>
                <jsp:include page="${body}" />
            </div>
        </div>
    </div>
</body>
<footer>
        <!-- Footer -->
        <div class="row">
	    	<div class="col-12 footer">
	        <c:choose>
        		<c:when test="${smallFooter }">
	                <%@ include file="footer_small.jsp" %>
	            </c:when>
	            <c:otherwise>
	            	<%@ include file="footer.jsp" %>
	            </c:otherwise>
        	</c:choose>
	        </div>
        </div>
</footer>
</html>