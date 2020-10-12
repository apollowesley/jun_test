<template>
  <div class="">
    <div
      class="container"
      style="display: flex;flex-direction: column;width: 100%;"
    >
      <div style="display: flex;flex-direction: column;padding-bottom: 10px;">
        <div
          style="display: flex;justify-content: space-between;padding-bottom:10px;"
          class="block"
        >
          <div />
          <div style="align-self:flex-end;">
            <el-button
              v-if="!isTrashed"
              type="primary"
              style="align-self: flex-end;"
              icon="el-icon-circle-plus-outline"
              @click="addUser"
            >
              添加分类
            </el-button>
            <el-button
              v-if="isTrashed"
              type="info"
              icon="el-icon-arrow-left"
              @click="trashed(0)"
            >
              切回
            </el-button>
            <el-button
              v-else
              type="info"
              icon="iconfont icon-lajigarbage7"
              @click="trashed(1)"
            >
              回收站
            </el-button>
          </div>
        </div>
      </div>
      <template>
        <el-table
          :data="userData"
          type="index"
          border
          class="table"
          ref="multipleTable"
          style="width: 100%;"
        >
          <el-table-column
            prop="id"
            label="权限ID"
            width="80"
          />
          <el-table-column
            label="分类名"
            min-width="80"
            width="100"
          >
            <template slot-scope="scope">
              <div
                slot="reference"
                class="name-wrapper"
              >
                <el-tag @click="handleEdit('rank', scope.row)">
                  {{ scope.row.rank_name }}
                </el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="功能管理"
            min-width="200"
          >
            <template slot-scope="scope">
              <div
                slot="reference"
                class="name-wrapper"
                style="max-height: 150px;overflow-y:auto"
              >
                <span
                  v-for="(item,idx ) in scope.row.admin_rank_func"
                  :key="idx"
                >{{ idx+1 }}.{{ item.func_name }}
                  <el-button
                    type="text"
                    @click="handleFuncDel('del', scope.row,idx)"
                    class="el-icon-close"
                  />,
                </span>
                <el-button
                  type="text"
                  icon="el-icon-circle-plus"
                  style="font-size: 1.2em;color: #E6A23C;"
                  circle
                  @click="handleEdit('func', scope.row)"
                />
              </div>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="编辑"
            width="150"
          >
            <template slot-scope="scope">
              <div v-if="!isTrashed">
                <el-button
                  type="text"
                  size="small"
                  icon="el-icon-edit"
                  @click="handleEdit('rank', scope.row)"
                >
                  编辑
                </el-button>
                <el-button
                  type="text"
                  size="small"
                  icon="el-icon-delete"
                  style="color:red"
                  @click="handleDelete('del', scope.row)"
                >
                  删除
                </el-button>
              </div>
              <div v-else>
                <el-button
                  type="text"
                  plain
                  size="small"
                  icon="el-icon-refresh"
                  style="color:red"
                  @click="handleDelete('restore', scope.row)"
                >
                  恢复
                </el-button>
                <el-button
                  type="text"
                  size="small"
                  icon="el-icon-delete"
                  style="color:red"
                  @click="handleDelete('del', scope.row,true)"
                >
                  彻底删除
                </el-button>
              </div>
            </template>
          </el-table-column>

          <el-table-column
            prop="create_time"
            label="创建日期"
            data-key="create_time"
            width="100"
            :formatter="formatDate"
          />
          <el-table-column
            prop="update_time"
            label="更新日期"
            data-key="update_time"
            width="100"
            :formatter="formatDate"
          />
        </el-table>
      </template>

      <div
        class="pagination"
        style=""
      >
        <el-pagination
          background
          @current-change="handleCurrentChange"
          :page-size="10"
          layout="prev, pager, next"
          :total="dataTotal"
        />
      </div>
    </div>

    <!-- 编辑弹出框 -->
    <el-dialog
      title="编辑"
      :visible.sync="editVisible"
      width="500px"
    >
      <el-form
        ref="formEdit"
        :model="formEdit"
        label-width="100px"
        size="medium"
      >
        <el-form-item label="职务id">
          <el-input
            v-model="formEdit.id"
            disabled="disabled"
          />
        </el-form-item>
        <el-form-item
          v-if="action=='rank'"
          label="职务名称"
        >
          <div style="display: flex;flex-direction: row;flex-wrap:nowrap ">
            <el-input
              style=""
              v-model="formEdit.rank_name"
              placeholder="请输入职务名称"
            />
          </div>
        </el-form-item>
        <div v-if="action=='func'">
          <el-button
            v-for="(item,index) in funcInfo"
            :key="index"
            @click="handleFuncAdd(item)"
          >
            {{ item.func_name }}
            <span class="el-icon-plus" />
          </el-button>
        </div>
      </el-form>
      <div
        slot="footer"
        class="dialog-footer"
        style="width: 100%;"
      >
        <el-button
          style=""
          @click="editVisible = false"
        >
          取 消
        </el-button>
        <el-button
          v-if="action!='func'"
          style=""
          type="primary"
          @click="saveEdit(action)"
        >
          提交
        </el-button>
      </div>
    </el-dialog>

    <!-- 添加用户弹出框 -->
    <el-dialog
      title="添加管理人员"
      :visible.sync="addVisible"
      width="500px"
    >
      <el-form
        ref="form"
        :model="form"
        label-width="80px"
        label-position=""
      >
        <el-form-item label="职务名称">
          <div style="display: flex;flex-direction: row;flex-wrap:nowrap ">
            <el-input
              style=""
              v-model="form.rank_name"
              placeholder="请输入职务名称"
            />
          </div>
        </el-form-item>
      </el-form>
      <div
        slot="footer"
        class="dialog-footer"
        style="width: 100%;"
      >
        <el-button @click="addVisible=false">
          取消
        </el-button>
        <el-button
          type="primary"
          @click="onSubmit('rank')"
        >
          提交
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    import Request from "../../../request/index.js"
    import yc from 'yc-js';
    var Time = yc.Time;
    var Arr = yc.Arr;
    var Province = yc.province;
    var Validate = yc.Validate;
    export default {
        name: 'AdminList',
        data() {
            return {

                isTrashed: 0, //回收站

                p: 0, //向服務器請求的页码
                n: 10, //服务器每次返回数据量
                dataP: 0, //本地文档显示数据的页码
                dataN: 10, //本地文档每页显示数据的数量
                // dataPN:1,//本地数据总页数
                dataTotal: 10, //符合条件的数据数量
                checkedFunc: [], //功能多选中
                tableData: [],
                select_word: 0, //按关键词查询
                editVisible: false, //编辑
                action: '',
                funcAll: [],
                funcInfo: [],
                addVisible: false, //添加
                formEdit: { //编辑修改表单
                    id: '',
                    rank_name: '',
                },
                form: { //添加表单
                    rank_name: ''
                },
                formPage: 1, //from搜索用户列表页码
            }
        },
        beforeMount() {

        },
        created() {
            this.getRankFunc();
            this.getData();
        },
        watch: {

        },
        filters: {

        },
        computed: {
            userData: {
                get() {
                    var tableData = this.tableData;
                    var total = (this.dataP) * this.dataN;
                    this.setDataTotal(tableData.length);
                    return tableData.slice(total, total + this.dataN)

                },
                set(num) {
                    this.dataTotal = num
                }
            }
        },
        methods: {
            formPageShow(index, page) {
                var p = (page - 1) * 10;
                if (index >= p && index <= (p + 10)) {
                    return true;
                }
                return false;
            },
            fromCurrentChange(e) {
                this.formPage = e;
                // console.log(e);
            },
            search() {
                this.userInfo = {};
                var time = Date.now() / 1000;
                var username = {
                    username: this.form.username,
                    start_time: parseInt(time) - 315360000
                };
                Request.get('AdminList', {
                        params: username
                    })
                    .then((res) => {
                        var data = res.data.data
                        if (res.data.code == 200) {
                            this.userInfo = data;
                        } else {
                            this.$notify({
                                title: '警告',
                                message: '操作失败' + res.data.message,
                                type: 'warning'
                            });
                        }
                        // console.log(res.data);


                    })
            },
            // 获取服务器权限列表
            getRankFunc() {
                Request.get('AdminAdminFunc_List',{params:{access:'admin'}})
                    .then((res) => {
                        // console.log(res)
                        if (res.data.code != 200) {
                            this.$notify({
                                title: '警告',
                                message: res.data.message,
                                type: 'warning'
                            });
                        } else {
                            this.funcAll = res.data.data.map(o => {
                                return {
                                    func_id: o.id,
                                    func_name: o.func_name
                                }
                            });
                        }
                    })

            },
            setDataTotal(num) {
                this.dataTotal = num
            },

            formatDate(row, column, val, index) {
                // 时间转换
                return Time.formatDate(val, 'yy-MM-dd')
            },
            // 分页导航
            handleCurrentChange(val) {
                this.dataP = val - 1;
            },
            trashed(e) {
                this.isTrashed = e;
                this.getData();
            },
            // 向服务器发送请求获取 数据
            getData() {
                var Rdate = Object.assign({
                    p: this.p,
                    trashed: this.isTrashed
                });
                Request.get('AdminAdminRank_List', {
                        params: Rdate
                    })
                    .then((res) => {
                        // console.log(res)
                        if (res.data.code != 200) {
                            this.$notify({
                                title: '警告',
                                message: res.data.message,
                                type: 'warning'
                            });
                        } else {
                            var data = res.data.data

                            function sortId(a, b) {
                                return a.create_time - b.create_time
                            }
                            data.sort(sortId);
                            this.tableData = data;
                        }

                    })
            },
            handleEdit(type, row) {
                // console.log(this.funcAll)
                var formEdit = this.formEdit;
                for (let p in formEdit) {
                    formEdit[p] = row[p]
                }
                if (type == 'func') {
                    row.admin_rank_func = row.admin_rank_func || [];
                    var items = row.admin_rank_func;
                    var funcInfo = [...this.funcAll];
                    // console.log(this.funcAll);
                    // console.log(items);
                    for (let i2 = 0; i2 < items.length; i2++) {
                        for (let i = 0; i < funcInfo.length; i++) {
                            if (funcInfo[i].func_id == items[i2].func_id) {
                                funcInfo.splice(i, 1);
                                break;
                            }
                        }
                    }

                    this.funcInfo = funcInfo;
                }
                this.row = row; //拿到选择的行数据
                this.formEdit = formEdit;
                this.editVisible = true;
                this.action = type;
            },
            // 保存编辑
            saveEdit() {
                var formEditUp = {};
                var url = '';

                switch (this.action) {
                    case 'rank':
                        formEditUp['id'] = this.formEdit['id'];
                        formEditUp['rank_name'] = this.formEdit['rank_name'];
                        url = 'AdminAdminRank_Update';
                        break;
                    case 'func':

                        formEditUp['rank_id'] = this.formEdit['id'];
                        var checkedFunc = this.checkedFunc;
                        var nameList = [];
                        var funcList = [];
                        for (let i in checkedFunc) {
                            nameList.push(checkedFunc[i].func_name);
                            funcList.push(checkedFunc[i].id);
                        }
                        formEditUp['func'] = funcList;
                        url = 'AdminAdminRankFunc_Update';
                        break;
                    default:
                        return;
                        break;
                }
                Request(url, {
                        data: formEditUp
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
                                    idx = i;
                                    tableData[i].rank_name = this.formEdit['rank_name'];
                                    newdata = tableData[i];
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
            handleFuncDel(type, row, idx) {
                var item = row.admin_rank_func[idx];
                Request('AdminAdminRankFunc_Delete', {
                        data: {
                            rank_id: row.id,
                            func_id: item.func_id
                        }
                    })
                    .then(res => {
                        let rt = res.data;
                        if (rt.code == 200) {
                            this.$notify({
                                title: '删除成功',
                                type: 'success',
                                message: res.data.message,
                            });
                            row.admin_rank_func.splice(idx, 1);
                        } else {
                            this.$notify({
                                title: '警告',
                                message: '操作失败' + res.data.message,
                                type: 'warning'
                            });
                        }
                    })
            },
            handleFuncAdd(item) {
                var row = this.row;
                // var item=row.admin_rank_func;
                Request('AdminAdminRankFunc_Add', {
                        data: {
                            rank_id: row.id,
                            func: item.func_id
                        }
                    })
                    .then(res => {
                        let rt = res.data;
                        if (rt.code == 200) {
                            this.$notify({
                                title: '添加成功',
                                type: 'success',
                                message: res.data.message,
                            });
                            for (let i = 0; i < this.funcInfo.length; i++) {
                                if (this.funcInfo[i].func_id == item.func_id) {
                                    this.funcInfo.splice(i, 1);
                                }
                            }

                            row.admin_rank_func.push(item);
                        } else {
                            this.$notify({
                                title: '警告',
                                message: '操作失败' + res.data.message,
                                type: 'warning'
                            });
                        }
                    })
            },
            handleDelete(type, row, isTrue) {
                var type = type || 'del';
                var caozuo = {
                    del: {
                        title: '删除',
                        api: 'AdminAdminRank_Delete',
                    },
                    restore: {
                        title: '恢复',
                        api: 'AdminAdminRank_Restore',
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
                                console.log(res)
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
            },
            addUser() {
                // this.action='add';
                this.addVisible = true;
            },
            onSubmit(type) {
                // console.log(this.form);
                var formUp = {};
                formUp.rank_name = this.form.rank_name;
                Request('AdminAdminRank_Add', {
                        data: formUp
                    })
                    .then(res => {
                        let rt = res.data;
                        if (rt.code == 200) {
                            this.$notify({
                                title: '成功',
                                type: 'success'
                            });
                            // console.log(rt.data);

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


            }
        }
    }
</script>

<style scoped>
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
</style>
