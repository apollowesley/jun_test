import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/index'
import Login from '@/components/user/login'
import Register from '@/components/user/register'
import ForgetPwd from '@/components/user/forgetPwd'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: '首页',
      component: Index
    },
    {
      path: '/login',
      name: '登录',
      component: Login
    },
    {
      path: '/register',
      name: '注册',
      component: Register
    },
    {
      path: '/forgetPwd',
      name: '修改密码',
      component: ForgetPwd 
    }
  ]
})
