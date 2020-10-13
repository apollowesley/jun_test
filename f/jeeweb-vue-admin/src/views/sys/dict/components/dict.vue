<template>
  <div id="dictComponent">
    <div>
      <div class="filter-container">
        <el-input v-model="listQuery.keyword" style="width: 200px;" class="filter-item" placeholder="请输入字典标签或值" @keyup.enter.native="handleFilter"/>
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
        <el-table-column
          prop="label"
          label="字典标签"
          width="160"/>
        <el-table-column
          prop="value"
          label="字典值"
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
          :page-sizes="[10,20,30, 50]"
          :page-size="listQuery.limit"
          :total="total"
          background
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"/>
      </div>

      <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
        <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="80px" style="width: 400px; margin-left:50px;">
          <el-form-item label="字典标签" prop="label">
            <el-input v-model="temp.label"/>
          </el-form-item>
          <el-form-item label="字典值" prop="value">
            <el-input v-model="temp.value"/>
          </el-form-item>
          <el-form-item label="排序" prop="sort">
            <el-input v-model="temp.sort"/>
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
  </div>
</template>

<script>
import { fetchDictList, createDict, deleteDict, updateDict } from '@/api/sys/dict'
import waves from '@/directive/waves' // 水波纹指令
export default {
  name: 'SysDictComponent',
  directives: {
    waves
  },
  data() {
    return {
      dictGroup: {},
      tableKey: 0,
      list: null,
      total: null,
      listLoading: false,
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
        label: '',
        value: '',
        sort: 1,
        remarks: ''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑字典',
        create: '添加字典'
      },
      rules: {
        label: [{ required: true, message: '字典标签不能为空', trigger: 'change' }],
        value: [{ required: true, message: '字典值不能为空', trigger: 'change' }],
        sort: [{ required: true, message: '排序不能为空', trigger: 'change' }]
      },
      downloadLoading: false
    }
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchDictList(this.listQuery).then(response => {
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
        label: '',
        value: '',
        sort: 1,
        remarks: '',
        gid: this.dictGroup.id
      }
    },
    refreshGroupDict(row) {
      this.listQuery.gid = row.id
      this.listQuery.page = 1
      this.listQuery.keyword = ''
      this.dictGroup = row
      this.getList()
    },
    handleCreate() {
      if (this.dictGroup.id === undefined || this.dictGroup.id === '') {
        this.$notify({
          title: '提示',
          message: '请选择字典分组',
          type: 'error',
          duration: 2000
        })
      } else {
        this.resetTemp()
        this.dialogStatus = 'create'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].clearValidate()
        })
      }
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          createDict(this.temp).then((response) => {
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
          updateDict(tempData).then((response) => {
            const data = response.data
            if (data.code === 0) {
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
      deleteDict(row.id).then((response) => {
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
  }
}
</script>
