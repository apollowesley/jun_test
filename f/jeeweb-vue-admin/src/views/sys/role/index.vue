<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-input v-model="listQuery.name" style="width: 200px;" class="filter-item" placeholder="请输入角色名称" @keyup.enter.native="handleFilter"/>
      <el-input v-model="listQuery.code" style="width: 200px;" class="filter-item" placeholder="请输入角色编码" @keyup.enter.native="handleFilter"/>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{ $t('table.search') }}</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">{{ $t('table.add') }}</el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :key="tableKey"
      :data="list"
      element-loading-text="给我一点时间"
      border
      fit
      highlight-current-row
      style="width: 100%">
      <el-table-column width="200" align="center" label="角色名称">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column width="150" label="角色编码">
        <template slot-scope="scope">
          <span>{{ scope.row.code }}</span>
        </template>
      </el-table-column>
      <el-table-column width="200" align="center" label="是否可用">
        <template slot-scope="scope">
          <span>{{ scope.row.usable | dictLabel('sf') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" style="width: 100px;" @click="toSetMenu(scope.row)">设置权限</el-button>
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">{{ $t('table.edit') }}</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">{{ $t('table.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination
        :current-page.sync="listQuery.page"
        :page-sizes="[10,20,30, 50]"
        :page-size="listQuery.limit"
        :total="total"
        background
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"/>
    </div>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="120px" style="width: 400px; margin-left:50px;">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="temp.name"/>
        </el-form-item>
        <el-form-item label="角色编码" prop="code">
          <el-input v-model="temp.code"/>
        </el-form-item>
        <el-form-item label="是否系统数据" prop="isSys">
          <el-radio
            v-for="item in dictList('sf')"
            :key="item.id"
            v-model="temp.isSys"
            :label="item.value">
            {{ item.label }}
          </el-radio>
        </el-form-item>
        <el-form-item label="是否可用" prop="usable">
          <el-radio
            v-for="item in dictList('sf')"
            :key="item.id"
            v-model="temp.usable"
            :label="item.value">
            {{ item.label }}
          </el-radio>
        </el-form-item>
        <el-form-item label="备注">
          <el-input
            :autosize="{ minRows: 2, maxRows: 4}"
            v-model="temp.remarks"
            type="textarea"
            placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">{{ $t('table.confirm') }}</el-button>
        <el-button v-else type="primary" @click="updateData">{{ $t('table.confirm') }}</el-button>
      </div>
    </el-dialog>
    <!--设置权限-->
    <el-dialog :visible.sync="dialogFormMenuVisible" :fullscreen="true" title="设置权限">
      <el-form ref="dataForm" :model="menuTemp" label-position="left" label-width="120px" style="width: 400px; margin-left:50px;">
        <el-form-item label="操作权限">
          <el-tree
            ref="menuTree"
            :data="menuData"
            :props="menuTreeProps"
            :default-checked-keys="selectMenuIds"
            default-expand-all = "true"
            show-checkbox
            node-key="id"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormMenuVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button type="primary" @click="handleChangeMenus">设置权限</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { fetchList, addRole, deleteRole, updateRole, fetchRoleMenu, setMenu } from '@/api/sys/role'
import waves from '@/directive/waves' // 水波纹指令

export default {
  name: 'SysRoleList',
  directives: {
    waves
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      showReviewer: false,
      temp: {
        id: undefined,
        isSys: '1',
        usable: '1',
        name: '',
        code: '',
        remark: ''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑角色',
        create: '添加角色'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        code: [{ required: true, message: '角色编码必填', trigger: 'change' }],
        name: [{ required: true, message: '角色名称必填', trigger: 'change' }],
        isSys: [{ required: true, message: '是否系统必选', trigger: 'blur' }],
        usable: [{ required: true, message: '是否可用必选', trigger: 'blur' }]
      },
      dialogFormMenuVisible: false,
      menuData: null,
      selectCurentRoleId: '',
      selectMenuIds: [],
      menuTemp: {
        menuIds: ''
      },
      menuTreeProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchList(this.listQuery).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleSizeChange(val) {
      this.listQuery.limit = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.page = val
      this.getList()
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作成功',
        type: 'success'
      })
      row.status = status
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        isSys: '1',
        usable: '1',
        name: '',
        code: '',
        remark: ''
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          addRole(this.temp).then(() => {
            this.list.unshift(this.temp)
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '添加成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp)
          updateRole(tempData).then(() => {
            for (const v of this.list) {
              if (v.id === this.temp.id) {
                const index = this.list.indexOf(v)
                this.list.splice(index, 1, this.temp)
                break
              }
            }
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '更新成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleDelete(row) {
      deleteRole(row.id).then(() => {
        this.$notify({
          title: '成功',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
        const index = this.list.indexOf(row)
        this.list.splice(index, 1)
      })
    },
    getRoleMenus(roleId, menuModule) {
      fetchRoleMenu(roleId, menuModule).then(response => {
        this.menuData = response.data.menus
        this.selectMenuIds = response.data.selectMenuIds
      })
    },
    toSetMenu(row) {
      this.selectCurentRoleId = row.id
      this.dialogFormMenuVisible = true
      this.getRoleMenus(this.selectCurentRoleId, this.menuTemp.module)
    },
    changeMenu() {
      this.getRoleMenus(this.selectCurentRoleId, this.menuTemp.module)
    },
    handleChangeMenus() {
      const checkedKeys = this.$refs.menuTree.getCheckedKeys()
      const menuIds = checkedKeys.join(',')
      const postData = { roleId: this.selectCurentRoleId, menuIds: menuIds, module: this.menuTemp.module }
      setMenu(postData).then(response => {
        const data = response.data
        if (data.code === 0) {
          this.dialogFormMenuVisible = false
          this.$notify({
            title: '成功',
            message: '设置成功',
            type: 'success',
            duration: 2000
          })
        } else {
          this.$notify({
            title: '失败',
            message: data.msg,
            type: 'error',
            duration: 2000
          })
        }
      })
    }
  }
}
</script>
