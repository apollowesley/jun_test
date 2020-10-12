const state = {
  menus: []
}

const mutations = {
  SET_MENUS: (state, menus) => {
    state.menus = menus
  }
}
const actions = {
  generateMenus({
    commit
  }, menus) {
    return new Promise(resolve => {
      commit('SET_MENUS', menus)
      resolve(menus)
    })
  }
}

export default {
  namespaced: true,
  mutations,
  actions,
  state
}