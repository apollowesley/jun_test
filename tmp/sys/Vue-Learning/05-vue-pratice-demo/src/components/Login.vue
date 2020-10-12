<template>
  <div class="about">
    <h1>This is 登录 page</h1>
    <!--登录-->
    <div>
      <p>登录：</p><input v-model="loginUser" placeholder="请输入用户名">
      <p>密码：</p><input v-model="loginPassword" placeholder="请输入密码" type="password">
      <button @click="login">登录</button>
      <button type="button" @click="()=>{this.$router.push('/register')}">注册</button>
    </div>
  </div>
</template>
<script>
export default {
  name: 'login',
  data() {
    return {
      loginUser: '',
      loginPassword: '',
    };
  },
  methods: {
    login() {
      // eslint-disable-next-line max-len
      if (this.loginUser && this.loginPassword && this.loginUser === this.loginPassword && this.findUser(this.loginUser)) {
        sessionStorage.setItem('loginUserName', this.loginUser);
        this.$router.push({ name: 'articleList' });
      } else {
        alert('密码应该与用户名相同，如果用户不存在，请先注册!');
      }
    },
    findUser(username) {
      const usersStr = sessionStorage.getItem('users');
      if (!usersStr) {
        return false;
      }
      const users = JSON.parse(usersStr);
      for (let i = 0; i < users.length; i += 1) {
        if (username === users[i].username) {
          return true;
        }
      }
      return false;
    },
  },
};

</script>
