<%@ page contentType="text/html; charset=utf-8" language="java"	import="java.util.*" errorPage=""%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的欢乐生活 - ${websiteName}</title>
<link charset="utf-8" rel="stylesheet" href="${templatePath}/css/home.css" />
<link href="${templatePath}/css/bills.css" rel="stylesheet" />
<link href="${templatePath}/css/operate.css" rel="stylesheet" />
<link href="${templatePath}/css/alice.style.record.css" rel="stylesheet" />
<style type="text/css">
.statisticstime {
	margin: 12px auto 12px 12px;
}

.statisticstime span {
	margin: auto 2px;
}

.statisticstime input {
	width: 98px;
	height: 21px;
	font-weight: 700;
	font-size: 14px;
	padding-left: 7px;
}
</style>
</head>
<body>
	<%@include file="global_topbar.jsp"%>
	<div class="container">		
<form name="topSearchForm" class="record-search-form  record-search-min " id="topSearchForm" action="" method="get" coor="form">
		
<div class="record-search-date pb-15" id="J-search-date-container" data-widget-cid="widget-4">
	<div class="ui-form-item ui-form-item-time">
		<label class="ui-form-label">起止日期：</label>
		<div class="ui-form-item-content">	
			<div class="range-quick-date fn-clear">
				
		<div class="quick-input-date fn-left">
			<ul>
				<li><input name="beginDate"  class="date-search-input" 
					type="text" id="starttime" size="10" maxlength="10"
					onclick="new Calendar().show(this);" readonly="readonly" value="${startDate}" /><span class="ui-separator-pd">-</span><input
					name="endDate" type="text" id="endtime" size="10"  class="date-search-input"
					maxlength="10" onclick="new Calendar().show(this);"
					readonly="readonly" value="${endDate}"/>
				</li>
			</ul>
		</div>
				
                <div class="quick-link-date gray-links fn-left">
                    <a id="J-today" href="Records.yy?startDate=${today}&endDate=${today}" seed="quickLinkDate-JToday" smartracker="on">今天</a>
                    <a id="J-one-month" href="Records.yy?startDate=${lastmonthday}&endDate=${today}" seed="quickLinkDate-JOneMonth" smartracker="on">最近1个月</a>
                    <a id="J-three-month" href="Records.yy?startDate=${threemonthday}&endDate=${today}" seed="quickLinkDate-JThreeMonth" smartracker="on">3个月</a>
                    <a id="J-one-year" href="Records.yy?startDate=${lastyear}&endDate=${today}" seed="quickLinkDate-JOneYear" smartracker="on">1年</a>
                    <!-- <a id="J-more-year" href="https://lab.alipay.com/consume/record/historyIndexNew.htm" target="_blank" seed="quickLinkDate-JMoreYear" smartracker="on">1年前&gt;&gt;</a> 使用新页面，进行高级查找-->
					<a href="Records.yy?startDate=">显示全部</a>
				</div>
			</div>
			<div class="range-date" id="J-date-slider"></div>
		</div>
	</div>
</div>

<style type="text/css">
.record-search-min .record-type-list{
	width:510px;
}
</style>
<div class="record-search-cate">
    <div class="ui-form-item" id="ui-form-item-show"> 		<label class="ui-form-label ui-label-text">交易分类：</label>
		<div class="ui-form-item-content">
			<div class="fn-left record-type-list gray-links" id="J-consume-category">  
                                                         
					<a href="Records.yy?startDate=${startDate}&endDate=${endDate}&tagid=0" smartracker="on">全部</a>
				                                         
					<a href="Records.yy?startDate=${startDate}&endDate=${endDate}&travelleader=1&tagid=1" smartracker="on">行程</a>
					
					<a href="Records.yy?startDate=${startDate}&endDate=${endDate}&swd=6&tagid=2" smartracker="on">购物</a>
				                                         
					<a href="Records.yy?startDate=${startDate}&endDate=${endDate}&swd=31&tagid=3" smartracker="on">付款</a>
				                                         
					<a href="Records.yy?startDate=${startDate}&endDate=${endDate}&swd=32&tagid=4" smartracker="on">收款</a>
				                                         
					<a href="Records.yy?startDate=${startDate}&endDate=${endDate}&swd=20&tagid=5" smartracker="on">缴水电煤</a>
				                                         
					<a href="Records.yy?startDate=${startDate}&endDate=${endDate}&swd=7&tagid=6" smartracker="on">信用卡还款</a>
				                                         
					<a href="Records.yy?startDate=${startDate}&endDate=${endDate}&swd=30&tagid=7" smartracker="on">理财</a>
				                                         
					<a href="Records.yy?startDate=${startDate}&endDate=${endDate}&swd=19&tagid=8" smartracker="on">交房租</a>
				                                         
					<a href="Records.yy?startDate=${startDate}&endDate=${endDate}&swd=28&tagid=9" smartracker="on">送礼金</a>
				                                         
					<a href="Records.yy?startDate=${startDate}&endDate=${endDate}&swd=18&tagid=10" smartracker="on">缴通讯费</a>
				                                         
					<a href="Records.yy?startDate=${startDate}&endDate=${endDate}&swd=21&tagid=11" smartracker="on">还贷款</a>
					
					<a href="Records.yy?startDate=${startDate}&endDate=${endDate}&swd=1&tagid=12" smartracker="on">工资</a>
				                                         
					<a href="Records.yy?startDate=${startDate}&endDate=${endDate}&swd=29&tagid=12" smartracker="on">捐款</a>
							</div>
			<a class="record-type-more" id="J-consume-category-more" href="javascript:;" seed="CR-bn-else">其他</a>
		</div>
	</div>

