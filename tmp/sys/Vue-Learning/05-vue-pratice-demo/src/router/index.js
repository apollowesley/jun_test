import Vue from 'vue';
import VueRouter from 'vue-router';
// 这里的 '../' 可以使用'@'来代替，因为它等价于'/src'目录
import Index from '../views/Index.vue';
import Login from '../components/Login.vue';
import ArticalSend from '../views/ArticalSend.vue';
// import ArticleList from '../views/ArticleList.vue';

// 懒加载
const UserCenter = () => import(/* webpackChunkName: "userCenter" */ '../views/UserCenter.vue');
const ArticleList = () => import(/* webpackChunkName: "articleList" */ '../views/ArticleList.vue');
const Register = () => import(/* webpackChunkName: "articleList" */ '../components/Register.vue');
const ArticleDetailInfo = () => import(/* webpackChunkName: "articleDetailInfo" */ '../components/ArticleDetailInfo.vue');

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'login',
    component: Login,
  }, {
    path: '/login',
    name: 'login',
    component: Login,
  }, {
    path: '/register',
    name: 'register',
    component: Register,
  },
  {
    path: '/index',
    name: 'index',
    component: Index,
    redirect: '/index/articleList', // 默认显示子路由
    children: [
      {
        path: '/index/userCenter', // 注意自路由的路径参数中不要携带 '/',否则路由会从根路径开始查找
        name: 'userCenter',
        component: UserCenter,
      }, {
        path: '/index/articleList',
        name: 'articleList',
        component: ArticleList,
      },
    ],
  }, {
    path: '/articalSend',
    name: 'articalSend',
    component: ArticalSend,
  }, {
    path: '/articleDetailInfo',
    name: 'articleDetailInfo',
    component: ArticleDetailInfo,
  },

];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  linkActiveClass: 'active',
  routes,
});

export default router;
