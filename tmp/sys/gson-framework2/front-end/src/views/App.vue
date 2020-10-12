<template>
  <Layout siderFixed :siderCollapsed="sliderCollapsed">
    <Sider theme="dark">
      <h1 class="layout-logo" v-if="!sliderCollapsed">极速(Gson)</h1>
      <h1 class="layout-logo" v-else>Gs</h1>
      <Menu :datas="menu" :inlineCollapsed="sliderCollapsed" @onclick="trigger" ref='menu'></Menu>
    </Sider>
    <Layout headerFixed>
      <HHeader theme="white">
        <AppHeader/>
      </HHeader>
      <Breadcrumb :datas="datas"></Breadcrumb>
      <Content class="layout-content" :style="{minHeight:minHeight+'px'}">
        <router-view/>
      </Content>
      <HFooter class="layout-footer">
        Copyright © 2019 <a href="https://www.gson.cn" target="_blank"> 极速(Gson) </a>
      </HFooter>
    </Layout>
  </Layout>
</template>

<script>
import {mapState} from 'vuex'
import AppHeader from '../components/AppHeader'

const BaseBreadcrumb = [{title: '首页', route: {name: 'Home'}}]

export default {
  components: {AppHeader},
  computed: {
    ...mapState('common', ['sliderCollapsed', 'menu']),
    minHeight() {
      return this.innerHeight - 166
    }
  },
  data() {
    return {
      innerHeight: window.innerHeight,
      datas: BaseBreadcrumb
    }
  },
  methods: {
    menuSelect() {
      if (this.$route.name) {
        this.$refs.menu.select(this.$route.name)
      }

      let breadcrumb = [].concat(BaseBreadcrumb)
      let {group, title} = this.$route.meta
      if (group) {
        breadcrumb.push({title: group})
      }
      if (this.$route.name != 'Home') {
        breadcrumb.push({title: title, route: {name: this.$route.name}})
      }
      this.datas = breadcrumb
    },
    trigger(data) {
      this.$router.push({name: data.key})
    }
  },
  watch: {
    $route() {
      this.menuSelect()
    }
  },
  mounted() {
    this.menuSelect()
    this.$nextTick(() => {
      window.addEventListener('resize', () => {
        this.innerHeight = window.innerHeight
      })
    })
  }
}
</script>
<style scoped lang="less">
  .h-breadcrumb {
    padding: 10px;
  }

  .layout-content {
    background-color: #fff;
  }
</style>