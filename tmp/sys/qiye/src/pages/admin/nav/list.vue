<template>
    <div class="">
          <div class="block">
    <el-tree ref="elTree" 
      :data="categoryList"
      node-key="id"
      default-expand-all
      :expand-on-click-node="false">
      <div class="custom-tree-node" slot-scope="{ node, data }" >
        <span class="tree-left" >{{ data.name }}</span>
        <span class="tree-right" >
            <span v-if="data.id"  class="tree-span">
                <span class="tree-span" >URL: <span class="tree-tip">{{data.url}}</span></span>
                <span class="tree-span" >ID: <span class="tree-tip">{{data.id}}</span></span>
                <span class="tree-span">状态: <span class="tree-tip">{{getState(data.state)}}</span></span>
                <span  class="tree-span" >排序权重: <span class="tree-tip">{{data.ranking}}</span></span>
                
                <el-button
                    type="text"
                    size="mini"
                    @click="() => handleEdit('edit', data)">
                    编辑
                </el-button>
                <el-button 
                  type="text"
                  size="mini"
                  @click="() =>handleDelete('del', data,node)">
                  删除
                </el-button>
            </span>

          <el-button
            type="text"
            size="mini"
            @click="() => handleEdit('add', data)">
            添加
          </el-button>
        </span>
      </div>

    </el-tree>
  </div>
        <!-- 公共form表单弹出框 -->
        <el-dialog :title="action=='add'?'添加':'修改'" :visible.sync="editVisible" width="70%">
            <div class="form-box" style="align-self: center;">
            	<el-form ref="form" :model="form" label-width="100px" style="padding-top: 20px;">
            		<el-form-item label="标题" >
            			<el-input v-model="form.name" maxlength="20" minlength="2"></el-input>
            		</el-form-item>
            		<el-form-item  label="添加图片" >
            			<update-file :controlledAdd="imageAdd"  :imgList="imgList" :max="max"   :maxwh="maxwh" :quality="quality" :change="fileChange"></update-file>	
            		</el-form-item>
                    <el-form-item label="URL链接地址" >
                    	<el-input v-model="form.url" maxlength="40" minlength="2"></el-input>
                        <div>URL:地址说明（例如您网址:http://xxx/#/new/list/2,只需填写#后边部分:/list/news?id=2;如果是站外网站需填写http开头的完整路径</div>
                    </el-form-item>
                    <el-form-item label="排序" >
                        <el-input v-model="form.ranking"></el-input>
                    </el-form-item>
                    <el-form-item label="栏目板块" style="text-align: left;">
                    	<el-cascader change-on-select style="width:100%"  :options="categoryList"  v-model="genealogy"  :props="props"  >
                    	</el-cascader>
                    </el-form-item>
            		<el-form-item label="状态" style="text-align: left;">
            			<el-select v-model="form.state"  placeholder="请选择" style=";background: #;">
            				<el-option :key="item.id" v-for="item in newsState" :label="item.name" :value="item.id"></el-option>
            			</el-select>
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
    var      Validate=yc.Validate;
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

                genealogy:[0],//
                treeMax:3,//树节点最多级数
                maxwh:200,//图片尺寸
                quality:1,//图片质量
                max:1,//图片数量
                imgList:[],//传递给图片组件显示图片

                list:{},

                props: {
                  label: 'name',
                  value:'id',
                  children: 'children'
                },
                
                newsState:newsState,

                action: 'add',

                // isTrashed: 0,
                editVisible: false, //编辑
                row:{},

                form: {
                    id:0,
                    name: '',//标题
                    image: '',//内容
                    genealogy:[],
                    ranking:0,
                    url:'',
                    pid: '',//分类
                    state: ""//状态
                }
            }
        },
        created() {

            this.getData();
        },
        watch:{
            genealogy(genealogy){
               var n=this.treeMax;

               var newData=[];
               if(genealogy && typeof genealogy=='object'){
                   if(genealogy.length>n){
                       this.$message({
                           message: '警告!目前系统最高仅支持'+this.treeMax+'级菜单',
                           type: 'warning'
                       })
                    }
                       
                       for(let i=0;i<genealogy.length;i++){
                           if(this.form.id && this.form.id==genealogy[i]){
                               this.$message({
                                   message: '警告!不可以是子菜单或同级呦',
                                   type: 'warning'
                               })
                               break;
                               // return;
                           }else{
                                if(i<n){
                                  newData.push(genealogy[i])  
                                }   
                           }
                       }
               }else{
                   newData=[0];
               }
                   this.$set(this.form,'genealogy',newData);
            },
            editVisible(e){
                // this.list=list;
            },
            // 验证表单不正确的输入会纠错
            form:{
                deep: true,
                handler(newValue,oldValue){

                     if(newValue && newValue.ranking && newValue.ranking!=oldValue.ranking){
                         if(!Validate.isNumber(newValue['ranking']) || newValue['ranking']>99){
                            newValue.ranking= 99;
                         }
                     }
                } 
            },
        },
        computed: {
            categoryList(){
                var newObj=JSON.parse(JSON.stringify(this.list));
                var categoryList= Arr.getTree(newObj);
                categoryList=Arr.getTreeSortBy(categoryList,'ranking','children',false);
                return categoryList;
            }
        },
        
        methods: {
          getState(state){
              var newsState=this.newsState;
              for (var i in newsState) {
                  if(newsState[i].id==state){
                      return newsState[i].name
                  }
              }
          },
            // 树添加
            append(newChild) {
           this.list.push(newChild);
          },
          // 树删除
          remove(data) {
              if(data.id==0 || data.children &&  typeof data.children =='object' && data.children.length>0){
                  var msg='请先删除子内容';
                  if(data.id==0){
                      msg="此选项无需删除";
                  }
                  this.$message({
                      message: '警告!'+msg,
                      type: 'warning'
                    });
                  return false;
              }
              var list={};
              for(let id in this.list){
                  if(id!=data.id){
                      list[id]=this.list[id];
                  }
              }
             this.list=list;
             return true;
          },
      
            getFileUrl(url,type){
                    return this.$config.getFileUrl(url,type);   
            },
            imageAdd(e){
                if(e.src){
                    this.imgList=[{src:e.src}];
                 this.form.image=e.src;
                }
            },

            fileChange(e){

                if(e && typeof e=='object' && e[0] && e[0].src){
                    this.form.image=e[0].src;  
                    this.imgList=e;
                }
                // this.image=e; 
            },
            formatDate(row, column, val, index) {
                // 时间转换
                if (val) {
                    return Time.formatDate(val, 'yyyy-MM-dd')
                }
                return val;
            },
            // 向服务器发送请求获取 数据
            getData() {
                // var isTrashed = this.isTrashed;
                Request('AdminNav_List').then(e=>{
                    // console.log(e)
                    if(e.data.code==200){
                         var data=e.data.data;
                         var oldObj={'0':{id:0,name:"顶级菜单",pid:0,ranking:0}};
                         // 转成obj 用id作为key

                         for (let i=0; i<data.length;i++) {
                             oldObj[data[i].id+'']=data[i];
                         }
                          this.list =oldObj;
                        
                    }
                })  
            },
            handleEdit(type, row) {

                var row = JSON.parse(JSON.stringify(row));
                this.row=row;
                this.formTitle = '修改';
                var formUp={};
                for (let p in this.form) {
                        formUp[p] = row[p]
                }
                // 格式化族谱 如果不是数组转成 数组格式
                if(formUp.genealogy){
                    if(typeof formUp.genealogy=='object' ){
                        // this.genealogy=formUp.genealogy;
                    }else{
                        try{
                        	formUp.genealogy=JSON.parse(formUp.genealogy);
                        }catch(e){
                        	formUp.genealogy=[];
                        }
                    }
                }
                if (typeof formUp.genealogy!='object' ) {
                    formUp.genealogy=[]
                } 
                this.genealogy=formUp.genealogy;
                this.action = type;
                this.formTitle = '添加';
                this.editVisible = true;
//                 console.log(row)
//                 console.log(formUp)
                switch (type) {
                    case 'add':
                        formUp.pid=formUp.id;
                        formUp.ranking=10;
                        formUp.image='';
                        this.imgList=[];
                        // console.log(formUp.genealogy)
                        // return;
                        formUp.genealogy.push(formUp.id);
                        formUp.id=0;
                        // console.log(formUp)
                        this.form=formUp;
                        break;
                    case 'edit':
                        this.form=formUp;
                        this.formTitle = '修改';
                        this.imgList=[{src:formUp.image}];
                         //级联选择器默认值处理结束 —————————————————————>>>>>>>>>>>>>>>>>
                        // Object.assign(this.form, formUp);
                        break;
                    default:
                        break;
                }
                
                 //级联选择器默认值处理结束 —————————————————————>>>>>>>>>>>>>>>>>
                // Object.assign(this.form, row);
            },
            onSubmit(formName) {
                var formUp =  JSON.parse(JSON.stringify(this.form));
                // console.log(formUp)
                var url = 'AdminNav_Add';
                if(formUp.genealogy && typeof formUp.genealogy=='object' ){
                    formUp.pid=formUp.genealogy[formUp.genealogy.length-1];
                }
                switch (this.action) {
                    case 'add':
                        delete formUp.id;
                        break;
                    case 'edit':
                    
                        url = 'AdminNav_Update';
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
                        // console.log(formUp);
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
                                // console.log(res)
                                let rt = res.data;
                                if (rt.code == 200) {
                                    this.$notify({
                                        title: this.formTitle + '成功',
                                        type: 'success'
                                    });
                                    // console.log(rt.data)
                                    var list =this.list;
                                        this.$set(this.list,rt.data.id,rt.data)
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
            handleDelete(type, row,node) {

                var type = type || 'del';
                var caozuo = {
                    del: {
                        title: '删除',
                        api: 'AdminNav_Delete',
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
                                }
                            })
                            .then(res => {
                                console.log(res)
                                let rt = res.data;
                                if (rt.code == 200) {
                                    this.$notify({
                                        title: CZ.title + '成功',
                                        type: 'success',
                                        message: res.data.message,
                                    });
                                    this.remove(row);

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
    
    .custom-tree-node{
        .tree-span{
            margin: 10px;
        }
        .tree-left{
            font-weight: 600;
        }
        .tree-right{
            color: #b2b2b2;
        }
        .tree-tip{
            color: #13CE66;
        }
        color:#666;
        flex: 1;
        display: flex;
        align-items: center;
        font-size: 15px;
        border-bottom: 1px  dashed #f2f2f2;
        margin-bottom: 3px;
        justify-content: space-between;
        padding-right: 8px;
  }

  
  

</style>
