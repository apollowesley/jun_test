define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");
	var utils = require("$UI/system/components/justep/common/utils");

	var Model = function() {
		this.callParent();
	};

	// 图片路径转换
	Model.prototype.getImageUrl = function(url) {
		return require.toUrl(url);
	};

	// 页面初始化
	Model.prototype.modelLoad = function(event) {
		$("input[xid=keyInput]", this.getRootNode()).val("请输入关键字...");
		$(".x-navbox .x-list", this.getRootNode()).css({"width":this.comp("navData").getCount()*54});
	};

	// 加载数据
	Model.prototype.productDataCustomRefresh = function(event) {
		var newsData = event.source;
		$.ajax({
			type : "GET",
			url : require.toUrl('./json/newsData.json'),
			dataType : 'json',
			async : false,
			cache : false,
			success : function(data) {
				newsData.loadData(data);// 将返回的数据加载到data组件
			},
			error : function() {
				throw justep.Error.create("加载数据失败");
			}
		});

	};
	Model.prototype.navDataCustomRefresh = function(event) {
		var navData = event.source;
		$.ajax({
			type : "GET",
			url : require.toUrl('./json/navData.json'),
			dataType : 'json',
			async : false,
			cache : false,
			success : function(data) {
				navData.loadData(data);// 将返回的数据加载到data组件
			},
			error : function() {
				throw justep.Error.create("加载数据失败");
			}
		});
	};
	
	Model.prototype.navLiClick = function(event){		 
		/*
		1、栏目单击里，加载对应content
		*/
		var data = this.comp("navData");
		var windowContainer1=this.comp("windowContainer1");		
		windowContainer1.set("src",require.toUrl(data.getValue("fUrl",data.getCurrentRow())));
		windowContainer1.refresh(this.comp("windowContainer1").get("src"));
		this.comp("popOver").hide();
	
		var index=data.getCurrentRowID();
		if(index-1>0){
			$(".x-navbox", this.getRootNode()).scrollLeft((index-1)*54);
		} else{
			$(".x-navbox", this.getRootNode()).scrollLeft(0);
		}
	};
	
	// 进入列表页
	Model.prototype.listClick = function(event) {
		justep.Shell.showPage("searchList");
	};

	// 进入内容页
	Model.prototype.detailClick = function(event) {
		justep.Shell.showPage("detail");
	};

	// 24小时要闻
	Model.prototype.importantBtnClick = function(event) {
		justep.Shell.showPage("important");
	};

	// 下滑显示搜索
	var startY;
	Model.prototype.navContent1Touchstart = function(event) {
		startY = event.originalEvent.changedTouches[0].pageY;
	};
	Model.prototype.navContent1Touchmove = function(event) {
		var moveEndY = event.originalEvent.changedTouches[0].pageY;
		var Y = moveEndY - startY;
		if (Y > 0) {
			$(".x-seach", this.getRootNode()).slideDown();
		}
	};

	Model.prototype.contents1ActiveChange = function(event){
		var to = event.to;
		if (to >= 1) {
			// 优化内存占用
			$('.x-window-container', this.comp('contents1').$domNode).css('display', 'none');
			this.comp('mainContainer' + (to + 1)).$domNode.css('display', 'block');
			this.comp('mainContainer' + (to + 1)).load();
		}
	};

	Model.prototype.newsContentActive = function(event){
		this.comp("windowContainer1").load(this.comp("windowContainer1").get("src"));
		$(this.comp("windowContainer1").domNode).show();
	};

	return Model;
});