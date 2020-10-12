<template>
  <div class="app-header">
    <div style="float:left;">
      <Button :icon="sliderCollapsed ? 'icon-align-right':'icon-align-left'" size="l" noBorder class="font20" @click="toggleSlider"></Button>
    </div>
    <div class="float-right app-header-info">
      <div class="app-header-icon-item" v-tooltip content="说明文档" theme="white" @click="goGithub">
        <i class="h-icon-help"></i>
      </div>
      <DropdownMenu className="app-header-dropdown" trigger="hover" offset="0 5" :width="150" placement="bottom-end" :datas="infoMenu" @onclick="trigger">
        <span>{{userName}}</span>
      </DropdownMenu>
    </div>
  </div>
</template>

<script>
import {mapActions, mapGetters, mapState} from 'vuex'
import Kernel from '../kernel/kernel'

export default {
  name: 'AppHeader',
  data() {
    return {
      infoMenu: [
        {key: 'info', title: '个人信息', icon: 'h-icon-user'},
        {key: 'logout', title: '退出登录', icon: 'h-icon-outbox'}
      ]
    }
  },
  computed: {
    ...mapState('common', ['user', 'sliderCollapsed']),
    ...mapGetters('common', ['userName'])
  },
  methods: {
    ...mapActions('common', ['toggleSlider']),
    goGithub() {

    },
    trigger(data) {
      if (data === 'logout') {
        this.$api.common.logout().then(() => {
          Kernel.clearToken()
          window.location.reload(true)
        })
      }
    }
  }
}
</script>

<style lang="less">
  .app-header {
    color: rgba(49, 58, 70, 0.8);

    .h-autocomplete {
      line-height: 1.5;
      float: left;
      margin-top: 15px;
      margin-right: 20px;
      width: 120px;

      &-show, &-show:hover, &-show.focusing {
        outline: none;
        box-shadow: none;
        border-color: transparent;
        border-radius: 0;
      }

      &-show.focusing {
        border-bottom: 1px solid #eee;
      }
    }

    &-info &-icon-item {
      cursor: pointer;
      display: inline-block;
      float: left;
      padding: 0 15px;
      height: @layout-header-height;
      line-height: @layout-header-height;
      margin-right: 10px;

      &:hover {
        background: @hover-background-color;
      }

      i {
        font-size: 18px;
      }

      a {
        color: inherit;
      }

      .h-badge {
        margin: 20px 0;
        display: block;
      }
    }

    &-dropdown {
      float: right;
      margin-left: 10px;
      padding: 0 20px 0 15px;

      .h-icon-down {
        right: 20px;
      }

      cursor: pointer;

      &:hover, &.h-pop-trigger {
        background: @hover-background-color;
      }

      &-dropdown {
        padding: 5px 0;

        .h-dropdownmenu-item {
          padding: 8px 20px;
        }
      }
    }

    &-menus {
      display: inline-block;
      vertical-align: top;

      > div {
        display: inline-block;
        font-size: 15px;
        padding: 0 25px;
        color: @dark-color;

        &:hover {
          color: @primary-color;
        }

        + div {
          margin-left: 5px;
        }

        &.h-tab-selected {
          color: @white-color;
          background-color: @primary-color;
        }
      }
    }
  }
</style>
