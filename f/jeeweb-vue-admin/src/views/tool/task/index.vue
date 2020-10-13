<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-input v-model="listQuery.jobName" style="width: 200px;" class="filter-item" placeholder="请输入任务名称" @keyup.enter.native="handleFilter"/>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{ $t('table.search') }}</el-button>
    </div>
    <div class="toolbar-container">
      <el-button size="small" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-circle-plus" @click="handleCreate">{{ $t('table.add') }}</el-button>
      <el-button :loading="batchDeleteLoading" size="small" class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-delete" @click="handleBatchDelete">{{ $t('table.delete') }}</el-button>
      <el-button :loading="refreshTaskLoading" size="small" class="filter-item" type="primary" icon="el-icon-refresh" @click="handleRefreshTask">刷新任务</el-button>
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
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" align="center"/>
      <el-table-column width="250" align="center" label="任务名称">
        <template slot-scope="scope">
          <span>{{ scope.row.jobName }}</span>
        </template>
      </el-table-column>
      <el-table-column width="120" align="center" label="任务分组">
        <template slot-scope="scope">
          <span>{{ scope.row.jobGroup }}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="执行类">
        <template slot-scope="scope">
          <span>{{ scope.row.executeClass }}</span>
        </template>
      </el-table-column>
      <el-table-column width="80" align="center" label="方法名">
        <template slot-scope="scope">
          <span>{{ scope.row.methodName }}</span>
        </template>
      </el-table-column>
      <el-table-column width="100" align="center" label="执行表达式">
        <template slot-scope="scope">
          <span>{{ scope.row.cronExpression }}</span>
        </template>
      </el-table-column>
      <el-table-column width="80" align="center" label="任务状态">
        <template slot-scope="scope">
          <span>{{ scope.row.jobStatus| dictLabel('sf') }}</span>
        </template>
      </el-table-column>
      <el-table-column width="80" align="center" label="是否同步">
        <template slot-scope="scope">
          <span>{{ scope.row.isConcurrent | dictLabel('sf') }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.actions')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.jobStatus==0" type="primary" size="mini" @click="handleChangeJobStatus(scope.row, 'start', '启动')">开始</el-button>
          <el-button v-if="scope.row.jobStatus==1" type="danger" size="mini" @click="handleChangeJobStatus(scope.row, 'stop', '停止')">停止</el-button>
          <el-button type="primary" size="mini" @click="handleRefresh(scope.row)">刷新</el-button>
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">{{ $t('table.edit') }}</el-button>
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

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" :fullscreen="true">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="120px" style="width: 90%; margin-left:50px;">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="任务名称" prop="jobName">
              <el-input v-model="temp.jobName"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="任务分组" prop="jobGroup">
              <el-input v-model="temp.jobGroup"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="加载方式" prop="loadWay">
              <el-radio
                v-for="item in dictList('loadway')"
                v-model="temp.loadWay"
                :label="item.value"
                :key="'type' + item.label">
                {{ item.label }}
              </el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="执行类" prop="executeClass">
              <el-input v-model="temp.executeClass"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="方法名" prop="methodName">
              <el-input v-model="temp.methodName"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="方法参数" prop="methodParams">
              <el-input v-model="temp.methodParams"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="cron表达式" prop="cronExpression">
              <el-input v-model="temp.cronExpression"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否同步" prop="isConcurrent">
              <el-radio
                v-for="item in dictList('sf')"
                v-model="temp.isConcurrent"
                :label="item.value"
                :key="'type' + item.label">
                {{ item.label }}
              </el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="任务描述">
          <el-input
            :autosize="{ minRows: 2, maxRows: 4}"
            v-model="temp.templateContent"
            type="textarea"
            placeholder="请输入任务描述"/>
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
import { fetchScheduleList, createSchedule, deleteSchedule, batchDeleteSchedule, updateSchedule, refreshTask, refreshSchedule, changeJobStatus } from '@/api/tool/task/schedule'
import waves from '@/directive/waves' // 水波纹指令

export default {
  name: 'SysScheduleList',
  directives: {
    waves
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: null,
      listLoading: true,
      refreshTaskLoading: false,
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
        jobName: '',
        cronExpression: '',
        executeClass: '',
        methodName: '',
        methodParams: '',
        misfirePolicy: '',
        loadWay: '1',
        isConcurrent: '1',
        description: '',
        jobStatus: '',
        jobGroup: ''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑任务',
        create: '添加任务'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        jobName: [{ required: true, message: '请输入任务名称！', trigger: 'blur' }],
        jobGroup: [{ required: true, message: '请输入任务分组！', trigger: 'blur' }],
        executeClass: [{ required: true, message: '请输入执行类！', trigger: 'blur' }],
        loadWay: [{ required: true, message: '请选择加载方式！', trigger: 'change' }],
        isConcurrent: [{ required: true, message: '请选择是否同步！', trigger: 'change' }],
        methodName: [{ required: true, message: '请输入方法名！', trigger: 'blur' }],
        cronExpression: [{ required: true, message: '请输入cron表达式！', trigger: 'blur' }]
      },
      downloadLoading: false,
      multipleSelection: []
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchScheduleList(this.listQuery).then(response => {
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
        jobName: '',
        cronExpression: '',
        executeClass: '',
        methodName: '',
        methodParams: '',
        misfirePolicy: '',
        loadWay: '1',
        isConcurrent: '1',
        description: '',
        jobStatus: '',
        jobGroup: ''
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
          createSchedule(this.temp).then((response) => {
            const data = response.data
            if (data.code !== 0) {
              this.$message({
                message: data.msg,
                type: 'error'
              })
            } else {
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '创建成功',
                type: 'success',
                duration: 2000
              })
              this.getList()
            }
          })
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.temp.timestamp = new Date(this.temp.timestamp)
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
          tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          updateSchedule(tempData).then((response) => {
            const data = response.data
            if (data.code !== 0) {
              this.$message({
                message: data.msg,
                type: 'error'
              })
            } else {
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '创建成功',
                type: 'success',
                duration: 2000
              })
              this.getList()
            }
          })
        }
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    handleDelete(row) {
      this.$confirm('确认要删除任务么?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteSchedule(row.id).then(response => {
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
    handleRefresh(row) {
      this.$confirm('确认要更新任务么?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        refreshSchedule(row.id).then(response => {
          const data = response.data
          if (data.code === 0) {
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
              message: data.errmsg,
              type: 'error',
              duration: 2000
            })
          }
        })
      })
    },
    handleChangeJobStatus(row, cmd, cmdLabel) {
      this.$confirm('你确定要' + cmdLabel + '该计划任务么?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        changeJobStatus(row.id, cmd).then(response => {
          const data = response.data
          if (data.code === 0) {
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
              message: data.errmsg,
              type: 'error',
              duration: 2000
            })
          }
        })
      })
    },
    handleRefreshTask() {
      refreshTask().then(() => {
        this.$notify({
          title: '成功',
          message: '提交成功',
          type: 'success',
          duration: 2000
        })
        this.$refs.multipleTable.clearSelection()
        this.refreshTaskLoading = false
      }).catch(() => {
        this.refreshTaskLoading = false
      })
    },
    handleBatchDelete() {
      if (this.multipleSelection.length) {
        this.batchDeleteLoading = true
        const list = this.multipleSelection
        var ids = []
        list.forEach(function(value, index, array) {
          ids.push(value.id)
        })
        var idsStr = ids.join(',')
        this.$confirm('你确定要删除该计划任务么?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          batchDeleteSchedule(idsStr).then(() => {
            this.$notify({
              title: '成功',
              message: '提交成功',
              type: 'success',
              duration: 2000
            })
            this.getList()
            this.batchDeleteLoading = false
          }).catch(() => {
            this.batchDeleteLoading = false
          })
        })
        this.getList()
      } else {
        this.$message({
          message: '至少选择一条任务',
          type: 'warning'
        })
      }
    }
  }
}
</script>
