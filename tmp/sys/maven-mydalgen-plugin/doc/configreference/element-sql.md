# sql标签
遵循标准JDBC规则编写的SQL语句，生成代码后会成为DAO接口的注释信息，该标签内容必须包含在`<![CDATA[]]>`内。

# Required Attributes
None

# Optional Attributes
None
# Child Elements
None

# 示例		
```
<?xml version="1.0" encoding="UTF-8"?>
<table xmlns="http://smartboot.oschina.io/schema/mydalgen/table" 
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
        xsi:schemaLocation="http://smartboot.oschina.io/schema/mydalgen/table 
        http://smartboot.oschina.io/schema/mydalgen/table.xsd
        sqlname="user_info">
    <opertation **>
        <sql><![CDATA[
            select * from table where id=? 
          ]]></sql>
        ***
    </operation>
 </table>
```