# extraparams标签
自定义DAO接口入参,参数数量、类型、顺序由其子标签param定义。一旦设置了extraparams，则operation属性paramType失效		

# Required Attributes
None

# Optional Attributes
None
# Child Elements
- [&lt;param&gt;](element-param.md) (1..N)

# 示例		

	<opertation **>
		<extraparams>
			<param name="param1" javatype="int">
			<param name="param2" javatype="java.lang.String">
			<param name="param3" javatype="xx.yy.ZZ">
		</extraparams>
    	***
	</operation>