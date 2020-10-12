<template>
    <div class="user-list-body">
        <div class="container" style="display: flex;flex-direction: column;width: 100%;">
            <div style="display: flex;flex-direction: column;padding-bottom: 10px;">
                <div style="display: flex;justify-content: space-between;padding-bottom:10px;" class="block">
                    <div></div>
                    <div style="align-self:flex-end;">
                        <el-button v-if="!isTrashed" type="primary" style="align-self: flex-end;" icon="el-icon-circle-plus-outline"
                            @click="addUser">添加用户</el-button>
                        <el-button v-if="!isTrashed" type="danger" icon="el-icon-delete" @click="delAll('del')">批量删除</el-button>
                        <el-button v-else type="warning" icon="el-icon-refresh" @click="delAll('restore')">批量恢复</el-button>
                        <el-button v-if="isTrashed" type="info" icon="el-icon-arrow-left" @click="trashed(0)">切回</el-button>
                        <el-button v-else type="info" icon="iconfont icon-lajigarbage7" @click="trashed(1)">回收站</el-button>
                    </div>
                </div>
                <el-card>
                    <div style="display: flex;justify-content: space-between;flex-direction: row;" class="block">
                        <div>
                            <el-date-picker v-model="search_time" type="daterange" align="right" unlink-panels
                                range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" value-format="timestamp"
                                :picker-options="pickerOptions">
                            </el-date-picker>
                        </div>
                        <div class="">
                            <el-select v-model="select_province" placeholder="按省查询" size="10">
                                <el-option key="不限" label="不限" :value="0"></el-option>
                                <el-option :key="key" v-for="(item,key) in province" :label="item" :value="key"></el-option>
                            </el-select>
                        </div>
                        <div>
                            <el-input v-model="select_word" placeholder="用户名关键词"></el-input>
                        </div>
                        <div style="flex:1;display: flex;justify-content: flex-end;">
                            <el-button type="primary" icon="el-icon-search" @click="search">获取数据</el-button>
                        </div>
                    </div>
                </el-card>
                <!-- </el-card> -->
            </div>
            <template>
                <!-- <div style="position: relative;"> -->
                <el-table :data="userData" type="index" :default-sort="{prop: 'create_time', order: 'descending'}"
                    border class="table" ref="multipleTable" style="width: 100%;" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55" align="center"></el-table-column>
                    <el-table-column prop="id" label="用户ID" width="160"></el-table-column>
                    <el-table-column prop="username" label="登陆名" width="160"></el-table-column>
                    <el-table-column width="160" label="昵称" size="small">
                        <template slot-scope="scope">
                            <div slot="reference" class="name-wrapper">
                                <span>{{scope.row.nickname}}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="city" label="地区" width="140"></el-table-column>
                    <el-table-column prop="state" label="状态" width="80">
                        <template slot-scope="scope">
                            <div slot="reference" class="name-wrapper">
                                <el-tag @click="handleEdit('edit', scope.row)" :type="scope.row.state==1?'success':'warning'">{{scope.row.state|filterState}}</el-tag>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column fixed="right" label="编辑" width="130">
                        <template slot-scope="scope">
                            <el-button v-if="isTrashed" type="text" plain size="small" icon="el-icon-refresh" class="red"
                                @click="handleDelete('restore', scope.row)">恢复</el-button>
                            <el-button v-else type="text" size="small" icon="el-icon-delete" class="red" @click="handleDelete('del', scope.row)">删除</el-button>
                            <el-button type="text" size="small" icon="el-icon-edit" @click="handleEdit('password', scope.row)">编辑</el-button>
                        </template>
                    </el-table-column>

                    <el-table-column prop="create_time" label="创建日期" data-key="create_time" :formatter="formatDate"
                        sortable width="160">
                    </el-table-column>
                    <el-table-column prop="update_time" label="更新日期" data-key='update_time' :formatter="formatDate"
                        sortable>
                    </el-table-column>
                </el-table>
            </template>

            <div class="pagination" style="">
                <el-pagination background @current-change="handleCurrentChange" :page-size="10" layout="prev, pager, next"
                    :total="dataTotal">
                </el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="400px">
            <el-form ref="formEdit" :model="formEdit" label-width="100px" size="medium">
                <el-form-item label="用户名">
                    <el-input v-model="formEdit.username" disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item v-if="action=='password'" label="登陆密码" prop="password">
                    <el-input type="password" v-model="formEdit.password" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item v-if="action=='edit'" label="账号状态">
                    <el-select v-model="formEdit.state" placeholder="请选择状态">
                        <el-option :key="item.state" v-for="item in state" :label="item.name" :value="item.state">
                            <span style="float: left">{{item.name}}</span>
                            <span style="float: right; color: #8492a6; font-size: 13px">{{item.state}}</span>
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 添加用户弹出框 -->
        <el-dialog title="添加用户" :visible.sync="addVisible" width="30%">
            <div class="form-box" style="align-self: center;">
                <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                    <el-form-item label="登陆名" prop="username">
                        <el-input v-model="form.username"></el-input>
                    </el-form-item>
                    <el-form-item label="登陆密码" prop="password">
                        <el-input type="password" v-model="form.password" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="confirm_password">
                        <el-input type="password" v-model="form.confirm_password" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit('form')">提交</el-button>
                        <el-button @click="addVisible=false">取消</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    import Request from "../../../request/index.js"
    import yc from "yc-js";
    var Validate =yc.Validate;
    var Time =yc.Time;
    var Arr =yc.Arr;
    var Province=yc.Area.province;
    var Md5=yc.Md5;
    export default {
        name: 'userUserList',
        data() {
            // 公用验证规则
            var checkType = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('请选择'));
                }
            };
            var validatePass = (rule, value, callback) => {
                var rule = [{
                    checkType: "password",
                    name: 'password'
                }]
                var data = {
                    password: value
                }
                var Val = Validate;
                var check = Val.check(data, rule);
                // console.log(check+Val.error)
                if (!check) {
                    callback(Val.error)
                    return Val.error;
                }
                this.$refs.form.validateField('confirm_password');
                callback()
            }
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.form.password) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };

            return {
                // loading:true,//加载中
                // 查询日期
                pickerOptions: {
                    shortcuts: [{
                        text: '最近一周',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近一个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近三个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit('pick', [start, end]);
                        }
                    }]
                },
                search_time: [(Date.now() - 86400000 * 15), Date.now()],
                isTrashed: 0, //回收站

                state: [{
                    name: '正常',
                    state: 1
                }, {
                    name: '关闭',
                    state: -1
                }, {
                    name: '待审核',
                    state: 0
                }],
                p: 0, //向服務器請求的页码
                n: 10, //服务器每次返回数据量
                dataP: 0, //本地文档显示数据的页码
                dataN: 10, //本地文档每页显示数据的数量
                // dataPN:1,//本地数据总页数
                dataTotal: 10, //符合条件的数据数量
                province: [],
                tableData: [],
                multipleSelection: [], //被选中的
                select_word: 0, //按关键词查询
                select_province: 0, //按权限查询
                editVisible: false, //编辑
                action: '',
                // delVisible: false,//删除
                addVisible: false, //添加
                // action:'edit',//当前表单请求类型编辑/添加/删除
                formEdit: { //编辑修改表单
                    id: '',
                    username: '',
                    password: '',
                    state: ''
                },

                form: { //添加表单
                    username: '',
                    password: '',
                    confirm_password: ''
                },

                rules: {
                    username: [{
                        validator: validatePass,
                        trigger: 'blur'
                    }],
                    password: [{
                        validator: validatePass,
                        trigger: 'blur'
                    }],
                    confirm_password: [{
                        validator: validatePass2,
                        trigger: 'blur'
                    }]
                }
            }
        },
        beforeMount() {

        },
        created() {

            this.getProvince();
            this.getData();
        },
        watch: {
            isTrashed(e){
                //页面归零
               this.p= 0;
               this.dataP = 0;
            },
            select_province(e) {
                this.dataP = 0;
                this.getData();
            },
            select_word(e) {
                this.dataP = 0;
            }
        },
        filters: {
            filterState(state) {
                var value = '待审核';
                switch (state) {
                    case 0:
                        value = '待审核';
                        break;
                    case 1:
                        value = '正常';
                        break;
                    default:
                        value = "屏蔽";
                        break;
                }
                return value;
            }
        },
        computed: {
            userData: {
                get() {
                    var tableData = this.tableData;
                    var total = (this.dataP) * this.dataN;
                    var tableData = tableData.filter((d) => {
                        d.username = d.username || d.nickname || "未命名";
                        d.id = d.id || '1';
                        d.city = d.city || '未知';
                        if ((this.select_rank && this.select_rank == d.rank_id || !this.select_rank) && (!
                                this.select_word || this.select_word && d.username.indexOf(this.select_word) >
                                -1)) {
                            return d;
                        }
                    });
                    this.setDataTotal(tableData.length);

                    return tableData.slice(total, total + this.dataN)

                },
                set(num) {
                    this.dataTotal = num
                }
            }
        },
        methods: {
            // 获取省地址列表
            getProvince() {
                this.province = Province;

            },
            setDataTotal(num) {
                this.dataTotal = num
            },

            formatDate(row, column, val, index) {
                // 时间转换
                return Time.formatDate(val)
            },
            // 分页导航
            handleCurrentChange(val) {
                this.dataP = val - 1;
            },
            // 向服务器发送请求获取 数据
            getData() {
                var search_time = this.search_time;
                if (!Array.isArray(search_time) || JSON.stringify(search_time) == '[]') {
                    search_time = {};
                } else {
                    search_time = {
                        start_time: parseInt(search_time[0] / 1000),
                        last_time: parseInt(search_time[1] / 1000),
                    }
                }
                var province = {
                    area: this.select_province
                };
                var username = {
                    username: this.select_word
                };
                var Rdate = Object.assign({
                    p: this.p,
                    trashed: this.isTrashed
                }, search_time, province, username);
                Request.get('AdminUser_List', {
                        params: Rdate
                    })
                    .then((res) => {
                        if (res.data.code != 200) {
                            this.$notify({
                                title: '警告',
                                message: res.data.message,
                                type: 'warning'
                            });
                        } else {

                            var data = res.data.data
                            this.dataPN = parseInt(data.length / this.dataN);

                            function sortId(a, b) {
                                return a.create_time - b.create_time
                            }
                            data.sort(sortId);
                            this.tableData = data;
                        }

                    })
            },
            trashed(e) {
                this.p = 0;
                this.dataP = 0;
                this.isTrashed = e;
                this.getData();
            },
            search() {
                this.getData();
                this.is_search = true;
            },

            filterTag(value, row) {
                return row.tag === value;
            },
            handleEdit(type, row) {
                var formEdit = this.formEdit;
                for (let p in formEdit) {
                    formEdit[p] = row[p]
                }
                this.row = row; //拿到选择的行数据
                this.formEdit = formEdit;
                this.editVisible = true;
                this.action = type;
            },
            delAll(type) {
                var type = type || 'del';
                var caozuo = {
                    del: {
                        title: '删除',
                        api: 'AdminUser_DeleteAll',
                    },
                    restore: {
                        title: '恢复',
                        api: 'AdminUser_RestoreAll',
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

                const length = this.multipleSelection.length;

                if (length < 1) {
                    this.$alert('请选择需要' + CZ.title + '的数据后进行' + CZ.title + '操作？', '错误提示', {
                        confirmButtonText: '确定',
                        type: 'warning'
                    });
                    return;
                }
                this.$confirm('此次操作您将会' + CZ.title + length + '条数据，是否真的要' + CZ.title + '？', '确认' + CZ.title, {
                        distinguishCancelAndClose: true,
                        type: 'warning',
                        confirmButtonText: '立即' + CZ.title,
                        cancelButtonText: '放弃'
                    })
                    .then(() => {

                        this.$message({
                            type: 'info',
                            message: CZ.title + '中'
                        });

                        let str = '';
                        var list = [];
                        for (let i = 0; i < length; i++) {
                            str += this.multipleSelection[i].id + ' ';
                            list.push(this.multipleSelection[i].id)
                        }
                        Request(CZ.api, {
                                data: {
                                    list: list
                                }
                            })
                            .then(res => {
                                let rt = res.data;
                                if (rt.code == 200) {
                                    this.$notify({
                                        title: CZ.title + '成功',
                                        message: res.data.message,
                                        type: 'success'
                                    });
                                    this.tableData = this.tableData.filter((d) => {
                                        var is = true;
                                        for (let i = 0; i < list.length; i++) {
                                            if (list[i] == d.id) {
                                                is = false;
                                            }
                                        }
                                        if (is) {
                                            return d;
                                        }
                                    })
                                } else {
                                    this.$notify({
                                        title: '警告',
                                        message: '操作失败' + res.data.message,
                                        type: 'warning'
                                    });
                                }

                            })

                    })
                    .catch(action => {
                        this.$message({
                            type: 'info',
                            message: action === 'cancel' ?
                                '放弃保存并离开页面' : '停留在当前页面'
                        })
                    });

            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            // 保存编辑
            saveEdit() {
                // var formEditUp=this.formEditUp;
                var formEditUp = {};
                var url = '';
                switch (this.action) {
                    case 'password':
                        formEditUp['password'] = Md5.Md5(Md5.Md5(this.formEdit['password']));
                        url = 'AdminUser_update';
                        break;
                    case 'edit':
                        formEditUp['state'] = this.formEdit['state'];
                        url = 'AdminUser_update';
                        break;
                    default:
                        return;
                        break;
                }

                Request(url, {
                        data: {
                            id: this.formEdit.id,
                            form: formEditUp
                        }
                    })
                    .then(res => {
                        let rt = res.data;
                        if (rt.code == 200) {
                            this.$notify({
                                title: '成功',
                                type: 'success'
                            });

                            var tableData = this.tableData;
                            var idx = 0;
                            var newdata = {};
                            //不需要请求是服务器 实时把新数据直接更新到html 
                            for (let i = 0; i < tableData.length; i++) {
                                if (tableData[i].id == this.formEdit.id) {
                                    for (let p in formEditUp) {
                                        tableData[i][p] = formEditUp[p];
                                    }
                                    newdata = tableData[i];
                                    idx = i;
                                }
                            }

                            this.$set(this.tableData, idx, newdata);
                        } else {
                            this.$notify({
                                title: '警告',
                                message: '操作失败' + res.data.message,
                                type: 'warning'
                            });
                        }

                    })
                this.editVisible = false;

            },
            handleDelete(type, row) {
                var type = type || 'del';
                var caozuo = {
                    del: {
                        title: '删除',
                        api: 'AdminUser_Delete',
                    },
                    restore: {
                        title: '恢复',
                        api: 'AdminUser_Restore',
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
                                    id: row.id
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
            },
            addUser() {
                // this.action='add';
                this.addVisible = true;
            },
            onSubmit(formName) {

                var formUp = {};

                formUp.username = this.form.username
                formUp['password'] = Md5.Md5(formUp['username'] + formUp['password'])
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        Request('AdminUser_Add', {
                                data: formUp
                            })
                            .then(res => {
                                let rt = res.data;
                                if (rt.code == 200) {
                                    this.$notify({
                                        title: '成功',
                                        type: 'success'
                                    });

                                    this.tableData.push(rt.data);
                                    this.addVisible = false;
                                    // this.$message.success('添加成功');
                                } else {
                                    this.$notify({
                                        title: '警告',
                                        message: '操作失败' + res.data.message,
                                        type: 'warning'
                                    });
                                }

                            })
                    } else {
                        return false;
                    }
                });

            }
        }
    }
</script>

<style scoped lang="scss">
    .user-list-body{
        
    .handle-box {
        /* margin-bottom: 20px; */
    }

    .handle-select {
        width: 120px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }

    .del-dialog-cnt {
        font-size: 16px;
        text-align: center
    }

    .table {
        width: 100%;
        font-size: 14px;
    }

    .red {
        color: #ff0000;
    }
    }
</style>
