<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
    // var tab;
	west = function(){
		debugger;
		var west_this=this;
		var navList=[
					{title:'基础配置',content:'',iconCls:'icon-folder',selected:'false'},
					{title:'部门管理',content:'',iconCls:'icon-folder',selected:'false'},
				    {title:'订单管理',content:'',iconCls:'icon-folder',selected:'false'}];
		west_this.init=function(){
			//必须配置这个才能显示
			$("#west_nav").accordion({animate:true});
			debugger;
			var defalutUrl= 'views/jsp/config/config.jsp';
			$.each(navList, function(index, row) {
				debugger;
				if ('基础配置'==row.title) {
					var tab={title:'阿里云服务器配置',content:'<iframe src="'
						+ defalutUrl
						+ '" frameborder="0" style="border:0;width:100%;height:100%;"></iframe>',
							closable:true,tools:[{
								iconCls:'icon-mini-refresh',handler:function(){
								alert('refresh');
								}
							}]
						};
					row.content="<ul><li><a href='#''  onclick='centerObject.addTabs("+JSON.stringify(tab)+")'>阿里云服务器配置</a></li></ul>";
				}
				$('#west_nav').accordion('add', {
					title: row.title,
					content: row.content,
					selected: row.selected,
					iconCls:row.iconCls,
				});
			});
		}

	};
	var westObject=new west();

	$(function(){
		debugger;
		$.messager.progress({text:'导航功能正在加载中...'});
		westObject.init();
		$.messager.progress('close');

	});

</script>


<div id="west_nav" class="easyui-accordion" >
	<!--显示导航栏内容 -->
</div>