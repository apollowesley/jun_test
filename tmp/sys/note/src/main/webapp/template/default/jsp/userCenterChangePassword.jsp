<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="user_cent_r">
<script type="text/javascript" src="${templatePath}/js/changePwLegal.js"></script>
<link href="${templatePath}/css/register.css" rel="stylesheet" type="text/css"/>
<form action="changePwandEmail.yy" method="post" name="changePw" id="changePw">
<h1>修改密码<span>( <i>*</i> 必须填写项 )</span></h1>
<div class="Menulink"><ul><a class="selected">修改密码</a></ul></div>
<table>
<tr><td class="u_td_l"><i>*</i>当前登录密码：</td><td><input id="password_old" name="password_old" type="password" value=""/><div id="password_msg" class="msg_con"></div></td></tr>
<tr><td class="u_td_l"><i>*</i>设置新的密码：</td><td><input id="password_new" name="password_new" type="password" value=""/><div id="password2_msg" class="msg_con"></div></td></tr>
<tr><td class="u_td_l"><i>*</i>确认新的密码：</td><td><input id="password_new2" name="password_new2" type="password" value=""/><div id="password3_msg" class="msg_con"></div></td></tr>
<tr><td colspan="2"  class="confirm_box"><span><input id="confirm_box" type="checkbox" onclick="if(this.checked){document.getElementById('sub_btn').removeAttribute('disabled');}else{document.getElementById('sub_btn').setAttribute('disabled','disabled');}"/><lable>修改成功后，系统会自动退出，需要重新登录。</lable></span></td></tr>
<tr><td class="u_td_l"></td><td><input id="sub_btn" class="btnText" type="button" value="保 存" onclick="checkform();" disabled="disabled"/></td></tr>
</table>
<input type="hidden" name="cpae" value="cp"/>
</form>
</div>