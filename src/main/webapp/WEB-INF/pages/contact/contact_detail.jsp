<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>详细资料</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/contact_detail.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>
</head>
<body>
	<a href="javascript:history.go(-1);" id="history"><img id="history" src="${pageContext.request.contextPath}/img/Arrow_Left.png" />
		<div class="history">通讯录</div>
	</a>
	<header>详细资料</header>
	<section>
		<div class="contact_detail">
			<div class="west">
				<img src="${pageContext.request.contextPath}/img/sonic_generations.png" class="west" />
			</div>
			<div class="east">
				<div class="north">
					${contactDetail.nickname}
				</div>
				<div class="south">
					es账号：${contactDetail.account}
				</div>
			</div>

		</div>
		<div class="tag">
			<div class="tag_content">设置备注和标签</div>
			<img class="right_arrow tag" src="${pageContext.request.contextPath}/img/right_arrow.png" />
		</div>
		<div class="others">
			<div class="location">
				<div class="location_title">
					地区
				</div>
				<div class="location_content">辽宁 沈阳</div>
				<img class="right_arrow location" src="${pageContext.request.contextPath}/img/right_arrow.png" />
			</div>
			<div class="self_photo">
				<div class="self_photo_title">
					个人相册
				</div>
				<div class="photo_detail">
					<img class="photo_detail" src="${pageContext.request.contextPath}/img/sonic_generations.png" />
					<img class="photo_detail" src="${pageContext.request.contextPath}/img/sonic_generations.png" />
					<img class="photo_detail" src="${pageContext.request.contextPath}/img/sonic_generations.png" />
					<img class="photo_detail" src="${pageContext.request.contextPath}/img/sonic_generations.png" />
				</div>
				<img class="right_arrow self_photo" src="${pageContext.request.contextPath}/img/right_arrow.png" />
			</div>
			<div class="more">
				<div class="more_title">
					更多
				</div>
				<img class="right_arrow more" src="${pageContext.request.contextPath}/img/right_arrow.png" />
			</div>
		</div>
		<div class="send_msg">
			<button class="send_msg">发信息</button>
		</div>
	</section>
	<input id="basePath" type="hidden"
		value="${pageContext.request.contextPath}" />
	<input id="contact_account" type="hidden"
		value="${contactDetail.account}" />
	<script type="text/javascript">
		$(function(){
			var basePath = $("input#basePath").val();
			$("button.send_msg").click(function(){
				var contact_account  = $("input#contact_account").val();
				window.location.href=basePath+"/message/fullychat?contact_account="+contact_account;
			});
		})
	</script>
</body>
</html>