</div>


<div class="ui-from-item  fn-clear customize-category-item">
	<label class="ui-form-label ui-label-text">自定义：</label>
    <div class="ui-form-item-content">
        	<lable>&nbsp;类型：</lable><input type="text" id="btypeword" name="btypeword" value="${btypeword}"/><lable>&nbsp;留言：</lable><input type="text" id="searchword" name="swdlike" value="${swdlike}"/><input type="button" value="查询" onclick="searchwords();"/><lable>&nbsp;可以在上面选择时间</lable>
	</div>
</div>


<div class="record-search-option">
	<div class="record-search-timeType mt-15">  		<div class="ui-form-item">
            <label class="ui-form-label ui-label-text">时间类型：</label>
            <div class="ui-form-item-content gray-links" id="J-consume-category">
                                    <a class="active" href="https://consumeprod.alipay.com/record/standard.htm?_input_charset=utf-8&amp;dateRange=customDate&amp;tradeType=all&amp;status=all&amp;fundFlow=all&amp;endDate=2014.03.25&amp;dateType=createDate&amp;beginDate=2013.04.03&amp;pageNum=1" seed="JConsumeCategory-activeT2" smartracker="on">创建时间</a>
                    <a href="https://consumeprod.alipay.com/record/standard.htm?_input_charset=utf-8&amp;dateRange=customDate&amp;tradeType=all&amp;status=all&amp;fundFlow=all&amp;endDate=2014.03.25&amp;dateType=payDate&amp;beginDate=2013.04.03&amp;pageNum=1" seed="JConsumeCategory-linkT26" smartracker="on">付款时间</a>
                    <a href="https://consumeprod.alipay.com/record/standard.htm?_input_charset=utf-8&amp;dateRange=customDate&amp;tradeType=all&amp;status=all&amp;fundFlow=all&amp;endDate=2014.03.25&amp;dateType=receiveDate&amp;beginDate=2013.04.03&amp;pageNum=1" seed="JConsumeCategory-linkT27" smartracker="on">收款时间</a>
                			</div>
		</div>
	</div>

	<div class="record-search-fundFlow mt-10">  		<div class="ui-form-item">
			<label class="ui-form-label ui-label-text">资金流向：</label>
            <div class="ui-form-item-content gray-links" id="J-consume-category">
                				    <a class="active" href="https://consumeprod.alipay.com/record/standard.htm?_input_charset=utf-8&amp;dateRange=customDate&amp;tradeType=all&amp;status=all&amp;fundFlow=all&amp;endDate=2014.03.25&amp;dateType=createDate&amp;beginDate=2013.04.03&amp;pageNum=1" seed="JConsumeCategory-activeT3" smartracker="on">全部</a>
                    <a href="https://consumeprod.alipay.com/record/standard.htm?_input_charset=utf-8&amp;dateRange=customDate&amp;tradeType=all&amp;status=all&amp;fundFlow=in&amp;endDate=2014.03.25&amp;dateType=createDate&amp;beginDate=2013.04.03&amp;pageNum=1" seed="JConsumeCategory-linkT28" smartracker="on">收入</a>
                    <a href="https://consumeprod.alipay.com/record/standard.htm?_input_charset=utf-8&amp;dateRange=customDate&amp;tradeType=all&amp;status=all&amp;fundFlow=out&amp;endDate=2014.03.25&amp;dateType=createDate&amp;beginDate=2013.04.03&amp;pageNum=1" seed="JConsumeCategory-linkT29" smartracker="on">支出</a>
                			</div>	
		</div>
	</div>

	<div class="record-search-amount" id="J-search-amount-container">  		<div class="ui-form-item">
			<label class="ui-form-label">金额范围：</label>
			<div class="ui-form-item-content J-edit-box fn-pr">
				<div class="amount-input">
					<input name="minAmount" class="ui-input ui-input-len10" id="minAmount" style="-ms-ime-mode: disabled;" type="text" value="" seed="CR-money-min" autocomplete="off"> 
					<span class="ui-separator-pd">-</span>
					<input name="maxAmount" class="ui-input ui-input-len10" id="maxAmount" style="-ms-ime-mode: disabled;" type="text" value="" seed="CR-money-max" autocomplete="off">	
				</div>
				<a class="ui-button ui-button-swhite" id="J-amount-btn" href="javascript:;" seed="JEditBox-JAmountBtn" smartracker="on">
					<span class="ui-button-text">确定</span>
				</a>
			</div>
		</div>
	</div>
