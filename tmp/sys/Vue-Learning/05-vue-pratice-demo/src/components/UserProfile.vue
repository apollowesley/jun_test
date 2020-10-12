<template>
  <div class="hello">
    <h1>用户名：{{ username }}</h1>
    <h1>密码：{{ password }}</h1>
  </div>
</template>

<script>
export default {
  name: 'UserProfile',
  props: {
    username: String,
    password: String,
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
h3 {
  margin: 40px 0 0;
}
</style>
