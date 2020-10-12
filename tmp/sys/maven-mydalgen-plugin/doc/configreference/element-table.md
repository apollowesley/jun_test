# table标签
table标签为mydalgen模板文件的根目录标签，在该标签内编写的所有配置都是围绕table标签关联的表对象进行的CRUD操作，即一个table标签对应一张数据库表。

# Required Attributes
| 属性名 | 描述 |
|----|---|
| sqlname|模板文件对应的数据库表名称|

# Optional Attributes
None
# Child Elements
- [&lt;resultMap&gt;](element-resultMap.md) (0..N) 定义查询结果对象模型。
- [&lt;opertation&gt;](element-opertation.md) (1..N) 定义CRUD操作

# 示例		

	    <?xml version="1.0" encoding="UTF-8"?>
	    <table xmlns="http://smartboot.oschina.io/schema/mydalgen/table" 
	    	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	    	xsi:schemaLocation="http://smartboot.oschina.io/schema/mydalgen/table 
	    	http://smartboot.oschina.io/schema/mydalgen/table.xsd
	    	sqlname="user_info">
			<resultMap **>
				***
			</resultMap>
			<opertation **>
	        	***
			</operation>
	    </table>