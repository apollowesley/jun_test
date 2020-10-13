<template>
  <div class="app bg-themeblue pt-5">
    <div class="container text-center fadeIn bg-themeblue">
      <div class="login-box">
        <div style="height:125px; line-height: 125px">
          <img src="/static/img/logos/logo-big.png">
        </div>
        <div class="text-muted mb-2">Login to Yeapp Admin</div>
        <form @submit.prevent="done" class="text-left" autocomplete="off">
          <div class="form-group">
            <input name="name" v-model="name" v-validate="'required|min:3'" title="邮箱/手机号" placeholder="邮箱/手机号" class="form-control form-control-line" autofocus>
            <span class="invalid-feedback">{{ errors.first('name') }}</span>
          </div>
          <div class="form-group relative">
            <input name="pass" v-model="pass" v-validate="'required|min:3'" title="密码" placeholder="密码" class="form-control form-control-line" type="password">
            <span class="invalid-feedback">{{ errors.first('pass') }}</span>
            <button class="btn btn-outline-primary rad-30" :class="{'loading': loading}" :disabled="errors.any() || loading">登 录</button>
          </div>
          <div class="clearfix mb-2">
            <label class="ui-checkbox float-left">
              <input type="checkbox" name="rememberMe"><i class="text-primary"></i> <span class="font-xs align-middle">记住我</span>
            </label>
            <span class="float-right">
              <a class="btn bg-facebook text-white rounded-circle btn-icon"><i class="fa fa-qq"></i></a>
              <a class="btn bg-googleplus text-white rounded-circle btn-icon"><i class="fa fa-wechat"></i></a>
            </span>
          </div>
        </form>
        <div class="clearfix">
          <a href="#/signup" class="float-left">注册</a>
          <a href="#/logout" class="float-right">忘记密码?</a>
        </div>
      </div>
      <div class="text-muted pt-2">v0.1</div>
    </div>
  </div>
</template>

<script>
  import {Auth} from '../resources'
  import toastr from '../misc/toastr.esm'

  export default {
    name: 'login',
    data: () => ({
      name: '',
      pass: '',
      rememberMe: false,
      loading: false
    }),
    beforeRouteEnter (to, from, next) {
      console.log('beforeRouteEnter', to)
      next()
    },
    methods: {
      done () {
        this.loading = true
        Auth.login(this.$data).then(response => {
          if (response.data.success) {
            return this.$router.replace(this.$route.query.redirect || '/')
          }
          toastr.warning(response.data.msg)
          this.loading = false
        }, response => {
          toastr.warning(response)
          this.loading = false
        })
      }
    },
    mounted () {
      document.body.classList.remove('preloader')
    }
  }
</script>

<style>
  .login-box {
    width: 380px;
    border-radius: 40px;
    box-shadow: 0 0 50px rgba(0, 0, 0, .2);
    padding: 30px;
    display: inline-block;
    background-color: #fff
  }
  .login-box button { position: absolute; right: 0; top: -8px }
  @media (max-width: 767px) {
    .login-box { width: 100% }
  }
</style>
