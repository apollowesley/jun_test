import Vue from 'vue'
import App from './App.vue'
import 'vx-easyui/dist/themes/default/easyui.css';
// import 'vx-easyui/dist/themes/material-blue/easyui.css';
// import 'vx-easyui/dist/themes/bootstrap/easyui.css';
import 'vx-easyui/dist/themes/icon.css';
import 'vx-easyui/dist/themes/vue.css';
import 'font-awesome/css/font-awesome.min.css'
import router from './router/index';

import EasyUI from 'vx-easyui';

Vue.use(EasyUI);

Vue.config.productionTip = false

new Vue({
  router,	//路由注册
  render: h => h(App),
}).$mount('#app')
