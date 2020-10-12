<template>
  <div>
    <biz-page
      :form="form"
      :table="table"
      :pager="pager"
    ></biz-page>
  </div>
</template>
<script>
import BizPage from '@/components/BizPage.vue'
import { list } from '@/api/portalResource'
export default {
  name: 'resource',
  components: {
    BizPage
  },
  data() {
    return {
      pager: {
        total: 0,
        pageSize: 10,
        currentPage: 1,
        currentChange: (index) => {
          this.pager.currentPage = index
          this.list()
        },
        sizeChange: (size) => {
          this.pager.pageSize = size
          this.list()
        }
      },
      form: {
        search: {
        },
        formItems: [
          { type: 'input', prop: 'resourceName', placeholder: '请输入资源名' },
          { type: 'input', prop: 'resourceDesc', placeholder: '请输入资源描述' }
        ],
        actions: [
          {
            label: '查询',
            icon: 'el-icon-search',
            type: 'primary',
            handle: () => this.list()
          }
        ]
      },
      table: {
        loading: false,
        list: [],
        columns: [{
          prop: 'id',
          label: '编号',
          width: 150
        },
        {
          prop: 'resourceType',
          label: '资源类型',
        },
        {
          prop: 'resourceName',
          label: '资源名称',
        },
        {
          prop: 'resourceDesc',
          label: '角色描述',
        },
        {
          prop: 'parentId',
          label: '父资源Id',
          width: 80
        },
        {
          prop: 'createTime',
          label: '创建时间',
          formatter: (row) => {
            return this.$formatDateTime(row.createTime)
          }
        },
        {
          prop: 'updateTime',
          label: '更新时间',
          formatter: (row) => {
            return this.$formatDateTime(row.createTime)
          }
        }]
      }
    }
  },
  methods: {
    list() {
      this.table.loading = true
      const params = {
        currentPage: this.pager.currentPage,
        pageSize: this.pager.pageSize,
        resourceName: this.form.search.resourceName,
        resourceDesc: this.form.search.resourceDesc
      }
      list(params).then(res => {
        this.table.list = res.list
        this.pager.total = res.total
        this.table.loading = false
      })
    }
  },
  created() {
    this.list()
  }
}
</script>