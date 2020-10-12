import Vue from 'vue'
import VueRouter from 'vue-router'
import { isAuth } from "../common";

Vue.use(VueRouter)

let routes = [
    {
        name: '/',
        path: '/',
        meta: {
            requiresAuth: false,
            title: 'index',
        },
        component: (resolve) => {
            require(['../view/index.vue'], resolve)
        }
    }, {
        name: 'login',
        path: '/login',
        meta: { requiresAuth: false },
        component: (resolve) => {
            require(['../view/login.vue'], resolve)
        },
        beforeEnter: (to, from, next) => {
            if (isAuth()) {
                next('/index')
            }
            next();
        }
    }, {
        name: 'shell',
        path: '/shell',
        meta: { requiresAuth: false },
        component: (resolve) => {
            require(['../view/common/shell.vue'], resolve)
        },
        children: [
            {
                name: 'index',
                path: '/index',
                meta: {
                    requiresAuth: false,
                    title: '首页',
                    level:0
                },
                component: (resolve) => {
                    require(['../view/index/index'], resolve)
                }
            },
            {
                name: 'category',
                path: '/category',
                meta: {
                    requiresAuth: false,
                    title: '分类',
                    level:0
                },
                component: (resolve) => {
                    require(['../view/category/index'], resolve)
                }
            },
            {
                name: 'find',
                path: '/find',
                meta: {
                    requiresAuth: false,
                    title: '发现',
                    level:0
                },
                component: (resolve) => {
                    require(['../view/find/index'], resolve)
                }
            },
            {
                name: 'shopping',
                path: '/shopping',
                meta: {
                    requiresAuth: false,
                    title: '购物车',
                    level:0
                },
                component: (resolve) => {
                    require(['../view/shopping/index'], resolve)
                }
            },
            {
                name: 'center',
                path: '/center',
                meta: {
                    requiresAuth: true,
                    title: '个人中心',
                    level:0
                },
                component: (resolve) => {
                    require(['../view/center/index'], resolve)
                }
            },
            {
                name: 'good',
                path: '/good',
                meta: {
                    requiresAuth: false,
                    title: '商品详情',
                    level:1
                },
                component: (resolve) => {
                    require(['../view/common/good'], resolve)
                }
            }
        ]
    }]

export const router = new VueRouter({
    mode: 'history',
    routes
})
router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // this route requires auth, check if logged in
        // if not, redirect to login page.
        if (!isAuth()) {
            next({
                path: '/login',
                query: { redirect: to.fullPath }
            })
        } else {
            next()
        }
    } else {
        next() // 确保一定要调用 next()
    }
})