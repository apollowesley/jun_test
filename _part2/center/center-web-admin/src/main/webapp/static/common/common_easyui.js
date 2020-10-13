var namespace = "";

function getNamespace() {
	return namespace;
}

function setNamespace(currentNamespace) {
	namespace = currentNamespace;
}

function confirm(msg, callName) {
    $.messager.confirm("提示框", msg, function (r) {
        if (r) {
        	callName();
        }
    });
    return false;
}

/**
 * 格式化日期
 * @param value
 * @param row
 * @param index
 * @returns
 */
function dateFormatByEasyui(value, row, index) {
	return dateFormat(value);
}

/**
 * 格式化 日期类型的 函数
 * @param data
 * @returns
 */
function dateFormatter(data) {
	if (isEmpty(data)) {
		return "";
	}
	return dateFormat(data, null);
}

function add(url, title, width, height) {
	if (isEmpty(title)) {
		title = "添加";
	}
	openDialog(title, url, width, height);
}

function update(url, title, width, height) {
	var row = getSelected();
	if (row) {
		if (isEmpty(title)) {
			title = "修改";
		} 
		if (url.indexOf("?") >= 0) {
			url += "id="+row.id; 
		} else {
			url += "?id="+row.id; 
		}
		openDialog(title, url, width, height);
	}
}

function openDialog(title, url, width, height) {
	if (isEmpty(width)) {
		width = 400;
	}
	if (isEmpty(height)) {
		height = 300;
	}
	$("#dialogDiv").dialog({
        title: title,
        modal: true,
        width :width,
        height : height,
        resizable:true,
        href:url
    }).dialog('open');
}
function closeDialog() {
	$("#dialogDiv").dialog('close');
}

function closeDialogTwo() {
	$("#dialogDivTwo").dialog('close');
}

function openDialogTwo(title, url, width, height) {
	if (isEmpty(width)) {
		width = 400;
	}
	if (isEmpty(height)) {
		height = 300;
	}
	$("#dialogDivTwo").dialog({
        title: title,
        modal: true,
        width :width,
        height : height,
        resizable:true
    }).dialog('open');
	$("#dialogIframeTwo").attr("src", url);
}

function resetForm() {
	getForm().form('reset');
}

function resetForm(formId) {
	$("#"+formId).form('reset');
}

/**
 * 更新和添加提交表单，如果不例外，所有的更新和添加窗口都必须调用这个方法
 * @param namespace  模块命，比如user模块，就传user,form跟dialog的id命名方法必须规范
 */
function submitForm() {
    var $form = getForm();
    if ($form.form("validate")) {
    	var $grid = getGrid();
    	if ($grid.is(".easyui-treegrid")) {
    		var row = getSelected();
    		if (row) {
    			var parentId = $form.find("input[name=parentId]").val();
    			if (isEmpty(parentId)) {
    				$form.find("input[name=parentId]").val(row.id);
    			}
    		}
    	}
        $.post($form.attr("action"),$form.serialize(),function (resultData) {
            if (isSuccess(resultData)) {
                $form.parents(".easyui-dialog").dialog('close');
                if ($grid.is(".easyui-treegrid")) {
                	var row = getSelected();
                	if (row && row.parentId != "-1") {
                		$grid.treegrid('reload', row.parentId);
                		row.state = "open";
                	} else {
                		$grid.treegrid({pageNumber:1}).treegrid('reload');
                	}
                } else {
                	$grid.datagrid({pageNumber:1}).datagrid('reload');
                }
            }
        },"json");
    }
}

/**
 * 通过id删除数据，如果不例外，所有的通过id删除数据都必须调用这个方法
 * @param url
 */
function delById(url, id) {
	if (isEmpty(id)) {
		var row = getSelected();
		id = row.id;
	}
    if (!isEmpty(id)) {
    	$.messager.confirm('系统提示', '您确认吗?', function(r){
            if (r){
            	$.post(url,{id:id},function (resultData) {
            		if (isSuccess(resultData)) {
            			refreshData();
            		}
    	    	},"json");
            }
        });
    }
}

function refreshData() {
	var $grid = getGrid();
	if ($grid.is(".easyui-treegrid")) {
    	var row = getSelected();
    	if (row && row.parentId != "0") {
    		$grid.treegrid('reload', row.parentId);
    		row.state = "open";
    	} else {
    		$grid.treegrid({pageNumber:1});
    	}
    } else {
    	$grid.datagrid({pageNumber:1});
    }
}

/**
 * 搜索数据，所有搜索都必须调用这个方法
 */
