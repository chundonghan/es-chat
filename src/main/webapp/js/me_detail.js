var basePath;
//昵称缓存
var value;

$(function(){
	basePath = $("input#basePath").val();
	$("div.gender").click(function() {
		var g  = $("div.gender_").text().trim();
		$("div.edit_pop").show("slow");
		if($("div.edit_pop:has(div)").length!=0){
			return;
		}
		var content = '';
		content = "<a href=\"javascript:void(0);\" id=\"history\" onclick=\"cancel();\">" +
				"<img id=\"history\" src=\""+basePath+"/img/Arrow_Left.png\" />" +
				"<div class=\"history\">个人信息</div> </a>" +
				"<header>性别</header>";
		content += "<div class=\"change_gender\">" +
						"<div class=\"genders\">" +
							"<div class=\"gender_name\">" +
								"男" +
							"</div>";
		if(g == '男'){
			content  +=	"<div class=\"gender_is_selected\">" +
								"<img class=\"yes\" src=\""+basePath+"/img/yes.png\" />" +
							"</div>";
		}
			content +=	"</div>" + 
						"<div class=\"genders\">" +
							"<div class=\"gender_name\">" +
								"女" +
							"</div>";
		if(g == '女'){
			content +=	"<div class=\"gender_is_selected\">" +
								"<img class=\"yes\" src=\""+basePath+"/img/yes.png\" />" +
							"</div>";
		}	
		content += "</div>" +
					"</div>";
		$("div.edit_pop").append(content);
	});
	$("div.nickname").click(function() {
		if($("div.edit_pop:has(div)").length!=0){
			return;
		}
		var nickname = $("input#nickname").val();
		$("div.edit_pop").show("slow");
		var content = '';
		content = 	"<div class=\"headline\">" +
						"<a href=\"javascript:void(0)\" id=\"cancel\" onclick=\"cancel();\">取消</a>" +
						"<div class=\"nickname_title2\">昵称</div>" +
						"<a href=\"javascript:void(0)\" id=\"nickname_save\">保存</a>" +
					"</div>";
		content += "<div class=\"change_nickname\">"+
						"<input class=\"input_nickname\" value=\""+nickname+"\" /><img class=\"x\" src=\""+basePath+"/img/x.png\" />" +
				   "</div>";
		$("div.edit_pop").append(content);
	});
	$("div.avatar").click(function() {
		window.location.href=basePath+"/me/showAvatar";
	});
	$("body").on("click","div.genders",function(){
		var gen = $(this).children("div.gender_name").text().trim();
		$("div.gender_").text(gen);
		cancel();
		var gender_flag;
		if(gen == '女'){
			gender_flag = 0;
		}else if(gen == '男'){
			gender_flag = 1;
		}
		$.ajax({
			type : "POST",
			url : basePath+"/me/changeGender",
			data : {
				gender:gender_flag
			},
			dataType : "json",
			success : function(res) {
				
			}
		})
	});
	$("body").on("click","img.x",function(){
		$(this).hide();
		$("input.input_nickname").val("");
		$("input.input_nickname").focus();
		$("a#nickname_save").hide();
	});
	
	$("body").on("input propertychange","input.input_nickname",function(){
		value = trimLeft($(this).val());
		var nickname = $("input#nickname").val();
		
		if(value != ''){
			$("img.x").show();
		}else{
			$("img.x").hide();
		}
		if(value == nickname || value == ''){
			$("a#nickname_save").hide();
		}else{
			$("a#nickname_save").show();
		}
	});
	$("body").on("click","a#nickname_save",function(){
		cancel();
		toast("保存成功","showMessage");
		$("div.nickname_").text(value);
		$.ajax({
			type : "POST",
			url : basePath+"/me/changeNickname",
			data : {
				nickname:value
			},
			dataType : "json",
			success : function(res) {
				
			}
		})
	});
})
function cancel(){
	$("div.edit_pop").hide("slow");
	$("div.edit_pop").empty();
}
function trimLeft(str) {  
    var i = 0;  
    //从第一个位置即索引为0的位置开始查找不是" "空格的字符(串)  
    for (; i < str.length && str.charAt(i) == " "; i++);  
    return str.substring(i, str.length);  
} 