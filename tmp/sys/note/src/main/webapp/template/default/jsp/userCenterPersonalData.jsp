<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="user_cent_r">
<h1>个人资料<span>( <i>*</i> 必须填写项 )</span></h1>
<div class="Menulink"><ul><a class="selected">我的资料</a></ul></div>
<table>
<tr><td class="u_td_l">帐户/昵称：</td><td><input value='<%=request.getSession().getAttribute("UName")%>' readonly="readonly"/></td></tr>
<tr><td class="u_td_l">Email 邮箱：</td><td><input value='<%=request.getSession().getAttribute("UEmail")%>'  readonly="readonly"/></td></tr>
<tr><td class="u_td_l">手机号码：</td><td><input value='<%=request.getSession().getAttribute("TEL")%>'  readonly="readonly"/></td></tr>
</table>

<script type="text/javascript" src="${templatePath}/js/regAjax.js"></script>
<script type="text/javascript" src="${templatePath}/js/changeEmailLegal.js"></script>
<link href="${templatePath}/css/register.css" rel="stylesheet" type="text/css"/>
<form action="changePwandEmail.yy" method="post" name="changeEmail" id="changeEmail">
<h1>修改个人资料<span>( <i>*</i> 必须填写项，留空为不修改)</span></h1>
<div class="Menulink"><ul><a class="selected">修改个人资料</a></ul></div>
<table>
<tr><td class="u_td_l"><i>*</i>当前登录密码：</td><td><input id="password_now" name="password_old" type="password" value=""/><div id="password_msg" class="msg_con"></div></td></tr>
<tr><td class="u_td_l">帐户/昵称：</td><td><input id="username" name="username" type="text" value=""/><div id="username_msg" class="msg_con"></div></td></tr>
<tr><td class="u_td_l">Email 邮箱：</td><td><input id="email" name="email" type="text" value=""/><div id="email_msg" class="msg_con"></div></td></tr>
<tr><td class="u_td_l">手机号码：</td><td><input id="tel" name="tel" type="text" value=""/><div id="tel_msg" class="msg_con"></div></td></tr>
<tr><td colspan="2"  class="confirm_box"><span><input id="confirm_box" type="checkbox" onclick="if(this.checked){document.getElementById('sub_btn').removeAttribute('disabled');}else{document.getElementById('sub_btn').setAttribute('disabled','disabled');}"/><lable>修改成功后，系统会自动退出，需要重新登录。</lable></span></td></tr>
<tr><td class="u_td_l"></td><td><input id="sub_btn" class="btnText" type="button" value="保 存" onclick="checkform();" disabled="disabled"/></td></tr>
</table>
<input type="hidden" name="cpae" value="ce"/>
</form>
</div>