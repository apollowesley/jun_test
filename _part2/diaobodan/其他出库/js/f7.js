var EAS_F7 = function(){
	this.title = "用户F7";
	this.data_url='/extapiserver/getbaselist';
	this.data_key='t_pm_user';
	this.data_id='FID';
	this.data_display="fname_l2";
	this.data_filter="";
	this.isAutoFetchData = false;
	this.callback_func = function(isCancel,selectedObject){
		
	};

	EAS_F7.prototype.showF7Selector=function(){
		var html = "<div id='F7_totalDiv1' class='F7_MaskMain'></div><div      class='F7_mainDiv' id='F7_totalDiv2'>";
			html = html +"<div class='F7_Title'> <button id='F7_Close' type='button' class='close' data-dismiss='modal' aria-hidden='true' style='padding-right:15px;'>×</button><h3>&nbsp;&nbsp;&nbsp;"+this.title+"</h3></div>"
			html = html+"<div class='F7_SearchDiv'>";
			html = html +"<input id='f7_searchtext' type='text' class='searchtext' placeHolder='请输入查询条件' />";
			html = html +"<button id='f7_searchbtn' type='button' class='btn searchbtn' >查询</button>";
			html = html +"</div>";
			html = html +"<div id='f7_resultdiv'  class='F7_ResultDiv'>";
			html = html +"</div>";
			html = html +"</div>";
		$(html).appendTo($('body'));
		$("body").addClass("body-overflow-hidden");
		$('#f7_searchbtn').unbind('click');
		$('#f7_searchbtn').bind("click",{that:this},this.actionSearch);
		$('#F7_Close').unbind('click');
		$('#F7_Close').bind("click",{that:this},function(event){
			$("body").removeClass("body-overflow-hidden");
			$('#F7_totalDiv1').remove();
			$('#F7_totalDiv2').remove();
		});
		if (this.isAutoFetchData){
			var event = {};
			event.data = {};
			event.data.that = this;
			this.actionSearch(event);
		}
	};
			
	EAS_F7.prototype.actionSearch=function(event){
		var that = event.data.that;
		var searchkey = $('#f7_searchtext')[0].value;
		var searchFilter = '';
		if (searchkey.length >0){
			searchFilter = " (a.fname_l2 like '%" +searchkey+ "%'  or a.FNumber like '%"+searchkey +"%' ) ";
		}
		if (that.data_filter != null 
				&& that.data_filter != undefined 
				&& that.data_filter.length >0){
			if (searchFilter.length==0){
				searchFilter = that.data_filter;
			}else{
				searchFilter = "(" +searchFilter+") and (" +that.data_filter+")";
			}
		}
		_loading.start();
		$.post(BaseApp.base_url+that.data_url,{
					tablename:that.data_key,
					idfield:that.data_id,
					displayfield:that.data_display,
					filter:searchFilter,
					t:BaseApp.token
				},
				function(responseData){that.actionSearch_Callback(that,responseData);}
		);
	};

	EAS_F7.prototype.init=function(){
		if($('link[href$="f7.css"]').length == 0)
			$('<link href="./css/f7.css" rel="stylesheet"/>').appendTo($('head'));
		if($('link[href$="bootstrap.css"]').length == 0)
			$('<link href="./css/bootstrap.css" rel="stylesheet"/>').appendTo($('head'));
	};

	EAS_F7.prototype.actionSearch_Callback=function(that,responseData){
		_loading.end();
		if (responseData.code == 0 ){
			var totalHtml = "<ul class='' style='margin:0 0 0 0;'>";
			for(var  i= 0;i<responseData.data.length;i++){
				var itemHtml = "<li class='list-group-item f7_resultdiv_li' value='" +responseData.data[i].id+ "'>";
					itemHtml = itemHtml +responseData.data[i].displayname;
					itemHtml = itemHtml+"</li>";
				totalHtml =totalHtml +itemHtml;
			}
			
			totalHtml = totalHtml+"</ul>";
			if (responseData.data.length >0){
				$('#f7_resultdiv').html(totalHtml);
				$('.f7_resultdiv_li').bind('click',{that:that},that.click_item);
			}else{
				$('#f7_resultdiv').html('没有查询到数据');
			}
		}else{
			$('#f7_resultdiv').html("发生错误:"+responseData.msg);
		}
	};

	EAS_F7.prototype.click_item =function(obj){
		var selectedID = obj.target.getAttribute("value");
		var selectedName = obj.target.textContent;
		var that = obj.data.that;
		that.callback_func(false,{id:selectedID,value:selectedName});
		$("body").removeClass("body-overflow-hidden");
		$('#F7_totalDiv1').remove();
		$('#F7_totalDiv2').remove();
	};
}


	