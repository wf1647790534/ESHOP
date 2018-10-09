<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://localhost:8080/3-28/util" prefix="util"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人信息</title>
<script
	src="${pageContext.request.contextPath}/static/js/jquery-1.12.1.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css">
<script
	src="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>

<script
	src="${pageContext.request.contextPath}/static/js/jquery.validate.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/static/js/myValidate.js"
	type="text/javascript"></script>



<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap.css" />

<style>
body {
	position: relative;
}

.info {
	font-size: 20px;
	margin-top: 50px;
	background-color: #DDDDDD;
}
</style>
</head>
<body>


	<dl class="info col-md-6 col-md-offset-2 dl-horizontal">
		<!-- dl-horizontal”给定义列表实现水平显示效果 -->
		<div class="col-md-10 ">
			<dt>真实姓名:</dt>
			<dd>${userBean.truename }</dd>
			<dt>性别:</dt>
			<dd>${userBean.sex }</dd>
			<dt>手机号:</dt>
			<dd>${userBean.username }</dd>
			<dt>昵称:</dt>
			<dd>${userBean.nickname }</dd>
			<dt>当前密码:</dt>
			<dd>${userBean.password }</dd>
			<dt></dt>
			<dd></dd>
		</div>
		<div class="col-md-2 ">
			<button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal">修改信息</button>
		</div>
	</dl>
	<div class=" col-md-2 col-md-offset-1">
		<img class="img-circle" alt=" 商品图片未加载 " src="${userBean.pic}"
			width="150px" height="150px">
		<form enctype="multipart/form-data"
			action="${pageContext.request.contextPath }/frontUser/userServlet?method=updatePic&id=${userBean.id}"
			method="post">
			<input type="file"
							class="form-control" name="pic" id="pic" placeholder="pic" />
			<button type="submit" class="btn btn-default">上传</button>
		</form>
	</div>



	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改个人信息</h4>
				</div>
				<form enctype="multipart/form-data"
					action="${pageContext.request.contextPath }/frontUser/userServlet?method=updateInfo&id=${userBean.id}"
					method="post" id="checkForm">
					<div class="modal-body">

						<div class="form-group">
							<label for="account">新手机号：</label> <input type="text"
								class="form-control" name="account" id="account"
								placeholder=" 请输入您的手机号 ">
						</div>
						<div class="form-group">
							<label for="inputPassword">新密码： </label> <input type="password"
								class="form-control" name="password" id="inputPassword"
								placeholder="password">
						</div>
						<div class="form-group">
							<label for="password2">确认密码： </label> <input type="password"
								class="form-control" name="password2" id="password2"
								placeholder="password">
						</div>
						<div class="form-group">
							<label for="nickname">昵称：</label> <input type="text"
								class="form-control" name="nickname" id="nickname"
								placeholder="nickname">
						</div>





					</div>



					<div class="modal-footer">
						<div class="row-fluid">
							<div class="col-md-3"></div>
							<div class="col-md-3">
								<button type="submit" class="btn btn-default">提交更改</button>
							</div>
							<div class="col-md-3">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">关闭</button>
							</div>
						</div>
					</div>

				</form>
			</div>
		</div>
	</div>
	<div class="col-md-12">
		<c:if test="${param.status.equals('1')}">
			<div class="alert alert-success" role="alert">
				<span style="margin-left: 75px;">修改成功！！！</span>
			</div>
		</c:if>
		<c:if test="${param.status.equals('2')}">
			<div class="alert alert-danger" role="alert">手机号已存在，修改失败！！！</div>
		</c:if>
	</div>

</body>
</html>

