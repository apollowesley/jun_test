<template>
  <div class="app-settings d-none d-md-block">
    <button class="btn no-shadow" @click="toggle">
      <i class="fa fa-gear fa-spin"></i>
    </button>
    <div class="py-2 bb-l">设置</div>
    <div class="py-1 px-3 bg-light">菜单</div>
    <section class="pl-3">
      <div class="d-flex align-items-center bb-eee py-1 pr-3">折叠/展开菜单
        <label class="ui-switch ui-switch-sm ml-auto">
          <input type="checkbox" name="asideFolded" v-model="layout" value="aside-folded" @change="change"><i></i>
        </label>
      </div>
      <div class="d-flex align-items-center bb-eee py-1 pr-3">指向展开菜单
        <label class="ui-switch ui-switch-sm ml-auto">
          <input type="checkbox" name="asideHover" v-model="layout" value="aside-hover" @change="change"><i></i>
        </label>
      </div>
      <div class="d-flex align-items-center bb-eee py-1 pr-3">
        <span>固定菜单</span>
        <label class="ui-switch ui-switch-sm ml-auto">
          <input type="checkbox" name="asideFixed" v-model="layout" value="aside-fixed" @change="change"><i></i>
        </label>
      </div>
    </section>
    <div class="py-1 px-3 bg-light">其它</div>
    <section class="pl-3">
      <div class="d-flex align-items-center bb-eee py-1 pr-3">固定宽度布局
        <label class="ui-switch ui-switch-sm ml-auto">
          <input type="checkbox" name="layoutBoxed" v-model="layout" value="layout-boxed" @change="change"><i></i>
        </label>
      </div>
      <div class="d-flex align-items-center bb-eee py-1 pr-3">固定页头
        <label class="ui-switch ui-switch-sm ml-auto">
          <input type="checkbox" name="headerFixed" v-model="layout" value="header-fixed" @change="change"><i></i>
        </label>
      </div>
      <div class="d-flex align-items-center bb-eee py-1 pr-3">固定页脚
        <label class="ui-switch ui-switch-sm ml-auto">
          <input type="checkbox" name="footerFixed" v-model="layout" value="footer-fixed" @change="change"><i></i>
        </label>
      </div>
    </section>

    <div class="row no-gutters mt-2 px-2 theme-switcher">
      <div class="col-6 px-1">
        <label class="bg-themedark" :class="{'active': theme == 'theme-dark'}" title="Dark" @click="active"><i class="fa fa-check"></i></label>
      </div>
      <div class="col-6 px-1">
        <label class="bg-themewhite" :class="{'active': theme == 'theme-white'}" title="White" @click="active"><i class="fa fa-check"></i></label>
      </div>
      <div class="col-6 px-1">
        <label class="bg-themeblue" :class="{'active': theme == 'theme-blue'}" title="Blue" @click="active"><i class="fa fa-check"></i></label>
      </div>
      <div class="col-6 px-1">
        <label class="bg-themebrown" :class="{'active': theme == 'theme-brown'}" title="Brown" @click="active"><i class="fa fa-check"></i></label>
      </div>
    </div>

    <div class="text-center my-3">
      <button class="btn rounded-circle btn-secondary btn-sm" @click="reset">恢复默认设置</button>
    </div>
  </div>
</template>
<script>
  import storage from 'localStorage'

  export default {
    name: 'appSettings',
    data: () => ({
      layout: ['header-fixed'],
      theme: storage.theme || 'theme-dark'
    }),
    created () {
      // 非默认主题, 则进行切换
      if (this.theme) {
        document.getElementById('app-theme').setAttribute('href', `/static/css/${this.theme}.css`)
      } else {
        document.getElementById('app-theme').removeAttribute('href')
      }
      var stored = storage.getItem('layout')
      if (stored) {
        this.layout = JSON.parse(stored)
      }
      document.body.className = this.layout.join(' ')
    },
    methods: {
      // 切换主题颜色
      active (e, theme) {
        // 获取选中主题, 优先使用参数传递的主题

        if (!theme) {
          theme = 'theme-' + e.target.title.toLowerCase()
        }
        // 更改样式链接
        document.getElementById('app-theme').setAttribute('href', `/static/css/${theme}.css`)
        // 保存
        storage.setItem('theme', theme)
        this.theme = theme
      },
      // 切换布局
      change (e) {
        var value = e.target.value

        if (value === 'aside-hover') {
          var fixed = this.layout.indexOf('aside-fixed')
          console.log(fixed)
        }
        // 切换选中样式
        document.body.classList.toggle(value)
        // 保存布局
        storage.setItem('layout', JSON.stringify(this.layout))
      },
      reset () {
        // active default theme
        this.active(null, 'theme-blue')
        // remove all layout classes
        document.body.classList.remove('layout-boxed')
        document.body.classList.remove('footer-fixed')
        document.body.classList.remove('aside-folded')
        document.body.classList.remove('aside-hover')
        document.body.classList.remove('aside-fixed')
        document.body.classList.add('header-fixed')
        // change and store current layout
        this.layout = ['header-fixed']
        storage.setItem('layout', JSON.stringify(this.layout))
      },
      toggle () {
        this.$el.classList.toggle('app-settings-show')
      }
    }
  }
</script>

<style>
  .app-settings {
    box-shadow: 0 1px 1px rgba(0, 0, 0, .05);
    border: 1px solid #007aff;
    background-color: #fff;
    border-right-width: 0;
    transition: right .4s ease-in-out;
    position: fixed;
    width: 200px;
    z-index: 15;
    right: -200px;
    top: 51px
  }
  .app-settings > .btn {
    background: #fff !important;
    border-radius: 3px 0 0 3px;
    border-color: #007aff;
    border-right-width: 0;
    padding: 10px 15px;
    position: absolute;
    left: -45px;
    top: -1px
  }
  .app-settings.app-settings-show {
    right: 0;
  }
  .theme-switcher label {
    text-align: center;
    display: block
  }
  .theme-switcher i {
    visibility: hidden;
    line-height: 31px;
    color: #007aff
  }
  .theme-switcher .active i {
    visibility: visible
  }
</style>
