<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户详情</title>
<script
	src="${pageContext.request.contextPath}/static/js/jquery-1.12.1.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css">
<script
	src="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
<style type="text/css">
</style>
</head>
<body>
	<div class="container">
		<h1>用户详情</h1>
		
		<table class="table table-striped">
			<tr>
				<td>id</td>
				<td>${userbean.id}</td>
			</tr>
			<tr>
				<td>账号</td>
				<td>${userbean.username}</td>
			</tr>
			<tr>
				<td>头像</td>
				<td><img alt=" 图片 " src="${userbean.pic }" width="50px"
					height="50px" class="img-circle"></td>
			</tr>
			<tr>
				<td>昵称</td>
				<td>${userbean.nickname}</td>
			</tr>
			<tr>
				<td>真实姓名</td>
				<td>${userbean.truename}</td>
			</tr>
			<tr>
				<td>性别</td>
				<td>${userbean.sex}</td>
			</tr>
			<tr>
				<td>冻结状态</td>
				<td>
<c:if test="${userbean.status==1 }">活跃</c:if>
<c:if test="${userbean.status==0 }">冻结</c:if>
</td>
			</tr>
			<tr>
				<td>创建时间</td>
				<td>${userbean.createDate}</td>
			</tr>
		</table>

	</div>
</body>
</html>