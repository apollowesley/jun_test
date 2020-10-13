$(function(){
    formValidate("permissionEditForm",{
        name:{
            required: true,
            maxlength:20
            },
        description:{
            required: true,
            maxlength:50
        },
        code:{
            required: true,
            maxlength:50
        },
        status:{
            required: true
        }
    });
});




function checkPermission() {
    var zTree = $.fn.zTree.getZTreeObj("permissionTree"), 
    type = {"Y" : "ps","N" : "s"};
    zTree.setting.check.chkboxType = type;
}

$(document).ready(function() {
    var setting = {
        check : {
            enable : true
        },
        data : {
            simpleData : {
                enable : true
            }
        },
        callback : {
            onClick : function(event, treeId, treeNode){//节点被点击后，设置选择的节点id
                $("#permissionUpdateDiv #parentId").val(treeNode.id);
            }
        }
    };
    var permissions = $("input[name='permissions']").val();
    $.fn.zTree.init($("#permissionUpdateDiv #permissionTree"), setting, eval("(" + permissions + ")"));
    checkPermission();
    
    
    //默认选中权限的父亲节点
    var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
	var nodes = treeObj.getCheckedNodes(false);
	var parentId = $("#permissionUpdateDiv #parentId").val();
	if (nodes.length>0) {
		for(var i = 0; i < nodes.length; i++){
			if(nodes[i].id == parentId){
				treeObj.selectNode(nodes[i]);
				break;
			}
		}
	}
});

// 验证是否选择了权限
function check_param() {
    var numberval = 0;
    var treePermission = $.fn.zTree.getZTreeObj("permissionTree"), 
        permissions = treePermission.getCheckedNodes(true), 
        pmss = "";
    if (permissions.length > 0) {
        for (var i = 0; i < permissions.length; i++) {
            pmss += permissions[i].id + ",";
        }
        if (pmss.length > 0) {
            pmss = pmss.substring(0, pmss.length - 1);
        }
        $("#permissionUpdateDiv #permissionIds").val(pmss);
    } else {
        numberval++;
    }
    if (numberval > 0) {
        return false;
    }
    return true;
}


//提交
function permissionSubmitForm(parentIndex){
	var parentId = $("#permissionUpdateDiv #parentId").val();
    if(parentId == null || parentId == ""){
        alertMsg("请选择权限所属层级",0);
        return false;
    }
    
    var s=$("#permissionUpdateDiv #permissionEditForm").validate().form();
    if(s){
        $.ajax({
            type : "post",
            url : $("#permissionUpdateDiv #updatePermissionUrl").val(),
            data : {
            	"name" : $("#permissionUpdateDiv #name").val(),
            	"code" : $("#permissionUpdateDiv #code").val(),
            	"status" : $("#permissionUpdateDiv #status").val(),
            	"description" : $("#permissionUpdateDiv #description").val(),
            	"id" : $("#permissionUpdateDiv #permissionId").val(),
            	"parentId" : $("#permissionUpdateDiv #parentId").val()
            },
            dataType : "json",
            success : function(data) {
                if(data.code == 200){
                	layer.msg('更新成功', {
                        shift: 2,
                        icon: 6
                    });
                	closeMsg(parentIndex);
                	
                	$('#permissionListTable').bootstrapTable("refresh");
                }else{
                	layer.msg(data.msg, {
                        shift: 2,
                        icon: 5
                    });
                    closeMsg(parentIndex);
                }
            }
        });
    }
}