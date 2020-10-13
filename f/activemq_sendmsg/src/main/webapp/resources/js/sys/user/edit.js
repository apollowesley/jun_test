$(function(){
    formValidate("#userEditForm",{
    	userName:{
            required: true
        },
        realName:{
            required: true
        },
        status:{
            required: true
        }
    });
})  


function checkRole() {
    var zTree = $.fn.zTree.getZTreeObj("roleTree"), 
    type = {"Y" : "ps","N" : "ps"};
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
            onCheck : function(event, treeId, treeNode){//节点被点击后，设置选择的节点id
            	check_param();
            }
        }
    };
    var roles = $("input[name='allRoles']").val();
    $.fn.zTree.init($("#roleTree"), setting, eval("(" + roles + ")"));
    checkRole();
});

// 验证是否选择了角色
function check_param() {
    var numberval = 0;
    var treeRole = $.fn.zTree.getZTreeObj("roleTree"), 
        roles = treeRole.getCheckedNodes(true), 
        pmss = "";
    if (roles.length > 0) {
        for (var i = 0; i < roles.length; i++) {
            pmss += roles[i].id + ",";
        }
        if (pmss.length > 0) {
            pmss = pmss.substring(0, pmss.length - 1);
        }
        $("#roleIds").val(pmss);
    } else {
    	$("#roleIds").val("");
        numberval++;
    }
    if (numberval > 0) {
        return false;
    }
    return true;
}


function userSubmitForm(parentIndex){
    var s=$("#userEditForm").validate().form();
    if(s){
    	if(!check_param()){
    		layer.msg("请为该用户赋予角色", {
                shift: 2,
                icon: 5
            });
            return false;
        }
        
        $.ajax({
            type : "post",
            url : $("#updateUserUrl").val(),
            data : $("#userEditForm").serialize(),
            dataType : "json",
            success : function(data) {
                if(data.code == 200){
                    layer.msg('修改成功', {
                        shift: 2,
                        icon: 6
                    });
                    closeMsg(parentIndex);
                    
                    $('#userListTable').bootstrapTable("refresh");
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