</div>
 
 </form>
		
		<div class="bills">
			<div class="lcontent_r">
				<form name="deleteform" method="post" action="Bills.yy">
					<input type="hidden" name="a" value="delete" />
					<input type="hidden" name="r" value="record" />
					<input type="hidden" id="b" name="b" value="" />
				</form>
				<div class="bill_list_r" id="bill_list">
					<div class="ui_recent_footer">
						<c:if test="${pagenow!=0}">
							<a title="首页" class="more"
								href="Records.yy?startDate=${startDate}&endDate=${endDate}&pageNum=0&swd=${swd}&swdlike=${swdlike}&tagid=${tagid}"
								target="_self"><<首页</a>
							<a title="上一页" class="more"
								href="Records.yy?startDate=${startDate}&endDate=${endDate}&pageNum=${pagenow-1}&swd=${swd}&swdlike=${swdlike}&tagid=${tagid}"
								target="_self"><上一页</a>
						</c:if>
						第${pagenow*20+1}-${(pagenow*20+20)>ofTotal?ofTotal:(pagenow*20+20)}条
						共${ofTotal}条
						<c:if test="${pagenow!=pages}">
							<a title="下一页" class="more"
								href="Records.yy?startDate=${startDate}&endDate=${endDate}&pageNum=${pagenow+1}&swd=${swd}&swdlike=${swdlike}&tagid=${tagid}"
								target="_self">下一页></a>
							<a title="尾页" class="more"
								href="Records.yy?startDate=${startDate}&endDate=${endDate}&pageNum=${pages}&swd=${swd}&swdlike=${swdlike}&tagid=${tagid}"
								target="_self">尾页>></a>
						</c:if>
					</div>
				</div>
			</div>
		</div>

		<div id="modify_form" class="modify_form">
	<div id="modify_add_block" class="bill_add_block">
	<form name="modifyform" method="post" action="Bills.yy">
        <div class="bill_add">
      <p>修改记账：</p><div class="bill_add_h"><p>
      您于&nbsp;<span id='thetime'>
    <input type="text" name="datetime_m" id="datetime_m" readonly="readonly" value=""/></span>&nbsp;因为<input class="b_type" type="text" id="btype_m" name="btype_m" value="吃饭 购物等等" readonly/><select class="b_io" id="bio_m" name="bio_m">
            <option value="">&nbsp;</option>
            <option value="1">支出</option>
            <option value="2">收入</option>
            <option value="3">债务</option>
            <option value="4">债权</option>
            <option value="5">无金钱</option></select><input class="b_amount" type="text" id="bamount_m" name="bamount_m" value="金额"/>元，特此记录！</p><div><textarea class="b_caption" type="text" id="bcaption_m" name="bcaption_m"/>再说点什么……</textarea></div>
        </div>
        <div class="bill_add_s">
    	<input type="hidden" name="a" value="modify"/>
		<input type="hidden" name="r" value="record" />
        <input type="button" value="取消" onclick="javascript:document.getElementById('modify_form').className='modify_form';	document.getElementById('modify_mask').className='modify_mask';"/>
       <input type="button" value="保存" onclick="javascript:if(checknull_m()){document.modifyform.submit();}"/>
         <span id="checknullprompt_m"></span>
        </div></div>
    <input type="hidden" id="b_modify" name="b" value=""/></form>
    </div>
	</div>
		<div id="modify_mask" class="modify_mask"></div>
	</div>
