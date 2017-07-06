<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>我的头像</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/avatar.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/toast.js"></script>		
<script>
	var basePath;
	$(function() {
		basePath = $("input#basePath").val();
		imgVetically()
		$("a#more").click(function() {
			$("div.avatar_pop").show();
		});
		$("div.avatar_cancel").click(function() {
			$("div.avatar_pop").hide();
		});
		$("input.upload_").on("change", function() {
			var _this = $(this);
			var fr = new FileReader();
			fr.readAsDataURL(this.files[0]);
			var img = new Image();
			fr.onload = function() {
				img.src = this.result;
				var avatar = this.result;
				$("div.avatar_pop").hide();
				img.onload = function() {
					$("section").html(img);
					$("section").find("img").css({
						"width" : "100%"
					});
					imgVetically();
					console.log(avatar);
					$.ajax({
						type : "POST",
						url : basePath+"/me/changeAvatar",
						data : {
							avatar:avatar
						},
						dataType : "text",
						success : function(res) {
							if(res == "succ"){
								toast("保存成功","showMessage");
							}else if(res == "failed"){
								toast("保存失败","showMessage");
							}
						}
					})
				}
			}
		});
	})
	function imgVetically() {
		var h = $(document).height();
		var hh = $("header").height();
		var ih = $("section").find("img").height();
		var m = (h - hh - ih) / 2;
		$("section").find("img").css({
			"margin-top" : m
		});
	}
</script>
</head>
<body>
	<a href="javascript:history.go(-1);" id="history"><img id="history"
		src="${pageContext.request.contextPath}/img/Arrow_Left.png" />
		<div class="history">个人信息</div> </a>
	<a href="javascript:void(0);" id="more">···</a>
	<header>头像</header>
	<section>
		<img class="avatar" src="${pageContext.request.contextPath}/${avatar}" />
		
	</section>
	<div class="avatar_pop">
		<div class="avatar_options">
			<div class="choose_avatar">
				从本地选择<input type="file" class="upload_" />
			</div>
		</div>
		<div class="avatar_cancel">取&nbsp;消</div>
	</div>
	<div id="showMessage" class="showMessage" ></div>
	<input id="basePath" type="hidden"
		value="${pageContext.request.contextPath}" />
</body>
</html>