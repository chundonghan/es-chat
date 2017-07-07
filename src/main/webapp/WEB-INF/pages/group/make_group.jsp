<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>创建盟军</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/make_group.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/toast.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/make_group.js"></script>	
</head>
<body>

	<div class="headline">
		<a href="javascript:history.go(-1);" id="cancel">取消</a>
		<div class="select_title">选择联系人</div>
		<a href="javascript:void(0)" id="group_save">确定</a>
	</div>
	<section>
		<div class="search_box">
			<div class="selected_avatar"></div>
			<div class="search_contact">
				<input class="search_contact" type="text" value="" placeholder="搜索" />
			</div>
		</div>
	</section>
	<div id="showMessage" class="showMessage" ></div>
	<input id="basePath" type="hidden"
		value="${pageContext.request.contextPath}" />
	<input id="token" type="hidden" value="${token}" />
</body>
</html>