import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

const routes = [{
    path: "/login",
    hidden: true,
    component: () => import("@/views/login.vue")
  },
  {
    path: "/404",
    component: () => import("@/views/notFound/index.vue"),
    hidden: true
  },
  {
    path: "/401",
    component: () => import("@/views/noAuthority"),
    hidden: true
  },
  {
    path: "/",
    name: "AppMain",
    redirect: "/home",
    meta: {
      title: "首页",
      icon: "el-icon-s-home"
    },
    hidden: true,
    component: () => import("@/views/AppMain.vue"),
    children: [{
      path: "home",
      name: "home",
      meta: {
        title: "首页面",
        icon: "el-icon-s-home"
      },
      component: () => import("@/views/home/index.vue")
    }, {
      path: "center",
      name: "center",
      meta: {
        title: "个人中心",
        icon: "el-icon-s-home"
      },
      component: () => import("@/views/portal/center")
    }]
  },
  {
    path: "/portal",
    meta: {
      title: "系统管理",
      icon: "el-icon-setting",
      resourceName: "PORTAL_MANAGER"
    },
    component: () => import("@/views/AppMain.vue"),
    children: [{
        path: "user",
        name: "user",
        meta: {
          title: "系统用户",
          icon: "el-icon-user",
          resourceName: "PORTAL_MANAGER_USER_MENU"
        },
        component: () => import("@/views/portal/user/index.vue")
      },
      {
        path: "role",
        meta: {
          title: "系统角色",
          icon: "el-icon-s-check",
          resourceName: "PORTAL_MANAGER_ROLE_MENU"
        },
        component: () => import("@/views/portal/role")
      },
      {
        path: "resource",
        meta: {
          title: "系统资源",
          icon: "el-icon-menu",
          resourceName: "PORTAL_MANAGER_RESOURCE_MENU"
        },
        component: () => import("@/views/portal/resource")
      }
    ]
  },
  {
    path: "/order",
    meta: {
      title: "订单管理",
      icon: "el-icon-s-order"
    },
    component: () => import("@/views/AppMain.vue"),
    children: [{
        path: "list",
        meta: {
          title: "订单列表",
          icon: "el-icon-s-data"
        },
        component: () => import("@/views/order/list")
      },
      {
        path: "add",
        meta: {
          title: "添加订单",
          icon: "el-icon-circle-plus"
        },
        component: () => import("@/views/order/add")
      }
    ]
  },
  {
    path: "*",
    redirect: "/404",
    hidden: true
  }
];

const createRouter = () =>
  new Router({
    mode: "hash",
    scrollBehavior: () => ({
      y: 0
    }),
    routes: routes
  });

const router = createRouter();
export default router;

export function routers() {
  return routes;
}