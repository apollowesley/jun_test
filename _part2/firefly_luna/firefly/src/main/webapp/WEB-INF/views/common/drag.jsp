<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="_drag_page_id" value="<%=System.currentTimeMillis()%>"></c:set>
<style type="text/css">
.dragsplitbar {
	float: left;
	height: 480px;
	display: block;
	cursor: col-resize;
	left: 506px;
	top: 32px;
	width: 5px;
}
.dragsplitbarline {
	border-color: #c0c0c0;
	background: #CCC;
	height: 480px;
	display: none;
	width: 3px;
	border-style: solid;
	border-width: 1px;
	cursor: col-resize;
	position: absolute;
	top: 32px;
	left: 506px;
	z-index: 20;
}
</style>
<script type="text/javascript">
	$(function() {
		$splitBar_${_drag_page_id} = $(".dragsplitbar", navTab.getCurrentPanel());
		$panelPackSplitBarProxy_${_drag_page_id} = $(".dragsplitbarline", navTab.getCurrentPanel());
		$container_${_drag_page_id} = $(".tabsContent div:first-child", navTab.getCurrentPanel());
		$splitBar_${_drag_page_id}.unbind("mousedown");
		$splitBar_${_drag_page_id}.mousedown(function() {
			//左面导航宽度
			leftWidth = $("#sidebar").css("display") == "none" ? 14 : parseInt($("#sidebar").width());
			//拖动
			var dragFlag = true;
			var _x = parseInt(event.pageX);
			var disX = _x + leftWidth;
			setTimeout(function() {
				$panelPackSplitBarProxy_${_drag_page_id}.show();
			},
			100);
			//$(document).unbind("mousemove");
			//$(document).unbind("mouseup");
			$(document).mousemove(function(event) {
				if (!dragFlag) return;
				var newX = parseInt(event.pageX);
				var newdX = newX - leftWidth - 14;
				$panelPackSplitBarProxy_${_drag_page_id}.css("left", newdX + "px");
			});
			$(document).mouseup(function(event) {
				if (dragFlag) {
					dragFlag = false;
					var newX = parseInt(event.pageX);
					$container_${_drag_page_id}.css("width", (newX - leftWidth - 20) + "px");
					$panelPackSplitBarProxy_${_drag_page_id}.css("left", (newX - leftWidth - 14) + "px");
					$panelPackSplitBarProxy_${_drag_page_id}.hide();
					$("div.j-resizeGrid").each(function() {
						var width = $(this).innerWidth();
						if (width) {
							$("div.gridScroller", this).width(width + "px");
						}
					});
				}
			});
			return false;
		})
	})
</script>
<div class="dragsplitbar"></div>
<div class="dragsplitbarline"></div>