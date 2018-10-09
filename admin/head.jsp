<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<script src="${pageContext.request.contextPath }/static/bootstrap-3.3.5-dist/js/bootstrap.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap.css" />
<div class='navbar navbar-default' style='padding:20px 0 10px; margin:0'>
<div class='container-fluid'>
	<div class='navbar-header'>
<!-- 		这个button的用法是设么意思，编译器提示没有的属性有是怎么回事？ -->
		<button type="button" class='navbar-toggle collapsed'
			data-toggle='collapse' data-target="#bs-example-navbar-collapse-1"
			aria-expanded="false">
			<span class='sr-only'>Toggle navigation</span>
			<span class='icon-bar'></span>
			<span class='icon-bar'></span>
			<span class='icon-bar'></span>
		</button>
		<a class='navbar-brand' href='#'>ec-web综合管理平台</a>
	</div>
	
	<div class='colapse navbar-collapse' id='bs-example-navbar-collapse-1'>
		<ul class="nav navbar-nav navbar-right">
			<li><a target='_parent' href='${pageContext.request.contextPath }/admin/main.jsp'>首页</a>
			<li role='separator' class="divider"></li>
			<li><a target="_parent" href="${pageContext.request.contextPath }/AdminServlet?method=end&status=1">退出</a>
		</ul>
	</div>
</div>
</div>