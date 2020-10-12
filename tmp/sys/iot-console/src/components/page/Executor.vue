<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> executor表格</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="delete" class="handle-del mr10" @click="addVisible=true">添加</el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                
                <el-table-column prop="title" label="title" width="120">
                </el-table-column>
                <el-table-column prop="description" label="简介">
                </el-table-column>
                <el-table-column prop="uuid" label="uuid" >
                </el-table-column>
                <el-table-column prop="update" label="update" sortable width="150">
                </el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.row)">编辑</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background @current-change="handleCurrentChange" layout="prev, pager, next" :total="pageCount">
                </el-pagination>
            </div>
        </div>

        <!-- 添加弹出框 -->
        <el-dialog title="添加" :visible.sync="addVisible" width="70%">
            <el-form ref="form" :model="form" label-width="50px">
                <el-form-item label="标题">
                    <el-input v-model="form.title"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="form.description"></el-input>
                </el-form-item>
                <el-form-item label="内容">
                    <el-input type="textarea"  v-model="form.content"  rows=5></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addVisible = false">取 消</el-button>
                <el-button type="primary" @click="addExecutor">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="70%">
            <el-form ref="reloadObj" :model="reloadObj" label-width="50px">
                <el-form-item label="标题">
                    <el-input v-model="reloadObj.title"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="reloadObj.description"></el-input>
                </el-form-item>
                <el-form-item label="内容">
                    <el-input type="textarea"  v-model="reloadObj.content"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import {getExecutors,addClass,reloadClass,getExecutorContent} from '@/api/executor'
    import axios from 'axios'
    import {timestampToTime} from '@/utils/toTimeStr'
    import Qs from 'qs'
    let serverUrl = 'https://gw.iotechn.com/m.api/'  //开发环境
    export default {
        name: 'basetable',
        data() {
            return {
                url: './static/vuetable.json',
                tableData: [],
                cur_page: 1,
                pageCount:0,
                multipleSelection: [],
                select_cate: '',
                select_word: '',
                del_list: [],
                is_search: false,
                editVisible: false,
                addVisible:false,
                form: {
                    content: '',
                    title: '',
                    description: ''
                },
                reloadObj:{
                    content:'',
                    title:'',
                    description:''
                },
                idx: -1
            }
        },
        created() {
            // this.getData();
            this.getExecutor()
        },

       
        methods: {
            setData(arr){
                for(let i = 0 ; i < arr.length ; i++){
                    arr[i].date = timestampToTime(arr[i].gmtCreate)
                    arr[i].update = timestampToTime(arr[i].gmtUpdate)
                }
                 this.tableData=arr
            },
            getExecutor(page=1){
                getExecutors(page)
                .then(res=>{
                    if(res.code==200){
                        this.setData(res.result.data)
                        this.pageCount = res.result.totalPageNo*10
                    }
                })
            },
            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                this.getExecutor(val);
            },
            handleEdit(obj) {
               this.reloadObj = obj
               getExecutorContent(obj.uuid)
               .then(res=>{
                   if(res.code===200&&res.result!=false){
                       this.reloadObj.content =res.result
                        this.editVisible = true
                   }
               })
              
            },
            // 保存编辑
            saveEdit() {
                var data =  Qs.stringify({
                        _gp:'executor',
                        _mt:'reloadClass',
                        uuid:this.reloadObj.uuid,
                        content:this.reloadObj.content
                });
                axios({
                        url: serverUrl,
                        method: 'post',
                        headers: {
                        'Content-Type':'application/x-www-form-urlencoded'
                    },
                    data
                })
                    .then(res=>{
                        if(res.data.code!=200){
                            this.$message.error(res.data.msg)
                        }else{
                            this.$message.success('ok啦')
                        }
                    })
            },
            addExecutor(){
                addClass(this.form.content,this.form.title,this.form.description)
                .then(res=>{
                    if(res.code==200){
                         this.$message.success(`成功啦`);
                    }
                    this.addVisible=false
                })
            }
        }
    }
</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;
    }
    .handle-select {
        width: 120px;
    }
    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .del-dialog-cnt{
        font-size: 16px;
        text-align: center
    }
    .table{
        width: 100%;
        font-size: 14px;
    }
    .red{
        color: #ff0000;
    }
    .content-input{
        height: 600px;
    }
</style>