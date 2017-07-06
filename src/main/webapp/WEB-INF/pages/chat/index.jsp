<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>es畅聊</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/index.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/index.js"></script>
</head>
<body>
	<header>es-chat</header>
	<section >
		
	</section>
	<footer>
		<ul>
			<li id="chat"><img
				src="${pageContext.request.contextPath}/img/index/chat.png"
				id="chat" /> <img
				src="${pageContext.request.contextPath}/img/index/chat_.png"
				id="chat_" /></li>
			<li id="contact"><img
				src="${pageContext.request.contextPath}/img/index/contact_.png"
				id="contact" /> <img
				src="${pageContext.request.contextPath}/img/index/contact.png"
				id="contact_" /></li>
			<li id="discover"><img
				src="${pageContext.request.contextPath}/img/index/discover_.png"
				id="discover" /> <img
				src="${pageContext.request.contextPath}/img/index/discover.png"
				id="discover_" /></li>
			<li id="me"><img
				src="${pageContext.request.contextPath}/img/index/me_.png" id="me" />
				<img src="${pageContext.request.contextPath}/img/index/me.png"
				id="me_" /></li>
		</ul>
	</footer>
	<input id="basePath" type="hidden"
		value="${pageContext.request.contextPath}" />
</body>
</html>