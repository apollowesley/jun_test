import Vue from 'vue'
import App from './App'
import config from'./config/index.js'
import store from './store'

Vue.config.productionTip = false
Vue.prototype.$store = store
Vue.prototype.$config = config
App.mpType = 'app'

const app = new Vue({
    store,
    ...App
})
app.$mount()
