<template>
    <div class="" style="position: relative;height: 100%;">
        <div class="container" style="display: flex;flex-direction: column;width: 100%;">
            <div style="display: flex;flex-direction: column;padding-top: 10px;">
                <div style="display: flex;justify-content: space-between;padding-bottom: 15px;padding-top:15px ;">
                	<div class="" style="align-self: flex-start;display: flex;">
                        <el-cascader v-model="searchCategoryCascader" expand-trigger="hover" :options="categoryList" :props="props"   @change="cascaderChange">
                        </el-cascader>
                        <el-cascader  change-on-select v-model="searchCategoryCascader2" expand-trigger="hover"  :options="categoryList2" :props="props2"   @change="cascaderChange2">
                        </el-cascader>
                        <div style="padding-left: 10px;">
                            页码<el-input v-model="search.p" placeholder="页" class="handle-input" style="width: 70px;padding-left: 4px;padding-right: 5px;"></el-input>
                        </div>
                        <div style="padding-left: 10px;">
                            数量<el-input v-model="search.n" placeholder="请求数量" class="handle-input" style="width: 70px;padding-left: 4px;padding-right: 5px;"></el-input>
                        </div>
                	    <!-- <el-input v-model="select_word" placeholder="筛选关键词" class="handle-input mr10"></el-input> -->
                	    <el-button type="primary" icon="search" @click="startSearch">获取数据</el-button>
                	</div>
                	<div style="align-self:flex-end;">
                        <el-button type="primary" icon="search" @click="handleEdit('add',form)">添加</el-button>
                        <el-button v-if="search.isTrashed" type="info" icon="el-icon-arrow-left" @click="trashed(0)">切回</el-button>
                        <el-button v-else type="info" icon="iconfont icon-lajigarbage7" @click="trashed(1)">回收站</el-button>
                		<!-- <el-button type="primary" style="align-self: flex-end;" icon="delete" class="handle-del mr10" @click="addUser">添加用户</el-button> -->
                	</div>
                </div>
                <template>
                    <el-table :data="userData" stripe type="index" border class="table" ref="multipleTable" max-height="700"
                        style="width: 100%;">
                        <el-table-column prop="id" label="ID"  width="120"></el-table-column>
                        <el-table-column prop="image" label="主图" width="120">
                             <template slot-scope="scope">
                                 <div style="height: 70px;">
                                     <img :src="getFileUrl(scope.row.image,'image')" style="width: 100px;"  mode="cover">
                                 </div>
                             </template>
                        </el-table-column>
                        <el-table-column prop="flux" label="浏量"  width="60"></el-table-column>
                        <el-table-column prop="areaName" label="地区"  min-width="70"></el-table-column>
                        <el-table-column prop="func_name" label="标题" min-width="100">
                            <template slot-scope="scope">
                                <div>
                                    <span v-html="scope.row.title"></span>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column prop="url" label="URL链接地址" min-width="150"></el-table-column>
                        <el-table-column prop="create_time" label="创建日期" data-key="create_time" :formatter="formatDate"
                            width="100">
                        </el-table-column>
                        <el-table-column prop="update_time" label="更新日期" data-key='update_time' :formatter="formatDate"
                            width="100">
                        </el-table-column>
                        <el-table-column fixed="right" label="编辑" width="180">
                            <template slot-scope="scope">
                                <div v-if="!search.isTrashed">
                                    <el-button type="text" size="small" icon="el-icon-delete" style="color:red" @click="handleDelete('del', scope.row,false)">删除</el-button>
                                    <el-button type="text" size="small" icon="el-icon-edit" @click="handleEdit('edit', scope.row)">编辑</el-button>
                                     <el-button type="text" size="small" icon="el-icon-document" @click="handleDetail(scope.row)">详情</el-button>
                                </div>
                                <div v-else>
                                    <el-button type="text" plain size="small" icon="el-icon-refresh" @click="handleDelete('restore', scope.row)">恢复</el-button>
                                    <el-button type="text" size="small" icon="el-icon-delete" style="color:red" @click="handleDelete('del', scope.row,true)">彻底删除</el-button>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="pagination" style="">
                        <el-pagination background @current-change="handleCurrentChange" :page-size="10" layout="prev, pager, next"
                            :total="tableData.length">
                        </el-pagination>
                    </div>
                </template>
            </div>
        </div>
        <div  v-if="isShow"   style="width: 100%; position: absolute;z-index: 999;top:0;bottom: 0;height: 100%; background:rgba(0,0,0,0.8);display: flex;justify-content: space-between;flex-direction: column;">
              <div style="color: red;font-size: 3em;position: absolute;right: 20px;" @click="isShow=!isShow">╳</div>
                  <el-form    class="demo-table-expand" label-width="100px"  style="background: #fff;width: 90%;margin: 5%;height: 90%;" >
                    <el-form-item label="ID">
                        <span>{{ detail.id }}</span>
                    </el-form-item>
                    <el-form-item label="标题" >
                        <span v-html="detail.title"></span>
                    </el-form-item>
                    <el-form-item label="图片" style="width: 100%;height: 400px;">
                        <div style="display: flex;justify-content: center;">
                            <img :src="getFileUrl(detail.image,'image')"  style="height: 300px; padding: 5px;" />
                        </div>
                    </el-form-item>
                    <el-form-item label="URL地址">
                        <div style="word-wrap:break-word; word-break:normal;">{{detail.url}}</div>
                    </el-form-item>
                    <el-form-item label="信息">
                        <div class="form-detail-info" style="word-wrap:break-word; word-break:normal;">
                            <span class="form-detail-info-span">访问量:<span>{{detail.flux}}</span></span>
                            <span class="form-detail-info-span">投放地区:<span>{{detail.areaName}}</span></span>
                            <span class="form-detail-info-span">经度:<span>{{detail.longitude}}</span></span>
                            <span class="form-detail-info-span">纬度:<span>{{detail.latitude}}</span></span>
                        </div>
                    </el-form-item>
                </el-form>
        </div>

        <!-- 公共form表单弹出框 -->
        <el-dialog :title="action=='add'?'添加':'修改'" :visible.sync="editVisible" width="70%">
            <div class="form-box" style="align-self: center;">
            	<el-form ref="form" :model="form" label-width="100px" style="padding-top: 20px;">
            		<el-form-item label="标题" prop="username">
            			<el-input v-model="form.title"></el-input>
            		</el-form-item>
            		<el-form-item  label="添加图片" prop="username">
            			<update-file openSort="true"  :controlledAdd="imageAdd"   :imgList="imgList" :max="max"  :maxwh="maxwh" :quality="quality" :change="fileChange"></update-file>	
            		</el-form-item>
            
            		<el-form-item label="板块" style="text-align: left;">
            			<el-cascader  change-on-select  :options="categoryList" :props="props"  v-model="genealogy"  style="width:100%"  @change="categoryChange">
            			</el-cascader>
            		</el-form-item>
                    
                    <el-form-item label="地区" style="text-align: left;">
                    	<el-cascader  change-on-select  :options="categoryList2" :props="props2"  v-model="genealogy2"  style="width:100%"  @change="categoryChange2">
                    	</el-cascader>
                    </el-form-item>
                    
            		<el-form-item label="状态" style="text-align: left;">
            			<el-select  style="width:100%"  v-model="form.state" prop="newsState" placeholder="请选择" >
            				<el-option :key="item.id" v-for="(item) in newsState" :label="item.name" :value="item.id"></el-option>
            			</el-select>
            		</el-form-item>
            		<el-form-item label="URL地址" style="text-align: left;">
            			<el-input  v-model="form.url">
            			</el-input>
            		</el-form-item>
            		<el-form-item>
            			<el-button type="primary" @click="onSubmit('form')">提交</el-button>
            			<el-button @click="editVisible=false">取消</el-button>
            		</el-form-item>
            
            	</el-form>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    import yc from "yc-js";
    import Request from "../../../request/index.js"
    import updateFile from "../../../components/template/updateFile.vue"
    import newsState from "../../../common/data/newsState.js"
    var     $file= yc.File
    var     Arr=yc.Arr;
    var     Time=yc.Time;
    export default {
        components:{
        	updateFile
        },
        name: 'adminList',
        data() {
            return {
                isShow:false,
                searchCategoryCascader:[0],
                searchCategoryCascader2:[110000],
                maxwh:1920,//图片尺寸
                quality:1,//图片质量
                max:1,//图片数量
                imgList:[],

                uploadApi:{
                    add:"UserNewsImage_Upload",
                    delete:"UserNewsImage_Delete",
                },
                genealogy:[0],
                categoryList: [],
                props: {
                  label: 'name',
                  value:'id',
                  children: 'children'
                },
                
                 genealogy2:[0],
                categoryList2:[],
                props2: {
                  label: 'name',
                  value:'id',
                  children: 'children'
                },
                newsState:newsState,
                search:{
                    p:0,//页码=服务器请求过的页码
                    isTrashed: 0,
                    n:100,//请求100条数据
                    
                },//搜索
                action: 'add',
                page:1,//页码 本地数据展现的页码
                num:10,//每页数量
                pageNum:10,//看过的数量
                tableData: [],
                detail:{},//文章详情
                // isTrashed: 0,
                editVisible: false, //编辑
                row:{},
                form: {
                    title: '',//标题
                    url: '',//内容
                    image:'',
                    area_code:'',
                    category_id: '',//分类
                    state: ""//状态
                }
            }
        },
        created() {
            Request('AdminAdCategory_List').then(e=>{
                // console.log(e)
                if(e.data.code==200){
                     var data=e.data.data;

                     var Obj={};
                     // 转成obj 用id作为key
                     for (let i=0; i<data.length;i++) {
                         Obj[data[i].id+'']=data[i];
                     }
                      this.list= JSON.parse(JSON.stringify(Obj));//原始数据obj index=id
                      var categoryList= Arr.getTree(Obj);
                      categoryList.push({id:0,name:"不限"});
                      this.categoryList=Arr.getTreeSortBy(categoryList,'ranking','children',false);
                }
            })
            Request('Area_List').then(e=>{
                // console.log(e)
                if(e.data.code==200){
                     var data=e.data.data;
            
                     var Obj={};
                     // 转成obj 用id作为key
                     for (let i=0; i<data.length;i++) {
                         Obj[data[i].id+'']=data[i];
                     }
                      this.list2= JSON.parse(JSON.stringify(Obj));//原始数据obj index=id
                      var categoryList2= Arr.getTree(Obj);
                      categoryList2.push({id:0,name:"不限"});
                      this.categoryList2=Arr.getTreeSortBy(categoryList2,'ranking','children',false);
                }
            })  
            this.getData();
        },
        watch:{
            editVisible(e){
               if(e){
                   var categoryList=this.categoryList;
                   var categoryList2=this.categoryList2;
                   
                    this.categoryList=categoryList.filter((e)=>{
                        if(e.id!=0){
                            return e;
                        }
                    });
                    this.categoryList2=this.categoryList2.filter((e)=>{
                          if(e.id!=0){
                              return e;
                          }
                      });
               }else{
                   this.categoryList2.push({id:0,name:"不限"});
                   this.categoryList.push({id:0,name:"不限"});
               }
            },
             search: {
        　　　　handler(newValue, oldValue) {
                    if(newValue.n%10!=0 || newValue.n>100){
                       this.$message.warning('只能是10的倍数，最大值100');
                        this.search.n=10;
                    }
                    // this.page=1; 
        　　　　　　console.log(newValue)
        　　　　},
        　　　　deep: true
        　　}
        },
        computed: {
            userData: {
                get() {
                   var page= (this.page-1)*this.num;
                   var max=page+this.num;
                   var i=-1;
                    return  this.tableData.filter(e=>{
                         i++;
                        if (i >=page && i <max) {
                        	return e
                        } 
                    });
                }
            }
        },
        
        methods: {
            getFileUrl(url,type){
                    return this.$config.getFileUrl(url,type);   
            },imageAdd(e){
                if(e.src){
                    this.imgList=[{src:e.src}];
                 this.form.image=e.src;
                }
            },
            fileChange(e){
                if(e && typeof e=='object' && e[0] && e[0].src){
                    this.form.image=e[0].src; 
                    this.detail.image=e[0].src;
                }
                this.image=e; 
            },
            startSearch(){
                this.page=1;
               this.getData();
            },
            
            categoryChange(arr){
                this.form.category_id=arr[arr.length-1];
            },
            categoryChange2(arr){
                this.form.area_code=arr[arr.length-1];
            },
            cascaderChange(arr) {
            this.search.category_id=arr[arr.length-1];
            },
            cascaderChange2(arr) {
            this.search.area_code=arr[arr.length-1];
            },
            handleCurrentChange(e){
                this.page=e; 
            },
            trashed(e) {
                this.search.isTrashed = e;
                this.getData();
            },
            formatDate(row, column, val, index) {
                // 时间转换
                if (val) {
                    return Time.formatDate(val, 'yyyy-MM-dd')
                }
                return val;
            },
            handleDetail(row){
                this.isShow=true;
               this.getDataDetail(row.id);
            },
            getDataDetail(id){
               if(id!=this.detail.id){
                    Request('AdminAd_Detail',{params:{id:id}}).then(e=>{
                        var data=e.data;
                        if(data.code==200){
                            this.detail=data.data;  
                        }
                    })
             }
            
            },
            // 向服务器发送请求获取 数据
            getData() {
                // var isTrashed = this.isTrashed;
                Request.get('AdminAd_List', {
                        params:this.search
                    })
                    .then((res) => {
                        if (res.data.code != 200) {
                            this.$notify({
                                title: '警告',
                                message: res.data.message,
                                type: 'warning'
                            });
                        } else {
                            // console.log(res);
                            this.tableData = res.data.data;
                        }

                    })
            },
            handleEdit(type, row) {
                this.action = type;
                this.formTitle = '添加';
                this.editVisible = true;
                switch (type) {
                    case 'add':
                        break;
                    case 'edit':
                        this.row= JSON.parse(JSON.stringify(row));
                        this.formTitle = '修改';
                        
                        this.getDataDetail(row.id);
                        // 级联选择器默认值处理开始<<<<<<<<<<<<<——————————————————————
                        var list=JSON.parse(JSON.stringify(this.list));
                        var list2=JSON.parse(JSON.stringify(this.list2));
                        this.imgList=[{src:row.image}];
                        var category=[];
                        category.push(row.category_id);
                        var area=[];
                        area.push(row.area_code);
                          // 向上找到无限极父菜单
                         function getCate(item,category,list){
                             var list=list || {};
                             var category=category || [];
                             // category.push(item.id)
                             if(item && item.pid && list[item.pid]){
                                category.push(item.pid);
                               return getCate(list[item.pid],category,list);
                             }
                             return category;
                         }

                         category=getCate(list[row.category_id],category,list);
                         area=getCate(list2[row.area_code],area,list2);

                        // 倒排序
                        function getSort(arr){
                            var newArr=[];
                            for(let i=(arr.length-1);i>=0;i--){
                                newArr.push(arr[i]);
                            }
                            return newArr
                        }
                        this.genealogy2=getSort(area);
                        this.genealogy=getSort(category);
                         //级联选择器默认值处理结束 —————————————————————>>>>>>>>>>>>>>>>>
                        Object.assign(this.form, row);
                        console.log(this.form)

                        break;
                    default:
                        break;
                }
            },
            onSubmit(formName) {
                var formUp = this.form;
                var url = 'AdminAd_Add';
                switch (this.action) {
                    case 'add':
                    
                        break;
                    case 'edit':
                    
                        url = 'AdminAd_Update';
                        var n=0;
                        var c=0;
                        var row=this.row;
                        for(let i in formUp){
                            c++;
                            if(row[i] && row[i]==formUp[i] && i!='id'){
                                n++;
                                delete formUp[i];
                            }
                        }
                        if((n+1)==c){
                            this.$notify({
                                title: '警告——操作失败',
                                message: '提示：1、没有需要修改的数据；2、修改图片无需提交' ,
                                type: 'warning'
                            });
                            return;
                        }
                        break;
                    default:
                        return;
                        break;
                }
                this.$refs[formName].validate((valid) => {
                    // console.log(valid);
                    if (valid) {
                        Request(url, {
                                data: formUp
                            })
                            .then(res => {
                                let rt = res.data;

                                if (rt.code == 200) {
                                    this.$notify({
                                        title: this.formTitle + '成功',
                                        type: 'success'
                                    });
                                    if (this.action == 'add') {
                                        this.tableData.push(rt.data);
                                    } else {
                                        this.tableData.filter((e) => {
                                            if (e.id == rt.data.id) {
                                                return Object.assign(e, rt.data);
                                            }
                                        });
                                    }

                                    this.editVisible = false;
                                } else {
                                    this.$notify({
                                        title: '警告',
                                        message: '操作失败' + res.data.message,
                                        type: 'warning'
                                    });
                                }

                            })
                    } else {
                        // console.log('error submit!!');
                        return false;
                    }
                });

            },
            handleDelete(type, row, isTrue) {
                var type = type || 'del';
                var caozuo = {
                    del: {
                        title: '删除',
                        api: 'AdminAd_Delete',
                    },
                    restore: {
                        title: '恢复',
                        api: 'AdminAd_Restore',
                    }
                }
                if (!caozuo[type]) {
                    this.$alert('非法操作？', '错误提示', {
                        confirmButtonText: '确定',
                        type: 'warning',
                    });
                    return;
                }
                var CZ = caozuo[type];
                var length = 1;
                this.$confirm('此次操作您将会' + CZ.title + length + '条数据，是否真的要' + CZ.title + '？', '确认' + CZ.title, {
                        distinguishCancelAndClose: true,
                        type: 'warning',
                        confirmButtonText: '立即' + CZ.title,
                        cancelButtonText: '放弃'
                    })
                    .then(() => {
                        Request(CZ.api, {
                                data: {
                                    id: row.id,
                                    isTrue: isTrue
                                }
                            })
                            .then(res => {
                                let rt = res.data;
                                if (rt.code == 200) {
                                    this.$notify({
                                        title: CZ.title + '成功',
                                        type: 'success',
                                        message: res.data.message,
                                    });
                                    this.tableData = this.tableData.filter((d) => {
                                        if (d.id != row.id) {
                                            return d;
                                        }
                                    })
                                    // this.$message.success('删除成功');
                                } else {
                                    this.$notify({
                                        title: '警告',
                                        message: '操作失败' + res.data.message,
                                        type: 'warning'
                                    });
                                }

                            })
                    })

                this.delVisible = false;
            }
        }
    }
</script>

<style lang="scss">
    .form-detail-info-span{
       padding-left: 10px; 
       padding-right: 10px;
       span{
           color:#13CE66;
       }
    }
    .demo-table-expand {
        font-size:1em;
        width: 100%;
        /* display: flex; */
    }

    .demo-table-expand label {
        font-weight: 600;
        text-align: right;
        width: 190px;
        color: #99a9bf;
        
    }

    .demo-table-expand .el-form-item {
        font-size:2em ;
        border-bottom:  1px solid #c3c3c3;
        /* margin-right: 0; */
        /* margin-bottom: 0; */
        width: 100%;
    }
</style>
