
var PigPlan_otherDelivery={
	permissionType:0,
	addUrl:{},
	baseData:{},//为了检验是否修改过，这个属性用于记录未更改前的记录
	oldData:{}, //保存返回的数据	
	id:'',
	storageorgunitid:'', //库存组织的id
	trantypeid:'',       //事务类型的id
	breedingbatchid:'',   //养殖批次的id
	init:function(){},
	bindEvent:function(){},
	getData:function(){},
	saveData:function(){},
	saveData_callback:function(responseData){},
	saveData_return:function(){},
	checkform:function(){},
	commitData:function(fId){},
	commitData_callback:function(responseData){},
	updateData:function(fId){},
	updateData_callback:function(responseData){},
	checkPermission:function(){},//检查权限
	checkPermission_Callback:function(responseData){},//检查权限返回
	copyObject2BaseData:function(){},
	checkIsChanged:function(){},
	actionList:function(){},

};
PigPlan_otherDelivery.actionList = function(){
	if (PigPlan_otherDelivery.checkIsChanged()){
		layer.confirm('单据数据已经修改，是否放弃修改？',function(){
			window.location = "otherDeliveryList.html";
		},function(){
			layer.closeAll();
		});
	}else{
		window.location.href='otherDeliveryList.html';
	}
}
//增加数字百分比的函数
Number.prototype.toPercent = function(){

	return (Math.round(this * 10000)/100).toFixed(0) + '%';
}
//增加获取数组指定值下标的函数
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
	if (this[i] == val) return i;
	}
	return -1;
};
//增加删除数组里面的指定值的函数
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
	this.splice(index, 1);
	}
};
PigPlan_otherDelivery.checkIsChanged = function(){
	if (PigPlan_otherDelivery.baseData.id!=PigPlan_otherDelivery.id) {
		return true;
	}
	if (PigPlan_otherDelivery.baseData.storageorgunitid!=PigPlan_otherDelivery.storageorgunitid){
		return true;
	}
	if (PigPlan_otherDelivery.baseData.trantypeid!=PigPlan_otherDelivery.trantypeid ){
		return true;
	}
	if(PigPlan_otherDelivery.baseData.breedingbatchid!=PigPlan_otherDelivery.breedingbatchid){
		return true;
	}
	var tmp1 ;
	var tmp2 ;
	tmp1 = PigPlan_otherDelivery.baseData.qty;
	if (isNaN(tmp1)){
		tmp1 = 0;
	}
	tmp2 = parseInt($('#deliveryAmount input').val());
	if (isNaN(tmp2)){
		tmp2 = 0;
	}
	if (tmp1 != tmp2 ){
		return true;
	}
	tmp1 = PigPlan_otherDelivery.baseData.reason;
	if (tmp1 == undefined){
		tmp1 = "";
	}
	tmp2 = $('#deliveryReason input').val();
	if(tmp2 == undefined){
		tmp2 = "";
	}
	if(tmp1 !=tmp2 ){
		return true;
	}
	tmp1 = PigPlan_otherDelivery.baseData.remark;
	if (tmp1 == undefined){
		tmp1 = "";
	}
	tmp2 = $('#remark input').val();
	if (tmp2 == undefined){
		tmp2 = "";
	}
	if(tmp1 != tmp2 ){
		return true;
	}
	if($('#raise input').prop('checked')){
	    tmp2=1; 
	}
	else{
		tmp2=0;
	}
	if (PigPlan_otherDelivery.baseData.ishosted != tmp2 ){
		return true;
	}
	return false;
}
PigPlan_otherDelivery.copyObject2BaseData =function(){
	PigPlan_otherDelivery.baseData = {};
	PigPlan_otherDelivery.baseData.id=PigPlan_otherDelivery.oldData.id;
	PigPlan_otherDelivery.baseData.storageorgunitid=PigPlan_otherDelivery.oldData.storageorgunitid;
	PigPlan_otherDelivery.baseData.trantypeid=PigPlan_otherDelivery.oldData.trantypeid;
	PigPlan_otherDelivery.baseData.breedingbatchid=PigPlan_otherDelivery.oldData.breedingbatchid;
	PigPlan_otherDelivery.baseData.qty=PigPlan_otherDelivery.oldData.qty;
	PigPlan_otherDelivery.baseData.reason=PigPlan_otherDelivery.oldData.reason;
	PigPlan_otherDelivery.baseData.remark=PigPlan_otherDelivery.oldData.remark;
	PigPlan_otherDelivery.baseData.ishosted=PigPlan_otherDelivery.oldData.ishosted;
}
//获取默认库存组织
PigPlan_otherDelivery.loadDefaultStorageOrg=function(){
	$.post(BaseApp.base_url+"/extapiserver/eas/yhsp/userdefaultstorageorg",{
		t:BaseApp.token
	},PigPlan_otherDelivery.loadDefaultStorageOrg_Callback);
}
//获取默认库存组织的回调函数
PigPlan_otherDelivery.loadDefaultStorageOrg_Callback = function(responseData){
	if (responseData.code == 0 ){
		$('#organize input')[0].value=responseData.data.displayname;
		PigPlan_otherDelivery.storageorgunitid=responseData.data.id;
		PigPlan_otherDelivery.baseData.storageorgunitid = PigPlan_otherDelivery.storageorgunitid;
	}
}
PigPlan_otherDelivery.checkPermission = function(){
	_loading.start();
	$.post(BaseApp.base_url+'/extapiserver/eas/yhsp/approveOutPlanInfo/permission',
		{
			t:BaseApp.token
		},
		PigPlan_otherDelivery.checkPermission_Callback
	);
}
PigPlan_otherDelivery.checkPermission_Callback=function(responseData){
	_loading.end();
	if (responseData.code == 1 || responseData.code == 2){
		PigPlan_otherDelivery.permissionType = responseData.code;
		PigPlan_otherDelivery.init();
	}else{
		window.location = "error.html?code="+responseData.code;
	}
}
PigPlan_otherDelivery.setAction4AddNew = function(){
	$("#bottom").html('<span class="queryList">列表</span>  <span class="nav_seprator">   |   </span><span class="save">保存</span>');
	PigPlan_otherDelivery.bindEvent();
}
PigPlan_otherDelivery.setAction4Edit = function(){
	if (PigPlan_otherDelivery.permissionType != 2){
		PigPlan_otherDelivery.setAction4View();
		return ;
	}
	if (PigPlan_otherDelivery.oldData.billbasestatus != '10' 
		&& PigPlan_otherDelivery.oldData.billbasestatus != '20'){
		PigPlan_otherDelivery.setAction4View();
		return ;
	}
	$("#bottom").html('<span class="queryList">列表</span>  <span class="nav_seprator">   |   </span><span class="delete">删除</span> <span class="nav_seprator">   |   </span><span class="commit">提交</span>  <span class="nav_seprator">   |   </span><span class="save">保存</span> ');
	PigPlan_otherDelivery.bindEvent();
}
PigPlan_otherDelivery.setAction4View  =function(){
	$("#bottom").html('<span class="queryList">列表</span>');
	PigPlan_otherDelivery.bindEvent();
}
PigPlan_otherDelivery.init=function(){
	PigPlan_otherDelivery.addUrl=BaseApp.getParamByUrl();
	//PigPlan_otherDelivery.bindEvent();
	//PigPlan_otherDelivery.oldData.images=[];
	if(PigPlan_otherDelivery.addUrl.batchid==null){	
		var time=new Date();	
		var timework=time.format("yyyy-MM-dd");
		$('#date').val(timework); 
		PigPlan_otherDelivery.loadDefaultStorageOrg();
		PigPlan_otherDelivery.baseData = {};
		PigPlan_otherDelivery.baseData.id=PigPlan_otherDelivery.id;
		PigPlan_otherDelivery.baseData.storageorgunitid=PigPlan_otherDelivery.storageorgunitid;
		PigPlan_otherDelivery.baseData.trantypeid=PigPlan_otherDelivery.trantypeid;
		PigPlan_otherDelivery.baseData.breedingbatchid=PigPlan_otherDelivery.breedingbatchid;
		PigPlan_otherDelivery.baseData.qty=parseInt($('#deliveryAmount input').val());
		PigPlan_otherDelivery.baseData.reason=$('#deliveryReason input').val();
		PigPlan_otherDelivery.baseData.remark=$('#remark input').val();
		if($('#raise input').prop('checked')){
		    PigPlan_otherDelivery.baseData.ishosted=1; 
		}
		else{
			PigPlan_otherDelivery.baseData.ishosted=0;
		}
		PigPlan_otherDelivery.setAction4AddNew();
	}
	else {
		if(PigPlan_otherDelivery.addUrl.batchStatus=='10'||PigPlan_otherDelivery.addUrl.batchStatus=='20'){
			PigPlan_otherDelivery.setAction4Edit();
		}
		else{
			PigPlan_otherDelivery.setAction4View();
		}
		 PigPlan_otherDelivery.updateData(PigPlan_otherDelivery.addUrl.batchid);
	}
	PigPlan_otherDelivery.bindEvent();
}
PigPlan_otherDelivery.bindEvent=function(){
	//库存组织
	var  organize_F7=new EAS_F7();
	organize_F7.data_url="/extapiserver/eas/base/org/storageorglist";
	organize_F7.title="库存组织";
	organize_F7.init();	
	$('#organize').unbind('click');
	$('#organize').on('click','input',function(){
		organize_F7.showF7Selector();
	});
	organize_F7.callback_func = function(isCancel,selectedObj){
		$('#organize input')[0].value=selectedObj.value;
		PigPlan_otherDelivery.storageorgunitid=selectedObj.id;
	};
	//事务类型
	var affairType_F7=new EAS_F7();
	affairType_F7.data_url="/extapiserver/eas/yhsp/OtherOutBill/transactiontypelist";
	affairType_F7.title="事务类型";
	affairType_F7.init();	
	affairType_F7.isAutoFetchData=true;
	$('#affairType').unbind('click');
	$('#affairType').on('click','input',function(){
		affairType_F7.showF7Selector();
	});
	affairType_F7.callback_func = function(isCancel,selectedObj){
		$('#affairType input')[0].value=selectedObj.value;
		PigPlan_otherDelivery.trantypeid=selectedObj.id;
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
		PigPlan_otherDelivery.breedingbatchid=selectedObj.id;
	};

	$('.save').unbind('click');	
	$('.save').click(function(){
		PigPlan_otherDelivery.getData();		
	});

	$('.queryList').unbind('click');
	$('.queryList').click(function(){
		if (PigPlan_otherDelivery.checkIsChanged()){
			layer.confirm('单据数据已经修改，是否放弃修改？',function(){
				window.location = "otherDeliveryList.html";
			},function(){
				layer.closeAll();
			});
		}else{
			window.location.href='otherDeliveryList.html';
		}
	});
	$('.commit').unbind('click');
	$('.commit').click(function(){
		PigPlan_otherDelivery.checkform();
	});
	$('.delete').unbind('click');
	$('.delete').click(function(){	
		layer.open({
			style: 'width:60%;',
		    content: '是否删除单据？',
		    btn: ['确认', '取消'],
		    shadeClose: false,
		    yes: function(){		    	
			         PigPlan_otherDelivery.deleteData(PigPlan_otherDelivery.oldData.id);	       
		    }, no: function(){
		    }
		});
		
	});
	$('#choosePic img').unbind('click');
	$("#choosePic img").click(function(){
		mxCore.selectPhoto(function(responseData){
			if (responseData.code == 0 ){
				if (responseData.data != undefined && responseData.data.length >0){
					var imageId = 'img-' + (new Date()).valueOf();
					var imageBase64=responseData.data;
					if(PigPlan_otherDelivery.oldData.images==null||PigPlan_otherDelivery.oldData.images==undefined){
						PigPlan_otherDelivery.oldData.images=[];
						PigPlan_otherDelivery.oldData.images.push(imageId);
					}else{
						PigPlan_otherDelivery.oldData.images.push(imageId);
					}
					mxCore.offlineSaveImage(imageId,imageBase64,function(){
						if (responseData.code == 0 ){
							PigPlan_otherDelivery.uploadImage(imageId,imageBase64);
						}else{
							layer.msg(responseData.msg);
						}
					});
				}
			}else{
				layer.msg(responseData.msg);
			}
		});
	});
}
PigPlan_otherDelivery.getData=function(){
		_loading.start();
		PigPlan_otherDelivery.oldData.id=PigPlan_otherDelivery.id;
		PigPlan_otherDelivery.oldData.storageorgunitid=PigPlan_otherDelivery.storageorgunitid;
		PigPlan_otherDelivery.oldData.trantypeid=PigPlan_otherDelivery.trantypeid;
		PigPlan_otherDelivery.oldData.breedingbatchid=PigPlan_otherDelivery.breedingbatchid;
		PigPlan_otherDelivery.oldData.qty=parseInt($('#deliveryAmount input').val());
		PigPlan_otherDelivery.oldData.reason=$('#deliveryReason input').val();
		PigPlan_otherDelivery.oldData.remark=$('#remark input').val();
		if($('#raise input').prop('checked')){
		    PigPlan_otherDelivery.oldData.ishosted=1; 
		}
		else{
			PigPlan_otherDelivery.oldData.ishosted=0;
		}
		PigPlan_otherDelivery.saveData();
	
	
}
PigPlan_otherDelivery.saveData=function(){
	$.post(BaseApp.base_url+'/extapiserver/eas/yhsp/OtherOutBill/save',{
				t:BaseApp.token,
				billinfo:JSON.stringify(PigPlan_otherDelivery.oldData)
			},PigPlan_otherDelivery.saveData_callback);
}

