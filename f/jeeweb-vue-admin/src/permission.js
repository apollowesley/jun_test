import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css'// progress bar style
import { getToken } from '@/utils/auth' // getToken from cookie

NProgress.configure({ showSpinner: false })// NProgress Configuration

const whiteList = ['/login', '/auth-redirect']// no redirect whitelist

router.beforeEach((to, from, next) => {
  NProgress.start() // start progress bar
  /* has no token*/
  if (to.meta && to.meta.requireAuth === false) { // 不需要登陆，直接进入
    next()
  } else {
    if (getToken()) { // determine if there has token
      /* has token*/
      if (to.path === '/login') {
        next({ path: '/' })
        NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
      } else {
        if (store.getters.permissions.length === 0) { // 判断当前用户是否已拉取完user_info信息
          store.dispatch('GetUserInfo').then(res => { // 拉取user_info
            // 加载数据字典
            store.dispatch('GetDicts').then(() => {
              console.log('数据字典加载成功...')
            })
            // 获取路由
            if (store.getters.fetchRoutes.length === 0) { // 判断是否获取路由
              store.dispatch('GetRoutes').then(() => {
                console.log(store.getters.addRouters)
                router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
                next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
              })
            }
            store.dispatch('GetPermissions').then(() => { // 获取权限
              // 没有动态改变权限的需求可直接next() 删除下方权限判断 ↓
              next()
            })
          }).catch(() => {
            store.dispatch('FedLogOut').then(() => {
              Message.error('Verification failed, please login again')
              next('/login') // 否则全部重定向到登录页
            })
          })
        } else {
          // 没有动态改变权限的需求可直接next() 删除下方权限判断 ↓
          next()
          /* if (hasPermission(store.getters.permissions, to.meta.permission)) {
            next()//
          } else {
            next({ path: '/401', replace: true, query: { noGoBack: true }})
          } */
          // 可删 ↑
        }
      }
    } else {
      /* has no token*/
      if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
        next()
      } else {
        next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
        NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it
      }
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})
