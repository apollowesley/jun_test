<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-input v-model="listQuery.keyword" style="width: 200px;" class="filter-item" placeholder="请输入部门名称" @keyup.enter.native="handleFilter"/>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{ $t('table.search') }}</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">{{ $t('table.add') }}</el-button>
    </div>

    <tree-table :data="list" :async="false" :columns="columns" :expand-all="expandAll" border>
      <el-table-column label="备注">
        <template slot-scope="scope">
          <span style="color:sandybrown">{{ scope.row.remarks }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">{{ $t('table.edit') }}</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">{{ $t('table.delete') }}
          </el-button>
        </template>
      </el-table-column>
    </tree-table>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
        <el-form-item label="上级部门" prop="parentIds">
          <el-cascader
            v-model="temp.parentIds"
            :options="list"
            :props="treeProps"
            :show-all-levels="false"
            label="name"
            change-on-select/>
        </el-form-item>
        <el-form-item label="部门名称" prop="name">
          <el-input v-model="temp.name"/>
        </el-form-item>
        <el-form-item label="备注">
          <el-input :autosize="{ minRows: 2, maxRows: 4}" v-model="temp.remarks" type="textarea" placeholder="Please input"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">{{ $t('table.confirm') }}</el-button>
        <el-button v-else type="primary" @click="updateData">{{ $t('table.confirm') }}</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { fetchOrganizationList, createOrganization, deleteOrganization, updateOrganization } from '@/api/sys/organization'
import waves from '@/directive/waves' // 水波纹指令
import treeTable from '@/components/TreeTable'

export default {
  name: 'SysOrganization',
  directives: {
    waves
  },
  components: { treeTable },
  data() {
    return {
      expandAll: true,
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
          width: 300
        }
      ],
      listQuery: {
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      showReviewer: false,
      temp: {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        type: '',
        status: 'published'
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改机构',
        create: '添加机构'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        type: [{ required: true, message: 'type is required', trigger: 'change' }],
        timestamp: [{ type: 'date', required: true, message: 'timestamp is required', trigger: 'change' }],
        title: [{ required: true, message: 'title is required', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchOrganizationList(this.listQuery).then(response => {
        this.list = response.data
        this.listLoading = false
      })
    },
    handleFilter() {
      this.getList()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        importance: 1,
        remark: '',
        timestamp: new Date(),
        title: '',
        status: 'published',
        type: ''
      }
      this.treeSelectedOptions.length = 0
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
          createOrganization(this.temp).then(() => {
            this.getList()
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '创建成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleUpdate(row) {
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
          console.log(tempData)
          updateOrganization(tempData).then(() => {
            this.getList()
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
      deleteOrganization(row.id).then(() => {
        this.$notify({
          title: '成功',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
        this.getList()
      })
    }
  }
}
</script>
