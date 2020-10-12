(function(document,window){
    window.qxzl={
        addServiceURL:"QequestController/addService",//添加服务端
        addClientURL:"QequestController/addClient",//添加客户端
        deleteServiceURL:"QequestController/deleteService",//删除服务器
        deleteClientURL:"QequestController/deleteClient",//删除客户端
        updateRequestURL:"QequestController/updateRequest",//修改请求
        deleteRequestURL:"QequestController/deleteRequest",//删除请求
        redisBufferClearURL:"QequestController/redisBufferClear",//redi缓存清除
        qajax:function (url,dataJson) {
            $.ajax({
                type: "POST",
                url: url,
                data: dataJson,
                dataType: "json",
                success: function(data){
                    console.log(data);
                    if(data.success=="true"){
                        alert(data.obj);
                        window.location.reload();
                    }
                }
            })
        },
        addService:function(str){//添加服务端
            this.qajax(this.addServiceURL,str);
        },
        addClient:function(str){//添加客户端
            this.qajax(this.addClientURL,str);
        },
        deleteService:function(ip){//删除服务端
            this.qajax(this.deleteServiceURL,ip);
        },
        deleteClient:function(ip){//删除客户端
            this.qajax(this.deleteClientURL,ip);
        },
        updateRequest:function(ip){//修改请求
            this.qajax(this.updateRequestURL,ip);
        },
        deleteRequest:function (ip) {//删除请求
            this.qajax(this.deleteRequestURL,ip);
        },
        redisBufferClear:function(str){//redi缓存清除
            this.qajax(this.redisBufferClearURL,str);
        }
    };
    //修改服务端
    $("#updateServiceButton").click(function(){
        var ip1=$("#updateServiceSelect option:selected").val();

        var ip2=$("#updateClientsSelect option:selected").val();
        qxzl.updateRequest({Serviceip:ip1,Clientsip:ip2});
    })
    //添加服务端
    $("#addServiceButton").click(function(){
        var x=$("#ServiceForm").serialize();
        qxzl.addService(x);
    })
    //添加客户端
    $("#addClientButton").click(function(){
       var x=$("#ClientForm").serialize();
        qxzl.addClient(x);
    })

    //删除服务端
    $(".deleteServiceButton").click(function(){
        var ip=$(this).attr("alt");
        qxzl.deleteService({ip:ip});
    })

    //删除客户端
    $(".deleteClientButton").click(function(){
        var ip=$(this).attr("alt");
        qxzl.deleteClient({ip:ip});
    })
    //解绑
    $(".unbindButton").click(function(){
        var ip=$(this).attr("alt");
        qxzl.deleteRequest({ip:ip});
    })
    $("#redisBufferClearButton").click(function () {
        qxzl.redisBufferClear({type:2});
    })

})(document,window);