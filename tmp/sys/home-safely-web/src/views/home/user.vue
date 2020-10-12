<template>
  <div class="app-container">
    <el-row>
      <el-cascader
        clearable
        placeholder="部门"
        :show-all-levels="false"
        v-model="deptSelectedList"
        :options="deptListOptions"
        :props="deptListProps"
        @change="handleChange">
      </el-cascader>
      <el-button @click="refresh">刷新</el-button>
    </el-row>
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
      <el-table-column align="center" label="姓名">
        <template slot-scope="scope">
          {{ scope.row.userName }}
        </template>
      </el-table-column>
      <el-table-column label="手机号" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.userMobile }}</span>
        </template>
      </el-table-column>
      <el-table-column label="主管" align="center">
        <template slot-scope="scope">
          {{ scope.row.isLeader ? '是' : '' }}
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="回家状态" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.homeRecord" :type="scope.row.homeRecord | statusFilter">{{ scope.row.homeRecord | statusTextFilter }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="回家时间">
        <template slot-scope="scope" v-if="scope.row.homeRecord">
          <i class="el-icon-time" />
          <span>{{ scope.row.homeRecord.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="created_at" label="地理围栏">
        <template slot-scope="scope" v-if="scope.row.homeRecord">
          <i v-if="scope.row.homeRecord.inGeofence" class="el-icon-check" />
          <i v-if="!scope.row.homeRecord.inGeofence" class="el-icon-close" />
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getHomeUserList, getHomeDeptList } from '@/api/homeUser'

export default {
  filters: {
    statusFilter(homeRecord) {
      const statusMap = {
        homeDirect: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      let status;
      if (homeRecord) {
        if (homeRecord.homeStatus === 1 || homeRecord.homeStatus === 3) {
          status = 'homeDirect'
        } else if (homeRecord.homeStatus === 2) {
          status = 'draft'
        }
      } else {
        status = ''
      }
      return statusMap[status]
    },
    statusTextFilter(homeRecord) {
      let status;
      if (homeRecord) {
        if (homeRecord.homeStatus === 1 || homeRecord.homeStatus === 3) {
          status = '已回家'
        } else if (homeRecord.homeStatus === 2) {
          status = '晚回家'
        }
      } else {
        status = ''
      }
      return status
    }
  },
  data() {
    return {
      list: null,
      allList: null,
      listLoading: true,
      deptSelected: null,
      deptSelectedList: [],
      deptListOptions: [],
      deptListProps: {
        expandTrigger: 'hover',
        value: 'id',
        label: 'name'
      }
    }
  },
  created() {
    this.fetchData()
  },
  watch: {
    deptSelected(newValue, oldValue) {
      if (newValue) {
        this.list = this.allList.filter(u => u.department && u.department.indexOf(newValue + '') !== -1)
      } else {
        this.list = this.allList
      }
    }
  },
  methods: {
    refresh() {
      this.fetchData();
    },
    fetchData() {
      this.listLoading = true
      getHomeUserList().then(response => {
        this.list = response.data
        this.allList = response.data
        console.log(this.list)
        this.listLoading = false
      })
      getHomeDeptList().then(res => {
        this.deptListOptions = res.data
      })
    },
    handleChange(value) {
      this.deptSelectedList = value
      this.deptSelected = value[value.length - 1]
    }
  }
}
</script>
