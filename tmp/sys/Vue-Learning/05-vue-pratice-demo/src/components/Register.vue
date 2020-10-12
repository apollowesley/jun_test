<template>
  <div class="about">
    <h1>This is 注册 page</h1>
    <!--注册-->
    <div>
      <p>登录：</p><input v-model="username" placeholder="请输入用户名">
      <p>输入密码：</p><input v-model="password" placeholder="请输入密码" type="password">
      <p>确认密码：</p><input v-model="repeatPassword" placeholder="请输入密码" type="password">
      <button @click="register">注册</button>
    </div>
  </div>
</template>
<script>
export default {
  name: 'register',
  data() {
    return {
      username: '',
      password: '',
      repeatPassword: '',
    };
  },
  methods: {
    register() {
      if (this.findRegisterUser(this.username)) {
        alert('用户已经存在');
      }
      if (this.username && this.password && this.repeatPassword
        && this.password === this.repeatPassword
        && this.username === this.password) {
        const storeUsres = sessionStorage.getItem('users');
        const users = storeUsres ? JSON.parse(storeUsres) : [];
        const registerUser = { username: this.username, password: this.password };
        users.push(registerUser);
        sessionStorage.setItem('users', JSON.stringify(users));
        this.$router.push('/login');
      } else {
        alert('请确认用户名密码是否无误');
      }
    },
    findRegisterUser(username) {
      const usersStr = sessionStorage.getItem('users');
      if (!usersStr) {
        return false;
      }
      const users = JSON.parse(usersStr);
      for (let i = 0; i < users.length; i += 1) {
        if (username === users[i].loginUser) {
          return true;
        }
      }
      return false;
    },
  },

};

</script>
