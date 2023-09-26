<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<div id="header">
			<h1>${blogVo.title}</h1>
			<ul>
				<c:choose>
					<c:when test="${sessionScope.authUser != null}">
						<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
						<li><a
							href="${pageContext.request.contextPath}/${sessionScope.authUser.id}/admin/basic">블로그
								관리</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<!-- 여기서 분기처리 postvo가 null이 아닐 때 -->
					<h4>${postInfo.title}</h4>
					<p>
						${postInfo.contents} 
					<p>
				</div>
				<ul class="blog-list">
					<c:if test="${postList != null}">
						<c:forEach items="${postList}" var="post">
							<li><a
								href="${pageContext.request.contextPath}/${sessionScope.authUser.id}/${categoryNo}/${post.no-1}">${post.title}</a></li>
						</c:forEach>
					</c:if>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img
					src="${pageContext.request.contextPath}/assets/images/loopy.jpg">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach items="${categoryList}" var="category">
					<li><a
						href="${pageContext.request.contextPath}/${sessionScope.authUser.id}/${category.no}">${category.name}</a></li>
				</c:forEach>
			</ul>
		</div>

		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>