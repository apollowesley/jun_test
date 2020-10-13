var M_PigPlanEdit = {
		billid: '', //记录当前正在处理的单据id
		dialogType: 1,
		oldData: {},
		newData: {},
		breedbatchid: [],
		breedingbatchid:'',
		oldbreedingbatchid: [],
		detailId: [],
		qty: [],
		oldqty: [],
		totalQty: [],
		oldtotalQty: [],
		checkIsModify: function() {}, //检查单据中的数据是否被修改
		init: function() {},
		init_AddNew: function() {}, //表示新建增加数据状态
		init_ViewOrEdit: function() {}, //表示观看或编辑状态
		bindEvent: function() {},
		getData: function() {}, //获取表单的各项数据
		saveData: function() {}, //保存表单的数据
		saveData_callback: function(responseData) {},
		showData: function() {},
		updateData: function(fId) {}, //加载数据
		updateData_callback: function(responseData) {},
		checkform: function() {},
		checkPermission: function() {}, //检查权限
		checkPermission_Callback: function(responseData) {},
		dialogEvent: function(obj) {}, //弹出层事件函数
		sumData: function() {}, //合计数据
		changeUIForAddNew: function() {}, //改变底部按钮，用来新增数据
		changeUIForEdit: function() {}, //改变底部按钮，用来编辑表单
		changeUIForView: function() {}, //改变底部按钮，用来观察表单
	}
	//增加数字百分比的函数
