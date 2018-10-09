<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script src="${pageContext.request.contextPath}/static/js/jquery-1.12.1.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css">
</head>

<body>
	<div class="navbar navbar-defult" role="navigation">
		<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
			<a class="navbar-brand" target="_parent" href="${pageContext.request.contextPath}/front/productShow/productShowServlet?method=sort&id=0">EShop</a>
		    </div>
		    <!-- Collect the nav links, forms, and other content for toggling -->
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<form target="_top"  class="navbar-form navbar-left"
				action="${pageContext.request.contextPath}/front/productShow/productShowServlet?method=search"
				method="post" role="search">
				<div class="form-group">
					<input type="text" name="key" class="form-control"
						placeholder="Search">
				</div>
				<button type="submit" class="btn btn-default">搜索</button>
			</form>
		    	<ul class="nav navbar-nav navbar-right">
		    		<li><a target="_parent" href="${pageContext.request.contextPath}/front/shopping/shoppingServlet?method=toCart">我的购物车</a></li>
		    	</ul>
		    	<ul class="nav navbar-nav navbar-right">
		    		<li><a target="_parent" href="${pageContext.request.contextPath}/front/user/login.jsp">退出登录</a></li>
		    	</ul>
		    	<ul class="nav navbar-nav navbar-right">
		    		<li><a target="_parent" href="${pageContext.request.contextPath}/front/shopping/order.jsp">订单中心</a></li>
		    	</ul>
		    	<ul class="nav navbar-nav navbar-right">
		    		<li><a target="_parent" href="${pageContext.request.contextPath}/front/user/userInfo.jsp">用户：${userBean.nickname}</a></li>
		    	</ul>
		    	<ul class="nav navbar-nav navbar-right">
		    		<li><a target="_parent" href="${pageContext.request.contextPath}/front/productShow/productShowServlet?method=sort&id=0">首页</a></li>
		    	</ul>
		    </div>
		</div>
	</div>
</body>
</html>