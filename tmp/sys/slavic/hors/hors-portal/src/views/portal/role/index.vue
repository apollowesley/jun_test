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
      <el-dialog
        title="编辑角色资源"
        :visible.sync="dialog.resources.isShow"
        width="40%"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        :show-close="false"
      >
        <div>
          <el-tree
            ref="resourcesTree"
            :data="dialog.resources.list"
            show-checkbox
            node-key="id"
            check-strictly
            :default-checked-keys="dialog.resources.checked"
            :props="{
              children: 'children',
              label: 'resourceDesc'
            }"
          >
          </el-tree>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="resourcesCancel">取 消</el-button>
          <el-button
            type="primary"
            @click="resourcesSave"
          >确 定</el-button>
        </span>
      </el-dialog>
      <el-dialog
        title="角色关联用户"
        :visible.sync="dialog.users.isShow"
        width="30%"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        :show-close="false"
      >
        <div>
          <el-table
            :data="dialog.users.list"
            size="small"
            empty-text="当前角色未关联用户"
            border
            stripe
          >
            <el-table-column
              prop="id"
              label="编号"
              width="80"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="username"
              label="用户名"
              show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
              prop="userStatus"
              label="用户状态"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <span v-if="scope.row.userStatus == '1'">正常</span>
                <span v-if="scope.row.userStatus == '-1'">停用</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="usersCancel">取 消</el-button>
          <el-button
            type="primary"
            @click="usersCancel"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import BizPage from '@/components/BizPage.vue'
import { list, deleteRole, add, update, reResources, viewUsers, resourceList } from '@/api/portalRole'
export default {
  name: 'role',
  components: {
    BizPage: BizPage
  },
  data() {
    return {
      dialog: {
        resources: {
          roleId: '',
          isShow: false,
          list: [],
          checked: []
        },
        users: {
          isShow: false,
          list: []
        }
      },
      edit: {
        isShow: false,
        title: '编辑角色',
        form: {
          data: {},
          formItems: [
            { type: 'input', label: '用户名', prop: 'roleName', placeholder: '请输入角色名' },
            { type: 'input', label: '角色描述', prop: 'roleDesc', placeholder: '请输入角描述' }
          ]
        },
        confirm: () => {
          this.updateRole()
        },
        cancel: () => {
          this.edit.isShow = false
          this.list()
        }
      },
      add: {
        isShow: false,
        title: '添加角色',
        form: {
          data: {},
          formItems: [
            { type: 'input', label: '用户名', prop: 'roleName', placeholder: '请输入角色名' },
            { type: 'input', label: '角色描述', prop: 'roleDesc', placeholder: '请输入角色名' }
          ]
        },
        confirm: () => {
          this.addRole()
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
          { type: 'input', prop: 'roleName', placeholder: '请输入角色名' },
          { type: 'input', prop: 'roleDesc', placeholder: '请输入角色描述' }
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
          label: '角色编号',
        },
        {
          prop: 'roleName',
          label: '角色名称',
        },
        {
          prop: 'roleDesc',
          label: '角色描述',
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
        }],
        operates: {
          width: 400,
          fixed: 'right',
          list: [
            {
              label: '编辑资源',
              type: 'success',
              show: true,
              icon: 'el-icon-tickets',
              method: (index, row) => {
                this.editResource(index, row)
              }
            },
            {
              label: '查看用户',
              type: 'primary',
              show: true,
              icon: 'el-icon-view',
              method: (index, row) => {
                this.viewUsers(index, row)
              }
            },
            {
              label: '编辑',
              type: 'warning',
              show: true,
              icon: 'el-icon-edit',
              method: (index, row) => {
                this.handleEdit(index, row)
              }
            },
            {
              label: '删除',
              type: 'danger',
              icon: 'el-icon-delete',
              show: true,
              method: (index, row) => {
                this.handleDel(index, row)
              }
            }
          ]
        }
      }
    }
  },
  methods: {
    list() {
      this.table.loading = true
      const params = {
        currentPage: this.pager.currentPage,
        pageSize: this.pager.pageSize,
        roleName: this.form.search.roleName,
        roleDesc: this.form.search.roleDesc,
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
      this.$confirm('确认删除该角色?', '删除提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteRole(param).then(res => {
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
    addRole() {
      add(this.add.form.data).then(res => {
        console.log(res)
        this.add.form.data = {}
        this.list()
        this.add.isShow = false
        this.$message.success("添加成功")
      })
    },
    updateRole() {
      update(this.edit.form.data).then(res => {
        console.log(res)
        this.edit.form.data = {}
        this.list()
        this.edit.isShow = false
      })
    },
    editResource(index, row) {
      resourceList({ roleId: row.id }).then(res => {
        this.dialog.resources.roleId = row.id
        this.dialog.resources.list = res.list
        this.dialog.resources.checked = res.checked
        this.dialog.resources.isShow = true
        this.$message.success("编辑成功")
      })
    },
    resourcesCancel() {
      this.dialog.resources = {
        isShow: false,
        roleId: ''
      }
    },
    resourcesSave() {
      const params = {
        id: this.dialog.resources.roleId,
        resourceIds: this.$refs.resourcesTree.getCheckedNodes(false, true).map(item => { return item.id })
      }
      reResources(params).then(res => {
        console.log(res)
        this.$message.success("编辑成功")
        this.dialog.resources = {
          isShow: false
        }
      })
    },
    viewUsers(index, row) {
      viewUsers({ roleId: row.id }).then(res => {
        this.dialog.users.list = res
        this.dialog.users.isShow = true
      })
    },
    usersCancel() {
      this.dialog.users = {
        isShow: false,
        list: []
      }
    }
  },
  created() {
    this.list()
  }
}
</script>