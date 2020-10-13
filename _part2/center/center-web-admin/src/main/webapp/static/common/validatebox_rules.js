/**
 * @author yukaizhao
 */
$.extend($.fn.validatebox.defaults.rules, {  
    /*必须和某个字段相等  */
    equalTo: {
        validator:function(value,param){
            return $(param[0]).val() == value;
        },
        message:'字段不匹配!'
    },
    illegal: {
        validator:function(value,param){
            return false;
        },
        message:'非法字符,仅支持中英文、数字和下划线!'
    },
    numberLtTo: {
        validator:function(value,param){
            return $(param[0]).val() > value;
        },
        message:'数值过大!'
    },
    numberGtTo: {
        validator:function(value,param){
            return $(param[0]).val() < value;
        },
        message:'数值太小!'
    },
    numberLtEqualTo: {
        validator:function(value,param){
            return $(param[0]).val() >= value;
        },
        message:'数值过大!'
    },
    numberGtEqualTo: {
        validator:function(value,param){
            return $(param[0]).val() <= value;
        },
        message:'数值太小!'
    },
    dateLtTo: {
        validator:function(value,param){
        	var paramValue;
        	if ($(param[0]).hasClass("easyui-datetimebox")) {
        		paramValue = $(param[0]).datetimebox("getValue");
        	} else {
        		paramValue = $(param[0]).val();
        	}
        	var result = dateCompareTo(value, paramValue);
        	if (result > 0) {
        		return false;
        	} else {
        		return true;
        	}
        },
        message:'日期过大!'
    },
    dateGtTo: {
        validator:function(value,param){
        	var paramValue;
        	if ($(param[0]).hasClass("easyui-datetimebox")) {
        		paramValue = $(param[0]).datetimebox("getValue");
        	} else {
        		paramValue = $(param[0]).val();
        	}
        	var result = dateCompareTo(value, paramValue);
        	if (result < 0) {
        		return false;
        	} else {
        		return true;
        	}
        },
        message:'日期太小!'
    },
    dateLtEqualTo: {
        validator:function(value,param){
        	var paramValue;
        	if ($(param[0]).hasClass("easyui-datetimebox")) {
        		paramValue = $(param[0]).datetimebox("getValue");
        	} else {
        		paramValue = $(param[0]).val();
        	}
        	var result = dateCompareTo(value, paramValue);
        	if (result >= 0) {
        		return false;
        	} else {
        		return true;
        	}
        },
        message:'日期过大!'
    },
    dateGtEqualTo: {
        validator:function(value,param){
        	var paramValue;
        	if ($(param[0]).hasClass("easyui-datetimebox")) {
        		paramValue = $(param[0]).datetimebox("getValue");
        	} else {
        		paramValue = $(param[0]).val();
        	}
        	var result = dateCompareTo(value, paramValue);
        	if (result <= 0) {
        		return false;
        	} else {
        		return true;
        	}
        },
        message:'日期太小!'
    }
});

