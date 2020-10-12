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
	
	//进入内容页
	Model.prototype.detailClick = function(event){
		justep.Shell.showPage("detail");
	};
	
	//页面初始化
	Model.prototype.modelLoad = function(event){
		$("input[xid=keyInput]",this.getRootNode()).val("请输入关键字...");
		
		var startY;
		$("body").on("touchstart",function(event){
			startY = event.originalEvent.changedTouches[0].pageY;			
		});
		$("body").on("touchmove",function(event){
			var moveEndY = event.originalEvent.changedTouches[0].pageY;
			var Y = moveEndY - startY;
			if(Y>0){
				$(".x-seach").slideDown();
			}
		});
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
	
	return Model;
});