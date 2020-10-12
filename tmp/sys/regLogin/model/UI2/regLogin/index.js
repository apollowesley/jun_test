define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");
	var ShellImpl = require('$UI/system/lib/portal/shellImpl');

	var Model = function() {
		this.callParent();
		var shellImpl = new ShellImpl(this, {
			"contentsXid" : "pages",
			"pageMappings" : {
				"main" : {
					url : require.toUrl('./login.w')
				},
				"registerMobile" : {
					url : require.toUrl('./registerMobile.w')
				},
				"registerEmail" : {
					url : require.toUrl('./registerEmail.w')
				},
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