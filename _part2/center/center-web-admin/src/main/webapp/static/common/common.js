function redirect(url) {
	document.location.href = url;
}

function removeLastChar(str) {
	if (isEmpty(str)) {
		return "";
	} else {
		return str.substring(0,str.length-1);
	}
}

function dateCompareTo(startDate, endDate) {
	var begin=new Date(startDate.replace(/-/g,"/"));
    var end=new Date(endDate.replace(/-/g,"/"));
    return begin-end;
}

function writeObj(obj){ 
    var description = ""; 
    for(var i in obj){   
        var property=obj[i];   
        description+=i+" = "+property+"\n";  
    }   
    alert(description); 
} 

function reload() {
   window.location.reload(); 
}
/*
 * 用途：判断整数 输入：str 返回：如果全是返回true,否则返回false
 */
function isInteger(str) {
	var partten = /^[0-9]*[1-9][0-9]*$/;
	return partten.test(str);
};

/*
 * 用途：判断是不是电话号码 输入：str 返回：如果全是返回true,否则返回false
 */
function isPhone(str) {
	var partten = /^1[3,5,8]\d{9}$/;
	return partten.test(str);
};

/*
 * 用途：检查输入字符串是否为空或者全部都是空格 输入：str 返回：如果全是空返回true,否则返回false
 */
function isEmpty(str) {
	if (str == null || str == "" || typeof str == "undefined") {
		return true;
	}
	var regu = "^[ ]+$";
	var re = new RegExp(regu);
	return re.test(str);
};


function isNotEmpty(str) {
	return !isEmpty(str);
};

/*
 * 用途：判断是不是身份证 输入：str 返回：如果全是空返回true,否则返回false
 */
function isIDCard(str) {
	var partten = /^(\d{18,18}|\d{15,15}|\d{17,17}x)$/;
	return partten.test(str);
};

/*
 * 用途：检查输入的Email信箱格式是否正确 输入：strEmail：字符串 返回：如果通过验证返回true,否则返回false
 */
function isEmail(str) {
	var emailReg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
	if (emailReg.test(strEmail)) {
		return true;
	} else {
		alert("您输入的Email地址格式不正确！");
		return false;
	}
};

/*
 * 用途：校验ip地址的格式 输入：strIP：ip地址 返回：如果通过验证返回true,否则返回false；
 */
function isIP(strIP) {
	var re = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g; // 匹配IP地址的正则表达式
	if (re.test(strIP)) {
		if (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256
				&& RegExp.$4 < 256) {
			return true;
		}
	}
	return false;
};

/**
 * 判断字符串是否 在指定的长度内
 */
function isLength(str, min, max) {
	if (str == null) {
		return false;
	} else {
		var length = str.length;
		if (length < min) {
			return false;
		} else if (length > max) {
			return false;
		}
	}
	return true;
};

/*
 * 检查开始时间是否 大于 结束时间
 */
function opinionTime(stratTime, endTime) {
	var start = stratTime.split("-");
	var end = endTime.split("-");
	if (start[0] <= end[0]) {
		if (start[0] == end[0]) {
			if (start[1] <= end[1]) {
				if (start[1] == end[1]) {
					if (start[2] <= end[2]) {
						return false;
					} else
						return true;
				} else
					return false;
			} else
				return true;
		} else
			return false;
	} else
		return true;
}


function dateFormat(date,format) {
	if (isEmpty(format)) {
		format = "yyyy-MM-dd HH:mm:ss";
	}
	return formatDate(date, format);
}

function formatDate(date, format) {   
    if (!date) return;   
    if (!format) format = "yyyy-MM-dd";   
    switch(typeof date) {   
        case "string":   
            date = new Date(date.replace(/-/, "/"));   
            break;   
        case "number":   
            date = new Date(date);   
            break;   
    }    
    if (!date instanceof Date) return;   
    var dict = {   
        "yyyy": date.getFullYear(),   
        "M": date.getMonth() + 1,   
        "d": date.getDate(),   
        "H": date.getHours(),   
        "m": date.getMinutes(),   
        "s": date.getSeconds(),   
        "MM": ("" + (date.getMonth() + 101)).substr(1),   
        "dd": ("" + (date.getDate() + 100)).substr(1),   
        "HH": ("" + (date.getHours() + 100)).substr(1),   
        "mm": ("" + (date.getMinutes() + 100)).substr(1),   
        "ss": ("" + (date.getSeconds() + 100)).substr(1)   
    };       
    return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {   
        return dict[arguments[0]];   
    });                   
} 

