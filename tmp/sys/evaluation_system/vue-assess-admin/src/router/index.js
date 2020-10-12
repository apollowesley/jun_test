import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '首页',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },

  {
    path: '/',
    component: Layout,
    redirect: '/user',
    name: '用户管理',
    hidden: true,
    meta: { title: '用户管理', icon: 'example' },
    children: [
      {
        path: 'user',
        name: '用户管理',
        component: () => import('@/views/user/index'),
        meta: { title: '用户管理', icon: 'table' }
      }
    ]
  },

  {
    path: '/',
    component: Layout,
    redirect: '/indicator',
    name: '指标管理',
    meta: { title: '指标管理', icon: 'example' },
    children: [
      {
        path: 'indicator',
        name: '指标观测点管理',
        component: () => import('@/views/indicator/index'),
        meta: { title: '指标观测点管理', icon: 'table' }
      },
      {
        path: 'indicatorGrade',
        name: '观测点分数管理',
        component: () => import('@/views/indicatorGrade/index'),
        meta: { title: '观测点分数管理', icon: 'table' }
      },
      {
        path: 'indicatorGradeReference',
        name: '观测点参考管理',
        component: () => import('@/views/indicatorGradeReference/index'),
        meta: { title: '观测点参考管理', icon: 'table' }
      }
    ]
  },
  {
    path: '/',
    component: Layout,
    redirect: '/profession',
    name: '专业管理',
    meta: { title: '专业管理', icon: 'example' },
    children: [
      {
        path: 'profession',
        name: '专业管理',
        component: () => import('@/views/profession/index'),
        meta: { title: '专业管理', icon: 'table' }
      },
      {
        path: 'indicationProfession',
        name: '专业指标关系',
        component: () => import('@/views/indicationProfessionRelation/indicationProfessionRelation'),
        meta: { title: '专业指标关系', icon: 'table' }
      },
      {
        path: 'struct',
        name: '结构关系',
        hidden: true,
        component: () => import('@/views/profession/struct'),
        meta: { title: '结构关系', icon: 'table' }
      },
      {
        path: 'professionGrade',
        name: '专业分数管理',
        component: () => import('@/views/professionGrade/professionGrade'),
        meta: { title: '专业分数管理', icon: 'table' }
      }
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
