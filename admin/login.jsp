<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员登录</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/bootstrap-3.3.5-dist/css/bootstrap.css" media="all"/>
<style>
	.code{
		background-image:url();
		font-family:Arial;
		font-style:italic;
		color:Red;
		border:0;
		padding:2px 3px;
		letter-apacing:3px;
		font-weight:bolder;
		
	}
	.unchanged{
		border:0;
	}
</style>

<script type="text/javascript">
	var code;
	//	生成验证码
	function createCode(){
		code = "";
		var codeLength = 6;//验证码的长度
		var checkCode = document.getElementById("checkCode");
		var selectChar = new Array(
				0,1,2,3,4,5,6,7,8,9,
				'A','B','C','D','E','F','G','H','I','J','K','L',
				'M','N','O','P','Q','R','S','T','U','V','W','X',
				'Y','Z'
				);
		
		for(var i = 0; i < codeLength; i++){
			var charIndex = Math.floor(Math.random()*36);
			code += selectChar[charIndex];
		}
		
		if(checkCode){
			
			checkCode.className = "code";
			checkCode.value = code;
		}
	}
</script>
</head>
<body onload = 'createCode()'>
	<div class='row-fluid' style='margin-top:200px'>
		<div class='col-md-4'></div>
		<div class='col-md-4'>
			<form role="form" class='form-horizontal' 
				method='post' id  = 'checkForm'
				action='${pageContext.request.contextPath }/AdminServlet?method=login'>
				<div class='form-group'>
					<label class='col-md-3 control-label' for='username'>用户名</label>
					<div class='col-md-9'>
						<input class='form-control'name='username'type='text'id='username'placeholder='username'value=''autofocus="autofocus">
					</div>
				</div>
				<div class='form-group'>
					<label class='col-md-3 control-label' for='inputPassword'>密码</label>
					<div class='col-md-9'>
						<input class='form-control'name='password'type='password'id='inputPassword'placeholder='password'value=''>
					</div>
				</div>
				<div class='form-group'>
					<label class='col-md-3 control-label' for='inputPassword'>验证码</label>
					<div class='col-md-4'>
						<input class='form-control'name='codeCheck'type='text'id='codeCheck'placeholder='code'value=''>
					</div>
					<div class='col-md-4'>
						<input type='text' onclick='createCode()'readOnly id='checkCode'class='unchanged'style='width:80px'>
					</div>
					<div class='col-md-1'></div>
				</div>
				<div class='form-group'>
					
				</div>
				<div class='form=group'>
					<div class='col-md-offset-3 col-md-9'>
						<button type='submit' class='btn btn-primary btn-block'>登录</button>
					</div>
				</div>
				<c:if test="${param.status.equals('1') }">
					<div class='alert alert-danger' role='alert'>
						用户名或密码错误！
					</div>
				</c:if>
			</form>
			<div class='col-md-4'></div>
		</div>
	</div>
	<script type="text/javascript" src='${pageContext.request.contextPath }/static/js/jquery-1.12.1.js'></script>
	<script type="text/javascript" src='${pageContext.request.contextPath }/static/js/jquery-3.3.5-dist/js/bootstrap.js'></script>
	<script type="text/javascript" src='${pageContext.request.contextPath }/static/js/jquery.validate.js'></script>
	<script type="text/javascript" src='${pageContext.request.contextPath }/static/js/myValidate.js'></script>
</body>
</html>