function searchData() {
	var $grid = getGrid();
	if($grid.length > 0){
		var queryParams = $grid.datagrid('options').queryParams;
		queryParams = {}; 
		var $search = getSearch();
		
		var isValid = false;
		if ($search.find("#searchForm").attr("id") == "searchForm") {
			isValid = $search.find("#searchForm").form("validate");
		} else {
			isValid = true;
		}
		if (isValid) {
			$search.find('*').each(function() {
				if (!isEmpty($(this).val()) && !isEmpty($(this).attr('name'))) {
					var existValue = queryParams[$(this).attr('name')];
					if (isEmpty(existValue)) {
						var array = new Array();
						array.push($(this).val());
						queryParams[$(this).attr('name')] = array;	
					} else {
						existValue.push($(this).val());
						queryParams[$(this).attr('name')] = existValue;	
					}
				}
			});
			if ($grid.hasClass("easyui-treegrid")) {
				$grid.treegrid({queryParams:queryParams, pageNumber:1});
			} else {
				$grid.datagrid({queryParams:queryParams, pageNumber:1});
			}
		}
	}
}
 

function getSelected() {
	var $grid = getGrid();
	if ($grid.is(".easyui-treegrid")) {
		return $grid.treegrid('getSelected');
	} else {
		var row = $grid.datagrid('getSelected');
	    if (!row) {
	    	showMsg("你没有选中数据,请选择一条数据!");
	    }
	    return row;
	}
}

function getSelections() {
	var $grid = getGrid();
	var rows;
	if ($grid.is(".easyui-treegrid")) {
		rows =  $grid.treegrid('getSelections');
	} else {
		rows = $grid.datagrid('getSelections');
	}
	if (!rows || rows.length == 0) {
    	showMsg("你没有选中数据,请选择一条数据!");
    }
    return rows;
}

function isExistRowId(rowId) {
	var $grid = getGrid();
	var data = $grid.datagrid('getData');
	var flag = false;
	if (data && data.rows) {
		$.each(data.rows, function(i, row){
	        if (rowId == row.id) {
	        	showMsg("已经存在!");
	        	flag = true;
	        }
	    });
	}
	return flag;
}

function isExistRowId(rowId, namespace) {
	var currentNamespace = getNamespace();
	setNamespace(namespace);
	var $grid = getGrid();
	var data = $grid.datagrid('getData');
	var flag = false;
	if (data && data.rows) {
		$.each(data.rows, function(i, row){
	        if (rowId == row.id) {
	        	showMsg("已经存在!");
	        	flag = true;
	        }
	    });
	}
	setNamespace(currentNamespace);
	return flag;
}

function selectRow(index) {
	getGrid().datagrid('selectRow', index);
}

/**
 * 判断后台方法是否操作成功
 * @param resultData
 * @returns {Boolean}
 */
function isSuccess(resultData) {
    if (resultData.code == "200") {
        if (!isEmpty(resultData.msg)) {
    		showMsg(resultData.msg);
    	}
        return true;
    } else if (resultData.showType == "error") {
    	showAlertErrorMsg(resultData.msg);
    } else {
    	showMsg(resultData.msg);
    }
}

/**
 * 操作成功，在屏幕右下方提示用户成功
 * @param msg
 */
function showMsg(msg) {
	$.messager.show({
	    title:'系统提示',
	    msg:msg,
	    showType:'show'
	});
}

/**
 * 操作失败，用alert error提示用户失败
 * @param msg
 */
function showAlertErrorMsg(msg){
    showAlertMsg(msg,'error');
}

function showAlertMsg(msg, type){
	$.messager.alert('系统提示',msg, type);
}

function addTab(_this) {
	var $this = $(_this);
	var title = $this.attr("title");
    if (!$('#maintabs').tabs('exists', title)) {
    	//判断是否进行iframe方式打开tab，默认为href方式
    	var url = $this.attr("url");
    	var icon = $this.attr("icon");
    	if($this.attr('isIframe') == "true") {
            $('#maintabs').tabs('add', {
                title : title,
                content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
                closable : true,
                icon : icon
            });    
        } else {
            $('#maintabs').tabs('add', {
                title : title,
                href : url,
                closable : true,
                icon : icon
            });                        
        }
    } else {
        $('#maintabs').tabs('select', title);
    }
}

function getForm() {
	return $("#form");
}

function getGrid() {
	if (isEmpty(getNamespace())) {
		return $("#grid");
	} else {
		return $("#"+getNamespace()+"Grid");
	}
}

function getSearch() {
	if (isEmpty(getNamespace())) {
		return $("#searchDiv");
	} else {
		return $("#"+getNamespace()+"SearchDiv");
	}
}

document.onkeydown = function(event){
    if (event.keyCode == 13) {
    	searchData();
    }
};

$(document).ready(function(){
	$('input').live('blur', function() {
	    var value = $(this).val();
	    $(this).val($.trim(value));
	});
});
