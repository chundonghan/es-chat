var basePath;
$(function(){
	basePath = $("input#basePath").val();
	initialApply();
	$("div.new_contact_search_box").click(function() {
		$("div.search_pop").show();
	});
	$("input#search_account").bind('input propertychange',function(){
		
		var val = $(this).val().trim();
		
		if(val!=''){
			$("img.search_erase").show(300);
			$("div.search_content").show();
			$("div.search_text_detail").text(val);
		}else{
			$("div.search_content").hide();
			$("img.search_erase").hide();
		}
	});
	$("img.search_erase").click(function(){
		$("input#search_account").val("");
		$("input#search_account").focus();
		$("div.search_content").hide();
		$("div.not_exist").hide();
		$(this).hide();
	});
	$("div.search_content").on('click',function(){
		$("div.not_exist").hide();
		var search_content = $("div.search_text_detail").text();
		$.ajax({
			type : "POST",
			url : basePath+"/contact/newContactSearch",
			data : {
				search_content:search_content
			},
			dataType : "json",
			success : function(res) {
				if(res.status == 0){
					alert(res.message);
				}else if(res.status == 1){
					$("div.search_content").hide();
					$("div.not_exist").show();
				}else if(res.status == 2){
					window.location.href = basePath+"/contact/contactDetail?contact_account="+search_content;
				}else if(res.status == 3){
					window.location.href = basePath +"/contact/newContactDetail?contact_account="+search_content;
				}else if(res.status == 4){
					alert(res.message);
				}
			},
			error : function(e) {
				alert("请求失败！");
				console.log(e);
			}
		});
	});
	$("body").on('click', 'a#contact_add', function(){
		var contact_account  = $(this).parent().parent().attr("id");
		$.ajax({
			type : "POST",
			url : basePath+"/contact/addContact",
			data : {
				contact_account:contact_account
			},
			dataType : "text",
			success : function(res) {
				if(res == 'error'){
					toast("添加失败", "showMessage");
				}else if(res == "succ"){
					initialApply();
				}
				
			},
			error : function(e) {
				alert("请求失败！");
				console.log(e);
			}
		});
		
	});
})
function searchCancel(){
		$("div.not_exist").hide();
		$("div.search_pop").hide();
		$("input#search_account").val("");
}
var section;
function initialApply(val){
	$.ajax({
		type : "POST",
		url : basePath+"/contact/applyList",
		data : {
		},
		dataType : "json",
		success : function(res) {
			section = "";
			$.each(res,function(index,value){
				section += "<div class=\"each_contact\" id=\""+ value.account +"\">" +
								"<div class=\"west\">" +
									"<img class=\"new_contact_avatar\" src=\""+basePath+"/"+value.avatar+"\">" +
								"</div>" +
								"<div class=\"center\">" +
									"<div class=\"north\">" +
										value.nickname +
									"</div>" +
									"<div class=\"south\">" +
										value.validate_msg +
									"</div>" +
								"</div>" +
								"<div class=\"east\">";
									if(value.is_contacted==1){
										section += "<a href=\"javascript:void(0);\" id=\"contact_added\">已添加</a>";
									}else{
										section += "<a href=\"javascript:void(0);\" id=\"contact_add\">添&nbsp;&nbsp;加</a>";
									}
									
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
