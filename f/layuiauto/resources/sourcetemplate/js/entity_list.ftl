
layui.use(['form', 'layedit', 'laydate', 'element','table','layer'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,layedit = layui.layedit
        ,element = layui.element
        ,laydate = layui.laydate
        ,table = layui.table
        ,$ = layui.jquery;
        $("#menu_30000100").addClass("layui-this");
        
        //表格加载
		table.render({
		    elem: '#test'
		    ,height: 620
		    ,url: CONTEXT_PATH+'/${entityName?lower_case}/search'
		    ,method:'post'
		    ,toolbar: '#toolbarDemo'
		    ,limits : [ 5, 10, 50, 100, 150, 200, 10000 ]
		    ,id : 'contenttable'
		     // ,totalRow : true
		    ,page : { 
		                layout : [ 'limit', 'count', 'prev', 'page', 'next', 'skip' ] //自定义分页布局
		                ,groups : 3 
	                }
	        ,where : {rows : 10}
		    ,cols: [[ //表头
		    	{type : 'checkbox'},
		        {type:'numbers',title: '序号'},
		        {field : '${primaryProperty}',title : '${primaryProperty}',hide : true,width : 80},
		    	<#if columns??>
		            <#list columns as col>
		                <#if !col.primaryKey >
		        {field: '${col.propertyName}', title: '${col.remark!}', width:180, sort: true},
		                </#if>
		            </#list>
	    		</#if>
        		{fixed:'right',title : '操作',toolbar : '#barDemo',width : 180,style : 'background-color: white;'}
		    ]]
		});
		
		
		//table页头工具栏事件
		table.on('toolbar(test)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.${primaryProperty});
		    var data = checkStatus.data;
		    if(obj.event === 'add'){
		        layer.open({
                    type: 2
                    ,title: "添加" // 不显示标题栏
                    ,closeBtn: "1"
                    ,area: ['100%', '100%']
                    ,shade: 0.2
                    ,maxmin:true
                    ,resize :true
                    ,offset : '140px'
                    ,moveOut: true
                    ,moveType: 1 // 拖拽模式，0或者1
                    ,content: CONTEXT_PATH+'/${entityName?lower_case}/edit'
                });
		    }else if(obj.event === 'batchDelete'){
			    layer.confirm('确认删除吗', function(index) {
				    var ${primaryProperty}s = new Array();
	                data = table.checkStatus('contenttable').data;
	                for (var i = 0; i < data.length; i++) {
	                    ${primaryProperty}s.push(data[i].${primaryProperty});
	                }
	                if (${primaryProperty}s.length < 1) {
	                    layer.alert("至少选择一条记录删除!!", 
	                    {
	                        title: '提示信息',
	                    })
	                    return;
	                }
	                $.ajax({
	                    type : "POST",
	                    dataType : "json",// 预期服务器返回的数据类型
	                    url : CONTEXT_PATH+'/${entityName?lower_case}/delete',
	                    data : {
	                        ${primaryProperty}s : ${primaryProperty}s
	                    },
	                    success : function(result) {
	                        if(result.code == 200) {
                            	layer.msg('操作成功', {
	                              icon: 1,
	                              time: 500 
	                            }, function(){
	                               location.reload();
	                            });
                            }else {
                                layer.alert("操作失败");
                            }
	                    },
	                    error : function(msg) {
	                        layer.alert("操作失败");
	                    }
	                });
			    });
		    }
		    
		  });
		
		
		//table表格最后一列工具事件
        table.on('tool(test)', function(obj) 
        {
             var data = obj.data //获得当前行数据
                ,layEvent = obj.event; //获得 lay-event 对应的值
                if (layEvent === 'delete') 
                {
                	layer.confirm('确认删除吗', function(index) {
	                    var ${primaryProperty}s = new Array();
                	    ${primaryProperty}s.push(data.${primaryProperty});
	                    $.ajax({
	                        url : CONTEXT_PATH+'/${entityName?lower_case}/delete'
	                        ,type : "get"
	                        ,data : {
	                            ${primaryProperty}s : ${primaryProperty}s
	                        }
	                        ,dataType : "json"
	                        ,success : function(result) {
	                            if(result.code == 200) {
		                            layer.msg('操作成功', {
		                              icon: 1,
		                              time: 500 
		                            }, function(){
		                               location.reload();
		                            });
	                            }else {
	                                layer.alert("操作失败");
	                            }                   
	                        },
	                        error : function(msg) {
	                            layer.alert("操作失败");
	                        }
	                    })
                  });
                }else if (layEvent === 'edit') 
                {
	                var ${primaryProperty} = data.${primaryProperty};
	                layer.open({
	                    type: 2
	                    ,title: "编辑" // 不显示标题栏
	                    ,closeBtn: "1"
	                    ,area: ['100%', '100%']
	                    ,shade: 0.2
	                    ,maxmin:true
	                    ,offset : '140px'
	                    ,resize :true
	                    ,moveOut: true
	                    ,moveType: 1 // 拖拽模式，0或者1
	                    ,content : CONTEXT_PATH+'/${entityName?lower_case}/edit?${primaryProperty}='+${primaryProperty}
	                });
	                
	                
                }
        });
		
		//页面其它事件
		var active = {
           reload : function() 
           {
                //执行重载
                table.reload('contenttable', {
                    where : 
                    {
			            <#if columns??>
		                <#list columns as col>
		                <#if !col.primaryKey >
		                <#if col_index < 4>
		                	${col.propertyName} :  $("input[name='${col.propertyName}']").val(),
		                </#if>
		                </#if>
		                </#list>
		                </#if>
                    },
                    page : 
                    {
                        curr : 1  //重新从第 1 页开始
                    }
                });
            }
	}
	
	$('.layui-btn').on('click', function(){
    	var type = $(this).data('type');
    	active[type] ? active[type].call(this) : '';
  	});
  	
  	$(".layui-input").keydown(function(e) {//回车键的键位序号为13
            if (e.which == 13) {
                $("#search").click();
            }
    });
		
});
