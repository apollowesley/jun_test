var PigPlan_Approve={
	addUrl:{},
	baseData:{},//备份数据，以便退出是进行修改检测
	oldData:{}, //保存返回的数据
	approveData:{}, //保存时发送给后台的界面数据
	id:'',
	storageorgunitid:'', //库存组织的id
	breedingbatchid:'',   //养殖批次的id
	permissionType:0,
	init:function(){},
	bindEvent:function(){},
	getData:function(){},
	refreshPage:function(){},
	saveData:function(){},
	saveData_callback:function(responseData){},
	checkform:function(){},
	commitData:function(fId){},
	commitData_callback:function(responseData){},
	saveData_return:function(){},
	updateData:function(fId){},
	updateData_callback:function(responseData){},
	checkPermission:function(){},//检查权限
	checkPermission_Callback:function(responseData){},//检查权限返回
	copyData2Backup:function(){},
	checkIsChanged:function(){},
	setActionBar4AddNew:function(){},
	setActionBar4Edit:function(){},
	setActionBar4View:function(){},
	actionList:function(){},
}
PigPlan_Approve.actionList =function(){
	if (PigPlan_Approve.checkIsChanged()){
		layer.confirm('单据数据已经修改，是否放弃修改？',function(){
			window.location = "approveList.html";
		},function(){
			layer.closeAll();
		});
	}else{
		window.location.href='approveList.html';
	}
}
PigPlan_Approve.setActionBar4AddNew  =function(){
	$("#bottom").html('<span class="queryList">列表</span>  <span class="nav_seprator">   |   </span><span class="save">保存</span>');

	PigPlan_Approve.bindEvent();
}
PigPlan_Approve.setActionBar4Edit = function(){
	if (PigPlan_Approve.permissionType == 1){
		PigPlan_Approve.setActionBar4View();
		return ;
	}
	if (PigPlan_Approve.oldData.billbasestatus != '10' 
		&& PigPlan_Approve.oldData.billbasestatus!= '20' ){
		PigPlan_Approve.setActionBar4View();
		return ;
	}
	$("#bottom").html('<span class="queryList">列表</span>  <span class="nav_seprator">   |   </span><span class="delete">删除</span> <span class="nav_seprator">   |   </span><span class="commit">提交</span>  <span class="nav_seprator">   |   </span><span class="save">保存</span> ');
	PigPlan_Approve.bindEvent();
}
PigPlan_Approve.setActionBar4View = function(){
	$("#bottom").html('<span class="queryList">列表</span>');
	PigPlan_Approve.bindEvent();
}
PigPlan_Approve.copyData2Backup = function(){
	PigPlan_Approve.baseData.id = PigPlan_Approve.oldData.id ;
	PigPlan_Approve.baseData.storageorgunitid = PigPlan_Approve.oldData.storageorgunitid ;
	PigPlan_Approve.baseData.breedingbatchid = PigPlan_Approve.oldData.breedingbatchid ;
	PigPlan_Approve.baseData.outtype = PigPlan_Approve.oldData.outtype ;
	PigPlan_Approve.baseData.outdate = PigPlan_Approve.oldData.outdate ;
	PigPlan_Approve.baseData.qty = PigPlan_Approve.oldData.qty ;
	PigPlan_Approve.baseData.exceptAveWeight = PigPlan_Approve.oldData.exceptAveWeight ;
	PigPlan_Approve.baseData.description = PigPlan_Approve.oldData.description ;
}
PigPlan_Approve.checkIsChanged = function(){
	if (PigPlan_Approve.baseData.id != PigPlan_Approve.oldData.id){
		return true;
	}
	if (PigPlan_Approve.baseData.storageorgunitid != PigPlan_Approve.storageorgunitid){
		return true;
	}
	if (PigPlan_Approve.baseData.breedingbatchid != PigPlan_Approve.breedingbatchid){
		return true;
	}
	if (PigPlan_Approve.baseData.outtype!=$('#type select').val()) {
		return true;
	}
	var tmp1 = PigPlan_Approve.baseData.outdate;
	if (tmp1 != undefined){
		tmp1 = new Date(tmp1);
		tmp1 = tmp1.format("yyyy-MM-dd");
	}
	var tmp2 = new Date($('#approveDate input').val());
	tmp2 = tmp2.format("yyyy-MM-dd");
	if (tmp1 !=tmp2) {
		return true;
	}
	var tmp1 = parseInt(PigPlan_Approve.baseData.qty);
	if (isNaN(tmp1)){
		tmp1 = 0;
	}
	var tmp2 = parseInt($('#approveAmount input').val());
	if (isNaN(tmp2) ){
		tmp2 = 0;
	}
	if (tmp1!=tmp2){
		return true;
	}
	tmp1 = PigPlan_Approve.baseData.exceptAveWeight;
	if (tmp1 == undefined){
		tmp1=  "";
	}
	tmp2 = $('#exceptavgweight input').val();
	if (tmp2 == undefined){
		tmp2 = "";
	}
	if (tmp1 != tmp2) {
		return true;
	}
	tmp1 = PigPlan_Approve.baseData.description;
	if (tmp1 == undefined){
		tmp1 = "";
	}
	tmp2 = $('#remark input').val();
	if (tmp2 == undefined){
		tmp2 = "";
	}
	if (tmp1 != tmp2) {
		return true;
	}
	return false;
}
PigPlan_Approve.checkPermission = function(){
	_loading.start();
	$.post(BaseApp.base_url+'/extapiserver/eas/yhsp/approveOutPlanInfo/permission',
		{
			t:BaseApp.token
		},
		PigPlan_Approve.checkPermission_Callback
	);
}
PigPlan_Approve.checkPermission_Callback=function(responseData){
	_loading.end();
	if (responseData.code == 1 || responseData.code == 2 ){
		PigPlan_Approve.permissionType = responseData.code;
		PigPlan_Approve.init();
	}else{
		window.location = "error.html?code="+responseData.code;
	}
}
//获取默认库存组织
PigPlan_Approve.loadDefaultStorageOrg=function(){
	$.post(BaseApp.base_url+"/extapiserver/eas/yhsp/userdefaultstorageorg",{
		t:BaseApp.token
	},PigPlan_Approve.loadDefaultStorageOrg_Callback);
}
//获取默认库存组织的回调函数
PigPlan_Approve.loadDefaultStorageOrg_Callback = function(responseData){
	if (responseData.code == 0 ){
		$('#organize input')[0].value=responseData.data.displayname;
		PigPlan_Approve.storageorgunitid=responseData.data.id;
		PigPlan_Approve.baseData.storageorgunitid = PigPlan_Approve.storageorgunitid;
	}
}
PigPlan_Approve.init=function(){
	PigPlan_Approve.addUrl=BaseApp.getParamByUrl();
	$('#approveDate input').datepicker({format: 'yyyy-mm-dd',
			language: 'zh-CN',
			 autoclose: true});
	//$('type select')[0].value='常规出栏';
	if(PigPlan_Approve.addUrl.batchid==null){	
		var time=new Date();		
		$('#workDate input')[0].value=time.format("yyyy-MM-dd");  //！！要这样赋值
		$('#approveDate input')[0].value=time.format("yyyy-MM-dd");
		//$('type select')[0].value='常规出栏';
		$("#bottom").append('<span class="queryList">列表</span>  <span class="nav_seprator">   |   </span><span class="save">保存</span>');
		PigPlan_Approve.loadDefaultStorageOrg();
		PigPlan_Approve.baseData.breedingbatchid = "";
		PigPlan_Approve.baseData.id = PigPlan_Approve.oldData.id;
		PigPlan_Approve.baseData.outtype=$('#type select').val();
		PigPlan_Approve.baseData.outdate=new Date($('#approveDate input').val()).getTime();
		PigPlan_Approve.baseData.qty=parseInt($('#approveAmount input').val());
		PigPlan_Approve.baseData.exceptAveWeight = $('#exceptavgweight input').val();
		PigPlan_Approve.baseData.description=$('#remark input').val();
		PigPlan_Approve.setActionBar4AddNew();
	}
	else {
		if(PigPlan_Approve.addUrl.batchStatus=='10'||PigPlan_Approve.addUrl.batchStatus=='20'){
			$("#bottom").append('<span class="queryList">列表</span>  <span class="nav_seprator">   |   </span><span class="delete">删除</span> <span class="nav_seprator">   |   </span><span class="commit">提交</span>  <span class="nav_seprator">   |   </span><span class="save">保存</span> ');
		}
		else{
			$("#bottom").append('<span class="queryList">列表</span>');
		}
		 PigPlan_Approve.updateData(PigPlan_Approve.addUrl.batchid);
	}
	PigPlan_Approve.bindEvent();
}

