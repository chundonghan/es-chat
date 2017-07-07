var basePath;
var selected_contact = [];
var token;
$(function(){
	basePath = $("input#basePath").val();
	token = $("input#token").val();
	contactSection();
	initialSearchBox();
	$("body").on('click touchend', 'div.each_contact', function(){
		var account = $(this).attr("id");
		var avatar = $(this).children("div.ec_left").html().trim();
		if($(this).children("div.check").children("img.circle").is(":hidden")){
			$(this).children("div.check").children("img.circle").show();
			selected_contact.push(account);
			$("div.selected_avatar").append(avatar);
			
		}else{
			$(this).children("div.check").children("img.circle").hide();
			remove(selected_contact,account);
			$("div.selected_avatar").children("#"+account).remove();
			
		}
		if(selected_contact.length>0){
			$("a#group_save").text("确定("+selected_contact.length+")");
			$("a#group_save").css({"color":"lightgreen"});
			
		}else{
			$("a#group_save").text("确定");
			$("a#group_save").css({"color":"grey"});
		}
		initialSearchBox();
	})
	$("a#group_save").click(function(){
		if(selected_contact.length==0){
			return;
		}
		$.ajax({
			type : "POST",
			url : basePath+"/group/makeGroup",
			data : {
				contacts:selected_contact
			},
			dataType : "text",
			success : function(res) {
				if(res == "succ"){
					window.location.href=basePath+"/chat/index";
				}else{
					toast("创建失败","showMessage");
				}
			},
			error : function(e) {
				alert("请求失败！");
				console.log(e);
			}
		});
	})
})
function initialSearchBox(){
	var selected_avatar_w = $("div.selected_avatar").width();
	var dw = $(document).width();
	var search_contact_w = dw - selected_avatar_w-20;
	$("div.search_contact").css({ "width" : search_contact_w });
}
function indexOf(arr,val){
	for (var i = 0; i < arr.length; i++) {
		if (arr[i] == val) 
			return i;
	}
	return -1;
}
function remove(arr,val){
	var index = indexOf(arr,val);
	if (index > -1) {
		arr.splice(index, 1);
	}
}
function contactSection(val){
	$.ajax({
		type : "POST",
		url : basePath+"/contact/contactList",
		data : {
		},
		dataType : "json",
		success : function(res) {
			section = "<div class=\"contact_list\">";
			var count = 0;
			$.each(res,function(index,value){
				if(value.hasOwnProperty("initial")){
					section+="<div class=\"contact_init\">"+value.initial+"</div>";
				}else{
					section+= "<div class=\"each_contact\" id=\""+value.account+"\">"+
									"<div class=\"check\">"+
										"<div class=\"circle\"></div>"+
										"<img class=\"circle\" src=\""+basePath+"/img/circle.png\" />"+
									"</div>"+
									"<div class=\"ec_left\"><img  class=\"ec_left\" id=\""+value.account+"\" src=\""+basePath+"/"+value.avatar+"\"/></div>"+
									"<div class=\"ec_right\">"+value.nickname+"</div>"+
								"</div>";
					count ++;
				}
			});
			section += "</div>";
			$("section").append(section);
		},
		error : function(e) {
			alert("请求失败！");
			console.log(e);
		}
	});
	
}