var basePath;
var to_account;
var to_avatar;
$(function(){
	basePath = $("input#basePath").val();
	to_account = $("input#to_account").val();
	to_avatar = $("input#to_avatar").val();
	from_account = $("input#from_account").val();
	from_avatar = $("input#from_avatar").val();
	initialFullychat();
	$("input#chat_input").click(function(){
		var target = this;
		  setTimeout(function(){
		        target.scrollIntoView(true);
		   },100);
	})
})

var section;
function initialFullychat(){
	fullychat();
}
function fullychat(val){
	$.ajax({
		type : "POST",
		url : basePath+"/message/earlierChat",
		data : {
			to_account:to_account
		},
		dataType : "json",
		success : function(res) {
			section = "<div class=\"chat_time\">"+
								  "<div class=\"\" style=\"font-size:0.8rem;color:;\">"+
									  "——————以下是最近消息——————" +
								  "</div>"+
							  "</div>";
			$.each(res,function(index,value){
				if(value.hasOwnProperty("chat_time_title")){
					section += "<div class=\"chat_time\">"+
								  "<div class=\"chat_time_detail\">"+
									  value.chat_time_title +
								  "</div>"+
							  "</div>";
				}else{
					if(value.from_flag == '1'){
						section += "<div class=\"chat_from\" >" +
										"<div class=\"from_avatar\">" +
											"<img class=\"from_avatar\" src=\""+basePath+"/"+value.from_avatar+"\" />" +
										"</div>" +
										"<div class=\"from_content\" id=\""+value.from_account+"\">" +
											"<div class=\"out\"></div>" +
											"<div class=\"in\"></div>" +
											value.msg +
										"</div>" +
									"</div>";
					}else{
						section += "<div class=\"chat_to\" >" +
										"<div class=\"to_avatar\">" +
											"<img class=\"to_avatar\" src=\""+basePath+"/"+value.from_avatar+"\" />" +
										"</div>" +
										"<div class=\"to_content\" id=\""+value.from_account+"\">" +
											"<div class=\"out\"></div>" +
											"<div class=\"in\"></div>" +
											value.msg +
										"</div>" +
									"</div>";
						
						
					}
					
				}
			});		
			
			$("section").html(section);
			toBottom();
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
	websocket = new WebSocket("ws://10.10.2.52:4040/es-chat/webchat");
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
var from_avatar = '';
var from_account = '';
//接收到消息的回调方法
websocket.onmessage = function(event) {
	var msg  = event.data;
	var chat_to =  "<div class=\"chat_time\">"+
						  "<div class=\"chat_time_detail\">"+
						  		getCurrentTime() +
						  "</div>"+
					"</div>"+
					"<div class=\"chat_to\" >" +
						"<div class=\"to_avatar\">" +
						"<img class=\"to_avatar\" src=\""+basePath+"/"+to_avatar+"\" />" +
					"</div>" +
					"<div class=\"to_content\" id=\""+to_account+"\">" +
						"<div class=\"out\"></div>" +
						"<div class=\"in\"></div>" +
							msg +
					"</div>" +
					"</div>";
	
	$("section").append(chat_to);
	toBottom();
}
function getCurrentTime(){
	var date = new Date();
	var currentTime = date.getHours() + ":" + date.getMinutes();
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
//发送消息
function sendMsg(){
	var msg = $("input#chat_input").val().trim();
	if(''==msg){
		alert("不能发送空白消息");
		return;
	}
	websocket.send(msg);
	var chat_from =  "<div class=\"chat_time\">"+
						  "<div class=\"chat_time_detail\">"+
						  		getCurrentTime() +
						  "</div>"+
					"</div>"+
					"<div class=\"chat_from\" >" +
						"<div class=\"from_avatar\">" +
						"<img class=\"from_avatar\" src=\""+basePath+"/"+from_avatar+"\" />" +
					"</div>" +
					"<div class=\"from_content\" id=\""+from_account+"\">" +
						"<div class=\"out\"></div>" +
						"<div class=\"in\"></div>" +
							msg +
					"</div>" +
					"</div>";
	$("section").append(chat_from);
	toBottom();
	$("input#chat_input").val("");
	$("input#chat_input").focus();
}
function toBottom(){
	var h = $(document).height()-$(window).height();
	 $(document).scrollTop(h);
}
document.onkeydown=function(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];
    if(e && e.keyCode==27){ // 按 Esc 
        //要做的事情
      }
    if(e && e.keyCode==113){ // 按 F2 
         //要做的事情
       }            
     if(e && e.keyCode==13){ // enter 键
    	 sendMsg();
    }
}; 