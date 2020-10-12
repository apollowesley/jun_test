<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 设备表格，您的设备有{{count}}，
        您的在线设备有{{onlineCount}}！</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                
                <el-button type='primary' @click='addDevice'>添加设备</el-button>
            </div>
            <el-table :data="data" border class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                
                <el-table-column prop="title" label="title" width="100">
                </el-table-column>
                <el-table-column prop="name" label="name" width="100">
                </el-table-column>
                
                <el-table-column prop="description" label="简介">
                </el-table-column>
                <el-table-column prop="update" label="更新日期" sortable width="160">
                </el-table-column>
                <el-table-column prop="secretKey" label="secretKey" width="200">
                </el-table-column>
                
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                        <el-button type="text"  @click="handleCopy(scope.$index, scope.row)"> <i class="el-icon-lx-copy"></i> 克隆</el-button>
                        <el-button type="text"  @click="handleAdd(scope.$index, scope.row)"> <i class="el-icon-lx-add"></i> 添加预设</el-button>
                        <el-button type="text"  @click="lookCommand(scope.$index, scope.row)"> <i class="el-icon-lx-attention"></i> 查看预设</el-button>
                        <el-button type="text"  @click="handleAddDev(scope.$index, scope.row)"> <i class="el-icon-lx-roundadd"></i>添加参数</el-button>
                        <el-button type="text"  @click="lookDev(scope.$index, scope.row)"> <i class="el-icon-lx-attention"></i>查看参数</el-button>
                        <el-button type="text"  @click="handleTwo(scope.$index, scope.row)"> <i class="el-icon-lx-roundadd"></i>二维码</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background @current-change="handleCurrentChange" layout="prev, pager, next" :total=pageCount>
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="50px">
                <el-form-item label="name">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="title">
                    <el-input v-model="form.title"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="form.password"></el-input>
                </el-form-item>
                <el-form-item label="简介">
                    <el-input v-model="form.description"></el-input>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 添加弹出框 -->
        <el-dialog title="添加" :visible.sync="addVisible" width="30%">
            <el-form ref="addForm" :model="addForm" label-width="50px">
                <el-form-item label="name">
                    <el-input v-model="addForm.name"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="addForm.password"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveAdd">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 添加预设 -->
        <el-dialog title="添加预设命令" :visible.sync="commandShow" width="30%">
            <el-form ref="addCommand" :model="addCommand" label-width="70px">
                <el-form-item label="标题">
                    <el-input v-model="addCommand.title"></el-input>
                </el-form-item>
                <el-form-item label="命令">
                    <el-input v-model="addCommand.command"></el-input>
                </el-form-item>
                <el-form-item label="简介">
                    <el-input v-model="addCommand.description"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="commandShow = false">取 消</el-button>
                <el-button type="primary" @click="saveCommand">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 查看预设 -->
         <el-dialog title="查看预设" :visible.sync="commandListShow" width="70%">
            <el-table  
            :data="commandList"
            style="width: 100%">
            <el-table-column
                prop="title"
                label="标题"
                width="180">
            </el-table-column>
            <el-table-column
                prop="command"
                label="command"
                width="180">
            </el-table-column>
            <el-table-column
                prop="description"
                label="简介">
            </el-table-column>
            <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-delete" class="red" @click="deleteCommand(scope.row.deviceId,scope.row.id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background @current-change="yuSheChange" layout="prev, pager, next" :total=yuShePageCount>
                </el-pagination>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="commandListShow = false">确 定</el-button>
            </span>
         </el-dialog>
         <!-- 添加设备参数 -->
         <el-dialog title="添加设备参数 " :visible.sync="addDevShow" width="70%">
            <el-form ref="addDevForm" :model="addDevForm" label-width="90px" :rules="rules">
                <el-form-item label="标题" prop="title">
                    <el-input v-model="addDevForm.title"></el-input>
                </el-form-item>
                <el-form-item label="name" prop="name">
                    <el-input v-model="addDevForm.name" placeholder='只允许输入英文'></el-input>
                </el-form-item>
                <el-form-item label="过期时间" prop="expire">
                    <el-input v-model.number="addDevForm.expire" placeholder='只允许输入数字（单位秒）'></el-input>
                </el-form-item>
                <el-form-item label="简介">
                    <el-input v-model="addDevForm.description" placeholder='选填'></el-input>
                </el-form-item>
            </el-form>
           <span slot="footer" class="dialog-footer">
                <el-button @click="addDevShow = false">取 消</el-button>
                <el-button type="primary" @click="saveAddForm">确 定</el-button>
            </span>
         </el-dialog>
         <!-- 查看设备参数 -->
         <el-dialog title="查看预设" :visible.sync="devListShow" width="70%">
            <el-table  
            :data="devList"
            style="width: 100%">
            <el-table-column
                prop="id"
                label="id"
                width="180">
            </el-table-column>
            <el-table-column
                prop="name"
                label="name"
                width="180">
            </el-table-column>
            <el-table-column
                prop="description"
                label="简介">
            </el-table-column>
            <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-delete" class="red" @click="deleteDev(scope.row.deviceId,scope.row.id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination background @current-change="canShuChange" layout="prev, pager, next" :total=canShuPageCount>
                </el-pagination>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="commandListShow = false">确 定</el-button>
            </span>
         </el-dialog>
         <el-dialog  title="二维码 " :visible.sync="TwoConfig.isShow" width="40%" class='qr-box'>
             <vue-qr  :logoSrc="TwoConfig.logo" :text="TwoConfig.value" :size="200" :margin="0" v-show='TwoConfig.isShow' class="qr-c"></vue-qr>
             <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="TwoConfig.isShow=false">确 定</el-button>
            </span>
         </el-dialog>
         
    </div>
</template>

<script>
    import {getDeviceBasicInfo,getDevicePage,createDevice,updateDevice,deleteDevice,cloneDevice,addDeviceCommand,getDeviceCommandPage,deleteDeviceCommand,addDeviceParam,deleteDeviceParam,getDeviceParamPage} from '@/api/device'
    import VueQr from 'vue-qr'
    import {timestampToTime} from '@/utils/toTimeStr'
    export default {
        name: 'basetable',
        components:{
            VueQr
        },
        data() {
            //只可以输入英文
            var enReg = /^[A-Za-z]+$/
               var validateEn= (rule, value, callback) => {
                    if (!value) {
                         callback(new Error('不可以为空哦!!'))
                    }
                    setTimeout(() => {
                        if (!enReg.test(value)) {
                            callback(new Error('格式有误'))
                        } else {
                            callback()
                        }
                    }, 1000)
                }
            return {
                data:[],
                tableData: [],
                cur_page: 1,
                multipleSelection: [],
                select_cate: '',
                del_list: [],
                editVisible: false,
                delVisible: false,
                addVisible:false,
                commandShow:false,
                commandListShow:false,
                addDevShow:false,
                devListShow:false,
                devList:[
                    {
                        id:'',
                        name:'',
                        description:''
                    }
                ],
                form: {
                    name: '',
                    date: '',
                    address: ''
                },
                rules: {
                    // 校验英文，主要通过validator来指定验证器名称
                    name: [{validator: validateEn ,trigger: 'blur',required: true}],
                    expire:[{ required: true, message: '必填项'},
                        { type: 'number', message: '格式有误'}],
                    title:[{required:true,message:'必填项'}]
                },
                addCommand:{
                    title:'',
                    command:'',
                    description:''
                },
                commandList:[
                    {
                        title:'',
                        command:'',
                        description:''
                    }
                ],
                addDevForm:{
                    title:"",
                    name:"",
                    expire:'',
                    description:""
                },
                addForm:{
                    name:'',
                    password:''
                },
                //二维码
                TwoConfig: {
                    value: 'key获取失败了呢。',//显示的值、跳转的地址
                    logo:require('../../assets/logo.jpg'),//中间logo的地址
                    isShow:false
                },
                idx: -1,
                pageCount:0,
                yuShePageCount:0,
                yuSheCurrentCount:0,
                canShuPageCount:0,
                canshuCurrentCount:0,
                //这个是设备数字
                count:0,
                onlineCount:0
            }
        },
        created() {
            this.getDevicePage(1)
            getDeviceBasicInfo()
            .then(res=>{
                if(res.code==200&&res.result!=false){
                    this.count = res.result.count
                    this.onlineCount = res.result.onlineCount
                }
            })
        
        },
        methods: {
            //新建设备
            addDevice(){
                this.addVisible = true;
            },
            //获取设备
            getDevicePage(count=1){
                getDevicePage(count)
                .then(res=>{
                    if(res.code==200&&res.result!=false){
                        this.pageCount= res.result.totalPageNo*10
                        this.setData(res.result.data)
                    }
                })
            },
            //获取预设参数
            getDeviceCommandPage(page){
                getDeviceCommandPage(page,this.form)
                .then(res=>{
                    if(res.code==200&&res.result!=false){
                        this.commandList = res.result.data
                        this.yuShePageCount = res.result.totalPageNo*10
                    }
                })
            },
            //获取设备参数
            getDeviceParamPage(page){
                getDeviceParamPage(page,this.form.id)
                .then(res=>{
                    if(res.code==200&&res.result!=false){
                        this.devList = res.result.data
                        this.canShuPageCount = res.result.totalPageNo*10
                    }
                })
            },
            saveAdd(){
                createDevice(this.addForm.name,this.addForm.password)
                .then(res=>{
                    console.log(res)
                    if(res.code==200){
                         this.$message.success(`保存成功`);
                         this.getDevicePage(this.cur_page)
                         this.$router.go(0)
                    }
                })
                this.addVisible = false;
            },
            saveCommand(){
                addDeviceCommand(this.form,this.addCommand)
                .then(res=>{
                    if(res.code==200){
                         this.$message.success(`添加预设成功`);
                    }
                })
                this.commandShow = false;
            },
            deleteCommand(deviceId,id){
                deleteDeviceCommand(deviceId,id)
                .then(res=>{
                    if(res.code==200&&res.result==true){
                        this.$message.success(`删除成功`);
                        for(var i = 0; i < this.commandList.length ; i++){
                            if(this.commandList[i].id==id){
                                this.commandList.splice(i,1)
                            }
                        }   
                    }
                })
            },
            //删除参数
            deleteDev(deviceId,id){
                deleteDeviceParam(deviceId,id)
                .then(res=>{
                    if(res.code==200&&res.result==true){
                        this.$message.success(`删除成功`);
                        for(var i = 0; i < this.devList.length ; i++){
                            if(this.devList[i].id==id){
                                this.devList.splice(i,1)
                            }
                        }   
                    }
                })
            },
            //设置dATA
            setData(arr){
                for(let i = 0 ; i < arr.length ; i++){
                    arr[i].date = timestampToTime(arr[i].gmtCreate)
                    arr[i].update = timestampToTime(arr[i].gmtUpdate)
                }
                 this.data=arr
            },
            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                this.getDevicePage(val)
            },
            //查看预设的
            yuSheChange(val){
                this.yuSheCurrentCount = val;
                this.getDeviceCommandPage(val)
            },
            //查看参数
            canShuChange(val){
                this.canshuCurrentCount = val;
                this.getDeviceParamPage(val)
            },
            formatter(row, column) {
                return row.address;
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            handleEdit(index, row) {
                this.handleFrom(index,row)
                this.editVisible = true;
            },
            handleDelete(index, row) {
                this.handleFrom(index,row)
                
                this.delVisible = true
            },
            handleCopy(index,row){
                this.handleFrom(index,row)
                cloneDevice(this.form)
                .then(res=>{
                    if(res.code==200){
                        this.$message.success(`克隆成功`);
                    }
                })
            },
            handleAdd(index,row){
                this.handleFrom(index,row)
                this.commandShow = true
            },
            lookCommand(index,row){
                this.handleFrom(index,row)
                this.getDeviceCommandPage(1)
                this.commandListShow = true
            },
            lookDev(index,row){
                this.handleFrom(index,row)
                this.getDeviceParamPage(1)
                this.devListShow = true
            },
            handleAddDev(index,row){
                this.handleFrom(index,row)
                 this.addDevShow = true
            },
            handleTwo(index,row){
                this.TwoConfig.value = this.data[index].secretKey
                this.TwoConfig.isShow=true
            },
            //把当前一条的数据赋予给form.
            handleFrom(index,row){
                this.idx = index;
                const item = this.data[index];
                this.form = {
                    name: item.name,
                    id:item.id,
                    secretKey: item.secretKey,
                    password: item.password,
                    title:item.title,
                    description:item.description
                }
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            // 保存编辑
            saveEdit() {
                updateDevice(this.form)
                .then(res=>{
                    if(res.code==200&&res.result!=false){
                        this.$message.success(`修改第 ${this.idx+1} 行成功`);
                    }
                })
                this.editVisible = false;
                
            },
            // 确定删除
            deleteRow(){
                deleteDevice(this.form)
                .then(res=>{
                    if(res.code==200&&res.result!=false){
                        this.$message.success(`删除成功`);
                         this.getDevicePage(this.cur_page)
                    }
                })
                this.delVisible = false;
            },
            saveAddForm(){
                addDeviceParam(this.form.id,this.addDevForm)
                .then(res=>{
                    if(res.code===200){
                        this.$message.success('添加成功')
                    }
                })
                this.addDevShow = false;
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
    .qr-c{
        text-align: center
    }
</style>
