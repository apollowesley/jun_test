/**
 * 通用类库定义
 */
layui.define(['jquery','layer'], function (exports) {
    "use strict";
	
    var layer = layui.layer
	,$ = layui.jquery
	,notice = layui.notice;
    
    var common = {
        getPath: function (url) {
            return basePath + url;
        },
        //全局错误提示信息
		errorTips: "错误代码【status】操作失败，请联系系统人员处理",
		//调用成功通用弹窗
		success: function (msg,yes) {
			layer.alert(msg || "", {icon: 1, yes: yes, closeBtn: 0});
		},
		//调用失败通用弹窗
		error: function (msg,set) {
			layer.alert(msg, $.extend({},{icon: 2},set));
		},
        //通用询问弹窗
        confirm:function(msg,fun){
            layer.confirm(msg, {skin: 'layui-layer-admin', shade: .1},fun);
        },
        //同步ajax，用于提交表单数据        syncAjax: function(url,data,options){
            var result,defaultOptions = {
                url: xc.getPath(url),
                type: 'POST',
                async: false,
                data: data,
                beforeSend: function () {
                    layer.load(2);
                },
                success: function (response) {
                    result = response;
                },
                error: function (xhr, status, error) {
                    xc.error(xc.errorTips.replace("status",xhr.status));
                },
                complete: function () {
                    layer.closeAll('loading');
                }
            };
            $.ajax($.extend({},defaultOptions,options));
            return result;
        },
        //同步请求,用于提交json数据
        syncJSON: function (url, data) {
            return xc.syncAjax(url,JSON.stringify(data || {}),{contentType: "application/json;charset=UTF-8"});
        },
        //异步请求，用于提交表单数据        request: function (url, data, success,options) {
            var defaultOptions = {
                url: xc.getPath(url),
                type: 'POST',
                data: data,
                beforeSend: function () {
                    layer.load(2);
                },
                complete: function () {
                    layer.closeAll('loading');
                },
                success: success,
                error: function (xhr, status, error) {
                    xc.error(xc.errorTips.replace("status",xhr.status));
                }
            };
            $.ajax($.extend({},defaultOptions,options));
        },
        //异步请求,用于提交json数据
        requestJSON: function (url, data, success) {
            xc.request(url,JSON.stringify(data || {}),success,{contentType: "application/json;charset=UTF-8"});
        },
        //为空判断
        isNull: function (obj) {
            if (obj === undefined
                || obj === null
                || obj === ""
                || obj.length == 0) return true;
            return false;
        },
        //求字符串长度
        len: function (str) {
            return xc.isNull(str) ? 0 : str.toString().length;
        },
        //复制对象
        copy: function (obj) {
            return JSON.parse(JSON.stringify(obj));
        },
        //tips
        renderTips: function () {
            //验证提示
            $('.xc-tips').on('click', function () {
                var that = this;
                layer.tips($(that).attr("tips"), that);
            });
        },
        //向session添加数据
        setSession: function (key, obj) {
            sessionStorage.setItem(key, JSON.stringify(obj));
        },
        //取session数据
        getSession: function (key) {
            return JSON.parse(sessionStorage.getItem(key));
        },
        //删除session数据
        removeSession: function (key) {
            sessionStorage.removeItem(key);
        },
        //求数组最大值        max: function (arr) {
            return Math.max.apply({}, arr);
        },
        //求数组最小值        min: function (arr) {
            return Math.min.apply({}, arr)
        },
        //过滤中文
        filter_zh:function (obj) {
            var newStr = obj.value.replace(/[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/g,'');
            var $that = $(obj);
            if(obj.value!=newStr) {
                layer.tips('不允许输入中文', $that, {
                    tips: [2, '#ff4c4c']
                });
            }
            obj.value=newStr;
        },
        //过滤中文，转大写
        filter_zh_toUpperCase:function (obj) {
            var newStr = obj.value.replace(/[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/g,'');
            var $that = $(obj);
            if(obj.value!=newStr) {
                layer.tips('不允许输入中文', $that, {
                    tips: [2, '#ff4c4c']
                });
            }
            obj.value=newStr.toUpperCase();
        },
        //表单验证提示
        tip:function (errorText) {
            layer.msg(errorText, {icon: 5, shift: 6});
        },
        //数值转换，将num保留n位        fomatFloat:function (num, n) {
            // num为传入的值，n为保留的小数位            var f = parseFloat(num);
            if(isNaN(f)){
                return false;
            }
            f = Math.round(num*Math.pow(10, n))/Math.pow(10, n); // n 幂
            var s = f.toString();
            var rs = s.indexOf('.');
            //判定如果是整数，增加小数点再补0
            if(rs < 0){
                rs = s.length;
                s += '.';
            }
            while(s.length <= rs + n){
                s += '0';
            }
            return s;
        },
        //数值转换，将num四舍五入保留fixed位        toFixed:function(num, fixed){
          return parseFloat(xc.fomatFloat(num,fixed));
        },
        //获取复选框是否选中
        getCheckboxVal:function(id){
            return $(id).next().hasClass('layui-form-checked');
        },
        //动态加载js
        loadScript:function(src, callback) {
            if(xc.isNull(callback)) callback = function(){};
            layer.load(2);
            var script = document.createElement('script'),
                head = document.getElementsByTagName('head')[0];
            script.type = 'text/javascript';
            script.charset = 'UTF-8';
            script.src = src;
            if (script.addEventListener) {
                script.addEventListener('load', function () {
                    callback();
                    layer.closeAll('loading');
                }, false);
            } else if (script.attachEvent) {
                script.attachEvent('onreadystatechange', function () {
                    var target = window.event.srcElement;
                    if (target.readyState == 'loaded') {
                        callback();
                        layer.closeAll('loading');
                    }
                });
            }
            head.appendChild(script);
        },
        //获取开始时间，用于初始化时间控件        getStartDate:function () {
            return new Date(new Date().format("yyyy-MM-dd")+" 00:00:00");
        },
        //获取结束时间，用于初始化时间控件
        getEndDate:function () {
            return new Date(new Date().format("yyyy-MM-dd")+" 23:59:59");
        },
        //获取uuid
        uuid:function () {
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                var r = Math.random() * 16 | 0,
                    v = c == 'x' ? r : (r & 0x3 | 0x8);
                return v.toString(16);
            });
        },
        //提交表单数据（表单中可包含文件上传控件，如：input type='file'）        submitFormData:function(url,formId,success){
            var formData = new FormData($(formId)[0]);
            $.ajax({
                url:url,
                type:"post",
                data:formData,//参数
                cache: false,
                processData: false,
                contentType: false,
                beforeSend: function () {
                    layer.load(2);
                },
                complete: function () {
                    layer.closeAll('loading');
                },
                success: success,
                error: function (xhr, status, error) {
                    xc.error(xc.errorTips.replace("status",xhr.status));
                }
            })
        }
    }
	
    exports('common', common);
});

//全部替换
String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}

//时间格式化Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

//是否包含判断
Array.prototype.contains = function (needle) {
    for (i in this) {
        if (this[i] == needle) return true;
    }
    return false;
}

//字符串结束匹配String.prototype.endWith = function (str) {
    if (str == null || str == "" || this.length == 0 || str.length > this.length) {
        return false;
    }
    if (this.substring(this.length - str.length)) {
        return true;
    } else {
        return false;
    }
    return true;
};

//字符串开始匹配String.prototype.startWith = function (str) {
    if (str == null || str == "" || this.length == 0 || str.length > this.length) {
        return false;
    }
    if (this.substr(0, str.length) == str) {
        return true;
    } else {
        return false;
    }
    return true;
};