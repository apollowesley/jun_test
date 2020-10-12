import router from "../router";
import store from '../store'
import {
  routers
} from "@/router";

import {
  userResources
} from "@/api/portalUser";

import {
  getToken
} from "./token";
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
NProgress.configure({
  showSpinner: false
})

const whiteList = ["/login"];

router.beforeEach(async (to, from, next) => {
  NProgress.start()
  const token = getToken();
  if (token) {
    await userResources().then(res => {
      let menus = filterMenu(routers(), res.resources);
      store.dispatch("menu/generateMenus", menus);
      store.dispatch("resource/syncResources", res.resources);
    })
    if (to.path === "/login") {
      next({
        path: "/"
      });
    } else {
      if (to.meta && to.meta.resourceName && !store.getters.resources.some(resource => to.meta.resourceName == resource)) {
        if (to.meta && to.meta.resourceName && !store.getters.resources.some(resource => to.meta.resourceName == resource)) {
          next({
            path: "/401"
          });
        }
      }
      next();
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next();
    } else {
      next(`/login?redirect=${to.path}`);
    }
  }
});

router.afterEach(() => {
  NProgress.done()
});



function hasAuthority(route, resources) {
  if (route.meta && route.meta.resourceName) {
    return resources.some(role => route.meta.resourceName === role);
  } else {
    return true;
  }
}

function filterMenu(routes, resources) {
  const res = [];
  routes.forEach(route => {
    const tmp = {
      ...route
    };
    if (hasAuthority(tmp, resources)) {
      if (tmp.children) {
        tmp.children = filterMenu(tmp.children, resources);
      }
      res.push(tmp);
    }
  });
  return res;
}