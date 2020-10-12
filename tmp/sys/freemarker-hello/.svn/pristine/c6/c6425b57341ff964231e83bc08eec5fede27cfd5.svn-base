<#macro select id datas value="" key="" text="" headkey="" headtext="">
	<select id="${id}" name="${id}">
	<#if headkey!="">
	<option value="${headkey}">${headtext}</option>
	</#if>
	<#if datas?is_hash_ex>
	<#local keys=datas?keys/>
	<#list keys as key>
		<#if key==value>
			<option value="${key}" selected>${datas[key]}</option>
		<#else>
			<option value="${key}">${datas[key]}</option>
		</#if>
	</#list>
	<#else>
		<#list datas as data>
			<#if key!="">
				<#if value==data[key]?string>
					<option value="${data[key]}" selected>${data[text]}</option>
				<#else>
					<option value="${data[key]}">${data[text]}</option>
				</#if>
			<#else>
				<#if value==data>
					<option value="${data}" selected>${data}</option>
				<#else>
					<option value="${data}">${data}</option>
				</#if>
			</#if>
		</#list>	
	</#if>
	</select>
</#macro>