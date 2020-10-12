<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <div id="nav">
      <router-link :to="{name:'articleList'}">文章列表</router-link>
      <router-link :to="{name:'userCenter'}">个人中心</router-link>
    </div>
    <router-view/>
  </div>
</template>

<script>
// @ is an alias to /src
// import UserCenter from '@/views/UserCenter.vue';
// import ArticleList from '@/views/ArticleList.vue';

export default {
  name: 'home',
};
</script>

<style lang="scss">
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  #nav {
    padding: 30px;

    a {
      font-weight: bold;
      color: #2c3e50;
      margin-left: 50px;

      &.router-link-exact-active {
        color: #42b983;
      }
    }
  }
</style>
