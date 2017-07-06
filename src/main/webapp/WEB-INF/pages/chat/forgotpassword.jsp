<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>注册</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/forgotpassword.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/toast.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/forgotpassword.js"></script>
<script>
	$(function() {
	})
</script>
</head>
<body>
	<header>找回密码</header>
		<div class="signup">
			<div class="password">
				<label>手机号：</label>
				<a class="getcode" href="javascript:void(0);" id="getcode" onclick="getCode(this)">获取验证码</a>
				<input type = "text" id = "phone" name = "phone" onkeyup = "validatePhone(this);"/>
			</div>
			<div class="password">
				<label>短信验证码：</label>
				<input type="text" id="smsCode" name="smsCode" />
			</div>
			<div class="signupbtn">
				<button id="signupbtn" onclick="signUp()">修改密码</button>
			</div>
			<div id="showMessage" class="showMessage" ></div>
		</div>
</body>
</html>