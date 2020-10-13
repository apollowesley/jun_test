<template>
  <div class="app-container calendar-list-container">
    <div class="filter-container">
      <el-input v-model="listQuery.fileName" style="width: 200px;" class="filter-item" placeholder="请输入附件名称" @keyup.enter.native="handleFilter"/>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">{{ $t('table.search') }}</el-button>
    </div>

    <el-table
      v-loading="listLoading"
      ref="multipleTable"
      :key="tableKey"
      :data="list"
      element-loading-text="给我一点时间"
      border
      fit
      highlight-current-row
      tyle="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" align="center"/>
      <el-table-column width="200" align="center" label="文件名称">
        <template slot-scope="scope">
          <span>{{ scope.row.fileName }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="文件路径">
        <template slot-scope="scope">
          <span><a :href="scope.row.filePath" target="_blank">{{ scope.row.filePath }}</a></span>
        </template>
      </el-table-column>
      <el-table-column width="90" align="center" label="文件大小">
        <template slot-scope="scope">
          <span>{{ scope.row.fileSize }} K</span>
        </template>
      </el-table-column>
      <el-table-column width="80" align="center" label="扩展名">
        <template slot-scope="scope">
          <span>{{ scope.row.fileExtension }}</span>
        </template>
      </el-table-column>
      <el-table-column width="160" align="center" label="上传时间">
        <template slot-scope="scope">
          <span>{{ scope.row.uploadTime }}</span>
        </template>
      </el-table-column>
      <el-table-column width="120" align="center" label="上传的IP">
        <template slot-scope="scope">
          <span>{{ scope.row.uploadIp }}</span>
        </template>
      </el-table-column>

      <el-table-column :label="$t('table.actions')" width="80" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
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

  </div>
</template>

<script>
import { fetchAttachmentList, deleteAttachment } from '@/api/oss/attachment'
import waves from '@/directive/waves' // 水波纹指令

export default {
  name: 'SysSendlogList',
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
      multipleSelection: []
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchAttachmentList(this.listQuery).then(response => {
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
        phone: '',
        code: '',
        data: ''
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    handleDelete(row) {
      deleteAttachment(row.id).then(() => {
        this.$notify({
          title: '成功',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
        const index = this.list.indexOf(row)
        this.list.splice(index, 1)
      })
    }
  }
}
</script>
