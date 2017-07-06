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
			url : "../validate/forgotpassword/messageCode",
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

var validatePhoneHtml = "<div class=\"validatephone\"></div>";
var phoneFlag;
function validatePhone(val) {
	var phone = $(val).val();

	if (!$(".validatephone").length > 0) {
		$(val).parent().append(validatePhoneHtml);
	}
	$.ajax({
		type : "POST",
		url : "../validate/forgotpassword/phone",
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
	var phone = $("input#phone").val();
	var smsCode = $("input#smsCode").val();
	$.ajax({
		type : "POST",
		url : "./forgotpassword/code",
		data : {
			phone : phone,
			smsCode : smsCode,
		},
		dataType : "text",
		success : function(res) {
			var result = res;
			switch (result) {
			case "success":
				window.location.href = "./toResetPassword";
				break;
			case "failed":
				toast("短信验证码有误", "showMessage");
				break;
			}
		},
		error : function(e) {
			alert("请求失败！");
			console.log(e);
		}
	});
}