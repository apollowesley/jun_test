import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import menu from './modules/menu'
import resource from './modules/resource'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    menu,
    resource
  },
  getters
})

export default store