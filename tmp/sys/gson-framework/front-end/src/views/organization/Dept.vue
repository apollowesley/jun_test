<template>
  <layout-content>
    <Row type="flex" slot="header" :space-x="10">
      <Cell>
        <Select v-model="company" :datas="companyList" :nullOption="false"></Select>
      </Cell>
      <Cell>
        <router-link :to="{name:'DeptForm',params:{org:company}}">
          <button class="h-btn h-btn-primary">
            <i class="icon-plus"></i><span>新增部门</span>
          </button>
        </router-link>
      </Cell>
    </Row>
    <Table ref="table" :datas="dataList">
      <TableItem title="部门名称" prop="name" treeOpener></TableItem>
      <TableItem title="主管" prop="leader"></TableItem>
      <TableItem title="位置" prop="address"></TableItem>
      <TableItem title="操作" :width="200" align="center">
        <div class="actions" slot-scope="{data}">
          <span class="text-hover" @click="trigger('create',data)"> <i class="fa fa-plus"></i> 新建</span>
          <span class="text-hover" @click="trigger('edit',data)"> <i class="fa fa-edit"></i> 编辑</span>
          <span class="text-hover" @click="trigger('del',data)"> <i class="fa fa-trash"></i> 删除</span>
        </div>
      </TableItem>
    </Table>
  </layout-content>
</template>

<script>

export default {
  name: 'OrganizationDept',
  data() {
    return {
      companyList: [],
      company: null,
      dataList: []
    }
  },
  watch: {
    company() {
      this.loadDeptList()
    }
  },
  methods: {
    trigger(code, data) {
      this[code].call(this, data)
    },
    create(data) {
      this.$router.push({name: 'DeptForm', params: {org: this.company, id: data.id}})
    },
    edit(data) {
      this.$router.push({name: 'DeptForm', params: {id: data.id}})
    },
    del(row) {
      this.$Confirm(`确认删除部门${row.name}`).then(() => {
        this.$api.organization.dept.delete(row.id).then(({success, msg}) => {
          if (success) {
            this.$Message(`${row.name}部门已删除！`)
            this.loadDeptList()
          } else {
            this.$Message(msg)
          }
        })
      }).catch(() => {
      })
    },
    loadDeptList() {
      this.$api.organization.dept.list({
        company: this.company
      }).then(({data}) => {
        this.dataList = data
      })
    },
    loadCompanyList() {
      this.$api.organization.company.list().then(({data}) => {
        this.companyList = data.map(d => {
          return {
            title: d.name,
            key: d.id
          }
        })
        this.$nextTick(() => {
          this.company = this.companyList[0].key
        })
      })
    }
  },
  mounted() {
    this.loadCompanyList()
  }
}
</script>

<style scoped>

</style>
