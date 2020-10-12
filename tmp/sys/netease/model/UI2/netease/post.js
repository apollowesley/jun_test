define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");

	var Model = function() {
		this.callParent();
	};
	
	//图片路径转换
	Model.prototype.getImageUrl = function(url){
		return require.toUrl(url);
	};
	
	//加载数据
	Model.prototype.postDataCustomRefresh = function(event){
		var postData = event.source;
        $.ajax({
            type: "GET",
            url: require.toUrl('./json/postData.json'),
            dataType: 'json',
            async: false,
            cache: false,
            success: function(data){
            	postData.loadData(data);//将返回的数据加载到data组件
            },
            error: function(){
              throw justep.Error.create("加载数据失败");
            }
        });	
	};
	/*
	//内容隐藏显示
	$(".hiddenBtn").each(function(){
		var height=0;
		$(this).on("click",function(){
			var obj=$(this).parent().find(".features");	
			if(obj.height()<=105){
				height=obj.outerHeight(true);
				obj.css("height","auto");
				$(this).find("i").attr("class","icon-chevron-up");
			} else {
				obj.css("height",height);
				$(this).find("i").attr("class","icon-chevron-down");
			}
		});
	});
	*/
		
	//页脚文本框宽度
	Model.prototype.modelLoad = function(event){
		var width=$(window).width()-100;
		$(".form-control", this.getRootNode()).css({"width":width});
		$(".x-inputText", this.getRootNode()).val("写跟贴");
	};
	
	//功能目录
	Model.prototype.popMenuBtnClick = function(event){
		this.comp("popMenu").show();
	};
	
	//打开跟帖页
	Model.prototype.postClick = function(event){
		justep.Shell.showPage("post");
	};
	
	return Model;
});