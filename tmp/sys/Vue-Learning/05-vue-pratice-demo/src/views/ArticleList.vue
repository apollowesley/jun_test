<template>
  <div class="about">
    <h1>This is ArticalList.vue page</h1>
    <ul>
      <li v-for="(item, index) in articleList"
          @click="itemChooseClick(item)"
          :key="index">
        <div class="title">{{item.title}}</div>
        <div class="content">{{item.content}}</div>
        <div class="time">{{item.time}}</div>
        <div class="detail" v-show="index === current" @click="showInfo(item)">查看详情>></div>
      </li>
      <li v-show="articleList.length == 0" :style="{'text-align': 'center'}">这里空空如也~~</li>
    </ul>
  </div>
</template>
<script>
import store from '@/store/index';

export default {
  name: 'articleList',
  methods: {
    itemChooseClick(item) {
      console.log('当前选中：', item);
    },
    showInfo() {
      this.$router.push('/articleDetailInfo');
    },
  },
  computed: {
    articleList() {
      return store.state.articleList;
    },
  },
};

</script>
