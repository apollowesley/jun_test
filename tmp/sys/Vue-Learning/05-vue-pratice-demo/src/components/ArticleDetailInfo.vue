<template>
  <div class="wrapper">
    <div class="title">{{obj.title}}</div>
    <div class="content">
      {{obj.content}}
    </div>
    <div class="back" @click="back()">返回</div>
  </div>
</template>

<script>
export default {
  name: 'ArticleDetailInfo',
  props: ['obj'],
  data() {
    return {
    };
  },
  methods: {
    back() {
      this.$emit('backEvent');
    },
  },
};
</script>
