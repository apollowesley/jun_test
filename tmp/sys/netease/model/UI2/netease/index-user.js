define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");

	
	var Model = function() {
		this.callParent();
	};

	// 图片路径转换
	Model.prototype.getImageUrl = function(url) {
		return require.toUrl(url);
	};
    
    Model.prototype.switchText = function(){
        if(justep.Shell.userName.get()){
			return justep.Shell.userName.get();
		}else{
			return "立即登录"
		}
    
    }
    
	
	Model.prototype.switchBtnClick = function(event) {

		if(justep.Shell.userName.get()){
			justep.Shell.showPage("personal");
		}else{
			justep.Shell.showPage("login");
		}
		
	};
	Model.prototype.showPersional = function(event) {
		justep.Shell.showPage("personal");
	};

	Model.prototype.modelLoad = function(event) {

	};

	Model.prototype.modelUnLoad = function(event) {
		// justep.Shell.off();

	};



	return Model;
});