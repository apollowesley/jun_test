<template>
  <div class="about">
    <h1>This is ArticalSend.vue page</h1>
    <p>标题：</p><input v-model="title" placeholder="标题">
      <p>内容：</p><input v-model="content" placeholder="内容">
      <button @click="sendClick">发表</button>
      <button type="button" @click="()=>{this.$router.go('-1')}">返回</button>
  </div>
</template>

<script>
import store from '@/store/index';

export default {
  name: 'articalSend',
  data() {
    return {
      title: '',
      content: '',
    };
  },
  methods: {
    sendClick() {
      store.commit('addItem', {
        title: this.title,
        content: this.content,
        time: new Date(),
      });
      this.title = '';
      this.content = '';
      this.$router.push({ name: 'articleList' });
    },
  },
};

</script>
