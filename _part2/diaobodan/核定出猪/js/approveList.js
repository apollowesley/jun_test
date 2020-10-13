var PigPlan_ApproveList={
	oldData:'',  //保存查询之后返回的数据
	permissionType:0,
	loadParams:function(){},
	saveParams:function(){},
	init:function(){},
	checkPower:function(){},  
	checkPower_callback:function(responseData){},
	bindEvent:function(){},
	addApproveList:function(){},
	addApproveList_callback:function(responseData){},
	rowClick:function(){},  //点击列表的跳转函数
	checkPermission:function(){},//检查权限
	checkPermission_Callback:function(responseData){},//检查权限返回
}
PigPlan_ApproveList.loadParams = function(){
	var isLoadDefaultParams = false;
	if (localStorage.getItem("approvelist_saveData")){
		var d1 = new Date(localStorage.getItem("approvelist_saveData"));
		var s1 = d1.format("yyyy-MM-dd");
		var d2 = new Date();
		var s2 = d2.format("yyyy-MM-dd");
		if (s1 == s2 ){
			isLoadDefaultParams =false;
		}else{
			isLoadDefaultParams = true;
		}
	}else{
		isLoadDefaultParams = true;
	}
	if (isLoadDefaultParams ==false && localStorage.getItem("approvelist_datefrom")){
			var d = new Date(localStorage.getItem("approvelist_datefrom"));
			$('#startDate').val(d.format("yyyy-MM-dd"));
	}else{
			var d = new Date();
			$('#startDate').val(d.format("yyyy-MM-dd"));
	}
	if (isLoadDefaultParams ==false && localStorage.getItem("approvelist_dateto")){
			var d = new Date(localStorage.getItem("approvelist_dateto"));
			$('#endDate').val(d.format("yyyy-MM-dd"));
	}else{
			var d = new Date();
			$('#endDate').val(d.format("yyyy-MM-dd"));
	}
	if(isLoadDefaultParams == false && localStorage.getItem("approvelist_batchno")){
		$('#txtfilter_batchno').val(localStorage.getItem("approvelist_batchno"));
	}else{
		$('#txtfilter_batchno').val('');
	}


}
PigPlan_ApproveList.saveParams = function(){
	var  d =  $('#startDate').val();
	localStorage.setItem("approvelist_datefrom",d);
	var  d =  $('#endDate').val();
	localStorage.setItem("approvelist_dateto",d);
	localStorage.setItem("approvelist_batchno",$('#txtfilter_batchno').val());
	var d = new Date();
	localStorage.setItem("approvelist_saveData",d);

}
PigPlan_ApproveList.checkPermission = function(){
	_loading.start();
	$.post(BaseApp.base_url+'/extapiserver/eas/yhsp/approveOutPlanInfo/permission',
		{
			t:BaseApp.token
		},
		PigPlan_ApproveList.checkPermission_Callback
	);
}
 PigPlan_ApproveList.checkPermission_Callback=function(responseData){
	_loading.end();
	if (responseData.code == 1 || responseData.code == 2  ){
		PigPlan_ApproveList.permissionType  = responseData.code;
		PigPlan_ApproveList.init();
	}else{
		window.location = "error.html?code="+responseData.code;
	}
}
PigPlan_ApproveList.init=function(){
	// var time=new Date();
	// $('#startDate')[0].value=time.format("yyyy-MM-dd");  //！！要这样赋值
	// $('#endDate')[0].value=time.format("yyyy-MM-dd");
	PigPlan_ApproveList.bindEvent();
	PigPlan_ApproveList.loadParams();
	if (PigPlan_ApproveList.permissionType != 2){
		$('#bottom').css({'display':'none'});
	}
	_loading.start();
    $('#listContent tbody').html('');
	PigPlan_ApproveList.addApproveList();	
};
PigPlan_ApproveList.checkPower=function(){
	 _loading.start();
	$.post(BaseApp.base_url+'/extapiserver/eas/yhsp/approveOutPlanInfo/permission',
	{t:BaseApp.token},PigPlan_ApproveList.checkPower_callback);
}
PigPlan_ApproveList.checkPower_callback=function(responseData){
	_loading.end();
	if(responseData.code==0&&responseData.data!=''){
		layer.msg('有权限');
		PigPlan_ApproveList.bindEvent();
	}
	else if(responseData.code==-1){
		layer.msg(responseData.msg);
	}
	else if(responseData.code==-2){
		layer.msg(responseData.msg);
	}
	else if(responseData.code==-3){
		layer.msg(responseData.msg);
	}
	
}
PigPlan_ApproveList.bindEvent=function(){
	$('#startDate').datepicker({format: 'yyyy-mm-dd',
			language: 'zh-CN',
			 autoclose: true});
	$('#endDate').datepicker({format: 'yyyy-mm-dd',
			language: 'zh-CN',
			 autoclose: true});
	$('#dateContent').on('click','#btn_search',function(event){	
		   _loading.start();
		    $('#listContent tbody').html('');
			PigPlan_ApproveList.addApproveList();				
	});
	$('.add').click(function(){
		window.location.href='approve.html';
	});
};

 PigPlan_ApproveList.addApproveList=function(){
 	PigPlan_ApproveList.saveParams();
	$.post(BaseApp.base_url+'/extapiserver/eas/yhsp/approveOutPlanInfo/queryByDate',
	{t:BaseApp.token,
	 startDate:	new Date($('#startDate').val()).getTime(),
	 endDate:new Date($('#endDate').val()).getTime(),
	 search_batchno:$('#txtfilter_batchno').val()
	},PigPlan_ApproveList.addApproveList_callback);
};
PigPlan_ApproveList.addApproveList_callback=function(responseData){
    _loading.end();
	if(responseData.code==0&&responseData.data!=''){
		var htmlList;
		//htmlList+='<thead><tr><td>业务日期</td><td>养殖批次</td><td>状态</td></tr><thead><tbody>'
		for(var i=0;i<responseData.data.length;i++){
			var workDate=new Date(responseData.data[i].outdate);
	        var  wdate=workDate.format("yyyy-MM-dd");
			var itemhtml = $('#id_template_list')[0].innerHTML;
			var biztype;
			itemhtml = itemhtml.replace('@@id@@',responseData.data[i].id);
			itemhtml = itemhtml.replace('@@bizdate@@',wdate);
			itemhtml = itemhtml.replace('@@batchno@@',responseData.data[i].breedingbatch_name);
			//itemhtml = itemhtml.replace('@@biztype@@',responseData.data[i].outtype);								
			//htmlList+='<tr><td>'+wdate +'</td><td>'+responseData.data[i].breedingbatch_name
			//+'</td><td>'+responseData.data[i].outtype+'</td></tr>';\
			if(responseData.data[i].billbasestatus=='10'){
				biztype='新增';
			}
			else if(responseData.data[i].billbasestatus=='20')
			{
				biztype='保存';
			}
			else if(responseData.data[i].billbasestatus=='30')
			{
				biztype='提交';
			}
			else if(responseData.data[i].billbasestatus=='40')
			{
				biztype='审核';
			}
			itemhtml = itemhtml.replace('@@type@@',responseData.data[i].billbasestatus);		
			itemhtml = itemhtml.replace('@@biztype@@',biztype);	
			htmlList = htmlList+itemhtml;
	    }		
		$('#listContent tbody').append(htmlList);
		//htmlList.appendTo($('#listContent tbody'));
		PigPlan_ApproveList.oldData=responseData.data;
	}
	else if(responseData.code==0&&responseData.data==''){
		layer.msg('没有数据');  
		$('#listContent tbody').append('<tr class="no-records-found"><td colspan="3">No matching records found</td></tr>');
	
	}
	else if(responseData.code!=0){
			layer.msg(responseData.msg);  
		}
};
PigPlan_ApproveList.rowClick=function(billid,batchStatus){
    var changeid= encodeURIComponent(billid);//编码函数
     window.location.href='approve.html?batchid='+changeid+'&batchStatus='+batchStatus;
	
};
if (BaseApp.isDebug){
	PigPlan_ApproveList.checkPermission();
}else{
	mxCore.connectWebViewJavascriptBridge(function(bridge){
		mxCore.getMXToken('appid','securityid',function(responseData){
			if (responseData.code == 0 ){
				BaseApp.token=responseData.data.mxtoken;
			}else{
				BaseApp.token='dFG7epyDW3qtapPA845nwozMavyS7x';
			}
			PigPlan_ApproveList.checkPermission();
		});
		mxCore.setTurnbackMode2Normal();
	});
}



