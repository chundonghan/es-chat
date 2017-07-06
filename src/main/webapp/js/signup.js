var getcode = "获取验证码";
var phone;
$(function() {

})
/* 获取验证码 */
var isPhone = 1;

function getCode(e) {
	checkPhone(); // 验证手机号码
	if (isPhone) {

		$.ajax({
			type : "POST",
			url : "../validate/messageCode",
			data : {
				phone : phone,
			},
			dataType : "json",
			success : function(res) {
				toast(res.message, "showMessage");
				resetCode(e); // 倒计时
			},
			error : function(e) {
				alert("请求失败！");
				console.log(e);
			}
		});
	} else {
		$('#phone').focus();
	}

}
// 验证手机号码
function checkPhone() {
	phone = $('#phone').val();
	var pattern = /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))[0-9]{8}$/;
	isPhone = 1;
	if (phone == '') {
		alert('请输入手机号码');
		isPhone = 0;
		return;
	}
	if (!pattern.test(phone)) {
		alert('请输入正确的手机号码');
		isPhone = 0;
		return;
	}
}
// 倒计时
function resetCode(e) {
	var getCodeHref = $(e).attr('href');
	var getCodeClick = $(e).attr('onclick');

	$(e).removeAttr('href'); // 去掉a标签中的href属性
	$(e).removeAttr('onclick'); // 去掉a标签中的onclick事件
	var second = 60;
	var timer = null;
	$(e).css({
		"background-color" : "#a6a6a6"
	})
	$(e).text(second + "秒后重发");
	timer = setInterval(function() {
		second -= 1;
		if (second > 0) {
			$('#getcode').text(second + "秒后重发");
		} else {
			clearInterval(timer);
			$(e).attr('href', getCodeHref); // 去掉a标签中的href属性
			$(e).attr('onclick', getCodeClick); // 去掉a标签中的onclick事件
			$(e).css({
				"background-color" : "#28a545"
			})
			$(e).text(getcode);
		}
	}, 1000);
}
var validateAccountHtml = "<div class='validateaccount'></div>";
function validateAccount(val) {
	var account = $(val).val();
	if (!$(".validateaccount").length > 0) {
		$(val).parent().append(validateAccountHtml);
	}
	$.ajax({
		type : "POST",
		url : "../validate/account",
		data : {
			account : account,
		},
		dataType : "json",
		success : function(res) {
			$(val).css({
				"border-color" : "red"
			});
			var border_color = "#FF3333";
			if (res.message.toString() == "此账号可以使用") {
				border_color = "green";
				$(val).css({
					"border-color" : "green"
				});
			}
			$(".validateaccount").html(
					"<div class='out'></div><div class='in'></div>"
							+ res.message);
			$(".validateaccount").css({
				"display" : "block",
				"border-color" : border_color
			});
			$(".validateaccount").find(".out").css({
				"border-bottom-color" : border_color
			});

		},
		error : function(e) {
			alert("请求失败！");
			console.log(e);
		}
	});
}
var validatePasswordHtml = "<div class=\"validatepassword\"></div>";
function validatePassword(val) {
	var account = $("input#account").val();

	var password = $(val).val();

	if (!$(".validatepassword").length > 0) {
		$(val).parent().append(validatePasswordHtml);
	}
	$.ajax({
		type : "POST",
		url : "../validate/password",
		data : {
			password : password,
			account : account,
		},
		dataType : "json",
		success : function(res) {
			$(val).css({
				"border-color" : "red"
			});
			var border_color = "#FF3333";
			if (res.message.toString() == "此密码可以使用") {
				border_color = "green";
				$(val).css({
					"border-color" : "green"
				});
			}
			$(".validatepassword").html(
					"<div class='out'></div><div class='in'></div>"
							+ res.message);
			$(".validatepassword").css({
				"display" : "block",
				"border-color" : border_color
			});
			$(".validatepassword").find(".out").css({
				"border-bottom-color" : border_color
			});

		},
		error : function(e) {
			alert("请求失败！");
			console.log(e);
		}
	});
}
var validatePhoneHtml = "<div class=\"validatephone\"></div>";
var phoneFlag;
function validatePhone(val) {
	var phone = $(val).val();

	if (!$(".validatephone").length > 0) {
		$(val).parent().append(validatePhoneHtml);
	}
	$.ajax({
		type : "POST",
		url : "../validate/phone",
		data : {
			phone : phone,
		},
		dataType : "json",
		success : function(res) {
			$(val).css({
				"border-color" : "red"
			});
			var border_color = "#FF3333";
			phoneFlag = false;
			if (res.message.toString() == "此手机号可以使用") {
				border_color = "green";
				$(val).css({
					"border-color" : "green"
				});
				phoneFlag = true;
			}
			$(".validatephone").html(
					"<div class='out'></div><div class='in'></div>"
							+ res.message);
			$(".validatephone").css({
				"display" : "block",
				"border-color" : border_color
			});
			$(".validatephone").find(".out").css({
				"border-bottom-color" : border_color
			});

		},
		error : function(e) {
			alert("请求失败！");
			console.log(e);
		}
	});
}
function hideValidate(val) {
	$(val).next().hide();
}
function showValidate(val) {
	$(val).next().show();
}
function signUp() {
	var account = $("input#account").val();
	var password = $("input#password").val();
	var phone = $("input#phone").val();
	var smsCode = $("input#smsCode").val();
	/*
	 * $("input").each(function(){ console.log($(this).attr("id")); var value =
	 * $(this).val(); console.log(value); });
	 */
	$.ajax({
		type : "POST",
		url : "./signup",
		data : {
			account : account,
			password : password,
			phone : phone,
			smsCode : smsCode,
		},
		dataType : "text",
		success : function(res) {
			var result = res;console.log(result);
			switch (result) {
			case "success":
				window.location.href = "./tosignin";
				break;
			case "failed":
				toast("注册失败", "showMessage");
				break;
			}
			var result_array = res.split(",");
			for (var i = 0; i < result_array.length; i++) {
				switch (result_array[i]) {
				case "code_error":
					toast("短信验证码有误", "showMessage");
					continue;
				case "account_error":
					validateAccount($("#account"));
					continue;
				case "password_error":
					validatePassword($("#password"));
					continue;
				case "phone_error":
					validatePhone($("#phone"));
					continue;
				}
			}
		},
		error : function(e) {
			alert("请求失败！");
			console.log(e);
		}
	});
}