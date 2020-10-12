<template>
  <div>
    <biz-page
      :form="form"
      :table="table"
      :pager="pager"
      :edit="edit"
      :add="add"
    ></biz-page>
    <div>
      <el-row>
        <el-col>
          <el-dialog
            title="授权管理"
            :visible.sync="dialog.authority.isShow"
            width="720px"
          >
            <el-row>
              <el-col>
                <el-transfer
                  style="text-align: left;display: inline-block"
                  v-model="dialog.authority.added"
                  filterable
                  :titles="['可授权', '已授权']"
                  :button-texts="['取消', '授权']"
                  filter-placeholder="输入角色名搜索"
                  :right-default-checked="dialog.authority.added"
                  :props="{
                    key: 'id',
                    label: 'roleDesc'
                  }"
                  :data="dialog.authority.all"
                >
                </el-transfer>
              </el-col>
            </el-row>
            <span
              slot="footer"
              class="dialog-footer"
            >
              <el-button @click="authorityCancel">取 消</el-button>
              <el-button
                type="primary"
                @click="authoritySave"
              >保存</el-button>
            </span>
          </el-dialog>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import { list, deleteUser, add, update, roles, reRoles } from "@/api/portalUser";
import BizPage from '@/components/BizPage.vue'
export default {
  name: "user",
  components: {
    BizPage: BizPage
  },
  data() {
    return {
      dialog: {
        authority: {
          isShow: false,
          checked: [],
          all: [],
          added: [],
          userId: ''
        }
      },
      edit: {
        isShow: false,
        title: '编辑用户',
        form: {
          data: {},
          formItems: [
            { type: 'input', label: '昵称', prop: 'nickName', placeholder: '请输入用户昵称' },
            { type: 'input', label: '手机号', prop: 'userPhone', placeholder: '请输入用户手机号' },
            { type: 'input', label: '邮箱', prop: 'userEmail', placeholder: '请输入用户邮箱地址' },
            { type: 'input', label: '真实姓名', prop: 'realName', placeholder: '请输入用户真实姓名' },
            {
              type: 'select',
              label: '用户状态',
              prop: 'userStatus',
              clearable: true,
              options: [{ label: '正常', value: 1 }, { label: '停用', value: -1 }],
              props: { label: 'label', value: 'value' },
              change: row => row.value,
              placeholder: '请选择用户状态'
            }
          ]
        },
        modal: 'need',
        confirm: () => {
          this.update()
        },
        cancel: () => {
          this.edit.isShow = false
          this.list()
        }
      },
      add: {
        isShow: false,
        title: '添加用户',
        modal: 'need',
        form: {
          data: {},
          formItems: [
            { type: 'input', label: '用户名', prop: 'username', placeholder: '请输入用户名' },
            { type: 'input', label: '密码', prop: 'password', placeholder: '请输入用户密码' },
            { type: 'input', label: '昵称', prop: 'nickName', placeholder: '请输入用户昵称' },
            { type: 'input', label: '手机号', prop: 'userPhone', placeholder: '请输入用户手机号' },
            { type: 'input', label: '邮箱', prop: 'userEmail', placeholder: '请输入用户邮箱地址' },
            { type: 'input', label: '真实姓名', prop: 'realName', placeholder: '请输入用户真实姓名' },
            {
              type: 'select',
              label: '用户状态',
              prop: 'userStatus',
              clearable: true,
              options: [{ label: '正常', value: '1' }, { label: '停用', value: '-1' }],
              props: { label: 'label', value: 'value' },
              change: row => row.value,
              placeholder: '请选择用户状态'
            }
          ]
        },
        confirm: () => {
          this.addUser()
        },
        cancel: () => {
          this.add.isShow = false
          this.list()
        }
      },
      pager: {
        total: 0,
        pageSize: 10,
        currentPage: 1,
        currentChange: (index) => {
          this.pager.currentPage = index
          this.list()
        },
        sizeChange: (size) => {
          this.pager.pageSize = size
          this.list()
        }
      },
      form: {
        search: {
        },
        formItems: [
          { type: 'input', prop: 'username', placeholder: '请输入用户名' },
          { type: 'input', prop: 'nickName', width: '100px', placeholder: '请输入用户昵称' },
          {
            type: 'select',
            prop: 'userStatus',
            clearable: true,
            options: [{ label: '正常', value: '1' }, { label: '停用', value: '-1' }],
            props: { label: 'label', value: 'value' },
            change: row => row.value,
            placeholder: '请选择用户状态'
          },
          {
            type: 'dateTime',
            prop: 'dateArr'
          }
        ],
        actions: [
          {
            label: '添加',
            icon: 'el-icon-plus',
            type: 'success',
            handle: () => {
              this.add.isShow = true
            }
          },
          {
            label: '查询',
            icon: 'el-icon-search',
            type: 'primary',
            handle: () => this.list()
          }
        ]
      },
      table: {
        loading: false,
        list: [],
        columns: [{
          prop: 'id',
          label: '编号',
          width: '80px'
        },
        {
          prop: 'username',
          label: '用户名',
        },
        {
          prop: 'avatarUrl',
          label: '头像',
          align: 'center',
          render: (h, params) => {
            return h('el-avatar', {
              props: { size: 'small', src: params.row.avatarUrl ? process.env.VUE_APP_BASE_API + '/hors/portal/user/avatar?id=' + params.row.avatarUrl : "" } // 组件的props
            })
          }
        },
        {
          prop: 'realName',
          label: '真实姓名',
        },
        {
          prop: 'nickName',
          label: '昵称',
        },
        {
          prop: 'userPhone',
          label: '手机号',
        }, {
          prop: 'userEmail',
          label: '邮箱',
        },
        {
          prop: 'createTime',
          label: '创建时间',
          formatter: (row) => {
            return this.$formatDateTime(row.createTime)
          }
        },
        {
          prop: 'updateTime',
          label: '更新时间',
          formatter: (row) => {
            return this.$formatDateTime(row.createTime)
          }
        }, {
          prop: 'userStatus',
          label: '状态',
          align: 'center',
          render: (h, params) => {
            return h('el-tag', {
              props: { type: params.row.userStatus == '1' ? 'success' : 'danger' } // 组件的props
            }, params.row.userStatus == '1' ? '正常' : '停用')
          }
        }],
        operates: {
          width: 300,
          fixed: 'right',
          list: [
            {
              label: '授权',
              type: 'info',
              show: true,
              icon: 'el-icon-user',
              method: (index, row) => {
                this.handleAuthority(index, row)
              }
            },
            {
              label: '编辑',
              type: 'primary',
              show: true,
              icon: 'el-icon-edit',
              method: (index, row) => {
                this.handleEdit(index, row)
              }
            },
            {
              label: '删除',
              type: 'danger',
              show: true,
              icon: 'el-icon-delete',
              method: (index, row) => {
                this.handleDel(index, row)
              }
            }
          ]
        }
      }
    };
  },
  methods: {
    list() {
      this.table.loading = true
      const params = {
        currentPage: this.pager.currentPage,
        pageSize: this.pager.pageSize,
        username: this.form.search.username,
        userStatus: this.form.search.userStatus,
        nickName: this.form.search.nickName
      }
      list(params).then(res => {
        this.table.list = res.list
        this.pager.total = res.total
        this.table.loading = false
      })
    },
    handleDel(index, row) {
      const param = { id: row.id }
      this.$confirm('确认删除该用户?', '删除提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteUser(param).then(res => {
          console.log(res)
          this.$message.success("删除成功")
          this.list()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleEdit(index, row) {
      this.edit.form.data = row
      this.edit.isShow = true
    },
    handleAuthority(index, row) {
      this.dialog.authority.userId = row.id
      roles({ id: row.id }).then(res => {
        this.dialog.authority.isShow = true
        this.dialog.authority.all = res.all
        if (res.added) {
          this.dialog.authority.added = res.added.map(item => { return item.id })
        }
      })
    },
    authorityCancel() {
      this.dialog = {
        authority: {
          isShow: false,
          checked: [],
          all: [],
          added: []
        }
      }
    },
    authoritySave() {
      const params = {
        id: this.dialog.authority.userId,
        roleIds: this.dialog.authority.added
      }
      reRoles(params).then(res => {
        console.log(res)
        this.$message.success('授权成功')
        this.dialog = {
          authority: {
            isShow: false,
            checked: [],
            all: [],
            added: []
          }
        }
      })
    },
    addUser() {
      add(this.add.form.data).then(res => {
        console.log(res)
        this.add.form.data = {}
        this.$message.success('添加成功')
        this.list()
        this.add.isShow = false
      })
    },
    update() {
      console.log(this.edit.form.data)
      update(this.edit.form.data).then(res => {
        console.log(res)
        this.edit.form.data = {}
        this.$message.success('编辑成功')
        this.list()
        this.edit.isShow = false
      })
    }
  },
  created() {
    this.list()
  }
};
</script>
