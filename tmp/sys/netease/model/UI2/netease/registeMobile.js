define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");
	var Baas = justep.Baas;
	require("cordova!com.justep.cordova.plugin.security.mob");
	var Model = function() {
		this.callParent();

		this._deviceType = "pc"; // pc || wx || app
		this._userID = justep.UUID.createUUID();
		this._userDefaultName = "新用户";
		this._fPhoneNumber = "15010263286";
		this._userPasswd = "123456";
		this._userDefaultAddress = "北京市";
	};

	Model.prototype.sendsmsButton = function(event) {
		if (!navigator.mobsms) {
			justep.Util.hint("请安装最新版本(含插件)体验！");
			return;
		}
		;
		var phoneInput = this.comp("phonenumber").val();
		var reg = /^0?1[3|4|5|7|8][0-9]\d{8}$/;
		if (reg.test(phoneInput)) {
			this.comp("sendsmsbutton").mytimer();
			navigator.mobsms.send({
				"zone" : "86",
				"phoneNumber" : phoneInput
			}, function(info) {
			}, function(err) {
				justep.Util.hint(err);
			});
		} else {
			justep.Util.hint("手机号码不正确！")
		}
	};

	Model.prototype.modelLoad = function(event) {
		var self = this;
		var comp = this.comp("sendsmsbutton");
		Timmer.apply(comp, [ 60, "免费获取验证码", "重新发送" ]);

		document.addEventListener("deviceready", function() {
			self._userDefaultName = "App用户";
		}, false);

		if (justep.Browser.isX5App) {
			this._deviceType = "app";
		} else {
			this._deviceType = "web";
			window.navigator = window.navigator || {};
			window.navigator.mobsms = {
				init : function(appinfo) {
					alert("发送失败，只支持x5app");
				},
				send : function(appinfo) {
					alert("发送失败，只支持x5app");
				},
				verify : function(appinfo) {
					alert("无法验证，只支持x5app");
				}

			};
		}
		;

		this.comp('userData').filters.setVar("usera", this._userID);
		this.loadUserData();
	};

	// 依据 verify 返回值决定向Baas 保存用户。
	// 使用的是第一种方式。更安全的要求参见第二种方式
	// 第二种方式: http://wiki.mob.com/smssdk-service-verify/
	Model.prototype.verifyButton = function(event) {
		var self = this;

		var phoneInput = this.comp("phonenumber").val();
		var verifyCode = this.comp("verifyCode").val();
		var reg = /^0?1[3|4|5|7|8][0-9]\d{8}$/;
		var verifyCodeReg = /^\d{4}$/;

		if (verifyCodeReg.test(verifyCode) && reg.test(phoneInput)) {
			function success(info) {
				if (info.result == -1) {
					self._userID = phoneInput;

					justep.Shell.userType.set("ISM");
					justep.Shell.userName.set(self._userID);
					localStorage.removeItem("userUUID");

					var user = {};
					user.userid = self._userID;
					user.accountType = "ISM";
					user.name = self._userID || "NONAME";
					localStorage.setItem("userUUID", JSON.stringify(user));

					var userData = self.comp('userData');
					userData.setValue("fID", phoneInput);

					var reslut = userData.saveData();

					setTimeout(function() {
						justep.Shell.closePage();
					}, 3000);
				} else
					justep.Util.hint("验证失败：" + JSON.stringify(info), {
						"type" : "danger"
					});
			}
			;

			function fail(info) {
				alert("失败：" + JSON.stringify(info));
			}
			;

			this.verify({
				"zone" : "86",
				"phoneNumber" : phoneInput,
				"verificationCode" : verifyCode
			}, success, fail);
		} else {
			justep.Util.hint("验证码有误！")
		}
		;
	};

	Model.prototype.loadUserData = function() {
		var userData = this.comp("userData");
		if (!userData.loaded) {
			userData.refreshData();
			// 如果客户信息为空，新增客户信息
			if (userData.getCount() === 0) {
				this.comp("userData").newData({
					index : 0,
					defaultValues : [ {
						"fID" : this._userID,
						"passwd" : "",
						"fName" : this._userDefaultName,
						"fPhoneNumber" : this._fPhoneNumber,
						"fAddress" : this._userDefaultAddress
					} ]
				});
			}
		}
	};

	Model.prototype.verify = function(args, success, error) {
		navigator.mobsms.verify(args, success, error);
	};

	function Timmer(loopSecond, titile1, title2, lisentner) { // todo
		// 还要检查一个结果返回变量。
		this.loopSecond = loopSecond;
		this.waittime = loopSecond;
		var self = this;
		this.mytimer = function() {
			if (self.waittime <= 0) {
				self.set({
					"disabled" : false,
					"label" : titile1
				});
				this.waittime = loopSecond; // 可设 60秒
			} else {
				self.set({
					"disabled" : true,
					"label" : title2 + "(" + self.waittime + ")"
				});
				self.waittime--;
				setTimeout(function() {
					self.mytimer();
				}, 1000);
			}
		}

	}
	;

	Model.prototype.dbTestClick = function(event) {
		var self = this;
		var testData = this.comp("testData");
		testData.refreshData();
		testData.newData({
			index : 0,
			defaultValues : [ {
				"fID" : justep.UUID.createUUID(),
				"fType" : "ISM",
				"passwd" : "",
				"fName" : "test",
				"fPhoneNumber" : "123456789",
				"fAddress" : "test add"
			} ]
		});

		function onsuccess(info, phoneInput) { // for test
			if (info.result == -1) {
				self._userID = phoneInput;
				var userData = self.comp('testData');
				userData.setValue("fID", phoneInput);

				var reslut = userData.saveData();

			} else
				justep.Util.hint("验证失败：" + JSON.stringify(info), {
					"type" : "danger"
				});
		}
		;

		onsuccess({
			result : -1
		}, "18501978580");
	};

	return Model;
});