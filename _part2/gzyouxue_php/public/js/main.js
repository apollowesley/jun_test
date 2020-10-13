/**
 * 全局分页设置参数
 */
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
    

/**
 * 返回顶部按钮
 */
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

/**
 * 返回上一页按钮
 */
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

/**
 * 当用户选择用户类型后，跳转到相应的页面进一点完善信息
 */
$(function() {
	var to_student = $("#to_student");
	if (to_student.length == 1) {
		to_student.bind("click", function() {
			$('#student-modal').modal('hide');
			window.location.href = "/user/normal/to_student.html";
		});
	}
	var to_parent = $("#to_parent");
	if (to_parent.length == 1) {
		to_parent.bind("click", function() {
			$("#parent-modal").modal("hide");
			window.location.href = "/user/normal/to_add_kid.html";
		});
	}
	var to_teacher = $("#to_teacher");
	if (to_teacher.length == 1) {
		to_teacher.bind("click", function() {
			$("#teacher-modal").modal("hide");
			window.location.href = "/user/normal/to_teacher.html";
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
			value = value == null || value == undefined ? "" : value;
			jsonStr = combineJSONStr(jsonStr, key, value);
		} else if (key.indexOf("radio_") >= 0) {
			var value = $("input:radio[name='" + key + "']:checked").val();
			value = value == undefined ? "" : value;
			jsonStr = combineJSONStr(jsonStr, key, value);
		} else if (key.indexOf("check_") >= 0) {
			var checked = $("input:checkbox[name='" + key +"']:checked");
			var values = "";
			$.each(checked, function(idx, checkedItem){
				var value = checkedItem.val();
				value = value == undefined ? "" : value;
				if (value != "") {
					values = combineStr(originStr, value);
				}
			});
			jsonStr = combineJSONStr(jsonStr, key, values);
		} else if (key.indexOf("text_") >= 0) {
			 var value = $("textarea[name='" + key + "']").val();
			 value = value == null || value == undefined ? "" : value.replace(/\n/g, "<br />");
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

/**
 * 当表单出现错误时，重新输入值时清除错误样式
 */
$(function() {
	$("input,textarea").keyup(function() {
		// $(this).parent().parent().children("small[class='help-block']").remove();
		var name = $(this).attr("name");
		name = name == undefined ? "" : name;
		removeErrorClass(name);
	});
});

/**
 * 给有错误的表单数据增加错误样式
 * @param keys
 */
function addAllErrorClass(data) {
	for (var key in data) {
		var value = data[key];
		if (value != "undefined" && key != "result") {
			addErrorClass(key, value);
		}
	}
}

/**
 * 当后台验证表单不通过时，给表单增加错误显示样式
 * @param key
 * @param withInputGroup
 */
function addErrorClass(key, value) {
	var small_help = "<small class='help-block'>"+ value + "</small>";
	var remove_span = '<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>';
	var hidden_status = '<span id="' + key + '_errorStatus" class="sr-only">(error)</span>';
	var attr_name = "aria-describedby";
	var attr_value = key + "_errorStatus";
	var has_feedback = "has-feedback";
	var has_error = "has-error";
	var formGroupDiv;
	var colDiv;
	if (key.indexOf("select_") >= 0) { // bootstrap-multiselect
		var theSelect = $("select[id='" + key +"']");
		formGroupDiv = theSelect.parent().parent();
		colDiv = theSelect.parent();
		colDiv.append(remove_span);
		colDiv.append(hidden_status);
		colDiv.attr(attr_name, attr_value);
		colDiv.append(small_help);
		colDiv.addClass(has_feedback);
		formGroupDiv.addClass(has_error);
	} else if (key.indexOf("radio_") >= 0 || key.indexOf("check_") >= 0) { // icheck
		var theCheckDiv = $("div[id='" + key + "']");
		formGroupDiv = theCheckDiv.parent().parent();
		colDiv = theCheckDiv.parent();
		colDiv.append(remove_span);
		colDiv.append(hidden_status);
		colDiv.attr(attr_name, attr_value);
		colDiv.append(small_help);
		colDiv.addClass(has_feedback);
		formGroupDiv.addClass(has_error);
	} else {
		var theInput = $("input[name='" + key + "'],textarea[name='" + key + "']"); // input text或textarea
		var withInputGroup = theInput.parent(".input-group").length > 0;
		if (!withInputGroup) {
			formGroupDiv = theInput.parent().parent(); 
			colDiv = theInput.parent();
			colDiv.addClass(has_feedback);
		} else {
			formGroupDiv = theInput.parent().parent().parent();
			colDiv = theInput.parent().parent();
		}
		theInput.after(remove_span);
		theInput.after(hidden_status);
		theInput.attr(attr_name, attr_value);
		colDiv.append(small_help);
		formGroupDiv.addClass(has_error);
	}
}

/**
 * 移除所有指定的input的错误样式
 * @param keys
 */
function removeAllErrorClass(keys) {
	for (key in keys) {
		removeErrorClass(keys[key]);
	}
}

/**
 * 移除单个input的错误样式
 * @param key
 * @param withInputGroup
 */
function removeErrorClass(key) {
	var small_help = "small[class='help-block']";
	var has_feedback = "has-feedback";
	var has_error = "has-error";
	var formGroupDiv;
	var colDiv;
	if (key.indexOf("select_") >= 0) { // bootstrap-multiselect
		var theSelect = $("select[id='" + key +"']");
		formGroupDiv = theSelect.parent().parent();
		colDiv = theSelect.parent();
		theSelect.next().nextAll().remove();
		colDiv.removeClass(has_feedback);
		formGroupDiv.removeClass(has_error);
	} else if (key.indexOf("radio_") >= 0 || key.indexOf("check_") >= 0) { // icheck
		var theCheckDiv = $("div[id='" + key + "']");
		formGroupDiv = theCheckDiv.parent().parent();
		colDiv = theCheckDiv.parent();
		theCheckDiv.nextAll().remove();
		colDiv.remove(has_feedback);
		formGroupDiv.removeClass(has_error);
	} else {
		var theInput = $("input[name='" + key + "'],textarea[name='" + key + "']"); // input text或textarea
		var withInputGroup = theInput.parent(".input-group").length > 0;
		if (!withInputGroup) {
			formGroupDiv = theInput.parent().parent(); 
			colDiv = theInput.parent();
			colDiv.removeClass(has_feedback);
		} else {
			formGroupDiv = theInput.parent().parent().parent();
			colDiv = theInput.parent().parent();
		}
		colDiv.children(small_help).remove();
		theInput.nextAll().remove();
		formGroupDiv.removeClass(has_error);
	}
}	

/**
 * 登录时更换验证码图片
 */
$(function() {
	$("#change-auth-code").click(function() {
		$(this).parent().prev("img").attr("src", "/authcode?" + Math.random());	
	});
});

/**
 * 结合bootstrap-multiselect设定多选select最多可以选择多少项
 */
function selectLimit(id, option, checked, limit) {
	// Get selected options.
    var selectedOptions = $('#' + id + ' option:selected');
    if (selectedOptions.length >= limit) {
        // Disable all other checkboxes.
        var nonSelectedOptions = $('#' + id + ' option').filter(function() {
            return !$(this).is(':selected');
        });
        var dropdown = $('#' + id).siblings('.multiselect-container');
        nonSelectedOptions.each(function() {
            var input = $('input[value="' + $(this).val() + '"]');
            input.prop('disabled', true);
            input.parent('li').addClass('disabled');
        });
    } else {
        // Enable all checkboxes.
        var dropdown = $('#' + id).siblings('.multiselect-container');
        $('#' + id + ' option').each(function() {
            var input = $('input[value="' + $(this).val() + '"]');
            input.prop('disabled', false);
            input.parent('li').addClass('disabled');
        });
    }
}

/**
 * 管理员登录
 */
$(function() {
	$("#admin-login").bind("click", function() {
		var name_array = new Array("email", "password", "auth_code");
		$.post("/admin/login", getJSONObj(name_array), function(data) {
			removeAllErrorClass(name_array);
			if (data.result == "successed") {
				window.location.href = "/admin/tutorships.html";	
			} else if (data.result == "failed") {
				addAllErrorClass(data);
			}
		}, "json");
	});
});

/**
 * 用户注册
 */
$(function() {
	$("#user_reg").bind("click", function() {
		var name_array = new Array("email", "password", "confirm_password");
		$.post("/user/register", getJSONObj(name_array), function(data) {
			removeAllErrorClass(name_array);
			if (data.result == "successed") {
				window.location.href = "/";	
			} else if (data.result == "failed") {
				addAllErrorClass(data);
			}
		}, "json");
	});
});

/**
 * 用户登录
 */
$(function() {
	$("#user_login").bind("click", function() {
		var name_array = new Array("email", "password");
		$.post("/user/login", getJSONObj(name_array), function(data) {
			removeAllErrorClass(name_array);
			if (data.result == "successed") {
				window.location.href = "/";	
			} else if (data.result == "failed") {
				addAllErrorClass(data);
			}
		}, "json");
	});
});

/**
 * 用户修改密码
 */
$(function() {
	$("#user_change_pwd").bind("click", function() {
		var name_array = new Array("old_password", "password", "confirm_password");
		$.post("/user/setting/password", getJSONObj(name_array), function(data) {
			removeAllErrorClass(name_array);
			if (data.result == "successed") {
				$("#change_pwd_success").removeClass("hidden");
				$("#change_pwd_success").fadeIn();
				setTimeout(function() {
					$("#change_pwd_success").fadeOut();
				}, 10000);
				$("#user_change_pwd_form")[0].reset(); // 密码修改成功后，重置表单避免连续提交
			} else if (data.result == "failed") {
				addAllErrorClass(data);
			}
		}, "json");
	});
});

/**
 * 用户添加孩子信息
 */
$(function() {
	$("#user_add_kid").bind("click", function() {
		var name_array = new Array("nickname", "real_name", "school", "radio_gender", "select_grade", "text_evaluation");
		$.post("/user/parents/do_add_kid", getJSONObj(name_array), function(data) {
			removeAllErrorClass(name_array);
			if (data.result == "successed") {
				window.location.href = "/user/parents/kids.html";
			} else if (data.result == "failed") {
				addAllErrorClass(data);
			}
		}, "json");
	});
});

/**
 * 用户删除孩子信息
 */
$(function() {
	$("a[id^='user_delete_kid']").each(function() {
		$(this).bind("click", function() {
			var uid = $(this).attr("data-id");
			$("#delete_kid_confirmation").modal("show");
			$("#delete_kid").bind("click", function() {
				$("#delete_kid_confirmation").modal("hide");
				$.post("/user/parents/delete_kid", {"uid":uid}, function(data) {
					if (data.result == "successed") {
						$("div[data-id='" + uid + "']").remove();
						$("#delete_kid_successed").modal("show");
					} else if (data.result == "failed") {
						$("#delete_kid_failed").modal("show");
					}
				}, "json");
			});
		});
	});
});

/**
 * 用户完善学生信息
 */
$(function() {
	$("#user_add_student_info").bind("click", function() {
		var name_array = new Array("school", "select_grade", "text_evaluation");
		$.post("/user/student/add_student", getJSONObj(name_array), function(data) {
			removeAllErrorClass(name_array);
			if (data.result == "successed") {
				window.location.href = "/user/student.html";
			} else if (data.result == "failed") {
				addAllErrorClass(data);
			}
		}, "json");
	});
});

/**
 * 用户完善教师信息
 */
$(function() {
	$("#user_add_teacher_info").bind("click", function() {
		var name_array = new Array("radio_t_type", "where_from", "select_speciality", "text_evaluation");
		$.post("/user/teacher/add_teacher", getJSONObj(name_array), function(data) {
			removeAllErrorClass(name_array);
			if (data.result == "successed") {
				window.location.href = "/user/teacher.html";
			} else if (data.result == "failed") {
				addAllErrorClass(data);
			}
		}, "json");
	});
});

/**
 * 保存普通用户类型的用户信息
 */
$(function() {
	$("#save_normal_info").bind("click", function() {
		var name_array = new Array("nickname", "real_name", "radio_gender", "location", "phone", "qq", 
				"weibo", "wechat", "my_email");
		$.post("/user/normal/save_normal", getJSONObj(name_array), function(data) {
			removeAllErrorClass(name_array);
			if (data.result == "successed") {
				window.location.href = "/user/normal.html";
			} else if (data.result == "failed") {
				addAllErrorClass(data);
			}
		}, "json");
	});
});

/**
 * 保存家长用户类型的用户信息
 */
$(function() {
	$("#save_parent_info").bind("click", function() {
		var name_array = new Array("nickname", "real_name", "radio_gender", "location", "phone", "qq", 
				"weibo", "wechat", "my_email");
		$.post("/user/parents/save_parent", getJSONObj(name_array), function(data) {
			removeAllErrorClass(name_array);
			if (data.result == "successed") {
				window.location.href = "/user/parents.html";
			} else if (data.result == "failed") {
				addAllErrorClass(data);
			}
		}, "json");
	});
});

/**
 * 保存学生用户类型的用户信息
 */
$(function() {
	$("#save_student_info").bind("click", function() {
		var name_array = new Array("nickname", "real_name", "radio_gender", "location", "phone", "qq", 
				"weibo", "wechat", "my_email", "school", "select_grade", "text_evaluation");
		$.post("/user/student/save_student", getJSONObj(name_array), function(data) {
			removeAllErrorClass(name_array);
			if (data.result == "successed") {
				window.location.href = "/user/student.html";
			} else if (data.result == "failed") {
				addAllErrorClass(data);
			}
		}, "json");
	});
});

/**
 * 保存教师用户类型的用户信息
 */
$(function() {
	$("#save_teacher_info").bind("click", function() {
		var name_array = new Array("nickname", "real_name", "radio_gender", "location", "phone", "qq", 
				"weibo", "wechat", "my_email", "radio_t_type", "where_from", "select_speciality", "text_evaluation");
		$.post("/user/teacher/save_teacher", getJSONObj(name_array), function(data) {
			removeAllErrorClass(name_array);
			if (data.result == "successed") {
				window.location.href = "/user/teacher.html";
			} else if (data.result == "failed") {
				addAllErrorClass(data);
			}
		}, "json");
	});
});

/**
 * 用户发布辅导信息
 */
$(function() {
	$("#user_add_tutorship").bind("click", function() {
		var name_array = new Array("subject", "hours", "days", "class_time", "start_time", "location", 
				"text_request", "text_remarks");
		$.post("/user/tutorship/do_add_tutorship", getJSONObj(name_array), function(data) {
			removeAllErrorClass(name_array);
			if (data.result == "successed") {
				window.location.href = "/user/tutorship.html";
			} else if (data.result == "failed") {
				addAllErrorClass(data);
			}
		}, "json");
	});
});

/**
 * 输入框自动获取焦点
 */
function inputFocus(name) {
	$("input[name='" + name + "']").focus();
}

