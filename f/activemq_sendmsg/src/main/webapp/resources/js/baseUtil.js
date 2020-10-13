function openWindows(title,url,data,yesFunction,showSubmitBtn){
	var loadIndex = loadMsg();
	$.ajax({
		type:"post",
		url:url,
		data:data,
		dataType:"html",
		success:function(result){
			closeMsg(loadIndex);
			ZENG.msgbox._hide();
			//只显示取消按钮
			if(!showSubmitBtn && showSubmitBtn != undefined){
				layer.open({
					type:1,
					title:title,
					content:result,
					area: ['auto',$(window).height() - 100],
					btn: ["取消"],
					btn1: function(index){ 
						layer.close(index);
					}
				});
			}else{//全部显示
				layer.open({
					type:1,
					title:title,
					content:result,
					area: ['auto',$(window).height() - 100],
					btn: ["提交","取消"],
					btn1: function(index, layero){
						yesFunction(index,layero);
					},
					btn2: function(index){ 
						layer.close(index);
					}
				});
			}
		},error:function(){
			closeMsg(loadIndex);
			layer.msg('系统异常,请重试', {shade: [0.5,'#000']});
		}
	})
}
function formValidate(formId,r,m){
 $(formId).validate({
	success : function(a) {
		if(a.closest(".validate-form-group").length>0){
			a.closest(".input-group").parent().removeClass("has-error").addClass("has-success");
		}else{
			a.closest(".form-group").removeClass("has-error").addClass("has-success");
		}
	},
	highlight: function (element) { // hightlight error inputs
		if($(element).closest(".validate-form-group").length>0){
			$(element).closest('.input-group').parent().removeClass('has-success').addClass('has-error'); // set error class to the control group
		}else{
			$(element).closest('.form-group').removeClass('has-success').addClass('has-error'); // set error class to the control group
		}
        
    },
    unhighlight: function (element) { // revert the change dony by hightlight
    	if($(element).closest(".validate-form-group").length>0){
			$(element).closest('.input-group').parent().removeClass('has-error'); // set error class to the control group
		}else{
			 $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
		}
    },
	errorClass : "help-block m-b-none",
    errorElement: 'span',
    errorPlacement: function(error, element) {
    	if($(element).closest(".validate-form-group").length>0){
    		$(error).appendTo( $(element).parent().parent());
    	}else{
    		$(error).appendTo($(element).parent());
    	}
    },
    focusInvalid: false, // 提交表单后，未通过验证的表单（第一个或提交之前获得焦点的未通过验证的表单）会获得焦点。
    focusCleanup:true,//如果是 true 那么当未通过验证的元素获得焦点时，移除错误提示。避免和 focusInvalid 一起用。
//    onclick:true,//在点击复选框和单选按钮时验证。
//    onfocusout:true,//失去焦点时验证（不包括复选框/单选按钮）。
//    onsubmit:true,//提交时验证。设置为 false 就用其他方法去验证。
//    onkeyup:true,//在 keyup 时验证。
    ignore: "",
    rules: r,
    messages:m
});
}
function alertMsg(msg,i){
	var icon="";
	if(i==0){
		icon="<i class='fa fa-remove' style='color:red;vertical-align:middle;font-size:30px;'></i>&nbsp;";
	}else if(i==1){
		icon="<i class='fa fa-check' style='color:green;vertical-align:middle;font-size:30px;'></i>&nbsp;";
	}
	msg="<div style='padding:20px 15px 10px 15px;fon-size:20px;'>"+icon+msg+"</div>";
	layer.open({type:1,title:false,shade:[0.5,"#000"],content:msg,btn:["确定"],yes:function(index){layer.close(index)}});
}
function loadMsg(){
	return layer.load(0, {shade: [0.5,'#000']});
}
function closeMsg(index){
	layer.close(index);
}
function closeAllMsg(){
	layer.closeAll();
}
function refeshJmenu(id,title,daaa) {
    var t = $('.J_iframe[data-id="' +id+ '"]');
    if(t.length<=0){
    	return openJmenu(id,title,daaa);
    }else{
    	var sss = $('.J_menuTab[data-id="' +id+ '"]');
    	sss.click();
    }
    t.html("数据加载中......")
    $.ajax({
    	type:"post",
    	url:id,
    	data:(!daaa)?{}:daaa,
    	dataType:"html",
    	success:function(data){
    		t.html(data)
    	},error:function(){
    		t.text("数据加载出错");
    	}
    })
}
function closeJmenu() {
    var t = $(this).parents(".J_menuTab").data("id"),
    a = $(this).parents(".J_menuTab").width();
    if ($(this).parents(".J_menuTab").hasClass("active")) {
        if ($(this).parents(".J_menuTab").next(".J_menuTab").size()) {
            var i = $(this).parents(".J_menuTab").next(".J_menuTab:eq(0)").data("id");
            $(this).parents(".J_menuTab").next(".J_menuTab:eq(0)").addClass("active"),
            $(".J_mainContent .J_iframe").each(function() {
                return $(this).data("id") == i ? ($(this).show().siblings(".J_iframe").hide(), !1) : void 0
            });
            var n = parseInt($(".page-tabs-content").css("margin-left"));
            0 > n && $(".page-tabs-content").animate({
                marginLeft: n + a + "px"
            },
            "fast"),
            $(this).parents(".J_menuTab").remove(),
            $(".J_mainContent .J_iframe").each(function() {
                return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
            })
        }
        if ($(this).parents(".J_menuTab").prev(".J_menuTab").size()) {
            var i = $(this).parents(".J_menuTab").prev(".J_menuTab:last").data("id");
            $(this).parents(".J_menuTab").prev(".J_menuTab:last").addClass("active"),
            $(".J_mainContent .J_iframe").each(function() {
                return $(this).data("id") == i ? ($(this).show().siblings(".J_iframe").hide(), !1) : void 0
            }),
            $(this).parents(".J_menuTab").remove(),
            $(".J_mainContent .J_iframe").each(function() {
                return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
            })
        }
    } else $(this).parents(".J_menuTab").remove(),
    $(".J_mainContent .J_iframe").each(function() {
        return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
    }),
    e($(".J_menuTab.active"));
    return ! 1
}
function subJmenu(t) {
    var e = 0;
    return $(t).each(function() {
        e += $(this).outerWidth(!0)
    }),
    e
}
function beautifulJmenu(e) {
    var a = subJmenu($(e).prevAll()),
    i = subJmenu($(e).nextAll()),
    n = subJmenu($(".content-tabs").children().not(".J_menuTabs")),
    s = $(".content-tabs").outerWidth(!0) - n,
    r = 0;
    if ($(".page-tabs-content").outerWidth() < s) r = 0;
    else if (i <= s - $(e).outerWidth(!0) - $(e).next().outerWidth(!0)) {
        if (s - $(e).next().outerWidth(!0) > i) {
            r = a;
            for (var o = e; r - $(o).outerWidth() > $(".page-tabs-content").outerWidth() - s;) r -= $(o).prev().outerWidth(),
            o = $(o).prev()
        }
    } else a > s - $(e).outerWidth(!0) - $(e).prev().outerWidth(!0) && (r = a - $(e).prev().outerWidth(!0));
    $(".page-tabs-content").animate({
        marginLeft: 0 - r + "px"
    },
    "fast")
}

