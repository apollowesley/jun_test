<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hello FreeMarker</title>
</head>
<body>
<h4>freemarker其他常见内建函数</h4>
<#assign word = "  hello freemarker,java   "/>
<#assign nowDate = "2018年2月26日"?date("yyyy年MM月dd日")/>
<#assign nowTime = "13:17:26"?time("hh:mm:ss")/>
<#assign nowDateTime = "2018年2月26日13:17:26"?datetime("yyyy年MM月dd日hh:mm:ss")/>
<div>
    <h2>处理字符串内建函数：【${word}】</h2>
    <p>substring 字符串的截取【${word?substring(4)}】</p>
    <p>cap_first 首字母变大写【${word?capFirst}】</p>
    <p>ends_with 以"ker"结尾的函数【${word?endsWith("ker")}】</p>
    <p>contains 字符串是否包含"mar"目标字符串的函数【${word?contains("mar")}】</p>
    <p>starts_with 字符串以...开始【${word?startsWith("free")}】</p>
    <p>如何把一个字符串转化为date 、datetime 、time类型的函数 date datetime time【${nowDate}、${nowTime}、${nowDateTime}】</p>
    <p>index_of 字符串所在的位置【${word?indexOf("mar")}】</p>
    <p>last_index_of 字符串最后所在的位置【${word?lastIndexOf("e")}】</p>
<#--这样会报错，freemarker规定【不能直接输出除了字符串和数字之外的类型，否则会报错】-->
    <p>split 分割字符串【<#list word?split(",") as item>${item}——</#list>】</p>
    <p>trim 去掉字符串两头的空格【${word?trim}】</p>
</div>

<#assign PI=3.1415926/>
<#assign numList = [12,0.23,89,12.03,69.56,45.67,-0.56,-8.05,-89.56,4.69]/>
<div>
    <h2>处理数字的内建函数:【${PI}---3.1415926】</h2>
    <p>round 四舍五入【${PI?round}】</p>
<#list numList as num><p> ${num} ?round=${num?round} ?floor=${num?floor} ?ceiling=${num?ceiling}</p> </#list>
    <p>string x?string("0.##") 对数字进行格式化【${PI?string("0.###")}】</p>
</div>
<#assign listVar = ["java","python","go","c#","html"]/>
<div>
    <h2>处理list内建函数：【<#list listVar?reverse as item>${item}、</#list>】</h2>
    <p>first 取list第一个值【${listVar?first}】</p>
    <p>last 取list最后一个值【${listVar?last}】</p>
    <p>seq_contains 这个序列是否包含【${listVar?seqContains("python")}】</p>
    <p>seq_index_of 这个序列所在的位置【${listVar?seqIndexOf("go")}】</p>
    <p>size list长度【${listVar?size}】</p>
    <p>reverse 倒序【<#list listVar?reverse as item>${item}、</#list>】</p>
    <p>sort 升序排序【<#list listVar?reverse as item>${item}、</#list>】</p>
    <p>sort_by 根据属性排序【对封装类型排序】</p>
    <p>trunk 把字符串分块处理【size=${listVar?chunk(3)?size},<#list listVar?chunk(3)[0] as item>${item}--</#list>】</p>
</div>
<#assign isBoolean=true?isBoolean />
<#assign isString="helloWorld"?isString />
<#assign isNumber=100?isNumber />
<#assign isMethod="not method"?isMethod />
<#assign evalResult="2*9+2"/>
<#assign hasContent=""?hasContent?string("yes has content","no doesn't has content")/>
<div>
    <h2>其他内建函数</h2>
    <p>is函数：判断变量的类型【${isBoolean}】</p>
    <p>is_string 字符串【${isString}】</p>
    <p>is_number 整数【${isNumber}】</p>
    <p>is_method 方法【${isMethod}】</p>
    <p>() 对变量进行判断</p>
    <p> eval 求值函数2*9+2【${evalResult?eval}】</p>
    <p> 是否有内容【${hasContent}】</p>
</div>
</body>
</html>