define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");
	
	var Model = function() {
		this.callParent();
	};

	// 进入注册页 
	Model.prototype.registeredClick = function(event) {
		var demo = $(event.target).attr("url");
		justep.Shell.showPage(demo);
	};


	Model.prototype.loginIsmBtn = function(event) {
		var phoneInput = this.comp("nameInput").val();
		var passwordInput = this.comp("passwordInput").val();

		var userData = this.comp("baasData1");
		userData.clear();
		userData.filters.setVar("userPhone", phoneInput);
		userData.filters.setVar("password", passwordInput);
		userData.refreshData();
		if (userData.count()> 0) {
			justep.Util.hint("登录成功");
			justep.Shell.userName.set(phoneInput);
			localStorage.removeItem("userUUID");

			var user = {};
			user.userid = phoneInput;
			user.accountType = "ISM";
			user.name = phoneInput || "NONAME";
			localStorage.setItem("userUUID", JSON.stringify(user));

			setTimeout(function() {
				justep.Shell.closePage();
			}, 3000);
		} else {
			justep.Util.hint("用户名或密码有误！", {
				"type" : "danger"
			});
		}

	};

	return Model;
});