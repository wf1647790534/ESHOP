<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>地址管理</title>
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
<script>
  $(function(){
	$("#close1").click(function(){
		$("#types").empty();
		$("#form")[0].reset();
	});
	
	$("#close2").click(function(){
		$("#types").empty();
		$("#form")[0].reset();
	}); 
	
    $("#addAddress").click(function(){
      $("#mymodal").modal("toggle");
      $
		.post(
					"/Eshop/front/user/AddressServlet",
					{
						method : "toadd",
						type : "province",
					},
					function(data) {
						if (data != null && data.length > 0) {
							var content = "<div class='col-sm-2' ><select style= 'width:100px' name='provinces' class='form-control' onchange='city(this)' id='type0'><option value='-1'>-- 省 --</option>";
							for ( var type in data) {
								content += "<option value='"+data[type].id+"'>"
										+ data[type].name + "</option>";
							}
							content += "</select></div>";
							$("#types").append(content);
						}
					}, "json");
    });
  });
  function city(obj) {
		$(obj).parent().nextAll().remove();
		$("#city").val("");
		$("#area").val("");
			//ajax请求
			var id = obj.value;
			$("#province").val(id);
			$
				.post(
							"/Eshop/front/user/AddressServlet",
							{
								method : "toadd",
								type : "city",
								id : id
							},
							function(data) {
								if (data != null && data.length > 0) {
									var content = "<div class='col-sm-2' ><select style= 'width:100px' name='citys'class='form-control' onchange='area(this)' id='type0'><option value='-1'>-- 市 --</option>";
									for ( var type in data) {
										content += "<option value='"+data[type].id+"'>"
												+ data[type].name + "</option>";
									}
									content += "</select></div>";
									$("#types").append(content);
								}
							}, "json");
	}

	function area(obj) {
		$(obj).parent().nextAll().remove();
		$("#area").val("");
			//ajax请求
			var id = obj.value;
			$("#city").val(id);
			$
				.post(
							"/Eshop/front/user/AddressServlet",
							{
								method : "toadd",
								type : "area",
								id : id
							},
							function(data) {
								if (data != null && data.length > 0) {
									var content = "<div class='col-sm-2' ><select style= 'width:100px' name='areas'class='form-control' onchange='setarea(this)' id='type0'><option value='-1'>-- 县 --</option>";
									for ( var type in data) {
										content += "<option value='"+data[type].id+"'>"
												+ data[type].name + "</option>";
									}
									content += "</select></div>";
									$("#types").append(content);
								}
							}, "json");
	}
	function setarea(obj) {
		var id = obj.value;
		$("#area").val(id);
	}
</script>
<body>
<%@include file="../top.jsp"%>
<util:type path="${pageContext.request.contextPath}" />
	<div class="container">
	<button type="button" class="btn btn-default" id="addAddress">添加新地址</button>
	<c:if test="${param.status.equals('0')}">
		<div class="alert alert-danger"role="alert">请填写完整信息</div>
	</c:if>
	<div class="modal" id="mymodal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" id="close1"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
				<h4 class="modal-title">添加地址</h4>
			</div>
			<div class="modal-body">
					<div class="form-group col-md-12" id="types">
				
		
					</div>
			<br><br>
				<form action="${pageContext.request.contextPath}/front/user/AddressServlet?method=add" method="post" id="form">
				<input name="province" id="province" type="hidden"  />
				<input name="city" id="city" type="hidden"  />
				<input name="area" id="area" type="hidden"  />
				详细地址:<input class="form-control" style= "width:400px" id="address" name="address" type="text" placeholder="详细地址" />
				收货人姓名:<input class="form-control" style= "width:400px" name="name" type="text" placeholder="收货人姓名" />
				联系电话:<input class="form-control" style= "width:400px" name="cellphone" type="text" placeholder="联系电话" />
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" id="close2">关闭</button>
					<button type="submit" class="btn btn-primary">保存</button>
				</div>
				</form>
			</div>
			
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

		<c:forEach items="${addressBeans}" var="item" varStatus="status">
		<table class="table table-hover ">
			<tr>
				<td>收货人</td>
				<td>${item.name}</td>
			</tr>
			<tr>
				<td>所在地区</td>
				<td>${item.province}${item.city}${item.area}</td>
			</tr>
			<tr>
				<td>地址</td>
				<td>${item.address}</td>
			</tr>
			<tr>
				<td>手机</td>
				<td>${item.cellphone}</td>
			</tr>
			<tr>
				<td></td>
				<td>
				<a href="${pageContext.request.contextPath}/front/user/AddressServlet?method=delete&id=${item.id}" >
				<button type="button" class="btn btn-default" >删除</button>
				</a>
				
				<c:if test="${item.status==0 }">
					<a href="${pageContext.request.contextPath}/front/user/AddressServlet?method=setdefault&id=${item.id}" >
					<button type="button" class="btn btn-default" >设为默认</button>
					</a>	
				</c:if>
				</td>
			</tr>
			
			
			</table>
			<br>
		</c:forEach>
		
	</div>
</body>
</html>