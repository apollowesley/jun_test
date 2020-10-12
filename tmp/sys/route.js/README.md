## 引入文件说明
### 项目中不使用ramda  引入一个文件
```html
  <script type="text/javascript" src="../dist/route.all.min.js"></script>
```
## 使用ramda 引入三个文件
```html
<script src="../lib/ramda.min.js"></script>
<script src="../lib/jueji.js"></script>
<script src="../js/route.js"></script>
```
# 浏览器路由匹配
`内含函数流，中断，异步功能`
```javascript
var s = J.stream;
var ds = J.dostream;
var dofn = route.route([
	//解析URL参数并放到ctx.query中
	//nextp 代表执行下面的规则
	s(route.queryStringG,J.ctx("query"),route.nextp)
	//匹配/hello 匹配失败继续向下执行
	,s(route.nameP("/hello"),domlog)
	,s(route.nameP("/"),domlog)
	,s(route.paramP("/:a/:b"),domlog)
	,s(route.nameP("/hello"),domlog)
	,s(route.startP("/yibu"),R.tap(loadState),yibutest,domlog)
	,s(diyP,domlog)
	,domlog404
]);
dofn("/hello");
```
## 自动监控浏览器#(hash)发生变化
```javascript
//监控hash变化 获取URL执行dofn
ds(null,route.hashMilldam,dofn);
```
## 自动监控H5路由
```javascript
//利用H5 api改变URL 放入浏览器历史记录
//需要引入文件 /js/h5routeMilldam.js
ds(null,h5r.h5routeMilldam,dofn);
```
## 详见demo [在线demo](http://sandbox.runjs.cn/show/mg3ch4e4)

## J.stream 函数流 介绍
```javascript
var fn=J.stream(fn1,fn2,fn3);
fn(arg);
```
约等同于
```javascript
var ctx={};
var fn1r=fn1(arg,ctx);
var fn2r=fn2(fn1r,ctx);
var fn3r=fn3(fn2r,ctx);
return fn3r;
```
中断处理 比如`fn1`遇到业务错误不需要执行后面的函数
`fn1`返回 `J.left(数据)` 即可

异步处理 `fn2`需要请求接口获得数据之后交给`fn3`处理
fn2返回J.milldam即可  详见在线demo

## qq群讨论
426566496