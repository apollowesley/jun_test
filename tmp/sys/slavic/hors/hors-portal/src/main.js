import Vue from "vue";
import App from "./App";
import router from "./router";
import store from './store'
import '../src/utils/authority';
import '../src/utils/util';
import Fragment from 'vue-fragment'
Vue.use(Fragment.Plugin)
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
import CollapseTransition from 'element-ui/lib/transitions/collapse-transition';
import locale from 'element-ui/lib/locale/lang/zh-CN'
import "@/styles/index.scss";
import animated from 'animate.css'
Vue.use(animated)

Vue.config.productionTip = false;
Vue.use(ElementUI, {
  locale
})
Vue.component(CollapseTransition.name, CollapseTransition)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");