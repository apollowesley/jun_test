<template>
  <nav class="app-header navbar navbar-toggleable-sm">

    <a class="nav-item d-md-none" title="拉出菜单" @click="pushAside"><i class="fa fa-bars"></i></a>
    <a class="nav-item d-none d-md-block" title="展开菜单" @click="toggleAside"><i class="fa fa-bars"></i></a>

    <div class="dropdown static">
      <a class="nav-item" data-toggle="dropdown" v-ripple>Menu</a>
      <div class="dropdown-menu m-0 p-3" style="width: 99%">
        <div class="row">
          <div class="col-2 text-center">
            <img :src="user.avatar" class="rounded-circle thumb-lg">
            <div class="h4 m-0">{{user.name}}</div>
            <small class="badge badge-default">{{user.roleName}}</small>
          </div>
          <div class="col-4">
            <div class="font-weight-bold">Media <span class="badge badge-pill badge-success">10</span></div>
            <div class="row row-gap-5">
              <div class="col-6 col-md-3" v-for="item in 4">
                <img :src="'/static/img/london.jpg'" class="img-fluid">
              </div>
            </div>
          </div>
          <div class="col-3">
            <div class="font-weight-bold">系统工具 <span class="badge badge-pill badge-primary">12</span></div>
            <div class="row">
              <div class="col-md-4">
                <div><a href="/reload_config" rel="request"><i class="fa fa-angle-right"></i>重载配置</a></div>
                <div><a href="/clean_cache" rel="request"><i class="fa fa-angle-right"></i>清空缓存</a></div>
                <div><a href="#/html/ui.tabs-accordions.html"><i class="fa fa-angle-right"></i>Tabs</a></div>
              </div>
              <div class="col-md-8">
                <div><a href="#/html/ui.buttons.html"><i class="fa fa-angle-right"></i>Buttons</a></div>
                <div><a href="#/html/ui.font-awesome-icons.html"><i class="fa fa-angle-right"></i>FontAwesome Icons <span class="badge badge-pill badge-warning">500+</span></a></div>
                <div><a href="#/html/ui.themify-icons.html"><i class="fa fa-angle-right"></i>Themify Icons <span class="badge badge-pill badge-warning">300+</span></a></div>
              </div>
            </div>
          </div>
          <div class="col-3">
            <div class="card bg-twitter text-center b-0 p-3">
              <i class="fa fa-twitter fa-2x text-white"></i>
              <div class="text-white font-sm">
                "Your time is limited, so don't waste it living someone else's life."
                <div class="text-right font-xs text-italic">- Steve Travis</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <a class="nav-item" title="打开搜索" @click="toggleSearch" v-ripple><i class="ti-search"></i></a>

    <div class="dropdown ml-auto">
      <a class="nav-item" data-toggle="dropdown">
        <span class="badge badge-pill badge-primary absolute r-0 t-0">3</span>
        <i class="fa fa-comment-o font-lg"></i>
        <span class="d-none d-md-inline-block">Messages</span>
      </a>
      <div class="dropdown-menu dropdown-menu-right m-0" style="width: 350px">
        <div class="px-3">MESSAGES <a class="float-right"><i class="fa fa-check"></i> all read</a></div>
        <ul class="list-unstyled scrollable with-image font-xs">
          <li v-for="item in 3">
            <img src="/static/img/avatar.svg" class="thumb-xxs rounded-circle">
            <div>
              <p class="m-0"><strong>Jesse Holmes</strong> <span class="float-right time font-xs">Now</span></p>
              <p class="m-0">Pork chop brisket corned</p>
            </div>
          </li>
        </ul>
        <div class="text-center"><a><i class="fa fa-eye"></i> view all</a></div>
      </div>
    </div>

    <div class="dropdown">
      <a class="nav-item" data-toggle="dropdown">
        <span class="d-inline-block relative">
          <img :src="user.avatar" class="rounded-circle thumb-xxs">
          <i class="network on bottom"></i>
        </span>
        <span class="d-none d-md-inline-block ml-1">{{user.name}} <i class="fa fa-angle-down text-primary"></i></span>
      </a>
      <div class="dropdown-menu dropdown-menu-right pt-0 m-0">
        <div class="dropdown-item bg-light bb-eee mb-2 p-3">
          <div class="mb-2">资料完整度100%</div>
          <div class="progress">
            <div class="progress-bar progress-xs w-100"></div>
          </div>
        </div>
        <router-link class="dropdown-item py-2" to="/user/profile"><i class="fa fa-edit"></i> 个人资料</router-link>
        <div class="dropdown-item py-2" @click="toggleSettings">
          <i class="fa fa-cog"></i> 设置<span class="badge badge-pill badge-default float-right">new</span>
        </div>
        <div class="dropdown-divider"></div>
        <router-link class="dropdown-item py-2" to="/logout"><i class="fa fa-sign-out"></i> 退出登录</router-link>
      </div>
    </div>

    <a class="nav-item" @click="toggleQuickview" title="打开右边栏" v-ripple>
      <i class="fa fa-ellipsis-v"></i>
    </a>
  </nav>

</template>

<script>
  import {User} from '../../resources'

  function hander (e) {
    var left = e.pageX || e.clientX
    if (left > 220) {
      document.body.classList.remove('aside-pushed')
      document.querySelector('#app').removeEventListener('click', hander, true)
    }
  }

  export default {
    data: () => ({
      user: User.current()
    }),
    methods: {
      toggleQuickview: () => document.querySelector('.app-quickview').classList.toggle('app-quickview-show'),
      toggleSettings: () => document.querySelector('.app-settings').classList.toggle('app-settings-show'),
      toggleSearch: () => document.querySelector('.app-search').classList.toggle('app-search-show'),
      toggleAside: () => document.body.classList.toggle('aside-folded'),
      pushAside () {
        var pushed = document.body.className.indexOf('aside-pushed')
        if (pushed === -1) {
          document.querySelector('#app').addEventListener('click', hander)
          document.body.classList.add('aside-pushed')
        } else {
          document.querySelector('#app').removeEventListener('click', hander)
          document.body.classList.remove('aside-pushed')
        }
      }
    }
  }
</script>
