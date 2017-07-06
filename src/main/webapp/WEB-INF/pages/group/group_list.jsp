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
<script type="text/javascript">
	var basePath;
	$(function(){
		basePath = $("input#basePath").val();
		getGroupList();
		$("body").on('click touchend', 'div.each_contact', function(){
			var group_id = $(this).attr("id");
			window.location.href=basePath+"/group/message/fullychat?group_id="+group_id;
		});
	})
	function getGroupList(){
		$.ajax({
			type : "POST",
			url : basePath+"/group/groupList",
			data : {
			},
			dataType : "json",
			success : function(res) {
				section = "";
				$.each(res,function(index,value){
					section += "<div class=\"each_contact\" id=\""+ value.id +"\">" +
									"<div class=\"west\">" +
										"<img class=\"new_contact_avatar\" src=\""+basePath+"/"+value.group_avatar+"\">" +
									"</div>" +
									"<div class=\"center\" style=\"line-height:2.5rem;\">" +
										value.group_name +
									"</div>" +
									"<div class=\"east\">";
							section +="</div>";
					section +="</div>";
				});		
				
				$("div.new_contact_list").html(section);
			},
			error : function(e) {
				alert("请求失败！");
				console.log(e);
			}
		});
	}
</script>
</head>
<body>
	<a href="javascript:history.go(-1);" id="history"><img id="history" src="${pageContext.request.contextPath}/img/Arrow_Left.png" />
		<div class="history">通讯录</div>
	</a>
	<header>新的盟友</header>
	<section>
		<div class="new_contact_search">
			<div class="new_contact_search_box" ><img src="${pageContext.request.contextPath}/img/index/chat_search.png" class="new_contact_search_box" />搜索 </div>
		</div>
		<div class="new_contact_list">
			
		</div>
		<input id="basePath" type="hidden"
			value="${pageContext.request.contextPath}" />
	<div id="showMessage" class="showMessage" ></div>
	</section>
	<input id="basePath" type="hidden"
		value="${pageContext.request.contextPath}" />
		
</body>
</html>