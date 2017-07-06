<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>登录</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/signin.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>
<script>
	$(function() {
		$("a#forgotpassword").click(function() {
			window.location.href="${pageContext.request.contextPath}/chat/toForgotPassword";
		});
		$("a#signup").click(function() {
			window.location.href="${pageContext.request.contextPath}/chat/tosignup";
		});
		$("button#signinbtn").click(function(){
			var account = $("input#account").val();
			var password = $("input#password").val();
			
			$.ajax({
				 type: "POST",
	             url: "${pageContext.request.contextPath}/chat/signin",
	             data: {
	            	 "account" : account,
	            	 "password" : password,
	            	 "create_timestamp":Date.parse(new Date())/1000
	             },
	             dataType: "json",
	             success: function(res){
	            	 
	            	 if(res.message=="login"){
	            	 	window.location.href="${pageContext.request.contextPath}/chat/index";
	            	 }else{
	            		 console.log(res);
	            	 }
	             },
	             error: function(e){
	            	 alert("请求失败！");
	            	 console.log(e);
	             }
			});
		});
	})
</script>
</head>
<body>
	<div class="signtop">es畅聊</div>
	<div class="signin">
		<div class="account">
			<label>账号：</label> <input type="text" id="account" name="account" />
		</div>
		<div class="password">
			<label>密码：</label><a class="forgotpassword"
				href="javascript:void(0);" id="forgotpassword">忘记密码？</a> <input
				type="password" id="password" name="password" />
		</div>
		<div class="signinbtn">
			<button id="signinbtn">登 录</button>
		</div>
	</div>
	<div class="signup">
		还没有账号？ <a class="signup" href="javascript:void(0);" id="signup" >去注册</a>
	</div>
</body>
</html>