function getDateDiff(startTime, endTime, diffType) {
	startTime = startTime.replace(/\-/g, "/");
	endTime = endTime.replace(/\-/g, "/");
	diffType = diffType.toLowerCase();
	var sTime = new Date(startTime);
	var eTime = new Date(endTime);
	var divNum = 1;
	switch (diffType) {
	case "second":
		divNum = 1000;
		break;
	case "minute":
		divNum = 1000 * 60;
		break;
	case "hour":
		divNum = 1000 * 3600;
		break;
	case "day":
		divNum = 1000 * 3600 * 24;
		break;
	default:
		break;
	}
	return parseInt((eTime.getTime() - sTime.getTime()) / parseInt(divNum));
}

function action() {
	 return "aaa";
}



Date.prototype.format = function(format) // author: meizz
{
  var o = {
    "M+" : this.getMonth()+1, // month
    "d+" : this.getDate(),    // day
    "h+" : this.getHours(),   // hour
    "m+" : this.getMinutes(), // minute
    "s+" : this.getSeconds(), // second
    "q+" : Math.floor((this.getMonth()+3)/3),  // quarter
    "S" : this.getMilliseconds() // millisecond
  };
  if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
    (this.getFullYear()+"").substr(4 - RegExp.$1.length));
  for(var k in o)if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,
      RegExp.$1.length==1 ? o[k] :
        ("00"+ o[k]).substr((""+ o[k]).length));
  return format;
};

$.fn.deserialized = function(o){
    if(o==null){
        var form = document.forms[$(this).attr('name')];
        if(form!==undefined){
            form.reset();
            return true;
        }else{
            alert("重置表单名为"+$(this).attr('name')+"失败.");
            return false;
        }
    }
    var $this=this;
    var a = $this.serializeArray();
    $.each(a, function(i,n) {
        var $t=$('(input|textarea|select)[name=\''+this.name+'\']',$this);
        if($t.attr("type")=='radio'){
            $.each($t, function(i,n) {
                if($(this).val()==o[this.name]) {
                    $(this).attr("checked",true);
                }else{
                    $(this).attr("checked",false);
                }
            });
        }else{
            if (o[this.name]) {
                $t.val(o[this.name].valueOf());
            } else {
                $t.val("");
            }
        }
    });
};

/**
 * 选中复选框，如果已经选中，取消选中
 * @param thisObj
 * @param checkboxName
 */
function selectedAll(thisObj,checkboxName) {
	if (isEmpty(checkboxName)) {
		checkboxName = "checkboxs";
	}
	var flag = false; 
	if ($(thisObj).attr("checked") == "checked") {
		flag = true;
	}
	$("input[name="+checkboxName+"]:checkbox").attr("checked", flag);
} 

function getSingleCheckboxVal(checkboxName) {
	if (isEmpty(checkboxName)) {
		checkboxName = "checkboxs";
	}
	var i = 0;
	var value = "";
	$("input[name="+checkboxName+"]:checkbox").each(function () {
		if ($(this).attr("checked") == "checked") {
			i ++;
			value = $(this).val();
		}
		if (i > 1) {
			return null;
		}
    });
	if (i > 1) {
		return null;
	} else {
		return value;
	}
}

function getCheckboxVals(checkboxName,delimiter) {
	if (isEmpty(checkboxName)) {
		checkboxName = "checkboxs";
	}
	if (isEmpty(delimiter)) {
		delimiter = "&";
	}
	var value = "";
	$("input[name="+checkboxName+"]:checkbox").each(function () {
		if ($(this).attr("checked") == "checked") {
			value += $(this).val() + delimiter;
		}
    });
	if (isNotEmpty(value)) {
		value = value.substring(0,value.length-1);
	}
	return value;
}

 