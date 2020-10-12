function showQuestionTemplate(showMess) {
	var data = '';
	if (showMess == 'statistics') {
		data = {
			"isTypeNum" : true
		}
	}
	$.ajax({
		type : "POST",
		async : false,
		url : "QuestionTemplateAction_showQuestionTemplate",
		data : data,
		dataType : "json",
		success : function(msg) {
			if (msg == null || msg.length <= 0) {
				if (showMess == 'statistics') {
					$('#statistics').text("没有类容");
				} else {
					$('#content').text("没有类容");
				}
			} else {
				if (showMess == 'statistics') {
					for (i = 0; i < msg.questionTemplates.length; i++) {
						ergodicTemplates(msg.questionTemplates[i], 0, "");
					}
					$('#statistics').append(
							"<tr><td>一共</td><td>" + msg.all[0] + "</td><td>"
									+ msg.all[1] + "</td><td>" + msg.all[2]
									+ "</td></tr>");
				} else {
					for (i = 0; i < msg.questionTemplates.length; i++) {
						recursionTemplates(msg.questionTemplates[i], 0, "");
					}
				}
			}
		}
	});
}
function ergodicTemplates(template, level, mess) {
	var preLevel = "";
	for (var i = 0; i < level; i++) {
		preLevel += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	$('#statistics').append(getRows(template, preLevel));
	var childrens = template.childrens;
	if (childrens != null && childrens.length > 0) {
		for (var j = 0; j < childrens.length; j++) {
			arguments.callee(childrens[j], level + 1, mess);
		}
	}
}
function recursionTemplates(template, level, mess) {
	var preLevel = "";
	for (var i = 0; i < level; i++) {
		preLevel += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	$('#content').append(getTable(template, preLevel));
	var childrens = template.childrens;
	if (childrens != null && childrens.length > 0) {
		for (var j = 0; j < childrens.length; j++) {
			arguments.callee(childrens[j], level + 1, mess);
		}
	}
}

function getRows(template, preLevel) {
	var s = "&nbsp;";
	var event = "";
	var a = template.questionTypeNum[0];
	var b = template.questionTypeNum[1];
	var c = template.questionTypeNum[2];
	if (template.childrens != null && template.childrens.length > 0) {
		s = "class='icon-list-buttom'";
		event = "onclick=displayTemplate('" + template.id + "')";
		a = "";
		b = "";
		c = "";

	}
	mess = "<tr id='" + template.id + "'" + ">" + "<input type='hidden' id='"
			+ template.id + "show' value='false'>" + "<td>" + preLevel
			+ "<span id=" + template.id + "list " + s + event + "></span>"
			+ template.name + "</td>" + "<td>" + a + "</td>" + "<td>" + b
			+ "</td>" + "<td>" + c + "</td>" + "</tr>";
	return mess;
}

function getTable(template, position) {
	var s = "&nbsp;";
	var event = "";
	if (template.childrens != null && template.childrens.length > 0) {
		s = "class='icon-list-buttom'";
		event = "onclick=displayTemplate('" + template.id + "')";
	}
	mess = "<tr id='"
			+ template.id
			+ "'"
			+ ">"
			+ "<input type='hidden' id='"
			+ template.id
			+ "show' value='false'>"
			+ "<td>"
			+ position
			+ "<span id="
			+ template.id
			+ "list "
			+ s
			+ event
			+ "></span>"
			+ "<input type='checkbox' group='tid'>"
			+ template.name
			+ "</td>"
			+ "<td>"
			+ template.questionNum
			+ "</td>"
			+ "<td>"
			+ date2str(new Date(template.addTime), "yyyy-MM-d h:m:s")
			+ "</td>"
			+ "<td><a class='btnAdd' href='QuestionTemplateAction_toAddQuestionTemplatePage?pid="
			+ template.id
			+ "' target='dialog' rel='dlg_page2' width='550' height='300'><span>添加</span></a>"
			+ "<a title='修改'target='dialog' rel='dlg_page2' width='550' height='300' class='btnEdit' href='QuestionTemplateAction_toUpdateQuestionTemplatePage?id="
			+ template.id
			+ "'"
			+ ">修改</a>"
			+ "<a title='确定要删除该模板（以及其子模板）吗？' target='ajaxTodo' href='QuestionTemplateAction_doDeleteaQuestionTemplate?id="
			+ template.id + "' class='btnDel'>删除</a></td>" + "</tr>";
	return mess;
}

function displayTemplate(tid) {
	var Isshow = $("#" + tid + "show").val();
	if (Isshow == 'false') {
		$("#" + tid + "show").val(true);
		$("#" + tid + "list").removeClass().addClass("icon-list-buttom");
	} else {
		$("#" + tid + "show").val(false);
		$("#" + tid + "list").removeClass().addClass("icon-list-left");
	}
	$.ajax({
		type : "POST",
		async : false,
		url : "QuestionTemplateAction_displayTemplate",
		data : {
			"id" : tid,
		},
		dataType : "json",
		success : function(msg) {
			if (msg == null || msg.length <= 0) {
				$('#content').text("没有类容");
			} else {
				for (i = 0; i < msg.length; i++) {
					// alert(msg[i])
					if (Isshow == 'true') {
						$("#" + msg[i]).hide();
					} else {
						$("#" + msg[i]).show();
					}
				}
			}
		}
	});

}

function date2str(x, y) {
	var z = {
		y : x.getFullYear(),
		M : x.getMonth() + 1,
		d : x.getDate(),
		h : x.getHours(),
		m : x.getMinutes(),
		s : x.getSeconds()
	};
	return y.replace(/(y+|M+|d+|h+|m+|s+)/g, function(v) {
		return ((v.length > 1 ? "0" : "") + eval('z.' + v.slice(-1)))
				.slice(-(v.length > 2 ? v.length : 2))
	});
}
