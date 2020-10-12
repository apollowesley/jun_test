# param标签
定义入参对象，[extraparams](element-extraparams.md)的子标签

# Required Attributes
| 属性名 | 描述 |
|----|---|
| name|自定义DAO接口参数名称|
| javatype|自定义DAO接口参数类型|

# Optional Attributes
None
# Child Elements
None

# 示例		

```
<opertation **>
    <extraparams>
        <param name="param1" javatype="int">**
        <param name="param2" javatype="java.lang.String">
        <param name="param3" javatype="xx.yy.ZZ">
    </extraparams>
    ***
</operation>
```