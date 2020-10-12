define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");

	var Model = function() {
		this.callParent();
	};
	
	Model.prototype.modelLoad = function(event){
		//删除模块
		$(".icon-android-close", this.getRootNode()).each(function(){
			$(this).on("click",function(){
				$(this).parent().parent().parent().remove();
			});
		});
		
		/*
		判断html标签是否有iosstatusbar,有测表示在苹果手机上运行，顶部需要留出20px，因此修改popover的top
		*/
		var src=$("html", this.getRootNode()).hasClass("iosstatusbar");
		if(src){
			$(".x-nav-guide", this.getRootNode()).css({"margin-top":"20px"});
		}	
	};
	
	//进入列表页
	Model.prototype.listClick = function(event){
		justep.Shell.showPage("list");
	};
	
	//图片路径转换
	Model.prototype.getImageUrl = function(url){
		return require.toUrl(url);
	};
		
	//进入内容页
	Model.prototype.detailClick = function(event){
		justep.Shell.showPage("detail");
	};
	
	//进入登录页
	Model.prototype.loginBtnClick = function(event){
		justep.Shell.showPage("login");
	};
	
	
	
	return Model;
});