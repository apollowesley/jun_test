<template>
  <div id="dictGroupComponent">
    <div>
      <div class="filter-container">
        <el-input v-model="listQuery.keyword" style="width: 200px;" class="filter-item" placeholder="请输入分组名称或编码" @keyup.enter.native="handleFilter"/>
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
        style="width: 100%"
        @row-click="refreshDicts">
        <el-table-column
          prop="name"
          label="分组名称"
          width="160"/>
        <el-table-column
          prop="code"
          label="分组编码"
          width="160"/>
        <el-table-column :label="$t('table.actions')" align="center" width="180" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">{{ $t('table.edit') }}</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">{{ $t('table.delete') }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-container">
        <el-pagination
          :current-page.sync="listQuery.page"
          :page-sizes="[5,10,20, 30]"
          :page-size="listQuery.limit"
          :total="total"
          background
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"/>
      </div>

      <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
        <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="100px" style="width: 400px; margin-left:50px;">
          <el-form-item label="分组名称" prop="name">
            <el-input v-model="temp.name"/>
          </el-form-item>
          <el-form-item label="分组编码" prop="code">
            <el-input v-model="temp.code"/>
          </el-form-item>
          <el-form-item label="分组备注">
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
  </div>
</template>

<script>
import { fetchDictGroupList, createDictGroup, deleteDictGroup, updateDictGroup } from '@/api/sys/dictGroup'
import waves from '@/directive/waves' // 水波纹指令

export default {
  name: 'SysDictGroupComponent',
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
        limit: 5,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: '+id'
      },
      showReviewer: false,
      temp: {
        id: undefined,
        name: '',
        code: '',
        remarks: ''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑分组',
        create: '添加分组'
      },
      rules: {
        name: [{ required: true, message: '分组名称必填', trigger: 'change' }],
        code: [{ required: true, message: '分组编码必填', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    refreshDicts(row) {
      this.$emit('refreshDicts', row)
    },
    getList() {
      this.listLoading = true
      fetchDictGroupList(this.listQuery).then(response => {
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
        name: '',
        code: '',
        remarks: ''
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
          createDictGroup(this.temp).then((response) => {
            const data = response.data
            if (data.code === 0) {
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '创建成功',
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
          updateDictGroup(tempData).then((response) => {
            const data = response.data
            if (data.code === 0) {
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '更新成功',
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
    handleDelete(row) {
      deleteDictGroup(row.id).then((response) => {
        const data = response.data
        if (data.code === 0) {
          this.getList()
          this.dialogFormVisible = false
          this.$notify({
            title: '成功',
            message: '删除成功',
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
