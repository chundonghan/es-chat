function getId(s) {
	return document.getElementById(s);
}

function getHtml(s, html) {
	getId(s).innerHTML = html;
}
var toastTime = null;
var displayTime = null;

function setToast(html,s) {
	if(toastTime != null) {
		clearTimeout(toastTime);
		clearTimeout(displayTime);
	}
	getId(s).style.display = 'block';
	getId(s).style.opacity = 1;
	getHtml(s, html);
	toastTime = setTimeout(function() {
		getId(s).style.opacity = 0;
		displayTime = setTimeout(function() {
			getId(s).style.display = 'none';
		}, 1500);
	}, 1500);
}

function toast(msg,s) {
		setToast('<div style="color:#fff;background: rgba(0, 0, 0, 0.7);border-radius: 0.2em;padding: 0.2em;text-align: center;margin: 0 auto;height:2em;line-height:2em;">'+msg+'</div>',s);
}