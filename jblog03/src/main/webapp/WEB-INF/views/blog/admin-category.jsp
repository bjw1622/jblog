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
			<a
				href="${pageContext.request.contextPath}/${sessionScope.authUser.id}">
				<h1>백재원 이올시다의 블로그에 오신걸 콩그레츄레이션</h1>
			</a>
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
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a
						href="${pageContext.request.contextPath}/${sessionScope.authUser.id}/admin/basic">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a
						href="${pageContext.request.contextPath}/${sessionScope.authUser.id}/admin/write">글작성</a></li>
				</ul>
				<table class="admin-cat">
					<tr>
						<th>번호</th>
						<th>카테고리명</th>
						<th>포스트 수</th>
						<th>설명</th>
						<th>삭제</th>
					</tr>
					<c:forEach items="${categoryList}" var="category"
						varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td>${category.name}</td>
							<td>${category.totalPostCount}</td>
							<td>${category.description}</td>
							<td>
								<form
									action="${pageContext.request.contextPath}/${sessionScope.authUser.id}/admin/category/${category.no}"
									method="post">
									<button type="submit">
										<img
											src="${pageContext.request.contextPath}/assets/images/delete.jpg">
									</button>
								</form>
							</td>
						</tr>
					</c:forEach>

				</table>

				<form
					action="${pageContext.request.contextPath}/${sessionScope.authUser.id}/admin/category"
					method="post">
					<h4 class="n-c">새로운 카테고리 추가</h4>
					<table id="admin-cat-add">
						<tr>
							<td class="t">카테고리명</td>
							<td><input type="text" name="name"></td>
						</tr>
						<tr>
							<td class="t">설명</td>
							<td><input type="text" name="description"></td>
						</tr>
						<tr>
							<td class="s">&nbsp;</td>
							<td><input type="submit" value="카테고리 추가"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>