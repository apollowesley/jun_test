import Vue from 'vue'
import VeeValidate from 'vee-validate/dist/vee-validate.min'
import validators from './misc/validators'
import directives from './misc/directives'
import messages from './misc/vee-validate-messages'
import filters from './misc/filters'
import toastr from './misc/toastr.esm'
import router from './routers'
import {dropdownEvent} from './misc/utils'
// import stylesheets
import 'jstree/dist/themes/default/style.min.css'
import 'font-awesome/css/font-awesome.min.css'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'tb-icons/lib/styles/tb-icons.css'
import '../static/themify-icons/themify-icons.css'
import '../static/css/plugins.css'
import '../static/css/style.css'
// import javascripts
import 'sweetalert/dist/sweetalert.min'
import 'jstree/dist/jstree.min'

// register custom validators
Object.keys(validators).forEach(v => VeeValidate.Validator.extend(v, validators[v]))

// register custom directives
Object.keys(directives).forEach(d => Vue.directive(d, directives[d]))

// register custom filters
Object.keys(filters).forEach(d => Vue.filter(d, filters[d]))

// register custom global utility functions
// Object.assign(Vue.prototype, utils)

// register global toastr as `notice`
Vue.prototype.$notice = toastr

dropdownEvent()

Vue.use(VeeValidate, {
  locale: 'zhCN',
  dictionary: {
    zhCN: {messages}  // 注册VeeValidate中文提示
  },
  classes: true,
  classNames: {
    touched: 'touched', // the control has been blurred
    untouched: 'untouched', // the control hasn't been blurred
    valid: 'is-valid', // model is valid
    invalid: 'is-invalid', // model is invalid
    pristine: 'pristine', // control has not been interacted with
    dirty: 'dirty' // control has been interacted with
  }
})

new Vue({
  template: '<router-view id="app"></router-view>',
  router
}).$mount('#app')
