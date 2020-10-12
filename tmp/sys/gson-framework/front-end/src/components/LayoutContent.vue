<template>
  <Row class="content-view">
    <Cell class="content-view-header" v-if="$slots.header">
      <slot name="header"/>
    </Cell>
    <Cell>
      <slot/>
    </Cell>
    <Cell v-if="$slots.footer" class="content-view-footer">
      <slot name="footer"/>
    </Cell>
    <Loading text="Loading" :loading="loading"></Loading>
  </Row>
</template>

<script>
export default {
  name: 'LayoutContent',
  props: {
    title: String,
    noTitle: Boolean,
    loading: Boolean,
    isBack: Boolean
  },
  computed: {
    navTitle() {
      return this.title ? this.title : this.$route.meta.title
    }
  },
  methods: {
    doBack() {
      this.$router.back()
    }
  },
}
</script>

<style scoped lang="less">
  .content-view {
    margin-top: 20px;

    .content-view-header {
      margin-top: -20px;
    }
  }
</style>
