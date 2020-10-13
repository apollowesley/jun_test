var pager_options = {
				bootstrapMajorVersion: 3,
                currentPage: 1,
                totalPages: 10,
				numberOfPages: 5,
				itemTexts: function (type, page, current) {
                    switch (type) {
                    case "first":
                        return "&laquo;";
                    case "prev":
                        return "&lsaquo;";
                    case "next":
                        return "&rsaquo;";
                    case "last":
                        return "&raquo;";
                    case "page":
                        return page;
                    }
                },
				tooltipTitles: function (type, page, current) {
                    switch (type) {
                    case "first":
                        return "首页";
                    case "prev":
                        return "上一页";
                    case "next":
                        return "下一页";
                    case "last":
                        return "尾页";
                    case "page":
                        return "第" + page + "页";
                    }
                },
				itemContainerClass: function (type, page, current) {
                	return (page === current) ? "active" : "site pointer-cursor";
            	},
				pageUrl: function(type, page, current){
					if (page !== current) {
                		return "#"+page;
					} else {
						return "javascript:;";
					}
            	}
            }
    

$(function(){
    $.scrollUp({
		scrollName:"scrollUp",
		topDistance:"300",
		topSpeed:300,
		animation:"fade",
		animationInSpeed:200,
		animationOutSpeed:200,
		scrollText:'<i class="fa fa-angle-up"></i>',
		scrollTitle:"返回顶部",
		activeOverlay:!1});
});

$(function(){
	$.pageBack({
		pageBackName:"page-back",
		pageBackText:'<i class="fa fa-angle-left"></i>',
		pageBackTitle:"返回上一页"
	});
});

$(function() {
	$("a#prev-apply-teachers").bind("click", function() {
		if ($("a#prev-apply-teachers").parent(".disabled").length <= 0) {	
			$("div#apply-teachers").children().fadeOut(function() {
				$(this).remove();
			});
		}
	});
	$("#next-apply-teachers").bind("click", function() {
		$("div#apply-teachers").children().remove();
		var data = [{"t_name":"闵老师","t_from":"赣南师范学院教育技术学专业"}, {"t_name":"刘老师","t_from":"赣南师范学院数学专业"}];
		var apply_teacher_tmpl = doT.template($("#apply-teacher-tmpl").text());
		$.each(data, function(idx, item) {
			$("div#apply-teachers").append(apply_teacher_tmpl(item));
		});
		$("div#apply-teachers").children().fadeIn();
		$("a#prev-apply-teachers").parent(".disabled").removeClass();
	});	
});

function getTotals(url) {
	$.get(url, function(data){
		return data;
	});
}

$(function() {
	var set_to_student = $("#set-to-student");
	if (set_to_student.length == 1) {
		set_to_student.bind("click", function() {
			$('#student-modal').modal('hide');
			window.location.href = "set-to-student.html";
		});
	}
	var set_to_parent = $("#set-to-parent");
	if (set_to_parent.length == 1) {
		set_to_parent.bind("click", function() {
			$("#parent-modal").modal("hide");
			window.location.href = "add-kid.html";
		});
	}
	var set_to_parent = $("#set-to-teacher");
	if (set_to_parent.length == 1) {
		set_to_parent.bind("click", function() {
			$("#teacher-modal").modal("hide");
			window.location.href = "set-to-teacher.html";
		});
	}
});

/**
 * 把表单中的input text, radio, checkbox, select, textarea的值取到，并且构造成json字符串
 * @param keys
 * @returns {String}
 */
