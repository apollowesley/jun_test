const state = {
  resources: []
}

const mutations = {
  SET_RESOURCES: (state, resources) => {
    state.resources = resources
  }
}
const actions = {
  syncResources({
    commit
  }, resources) {
    return new Promise(resolve => {
      commit('SET_RESOURCES', resources)
      resolve(resources)
    })
  }
}

export default {
  namespaced: true,
  mutations,
  actions,
  state
}