<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://localhost:8080/3-28/util" prefix="util"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看管理员</title>
<script
	src="${pageContext.request.contextPath}/static/js/jquery-1.12.1.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/js/bootstrap.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap.css" />
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<h1>管理员列表</h1>
			</div>
		</div>
		<div class="row-fluid">
			<div class="col-md-1 "></div>
			<div class="col-md-10 ">
				<table class="table table-striped">
					<tr>
						<td>id</td>
						<td>username</td>
						<td>password</td>
						<td>salt</td>
						<td align="center" colspan="2">操作</td>
					</tr>
					<!-- forEach遍历出adminBeans -->
					<c:forEach items="${adminBeans }" var="item" varStatus="status">
						<tr>
							<td>${item.id }</td>
							<td><a>${item.username }</a></td>
							<td>${item.password }</td>
							<td>${item.salt }</td>
							<td><a href="${pageContext.request.contextPath}/AdminServlet?method=toUpdate&id=${item.id }">修改</a></td>
							<td><a href="${pageContext.request.contextPath}/AdminServlet?method=delete&id=${item.id }">删除</a></td>
						</tr>
					</c:forEach>
				</table>
				<c:if test="${param.status.equals('2')}">
					<div class="alert alert-success" role="alert">修改成功</div>
				</c:if>
				<c:if test="${param.status.equals('3')}">
					<div class="alert alert-success" role="alert">删除成功</div>
				</c:if>
				<c:if test="${param.status.equals('1')}">
					<div class="alert alert-info" role="alert">没有权限操作超级管理员</div>
				</c:if>
			</div>
			<div class=" col-md-1"></div>
		</div>
		<div class="row-fluid">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<div>
					<util:page pagingBean = "${pagingBean }"/>
				</div>
			</div>
			<div class="col-md-3"></div>
		</div>
	</div>
</body>
</html>