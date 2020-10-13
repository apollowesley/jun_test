<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息管理系统</title>

<style>
.popBtn{width:18px; height:18px; cursor:pointer; float:right; margin-left:1px; margin-top:12px; display:inline; background:url(static/images/buttons.png) no-repeat;}
.popClose{margin-right:10px; width:15px; background-position:-39px 0}
.popClose:hover{background-position:-39px -20px}
.popShow{background-position:0px 0}
.popShow:hover{background-position:0px -20px}
.popHide{background-position:-19px 0}
.popHide:hover{background-position:-19px -20px}
</style>
<link type="image/x-icon" href="static/images/favicon.ico" rel="shortcut icon">
<link href="static/jquery/treeGrid/themes/gray/easyui.css"
	rel="stylesheet" type="text/css" />

<link href="static/dwz/themes/default/style.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="static/dwz/themes/css/core.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="static/dwz/themes/css/css_register_min.css" rel="stylesheet"
	type="text/css" media="screen" />
<link href="static/dwz/themes/css/print.css" rel="stylesheet"
	type="text/css" media="print" />
<link href="static/dwz/uploadify/css/uploadify.css" rel="stylesheet"
	type="text/css" media="screen" />

<script src="static/jquery/jquery-1.11.0.min.js" type="text/javascript"></script>
<script src="static/jquery/jquery-migrate-1.0.0.min.js"
	type="text/javascript"></script>

<link href="static/styles/panel_item.css" rel="stylesheet"
	type="text/css" />
<link href="static/jquery/gridly/jquery.gridly.css" rel="stylesheet"
	type="text/css" media="screen" />
<script src="static/jquery/gridly/jquery.gridly.js"
	type="text/javascript"></script>
<script src="static/jquery/gridly/jquery.json-2.4.js"
	type="text/javascript"></script>
<script src="static/layer/layer.js"
	type="text/javascript"></script>	

<!-- treetable begin -->

<script src="static/jquery/treeGrid/jquery.easyui.min.js"></script>


<script src="static/jquery/treetable/jquery.treetable.js"></script>
<script src="static/jquery/treetable/jquery-ui.js"></script>

<!-- treetable end -->
<script src="static/dwz/js/jquery.cookie.js" type="text/javascript"></script>
<script src="static/dwz/js/jquery.validate.js" type="text/javascript"></script>
<script src="static/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="static/dwz/xheditor/xheditor-1.2.1.min.js"
	type="text/javascript"></script>
<script src="static/dwz/xheditor/xheditor_lang/zh-cn.js"
	type="text/javascript"></script>
<script src="static/dwz/uploadify/scripts/jquery.uploadify.js"
	type="text/javascript"></script>


<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
<script type="text/javascript" src="static/dwz/chart/raphael.js"></script>
<script type="text/javascript" src="static/dwz/chart/g.raphael.js"></script>
<script type="text/javascript" src="static/dwz/chart/g.bar.js"></script>
<script type="text/javascript" src="static/dwz/chart/g.line.js"></script>
<script type="text/javascript" src="static/dwz/chart/g.pie.js"></script>
<script type="text/javascript" src="static/dwz/chart/g.dot.js"></script>

<script src="static/dwz/js/dwz.core.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.util.date.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.validate.method.js"
	type="text/javascript"></script>
<script src="static/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.drag.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.tree.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.accordion.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.ui.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.theme.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.navTab.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.tab.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.resize.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.dialog.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.stable.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.ajax.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.pagination.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.database.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.effects.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.panel.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.history.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.combox.js" type="text/javascript"></script>
<script src="static/dwz/js/dwz.print.js" type="text/javascript"></script>

<script src="static/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
<!-- 引入公共信息 -->
<link href="static/common/css/common.css" rel="stylesheet"
	type="text/css" />
<script src="static/common/js/common.js" type="text/javascript"></script>
<script src="static/common/js/autoComplete.js" type="text/javascript"></script>

 <script src="static/table-drag/tablednd.js" type="text/javascript"></script>
