### 开始第一个页面

项目已经搭建完成了，现在就可以开始开发页面了，让我们来创建第一个页面吧

1.	在项目page目录中新建home.vue文件
	> 页面被看作是一个单独的组件，用.vue作为后缀。

2.	编写组件代码
	```javascript
<template>
				<page path-name="home">
						<h1>hello world!</h1>
				</page>
</template>
<script>
import Page from '../../../src/components/page.vue'
export default{
				components:{
					Page
				}
}
</script>
```
	> 页面用到Page组件，请参考 [组件/Page](#!/doc/page) 

3.	配置路由
	route.config.js
	```javascript
import Home from './page/home.vue'
export default{
			'/home':{
				name: 'home',
				component: Home
			}
}
```

4.	设置导航，在项目frame.vue文件中，设置导航数据，结构参考 [组件/Navbar](#!/doc/navbar) 数据结构部分
	```json
{
			"link":"home",
			"name": "首页",
			"icon": "icon-home"
}
```

5. 至此，我们的一个普通页面就搭建完成，刷新页面看看效果吧