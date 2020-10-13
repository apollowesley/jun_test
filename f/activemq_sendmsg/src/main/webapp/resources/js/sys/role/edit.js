$(function(){
    formValidate("#roleEditForm",{
        name:{
            required: true
            },
        description:{
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
            enable : true
        },
        data : {
            simpleData : {
                enable : true
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
        	//if(permissions[i].id != -1){//排除最顶层
        		pmss += permissions[i].id + ",";
        	//}
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


function roleSubmitForm(parentIndex){
    var s=$("#roleEditForm").validate().form();
    if(s){
    	if(!check_param()){
    		layer.msg("请为该角色赋予权限", {
                shift: 2,
                icon: 5
            });
            return false;
        }
        
        $.ajax({
            type : "post",
            url : $("#editRoleUrl").val(),
            data : $("#roleEditForm").serialize(),
            dataType : "json",
            success : function(data) {
                if(data.code == 200){
                    layer.msg('更新成功', {
                        shift: 2,
                        icon: 6
                    });
                    closeMsg(parentIndex);
                    
                    $('#roleListTable').bootstrapTable("refresh");
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