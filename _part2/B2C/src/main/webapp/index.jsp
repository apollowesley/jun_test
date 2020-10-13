<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>银联全渠道PC/WAP跳转银联页面支付产品示例</title>

<style type="text/css">
<!--
html, body {
	width: 100%;
	height: 100%;
}

a {
	text-decoration: none;
	font-weight: normal;
}

a:link {
	color: #003399;
}

a:visited {
	color: #003399;
}

a:hover {
	color: #FF6600;
}

.px14 {
	font-size: 14px;
}

.STYLE2 {
	font-size: 14px;
	font-weight: bold;
}

#toptable td {
	border-top-width: 0px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 0px;
	border-top-style: solid;
	border-right-style: solid;
	border-bottom-style: solid;
	border-left-style: solid;
	border-top-color: #0000ff;
	border-right-color: #0000ff;
	border-bottom-color: #0000ff;
	border-left-color: #0000ff;
	text-align: left;
	font-size: 14px;
	font-weight: normal;
	word-break: break-all;
}

.btn {
	background-color: #0055AA;
	color: #FFFFFF;
	font-weight: bold;
	border: medium none;
	width: 120px;
	height: 28px;
}

.btn:hover {
	background-color: #00CC00;
	width: 120px;
	color: #FFFFFF;
	font-weight: bold;
	height: 28px;
}
-->
</style>

<script type="text/javascript">  
    function onPayForm(){
       document.all.payForm.submit();
    }
    function onPayForm2(){
        document.all.payForm2.submit();
     }
    function onPayUndoForm(){
    	document.all.payUndoForm.submit();
    }
    function onRefundForm(){
    	document.all.refundForm.submit();
    }
    function onQueryForm(){
    	document.all.queryForm.submit();
    }
    
    function onfileTransferForm(){
    	document.all.fileTransferForm.submit();
    }
    
    function displayConsumeDiv(){
    	document.getElementById("consumeDiv").style.display = "block";
    	document.getElementById("consumeRefundDiv").style.display = "none";
    	document.getElementById("consumeUndoDiv").style.display = "none";
    	document.getElementById("queryDiv").style.display ="none";
    	document.getElementById("fileTransferDiv").style.display = "none";
    }
    
    function displayConsumeUndoDiv(){
    	document.getElementById("consumeUndoDiv").style.display = "block";
    	document.getElementById("consumeDiv").style.display = "none";
    	document.getElementById("consumeRefundDiv").style.display = "none";
    	document.getElementById("queryDiv").style.display ="none";
    	document.getElementById("fileTransferDiv").style.display = "none";
    }
    function displayRefundDiv(){
    	document.getElementById("consumeRefundDiv").style.display = "block";
    	document.getElementById("consumeDiv").style.display = "none";
    	document.getElementById("consumeUndoDiv").style.display = "none";
    	document.getElementById("queryDiv").style.display ="none";
    	document.getElementById("fileTransferDiv").style.display = "none";
    }
    
    function displayQueryDiv(){
    	document.getElementById("queryDiv").style.display = "block";
    	document.getElementById("consumeRefundDiv").style.display = "none";
    	document.getElementById("consumeDiv").style.display = "none";
    	document.getElementById("consumeUndoDiv").style.display = "none";
    	document.getElementById("fileTransferDiv").style.display = "none";
    }
    function displayfileTransferDiv(){
    	
    	document.getElementById("fileTransferDiv").style.display = "block";
    	document.getElementById("queryDiv").style.display = "none";
    	document.getElementById("consumeRefundDiv").style.display = "none";
    	document.getElementById("consumeDiv").style.display = "none";
    	document.getElementById("consumeUndoDiv").style.display = "none";
    }
    
</script>

