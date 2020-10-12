# column标签
resultMap对象包含的字段集合		

# Required Attributes
| 属性名 | 描述 |
|----|---|
| name|查询语句中的表字段名称|
| javatype|字段名对应的Java数据类型|

# Optional Attributes
None
# Child Elements
None

# 示例		

	    <resultMap name="USER-INFO"
			type="org.smartboot.smartweb.dal.dataobject.UserInfoDO">
			<column name="id" javatype="int" />
			<column name="password" javatype="java.lang.String" />
		</resultMap>