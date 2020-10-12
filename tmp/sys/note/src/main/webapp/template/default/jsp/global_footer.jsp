<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="footer">
<div class="footcontent">
<div class="onerow">
<div class="copyright">copyright&nbsp;&copy;2013-?&nbsp;PowerBy:无妄</div>
<a class="addfavorite" title="点击加入收藏夹" onclick="javascript:addfavorite_with_url('http://bill.vpigirl.com/','我的生活')">加入收藏夹</a>
</div>
<div class="onerow">
<b class="fl">常用工具：</b>
<ul class="tools fl">
<li><a href="http://this.vpigirl.com/commonly/colorcontrol.html" target="_blank">颜色对照</a></li>
<li><a href="http://this.vpigirl.com/commonly/EncryptionPage.php" target="_blank">加密解密</a></li>
<li><a href="http://this.vpigirl.com/commonly/commonHTMLescapecharacters.html" target="_blank">HTML转义</a></li>
<li><a href="http://this.vpigirl.com/commonly/commonregularexpressions.html" target="_blank">常用正则表达式</a></li>
<li><a href="http://this.vpigirl.com/commonly/wordcount.html" target="_blank">字数统计</a></li>
<li><a href="http://this.vpigirl.com/commonly/qrcode" target="_blank">二维码生成</a></li>
</ul>
<p class="icp fr">ICP主体备案号：京ICP备14020828号</p>
</div>

<div class="onerow">
<a href="http://bbs.vpigirl.com/" target="_blank"><b>技术总结</b></a>
</div>
</div>
</div>
<script type="text/javascript">
	function addfavorite_with_url(url, title) {
		if (window.sidebar) {
			window.sidebar.addPanel(title, url, "");
		} else if (document.all) {
			window.external.AddFavorite(url, title);
		} else if (window.opera && window.print) {
			return true;
		}
	}
</script>