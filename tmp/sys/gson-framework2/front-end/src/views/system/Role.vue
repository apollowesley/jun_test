<template>
  <layout-content>
    <Row type="flex" slot="header" :space-x="10">
      <Cell>
        <Select v-model="company" :datas="companyList" :nullOption="false"></Select>
      </Cell>
      <Cell>
        <router-link :to="{name:'RoleForm',params:{org:company}}">
          <button class="h-btn h-btn-primary">
            <i class="icon-plus"></i><span>新增角色</span>
          </button>
        </router-link>
      </Cell>
    </Row>
    <Table ref="table" :datas="dataList">
      <TableItem title="角色名称" prop="name" :width="200"/>
      <TableItem title="角色说明" prop="caption"/>
      <TableItem title="状态">
        <template slot-scope="{data}">
          <h-switch @change="changeStatus(data)" v-model="data.status" :trueValue="1" :falseValue="0" small>{{data.status?'可用':'禁用'}}</h-switch>
        </template>
      </TableItem>
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
  name: 'SystemRole',
  data() {
    return {
      companyList: [],
      company: null,
      dataList: []
    }
  },
  watch: {
    company() {
      this.loadRoleList()
    }
  },
  methods: {
    edit(data) {
      this.$router.push({name: 'RoleForm', params: {id: data.id}})
    },
    del(row) {
      this.$Confirm(`确认删除角色${row.name}`).then(() => {
        this.$api.system.role.delete(row.id).then(({success, msg}) => {
          if (success) {
            this.$Message(`${row.name}角色已删除！`)
            this.loadRoleList()
          } else {
            this.$Message(msg)
          }
        })
      }).catch(() => {
      })
    },
    loadRoleList() {
      this.$api.system.role.list({
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
    },
    changeStatus(data) {
      this.$api.system.role.update(data).then(({data}) => {
        this.$Message('状态已变更！')
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
