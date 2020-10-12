define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");
	require("cordova!com.justep.cordova.plugin.qq");
	require("cordova!com.justep.cordova.plugin.weibo");
	require("cordova!com.justep.cordova.plugin.weixin.v3");

	var Model = function() {
		this.callParent();
	};

	Model.prototype.logoutBtnClick = function(event) {
		var type = justep.Shell.userType.get();
		var name = justep.Shell.userName.get();
		if (!confirm("确定要注销" + type + "用户：" + name + "吗？")) {
			return;
		}
		if (navigator.QQ) {
			navigator.QQ.logout();
		}
		;
		if (navigator.Weibo) {
			navigator.Weibo.logout();
		}
		;
		if ("WX" == type) {

		}
		justep.Shell.userType.set("");
		justep.Shell.userName.set("");
		localStorage.removeItem("userUUID");
		justep.Util.hint("注销成功");
		setTimeout(function() {
			justep.Shell.showPage("main");
		}, 5000);
	};

	Model.prototype.modelLoad = function(event) {
	};
	return Model;
});
