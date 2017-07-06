<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/me_detail.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/toast.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/me_detail.js"></script>	
	
<title>个人信息</title>
</head>
<body>
<a href="javascript:history.go(-1);" id="history"><img id="history" src="${pageContext.request.contextPath}/img/Arrow_Left.png" />
			<div class="history">我</div>
		</a>
		<header>个人信息</header>
		<section>
			<div class="avatar">
				<div class="avatar_title">头像</div>
				<div class="avatar_">
					<img class="avatar_" src="${pageContext.request.contextPath}/${me.avatar}" />
				</div>
				<div class="right_arrow">
					<img class="avatar_right_arrow" src="${pageContext.request.contextPath}/img/right_arrow.png" />
				</div>
			</div>
			<div class="nickname">
				<div class="nickname_title">昵称</div>
				<div class="nickname_">
					${me.nickname }
				</div>
				<div class="right_arrow">
					<img class="nickname_right_arrow" src="${pageContext.request.contextPath}/img/right_arrow.png" />
				</div>
			</div>
			<div class="es_account">
				<div class="es_account_title">es账号</div>
				<div class="es_account_">
					${me.account }
				</div>
			</div>
			<div class="gender">
				<div class="gender_title">性别</div>
				<div class="gender_">
					${me.gender }
				</div>
				<div class="right_arrow">
					<img class="gender_right_arrow" src="${pageContext.request.contextPath}/img/right_arrow.png" />
				</div>
			</div>
			<div id="showMessage" class="showMessage" ></div>
			<div class="edit_pop"></div>
		</section>
		<input id="basePath" type="hidden"
		value="${pageContext.request.contextPath}" />
	<input id="nickname" type="hidden"
		value="${me.nickname}" />
</body>
</html>