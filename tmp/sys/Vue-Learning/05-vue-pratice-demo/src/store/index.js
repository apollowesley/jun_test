import Vue from 'vue';
import Vuex from 'vuex';
import moment from 'moment';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    articleList: [],
  },
  mutations: {
    addItem(state, value) {
      // eslint-disable-next-line no-param-reassign
      value.time = moment(value.time).format('YYYY-MM-DD HH:mm');
      state.articleList.push(value);
    },
    removeItem(state, value) {
      state.articleList.splice(value, 1);
    },
  },
  actions: {
  },
  modules: {
  },
});
