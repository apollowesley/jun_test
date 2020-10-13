<template>
  <div v-if="page.pageNumbers && page.pageNumbers.length > 1" class="pagination">

    <a class="page-link" :class="{active: page.pageNumber == item}" @click="paging(item)"
       v-for="item in page.ellipsisPageNumbers" v-text="item == 0 ? '...' : item"></a>
    <template v-if="page.lastPageNumber > 50">
      <input title="指定页码" maxlength="3">
      <a class="page-link">Go!</a>
    </template>
    <select title="分页大小" @change="change" v-if="sizes">
      <option :value="size" v-for="size in sizes">{{size}}</option>
    </select>
  </div>
  <div v-else-if="page.totalCount == 0" class="pagination text-grey">没有找到记录...</div>
</template>

<script>
  export default {
    name: 'pagination',
    props: {
      page: { // 分页对象
        type: Object,
        requried: true
      },
      sizes: [Array, Boolean]
    },
    methods: {
      paging (pageno) {
        if (pageno === this.page.pageNumber || pageno === 0) { // 点击当前页或略过页(页数为0表示略过页)时,不触分页事件
          return
        }
        this.$emit('paging', pageno, this.page.pageSize)
      },
      change (e) {
        this.$emit('paging', this.page.pageNumber, e.target.value)
      }
    }
  }
</script>

<style>
  .pagination select, .pagination input {
    outline: none 0 !important;
    border: 1px solid #E6E6E6;
    border-radius: 3px;
    padding: 0 .5rem;
    margin: 0 0.5rem;
  }
  .pagination input {
    width: 3rem;
  }
  .page-link {
    border: 1px solid transparent !important;
    border-radius: .25rem;
    padding: .3rem .6rem;
    margin: 0 .25rem 0 0;
    font-size: .9375rem;
  }
  .page-link:focus, .page-link:hover, .page-link.active {
    background-color: #eceeef;
    text-decoration: none
  }
</style>