PigPlan_otherDelivery.saveData_callback=function(responseData){
        if(responseData.code==0){
			_loading.end();			  
			PigPlan_otherDelivery.oldData=responseData.data;
			PigPlan_otherDelivery.copyObject2BaseData();//备份数据，以便退出时候判断
			PigPlan_otherDelivery.id=responseData.data.id;
			PigPlan_otherDelivery.saveData_return();
			layer.open({
				style: 'width:60%;',
			    content: '保存成功！',
			    btn: ['确认'],
			    shadeClose: false,
			    no: function(){
			    }
			});

		}
		else if(responseData.code!=0){
			_loading.end();
			layer.msg(responseData.msg);  
		}
	
}
//检查格式
PigPlan_otherDelivery.checkform=function(){
		if($('#organize input').val()==''){
			layer.msg('库存组织不能为空');     
		}
		else if($('#affairType input').val()==''){
			layer.msg('事务类型不能为空');
		}	
		else if($('#batch input').val()==''){
			layer.msg('养殖批次不能为空');
		}
		else if($('#deliveryAmount input').val()==''){
			layer.msg('出库数量不能为空');
		}
		else if($(".image-div").html()==undefined){
				layer.msg("至少需上传一张图片");
				//return false;
	    }
		else{			
			    PigPlan_otherDelivery.commitData(PigPlan_otherDelivery.oldData.id);	    
		}
}
PigPlan_otherDelivery.commitData=function(fId){
	_loading.start();
	$.post(BaseApp.base_url+'/extapiserver/eas/yhsp/OtherOutBill/submit',{
				t:BaseApp.token,
				id:fId
			},PigPlan_otherDelivery.commitData_callback);
}
PigPlan_otherDelivery.commitData_callback=function(responseData){
	  _loading.end();
	if(responseData.code==0){
		layer.open({
				style: 'width:60%;',
			    content: '单据已提交！',
			    btn: ['确认'],
			    shadeClose: false,
			    yes: function(){
			    	window.location.href='otherDeliveryList.html';
			    }
			}); 
		//window.location.href='otherDelivery.html';
	}
	else if(responseData.code!=0){	
		layer.open({
				style: 'width:60%;',
			    content: responseData.msg
			});
			//layer.msg(responseData.msg);  
		}
}
PigPlan_otherDelivery.deleteData=function(fId){
	_loading.start();
	$.post(BaseApp.base_url+'/extapiserver/eas/yhsp/OtherOutBill/delete',{
				t:BaseApp.token,
				id:fId
			},PigPlan_otherDelivery.deleteData_callback);
}
PigPlan_otherDelivery.deleteData_callback=function(responseData){
	  _loading.end();
	if(responseData.code==0){
		//layer.msg(responseData.msg);  
		layer.open({
				style: 'width:60%;',
			    content: '删除成功',
			    btn: ['确认'],
			    shadeClose: false,
			    yes: function(){
			    	window.location.href='otherDeliveryList.html';
			    }
		}); 
	}
	else if(responseData.code!=0){	
			layer.open({
				style: 'width:60%;',
			    content: '删除失败:'+responseData.msg,
			    btn: ['确认'],
			    shadeClose: false,
			    yes: function(){
			    	
			    }
		}); 
		
		}
}
PigPlan_otherDelivery.saveData_return=function(){
	$('#bill input')[0].value=PigPlan_otherDelivery.oldData.number;
	var time=new Date(PigPlan_otherDelivery.oldData.bizdate);
	$('#workDate input')[0].value=time.format("yyyy-MM-dd");
	$('#organize input')[0].value=PigPlan_otherDelivery.oldData.storageorgunit_name;
	PigPlan_otherDelivery.storageorgunitid=PigPlan_otherDelivery.oldData.storageorgunitid;
	$('#affairType input')[0].value=PigPlan_otherDelivery.oldData.trantype_name;
	PigPlan_otherDelivery.trantypeid=PigPlan_otherDelivery.oldData.trantypeid;
	$('#batch textarea')[0].value=PigPlan_otherDelivery.oldData.breedingbatch_name;
	PigPlan_otherDelivery.breedingbatchid=PigPlan_otherDelivery.oldData.breedingbatchid;
	$('#deliveryAmount input')[0].value=PigPlan_otherDelivery.oldData.qty;
	$('#deliveryReason input')[0].value=PigPlan_otherDelivery.oldData.reason;
	$('#remark input')[0].value=PigPlan_otherDelivery.oldData.remark;
	if(PigPlan_otherDelivery.oldData.ishosted==1){
		$('#raise input').prop('checked','checked');
	}
	if(PigPlan_otherDelivery.oldData.billbasestatus == null 
		|| PigPlan_otherDelivery.oldData.billbasestatus == undefined 
		|| PigPlan_otherDelivery.oldData.billbasestatus=='10'
		|| PigPlan_otherDelivery.oldData.billbasestatus=='20'){
		PigPlan_otherDelivery.setAction4Edit();
	}
	else{
		PigPlan_otherDelivery.setAction4View();
	}
	//PigPlan_otherDelivery.bindEvent();
	$('.save').click(function(){
		PigPlan_otherDelivery.getData();		
	});
	$('.queryList').click(function(){
		if (PigPlan_otherDelivery.checkIsChanged()){
			layer.confirm('单据数据已经修改，是否放弃修改？',function(){
				window.location = "otherDeliveryList.html";
			},function(){
				layer.closeAll();
			});
		}else{
			window.location.href='otherDeliveryList.html';
		}
	});
	$('.commit').click(function(){
		PigPlan_otherDelivery.checkform();
	});
	$('.delete').click(function(){	
		layer.open({
			style: 'width:60%;',
		    content: '是否删除单据？',
		    btn: ['确认', '取消'],
		    shadeClose: false,
		    yes: function(){
		   		       PigPlan_otherDelivery.deleteData(PigPlan_otherDelivery.oldData.id);	       	
		    }, no: function(){
		    }
		});
		
	});
		
}
PigPlan_otherDelivery.updateData=function(fId){
    _loading.start(); 
	$.post(BaseApp.base_url+'/extapiserver/eas/yhsp/OtherOutBill/getById',
	{t:BaseApp.token,
	 id:decodeURIComponent(fId)	  //解码函数
	},PigPlan_otherDelivery.updateData_callback);
}
PigPlan_otherDelivery.updateData_callback=function(responseData){
	_loading.end();
	if(responseData.code==0){
		PigPlan_otherDelivery.oldData=responseData.data;
		PigPlan_otherDelivery.copyObject2BaseData();//吧数据备份起来，以便后续判断是否已经更改
		PigPlan_otherDelivery.id=responseData.data.id;
		$('#bill input')[0].value=responseData.data.number;
		var time=new Date(responseData.data.bizdate);
		//var workDate=time.getFullYear()+'-'+time.getMonth()+1+'-'+time.getDate();
		$('#workDate input')[0].value=time.format("yyyy-MM-dd");
		$('#organize input')[0].value=responseData.data.storageorgunit_name;
		PigPlan_otherDelivery.storageorgunitid=responseData.data.storageorgunitid;
		$('#affairType input')[0].value=responseData.data.trantype_name;
		PigPlan_otherDelivery.trantypeid=responseData.data.trantypeid;
		$('#batch textarea')[0].value=responseData.data.breedingbatch_name;
		PigPlan_otherDelivery.breedingbatchid=responseData.data.breedingbatchid;
		$('#deliveryAmount input')[0].value=responseData.data.qty;
		$('#deliveryReason input')[0].value=responseData.data.reason;
		$('#remark input')[0].value=responseData.data.remark;
		if(responseData.data.ishosted==1){
			$('#raise input').prop('checked','checked');
		}
		PigPlan_otherDelivery.loadImageFromMxcore();
		PigPlan_otherDelivery.setAction4Edit();
		//PigPlan_otherDelivery.bindEvent();
	}
	else if(responseData.code!=0){	
			layer.msg(responseData.msg);  
		}
		
}
//上传照片
PigPlan_otherDelivery.uploadImage=function(imageId,imageBase64){
	var imgUrl = "data:image/png;base64," + imageBase64;
	var html  = "<div class='image-div'>"+
					"<img id='"+imageId+"' src='"+imgUrl+"' onclick=\"PigPlan_otherDelivery.loadBigImage('" + imageId+"','"+imgUrl+"')\">"+
					"<div class='image-upload-div'>0%</div>"+
					"<div class='image-delete-btn' onClick=\"PigPlan_otherDelivery.deleteImage('" +imageId+"')\">"+
						"<img style='width:auto;height:auto' src ='img/deleteimage.png'>"+
					"</div>"+
				"</div >";
	$(".image-container").before(html);
	var uploadProcess=setInterval(function(){    //此方法不停地调用函数
		mxCore.getOfflineImageSendProgress(imageId,function(responseData){
			if(responseData.code==0){
				if(responseData.data.status==2){
					var percentComplete=responseData.data.sentcount/responseData.data.totalcount;
					$("#"+imageId).parent().find(".image-upload-div").text(percentComplete.toPercent());
				}else if(responseData.data.status==3){
					clearInterval(uploadProcess);
					$("#"+imageId).parent().find(".image-upload-div").text("100%").css("display","none");
				}else if(responseData.data.status==-1){
					$("#"+imageId).parent().find(".image-upload-div").text("上传失败");
				}else{
					//$("#"+imageId).parent().find(".image-upload-div").text("即将上传");
				}
			}else{
				layer.msg(responseData.msg);
			}
		})
	},2000);
}

