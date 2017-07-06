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

<script>
	var basePath;
	$(function() {
		basePath = $("input#basePath").val();
		$("button#signupbtn").click(function(){
			var newpass = $("input#phone").val();
			var confirmpass = $("input#password").val();
			$.ajax({
				type : "POST",
				url : "./resetPassword",
				data : {
					newpass : newpass,
					confirmpass:confirmpass
				},
				dataType : "text",
				success : function(res) {
					if(res == "phone_error"){
						validatePassword($("input#phone"));
					}else if(res == "not_equal"){
						validateComfirmPassword($("input#password"));
					}else if(res == "succ"){
						window.location.href=basePath+"/chat/tosignin";
					}
				}
			});
		});
	})
	function hideValidate(val) {
		$(val).next().hide();
	}
	function showValidate(val) {
		$(val).next().show();
	}
	var validatePasswordHtml = "<div class=\"validatepassword\"></div>";
	function validatePassword(val) {

		var newpass = $(val).val();
		var confirmpass = $("input#password").val();
		if (!$(".validatepassword").length > 0) {
			$(val).parent().append(validatePasswordHtml);
		}
		$.ajax({
			type : "POST",
			url : "../validate/reset_password",
			data : {
				newpass : newpass,
			},
			dataType : "json",
			success : function(res) {
				$(val).css({
					"border-color" : "red"
				});
				var border_color = "#FF3333";
				if (res.message.toString() == "此密码可以使用") {
					border_color = "green";
					$(val).css({
						"border-color" : "green"
					});
				}
				$(".validatepassword").html(
						"<div class='out'></div><div class='in'></div>"
								+ res.message);
				$(".validatepassword").css({
					"display" : "block",
					"border-color" : border_color
				});
				$(".validatepassword").find(".out").css({
					"border-bottom-color" : border_color
				});

			},
			error : function(e) {
				alert("请求失败！");
				console.log(e);
			}
		});
	}
	var validateComfirmPasswordHtml = "<div class=\"validatecomfirmpassword\"></div>";
	function validateComfirmPassword(val) {

		var confirmpass = $(val).val();
		var newpass = $("input#phone").val();
		if (!$(".validatecomfirmpassword").length > 0) {
			$(val).parent().append(validateComfirmPasswordHtml);
		}
		if("" == confirmpass){
			return;
		}
		if(confirmpass != newpass){
			$(val).css({
				"border-color" : "red"
			});
			var border_color = "#FF3333";
			$(".validatecomfirmpassword").html(
					"<div class='out'></div><div class='in'></div>"
							+ "新密码与确认密码不相同");
			$(".validatecomfirmpassword").css({
				"display" : "block",
				"border-color" : border_color
			});
			$(".validatecomfirmpassword").find(".out").css({
				"border-bottom-color" : border_color
			});
		}else{
			border_color = "green";
			$(val).css({
				"border-color" : "green"
			});	
			$(".validatecomfirmpassword").hide();
		}
	}
</script>
</head>
<body>
	<header>设置新密码</header>
	<div class="signup">
		<div class="password">
			<label>设置新密码</label> <input type="password" id="phone" name="phone" onkeyup="validatePassword(this)" onblur="hideValidate(this)"/>
		</div>
		<div class="password">
			<label>确认密码</label> <input type="password" id="password"
				name="password" onkeyup="validateComfirmPassword(this)" onblur="hideValidate(this)"/>
		</div>
		<div class="signupbtn">
			<button id="signupbtn">提交</button>
		</div>
		<div id="showMessage" class="showMessage"></div>
	</div>
	<input id="basePath" type="hidden"
		value="${pageContext.request.contextPath}" />
</body>
</html>