function openJmenu(t,i,daaa) {
    var n = !0;
    if (void 0 == t || 0 == $.trim(t).length) return ! 1;
    if ($(".J_menuTab").each(function() {
        return $(this).data("id") == t ? ($(this).hasClass("active") || ($(this).addClass("active").siblings(".J_menuTab").removeClass("active"), beautifulJmenu(this), $(".J_mainContent .J_iframe").each(function() {
            return $(this).data("id") == t ? ($(this).show().siblings(".J_iframe").hide(), !1) : void 0
        })), n = !1, !1) : void 0
    }), n) {
        var menuA = $('<a href="javascript:;" class="active J_menuTab" data-id="' + t + '">' + i + ' <i class="fa fa-refresh"></i>  <i class="fa fa-times-circle"></i></a>');
        $(".J_menuTab").removeClass("active");
        //var r = '<iframe class="J_iframe" name="iframe' + a + '" width="100%" height="100%" src="' + t + '?v=3.0" frameborder="0" data-id="' + t + '" seamless></iframe>';
        var r = $('<div class="J_iframe" id="iframe' + _all_menu_index_index_index + '"  data-id="' + t + '" style="width:100%;height:100%;overflow-y:auto;overflow-x:visible;position:relative;">数据加载中......</div>');
        $(".J_mainContent").find("div.J_iframe").hide().parents(".J_mainContent").append(r);
        menuA.find("i:first").on("click", function(){refeshJmenu(t,i,(!daaa)?{}:daaa)})
        menuA.find("i:last").on("click", closeJmenu)
        $(".J_menuTabs .page-tabs-content").append(menuA),
        beautifulJmenu($(".J_menuTab.active"))
        $.ajax({
        	type:"post",
        	url:t,
        	data:(!daaa)?{}:daaa,
        	dataType:"html",
        	success:function(data){
        		r.html(data)
        	},error:function(){
        		r.text("数据加载出错");
        	}
        })
        _all_menu_index_index_index++;
    }
    return ! 1
}
