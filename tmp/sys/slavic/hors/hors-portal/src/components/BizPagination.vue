<template>
  <div>
    <el-row>
      <el-col align="right">
        <el-pagination
          background
          :page-sizes="pager.sizes ? pager.sizes : dataDefault.pager.sizes"
          :page-size="pager.pageSize"
          layout="prev, pager, next, jumper, total, sizes"
          :total="pager.total ? pager.total : dataDefault.pager.total"
          :current-page="pager.currentPage"
          @current-change="pager.currentChange"
          @size-change="pager.sizeChange"
        />
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'BizPagination',
  data() {
    return {
      dataDefault: {
        pager: {
          sizes: [10, 30, 50, 100, 500],
          total: 0
        }
      }
    }
  },
  props: {
    pager: {
      type: Object,
      default: () => { return {} }
    }
  }
}
</script>