<template>
  <layout-content>
    <Row type="flex" slot="header" :space-x="10">
      <Cell>
        <Select v-model="company" :datas="companyList" :nullOption="false"></Select>
      </Cell>
      <Cell>
        <router-link :to="{name:'PositionForm',params:{org:company}}">
          <button class="h-btn h-btn-primary">
            <i class="icon-plus"></i><span>新增职位</span>
          </button>
        </router-link>
      </Cell>
    </Row>
    <Table ref="table" :datas="dataList">
      <TableItem title="职位名称" prop="name" :width="200"/>
      <TableItem title="职位职责" prop="responsibility"/>
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
  name: 'OrganizationPosition',
  data() {
    return {
      companyList: [],
      company: null,
      dataList: []
    }
  },
  watch: {
    company() {
      this.loadPositionList()
    }
  },
  methods: {
    edit(data) {
      this.$router.push({name: 'PositionForm', params: {id: data.id}})
    },
    del(row) {
      this.$Confirm(`确认删除职位${row.name}`).then(() => {
        this.$api.organization.position.delete(row.id).then(({success, msg}) => {
          if (success) {
            this.$Message(`${row.name}职位已删除！`)
            this.loadPositionList()
          } else {
            this.$Message(msg)
          }
        })
      }).catch(() => {
      })
    },
    loadPositionList() {
      this.$api.organization.position.list({
        company_id: this.company
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
