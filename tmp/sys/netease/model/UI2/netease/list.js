define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");
	
	var Model = function() {
		this.callParent();
	};	

	 //首页
	Model.prototype.homeBtnClick = function(event){
		justep.Shell.showPage("main");
	};
	
	//加载数据
	Model.prototype.productDataCustomRefresh = function(event){
		var newsData = event.source;
        $.ajax({
            type: "GET",
            url: require.toUrl('./json/newsData.json'),
            dataType: 'json',
            async: false,
            cache: false,
            success: function(data){
            	newsData.loadData(data);//将返回的数据加载到data组件
            },
            error: function(){
              throw justep.Error.create("加载数据失败");
            }
        });
	};
	
	//图片路径转换
	Model.prototype.getImageUrl = function(url){
		return require.toUrl(url);
	};
	
	//搜索按钮
	Model.prototype.seachBtnClick = function(event){
		/*
		根据输入的关键字查询数据
		 */
	};
	
	//进入内容页
	Model.prototype.detailClick = function(event){
		justep.Shell.showPage("detail");
	};

	
	return Model;
});