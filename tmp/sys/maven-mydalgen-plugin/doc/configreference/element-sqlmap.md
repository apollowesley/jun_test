# sqlmap标签
遵循Mybatis的Mapper XML语法规则定义的SQL语句，该标签内容必须包含在`<![CDATA[]]>`内。

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
        <sqlmap><![CDATA[
            select * from table where id=#{id}
          ]]></sqlmap>
        ***
    </operation>
 </table>
```