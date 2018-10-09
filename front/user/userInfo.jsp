<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人中心</title>
<!-- 导入bootstrap. css-->
	<script src="${pageContext.request.contextPath}/static/js/jquery-1.12.1.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css">
	<script src="${pageContext.request.contextPath}/static/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
	
	
</head>

	<frameset frameborder="1" rows="82px,*">
   		<frame src="../common/head.jsp"/>
   		<frameset cols="285px,*">
   			<frame src="../common/left.jsp"/>
   			<frame name="mainAction"/>
   		</frameset>
   	</frameset>

</html>

