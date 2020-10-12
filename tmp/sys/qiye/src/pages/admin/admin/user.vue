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
              添加管理人员
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
            prop="user_id"
            label="用户ID"
            width="160"
          />
          <el-table-column
            prop="rank_name"
            label="分类"
            min-width="100"
          >
            <template slot-scope="scope">
              <div @click="handleEdit('rank', scope.row)">
                <span v-html="scope.row.rank_name?scope.row.rank_name:'未定义'" />
                <el-button
                  type="text"
                  class="el-icon-edit-outline"
                />
              </div>
            </template>
          </el-table-column>

          <el-table-column
            width="140"
            label="名称"
            size="small"
          >
            <template slot-scope="scope">
              <div
                slot="reference"
                class="name-wrapper"
                @click="handleEdit('name', scope.row)"
              >
                <span>{{ scope.row.user_name }}</span>
                <el-button
                  type="text"
                  class="el-icon-edit-outline"
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
                  class="red"
                  @click="handleDelete('del', scope.row,false)"
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
                  @click="handleDelete('restore', scope.row)"
                >
                  恢复
                </el-button>
                <el-button
                  type="text"
                  size="small"
                  icon="el-icon-delete"
                  class="red"
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
            :formatter="formatDate"
            sortable
            width="160"
          />
          <el-table-column
            prop="update_time"
            label="更新日期"
            data-key="update_time"
            :formatter="formatDate"
            sortable
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
      width="400px"
    >
      <el-form
        ref="formEdit"
        :model="formEdit"
        label-width="100px"
        size="medium"
      >
        <el-form-item label="用户名">
          <el-input
            v-model="formEdit.user_name"
            disabled="disabled"
          />
        </el-form-item>
        <el-form-item
          v-if="action=='rank'"
          label="分类"
        >
          <el-select
            v-model="formEdit.rank_id"
            placeholder="请选择"
          >
            <el-option
              :key="item.id"
              v-for="item in rank"
              :label="item.rank_name"
              :value="item.id"
            >
              <span style="float: left">{{ item.rank_name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.id }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          v-if="action=='name'"
          label="名称"
        >
          <div style="display: flex;flex-direction: row;flex-wrap:nowrap ">
            <el-input
              style=""
              v-model="formEdit.user_name"
              placeholder="请输入名称"
            />
          </div>
        </el-form-item>
      </el-form>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="editVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="saveEdit"
        >确 定</el-button>
      </span>
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
        label-width="180px"
        label-position=""
      >
        <el-form-item label-width="0">
          <div style="display: flex;flex-direction: row;flex-wrap:nowrap ">
            <el-input
              style=""
              v-model="form.user_name"
              placeholder="请输入用户登陆名-—然后点击查找"
            />
            <el-button
              type="primary"
              icon="el-icon-search"
              @click="search"
            >
              查找
            </el-button>
          </div>
        </el-form-item>

        <div>
          <div>为您查寻到</div>
          <div
            v-for="(item,index) in userInfo"
            :key="index"
          >
            <el-form-item
              :label="'ID:'+item.id"
              v-if="formPageShow(index,formPage)"
            >
              <div style="display: flex;flex-direction: row;flex-wrap:nowrap ">
                <el-input
                  disabled="disabled"
                  style=""
                  v-model="item.username"
                  placeholder="请输入用户登陆名-—然后点击查找"
                />
                <el-button
                  type="primary"
                  icon="el-icon-circle-plus-outline"
                  @click="onSubmit(item)"
                  plain
                >
                  添加
                </el-button>
              </div>
            </el-form-item>
          </div>
          <el-pagination
            background
            layout="prev, pager, next"
            :page-size="10"
            @current-change="fromCurrentChange"
            :total="userInfo.length"
          />
        </div>
        <el-button
          style="width: 100%;"
          @click="addVisible=false"
        >
          取消
        </el-button>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
    import Request from "../../../request/index.js"
   import yc from 'yc-js';
    var Time =yc.Time;
    var Validate =yc.Validate;
    var Arr =yc.Arr;
    // var Province=yc.Area.province;
    export default {
        name: 'AdminList',
        data() {
            return {

                isTrashed: 0, //回收站

                p: 0, //向服務器請求的页码
                n: 10, //服务器每次返回数据量
                dataP: 0, //本地文档显示数据的页码
                dataN: 10, //本地文档每页显示数据的数量
                dataPN: 1, //本地数据总页数
                dataTotal: 10, //符合条件的数据数量
                tableData: [],

                editVisible: false, //编辑
                addVisible: false, //添加
                action: '', //当前表单请求类型编辑/添加/删除
                formEdit: { //编辑修改表单
                    user_id: '',
                    user_name: '',
                    rank_id: ''
                },
                userAll: [], //添加用户前搜索到的用户集
                form: { //添加表单
                    user_name: '',
                    user_id: '',
                    rank_id: ''
                },
                formPage: 1, //from搜索用户列表页码
                formPageNum:10,//from搜索用户每页其实数
                userInfoShow: false
            }
        },
        created() {
            this.getRank();
            this.getData();
        },
        watch: {
            formPage(e) {
                this.formPageNum = (e - 1) * 10;
            }
        },
        filters: {

        },
        computed: {
            userInfo: {
                get() {
                    var tableData = this.tableData;
                    var userAll = this.userAll;
                    return userAll.filter((e) => {
                        var hav = false;
                        for (var i = 0; i < tableData.length; i++) {
                            if (tableData[i].user_id == e.id) {
                                hav = true;
                                break;
                            }
                        }
                        if (!hav) {
                            return e;
                        }
                    });
                    // return All;
                },
                set(e) {
                    this.userInfoShow = e;
                }
            },
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
                    username: this.form.user_name,
                    start_time: parseInt(time) - 315360000
                };
                Request.get('AdminUser_List', {
                        params: username
                    })
                    .then((res) => {
                         // console.log(res);
                        // var data=res.data.data
                        if (res.data.code == 200) {
                            // var tableData=this.tableData;
                            this.userAll = res.data.data;
                        } else {
                            this.$notify({
                                title: '警告',
                                message: '操作失败' + res.data.message,
                                type: 'warning'
                            });
                        }
                    })
            },
            // 获取权限列表
            filterRank(rank_id) {
                var rank = this.rank;
                for (let i = 0; i < rank.length; i++) {
                    if (rank[i].id == rank_id) {
                        return rank[i].rank_name;
                    }
                }

                return '未知';
            },
            // 获取服务器权限列表
            getRank() {
                Request.get('AdminAdminRank_List')
                    .then((res) => {
                        // console.log(res.data);
                        var rank = res.data.data;
                        this.rank = rank;
                    })
                // this.province=Province;
            },
            setDataTotal(num) {
                this.dataTotal = num
            },

            formatDate(row, column, val, index) {
                // 时间转换
                if (val) {
                    return Time.formatDate(val, "yy-MM-dd hh:mm")
                }
                return val;
            },
            // 分页导航
            handleCurrentChange(val) {
                // console.log(val);
                this.dataP = val - 1;
            },
            trashed(e) {
                this.dataP=0;
                this.p=0;
                this.isTrashed = e;
                this.getData();
            },
            // 向服务器发送请求获取 数据
            getData() {
                var Rdate = Object.assign({
                    p: this.p,
                    trashed: this.isTrashed
                });
                Request.get('AdminAdminUser_List', {
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
                            // console.log(res.data)
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
            // 保存编辑
            saveEdit() {
                var formEditUp = {};
                var url = '';
                switch (this.action) {
                    case 'rank':
                        formEditUp['rank_id'] = this.formEdit['rank_id'];
                        url = 'AdminAdminUser_update';
                        break;
                    case 'name':
                        formEditUp['user_name'] = this.formEdit['user_name'];
                        url = 'AdminAdminUser_update';
                        break;
                    default:
                        return;
                        break;
                }
                Request(url, {
                        data: {
                            id: this.formEdit.user_id,
                            form: formEditUp
                        }
                    })
                    .then(res => {
                         // console.log(res)
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
                                if (tableData[i].user_id == this.formEdit.user_id) {
                                    newdata = tableData[i];
                                    idx = i;
                                    if (this.action == 'rank') {
                                        newdata.rank_id = formEditUp['rank_id'];
                                        newdata.rank_name = this.filterRank(formEditUp['rank_id']);
                                    } else if (this.action == 'name') {
                                        newdata.user_name = formEditUp['user_name'];
                                    }
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
            handleDelete(type, row,isTrue) {
                var type = type || 'del';
                var caozuo = {
                    del: {
                        title: '删除',
                        api: 'AdminAdminUser_Delete',
                    },
                    restore: {
                        title: '恢复',
                        api: 'AdminAdminUser_Restore',
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
                                    user_id: row.user_id,
                                    isTrue:isTrue
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
                                        if (d.user_id != row.user_id) {
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
                this.addVisible = true;
            },
            onSubmit(row) {
                // console.log(row);
                var formUp = {};
                formUp.user_id = row.id;
                Request('AdminAdminUser_Add', {
                        data: formUp
                    })
                    .then(res => {
                        console.log(res)
                        let rt = res.data;
                        if (rt.code == 200) {
                            this.$notify({
                                title: '成功',
                                type: 'success'
                            });
                            this.tableData.push(rt.data);
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
