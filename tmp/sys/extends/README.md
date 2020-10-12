
# layui 第三方组件发布规范

> 平台主要是收集来自 layui 官方以外的组件，由开发者自己维护。目的是为了释放 layui 官方原创组件的压力，将更具想象力的组件生态开放给社区，让那些有能力有兴趣的开发者也能参与进来，以群众之力，共筑 layui

## 第一步：制作组件
我们鼓励开发者遵循 “[layui 模块规范](https://www.layui.com/doc/base/modules.html#extend)” 来扩展自己的组件，原则上我们只接受原创组件，或者宽松协议下的知名组件改造。

### 组件收录要求：
* 组件最好是自己独立原创，也可以是二次开发，但不可完全照搬其它开源组件代码。
* 组件必须遵循 layui 模块规范
* 组件的 UI 风格必须与 layui 的风格相符
* 组件必须具备一定实用性
* 组件必须有较为详细的文档和示例

### 组件目录规范：
```
  ├─layui /layui 基础框架
  │─layui_exts //存放第三方组件的目录
      └─regionSelect //以 regionSelect 组件举例，以组件模块名建立一个目录
        └─regionSelect.js //组件核心 JS 库
        └─regionSelect.css //组件 CSS 库
```

### 编写组件使用说明：
```
layui.config({
  base: '../js/layui_exts/' //配置 layui 第三方扩展组件存放的基础目录
}).extend({
  regionSelect: 'regionSelect/regionSelect'
}).use(['regionSelect'], function(){
  var regionSelect = layui.regionSelect;
  
  //下面以 regionSelect 组件为例
  //执行实例
  regionSelect.render({
    elem: '#test'
    ,layout: ['province', 'city', 'county'] //自定义联动层级：省、市、区，默认为：省、市、区、街道
  });
  
});
```
具体也可以下载我们的：[示例文件](https://fly.layui.com/extend/demo/#download)

## 第二步：发布组件
> 当你拥有了自己的扩展组件，请发布到 layui 第三方组件平台。

* 组件标题：为你的组件取一个好的标题，让大家一目了然知道该组件是做什么的。
* 组件模块名：这是组件唯一的表示，必须对应组件源码内部的扩展模块名，否则不予通过。
* 组件功能描述：简单描述你的组件是做什么的，具备什么功能等。
* 组件文档：请尽可能详细说明你的组件的用法。
* 码云和 GitHub 地址：我们推荐你将组件托管在码云（gitee.com），然后在此提供组件在码云的仓库地址。GitHub 同理。
* 来源地址：如果组件有原始出处（或官网），我们推荐你填写。
* 上传组件资源包：请将组件资源资源包上传，里面必须包含组件源码。

>提交后，组件进入审核。通过后才会予以显示，期间你可以随时编辑。

## 第三步：维护组件
> 请定期维护和升级你的组件，并在平台更新文档和组件资源包，你的努力将会被 layui 所见证。