PigPlan_otherDelivery.downloadImage=function(imageId){
		var imgUrl = "";
		var html  = "<div class='image-div'>"+
						"<img id='"+imageId+"' src='img/deleteimage.png' onclick=\"PigPlan_otherDelivery.loadBigImage('" + imageId+"','"+imgUrl+"')\">"+
						"<div class='image-upload-div'>0%</div>"+
						"<div class='image-delete-btn' onClick=\"PigPlan_otherDelivery.deleteImage('" +imageId+"')\">"+
							"<img style='width:auto;height:auto' src ='img/deleteimage.png'>"+
						"</div>"+
					"</div>";
		$(".image-container").before(html);
		var downloadProcess=setInterval(function(){
			mxCore.getOfflineImageReceiveProgress(imageId,function(responseData){
				if(responseData.code==0){
					if(responseData.data.status==12){
						var percentComplete=responseData.data.receivecount/responseData.data.totalcount;
						$("#"+imageId).parent().find(".image-upload-div").text(percentComplete.toPercent());
						// $(".test").parent().find(".image-upload-div").text(percentComplete.toPercent());
					}else if(responseData.data.status==2){
						var percentComplete=responseData.data.sentcount/responseData.data.totalcount;
						$("#"+imageId).parent().find(".image-upload-div").text(percentComplete.toPercent());
						// $(".test").parent().find(".image-upload-div").text(percentComplete.toPercent());
					}else if(responseData.data.status==13
								|| responseData.data.status==0){
						clearInterval(downloadProcess);
						mxCore.getOfflineImage(imageId,function(responseData){
							if (responseData.code == 0 ){
								var imageBase64="data:image/jpeg;base64," + responseData.data;
								$("#"+imageId).attr("src",imageBase64);
								// $(".test").attr("src",responseData.data);
								$("#"+imageId).parent().find(".image-upload-div").css("display","none");
								// $(".test").parent().find(".image-upload-div").css("display","none");
							}
						});
					}else if(responseData.data.status==11){
						$("#"+imageId).parent().find(".image-upload-div").text("即将下载");
					}else if(responseData.data.status==1){
						$("#"+imageId).parent().find(".image-upload-div").text("即将上传");
					}else if(responseData.data.status==-11){
						$("#"+imageId).parent().find(".image-upload-div").text("下载失败");
					}else if(responseData.data.status==-1){
						$("#"+imageId).parent().find(".image-upload-div").text("上传失败");
					}
				}else{
					layer.msg(responseData.msg);
				}
			})
		},2000);
}

