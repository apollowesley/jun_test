define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");
	var Baas = justep.Baas;
	var Model = function() {
		this.callParent();
	};

	//获取邮箱验证码
	Model.prototype.sendsmsButton = function(event) {
		var email = this.comp("email").val();
		var reg = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
		if(!reg.test(email)){
			justep.Util.hint("邮箱格式不正确");
		}else{
			var me =this;
			Baas.sendRequest({
				"url" : "/justep/Register",
				"action" : "checkUsername",
				"params" : {
					param : email
				},
				"success" : function(data) {
					var num = data.count;
					if(num>0){
						justep.Util.hint("邮箱已存在");
					}else{
						me.sendCode();
					}
				},
				"error" : function() {	
				}
			});
		}
	};
	//发送验证码
	Model.prototype.sendCode = function(event) {
		var comp = this.comp("sendsmsbutton");
		Timmer.apply(comp, [ 60, "免费获取验证码", "重新发送" ]);
		var email = this.comp("email").val();
		var me = this;
		Baas.sendRequest({
			"url" : "/justep/Register",
			"action" : "sendEmail",
			"params" : {
				param : email
			},
			"success" : function(data) {
				var codes = data.code;
				localStorage.value = codes;
				me.comp("sendsmsbutton").mytimer();
			},
			"error" : function() {
			justep.Util.hint("请求失败");
			}
		});
	}

	//注册
	Model.prototype.verifyButton = function(event) {
		var userData = this.comp("userData");
		
		var email = this.comp("email").val();
		//获取发送的验证码
		var val = localStorage.getItem("value");
		//获取输入的验证码
		var verifyCode = this.comp("verifyCode").val();
		//获取密码
		var password = this.comp("password").val();
		var reg = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
		if(!reg.test(email)){
			justep.Util.hint("邮箱格式不正确");
		}else{
			if(val==verifyCode){
				userData.setValue("fPhoneNumber", email);
				userData.setValue("userPasswd", password);
				userData.saveData();
				justep.Util.hint("注册成功");
				justep.Shell.closePage();
			}else{
				justep.Util.hint("验证码不正确");
			}	
		}	
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
	};
	
	//保存数据事设置初始值
	Model.prototype.userDataAfterNew = function(event){
		var data = this.comp("userData");
		data.setValue("fID", justep.UUID.createUUID());
		data.setValue("fType", "邮箱");
		data.setValue("fName", "新用户");
		data.setValue("fAddress", "北京");
	};

	Model.prototype.modelLoad = function(event){
		var data = this.comp("userData");
		var row = data.getCurrentRow();
		if(!row){
			data.newData();
		}
	};

	return Model;
});