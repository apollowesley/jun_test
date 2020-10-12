/**
 *<p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : </li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年01月29日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(Router)

const routers = require.context('.', false, /.*\.js$/)
const menus = []
routers.keys().forEach(fileName => {
  if (fileName !== './index.js') {
    let mdName = fileName.substring(2, fileName.indexOf('.js'))
    routers(fileName).default.forEach(val => {
      val.path = `/${mdName}/` + val.path
      menus.push(val)
    })
  }
})

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '',
      name: 'Home',
      component: Home,
      meta: {title: '首页'}
    },
    ...menus
  ]
})

router.beforeEach((to, from, next) => {
  HeyUI.$LoadingBar.start()
  if (to.meta && to.meta.title) {
    document.title = to.meta.title + ' - 极速(Gson)'
  } else {
    document.title = '极速(Gson)'
  }
  next()
})

router.afterEach(() => {
  HeyUI.$LoadingBar.success()
  document.documentElement.scrollTop = 0
  document.body.scrollTop = 0
})

export default router
