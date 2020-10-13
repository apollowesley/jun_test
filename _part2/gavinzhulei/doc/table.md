### Table（表格）

表格组件，支持分页，滚动，查询过滤操作；查看 [组件/表格](#!/table) 提供的例子

#### 准备

index.html中加入样式文件
```html
<link rel="stylesheet" type="text/css" href="../assets/global/plugins/bootstrap-toastr/toastr.min.css"/>
<link rel="stylesheet" type="text/css" href="../assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
```
index.html中添加脚本文件
```html
<script src="../assets/global/plugins/bootstrap-toastr/toastr.min.js"></script>
```
/page/index.js中加入引用
```javascript
require('../../src/style/toast.scss')
```

#### 使用

1.	简单的表格
```javascript
import {VTable} from '../../../src/components'
export default{
	components:{
        VTable
    },
	methods:{
		loadTable(data){
            this.tableData = data.test
            //注1
        }
	}
}
```
```html
	<v-table :url="..." :size="15" @table:load="loadTable">
            <table class="table table-striped table-hover" >
                <thead>
                    <tr>
                        <th width="25%">测试</th>
                        <th width="25%">测试</th>
                        <th width="25%">测试</th>
                        <th width="25%">测试</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-if="tableData.length > 0" v-for="item in tableData">
                        <td>{{item["1"]}}</td>
                        <td>{{item["1"]}}</td>
                        <td>{{item["1"]}}</td>
                        <td>{{item["1"]}}</td>
                    </tr>
                </tbody>
            </table>
        </v-table>
```
> 注1： 这里需要将数据手动赋值给表格；如果表格是采用数据url的形式，loadTable方法是必须的

### 参数
| 参数   |  默认值 | 是否必须  | 描述  |
| ------------ | ------------ | ------------ | ------------ |
| url | | true | 表格数据url |
| filter | {} | false | 查询参数|
| paging | true | false | 是否启用分页 |
| size | 10 | false | 表格一页显示的条数（paging=true时使用） |
| reload | false | false | 设置此参数reload=true时，表格数据将刷新 |
| @table:load(data) | | true | 数据回调，回调后将数据遍历在表格中 | 