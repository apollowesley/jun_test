#cn.liulu.LMap v0.0.1 

> LMap用于跨地图库的js地图开发 目的是减少不同地图api所造成的重复代码 另外添加了一些额外的特性 以支持不同的需求 
相比高德api本人比较喜欢百度api才使用百度api作为蓝本进行开发的 \* _ \*|||

> 对于原本的api 是支持的 比如你使用高德地图 引入文件后使用LMap调用原始高德api是支持的

> 目前支持的api 只是百度api和 高德api 免费版 分别是 BMap和AMap

## 依赖 raphaelJs

## 以下是目前支持的通用api

### 基础类
* LMap.Point - 全部支持
* LMap.Pixel - 全部支持
* LMap.Bounds - containsPoint getCenter getSouthWest getNorthEast
* LMap.Size - 全部支持

### 地图

* LMap.Map - 增加了bind和unbind绑定事件 其实和原来的api是一样的 

> 默认设置 ： 可拖拽 不可缩放 双击放大 禁用键盘 禁用惯性拖拽 禁用连续缩放 启用双指缩放  启用自适应 最小与最大缩放级别固定为3-16


## 目前
> 目前针对百度地图的开发：
* 所有类都可以使用 
* 所有静态属性已经挂载到LMap上 原名BMAP_XXX 改为 LMap.LMAP_XXX
 * 增减了三个静态属性 
  * LMap.LMAP_EDITOR_CIRCLE_EDIT_TYPE_1 = 0; -- 编辑圆 使用圆形模式
  * LMap.LMAP_EDITOR_CIRCLE_EDIT_TYPE_2 = 1; -- 编辑圆 使用多边形模式
  * LMap.LMAP_EARTH_RADIUS = 6370996.81; -- GeoTool类使用的属性 也可全局使用
* 添加了三次贝塞尔曲线覆盖物 可以自定义曲线 因为原版的api使用的都是折线实现的曲线 所以我就 想尝试使用svg实现曲线覆盖物 
* 添加图形编辑库 未完善 可以使用这个编辑器 编辑Marker polyline polygon circle Curve  另外运行编辑圆有两种模式：多边形模式和圆形模式
* 新增api
 * LMap.GeoTool - 静态工具方法类 复制了百度开源库的GeoUtils的api 地址 [http://api.map.baidu.com/library/GeoUtils/1.2/src/GeoUtils.js](http://api.map.baidu.com/library/GeoUtils/1.2/src/GeoUtils.js)
 * LMap.Curve(命令列表-Array&lt;LMap.CurveCommand&gt;,曲线样式-Object) - 曲线覆盖物
  * 类似这样 ![img](http://i1.tietuku.com/6161de9de560649e.png)
 * LMap.CurveCommand(命令-String,参数列表-Array&lt;LMap.Point&gt;) - 曲线覆盖物的svgPath命令参数类 目前支持M和C两个命令
 * LMap.Editor 编辑类 
  * edit(Overlay) - 添加覆盖物 
  * open() - 开始使用鼠标编辑添加的覆盖物 
  * close() - 关闭使用鼠标编辑的覆盖物
* 即将添加的api 或者功能
 * 编辑图形的时候 临近点吸附 - 解决划区域时候的区域交叉不准确问题 当然只是在Editor类中 有这个功能 \* _ \*
  
> 目前高德所有类还没有挂载 等百度api完善再说
