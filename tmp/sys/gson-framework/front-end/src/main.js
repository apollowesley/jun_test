import './bootstrap'
import Vue from 'vue'
import App from './views/App'
import Login from './views/Login'
import LayoutContent from './components/LayoutContent'
import router from './router'
import store from './store'
import HeyUI from 'heyui'
import api from './api'

Vue.component(LayoutContent.name, LayoutContent)

Vue.use(HeyUI)
Vue.config.productionTip = false
Vue.prototype.$api = api

HeyUI.initDict({
  employeesType: {0: '无类型', 1: '全职', 2: '兼职', 3: '实习', 4: '劳务派遣', 5: '退休返聘', 6: '劳务外包'},
  employeesStatus: {0: '试用期', 1: '正式', 2: '离职'},
  gender: {0: '男', 1: '女'},
})

const AppInstance = new Vue({
  router,
  store,
  render: h => h(store.state.common.user.id ? App : Login)
})

store.dispatch('common/init').finally(() => AppInstance.$mount('#app'))
