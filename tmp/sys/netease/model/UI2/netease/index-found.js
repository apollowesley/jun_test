define(function(require) {
	var $ = require("jquery");
	var justep = require("$UI/system/lib/justep");

	var Model = function() {
		this.callParent();
	};
	
	//加载数据
	Model.prototype.foundDataCustomRefresh = function(event){
		var foundData = event.source;
        $.ajax({
            type: "GET",
            url: require.toUrl('./json/foundData.json'),
            dataType: 'json',
            async: false,
            cache: false,
            success: function(data){
            	foundData.loadData(data);//将返回的数据加载到data组件
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
		
	//进入列表页
	Model.prototype.listClick = function(event){
		justep.Shell.showPage("list");
	};
	/*	
	Model.prototype.getLeftImageUrl = function(row) {
		var url = row.val("fImg");
		if (row == row.data.getCurrentRow()) {
			url = url.replace(".png", "-1.png");
		}
		return require.toUrl(url);
	};
	
	Model.prototype.getImageUrl = function(url) {
		return require.toUrl(url);
	};
	*/
	return Model;
});