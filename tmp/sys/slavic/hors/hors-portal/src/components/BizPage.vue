<template>
  <div>
    <biz-form :form="form"></biz-form>
    <biz-table :table="table"></biz-table>
    <br>
    <biz-pagination :pager="pager"></biz-pagination>
    <biz-dialog
      v-if="add"
      :dialog="add"
      @change="add.isShow = false"
    />
    <biz-dialog
      v-if="edit"
      :dialog="edit"
      @change="edit.isShow = false"
    />
  </div>
</template>
<script>
import BizForm from '@/components/BizForm.vue'
import BizDialog from '@/components/BizDialog.vue'
import BizTable from '@/components/BizTable.vue'
import BizPagination from '@/components/BizPagination.vue'
export default {
  name: 'BizPage',
  data() {
    return {
      multipleSelection: []
    }
  },
  props: {
    edit: {
      type: Object,
      default: () => { return {} }
    },
    add: {
      type: Object,
      default: () => { return {} }
    },
    form: {
      type: Object,
      required: true,
      default: () => { return {} }
    },
    table: {
      type: Object,
      required: true,
      default: () => { return {} }
    },
    pager: {
      type: Object,
      required: true,
      default: function () { return {} }
    }
  },
  components: {
    BizForm: BizForm,
    BizDialog: BizDialog,
    BizTable: BizTable,
    BizPagination: BizPagination
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val
      this.$emit('handleSelectionChange', val)
    }
  }
}
</script>