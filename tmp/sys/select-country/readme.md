# jQuery世界国家下拉选择框

## 三大特性

- 支持默认选中项
- 支持实时搜索框，筛选数据
- 支持下拉列表每个国家带国旗图标

## 配置方式

​	select标签内置了三个属性data-default、data-live-search和data-flag

1. data-default属性可以配置默认选中项
2. data-live-search属性可以配置是否开启下拉列表的搜索框，值为"true"或"false"。
3. data-flag属性可以配置下拉的国际列表是否带有国旗图标，值为"true"或"false"。

## 示例

```html
<select class="selectpicker countrypicker" 
	data-live-search="true" 
	data-default="CN"
    data-flag="true"></select>
```

![预览](https://gitee.com/LinZhaoguan/select-country/raw/master/preview/p.gif)
