<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-input v-model="listQuery.keyword" style="width: 200px;" class="filter-item" placeholder="请输入菜单名称" @keyup.enter.native="handleFilter"/>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{ $t('table.search') }}</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">{{ $t('table.add') }}</el-button>
    </div>

    <tree-table :data="list" :async="false" :columns="columns" :expand-all="expandAll" border>
      <el-table-column width="150" label="前端地址">
        <template slot-scope="scope">
          <span>{{ scope.row.path }}</span>
        </template>
      </el-table-column>
      <el-table-column width="150" label="前端组件">
        <template slot-scope="scope">
          <span>{{ scope.row.component }}</span>
        </template>
      </el-table-column>
      <el-table-column width="80" align="center" label="排序">
        <template slot-scope="scope">
          <el-input v-model="scope.row.sort" size="mini" @change="handleChangeSort(scope.row.id, scope.row.sort)"/>
        </template>
      </el-table-column>
      <el-table-column width="100" label="图标">
        <template slot-scope="scope">
          <span>{{ scope.row.icon }}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="是否可用">
        <template slot-scope="scope">
          <span>{{ scope.row.enabled | dictLabel('sf') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" fixed="right" width="300" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">{{ $t('table.edit') }}</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">{{ $t('table.delete') }}</el-button>
          <el-button v-if="scope.row.type == '2'" type="primary" size="mini" style="width: 100px;" @click="handleGenerateButton(scope.row)">生成按钮</el-button>
        </template>
      </el-table-column>
    </tree-table>

    <el-dialog :title="textMap[dialogStatus]" :fullscreen="true" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="120px" style="width: 90%; margin-left:50px;">

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="权限类型" prop="type">
              <el-radio
                v-for="item in dictList('menutype')"
                v-model="temp.type"
                :label="item.value"
                :key="'type' + item.label">
                {{ item.label }}
              </el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否可用" prop="enabled">
              <el-radio
                v-for="item in dictList('sf')"
                v-model="temp.enabled"
                :label="item.value"
                :key="'enabled' + item.label">
                {{ item.label }}
              </el-radio>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="上级菜单" prop="parentIds">
              <el-cascader
                v-model="temp.parentIds"
                :options="list"
                :props="treeProps"
                :show-all-levels="false"
                label="name"
                change-on-select/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单名称" prop="name">
              <el-input v-model="temp.name"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="权限值" prop="permission">
              <el-input v-model="temp.permission"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="前端地址" prop="path">
              <el-input v-model="temp.path"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="前端组件" prop="component">
              <el-input v-model="temp.component"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="图标" prop="icon">
              <el-input v-model="temp.icon"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序" prop="sort">
              <el-input v-model="temp.sort"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注">
          <el-input :autosize="{ minRows: 2, maxRows: 4}" v-model="temp.remarks" type="textarea" placeholder="请填写备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">{{ $t('table.confirm') }}</el-button>
        <el-button v-else type="primary" @click="updateData">{{ $t('table.confirm') }}</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="generateButtonDialogFormVisible" title="生成按钮" width="60%">
      <el-form ref="generateButtonDataForm" :rules="generateButtonRules" :model="generateButtonTemp" label-position="left" label-width="120px" style="width: 90%; margin-left:50px;">
        <el-alert
          title="生成按钮操作执行后，会删除以前菜单下面的所有按钮，请谨慎操作"
          type="warning"
          style="margin-bottom: 20px;"
          show-icon/>
        <el-form-item label="父级权限" prop="parentPermission">
          <el-input v-model="generateButtonTemp.parentPermission"/>
        </el-form-item>
        <el-form-item label="权限" prop="permission">
          <el-checkbox-group v-model="generateButtonTemp.permission">
            <el-checkbox v-for="item in buttonInit" :label="item.permission" :key="item.permission">
              {{ item.title }}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="generateButtonDialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button type="primary" @click="generateButton">{{ $t('table.confirm') }}</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { fetchMenuList, createMenu, deleteMenu, updateMenu, changeSort, generateButton } from '@/api/sys/menu'
import waves from '@/directive/waves' // 水波纹指令
import { getDictList } from '@/utils/dict'
import treeTable from '@/components/TreeTable'

export default {
  name: 'MenuComponent',
  directives: {
    waves
  },
  components: { treeTable },
  data() {
    return {
      expandAll: false,
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      treeProps: {
        value: 'id',
        label: 'name'
      },
      treeSelectedOptions: [],
      columns: [
        {
          text: '名称',
          value: 'name',
          width: 180
        }
      ],
      listQuery: {
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      sexOptions: getDictList('sex'),
      showReviewer: false,
      temp: {
        id: undefined,
        name: '',
        type: '2',
        path: '',
        url: '',
        enabled: '1',
        sort: '0',
        icon: '',
        edirect: '',
        cacheable: 'true',
        requireAuth: 'true',
        component: '',
        remark: ''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改菜单',
        create: '添加菜单'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        type: [{ required: true, message: '菜单名称必填', trigger: 'change' }],
        name: [{ required: true, message: '菜单名称必填', trigger: 'blur' }],
        enabled: [{ required: true, message: '是否可用必选', trigger: 'change' }],
        sort: [{ required: true, message: '排序必填', trigger: 'blur' }],
        cacheable: [{ required: true, message: '是否缓存必选', trigger: 'change' }],
        requireAuth: [{ required: true, message: '是否需要认证必选', trigger: 'change' }]
      },
      downloadLoading: false,
      permissionTreeList: null,
      permissionQuery: {
        sort: '+id'
      },
      buttonInit: [
        {
          title: '查询',
          permission: 'list'
        },
        {
          title: '添加',
          permission: 'add'
        },
        {
          title: '更新',
          permission: 'update'
        },
        {
          title: '删除',
          permission: 'delete'
        },
        {
          title: '导出',
          permission: 'export'
        },
        {
          title: '导入',
          permission: 'import'
        }
      ],
      generateButtonTemp: {
        menuId: undefined,
        permission: [],
        parentPermission: ''
      },
      generateButtonDialogFormVisible: false,
      generateButtonRules: {
        parentPermission: [{ required: true, message: '父级权限必填', trigger: 'blur' }],
        permission: [{ required: true, message: '请选择权限', trigger: 'change' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchMenuList(this.listQuery).then(response => {
        this.list = response.data
        this.listLoading = false
      })
    },
    changeRequireAuth() {

    },
    handleFilter() {
      this.getList()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        name: '',
        type: '2',
        path: '',
        url: '',
        enabled: '1',
        sort: '0',
        icon: '',
        edirect: '',
        cacheable: 'true',
        requireAuth: 'true',
        component: '',
        remark: ''
      }
      this.treeSelectedOptions.length = 0
    },
    resetGenerateButtonTemp(row) {
      this.generateButtonTemp = {
        menuId: row.id,
        permission: [],
        parentPermission: ''
      }
    },
    handleChangeSort(rowId, value) {
      changeSort(rowId, value).then(response => {
        if (response.data.code === 0) {
          this.$notify({
            title: '成功',
            message: response.data.msg,
            type: 'success',
            duration: 2000
          })
          // this.getList()
        } else {
          this.$notify.error({
            title: '失败',
            message: response.data.msg,
            duration: 2000
          })
        }
      })
    },
    handleCreate() {
      this.resetTemp()
      this.changeRequireAuth()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      // 预处理提交的数据
      const parentIds = this.temp.parentIds
      if (parentIds !== undefined && parentIds !== '') {
        if (parentIds instanceof Array && parentIds.length > 0) {
          const parentId = parentIds[parentIds.length - 1]
          this.temp.parentId = parentId
        }
      }
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (this.temp.parentIds !== undefined && this.temp.parentIds !== '') {
            this.temp.parentIds.length = 0
          }
          createMenu(this.temp).then((response) => {
            const data = response.data
            if (data.code === 0) {
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '删除成功',
                type: 'success',
                duration: 2000
              })
              this.getList()
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
      })
    },
    handleUpdate(row) {
      this.resetTemp()
      this.temp = Object.assign({}, row) // copy obj
      if (this.temp.children !== undefined) {
        this.temp.children.length = 0
      }
      if (this.temp.parentIds !== undefined) {
        let parentIds = this.temp.parentIds.trim()
        if (parentIds.length > 0) {
          parentIds = parentIds.substr(0, parentIds.length - 1)
        }
        this.temp.parentIds = parentIds.split('\/')
      }
      this.temp.enabled = this.temp.enabled + ''
      this.temp.cacheable = this.temp.cacheable + ''
      this.temp.requireAuth = this.temp.requireAuth + ''
      this.changeRequireAuth()
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const parentIds = this.temp.parentIds
          if (parentIds !== undefined && parentIds !== '') {
            if (parentIds instanceof Array && parentIds.length > 0) {
              const parentId = parentIds[parentIds.length - 1]
              this.temp.parentId = parentId
            }
          }
          if (this.temp.parentIds !== undefined && this.temp.parentIds !== '') {
            this.temp.parentIds = undefined
          }
          if (this.temp.parent !== undefined) {
            this.temp.parent = undefined
          }
          const tempData = Object.assign({}, this.temp)
          updateMenu(tempData).then((response) => {
            const data = response.data
            if (data.code === 0) {
              this.getList()
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '更新成功',
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
      })
    },
    handleDelete(row) {
      this.$confirm('确认要删除菜单么?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteMenu(row.id).then(response => {
          const data = response.data
          if (data.code === 0) {
            this.$notify({
              title: '成功',
              message: '删除成功',
              type: 'success',
              duration: 2000
            })
            this.getList()
          } else {
            this.$notify({
              title: '失败',
              message: data.errmsg,
              type: 'error',
              duration: 2000
            })
          }
        })
      })
    },
    handleGenerateButton(row) {
      this.resetGenerateButtonTemp(row)
      this.generateButtonDialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['generateButtonDataForm'].clearValidate()
      })
    },
    generateButton() {
      this.$refs['generateButtonDataForm'].validate((valid) => {
        if (valid) {
          const permissionTitle = []
          this.generateButtonTemp.permission.forEach((permission, permissionIndex) => {
            this.buttonInit.forEach((item, index) => {
              if (permission === item.permission) {
                permissionTitle[permissionIndex] = item.title
              }
            })
          })
          this.generateButtonTemp.permissionTitles = permissionTitle.join(',')
          this.generateButtonTemp.permissions = this.generateButtonTemp.permission.join(',')
          generateButton(this.generateButtonTemp).then((response) => {
            if (response.data.code === 0) {
              this.getList()
              this.generateButtonDialogFormVisible = false
              this.$notify({
                title: '成功',
                message: response.data.msg,
                type: 'success',
                duration: 2000
              })
            } else {
              this.$notify.error({
                title: '失败',
                message: response.data.msg,
                duration: 2000
              })
            }
          })
        }
      })
    }
  }
}
</script>
