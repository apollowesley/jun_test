import Vue from 'vue';
import VueRouter from 'vue-router'
Vue.use(VueRouter);
import routerConfig from './router';
var router= new VueRouter(routerConfig);

import store from './store/';
import App from './App.vue';
import ELEMENT from './element/index.js';
import config from './config.js';
// import './common/element-variables.scss';
// import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ELEMENT);
import echarts from 'echarts';
Vue.prototype.$echarts = echarts;
Vue.prototype.$config = config;
Vue.config.productionTip = false;
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
