<template>
  <layout-content>
    <Row type="flex" slot="header" :space-x="10">
      <Cell>
        <TreePicker style="min-width: 200px" :option="param" ref="treepicker" v-model="dept" placeholder="请选择部门"></TreePicker>
      </Cell>
      <Cell>
        <router-link tag="button" class="h-btn h-btn-primary" :disabled="!dept" :to="{name:'EmployeesForm',params:{dept:dept}}">
          <i class="icon-plus"></i><span>新增员工</span>
        </router-link>
      </Cell>
    </Row>
    <Table :datas="pageData.records">
      <TableItem title="工号" prop="jobNumber"/>
      <TableItem title="姓名" prop="userName"/>
      <TableItem title="性别" prop="gender"/>
      <TableItem title="电话" prop="telephone"/>
      <TableItem title="邮箱" prop="email"/>
      <TableItem title="类型" prop="type"/>
      <TableItem title="入职时间" prop="entryDate"/>
      <TableItem title="操作" :width="200" align="center">
        <div class="actions" slot-scope="{data}">
          <span class="text-hover" @click="edit(data)"> <i class="fa fa-edit"></i> 编辑</span>
          <span class="text-hover" @click="del(data)"> <i class="fa fa-trash"></i> 删除</span>
        </div>
      </TableItem>
    </Table>
  </layout-content>
</template>

<script>
export default {
  name: 'OrganizationEmployees',
  data() {
    return {
      param: {
        keyName: 'id',
        parentName: 'parent',
        titleName: 'title',
        dataMode: 'list',
        datas: []
      },
      dept: null,
      deptName: null,
      pageData: {
        current: 1,
        records: [],
        size: 20,
        total: 0
      }
    }
  },
  watch: {
    dept(val) {
      this.loadEmployeesList()
      localStorage.setItem('employees_dept', val)
    }
  },
  methods: {
    edit(data) {
      this.$router.push({name: 'EmployeesForm', params: {id: data.id}})
    },
    del(row) {
      this.$Confirm(`确认删除员工${row.userName}`).then(() => {
        this.$api.organization.dept.delete(row.id).then(({success, msg}) => {
          if (success) {
            this.$Message(`${row.userName}员工已删除！`)
            this.loadEmployeesList()
          } else {
            this.$Message(msg)
          }
        })
      })
    },
    loadEmployeesList() {
      this.$api.organization.employees.list({
        department_id: this.dept,
        page: this.pageData.current
      }).then(({data}) => {
        this.pageData = data
      })
    },
    loadDeptList() {
      this.$api.organization.dept.fullTree().then(({data}) => {
        this.param = Object.assign(this.param, {datas: data})
        this.dept = parseInt(localStorage.getItem('employees_dept'))
      })
    }
  },
  mounted() {
    this.loadDeptList()
  }
}
</script>

<style scoped>

</style>
