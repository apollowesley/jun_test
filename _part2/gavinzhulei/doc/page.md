### Page

页面基础组件，页面中的根组件，需要每个组件页面引用才能正常展示页面

#### 使用
```javascript
import {Page} from '../../../src/components'

export default{
    components:{
        Page
    }
}
```
```html
<page path-name="home">
    <h1>hello world!</h1>
</page>
```

### 参数
| 参数   |  默认值 | 是否必须  | 描述  |
| ------------ | ------------ | ------------ | ------------ |
|  path-name |   | true  | 当前页面组件的链接地址，保持和路由中定义的一致，（注意此参数在html中的写法）  |
|  iframe   | false | false | 如果为true，页面启用iframe嵌入页面 |
|  url      |       | false | 如果iframe为true时，url为嵌入页面的地址（仅在iframe=true时有用） |