PigPlan_Approve.bindEvent=function(){
	//库存组织
	var  organize_F7=new EAS_F7();
	organize_F7.data_url="/extapiserver/eas/base/org/storageorglist";
	organize_F7.title="库存组织";
	organize_F7.init();	
	$('#organize input').unbind('click');
	$('#organize input').click(function(){
		organize_F7.showF7Selector();
	});
	organize_F7.callback_func = function(isCancel,selectedObj){
		$('#organize input')[0].value=selectedObj.value;
		PigPlan_Approve.storageorgunitid=selectedObj.id;
	};
	//养殖批次
	var batch_F7=new EAS_F7();
	batch_F7.data_url="/extapiserver/eas/yhsp/pigbatchlist";
	batch_F7.title="养殖批次";
	batch_F7.init();
	$('#batch textarea').unbind('click');
	$('#batch textarea').click(function(){
		batch_F7.showF7Selector();
	});
	batch_F7.callback_func = function(isCancel,selectedObj){
		$('#batch textarea')[0].value=selectedObj.value;
		PigPlan_Approve.breedingbatchid=selectedObj.id;
	};
	$('.save').unbind('click');
	$('.save').click(function(){
		PigPlan_Approve.getData();		
	});
	$('.queryList').unbind('click');
	$('.queryList').click(function(){
		if (PigPlan_Approve.checkIsChanged()){
			layer.confirm('单据数据已经修改，是否放弃修改？',function(){
				window.location = "approveList.html";
			},function(){
				layer.closeAll();
			});
		}else{
			window.location.href='approveList.html';
		}
	});
	$('.commit').unbind('click');
	$('.commit').click(function(){
		PigPlan_Approve.checkform();
		//PigPlan_Approve.commitData(PigPlan_Approve.addUrl.batchid);	
	});
	$('.delete').unbind('click');
	$('.delete').click(function(){	
		layer.open({
			style: 'width:60%;',
		    content: '是否删除单据？',
		    btn: ['确认', '取消'],
		    shadeClose: false,
		    yes: function(){
			    PigPlan_Approve.deleteData(PigPlan_Approve.oldData.id);
		    }, no: function(){
		    }
		});
	});
}
PigPlan_Approve.getData=function(){
		_loading.start();
		PigPlan_Approve.oldData.id=PigPlan_Approve.id;
		PigPlan_Approve.oldData.storageorgunitid=PigPlan_Approve.storageorgunitid;
		PigPlan_Approve.oldData.breedingbatchid=PigPlan_Approve.breedingbatchid;
		PigPlan_Approve.oldData.outtype=$('#type select').val();
		PigPlan_Approve.oldData.outdate=new Date($('#approveDate input').val()).getTime();//重要，将时间转为一串数字
		PigPlan_Approve.oldData.qty=parseInt($('#approveAmount input').val());
		PigPlan_Approve.oldData.exceptAveWeight = $('#exceptavgweight input').val();
		PigPlan_Approve.oldData.description=$('#remark input').val();
		if($('#judge input').prop('checked')){
		    PigPlan_Approve.oldData.isCloser=1;
		}
		else{
			PigPlan_Approve.oldData.isCloser=0;
		}
		PigPlan_Approve.saveData();
	
	
}
PigPlan_Approve.saveData=function(){
	$.post(BaseApp.base_url+'/extapiserver/eas/yhsp/approveOutPlanInfo/save',{
				t:BaseApp.token,
				billinfo:JSON.stringify(PigPlan_Approve.oldData)
			},PigPlan_Approve.saveData_callback);
	
}

