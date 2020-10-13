import { constantRouterMap, processRouter } from '@/router'
import { fetchMenuList } from '@/api/menu'
import { fetchPermissionList } from '@/api/permission'

const permission = {
  state: {
    routers: constantRouterMap,
    fetchRoutes: [],
    addRouters: [],
    permissions: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    },
    SET_FETCHROUTES: (state, routers) => {
      state.fetchRoutes = routers
    }
  },
  actions: {
    // 获取未认证路由
    GetRoutes({ commit, state }) {
      return new Promise((resolve, reject) => {
        fetchMenuList(state.token).then(response => {
          if (state.fetchRoutes.length === 0) {
            commit('SET_FETCHROUTES', response.data)
            const accessedRouters = processRouter(response.data)
            commit('SET_ROUTERS', accessedRouters)
          }
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 获取权限
    GetPermissions({ commit, state }) {
      return new Promise((resolve, reject) => {
        fetchPermissionList(state.token).then(response => {
          const permissions = response.data
          commit('SET_PERMISSIONS', permissions)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    }
  }
}

export default permission