<!-- 多选带回 -->
<script src="static/js/bring.js" type="text/javascript"></script>
<script type="text/javascript">
	//消息中心轮询
	var startMessage;
	$(function() {
		$.ajax({
			type : "GET",
			url : "sysMenu/getLoginUser?random=" + Math.random(),
			async : false,
			success : function(result) {
				$(".nav").html(result);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alertMsg.error(errorThrown + XMLHttpRequest + textStatus);
			}
		});
		//开始消息轮询
		$.ajax({
			type : "GET",
			url : "message/getMessageSendStatus?random=" + Math.random(),
			async : true,
			success : function(data) {
				if (data == 0) {
					clearInterval(startMessage);
				} else if (data == 1) {
					//startMessage = setInterval("getMessage()",5000); 
				} else {
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				//alertMsg.error("服务器未响应");
			}
		});
		$.get("sysMenu/getRootMenu", function(result) {

			$(".accordion").html(result);
			DWZ.init("static/dwz/dwz.frag.xml", {
				loginUrl : "sysMenu/getRootMenu",
				loginTitle : "登录", // 弹出登录对话框
				//    		loginUrl:"login.html",	// 跳到登录页面
				statusCode : {
					ok : 200,
					error : 300,
					timeout : 301
				}, //【可选】
				pageInfo : {
					pageNum : "pageNum",
					numPerPage : "numPerPage",
					orderField : "orderField",
					orderDirection : "orderDirection"
				}, //【可选】
				debug : false, // 调试模式 【true|false】
				callback : function(data) {
					initEnv();
					$("#themeList").theme({
						themeBase : "static/dwz/themes"
					}); // themeBase 相对于index页面的主题base路径
				}
			});

		});
	});

	function dialogAjaxDone(json) {
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok) {
			if (json.navTabId) {
				navTab.reload(json.forwardUrl, {}, json.navTabId);
			} else {
				navTab.reload();
			}
			$.pdialog.closeCurrent();

		}
	}
	function navTabReload(json) {
		DWZ.ajaxDone(json);
		navTab.reload();
	}
	//处理点击ESC事件，关闭弹出窗口
	function onkeyESC(event) {
		if (event.keyCode == DWZ.keyCode.ESC) {
			//	alertMsg.correct("您修改的资料未保存，请选择保存或取消！");
			if ($.pdialog._current)
				$.pdialog.closeCurrent();
		}
	}

	/**
	 * 普通ajax表单提交
	 * @param {Array} items   批量操作的items集合
	 * @param {int} minWidth  项最小宽度  
	 * @param {int} minHeight 项最小高度
	 * @param {int} splitSize 项与项间隔
	 */
	function itemInitPositionAndSize(items, minWidth, minHeight, splitSize,
			callback) {
		var k = 0;
		items.each(function(event) {
			var position = $(this).attr("position");
			var size = $(this).attr("size");
			var widthCount, heightCount;
			if (position) {
				widthCount = parseFloat(size.split(",")[0]
						- position.split(",")[0]);
				heightCount = parseFloat(size.split(",")[1]
						- position.split(",")[1]);
			} else {
				widthCount = size.split(",")[0];
				heightCount = size.split(",")[1];
			}
			if (!widthCount || !heightCount) {
				return false;
			}
			var sumWidth = minWidth * widthCount;
			var sumHeight = minHeight * heightCount;
			//循环使用 *2 因为有0.5的数据
			heightCount = heightCount * 2;
			widthCount = widthCount * 2;
			for (var i = 3; i <= widthCount; i++) {
				sumWidth = sumWidth + (splitSize / 2);
			}
			for (var i = 3; i <= heightCount; i++) {
				sumHeight = sumHeight + (splitSize / 2);
			}
			$(this).css("width", sumWidth + "px");
			$(this).css("height", sumHeight + "px");
			k++;
		});

		var a = setInterval(function() {
			if (k == items.length) {
				window.clearInterval(a);
				callback();
			}
		}, 10);

	}
	function uploadController(id, type, val, submitId) {
		$("#" + id)
				.uploadify(
						{
							method : 'post',
							swf : '${ctx}/static/jquery/uploadify/uploadify.swf',
							uploader : 'upload/uploadify;jsessionid=${pageContext.session.id}',
							fileObjName : 'file', // The name of the file object to use in your server-side script
							fileSizeLimit : '20MB', // The maximum size of an uploadable file in KB (Accepts units B KB MB GB if string, 0 for no limit)
							successTimeout : 0, // The number of seconds to wait for Flash to detect the server's response after the file has finished uploading
							removeCompleted : true, // Remove queue items from the queue when they are done uploading
							buttonText : '上传',
							height : 20,
							multi : false,
							onUploadProgress : function(file, bytesUploaded,
									bytesTotal, totalBytesUploaded,
									totalBytesTotal) {
								$('#' + submitId).attr("onclick",
										"return false;");
							},
							fileTypeDesc : '*.jpg;*.png;',
							fileTypeExts : '*.jpg;*.png;',
							width : 50,
							'onUploadSuccess' : function(event, response) {
								$('#' + submitId).removeAttr("onclick");
								if (type == "name") {
									$("input[name='" + val + "']")
											.val(response);
								} else if (type == "id") {
									$("#" + val).val(response);
								}
							},
							'onUploadError' : function(event, data, a, f, d, c,
									t) {
								$('#' + submitId).removeAttr("onclick");
								alert(event);
							}
						});
	}
</script>
</head>
<body scroll="no" onkeyup="onkeyESC(event)">
	<div id="layout">
		<div id="header">
			<div class="headerNav" style="text-align:center">
				<b style="color: white !important; font-size: 40px !important; font-family:LiSu;font-weight: bold !important; line-height: 50px !Important; vertical-align:middle;">信息管理系统</b>

				<ul class="nav">

				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div class="selected"></div></li>
				</ul>
			</div>
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse">
					<h2>菜单</h2>
					<div>收缩</div>
				</div>

				<div class="accordion" fillSpace="sidebar"></div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span
										class="home_icon">主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft tabsLeftDisabled">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight tabsRightDisabled">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="accountInfo">
							<p align="left">
								<span style="text-align: left; font-size: 16px">欢迎使用信息管理系统</span>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>

	<div id="footer">
		<SCRIPT LANGUAGE="JavaScript">
			var myDate = new Date();
			document.getElementById("footer").innerHTML = 'All Rights Reserved © 2000-'
					+ myDate.getFullYear();
		</script>
		毛毛科技
	</div>

</body>
</html>