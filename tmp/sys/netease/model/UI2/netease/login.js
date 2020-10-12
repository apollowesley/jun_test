define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");

	require("cordova!plugin.http.request");
	require("cordova!com.justep.cordova.plugin.qq");
	require("cordova!com.justep.cordova.plugin.weibo");
	require("cordova!com.justep.cordova.plugin.weixin.v3");

	// 仅作为示例用，如果正式开发，更好的方案参见takeout,从云服务器去请求微信
	var weixinApp = "wxc08fa78b0a117595";
	var weixinSecrt = "648dc0f1d889525095ed0cb2bea05720";

	var qqAppId = "1105114302";// demo QQ app id.

	var Model = function() {
		this.callParent();
	};

	// 图片路径转换
	Model.prototype.getImageUrl = function(url) {
		return require.toUrl(url);
	};

	// 进入注册页
	Model.prototype.registeredClick = function(event) {
		justep.Shell.showPage("registered");
	};

	Model.prototype.loginQQClick = function(event) {
		if (!navigator.QQ) {
			justep.Util.hint("请安装最新版本(含插件)体验！");
			return;
		}
		;
		var self = this;
		function success(info) {
			var user = {};
			user.userid = info.userid;
			user.accountType = "QQ";
			if (info.userid && info.access_token) {
				self.oathQQInfo(qqAppId, info.access_token, info.userid, function(name) {
					user.name = name || "NONAME";

					justep.Shell.userType.set(user.accountType);
					justep.Shell.userName.set(user.name);
					localStorage.setItem("userUUID", JSON.stringify(user));
					justep.Util.hint("登录成功");
					setTimeout(function() {
						justep.Shell.showPage("main");
					}, 3000);
				});

			}
		}
		;
		navigator.QQ.ssoLogin(success, function(info) {
			alert("登录失败");
		});

	};

	Model.prototype.loginWeiboClick = function(event) {
		if (!navigator.Weibo) {
			justep.Util.hint("请安装最新版本(含插件)体验！");
			return;
		}
		;

		var self = this;
		var onSuccess = function(info) {
			if (!info) {
				justep.Util.hint("登录失败 ", {
					"type" : "danger"
				});
				return;
			}
			var user = {};
			user.userid = info.id;
			user.accountType = "Weibo";
			user.name = info.name || "NONAME";

			justep.Shell.userType.set(user.accountType);
			justep.Shell.userName.set(user.name);
			localStorage.setItem("userUUID", JSON.stringify(user));
			justep.Util.hint("登录成功");

			setTimeout(function() {
				justep.Shell.showPage("main");
			}, 3000);

		}

		navigator.Weibo.ssoLogin(function(info) {
			self.oathWeiboInfo(info.access_token, info.userid, onSuccess);
		}, function(info) {
			alert("登录失败");
		});

	};

	Model.prototype.oathQQInfo = function(appid, token, openid, success, error) {
		var qqUrl = "https://graph.qq.com/user/get_user_info?" + "access_token=" + token + "&" + "oauth_consumer_key=" + appid + "&" + "openid=" + openid + "&format=json";
		var httpReq = new plugin.HttpRequest();
		httpReq.getJSON(qqUrl, function(status, data) {
			// 这里返回用户的信息
			success(data.nickname);

		});

	}

	Model.prototype.oathWeiboInfo = function(access_token, uid, onSuccess) {
		var url = "https://api.weibo.com/2/users/show.json?access_token=" + access_token + "&uid=" + uid;
		var httpReq = new plugin.HttpRequest();
		httpReq.getJSON(url, function(status, data) {
			onSuccess(data);
		});

	}

	Model.prototype.saveAccount = function(event) {

	}

	Model.prototype.weiboLoginBtn = function(event) {
		navigator.Weibo.ssoLogin(function(mess) {
			alert(JSON.stringify(mess));
		}, function(mess) {
			alert(JSON.stringify(mess));
		});

	};

	// x5 app
	Model.prototype.loginWeixinClicked = function(event) {
		var self = this;
		if (!navigator.weixin) {
			justep.Util.hint("请安装最新版本(含插件)体验！");
			return;
		}
		;

		var weixin = navigator.weixin;

		weixin.auth(function(info) {
			self.weixinCode2Token(info, self.authSuccess)
		},

		function(reason) {

			justep.Util.hint("登录失败: " + JSON.stringify(reason), {
				"type" : "danger"
			});
		});

	};

	Model.prototype.authSuccess = function(access_token, openid) {
		// https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
		if (!(access_token && openid)) {
			justep.Util.hint("登录失败:", {
				"type" : "danger"
			});
			return;
		}

		var url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid;
		var httpReq = new plugin.HttpRequest();

		httpReq.getJSON(url, function(status, data) {

			if (status) {
				justep.Util.hint("登录失败:", {
					"type" : "danger"
				});
			}
			var user = {};
			user.userid = data.openid;
			user.accountType = "WX";
			user.name = data.nickname || "NONAME";

			justep.Shell.userType.set(user.accountType);
			justep.Shell.userName.set(user.name);
			localStorage.setItem("userUUID", JSON.stringify(user));
			justep.Util.hint("登录成功");

			setTimeout(function() {
				justep.Shell.showPage("main");
			}, 3000);
			// 微信的返回内容
			// "openid":"OPENID",
			// "nickname":"NICKNAME",
			// "sex":1,
			// "province":"PROVINCE",
			// "city":"CITY",
			// "country":"COUNTRY",
			// "headimgurl":
			// "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
			// "privilege":[
			// "PRIVILEGE1",
			// "PRIVILEGE2"
			// ],
			// "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"

		});
	};

	Model.prototype.weixinCode2Token = function(info, authSuccess) {
		var code = info.code;
		var url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + weixinApp + "&secret=" + weixinSecrt + "&code=" + code + "&grant_type=authorization_code";

		var httpReq = new plugin.HttpRequest();
		httpReq.getJSON(url, function(status, data) {

			if (status) {
				justep.Util.hint("登录失败:", {
					"type" : "danger"
				});
				return;
			}
			var unionid = data.unionid || "none";
			var openid = data.openid || "none";
			var access_token = data.access_token;
			authSuccess(access_token, openid);

		});

	};

	Model.prototype.loginIsmBtn = function(event) {
		var phoneInput = this.comp("nameInput").val();
		var passwordInput = this.comp("passwordInput").val();
		var reg = /^0?1[3|4|5|7|8][0-9]\d{8}$/;
		if (!reg.test(phoneInput)) {
			return;
		}
		;

		var userData = this.comp("baasData1");
		userData.clear();
		userData.filters.setVar("userPhone", phoneInput);
		userData.filters.setVar("password", passwordInput);
		userData.refreshData();
		if (userData.count() > 0) {
			justep.Util.hint("登录成功");
			justep.Shell.userType.set("ISM");
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
			justep.Util.hint("用户或密码有误！", {
				"type" : "danger"
			});
		}

	};

	return Model;
});