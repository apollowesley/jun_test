#jValid【原创】

##一个类搞定javabean的验证【依赖fastjson】
【1个类搞定后端验证，依赖fastjson;默认使用的jdk8的编译级别。如果使用jdk6，使用validator6包下的类】

配置说明：json对象配置的方式，以{属性名:规则}的格式配置，多个规则之间使用";"分隔如：{属性名:规则1；规则2}
使用说明：MsgValidator用于需要返回验证消息的验证。辅助于开发或前端消息展示【可以根据需要自己定义消息】;ValidatorJ仅用于校验，不反悔验证失败的规则消息；

####不要验证消息[使用ValidatorJ] 与 要验证消息[使用MsgValidator]
if(!MsgValidator.valid(mobile, cfgs)){
　　req.set("msg",MsgValidator.msg());//获取验证失败的消息
　　return "验证不通过";
}

if(ValidatorJ.valid(mobile, cfgs)){
　　return "验证通过";
}

####通用规则

* 必填验证required："{email:'required;email'}"
* 可选验证，去掉required规则即可："{email:'email'}"

####字符串的规则

* pattern："{mobile:\"required;pattern:\^((13[0-9])|(15[\^4,\\\\\\\\D])|(18[0,5-9]))\\\\\\\\d{8}$\"}"

* length必须为11个长度："{mobile:'required;length:11'}"
* length必须小于等于11个长度："{address:'required;length:0,11'}"
* length必须为6~12个长度："{password:'required;length:6-12'}"

备注：
1.可选验证就是可空的字段，但是，有时候比如手机号，不填不校验，填了需要校验的这种情况。
2.有pattern规则的时候，需要将所有规则用双引号引起来，如上，否则内部解析会有问题，还有\\\\需要写为\\\\\\\\

***

####日期时间规则

* 日期："{createDate:'required;date'}"

* 时间："{createTime:'required;time'}"

* 日期时间："{createDate:'required;datetime'}"

* 时间戳："{createDate:'required;timestamp'}"

***

####integer规则

* 全部整数："{age:'required;integer'}"或"{age:'required;integer:+0-'}"

* 正整数："{age:'required;integer:+'}"

* 负整数："{age:'required;integer:-'}"

* 0和正整数："{age:'required;integer:0+'}"

* 0和负整数："{age:'required;integer:0-'}"

***

####range规则

* 范围域，0-50的闭区间：{amount:'required;range:[0,50]'}

* 离散域[类似枚举]：{amount:'required;range:(-2,-1,0,1,2,3)'}

* 范围域+离散域：{amount:'required;range:(-2,-1,0,1,2,3)[10,100]'}

***

####compare规则

* 小于："{createTime:'required', updateTime:'required;compare:<,createTime'}"

* 大于："{createTime:'required', updateTime:'required;compare:>,createTime'}"

* 小于等于："{createTime:'required', updateTime:'required;compare:<=,createTime'}"

* 大于等于："{createTime:'required', updateTime:'required;compare:>=,createTime'}"

备注：支持字符串的大小，日期，时间，日期时间，数字的大小，金额的大小比较


如果你喜欢，请支持一下作者，鼓励我做更多更好用的开源作品。

<img src="https://git.oschina.net/uploads/qrcode/qrcode_alipay_14749524821220485.png" width="160" height="160" align=center />

####使用方法

#####字符串的校验
//只支持字符串单值校验，别的类型可以转成字符串，因为所有类型都可以用字符串表示
String mobile = "13265968748";

//校验规则,属性名可以随意，这里使用"val"
String cfgs = "{val:'required;mobile'}";

if(ValidatorJ.valid(mobile, cfgs)){
　　return "验证通过";
}else{
　　return "验证不通过";
}

#####java bean的校验
Cup cup = new Cup("0");//bean对象
String cfgs = "{name:'required;integer'}";//校验规则

if(ValidatorJ.valid(cup, cfgs)){
　　return "验证通过";
}else{
　　return "验证不通过";
}

#####list<object>的校验
Cup cup = new Cup();
Cup cup2 = new Cup("aaa");
List cs  = new ArrayList();//list集合
cs.add(cup);
cs.add(cup2);
String cfgs = "{name:'required'}";

if(ValidatorJ.valid(cs, cfgs)){
　　return "验证通过";
}else{
　　return "验证不通过";
}

#####JsonArray的校验
String css = "[{name:'aa'},{name:'bb'},{name:'cc'},{name:''}]";
JSONArray cs = JSONArray.parseArray(css);//JsonArray对象的校验
String cfgs = "{name:'required'}";

if(ValidatorJ.valid(cs, cfgs)){
　　return "验证通过";
}else{
　　return "验证不通过";
}