function getJSONStr(keys) {
	var jsonStr = "{";
	$.each(keys, function(idx, key) {
		if (key.indexOf("select_") >= 0) {
			var value = $("#" + key).val();
			if (value == null) {
				value = "";
			}
			jsonStr = combineJSONStr(jsonStr, key, value);
		} else if (key.indexOf("radio_") >= 0) {
			var value = $("input:radio[name='" + key + "']:checked").val();
			jsonStr = combineJSONStr(jsonStr, key, value);
		} else if (key.indexOf("check_") >= 0) {
			var checked = $("input:checkbox[name='" + key +"']:checked");
			var values = "";
			$.each(checked, function(idx, checkedItem){
				var value = checkedItem.val();
				values = combineStr(originStr, value);
			});
			jsonStr = combineJSONStr(jsonStr, key, values);
		} else if (key.indexOf("text_") >= 0) {
			 var value = $("textarea[name='" + key + "']").val().replace(/\n/g, "<br />");
			 jsonStr = combineJSONStr(jsonStr, key, value);
	    } else {
			var value = $("input[name='" + key + "']").val();
			jsonStr = combineJSONStr(jsonStr, key, value)
		}
	});
	return jsonStr + "}";
}

/**
 * 将json字符串转化为json对象
 * @param keys
 * @returns
 */
function getJSONObj(keys) {
	return eval('(' + getJSONStr(keys) + ')');
}

/**
 * 将key-value组合成json字符串格式
 */
function combineJSONStr(originStr, key, value) {
	if (originStr == "{") {
		originStr = originStr + "'" + key + "':'" + value + "'";	
	} else {
		originStr += ",'" + key + "':'" + value + "'"; 	
	}
	return originStr;
}

/**
 *以逗号把多个字符串合成一个字符串
 */
function combineStr(originStr, str) {
	if (originStr == "") {
		originStr = str;
	} else {
		originStr += "," + str;	
	}
	return originStr;
}

$(function() {
	$("#admin-login").bind("click", function() {
		$.post("/youjiao/admin", getJSONObj(new Array("email", "password", "auth_code")), function(data) {
			if (data.result == "successed") {
				window.location.href = "/admin/tutorships";	
			} else if (data.result == "failed") {
				for (var key in data) {
					var value = data[key];
    				if (value != "undefined" && key != "result") {
						addErrorClass(key, value, true);
					}
				}
				removeErrorClass(key, true);
			}
		}, "json");
	});
});

$(function() {
	$("input").focus(function() {
		$(this).parent().parent().children("small[class='help-block']").remove();
	});
});

/**
 * 当后台验证表单不通过时，给表单增加错误显示样式
 * @param key
 * @param withInputGroup
 */
function addErrorClass(key, value, withInputGroup) {
	var small_help = "<small class='help-block'>"+ value + "</small>";
	var remove_span = '<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>';
	var hidden_status = '<span id="' + key + '_errorStatus" class="sr-only">(error)</span>';
	var input_aria = "aria-describedby" + key + "_errorStatus";
	var has_error = "has-error";
	if (key.indexOf("select_") >= 0) {
		
	} else if (key.indexOf("radio_") >= 0) {
		
	} else if (key.indexOf("check_") >= 0) {
		
	} else if (key.indexOf("text_") >= 0) {
		
	} else {
		var theInput = $("input[name='" + key + "']");
		var formGroupDiv;
		var colDiv;
		if (!withInputGroup) {
			formGroupDiv = theInput.parent().parent(); 
			colDiv = theInput.parent();
		} else {
			formGroupDiv = theInput.parent().parent().parent();
			colDiv = theInput.parent().parent();
		}
		colDiv.append(small_help);
		theInput.after(remove_span);
		theInput.after(hidden_status);
		theInput.attr(input_aria);
		formGroupDiv.addClass(has_error);
	}
}

/**
 * 移除单个input的错误样式
 * @param key
 * @param withInputGroup
 */
function removeErrorClass(key, withInputGroup) {
	var theInput = $("input[name='" + key + "']");
	var formGroupDiv;
	var colDiv;
	if (!withInputGroup) {
		formGroupDiv = theInput.parent().parent(); 
		colDiv = theInput.parent();
	} else {
		formGroupDiv = theInput.parent().parent().parent();
		colDiv = theInput.parent().parent();
	}
	colDiv.children("small[class='help-block']").remove();
	theInput.nextAll().remove();
	formGroupDiv.removeClass("has-error");
}

/**
 * 登录时更换验证码图片
 */
$(function() {
	$("#change-auth-code").click(function() {
		$(this).parent().prev("img").attr("src", "/youjiao/authcode?" + Math.random());	
	});
});

