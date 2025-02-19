<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
</head>
<style>
body {
	margin: 0 auto;
}

.header, .footer {
	background-color: #ffffff;
	color: white;
	text-align: center;
	padding: 10px;
	flex-shrink: 0; /* 푸터 크기가 줄어들지 않음 */
}
</style>

<header>
	
	<c:choose>
	<c:when test="${smallHeader}">
	<%@ include file="header_small.jsp" %>
	</c:when>
	<c:otherwise>
	<%@ include file="header.jsp" %>
	</c:otherwise>
	</c:choose>
            
</header>

<body>

<nav>
	<c:if test="${showNavbar}">
		<%@ include file="navbar.jsp" %>
	</c:if>
</nav>
	
<aside>
	<c:if test="${showSidebar}">
          	<!-- Sidebar -->
           <jsp:include page="${sidebar}" />
          </c:if>
</aside>

<main>
	<jsp:include page="${body}" />
</main>

<footer>
	<c:choose>
		<c:when test="${smallFooter }">
			<%@ include file="footer_small.jsp" %>
		</c:when>
		<c:otherwise>
			<%@ include file="footer.jsp" %>
		</c:otherwise>
	</c:choose>
</footer>

</body>
</html>