PigPlan_otherDelivery.renderImage=function(imageId,imageBase64){
	var imgUrl="data:image/png;base64," + imageBase64;
	var html  = "<div class='image-div'>"+
					"<img id='"+imageId+"' src='"+imgUrl+"' onclick=\"PigPlan_otherDelivery.loadBigImage('" + imageId+"','"+imgUrl+"')\">"+
					"<div class='image-delete-btn' onClick=\"PigPlan_otherDelivery.deleteImage('" +imageId+"')\">"+
						"<img style='width:auto;height:auto' src ='img/deleteimage.png'>"+
					"</div>"+
				"</div>";
	$(".image-container").before(html);
}

PigPlan_otherDelivery.closeBigImage=function(target){
	$(target).parent().css("display","none");
	$("body").removeClass("body-overflow-hidden");
}

PigPlan_otherDelivery.loadBigImage=function(imageId,imgUrl){
	var clientHeight=document.body.clientHeight;
	$(".big-image-div").show().height(clientHeight);
	$("body").addClass("body-overflow-hidden");
	$(".big-image-div img").attr("src",imgUrl);
}

PigPlan_otherDelivery.deleteImage=function(imageId){
	layer.open({
			style: 'width:60%;',
		    content: '确定删除图片么？',
		    btn: ['确认', '取消'],
		    shadeClose: false,
		    yes: function(){
		    	if(PigPlan_otherDelivery.oldData.id){
			    	$.post(BaseApp.base_url+'/extapiserver/eas/yhsp/OtherOutBill/deleteimage',
						{
							t: BaseApp.token,
							id: PigPlan_otherDelivery.oldData.id,
							imageid: imageId
						},
						PigPlan_otherDelivery.deleteImage_Callback
			          );
	          }
		    	else{
		    		$("#"+imageId).parent().remove();
		    		PigPlan_otherDelivery.oldData.images.remove(imageId);
		       // layer.msg("删除图片成功");
		    	}
			layer.closeAll();
			//layer.msg("删除图片成功");
		    }, no: function(){
		    }
		});
	
}

