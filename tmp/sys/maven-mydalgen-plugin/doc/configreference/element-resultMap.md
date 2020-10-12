# resultMap标签
等同于Mybatis中resultMap的概念
>作者本人对于该标签使用也不频繁，可以使用operation的resultType属性替代		

# Required Attributes
| 属性名 | 描述 |
|----|---|
| name|resultMap的唯一标识|
| type|封装字段结果集的Java实体类|

# Optional Attributes
None
# Child Elements
- [&lt;column&gt;](element-column.md) (1..N) 定义结果集的列元素

# 示例		

	    <?xml version="1.0" encoding="UTF-8"?>
	    <table xmlns="http://smartboot.oschina.io/schema/mydalgen/table" 
	    	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	    	xsi:schemaLocation="http://smartboot.oschina.io/schema/mydalgen/table 
	    	http://smartboot.oschina.io/schema/mydalgen/table.xsd
	    	sqlname="user_info">
			<resultMap name="USER-INFO-1"
			     type="org.smartboot.smartweb.dal.dataobject.UserInfoDO1">
			     ***
			</resultMap>
			<resultMap name="USER-INFO-2"
			     type="org.smartboot.smartweb.dal.dataobject.UserInfoDO2">
			     ***
			</resultMap>
	    </table>