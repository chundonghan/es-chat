<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>新的盟友</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/new_contact.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/toast.js"></script>	

</head>
<body>
		<a href="javascript:history.go(-1);" id="history"><img id="history" src="${pageContext.request.contextPath}/img/Arrow_Left.png" />
			<div class="history">通讯录</div>
		</a>
		<header>新的盟友</header>
		<section>
			<div class="new_contact_search">
				<div class="new_contact_search_box" ><img src="${pageContext.request.contextPath}/img/index/chat_search.png" class="new_contact_search_box" />es账号/手机号 </div>
			</div>
			<div class="new_contact_list">
				
			</div>
			<input id="basePath" type="hidden"
				value="${pageContext.request.contextPath}" />
		<div id="showMessage" class="showMessage" ></div>
		</section>
		<div class="search_pop" id="search_pop">
			<div class="search_top">
				<div class="search_top_left">
					<div class="search_box">
						<img class="search_box" src="${pageContext.request.contextPath}/img/index/chat_search.png" />
						<input class="search_account" id="search_account" type="text" placeholder="微信号/手机号" />
						<div class="search_erase">
							<img class="search_erase" src="${pageContext.request.contextPath}/img/x.png"/>	
						</div>
					</div>
				</div>
				<div class="search_top_right">
					<a class="search_cancel" id="search_cancel" href="javascript:void(0)" onclick="searchCancel();">取消</a>
				</div>
			</div>
			<div class="search_content">
				<div class="image">
					<img src="${pageContext.request.contextPath}/img/search.png"/>
				</div>
				<div class="search_text">
					<div class="search_title">搜索：</div>
					<div class="search_text_detail"></div>
				</div>
			</div>
			<div class="not_exist">
				此用户不存在
			</div>
		</div>
		
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/new_contact.js"></script>
</html>