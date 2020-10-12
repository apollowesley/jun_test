data.js — 简单的页面数据交互插件
==================================================

**data.js** 只关心如何获取/修改页面数据，没提供其它过多的功能，自然学起来简单，用起来问题少。

### 把 data.js 添加到您的网页 ###
data.js 没依赖第三方库，只要在 HTML 的 `<script>` 标签中引用它:
```
<head>
<script src="data.js"></script>
</head>
```

### 如何使用 data.js ###
比如有这么一段 HTML 代码
```
<!-- 姓名输入框 -->
<input data-name="user.name" type="text" />
<!-- 年龄输入框 -->
<input data-name="user.age" type="text" />
```
标签中的 data-name 属性相当于变量名，是 data.js 获取/修改页面数据的标识符。

```
// 修改变量名是 "user.name" 的页面数据
_('user.name', '小强');
// 获取变量名是 "user.name" 的页面数据
_('user.name'); => "小强"

// 修改变量名是 "user." 开头的页面数据
_('user.*', {'user.name': '小强', 'user.age': 11});
// 获取变量名是 "user." 开头的页面数据
_('user.*'); => {"user.name": "小强", "user.age": "11"}

// 修改变量名是 "user.name"、"user.age" 的页面数据
_(['user.name', 'user.age'], {'user.name': '小强', 'user.age': 11});
// 获取变量名是 "user.name"、"user.age" 的页面数据
_(['user.name', 'user.age']); => {"user.name": "小强", "user.age": "11"}

// 修改所有的页面数据
_('*', {'user.name': '小强', 'user.age': 11});
// 获取所有的页面数据
_('*'); => {"user.name": "小强", "user.age": "11"}

// 修改部分的页面数据
_('*', {'user.name': '小强'});
// 修改所有的页面数据，没指定值的页面数据清空
_('*', {'user.name': '小强'}, true);
// 批量修改页面数据
_('*', '这是输入框的文本');
// 把对象(纯粹的对象)赋值给页面数据
_('*',  _.primitive({}));
```
### 有用的方法 ###
```
// 对象的每个属性名加上前缀 "user."
var user = _.prefix({name: '小强', age: 11}, 'user.');
alert(JSON.stringify(user)); => {"user.name":"小强","user.age":11}

// 对象的每个属性名去掉前缀 "user."
var user = _.unprefix({"user.name":"小强","user.age":11}, 'user.');
alert(JSON.stringify(user)); => {"name":"小强","age":11}
```
### 扩展插件 ###
比如有这么一个 HTML 标签，其中 data-type 属性相当于变量类型
```
<input data-name="user.age" data-type="number" type="text" />
```
注册自定义类型的处理方法
```
// "_" 是 "$$data" 的别名，$$data.plugins 是自定义类型的地方，
// 上面标签的 "data-type" 属性值是 "number"， 那么获取/设置
// 这个标签的值，就会调用 $$data.plugins['number'] 中对应的方法，若没有
// 找到对应的方法，会抛出异常。
$$data.plugins['number'] = {
  getValue: function(element) {
    return Number(element.value);
  },
  setValue: function(element, value) {
    element.value = value;
  }
}

// 这时候获取到的返回值的类型是数字
_('user.age'); => 23
```

### 遇到几个问题 ###
```
// 问题1
// 若要赋给页面数据的值是 undefined，那是用 undefined 值更新
// 这个页面数据，还是选择不更新这个页面数据
var userName = undefined;
_('user.name'， userName);

// 问题2
// 若要更新一组页面数据，当值不是纯粹对象(JSON对象)的时候，
// 那是要用这个值更新这组所有的页面数据，还是选择这组页面数据不改变
_('user.*', '');
```



