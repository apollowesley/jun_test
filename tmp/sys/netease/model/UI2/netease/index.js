define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");
	var ShellImpl = require('$UI/system/lib/portal/shellImpl');
	require("$UI/demo/netease/appVersionChecker");
	
	var Model = function() {
		this.callParent();
		var shellImpl = new ShellImpl(this, {
			"contentsXid" : "pages",
			"pageMappings" : {
				"main" : {
					url : require.toUrl('./index_main.w')
				},
				"list" : {
					url : require.toUrl('./list.w')
				},
				"detail" : {
					url : require.toUrl('./detail.w')
				},
				"post" : {
					url : require.toUrl('./post.w')
				},
				"login" : {
					url : require.toUrl('./login.w')
				},
				"registeMobile" : {
					url : require.toUrl('./registeMobile.w')
				},
				"registered" : {
					url : require.toUrl('./registered.w')
				},
				"important" : {
					url : require.toUrl('./important.w')
				},
				"personal" : {
					url : require.toUrl('./personal.w')
				}
			}
		});
	};

	Model.prototype.modelLoad = function(event) {
		justep.Shell.userType = justep.Bind.observable();
		justep.Shell.userName = justep.Bind.observable();
		var userLocal = (localStorage.getItem("userUUID") && JSON.parse(localStorage.getItem("userUUID"))) || null;
		if (userLocal) {
			justep.Shell.userName.set(userLocal.name || "UNKNOWN");
			justep.Shell.userType.set(userLocal.accountType || "UNKNOWN");
		} else {
		}
	};

	return Model;
});