PigPlan_Approve.saveData_callback=function(responseData){
        if(responseData.code==0){
			_loading.end();		  
			PigPlan_Approve.oldData=responseData.data;
			PigPlan_Approve.id=responseData.data.id;
			PigPlan_Approve.copyData2Backup();
			PigPlan_Approve.saveData_return();
			layer.open({
				style: 'width:60%;font-size:18px;',
			    content: '保存成功！',
			    btn: ['确认'],
			    shadeClose: false,
			    no: function(){
			    	//PigPlan_Approve.saveData_return();
			    }
		});
			//PigPlan_Approve.refreshPage();
		}
		else if(responseData.code!=0){
			_loading.end();
			layer.msg(responseData.msg);  
		}
	
}
//检查格式
PigPlan_Approve.checkform=function(){
	    if($('#organize input').val()==''){
			layer.msg('库存组织不能为空');  
			return false;
		}
		else if($('#batch textarea').val()==''){
			layer.msg('养殖批次不能为空');
		}
		else if($('#type select').val()==null){
			layer.msg('出栏类型不能为空');
		}	
		else if($('#approveDate input').val()==''){
			layer.msg('核定出栏日期不能为空');
		}
		else if($('#approveAmount input').val()==''){
			layer.msg('核定出栏数量不能为空');
		}
		else{
			
			PigPlan_Approve.commitData(PigPlan_Approve.oldData.id);				
		}
}
PigPlan_Approve.commitData=function(fId){
	_loading.start();
	$.post(BaseApp.base_url+'/extapiserver/eas/yhsp/approveOutPlan/submit',{
				t:BaseApp.token,
				id:fId
			},PigPlan_Approve.commitData_callback);
}
PigPlan_Approve.commitData_callback=function(responseData){
	  _loading.end();
	if(responseData.code==0){
		layer.open({
				style: 'width:60%;',
			    content: '单据已提交！',
			    btn: ['确认'],
			    shadeClose: false,
			    yes: function(){
			    	window.location.href='approveList.html';
			    }
			}); 
		//window.location.href='otherDelivery.html';
	}
	else if(responseData.code!=0){	
			layer.open({
				style: 'width:60%;',
			    content: responseData.msg
			});
		}
}
PigPlan_Approve.deleteData=function(fId){
	_loading.start();
	$.post(BaseApp.base_url+'/extapiserver/eas/yhsp/approveOutPlanInfo/delete',{
				t:BaseApp.token,
				id:fId
			},PigPlan_Approve.deleteData_callback);
}
PigPlan_Approve.deleteData_callback=function(responseData){
	  _loading.end();
	if(responseData.code==0){
		layer.open({
				style: 'width:60%;',
			    content: '删除成功',
			    btn: ['确认'],
			    shadeClose: false,
			    yes: function(){
			    	window.location.href='approve.html';
			    }
		}); 
		//window.location.href='approve.html';
	}
	else if(responseData.code!=0){	
			layer.msg(responseData.msg);  
		}
}
PigPlan_Approve.saveData_return=function(){
	$('#bill input')[0].value=PigPlan_Approve.oldData.number;
	var time=new Date(PigPlan_Approve.oldData.bizdate);
	//var workDate=time.getFullYear()+'-'+time.getMonth()+1+'-'+time.getDate();
	$('#workDate input')[0].value=time.format("yyyy-MM-dd");
	$('#organize input')[0].value=PigPlan_Approve.oldData.storageorgunit_name;
	PigPlan_Approve.storageorgunitid=PigPlan_Approve.oldData.storageorgunitid;
	$('#batch textarea')[0].value=PigPlan_Approve.oldData.breedingbatch_name;
	PigPlan_Approve.breedingbatchid=PigPlan_Approve.oldData.breedingbatchid;
	$('#type select')[0].value=PigPlan_Approve.oldData.outtype;
	time=new Date(PigPlan_Approve.oldData.outdate);
	$('#approveDate input')[0].value=time.format("yyyy-MM-dd");
	$('#approveAmount input')[0].value=PigPlan_Approve.oldData.qty;
	$('#exceptavgweight input')[0].value=PigPlan_Approve.oldData.exceptAveWeight;
	$('#remark input')[0].value=PigPlan_Approve.oldData.description;
	if(PigPlan_Approve.oldData.isCloser==1){
		$('#judge input').prop('checked','checked');
	}
	if(PigPlan_Approve.oldData.batchStatus == null 
		|| PigPlan_Approve.oldData.batchStatus == undefined 
		|| PigPlan_Approve.oldData.batchStatus=='10'
		|| PigPlan_Approve.oldData.batchStatus=='20'){
		$(".save").hide();
		$(".queryList").hide();
		$('.delete').hide();
		$('.commit').hide();
		$('.nav_seprator').hide();
		$("#bottom").append('<span class="queryList">列表</span>  <span class="nav_seprator">   |   </span><span class="delete">删除</span> <span class="nav_seprator">   |   </span><span class="commit">提交</span>  <span class="nav_seprator">   |   </span><span class="save">保存</span> ');	
	}
	else{
		$("#bottom").append('<span class="queryList">列表</span>');
	}
	//PigPlan_otherDelivery.bindEvent();
	$('.save').click(function(){
		PigPlan_Approve.getData();		
	});
	$('.queryList').click(function(){
		if (PigPlan_Approve.checkIsChanged()){
			layer.confirm('单据数据已经修改，是否放弃修改？',function(){
				window.location = "approveList.html";
			},function(){
				layer.closeAll();
			});
		}else{
			window.location.href='approveList.html';
		}
	});
	$('.commit').click(function(){
		PigPlan_Approve.checkform();
		
	});
	$('.delete').click(function(){	
		layer.open({
			style: 'width:60%;',
		    content: '是否删除单据？',
		    btn: ['确认', '取消'],
		    shadeClose: false,
		    yes: function(){		    
			         PigPlan_Approve.deleteData(PigPlan_Approve.oldData.id);		         	    					
		    }, no: function(){
		    }
		});
		
	});
}
PigPlan_Approve.updateData=function(fId){
    _loading.start(); 
	$.post(BaseApp.base_url+'/extapiserver/eas/yhsp/approveOutPlanInfo/get',
	{t:BaseApp.token,
	 id:decodeURIComponent(fId)	  //解码函数
	},PigPlan_Approve.updateData_callback);
}
PigPlan_Approve.updateData_callback=function(responseData){
	_loading.end();
	if(responseData.code==0){
		PigPlan_Approve.oldData=responseData.data;
		PigPlan_Approve.id=responseData.data.id;
		PigPlan_Approve.copyData2Backup();//把单据数据备份起来，以便退出时校验是否已经发生修改
		var time=new Date(responseData.data.bizdate);
		//var workDate=time.getFullYear()+'-'+time.getMonth()+1+'-'+time.getDate();
		$('#workDate input')[0].value=time.format("yyyy-MM-dd");
		$('#organize input')[0].value=responseData.data.storageorgunit_name;
		PigPlan_Approve.storageorgunitid=responseData.data.storageorgunitid;
		$('#batch textarea')[0].value=responseData.data.breedingbatch_name;
		PigPlan_Approve.breedingbatchid=responseData.data.breedingbatchid;
		$('#type select')[0].value=responseData.data.outtype;
		time=new Date(responseData.data.outdate);
		$('#approveDate input')[0].value=time.format("yyyy-MM-dd");
		$('#approveAmount input')[0].value=responseData.data.qty;
		$('#exceptavgweight input')[0].value=responseData.data.exceptAveWeight;
		$('#remark input')[0].value=responseData.data.description;
		if(responseData.data.isCloser==1){
			$('#judge input').prop('checked','checked');
		}
		PigPlan_Approve.setActionBar4Edit();
		
	}
	else if(responseData.code!=0){	
			layer.msg(responseData.msg);  
		}
		
}
if(BaseApp.isDebug){
	PigPlan_Approve.init();
}else{
	mxCore.connectWebViewJavascriptBridge(function(bridge){
		mxCore.getMXToken('appid','securityid',function(responseData){
			if (responseData.code == 0 ){
				BaseApp.token=responseData.data.mxtoken;
			}else{
				BaseApp.token='dFG7epyDW3qtapPA845nwozMavyS7x';
			}
			PigPlan_Approve.checkPermission();
		});
		mxCore.setTurnbackMode2Custom('PigPlan_Approve.actionList');
	});
}


function ValidateNumber(e, pnumber){ //检查输入的是否为数字
	if (!/^\d+$/.test(pnumber)){
		$(e).val(/^\d+/.exec($(e).val()));
	}
	return false;
}
