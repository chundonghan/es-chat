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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/new_contact_detail.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/toast.js"></script>	
<script type="text/javascript">
	$(function() {
		var default_validate_input = "我是"+"${accountDetail.nickname}";
		var basePath = $("input#basePath").val();
		$("button.send_msg").click(function() {
			
			$("div.send_pop").show("slow");
			$("input#validate_input").val(default_validate_input);
			$("img.erase").show();
			
		});
		$("a#cancel").click(function() {
			$("div.send_pop").slideUp();
		})
		$("a#send").click(function(){
			var contact_account  = $("input#contact_account").val();
			var validate_msg = $("input#validate_input").val().trim();
			if(validate_msg == ''){
				toast("验证信息不能为空", "showMessage");
				return;
			}
			$.ajax({
				type : "POST",
				url : basePath+"/contact/addContact",
				data : {
					contact_account:contact_account,
					validate_msg:validate_msg
				},
				dataType : "text",
				success : function(res) {
					if(res == 'error'){
						toast("已发送", "showMessage");
					}else if(res == "succ"){
						toast("发送成功", "showMessage");
						var msg = "contact/FROM_ACCOUNT@"+contact_account+":MESSAGE";
						websocket.send(msg);
					}
					
				},
				error : function(e) {
					alert("请求失败！");
					console.log(e);
				}
			});
		})
		$("img.erase").click(function() {
			$(this).hide();
			$("input#validate_input").val("");
			$("input#validate_input").focus();
		})
		$("input#validate_input").keyup(function(){
			var val = $(this).val().trim();
			if(val == ''){
				$("img.erase").hide();
			}else{
				if($("img.erase").is(":hidden")){
					$("img.erase").show();    
				}
			}
		})
	})
var websocket = null;
//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
	websocket = new WebSocket("ws://10.10.2.52:4040/es-chat/webpush");
} else {
	alert('当前浏览器 Not support websocket')
}

//连接发生错误的回调方法
websocket.onerror = function() {
	console.log("WebSocket连接发生错误");
};

//连接成功建立的回调方法
websocket.onopen = function() {
	console.log("WebSocket连接成功");
}
//接收到消息的回调方法
websocket.onmessage = function(event) {
}
//连接关闭的回调方法
websocket.onclose = function() {
	console.log("WebSocket连接关闭");
}

//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function() {
	closeWebSocket();
}

//关闭WebSocket连接
function closeWebSocket() {
	websocket.close();
}

</script>
</head>
<body>
	<a href="javascript:history.go(-1);" id="history"><img id="history"
		src="${pageContext.request.contextPath}/img/Arrow_Left.png" />
		<div class="history">新的盟友</div> </a>
	<header>详细资料</header>
	<section>
		<div class="contact_detail">
			<div class="west">
				<img src="${pageContext.request.contextPath}/img/sonic_generations.png" class="west" />
			</div>
			<div class="east">
				<div class="north">${contactDetail.nickname}</div>
			</div>

		</div>
		<div class="tag">
			<div class="tag_content">设置备注和标签</div>
			<img class="right_arrow tag" src="${pageContext.request.contextPath}/img/right_arrow.png" />
		</div>
		<div class="send_msg">
			<button class="send_msg">添加好友</button>
		</div>
	</section>
	<div class="send_pop">
		<div class="headline">
			<a href="javascript:void(0)" id="cancel">取消</a>
			<div class="contact_validate">朋友验证</div>
			<a href="javascript:void(0)" id="send">发送</a>
		</div>
		<div class="validate_msg">
			<div class="validate_title">你需要发送验证申请，等对方验证</div>
			<div class="validate_input">
				<input id="validate_input" type="text" value=""/> <img class="erase"
					src="${pageContext.request.contextPath}/img/x.png">
			</div>
		</div>
		<div id="showMessage" class="showMessage" ></div>
	</div>
	<input id="basePath" type="hidden"
		value="${pageContext.request.contextPath}" />
	<input id="contact_account" type="hidden"
		value="${contactDetail.account}" />
</body>
</html>