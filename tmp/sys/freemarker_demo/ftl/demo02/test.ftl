<#-- list示例 -->
<#assign season_seq = ["winter", "spring", "summer", "autumn"]>
<#list season_seq as season>
${season_index + 1}. ${season}<#if season_has_next>,</#if>
</#list>

<#-- 函数示例 -->
${demo02.hello()}