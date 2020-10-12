import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/device',
            meta:{requiresAuth: true}
        },
        {
            path: '/',
            component: resolve => require(['../components/common/Home.vue'], resolve),
            meta: { title: '自述文件', requiresAuth: true},
            children:[
                {
                    path: '/dashboard',
                    component: resolve => require(['../components/page/Dashboard.vue'], resolve),
                    meta: { title: '系统首页' ,requiresAuth: true}
                },
                {
                    path: '/device',
                    component: resolve => require(['../components/page/Device.vue'], resolve),
                    meta: { title: '设备',requiresAuth: true }
                },
                {
                    path: '/executor',
                    component: resolve => require(['../components/page/Executor.vue'], resolve),
                    meta: { title: 'executor',requiresAuth: true }
                },
                {
                    path: '/tabs',
                    component: resolve => require(['../components/page/Tabs.vue'], resolve),
                    meta: { title: '消息' ,requiresAuth: true}
                },
                {
                    path: '/404',
                    component: resolve => require(['../components/page/404.vue'], resolve),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: resolve => require(['../components/page/403.vue'], resolve),
                    meta: { title: '403' }
                }
            ]
        },
        {
            path: '/login',
            component: resolve => require(['../components/page/Login.vue'], resolve)
        },
        {
            path:'/register',
            component:resolve => require(['../components/page/Register.vue'],resolve)
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
})
