import Vue from 'vue';
import App from './App';
import router from './router';
import axios from 'axios';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';    // 默认主题
// import '../static/css/theme-green/index.css';       // 浅绿色主题
import '../static/css/icon.css';
import "babel-polyfill";
import store from './store'
import {getToken} from './utils/auth'
Vue.use(ElementUI, { size: 'small' });
Vue.prototype.$axios = axios;

//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth) {  // 判断该路由是否需要登录权限
      if (getToken()) { 
          next();
      }
      else {
        console.log('mei')
          next({
              path: '/login',
              query: {redirect: to.fullPath}  // 将跳转的路由path作为参数，登录成功后跳转到该路由
          })
      }
  }
  else {
      next();
  }
})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');