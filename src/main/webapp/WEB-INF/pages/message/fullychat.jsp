<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>${nickname}</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/fullychat.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/fullychat.js"></script>
	<script type="text/javascript">
		$(function(){
			
		})
	</script>
</head>
<body>
	<a href="javascript:history.go(-1);" id="history"><img id="history"
		src="${pageContext.request.contextPath}/img/Arrow_Left.png" />
		<div class="history">es-chat</div> </a>
	<header>${nickname}</header>
	<section id="page">
	</section>
	<footer>
		<div class="audio_input"></div>
		<div class="chat_input">
			<input type="text" id="chat_input" />
		</div>
		<div class="emoji_inpput"></div>
		<div class="send">
			<button id="send" onclick="sendMsg();">发送</button>
		</div>
	</footer>
	<input id="basePath" type="hidden"
		value="${pageContext.request.contextPath}" />
	<input id="to_account" type="hidden"
		value="${to_account}" />	
	<input id="to_avatar" type="hidden"
		value="${to_avatar}" />
	<input id="from_account" type="hidden"
		value="${from_account}" />	
	<input id="from_avatar" type="hidden"
		value="${from_avatar}" />	
		
</body>
</html>