<script type="text/javascript" src="${templatePath}/js/modify_r.js"></script>
	<script type="text/javascript" src="${templatePath}/js/jquery-2.0.3.min.js"></script>
	<script type="text/javascript" src="${templatePath}/js/Calendar3.js"></script>
	<script type="text/javascript" src="${templatePath}/flotr2/ftime.js"></script>
	<script type="text/javascript">
		$(function() {
			var starttime = "", endtime = "";
			var intervalName; // 定时器句柄
			// 获得焦点时，启动定时器
			$("#starttime").focus(function() {
				starttime = $("#starttime").val();
				intervalName = setInterval(starttimecf, 1000);
			});
			$("#endtime").focus(function() {
				endtime = $("#endtime").val();
				intervalName = setInterval(endtimecf, 1000);
			});

			// 失去焦点时，清除定时器
			/*$("#starttime").blur(function()
			{
			    clearInterval(intervalName);
			});*/

			// input的值改变时执行的函数
			function starttimecf() {
				if ($("#starttime").val() != starttime) {
					starttime = $("#starttime").val();
					clearInterval(intervalName);
					getRecords();
				}
			}
			function endtimecf() {
				if ($("#endtime").val() != endtime) {
					endtime = $("#endtime").val();
					clearInterval(intervalName);
					getRecords();
				}
			}
		});
		function getRecords() {
			if(new Date(document.getElementById('starttime').value).getTime()>new Date(document.getElementById('endtime').value).getTime()){alert("开始时间不能大于结束时间！");return false;}
			var pos = location.href.indexOf("?");
			if (pos !== -1) {
				var url = location.href.substring(0, pos),
				startDate=document.getElementById('starttime').value,
				endDate=document.getElementById('endtime').value;
				url += "?startDate="+startDate
						+ "&endDate=" + endDate;
				location.href=url;
			} else {
				var url = location.href;
				startDate=document.getElementById('starttime').value,
				endDate=document.getElementById('endtime').value;
				url += "?startDate="+startDate
						+ "&endDate=" + endDate;
				location.href=url;
			}
		}
	</script>
	<%@include file="../jsp/global_footer.jsp"%>
	<%@include file="../html/modbacktop.html"%>
<script>
/*这里的变量来自于后台，是提供给下面的ejectdiv.js和weibolist.js使用的*/
var templatePath="${templatePath}";
var listjson='${listjson}';
var u="${sessionScope.UOID}";
</script>
<script type="text/javascript" src="${templatePath}/js/ejectdiv.js"></script>
<script type="text/javascript" src="${templatePath}/js/weibolist.js"></script>
<script>
var $$ = function (id) {
	return "string" == typeof id ? document.getElementById(id) : id;
};
function addlistennerMain(obj, eve, fun) {
	if (document.addEventListener) {// 如果是Firefox
		obj.addEventListener(eve, fun, true);
	} else {
		obj.attachEvent("on"+eve, fun);
	}
}
addlistennerMain($$("bio_m"),"change",bio_mChange);
function bio_mChange(){
	/*无金钱时，自动为0并不可编辑*/
	if($$("bio_m").value==5){$$("bamount_m").value=0;$$("bamount_m").setAttribute("type","hidden");}
	else{$$("bamount_m").setAttribute("type","text");}
}
/* 给活动的链接加样式 */
var lis=$$("J-consume-category").getElementsByTagName("a");
var tagid=${tagid}+"";/*这是一个jsp的EL表达式获取值*/
if(tagid==""){lis[0].setAttribute("class","active");}else{lis[tagid*1].setAttribute("class","active");}
/*点击 其它 打开更多的链接*/
addlistennerMain($$("J-consume-category-more"),"click",ui_item_show);
function ui_item_show(){
	var obj_ui_item=$$("ui-form-item-show");
	if(obj_ui_item.getAttribute("class")=="ui-form-item show"){
		obj_ui_item.setAttribute("class","ui-form-item");
	}
	else{
		obj_ui_item.setAttribute("class","ui-form-item show");
	}
}
function copyInput(myInput){
	  
	  var myNewInput = document.createElement("input");
	  myNewInput.name=myInput.name;
	  myNewInput.value=encodeURI(myInput.value,"UTF-8");
	  
	  return myNewInput;
	}
function searchwords(){
	var f=document.createElement("form");
	f.style.display="none";
	document.body.appendChild(f);
	var i1=$$("starttime");
	var i2=$$("endtime");
	var i3=$$("searchword");
	var i4=$$("btypeword");
	var i11=copyInput(i1);
	i11.name="startDate";
	var i21=copyInput(i2);
	i21.name="endDate";
	var i31=copyInput(i3);
	var i41=copyInput(i4);
	f.appendChild(i11);
	f.appendChild(i21);
	f.appendChild(i31);
	f.appendChild(i41);
	f.action="Records.yy";
	f.method="get";
	f.submit();
}
</script>
<script type="text/javascript" src="${templatePath}/js/mydateslider.js"></script>
<script type="text/javascript">
new mydateslider("${startDate}","${endDate}").show();
</script>
</body>
</html>