Number.prototype.toPercent = function() {

		return (Math.round(this * 10000) / 100).toFixed(0) + '%';
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
M_PigPlanEdit.checkPermission = function() {
	_loading.start();
	$.post(BaseApp.base_url + '/extapiserver/eas/yhsp/approveOutPlanInfo/permission', {
			t: BaseApp.token
		},
		M_PigPlanEdit.checkPermission_Callback
	);
}
M_PigPlanEdit.checkPermission_Callback = function(responseData) {
	_loading.end();
	if (responseData.code == 0) {
		M_PigPlanEdit.init();
	} else {
		window.location = "error.html?code=" + responseData.code;
	}
}
M_PigPlanEdit.init = function() {
	//	M_PigPlanEdit.addUrl = mxCore.getQueryString("batchid")
	//M_PigPlanEdit.bindEvent();
	//M_PigPlanEdit.oldData.images=[];
	if (mxCore.getQueryString("batchid") == null) {
		M_PigPlanEdit.init_AddNew();
		//M_PigPlanEdit.loadDefaultStorageOrg();
		//PigPlan_Approve.checkPower();
		//M_PigPlanEdit.bindEvent();
	} else {
		M_PigPlanEdit.init_ViewOrEdit();
	}
	M_PigPlanEdit.bindEvent();
}
M_PigPlanEdit.init_AddNew = function() {
	var time = new Date();
	var timework = time.format("yyyy-MM-dd");
	$('#workDate').val(timework);
	M_PigPlanEdit.changeUIForAddNew();
}

M_PigPlanEdit.init_ViewOrEdit = function() {
	if (mxCore.getQueryString("batchStatus") == '10' || mxCore.getQueryString("batchStatus") == '20' || mxCore.getQueryString("batchStatus") == '30') {
		M_PigPlanEdit.changeUIForEdit();
	} else {
		M_PigPlanEdit.changeUIForView();
	}
	M_PigPlanEdit.updateData(mxCore.getQueryString("batchid"));
}
M_PigPlanEdit.changeUIForAddNew = function() {
	$("#bottom").append('<span class="queryList">列表</span>    <span class="nav_seprator">   |   </span><span class="save">保存</span>');
}
M_PigPlanEdit.changeUIForEdit = function() {
	$("#bottom").append('<span class="queryList">列表</span>  <span class="nav_seprator">   |   </span><span class="delete">删除</span> <span class="nav_seprator">   |   </span><span class="commit">提交</span>  <span class="nav_seprator">   |   </span><span class="save">保存</span> ');
}
M_PigPlanEdit.changeUIForView = function() {
	$("#bottom").append('<span class="queryList">列表</span>');
}
M_PigPlanEdit.bindEvent = function() {
	//库存组织
	var organize_F7 = new EAS_F7();
	organize_F7.data_url = "/extapiserver/eas/base/org/storageorglist";
	organize_F7.title = "库存组织";
	organize_F7.init();
	$('#organize').on('click', function() {
		organize_F7.showF7Selector();
	});
	organize_F7.callback_func = function(isCancel, selectedObj) {
		$('#organize')[0].value = selectedObj.value;
		M_PigPlanEdit.storageorgunitid = selectedObj.id;
	};
	//事务类型
	var affairType_F7 = new EAS_F7();
	affairType_F7.data_url = "/extapiserver/eas/yhsp/OtherOutBill/transactiontypelist";
	affairType_F7.title = "事务类型";
	affairType_F7.init();
	affairType_F7.isAutoFetchData = true;
	$('#affairType').on('click', function() {
		affairType_F7.showF7Selector();
	});
	affairType_F7.callback_func = function(isCancel, selectedObj) {
		$('#affairType')[0].value = selectedObj.value;
		M_PigPlanEdit.trantypeid = selectedObj.id;
	};
	//运输车辆
	var car_F7 = new EAS_F7();
	car_F7.data_url = "/extapiserver/eas/yhsp/pigcarlist";
	car_F7.title = "运输车辆";
	car_F7.init();
	$('#transport').click(function() {
		car_F7.showF7Selector();
	});
	car_F7.callback_func = function(isCancel, selectedObj) {
		$('#transport')[0].value = selectedObj.value;
		M_PigPlanEdit.pigcarid = selectedObj.id;
	};
	//养殖批次
	var batch_F7 = new EAS_F7();
	batch_F7.data_url = "/extapiserver/eas/yhsp/pigbatchlist";
	batch_F7.title = "养殖批次";
	batch_F7.init();
	$('#batch').click(function() {
		batch_F7.showF7Selector();
	});
	batch_F7.callback_func = function(isCancel, selectedObj) {
		$('#batch')[0].value = selectedObj.value;
		M_PigPlanEdit.breedingbatchid = selectedObj.id;
	};
	$('.juTou').click(function() {
		$('.juTou').addClass('active');
		$('.fengLu').removeClass('active');
		$('#mainContent').show();
		$('#outputContent').hide();
	});
	$('.fengLu').click(function() {
		$('.fengLu').addClass('active');
		$('.juTou').removeClass('active');
		$('#mainContent').hide();
		$('#outputContent').show();
	});
	$("#btn-scan").click(function() {
		mxCore.getQRCode(function(responseData) {
			if (responseData.code == 0) {
				$("#code").val(responseData.data);
				M_PigPlanEdit.oldData.othernumber = responseData.data;
			} else {
				layer.msg(responseData.msg);
			}
		});
	});
	$('#add-btn').unbind(clickEvent).on(clickEvent, function() {
		M_PigPlanEdit.dialogType = 1;
		$('.dialog-wapper').show();
		$('#batch')[0].value = '';
		$('#amount')[0].value = '';
		$('#pigWeight')[0].value = '';
		$('#judge').prop('checked', false);
		//$('#bottom').css('background-color','white');
		$('#bottom').css('opacity', '0.5');
		M_PigPlanEdit.dialogEvent($('.11'));
		//$('table').unbind('click');
	});
	$('table').unbind(clickEvent).on(clickEvent, '.dataList', function() {
		$('.dialog-wapper').show();
		$('#bottom').css('opacity', '0.5');
		$('#batch')[0].value = $(this).find(".batchList").text();
		$('#amount')[0].value = $(this).find(".amountList").text();
		$('#pigWeight')[0].value = $(this).find(".pigWeightList").text();
		//var obj=$(this);
		if ($(this).find(".judgeList").text() == '是') {
			$('#judge').prop('checked', true);
		} else {
			$('#judge').prop('checked', false);
		}
		M_PigPlanEdit.dialogEvent(this);
		M_PigPlanEdit.dialogType = 0;
		//$('table').unbind('click');
	});
	$('.save').click(function() {
		M_PigPlanEdit.getData();
		M_PigPlanEdit.saveData();
	});
	$('.queryList').click(function() {
		M_PigPlanEdit.checkIsModify();
		//		window.location.href = 'list.html';
	});
	//		$('.commit').click(function() {
	//			M_PigPlanEdit.checkform();
	//		});
	$('.delete').click(function() {
		layer.open({
			style: 'width:60%;',
			content: '是否删除单据？',
			btn: ['确认', '取消'],
			shadeClose: false,
			yes: function() {
				M_PigPlanEdit.deleteData(M_PigPlanEdit.newData.id);
			},
			no: function() {}
		});

	});
	$("#choosePic").click(function() {
		mxCore.selectPhoto(function(responseData) {
			if (responseData.code == 0) {
				if (responseData.data != undefined && responseData.data.length > 0) {
					var imageId = 'img-' + (new Date()).valueOf();
					var imageBase64 = responseData.data;
					if (M_PigPlanEdit.oldData.images == null || M_PigPlanEdit.oldData.images == undefined) {
						M_PigPlanEdit.oldData.images = [];
						M_PigPlanEdit.oldData.images.push(imageId);
					} else {
						M_PigPlanEdit.oldData.images.push(imageId);
					}
					mxCore.offlineSaveImage(imageId, imageBase64, function() {
						if (responseData.code == 0) {
							M_PigPlanEdit.uploadImage(imageId, imageBase64);
						} else {
							layer.msg(responseData.msg);
						}
					});
				}
			} else {
				layer.msg(responseData.msg);
			}
		});
	});
}

M_PigPlanEdit.dialogEvent = function(obj) {
	
	$('#cancel').unbind(clickEvent).on(clickEvent, function() {
		$('.dialog-wapper').hide();
		$('#bottom').css('opacity', '1');
	});
	$('#ensure').unbind(clickEvent).on(clickEvent, function() {
		$('.dialog-wapper').hide();
		$('#bottom').css('opacity', '1');
		var judge;
		if ($('#judge').prop('checked')) {
			judge = '是';
		} else {
			judge = '否';
		}
		if (M_PigPlanEdit.dialogType == 1) { //判断是否为新增数据
			$('#detailTable tbody').append('<tr class="dataList" id="" batchid='+M_PigPlanEdit.breedingbatchid+'><td class="batchList">' + $('#batch').val() +
				'</td><td class="judgeList">' + judge + '</td><td class="amountList">' + $('#amount').val() + '</td><td class="pigWeightList">' + $('#pigWeight').val() + '</td></tr>');
		} else {
			$(obj).attr('batchid',M_PigPlanEdit.breedingbatchid);
			$(obj).find(".batchList").text($('#batch').val());
			$(obj).find(".judgeList").text(judge);
			$(obj).find(".amountList").text($('#amount').val());
			$(obj).find(".pigWeightList").text($('#pigWeight').val());
			M_PigPlanEdit.dialogType = 1;
		}
		M_PigPlanEdit.sumData();
	});
	$('#delet').unbind(clickEvent).on(clickEvent, function() {
		$('.dialog-wapper').hide();
		$('#bottom').css('opacity', '1');
		if (obj.id) {
			$.post(BaseApp.base_url + '/extapiserver/eas/yhsp/invtranstorage/deleteentry', {
				t: BaseApp.token,
				entryid: obj.id
			}, function(responseData) {
				layer.msg(responseData.msg);
			});
			M_PigPlanEdit.detailId.splice($.inArray(obj.id, M_PigPlanEdit.detailId), 1);
			//M_PigPlanEdit.breedingbatchid.splice($.inArray($(obj).attr('batchid'), M_PigPlanEdit.breedingbatchid), 1);
		}
		$(obj).remove();
		M_PigPlanEdit.sumData();
		if ($('.amountList').length == 0) {
			$('#amountSum').html('');
			$('#pigWeightSum').html('');
		}
	});
}
M_PigPlanEdit.sumData = function() {
	var amountSum = 0,
		pigWeightSum = 0;
	if ($('.amountList').length != 0) {
		for (var i = 0; i < $('.amountList').length; i++) {
			amountSum = amountSum + parseInt($('.amountList')[i].innerText);
			$('#amountSum').html(amountSum);
			pigWeightSum = pigWeightSum + parseInt($('.pigWeightList')[i].innerText);
			$('#pigWeightSum').html(pigWeightSum);
			//$('#pigWeightSum')[0].value=$('#pigWeightSum').val()+$('.pigWeightList')[i].innerText;
		}
	}
}
M_PigPlanEdit.checkIsModify = function() {
	if (M_PigPlanEdit.newData) {
		M_PigPlanEdit.getData();
		if (M_PigPlanEdit.newData.fare == null)
			M_PigPlanEdit.newData.fare = '';
		if(M_PigPlanEdit.newData.description == null)
		    M_PigPlanEdit.newData.description = '';
		//M_PigPlanEdit.newData.description != $('#remark').val() ||
		if (M_PigPlanEdit.newData.othernumber == null)
			M_PigPlanEdit.newData.othernumber = '';
		if (M_PigPlanEdit.qty.toString() != M_PigPlanEdit.oldqty.toString() ||
			M_PigPlanEdit.totalQty.toString() != M_PigPlanEdit.oldtotalQty.toString() ||
			M_PigPlanEdit.breedbatchid.toString() != M_PigPlanEdit.oldbreedingbatchid.toString() ||
			M_PigPlanEdit.newData.storageorgunitid != M_PigPlanEdit.storageorgunitid ||
			M_PigPlanEdit.newData.trantypeid != M_PigPlanEdit.trantypeid ||
			M_PigPlanEdit.newData.pigcarid != M_PigPlanEdit.pigcarid ||
			M_PigPlanEdit.newData.fare != $('#transportCost').val() ||
			M_PigPlanEdit.newData.description != $('#remark').val() ||
			M_PigPlanEdit.newData.othernumber != $('#code').val()) {
			layer.confirm('单据已经修改，请确认保存', {
				btn: ['确认', '取消'] //按钮
			}, function() {
				M_PigPlanEdit.saveData();
			}, function() {
				window.location.href = 'list.html';
			});
		} else {
			window.location.href = 'list.html';
		}

	} else {
		window.location.href = 'list.html';
	}

}
M_PigPlanEdit.getData = function() {
	//approveData.billbasestatus=$('#bill input').val();
	//approveData.workDate=$('#workDate input').val();
	_loading.start();
	M_PigPlanEdit.breedbatchid = [];
	M_PigPlanEdit.qty = [];
	M_PigPlanEdit.totalQty = [];
	M_PigPlanEdit.oldData.invTranStorageOutEntrys = [];
	M_PigPlanEdit.oldData.id = M_PigPlanEdit.billid;
	M_PigPlanEdit.oldData.storageorgunitid = M_PigPlanEdit.storageorgunitid;
	M_PigPlanEdit.oldData.trantypeid = M_PigPlanEdit.trantypeid;
	//M_PigPlanEdit.oldData.breedingbatchid = M_PigPlanEdit.breedingbatchid;
	M_PigPlanEdit.oldData.pigcarid = M_PigPlanEdit.pigcarid;
	//M_PigPlanEdit.deliveryData.outtype=$('#type select').val();
	//M_PigPlanEdit.deliveryData.outdate=new Date($('#approveDate input').val()).getTime();//重要，将时间转为一串数字
	M_PigPlanEdit.oldData.fare = $('#transportCost').val();
	//M_PigPlanEdit.oldData.reason = $('#deliveryReason input').val();
	M_PigPlanEdit.oldData.description = $('#remark').val();
	M_PigPlanEdit.oldData.othernumber = $('#code').val();
	//	if ($('#raise input').prop('checked')) {
	//		M_PigPlanEdit.oldData.ishosted = 1;
	//	} else {
	//		M_PigPlanEdit.oldData.ishosted = 0;
	//	}
	for (var i = 0; i < $('.amountList').length; i++) {
		var detailData = {};
		detailData.seq = 1;
		detailData.breedingbatchid = $('.dataList').eq(i).attr('batchid');
		M_PigPlanEdit.breedbatchid.push($('.dataList').eq(i).attr('batchid'));
		detailData.id = $('.dataList').eq(i).attr('id');
		detailData.qty = $(".amountList")[i].innerText;
		detailData.totalQty = $(".pigWeightList")[i].innerText;
		detailData.aveqty = parseInt(detailData.totalQty) / parseInt(detailData.qty);
		M_PigPlanEdit.qty[i] = $(".amountList")[i].innerText;
		M_PigPlanEdit.totalQty[i] = $(".pigWeightList")[i].innerText;
		M_PigPlanEdit.oldData.invTranStorageOutEntrys.push(detailData);
	}



}
M_PigPlanEdit.saveData = function() {
	/*$.post(BaseApp.base_url+'/extapiserver/eas/yhsp/approveOutPlanInfo/save',{
				t:BaseApp.token,
				billinfo:JSON.stringify(M_PigPlanEdit.deliveryData)
			},M_PigPlanEdit.saveData_callback);
	*/
	$.post(BaseApp.base_url + '/extapiserver/eas/yhsp/invtranstorage/save', {
		t: BaseApp.token,
		billinfo: JSON.stringify(M_PigPlanEdit.oldData)
	}, M_PigPlanEdit.saveData_callback);
}

M_PigPlanEdit.saveData_callback = function(responseData) {
		if (responseData.code == 0) {
			_loading.end();
			M_PigPlanEdit.newData = responseData.data;
			M_PigPlanEdit.billid = responseData.data.id;
			M_PigPlanEdit.showData();
			layer.open({
				style: 'width:60%;',
				content: '保存成功！',
				btn: ['确认'],
				shadeClose: false,
				no: function() {
					//PigPlan_Approve.showData();
				}
			});
			//M_PigPlanEdit.loadImageFromMxcore();
			//PigPlan_Approve.refreshPage();
		} else if (responseData.code != 0) {
			_loading.end();
			layer.msg(responseData.msg);
		}

	}
	//检查格式
M_PigPlanEdit.checkform = function() {
	if ($('#organize input').val() == '') {
		layer.msg('库存组织不能为空');
	} else if ($('#affairType input').val() == '') {
		layer.msg('事务类型不能为空');
	} else if ($('#batch input').val() == '') {
		layer.msg('养殖批次不能为空');
	} else if ($('#deliveryAmount input').val() == '') {
		layer.msg('出库数量不能为空');
	} else if ($(".image-div").html() == undefined) {
		layer.msg("至少需上传一张图片");
		//return false;
	} else {
		M_PigPlanEdit.commitData(M_PigPlanEdit.newData.id);
	}
}
M_PigPlanEdit.commitData = function(fId) {
	_loading.start();
	$.post(BaseApp.base_url + '/extapiserver/eas/yhsp/OtherOutBill/submit', {
		t: BaseApp.token,
		id: fId
	}, M_PigPlanEdit.commitData_callback);
}
M_PigPlanEdit.commitData_callback = function(responseData) {
	_loading.end();
	if (responseData.code == 0) {
		layer.open({
			style: 'width:60%;',
			content: '单据已提交！',
			btn: ['确认'],
			shadeClose: false,
			yes: function() {
				window.location.href = 'list.html';
			}
		});
		//window.location.href='otherDelivery.html';
	} else if (responseData.code != 0) {
		layer.open({
			style: 'width:60%;',
			content: responseData.msg
		});
		//layer.msg(responseData.msg);  
	}
}
M_PigPlanEdit.deleteData = function(fId) {
	_loading.start();
	$.post(BaseApp.base_url + '/extapiserver/eas/yhsp/invtranstorage/delete', {
		t: BaseApp.token,
		billid: fId
	}, M_PigPlanEdit.deleteData_callback);
}
M_PigPlanEdit.deleteData_callback = function(responseData) {
	_loading.end();
	if (responseData.code == 0) {
		//layer.msg(responseData.msg);  
		layer.open({
			style: 'width:60%;',
			content: '删除成功',
			btn: ['确认'],
			shadeClose: false,
			yes: function() {
				window.location.href = 'list.html';
			}
		});
	} else if (responseData.code != 0) {
		layer.open({
			style: 'width:60%;',
			content: '删除失败:' + responseData.msg,
			btn: ['确认'],
			shadeClose: false,
			yes: function() {

			}
		});

	}
}
M_PigPlanEdit.showData = function() {
	M_PigPlanEdit.oldbreedingbatchid = [];
	M_PigPlanEdit.oldqty = [];
	M_PigPlanEdit.oldtotalQty = [];
	$('#bill')[0].value = M_PigPlanEdit.newData.number;
	var time = new Date(M_PigPlanEdit.newData.bizdate);
	//var workDate=time.getFullYear()+'-'+time.getMonth()+1+'-'+time.getDate();
	$('#workDate')[0].value = time.format("yyyy-MM-dd");
	//$('#organize')[0].value = M_PigPlanEdit.oldData.storageorgunit_name;
	M_PigPlanEdit.storageorgunitid = M_PigPlanEdit.newData.storageorgunitid;
	//$('#affairType')[0].value = M_PigPlanEdit.oldData.trantype_name;
	M_PigPlanEdit.trantypeid = M_PigPlanEdit.newData.trantypeid;
	//$('#transport')[0].value = M_PigPlanEdit.newData.pigcar_name;
	M_PigPlanEdit.pigcarid = M_PigPlanEdit.newData.pigcarid;
	//$('#type select')[0].value=M_PigPlanEdit.newData.outtype;
	//time=new Date(M_PigPlanEdit.newData.outdate);
	//$('#approveDate input')[0].value=time.format("yyyy-MM-dd");
	$('#transportCost')[0].value = M_PigPlanEdit.newData.fare;
	$('#remark')[0].value = M_PigPlanEdit.newData.description;
	$('#code')[0].value = M_PigPlanEdit.newData.othernumber;
	//	if (M_PigPlanEdit.newData.ishosted == 1) {
	//		$('#raise input').prop('checked', 'checked');
	//	}
	for (var i = 0; i < M_PigPlanEdit.newData.invTranStorageOutEntrys.length; i++) {
		//$('.batchList')[i].value = M_PigPlanEdit.newData.invTranStorageOutEntrys[i].breedingbatch_name;
		M_PigPlanEdit.detailId[i] = M_PigPlanEdit.newData.invTranStorageOutEntrys[i].id;
		//M_PigPlanEdit.breedingbatchid[i] = M_PigPlanEdit.newData.invTranStorageOutEntrys[i].breedingbatchid;
		M_PigPlanEdit.oldbreedingbatchid[i] = M_PigPlanEdit.newData.invTranStorageOutEntrys[i].breedingbatchid;
		M_PigPlanEdit.oldqty[i] = $(".amountList")[i].innerText;
		M_PigPlanEdit.oldtotalQty[i] = $(".pigWeightList")[i].innerText;
		$('.dataList').eq(i).attr('id', M_PigPlanEdit.detailId[i]);
		//$('.dataList').eq(i).attr('batchid', M_PigPlanEdit.breedingbatchid[i]);
		$('.amountList')[i].value = M_PigPlanEdit.newData.invTranStorageOutEntrys[i].qty;
		$('.pigWeightList')[i].value = M_PigPlanEdit.newData.invTranStorageOutEntrys[i].totalQty;
	}
	if (M_PigPlanEdit.newData.batchStatus == null || M_PigPlanEdit.newData.batchStatus == undefined || M_PigPlanEdit.newData.batchStatus == '10' || M_PigPlanEdit.newData.batchStatus == '20') {
		$(".save").hide();
		$(".queryList").hide();
		$('.delete').hide();
		$('.commit').hide();
		$('.nav_seprator').hide();
		$("#bottom").append('<span class="queryList">列表</span>  <span class="nav_seprator">   |   </span><span class="delete">删除</span> <span class="nav_seprator">   |   </span><span class="commit">提交</span>  <span class="nav_seprator">   |   </span><span class="save">保存</span> ');
	} else {
		$("#bottom").append('<span class="queryList">列表</span>');
	}
	//M_PigPlanEdit.bindEvent();
	$('.save').click(function() {
		M_PigPlanEdit.getData();
	});
	$('.queryList').click(function() {
		M_PigPlanEdit.checkIsModify();
	});
	$('.commit').click(function() {
		M_PigPlanEdit.checkform();
	});
	$('.delete').click(function() {
		layer.open({
			style: 'width:60%;',
			content: '是否删除单据？',
			btn: ['确认', '取消'],
			shadeClose: false,
			yes: function() {
				M_PigPlanEdit.deleteData(M_PigPlanEdit.newData.id);
			},
			no: function() {}
		});

	});

}
M_PigPlanEdit.updateData = function(fId) {
	_loading.start();
	$.post(BaseApp.base_url + '/extapiserver/eas/yhsp/invtranstorage/get', {
		t: BaseApp.token,
		billid: decodeURIComponent(fId) //解码函数
	}, M_PigPlanEdit.updateData_callback);
}
M_PigPlanEdit.updateData_callback = function(responseData) {
		_loading.end();
		if (responseData.code == 0) {
			M_PigPlanEdit.newData = responseData.data;
			M_PigPlanEdit.billid = responseData.data.id;
			var time = new Date(responseData.data.bizdate);
			//var workDate=time.getFullYear()+'-'+time.getMonth()+1+'-'+time.getDate();
			$('#workDate')[0].value = time.format("yyyy-MM-dd");
			$('#organize')[0].value = responseData.data.storageorgunit_name;
			M_PigPlanEdit.storageorgunitid = responseData.data.storageorgunitid;
			$('#affairType')[0].value = responseData.data.trantype_name;
			M_PigPlanEdit.trantypeid = responseData.data.trantypeid;
			$('#transport')[0].value = responseData.data.pigcar_name;
			M_PigPlanEdit.pigcarid = responseData.data.pigcarid;
			$('#transportCost')[0].value = responseData.data.fare;
			$('#remark')[0].value = responseData.data.description;
			$('#code')[0].value = responseData.data.othernumber;
			if (responseData.data.invTranStorageOutEntrys != null) {
				for (var i = 0; i < responseData.data.invTranStorageOutEntrys.length; i++) {
					$('#detailTable tbody').append('<tr class="dataList" id=' + responseData.data.invTranStorageOutEntrys[i].id + ' batchId=' + responseData.data.invTranStorageOutEntrys[i].breedingbatchid + '><td class="batchList">' +
						responseData.data.invTranStorageOutEntrys[i].breedingbatch_name +
						'</td><td class="judgeList"></td><td class="amountList">' +
						responseData.data.invTranStorageOutEntrys[i].qty +
						'</td><td class="pigWeightList">' +
						responseData.data.invTranStorageOutEntrys[i].totalQty + '</td></tr>');
					//M_PigPlanEdit.breedingbatchid[i] = responseData.data.invTranStorageOutEntrys[i].breedingbatchid;
					M_PigPlanEdit.oldbreedingbatchid[i] = responseData.data.invTranStorageOutEntrys[i].breedingbatchid;
					M_PigPlanEdit.oldqty[i] = $(".amountList")[i].innerText;
					M_PigPlanEdit.oldtotalQty[i] = $(".pigWeightList")[i].innerText;
					M_PigPlanEdit.detailId[i] = responseData.data.invTranStorageOutEntrys[i].id;
				}
			}
			M_PigPlanEdit.sumData();
			M_PigPlanEdit.loadImageFromMxcore();
			//M_PigPlanEdit.bindEvent();
		} else if (responseData.code != 0) {
			layer.msg(responseData.msg);
		}

	}
	//上传照片
M_PigPlanEdit.uploadImage = function(imageId, imageBase64) {
	var imgUrl = "data:image/png;base64," + imageBase64;
	var html = "<div class='image-div'>" +
		"<img id='" + imageId + "' src='" + imgUrl + "' onclick=\"M_PigPlanEdit.loadBigImage('" + imageId + "','" + imgUrl + "')\">" +
		"<div class='image-upload-div'>0%</div>" +
		"<div class='image-delete-btn' onClick=\"M_PigPlanEdit.deleteImage('" + imageId + "')\">" +
		"<img style='width:auto;height:auto' src ='img/deleteimage.png'>" +
		"</div>" +
		"</div >";
	$(".image-container").before(html);
	var uploadProcess = setInterval(function() { //此方法不停地调用函数
		mxCore.getOfflineImageSendProgress(imageId, function(responseData) {
			if (responseData.code == 0) {
				if (responseData.data.status == 2) {
					var percentComplete = responseData.data.sentcount / responseData.data.totalcount;
					$("#" + imageId).parent().find(".image-upload-div").text(percentComplete.toPercent());
				} else if (responseData.data.status == 3) {
					clearInterval(uploadProcess);
					$("#" + imageId).parent().find(".image-upload-div").text("100%").css("display", "none");
				} else if (responseData.data.status == -1) {
					$("#" + imageId).parent().find(".image-upload-div").text("上传失败");
				} else {
					//$("#"+imageId).parent().find(".image-upload-div").text("即将上传");
				}
			} else {
				layer.msg(responseData.msg);
			}
		})
	}, 2000);
}

M_PigPlanEdit.downloadImage = function(imageId) {
	var imgUrl = "";
	var html = "<div class='image-div'>" +
		"<img id='" + imageId + "' src='img/deleteimage.png' onclick=\"M_PigPlanEdit.loadBigImage('" + imageId + "','" + imgUrl + "')\">" +
		"<div class='image-upload-div'>0%</div>" +
		"<div class='image-delete-btn' onClick=\"M_PigPlanEdit.deleteImage('" + imageId + "')\">" +
		"<img style='width:auto;height:auto' src ='img/deleteimage.png'>" +
		"</div>" +
		"</div>";
	$(".image-container").before(html);
	var downloadProcess = setInterval(function() {
		mxCore.getOfflineImageReceiveProgress(imageId, function(responseData) {
			if (responseData.code == 0) {
				if (responseData.data.status == 12) {
					var percentComplete = responseData.data.receivecount / responseData.data.totalcount;
					$("#" + imageId).parent().find(".image-upload-div").text(percentComplete.toPercent());
					// $(".test").parent().find(".image-upload-div").text(percentComplete.toPercent());
				} else if (responseData.data.status == 2) {
					var percentComplete = responseData.data.sentcount / responseData.data.totalcount;
					$("#" + imageId).parent().find(".image-upload-div").text(percentComplete.toPercent());
					// $(".test").parent().find(".image-upload-div").text(percentComplete.toPercent());
				} else if (responseData.data.status == 13 || responseData.data.status == 0) {
					clearInterval(downloadProcess);
					mxCore.getOfflineImage(imageId, function(responseData) {
						if (responseData.code == 0) {
							var imageBase64 = "data:image/jpeg;base64," + responseData.data;
							$("#" + imageId).attr("src", imageBase64);
							// $(".test").attr("src",responseData.data);
							$("#" + imageId).parent().find(".image-upload-div").css("display", "none");
							// $(".test").parent().find(".image-upload-div").css("display","none");
						}
					});
				} else if (responseData.data.status == 11) {
					$("#" + imageId).parent().find(".image-upload-div").text("即将下载");
				} else if (responseData.data.status == 1) {
					$("#" + imageId).parent().find(".image-upload-div").text("即将上传");
				} else if (responseData.data.status == -11) {
					$("#" + imageId).parent().find(".image-upload-div").text("下载失败");
				} else if (responseData.data.status == -1) {
					$("#" + imageId).parent().find(".image-upload-div").text("上传失败");
				}
			} else {
				layer.msg(responseData.msg);
			}
		})
	}, 2000);
}

M_PigPlanEdit.renderImage = function(imageId, imageBase64) {
	var imgUrl = "data:image/png;base64," + imageBase64;
	var html = "<div class='image-div'>" +
		"<img id='" + imageId + "' src='" + imgUrl + "' onclick=\"M_PigPlanEdit.loadBigImage('" + imageId + "','" + imgUrl + "')\">" +
		"<div class='image-delete-btn' onClick=\"M_PigPlanEdit.deleteImage('" + imageId + "')\">" +
		"<img style='width:auto;height:auto' src ='img/deleteimage.png'>" +
		"</div>" +
		"</div>";
	$(".image-container").before(html);
}

M_PigPlanEdit.closeBigImage = function(target) {
	$(target).parent().css("display", "none");
	$("body").removeClass("body-overflow-hidden");
}

M_PigPlanEdit.loadBigImage = function(imageId, imgUrl) {
	var clientHeight = document.body.clientHeight;
	$(".big-image-div").show().height(clientHeight);
	$("body").addClass("body-overflow-hidden");
	$(".big-image-div img").attr("src", imgUrl);
}

M_PigPlanEdit.deleteImage = function(imageId) {
	layer.open({
		style: 'width:60%;',
		content: '确定删除图片么？',
		btn: ['确认', '取消'],
		shadeClose: false,
		yes: function() {
			if (M_PigPlanEdit.oldData.id) {
				$.post(BaseApp.base_url + '/extapiserver/eas/yhsp/OtherOutBill/deleteimage', {
						t: BaseApp.token,
						id: M_PigPlanEdit.oldData.id,
						imageid: imageId
					},
					M_PigPlanEdit.deleteImage_Callback
				);
			} else {
				$("#" + imageId).parent().remove();
				M_PigPlanEdit.oldData.images.remove(imageId);
				// layer.msg("删除图片成功");
			}
			layer.closeAll();
			//layer.msg("删除图片成功");
		},
		no: function() {}
	});

}

M_PigPlanEdit.deleteImage_Callback = function(responseData) {
	if (responseData.code == 0) {
		var imageId = responseData.data;
		$("#" + imageId).parent().remove();
		M_PigPlanEdit.oldData.images.remove(imageId);
		layer.msg("删除图片成功");
	} else {
		layer.msg("删除图片失败:" + responseData.msg);
	}
}

M_PigPlanEdit.checkAndDownImge = function(imageId) {
	mxCore.getOfflineImage(imageId, function(responseData) {
		if (responseData.code == 0) {
			M_PigPlanEdit.renderImage(imageId, responseData.data);
		}
		if (responseData.code == 1) {
			M_PigPlanEdit.downloadImage(imageId);
		}
	});
}

M_PigPlanEdit.loadImageFromMxcore = function() {
	$(".image-div").remove();
	var imageList = M_PigPlanEdit.oldData.images;
	if (imageList != null) {
		for (var i = 0; i < imageList.length; i++) {
			M_PigPlanEdit.checkAndDownImge(imageList[i]);
		}
	}
}

function ValidateNumber(e, pnumber) { //检查输入的是否为数字
	if (!/^\d+$/.test(pnumber)) {
		$(e).val(/^\d+/.exec($(e).val()));
	}
	return false;
}

//mxCore.connectWebViewJavascriptBridge(function(bridge){
//	mxCore.getMXToken('appid','securityid',function(responseData){
//		if (responseData.code == 0 ){
//			BaseApp.token=responseData.data.mxtoken;
//		}else{
//			BaseApp.token='9q37KJZjux63UZJINc7LNXnbtazOSH';
//		}
//		M_PigPlanEdit.checkPermission();
//	});
//});

if (BaseApp.isDebug) {
	M_PigPlanEdit.init();
} else {
	mxCore.connectWebViewJavascriptBridge(function(bridge) {
		mxCore.getMXToken('appid', 'securityid', function(responseData) {
			if (responseData.code == 0) {
				BaseApp.token = responseData.data.mxtoken;
			} else {
				BaseApp.token = 'dFG7epyDW3qtapPA845nwozMavyS7x';
			}
			M_PigPlanEdit.checkPermission();
		});
	});
}