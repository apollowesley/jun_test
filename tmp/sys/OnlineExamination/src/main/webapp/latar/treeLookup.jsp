<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<
<script type="text/javascript">
<!--
	var tree='';
	$.ajax({
		type : "POST",
		async : false,
		url : "QuestionTemplateAction_showQuestionTemplate",
		dataType : "json",
		success : function(msg) {
			if (msg == null || msg.length <= 0) {
				$('#treeLookup').text("没有类容");
			} else {
				tree = "";
				for (i = 0; i < msg.questionTemplates.length; i++) {
					printTreeLookup(msg.questionTemplates[i]);
				}
				$('#treeLookup').html(tree);
			}
		}
	});
	
	function printTreeLookup(template) {
		var childrens = template.childrens;
		pram="href='javascript:'";
		if (childrens == null || childrens.length <= 0) {
			pram="href='javascript:' onclick='$.bringBack("+JSON.stringify(template)+")'";
		}
		tree+="<li><a "+pram+">"+template.name+"</a>";
		if (childrens != null && childrens.length > 0) {
			tree+="<ul>";
			for (var j = 0; j < childrens.length; j++) {
				arguments.callee(childrens[j]);
			}
			tree+="</ul>";
		}
		tree+="</li>";
	}
//-->
</script>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<ul class="tree treeFolder expand" id="treeLookup">
			<li><a href="javascript:">北京</a>
				<ul>
					<li><a href="javascript:" onclick="$.bringBack({id:1, districtName:'东城', cityName:'北京'})">东城</a></li>
					<li><a href="javascript:" onclick="$.bringBack({id:2, districtName:'西城', cityName:'北京'})">西城</a></li>
				</ul>
			</li>
		</ul>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
		</ul>
	</div>
</div>