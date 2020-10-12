<template>
  <layout-content>
    <Row type="flex" slot="header" :space-x="5">
      <Cell>
        <input v-width="200" type="text" placeholder="请输入帐号搜索">
      </Cell>
      <Cell>
        <Button color="green" icon="icon-search">搜索</Button>
      </Cell>
      <Cell>
        <router-link tag="button" class="h-btn h-btn-primary" :to="{name:'AccountForm'}">
          <i class="icon-plus"></i><span>新增账号</span>
        </router-link>
      </Cell>
    </Row>
    <Table :datas="dataList">
      <TableItem title="帐号" prop="account"/>
      <TableItem title="电话" prop="telephone"/>
      <TableItem title="关联员工" prop="employeesName"/>
      <TableItem title="状态" prop="status"/>
      <TableItem title="创建时间" prop="createDate"/>
      <TableItem title="操作" align="center">
        <div class="actions" slot-scope="{data}">
          <span class="text-hover" @click="edit(data)"><i class="fa fa-edit"></i> 编辑</span>
          <span class="text-hover" @click="del(data)"><i class="fa fa-trash"></i> 删除</span>
        </div>
      </TableItem>
    </Table>
    <Pagination slot="footer" v-if="page.total > page.size" v-model="page" :small="true" align="center"></Pagination>
  </layout-content>
</template>

<script>
export default {
  name: 'SystemAccount',
  data() {
    return {
      page: {
        page: 1,
        size: 10,
        total: 0
      },
      dataList: []
    }
  },
  watch: {
    page: {
      deep: true,
      handler() {
        this.loadData()
      }
    }
  },
  methods: {
    loadData() {
      this.$api.system.account.list({
        page: this.page.page,
        size: this.page.size
      }).then(res => {
        this.dataList = res.data.records
        this.page.total = res.data.total
      })
    },
    edit(data) {
      this.$router.push({name: 'AccountForm', params: {id: data.id}})
    },
    del(data) {
      this.$Confirm('确定删除？').then(() => {
        this.$api.system.account.delete(data.id).then(({success, msg}) => {
          if (success) {
            this.$Message.success('删除成功！')
            this.loadData()
          } else {
            this.$Message.error(msg)
          }
        })
      })
    }
  },
  mounted() {
    this.loadData()
  }
}
</script>

<style scoped>

</style>
