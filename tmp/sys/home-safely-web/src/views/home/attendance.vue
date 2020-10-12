<template>
  <div class="app-container">
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="ID" width="95">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="名称" width="300">
        <template slot-scope="scope">
          {{ scope.row.groupName }}
        </template>
      </el-table-column>
      <el-table-column label="人数" align="center" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.memberCount }}</span>
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="类型" align="center">
        <template slot-scope="scope">
          {{ scope.row.type | typeFilter }}
        </template>
      </el-table-column>
      <el-table-column label="规则" align="center">
        <template slot-scope="scope">
          <div v-for="cls in scope.row.classesList" :key="cls">{{cls}}</div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { attendanceList } from '@/api/homeUser'

export default {
  filters: {
    typeFilter(type) {
      if (type === 'FIXED') {
        return '固定班制'
      }
    }
  },
  data() {
    return {
      list: null,
      listLoading: true
    }
  },
  created() {
    this.fetchData()
  },
  watch: {
    deptSelected(newValue, oldValue) {
      this.list = this.list.filter(u => u.department && u.department.indexOf(newValue) !== -1)
    }
  },
  methods: {
    fetchData() {
      this.listLoading = true
      attendanceList().then(response => {
        this.list = response.data
        console.log(this.list)
        this.listLoading = false
      })
    }
  }
}
</script>
