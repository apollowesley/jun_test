<#macro page pageCtx rel="">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20"  <#if pageCtx.pageBean.pageSize==20>selected</#if>>20</option>
				<option value="50"  <#if pageCtx.pageBean.pageSize==50>selected</#if>>50</option>
				<option value="100" <#if pageCtx.pageBean.pageSize==100>selected</#if>>100</option>
				<option value="200" <#if pageCtx.pageBean.pageSize==200>selected</#if>>200</option>
			</select>
			<span>条，共${pageCtx.pageBean.totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${pageCtx.pageBean.totalCount}" numPerPage="${pageCtx.pageBean.pageSize}" pageNumShown="10" currentPage="${pageCtx.pageBean.currentPage}" <#if rel!="">rel="${rel}"</#if>></div>
</#macro> 
<#macro pageDialog pageCtx rel="">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="dialogPageBreak({numPerPage:this.value})">
				<option value="20" <#if pageCtx.pageBean.pageSize==20>selected</#if>>20</option>
				<option value="50" <#if pageCtx.pageBean.pageSize==50>selected</#if>>50</option>
				<option value="100" <#if pageCtx.pageBean.pageSize==100>selected</#if>>100</option>
				<option value="200" <#if pageCtx.pageBean.pageSize==200>selected</#if>>200</option>
			</select>
			<span>条，共${pageCtx.pageBean.totalCount}条</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${pageCtx.pageBean.totalCount}" numPerPage="${pageCtx.pageBean.pageSize}" pageNumShown="10" currentPage="${pageCtx.pageBean.currentPage}" <#if rel!="">rel="${rel}"</#if>></div>
</#macro> 
<#macro subMain>
<div id="subMainContent" class="pageFormContent" style="width:100%;display:none;padding:5px 0 0;height:300px;">
	<div class="formBar" style="border-width:1px 0;">
		<ul>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="closeSub">关闭</button></div></div>
			</li>
		</ul>
	</div>
	<div id="subMain" style="height:260px;overflow:hidden;"></div>	
</div>
</#macro>

<#macro uploadImg id name folder="" scriptData="" attributes="" fileExt="*.jpg;*.jpeg;*.gif;*.png" fileDesc="图像文件(*.jpg;*.jpeg;*.gif;*.png)" fileQueue="" onComplete="" onAllComplete="">
		<input id="${id}" type="file" name="${name}" ${attributes}
			uploader="<@s.url '/resources/js/dwz-ria/uploadify/scripts/uploadify.swf'/>"
			cancelImg="<@s.url '/resources/js/dwz-ria/uploadify/cancel.png'/>" 
			buttonImg="<@s.url '/resources/images/aqua1.jpg' />" 
			script="oss/upload.do?prefix=${folder}" 
			wmode="transparent"
			folder="${folder}"								
			fileQueue="${fileQueue}"
			scriptData="${scriptData}"
			onComplete="${onComplete}"
			onAllComplete="${onAllComplete}"
			fileExt="${fileExt}"
			fileDesc="${fileDesc}" />
</#macro>


<#macro uploadDoc id name folder="" scriptData="" attributes="" fileExt="*.pdf" fileDesc="文档文件(*.pdf)" fileQueue="" onComplete="" onAllComplete="">
		<input id="${id}" type="file" name="${name}" ${attributes}
			uploader="<@s.url '/resources/js/dwz-ria/uploadify/scripts/uploadify.swf'/>"
			cancelImg="<@s.url '/resources/js/dwz-ria/uploadify/cancel.png'/>" 
			buttonImg="<@s.url '/resources/images/aqua1.jpg' />" 
			script="oss/doc/upload.do?prefix=${folder}" 
			wmode="transparent"
			folder="${folder}"								
			fileQueue="${fileQueue}"
			scriptData="${scriptData}"
			onComplete="${onComplete}"
			onAllComplete="${onAllComplete}"
			fileExt="${fileExt}"
			fileDesc="${fileDesc}" />
</#macro>


<#macro tagInput tagNames="" width="500px" height="100px">
    <#include "../views/tag/tag_input.ftl"/>
</#macro>

<#macro choose_teacher id="orgview.id" name="orgview.orgName" nameValue="" idValue="" class="" lookupGroup="userview">
<input type="hidden" name="${id!}" id="${id!}" value="${(idValue)!}" class="${class!}"/>
<input type="text" name="${name!}" id="${name!}" value="${nameValue!}" readonly class="${class!}"/>
<a id="useridedit" href="perm/user/dialog/open.do?type=TEACHER" width="874" height="545" rel="userdialog" fresh=true mask=true  class="btnLook" lookupGroup="${lookupGroup!}" title="选择教师"></a>
</#macro>