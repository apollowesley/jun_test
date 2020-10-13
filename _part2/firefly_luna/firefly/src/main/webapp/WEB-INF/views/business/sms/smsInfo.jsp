<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<body>
	<div class="pageContent">
		<form id="inputForm" method="post" name="inputForm"
			action="sms/saveSms"
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<div class="pageFormContent" layoutH="56">

				<p>
					<label>短信签名：</label> <select name="smsFreeSignName"
						class="combox required"
						remoteUrl="systemDic/getSystemDicByType?type=SMS_SIGN"
						style="width: 240px" defalutOption=false>
					</select>
				</p>
				<p>
					<label>短信模板：</label> <select name="smsTemplateCode"
						class="combox required"
						remoteUrl="systemDic/getSystemDicByType?type=SMS_TEMPLATE"
						style="width: 240px" defalutOption=false>
					</select>
				</p>
				<p>
					<label>短信接收号码：</label> <input name="recNum" type="text"
						class="required" style="width: 240px" />
				</p>
				<p>
					<label>短信接收人：</label> <input name="smsParamCode" type="text"
						class="required" style="width: 240px" />
				</p>
				<p>
					<label>短信发送人：</label> <input type="text" name="smsParamItem"
						maxlength="80" style="width: 240px" />
				</p>
				<p>
					<label>短信模内容：</label>
					<textarea style="width: 240px" rows="4" cols="25" name="smsParamProduct"></textarea>
				</p>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">保存</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</body>
</html>