</head>
<body>
	<table width="80%" height="40%" id="toptable" align="center" cellpadding="3" cellspacing="1" bgcolor="#99CC59;">
		<tr>
			<td align="center" valign="top" width="70%" bgcolor="#FFFFEE">
				<div id="consumeDiv" style="display:none" >

					<form
						action="<%request.getContextPath();%>/B2C/FrontConsume.do"
						method="post" name="payForm" id="payForm" target="_blank">
						<table width="100%" height="100%" border="0" align="left"
							cellpadding="0" cellspacing="0" bgcolor="#AACC00">

							<tr bgcolor="#FFFFEE">
								<td height="26" align="right" valign="middle"><b>跳转银联页面支付，填写订单信息（只提供了商户号，交易金额，其他字段请具体参考代码）</td>
							</tr>
							<tr>
								<td align="center" valign="top" bgcolor="#FFFFEE">
									<table width="100%" height="100%" border="0" cellpadding="3"
										cellspacing="0" class="px14">
										<tr>
											<td align="left" valign="middle">商户号merId：</td>
											<td valign="middle"><input name="merId" type="text"
												value="777290058110048">（默认商户号仅作为联调测试使用，正式上线还请使用正式申请的商户号）
											</td>
										</tr>
										<tr>
											<td align="right" valign="middle">交易金额txnAmt：</td>
											<td valign="middle"><input type="text" name="txnAmt"
												value="1">（单位为分）</td>
										</tr>
										<tr>
											<td valign="middle"><b> <input name="button"
													type="button" value="跳转银联页面支付" class="btn"
													onClick="onPayForm();" /></td>
											<td valign="middle">&nbsp;</td>
										</tr>
									</table> <%-- <jsp:include  page="/other/consume_faq.jsp"/> --%>
								</td>
							</tr>
						</table>
					</form>
				</div>

				<div id="queryDiv" style="display: block">
					<form 
						action="<%request.getContextPath();%>/B2C/Query.do"
						method="post" name="queryForm" id="queryForm" target="_blank">
						<table width="100%" border="0" align="left" cellpadding="0"
							cellspacing="0" bgcolor="#FFCC00">
							<tr bgcolor="#FFFFEE">
								<td width="102" height="26" align="right" valign="middle"><b>填写交易状态查询关键要素</td>
							</tr>
							<tr>
								<td align="center" valign="top" bgcolor="#FFFFEE">
									<table width="100%" height="100%" border="0" cellpadding="3"
										cellspacing="0" class="px14">
										<tr>
											<td align="right" valign="middle">被查询交易的orderId：</td>
											<td valign="middle"><span
												style="color: #000000; font-size: 12px;"><input
													name="orderId" value=""></span></td>
										</tr>
										<tr>
											<td align="right" valign="middle">被查询交易的订单发送时间txnTime：</td>
											<td valign="middle"><input type="text" name="txnTime"
												value="">格式（YYYYMMDDhhmmss）</td>
										</tr>
										<tr>
											<td valign="top" height="26"><input name="button"
												type="button" value="交易状态查询" class="btn"
												onClick="onQueryForm();" /></td>
											<td valign="top">&nbsp;</td>
										</tr>
									</table> <%--  <jsp:include  page="/other/query_faq.jsp"/> --%>
								</td>
							</tr>
						</table>
					</form>
				</div>
				
				<div id="consumeDiv" style="display: block">

					<form
						action="<%request.getContextPath();%>/B2C/FrontConsumeByJSP.do"
						method="post" name="payForm2" id="payForm2" target="_blank">
						<table width="100%" height="100%" border="0" align="left"
							cellpadding="0" cellspacing="0" bgcolor="#AACC00">

							<tr bgcolor="#FFFFEE">
								<td height="26" align="right" valign="middle"><b>跳转银联页面支付，填写订单信息（只提供了商户号，交易金额，其他字段请具体参考代码）</td>
							</tr>
							<tr>
								<td align="center" valign="top" bgcolor="#FFFFEE">
									<table width="100%" height="100%" border="0" cellpadding="3"
										cellspacing="0" class="px14">
										<tr>
											<td align="left" valign="middle">商户号merId：</td>
											<td valign="middle"><input name="merId" type="text"
												value="777290058110048">（默认商户号仅作为联调测试使用，正式上线还请使用正式申请的商户号）
											</td>
										</tr>
										<tr>
											<td align="right" valign="middle">交易金额txnAmt：</td>
											<td valign="middle"><input type="text" name="txnAmt"
												value="1">（单位为分）</td>
										</tr>
										<tr>
											<td valign="middle"><b> <input name="button"
													type="button" value="跳转银联页面支付" class="btn"
													onClick="onPayForm2();" /></td>
											<td valign="middle">&nbsp;</td>
										</tr>
									</table> <%-- <jsp:include  page="/other/consume_faq.jsp"/> --%>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>