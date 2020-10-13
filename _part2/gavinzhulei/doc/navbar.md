### Navbar （菜单导航）

该组件用在 Page 组件中，展示左侧菜单，不需要使用者单独使用，使用者只需要提供菜单数据，参考文章最后的数据结构

#### 使用方法

1.	导入并注册组件
    ```javascript
import Navbar from './navbar.vue'
	
    export default{
        components:{
            Navbar
        }
    }
```
2.	使用组件
	```html
<navbar :path="path"></navbar>
```

#### 参数方法

| 参数  | 默认值  |  是否必须 | 描述
| ------------ | ------------ | ------------ | ------------ |
| path  | null  | true | 当前页面的路由名称，与路由配置文件（route.config.js）中路由名称一致 |

#### 数据结构

|   | 默认值  | 描述  |
| ------------ | ------------ | ------------ |
|  navData |  [] | 菜单所需的json数据结构，其中"link"与项目路由配置文件（route.config.js）中定义路由名称保持一致  |

菜单所需的数据结构如下：
```json
[
    {
        "link":"home",
        "name": "首页",
        "icon": "icon-home"
    },
    {
        "name": "组件",
        "icon": "icon-folder",
        "children":[
            {
                "name": "面板",
                "link": "panel",
                "icon": "icon-user"
            }
        ]
    }
]
```

| key  | value  |
| ------------ | ------------ |
| link  | 页面组件的名称，参考路由定义  |
| name | 菜单名称 |
| icon | 菜单用到的ICON图标，参考Metronic样式的icon|
|children|子菜单，存在子菜单的情况下|

>	该数据可通过异步从服务器获取或者由前端人员自己定义，需要使用者将此数据存入localStorage中，如下
```javascript
window.localStorage.setItem('navData', JSON.stringify(data))
```




