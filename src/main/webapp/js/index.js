var on_select_li="chat";
var basePath;
$(function(){
	basePath = $("input#basePath").val();
	li_ids=["chat","contact","discover","me"];
	$("footer ul li").click(function(){
		var li_id = $(this).attr("id");
		
		if(on_select_li != li_id){
			//$("#"+on_select_li).html("<img src='img/index/"+on_select_li+"_.png' id='"+on_select_li+"'/>");
			if (on_select_li == "chat") {
				$("#" + on_select_li).find("img#" + on_select_li).hide();
				$("#" + on_select_li).find("img#" + on_select_li + "_").show();
			} else {
				$("#" + on_select_li).find("img#" + on_select_li).show();
				$("#" + on_select_li).find("img#" + on_select_li + "_").hide();
			}
			
			on_select_li = li_id;
		}
		if (li_id == "chat") {
			$(this).find("img#" + li_id).show();
			$(this).find("img#" + li_id + "_").hide();
		} else {
			$(this).find("img#" + li_id).hide();
			$(this).find("img#" + li_id + "_").show();
		}
		switch (li_id) {
		case "chat":
			chatSection(this);
			break;
		case "contact":
			contactSection(this);
			break;
		case "discover":
			discoverSection(this);
			break;
		case "me":
			meSection(this);
			break;

		}
		
	});
	initialChatSection();
	
	$("body").on('click touchend', '.each_contact', function(){
		var contact_account  = $(this).attr("id");
		window.location.href=basePath+"/contact/contactDetail?contact_account="+contact_account;
	});
	$("body").on('click touchend', '.each_chat', function(){
		var contact_account  = $(this).attr("id");
		var is_group  =$(this).attr("data-group");
		if('0' == is_group){
			window.location.href=basePath+"/message/fullychat?contact_account="+contact_account;
		}
		if('1' == is_group){
			window.location.href=basePath+"/group/message/fullychat?group_id="+contact_account;
		}
	});
	$("body").on('click touchend', 'div#new_contact', function(){
		window.location.href=basePath+"/contact/newContact";
	});
	$("body").on('click touchend', 'div#group_list', function(){
		window.location.href=basePath+"/group/toGroupList";
	});
	$("body").on('click touchend', 'div.me_detail', function(){
		window.location.href=basePath+"/me/detail";
	});
	$("body").on('click touchend', 'a.add_more', function(){
		if($("div.add_more").is(":hidden")) {
			$("div.add_more").show("600");
		}else{
			$("div.add_more").hide("600");
		}
		return false;
	});
	$("body").click(function(e){
		if(e.target.className == "opt_title" || e.target.className == "opt_img"){
			return false;	
		}
		$("div.add_more").hide("600");
		return false;
	})
	$("body").on('click touchend', 'div#group_chat', function(){
		window.location.href=basePath+"/group/toMakeGroup";
	});
	$("body").on('click touchend', 'div#add_contact', function(){
		window.location.href=basePath+"/contact/newContact";
	});
})
var section;
function initialChatSection(){
	chatSection();
}
function chatSection(val){
	$("header").text("es-chat");
	$("body").css({"background-color":"#f9f9f9"});
	$.ajax({
		type : "POST",
		url : basePath+"/message/chatList",
		data : {
		},
		dataType : "json",
		success : function(res) {
			section = "<a class=\"add_more\" href=\"javascript:void(0);\">+</a>" +
						"<div class=\"add_more\">" +
							"<div class=\"out\"></div>" +
							"<div class=\"in\"></div>" +
							"<div class=\"each_opt\" id=\"group_chat\">" +
								"<img class=\"opt_img\" src=\""+basePath+"/img/index/Chat_bubble.png\" /><div class=\"opt_title\">发起群聊</div>" +
							"</div>" +
							"<div class=\"each_opt\" id=\"add_contact\">" +
								"<img class=\"opt_img\" src=\""+basePath+"/img/index/friends.png\" /><div class=\"opt_title\">添加朋友</div>" +
							"</div>" +
						"</div>";
			section += "<div class=\"chat_search\" >"+
						  "<div class=\"chat_search_box\"><img src=\""+basePath+"/img/index/chat_search.png\" class=\"chat_search_box\"/> 搜索 </div>"+
					  "</div>"+
					  "<div class=\"chat_content\">";
			$.each(res,function(index,value){
				section += "<div class=\"each_chat\" id=\""+value.account+"\" data-group=\""+value.is_group+"\" >"+
								"<div class=\"west\">";
								if(value.has_new_msg == '1'){
									section +="<div class=\"chat_red_dot\"></div>";
								}
								section += 	"<img src=\""+basePath+"/"+value.avatar+"\"/>"+
								"</div>"+
								"<div class=\"east\">"+
									"<div class=\"north\">"+
										"<div class=\"nickname\">"+
											value.nickname+
										"</div>"+
										"<div class=\"last_time\">"+
											value.last_chat_time+
										"</div>"+
									"</div>"+
									"<div class=\"south\">"+
										value.msg+
									"</div>"+
								"</div>"+
							"</div>";
			});		
			section += "<div class=\"end\">"+
							"已经加载全部"+
						"</div>"+
					"</div>";
			$("section").html(section);
		},
		error : function(e) {
			alert("请求失败！");
			console.log(e);
		}
	});
}
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
var local_from_avatar;
var local_nickname;
//接收到消息的回调方法
websocket.onmessage = function(event) {
	var ig = false;
	var msg  = event.data;
	var head = msg.substring(0,msg.indexOf("/"));
	var from = msg.substring(msg.indexOf("/")+1,msg.indexOf("@"));
	if(from.substring(0,1) == '#'){
		ig = true;
		from = from.substring(1,from.length);
	}
	
	$.ajax({
		type : "POST",
		url : basePath+"/message/info",
		async: false,
		data : {
			ig:ig,
			account:from
		},
		dataType : "json",
		
		success : function(res) {
			if(ig){
				local_from_avatar = res.group_avatar;
				local_nickname = res.group_name;
			}else{
				local_from_avatar = res.avatar;
				local_nickname = res.nickname;
			}
		}
	});
	var to = msg.substring(msg.indexOf("@")+1,msg.indexOf(":"));
	var text = msg.substring(msg.indexOf(":")+1);
	if(head == "msg"){
		if($("div.chat_content").children("div#"+from).length >0){
			$("div#"+from).children("div.east").children("div.north").children("div.last_time").text(getCurrentTime());
			$("div#"+from).children("div.east").children("div.south").text(text);
			$("div#"+from).children("div.west").prepend("<div class=\"chat_red_dot\"></div>");
		}else{
			//没有发过言 群
			if(ig){
				$("div.chat_content").prepend(
						"<div class=\"each_chat\" id=\""+from+"\" data-group=\"1\" >" +
						"<div class=\"west\">" +
							"<div class=\"chat_red_dot\"></div>" +
								"<img src=\""+basePath+"/"+local_from_avatar+"\"/>" +
						"</div>" +
						"<div class=\"east\">" +
							"<div class=\"north\">" +
								"<div class=\"nickname\">" +
									local_nickname +
								"</div>" +
								"<div class=\"last_time\">" +
									getCurrentTime() +
								"</div>"+
							"</div>" +
							"<div class=\"south\">" +
								text +
							"</div>" +
						"</div>" +
					"</div>"
				 );
			}else{//没有发过言 私
				$("div.chat_content").prepend(
						"<div class=\"each_chat\" id=\""+from+"\" data-group=\"0\" >" +
						"<div class=\"west\">" +
							"<div class=\"chat_red_dot\"></div>" +
								"<img src=\""+basePath+"/"+local_from_avatar+"\"/>" +
						"</div>" +
						"<div class=\"east\">" +
							"<div class=\"north\">" +
								"<div class=\"nickname\">" +
									local_nickname +
								"</div>"+
								"<div class=\"last_time\">" +
									getCurrentTime() +
								"</div>" +
							"</div>" +
							"<div class=\"south\">" +
								text +
							"</div>" +
						"</div>" +
					"</div>"
				 );
			}
		}
	}else if(head == "contact"){
		$("div#new_contact").children("div.cam_left").prepend("<div class=\"chat_red_dot\"></div>");
	}
	
}
function getCurrentTime(){
	var date = new Date();
	var min = date.getMinutes();
	if(min<10){
		min = "0"+min;
	}
	var currentTime = date.getHours() + ":" + min;
	return currentTime;
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


function contactSection(val){
	$("header").text("通讯录");
	$("body").css({"background-color":"#f9f9f9"});
	$.ajax({
		type : "POST",
		url : basePath+"/contact/contactList",
		data : {
		},
		dataType : "json",
		success : function(res) {
			section =  "<div class=\"contact_search\" >"+
							"<div class=\"contact_search_box\"><img src=\""+basePath+"/img/index/chat_search.png\" class=\"contact_search_box\"/> 搜索 </div>"+
						"</div>"+
						"<div class=\"contact_menu\">"+
						"<div class=\"contact_each_menu\" id=\"new_contact\">"+
							"<div class=\"cam_left\">"+
							"<img class=\"cam_left\" src=\""+basePath+"/img/index/new_friend.png\"/></div>"+
							"<div class=\"cam_right\">新的盟友</div>"+
						"</div> "+
						"<div class=\"contact_each_menu\" id=\"group_list\">"+
							"<div class=\"cam_left\"><img class=\"cam_left\" src=\""+basePath+"/img/index/group_chat.png\"/></div>"+
							"<div class=\"cam_right\">盟军</div>"+
						"</div>"+
						"</div>";
			section+= "<div class=\"contact_list\">";
			var count = 0;
			$.each(res,function(index,value){
				if(value.hasOwnProperty("initial")){
					section+="<div class=\"contact_init\">"+value.initial+"</div>";
				}else{
					section+= "<div class=\"each_contact\" id=\""+value.account+"\">"+
									"<div class=\"ec_left\"><img  class=\"ec_left\" src=\""+basePath+"/"+value.avatar+"\"/></div>"+
									"<div class=\"ec_right\">"+value.nickname+"</div>"+
								"</div>";
					count ++;
				}
			});
			section += "</div>"+
						"<div class=\"end\">"+
								"共"+count+"个联系人"+
						"</div>";
			$("section").html(section);
			$.ajax({
				type : "POST",
				url : basePath+"/contact/getNewContact",
				data : {
				},
				dataType : "text",
				success : function(res) {
					if(res == "has"){
						$("div#new_contact").children("div.cam_left").prepend("<div class=\"chat_red_dot\"></div>");
					}
				}
			});
		},
		error : function(e) {
			alert("请求失败！");
			console.log(e);
		}
	});
	
}
function discoverSection(val){
	$("header").text("发现");
	section="3";
	$("section").html(section);
}
function meSection(val){
	$("header").text("我");
	$("body").css({"background-color":"#dddddd"});
	$.ajax({
		type : "POST",
		url : basePath+"/me/info",
		data : {
		},
		dataType : "json",
		success : function(res) {
			section =  "<div class=\"me_detail\" >" +
							"<div class=\"west\">" +
							"<img src=\""+basePath+"/"+res.avatar+"\" class=\"west\" />" +
						"</div>" +
						
						"<div class=\"east\">" +
							"<div class=\"north\">" +
								res.nickname +
							"</div>" +
							"<div class=\"south\">" +
								"es账号："+res.account +
							"</div>" +
						"</div>" +
						"<div class=\"right_arrow\">" +
							"<img class=\"me\" src=\""+basePath+"/img/right_arrow.png\" />" +
						"</div>" +
						
					"</div>" +
					"<div class=\"setting\">" +
						"<div class=\"setting_content\">设置</div>" +
						"<img class=\"right_arrow\" src=\""+basePath+"/img/right_arrow.png\" />" +
					"</div>";
				
			$("section").html(section);
		},
		error : function(e) {
			alert("请求失败！");
			console.log(e);
		}
	});
}