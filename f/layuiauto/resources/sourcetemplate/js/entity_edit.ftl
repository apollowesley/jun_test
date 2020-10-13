
layui.use(['form', 'layedit', 'element','laydate'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,layedit = layui.layedit
        ,element = layui.element
        ,laydate = layui.laydate
        ,$ = layui.jquery;
        $("#menu_30000100").addClass("layui-this");

    //自定义验证规则
    form.verify({
        /*strLength32:[/^.{1,32}$/,'长度不能大于32个字符!!'],
        bankName: function(value){
            if(value.length < 3){
                return '至少得3个字符啊';
            }
        }*/
    });

    //监听提交
    form.on('submit(formDemo)', function(data){
        
        $.ajax({
                type : "POST",
                dataType : "json",
                url : CONTEXT_PATH+'/${entityName?lower_case}/save',
                data : $('#formDemo').serialize(),
                success : function(data) {
                    if (data.code == 200) {
                        layer.msg('操作成功', {
                              icon: 1,
                              time: 500 
	                    },function(){
	                          parent.location.reload()
                              parent.layer.closeAll();
	                    });
                    } else {
                        layer.msg(data.msg, {
                            icon : 2,
                            time : 3000,
                            offset : '12px',
                            closeBtn : 1
                        });
                    }
                },
                error : function() {
                    layer.msg("操作失败!", {
                        icon : 1,
                        time : 3000,
                        offset : '12px',
                        closeBtn : 1
                    });
                }
            });
            return false;
    });
});


