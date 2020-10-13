var M_PigPlanList = {
	oldData: {}, //保存查询之后返回的数据
	init: function() {},
	bindEvent: function() {},
	loadParams: function() {},
	saveParams: function(responseData) {},
	rowClick: function() {}, //点击列表的跳转函数
	checkPermission: function() {}, //检查权限
	checkPermission_Callback: function(responseData) {}, //检查权限返回
}
M_PigPlanList.checkPermission = function() {
	_loading.start();
	$.post(BaseApp.base_url + '/extapiserver/eas/yhsp/approveOutPlanInfo/permission', {
			t: BaseApp.token
		},
		M_PigPlanList.checkPermission_Callback
	);
}
M_PigPlanList.checkPermission_Callback = function(responseData) {
	_loading.end();
	if (responseData.code == 0) {
		M_PigPlanList.init();
	} else {
		window.location = "error.html?code=" + responseData.code;
	}
}
M_PigPlanList.init = function() {
	var time = new Date();
	$('#startDate')[0].value = time.format("yyyy-MM-dd"); //！！要这样赋值
	$('#endDate')[0].value = time.format("yyyy-MM-dd");
	M_PigPlanList.bindEvent();
	_loading.start();
	$('#listContent tbody').html('');
	M_PigPlanList.loadParams();
};

M_PigPlanList.bindEvent = function() {
	$('#startDate').datepicker({
		format: 'yyyy-mm-dd',
		language: 'zh-CN',
		autoclose: true
	});
	$('#endDate').datepicker({
		format: 'yyyy-mm-dd',
		language: 'zh-CN',
		autoclose: true
	});
	$('#dateContent').on('click', '#btn_search', function(event) {
		_loading.start();
		$('#listContent tbody').html('');
		M_PigPlanList.loadParams();
	});
	$('.add').click(function() {
		window.location.href = 'edit.html';
	});
};

M_PigPlanList.loadParams = function() {
	$.post(BaseApp.base_url + '/extapiserver/eas/yhsp/invtranstorage/list', {
		t: BaseApp.token,
		filter: "a.FCREATETIME >= (to_date('" + $("#startDate").val() + "','yyyy-mm-dd')) and a.FCREATETIME <= (to_date('" +
			$("#endDate").val() + " 23:59:59','yyyy-mm-dd HH24:MI:SS'))"
	}, M_PigPlanList.saveParams);
};
M_PigPlanList.saveParams = function(responseData) {
	_loading.end();
	if (responseData.code == 0 && responseData.data != null) {
		var htmlList;
		//htmlList+='<thead><tr><td>业务日期</td><td>养殖批次</td><td>状态</td></tr><thead><tbody>'
		for (var i = 0; i < responseData.data.length; i++) {
			var workDate = new Date(responseData.data[i].bizdate);
			//var  wdate=workDate.getFullYear()+'-'+workDate.getMonth()+1+'-'+workDate.getDate();
			var itemhtml = $('#id_template_list')[0].innerHTML;
			var biztype;
			itemhtml = itemhtml.replace('@@id@@', responseData.data[i].id);
			itemhtml = itemhtml.replace('@@bizdate@@', workDate.format("yyyy-MM-dd"));
			itemhtml = itemhtml.replace('@@batchno@@', responseData.data[i].storageorgunit_name);
			if (responseData.data[i].billbasestatus == '10') {
				biztype = '新增';
			} else if (responseData.data[i].billbasestatus == '20') {
				biztype = '保存';
			} else if (responseData.data[i].billbasestatus == '30') {
				biztype = '保存';
			} else if (responseData.data[i].billbasestatus == '40') {
				biztype = '提交';
			}
			itemhtml = itemhtml.replace('@@type@@', responseData.data[i].billbasestatus);
			itemhtml = itemhtml.replace('@@biztype@@', biztype);
			//htmlList+='<tr><td>'+wdate +'</td><td>'+responseData.data[i].breedingbatch_name
			//+'</td><td>'+responseData.data[i].outtype+'</td></tr>';\
			htmlList = htmlList + itemhtml;
		}
		$('#listContent tbody').append(htmlList);
		//htmlList.appendTo($('#listContent tbody'));
		M_PigPlanList.oldData = responseData.data;
	} else if (responseData.code == 0 && responseData.data == null) {
		layer.msg('没有数据');
		$('#listContent tbody').append('<tr class="no-records-found"><td colspan="3">No matching records found</td></tr>');
	} else if (responseData.code != 0) {
		layer.msg(responseData.msg);
	}
};
M_PigPlanList.rowClick = function(billid, batchStatus) {
	var changeid = encodeURIComponent(billid); //编码函数
	window.location.href = 'edit.html?batchid=' + changeid + '&batchStatus=' + batchStatus;

};
if (BaseApp.isDebug) {
	M_PigPlanList.init();
} else {
	mxCore.connectWebViewJavascriptBridge(function(bridge) {
		mxCore.getMXToken('appid', 'securityid', function(responseData) {
			if (responseData.code == 0) {
				BaseApp.token = responseData.data.mxtoken;
			} else {
				BaseApp.token = 'dFG7epyDW3qtapPA845nwozMavyS7x';
			}
			M_PigPlanList.checkPermission();
		});
	});
}