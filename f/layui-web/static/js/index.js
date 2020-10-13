layui.define(['element', 'jquery', 'common'], function(exports) {
	"use strict";
	
	var $ = layui.$
	,element = layui.element
	,common = layui.common
	,mainDiv = '.layui-layout'
	,nav = '.layui-nav'
	,tab = '.layui-tab'
	,tabFilter = 'index-tab';

	var index = {
		init: function(){
			//导航事件绑定
			$(mainDiv).find(nav).find('a[index-url]').click(function(){
				var e = $(this)
				,text = e.text().trim()
				,indexUrl = e.attr('index-url');
				index.tabAdd(text, indexUrl);
				index.tabChange(text);
			})
			//index界面事件绑定
			$('*[index-event]').click(function(){
				var e = $(this), event = e.attr('index-event');
				index[event].call(e);
			})
		},
		//新增一个Tab项
		tabAdd: function(title, url) {
			var isAdd = true;
			$(tab).find('.layui-tab-title li').each(function(item,i){
				if(title == $(this).attr('lay-id').trim()){
					isAdd = false;
				}
			})
			if(isAdd){
				element.tabAdd(tabFilter, {
					title: title,
					content: '<iframe src="'+url+'" frameborder="0" class="index-iframe"></iframe>',
					id: title
				})
			}else{
				index.tabChange(title);
			}
		},
		//删除指定Tab项
		tabDelete: function(layId) {
			element.tabDelete(tabFilter, layId);
		},
		//切换到指定Tab项
		tabChange: function(layId) {
			element.tabChange(tabFilter, layId);
		},
		signout: function(){
			common.confirm('确定注销吗？', function(){
				window.location.replace('login.html');
			})
		}
	}
	index.init();
	
	exports('index', index);
});
