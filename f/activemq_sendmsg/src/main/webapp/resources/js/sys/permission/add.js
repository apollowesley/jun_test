$(function(){
    formValidate("#permissionAddForm",{
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
})  


function checkPermission() {
    var zTree = $.fn.zTree.getZTreeObj("permissionTree"), 
    type = {"Y" : "ps","N" : "s"};
    zTree.setting.check.chkboxType = type;
}

$(document).ready(function() {
    var setting = {
        check : {
            enable : false
        },
        data : {
            simpleData : {
                enable : true
            }
        },
        callback : {
        	onClick : function(event, treeId, treeNode){//节点被点击后，设置选择的节点id
        		$("#parentId").val(treeNode.id);
        	}
        }
    };
    var permissions = $("input[name='permissions']").val();
    $.fn.zTree.init($("#permissionTree"), setting, eval("(" + permissions + ")"));
    checkPermission();
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
        $("#permissionIds").val(pmss);
    } else {
        numberval++;
    }
    if (numberval > 0) {
        return false;
    }
    return true;
}


function permissionSubmitForm(parentIndex){
    var s=$("#permissionAddForm").validate().form();
    if(s){
    	var parentId = $("#parentId").val();
        if(parentId == null || parentId == ""){
            alertMsg("请选择权限所属层级",0);
            return false;
        }
        
        $.ajax({
            type : "post",
            url : $("#addPermissionUrl").val(),
            data : {
                "name" : $("#permissionAddForm #name").val(),
                "description" : $("#permissionAddForm #description").val(),
                "parentId" : $("#parentId").val(),
                "code" : $("#permissionAddForm #code").val(),
                "status" : $("#permissionAddForm #status").val(),
            },
            dataType : "json",
            success : function(data) {
                if(data.code == 200){
                    layer.msg('添加成功', {
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