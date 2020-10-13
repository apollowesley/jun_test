<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<jsp:include page="../easyui_lib.jsp"></jsp:include>
</head>
<body>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:30px;padding:5px;background:#B3DFDA;">
		<jsp:include page="common/header.jsp"></jsp:include>
	</div>
	<div data-options="region:'west',split:true,title:'菜单'" style="width:150px;padding:10px;">
		<jsp:include page="common/menu.jsp"></jsp:include>
	</div>
	<div data-options="region:'center'">
		<div id="mainTab" class="easyui-tabs" data-options="fit:true,border:false">
			<div title="首页" style="padding:10px">
				<p style="font-size:14px">jQuery EasyUI framework help you build your web page easily.</p>
				<ul>
					<li>easyui is a collection of user-interface plugin based on jQuery.</li>
					<li>easyui provides essential functionality for building modem, interactive, javascript applications.</li>
					<li>using easyui you don't need to write many javascript code, you usually defines user-interface by writing some HTML markup.</li>
					<li>complete framework for HTML5 web page.</li>
					<li>easyui save your time and scales while developing your products.</li>
					<li>easyui is very easy but powerful.</li>
				</ul>
			</div>
		</div>
	</div>
<!-- 	<div data-options="region:'south',border:false" style="height:60px;"> -->
<%-- 		<jsp:include page="common/footer.jsp"></jsp:include> --%>
<!-- 	</div> -->
	
	<div id="tabsMenu" class="easyui-menu" style="width:120px;">
	  <div name="close">关闭</div>  
	  <div name="Other">关闭其他</div>  
	  <div name="All">关闭所有</div>
	</div>  
	
<script type="text/javascript">
//绑定tabs的右键菜单
$("#mainTab").tabs({
    onContextMenu : function (e, title) {
        e.preventDefault();
        $('#tabsMenu').menu('show', {
            left : e.pageX,
            top : e.pageY
        }).data("tabTitle", title);
    }
});

	//实例化menu的onClick事件
$("#tabsMenu").menu({
    onClick : function (item) {
        closeTab(this, item.name);
    }
});

	//几个关闭事件的实现
function closeTab(menu, type) {
    var curTabTitle = $(menu).data("tabTitle");
    var tabs = $("#mainTab");
    
    if (type === "close") {
        tabs.tabs("close", curTabTitle);
        return;
    }
    
    var allTabs = tabs.tabs("tabs");
    var closeTabsTitle = [];
    
    $.each(allTabs, function () {
        var opt = $(this).panel("options");
        if (opt.closable && opt.title != curTabTitle && type === "Other") {
            closeTabsTitle.push(opt.title);
        } else if (opt.closable && type === "All") {
            closeTabsTitle.push(opt.title);
        }
    });
    
    for (var i = 0; i < closeTabsTitle.length; i++) {
        tabs.tabs("close", closeTabsTitle[i]);
    }
}
</script>
</body>
</html>