PigPlan_otherDelivery.deleteImage_Callback=function(responseData){
	if (responseData.code == 0){
		var imageId=responseData.data;
		$("#"+imageId).parent().remove();
		PigPlan_otherDelivery.oldData.images.remove(imageId);
		layer.msg("删除图片成功");
	}else{
		layer.msg("删除图片失败:"+responseData.msg);
	}
}

PigPlan_otherDelivery.checkAndDownImge = function(imageId){
	mxCore.getOfflineImage(imageId,function(responseData){
		if (responseData.code == 0 ){
			PigPlan_otherDelivery.renderImage(imageId,responseData.data);
		}
		if(responseData.code == 1){
			PigPlan_otherDelivery.downloadImage(imageId);
		}
	});
}

PigPlan_otherDelivery.loadImageFromMxcore=function(){
	$(".image-div").remove();
	var imageList=PigPlan_otherDelivery.oldData.images;
	if(imageList!=null){
		for(var i=0;i<imageList.length;i++){
			PigPlan_otherDelivery.checkAndDownImge(imageList[i]);
		}
	}
}



function ValidateNumber(e, pnumber){ //检查输入的是否为数字
	if (!/^\d+$/.test(pnumber)){
		$(e).val(/^\d+/.exec($(e).val()));
	}
	return false;
}
if(BaseApp.isDebug){
	PigPlan_otherDelivery.checkPermission();
}else{
	mxCore.connectWebViewJavascriptBridge(function(bridge){
		mxCore.getMXToken('appid','securityid',function(responseData){
			if (responseData.code == 0 ){
				BaseApp.token=responseData.data.mxtoken;
			}else{
				BaseApp.token='9q37KJZjux63UZJINc7LNXnbtazOSH';
			}
			PigPlan_otherDelivery.checkPermission();

		});
		mxCore.setTurnbackMode2Custom('PigPlan_otherDelivery.actionList');
	});
}
