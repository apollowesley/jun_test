<#macro pager url totalPage curPage=1 class="" showPageNum=15>
	<#local halfPage=(showPageNum/2)?int/>
	${halfPage}
	<#if (halfPage>=curPage)>
		<@showPage start=1 end=curPage url=url class=class curPage=curPage/>
		<@showPage start=curPage+1 end=showPageNum curPage=curPage url=url class=class/>
	<#else>
		<@showPage start=curPage-halfPage end=curPage url=url class=class curPage=curPage/>
		<#if (curPage+halfPage>totalPage)>
			<#local endPage=totalPage/>
		<#else>
			<#local endPage=curPage+halfPage/>
		</#if>
		<@showPage start=curPage+1 end=endPage url=url class=class curPage=curPage/>
	</#if>
</#macro>

<#macro showPage start end curPage url class>
	<#list start..end as page>
		<#if curPage==page>
			[${page}]
		<#else>
			<a href="${url}" class="${class}">${page}</a>
		</#if>
	</#list>
</#macro>