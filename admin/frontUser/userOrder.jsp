<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户订单</title>
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
			<div class="col-md-4">
				<h1>用户订单</h1>
			</div>

		</div>
		<div class="row-fluid">
			<c:if test="${UserOrderBeans!=null}">
				<div class="col-md-10 col-md-offset-1">
					<table class="table">
						<tr>
							<td>id</td>
							<td>订单编号</td>
							<td>原价</td>
							<td>现价</td>
							<td>地址</td>
							<td>收货人</td>
							<td>支付方式</td>
							<td>支付状态</td>
							<td>购买时间</td>
							<td>操作</td>
						</tr>
						<c:forEach items="${UserOrderBeans}" var="item">
							<tr>
								<td>${item.id }</td>
								
								<td><a
								href="${pageContext.request.contextPath}/frontUser/userOrderServlet?method=detailUserlist&code=${item.code }">${item.code }</a></td>
								<td>${item.originalPrice }</td>
								<td>${item.price }</td>
								
								<td>${item.addressBean.province }&nbsp;${item.addressBean.city }&nbsp;${item.addressBean.area }&nbsp;${item.addressBean.address }</td>
								<td>${item.addressBean.name }</td>
								
								<td><c:if test="${item.paymentType==0 }">在线支付</c:if>
									<c:if test="${item.paymentType==1 }">货到付款</c:if></td>
								<td><c:if test="${item.status==0 }">未支付</c:if>
									<c:if test="${item.status==1 }">已支付</c:if>
									<c:if test="${item.status==2 }">未收货</c:if>
									<c:if test="${item.status==3 }">已收货</c:if></td>
								<td>${item.creatDate }</td>
								<td><a
									href="${pageContext.request.contextPath}/frontUser/userOrderServlet?method=detailUserlist1&username=${item.userId}">删除</a></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</c:if>
		</div>
		<div class="row-fluid">
			<div class="col-md-12">
				<c:if test="${param.status.equals('0')}">
					<div class="alert alert-info" role="alert">没有该用户</div>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>