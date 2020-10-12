import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: 'home/ArticleList',
    name: 'ArticleList',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/ArticleList.vue')
  },
  {
    path: '/home/userCenter',
    name: 'userCenter',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/UserCenter.vue')
  },
  {
    path: '/kiwipeach',
    name: 'kiwipeach',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "kiwipeach" */ '../views/Kiwipeach.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
