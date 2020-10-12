<template>
  <div style="height: 100%; overflow:hidden">
    <el-drawer
      title="我是标题"
      :visible.sync="setting"
      :modal="false"
      size="15%"
      :with-header="false"
    >
      <span>我来啦!</span>
    </el-drawer>
    <div
      class="global-search-area"
      :hidden="globalSearchAreaHidden"
    >
      <el-row style="height:100%">
        <el-col
          style="height:100%"
          :offset="4"
          :span="16"
        >
          <input
            v-model="globalSearchData"
            class="global-search-input"
            type="text"
            placeholder="Search..."
          >
        </el-col>
        <el-col
          :span="4"
          align="right"
        >
          <i
            class="el-icon-close"
            style="margin-top: 60px;margin-right: 60px;font-size: 30px;cursor: pointer;"
            @click="showSearchArea"
          ></i>
        </el-col>
      </el-row>
    </div>
    <el-container
      style="height: 100%;transition: transform 0.5s"
      :class="globalSearch ? 'globalSearch' : ''"
    >
      <el-header class="header">
        <el-row>
          <el-col
            :span="19"
            style="text-align: left;line-height: 60px;"
          >
            <el-button
              v-if="!collapse"
              plain
              icon="el-icon-s-fold"
              size="small"
              @click="collapseMenu"
            ></el-button>
            <el-button
              v-if="collapse"
              plain
              icon="el-icon-s-unfold"
              size="small"
              @click="collapseMenu"
            ></el-button>
            hors-portal
          </el-col>
          <el-col
            :span="1"
            align="center"
            style="line-height:60px"
            @click.native="showSearchArea"
            class="button-area"
          >
            <i
              style="background: #d9ded400;border: none;color: #fff;"
              class="el-icon-search "
            ></i>
          </el-col>
          <el-col
            :span="1"
            align="center"
            style="line-height:60px"
            class="button-area"
            @click.native="screenfull.toggle()"
          >
            <i
              class="el-icon-full-screen "
              style="background: #d9ded400;border: none;color: #fff;"
            ></i>
          </el-col>
          <el-col
            :span="1"
            align="center"
            style="line-height:60px"
            class="button-area"
          >
            <i
              class="el-icon-chat-dot-square "
              style="background: #d9ded400;border: none;color: #fff;"
            ></i>
          </el-col>
          <el-col
            :span="1"
            align="center"
            style="line-height:60px"
            class="button-area"
            @click.native="setting = !setting"
          >
            <i
              class="el-icon-setting "
              style="background: #d9ded400;border: none;color: #fff;"
            ></i>
          </el-col>
          <el-col
            :span="1"
            align="center"
            style="line-height: 60px;"
          >
            <el-dropdown
              style="margin-top:10px"
              @command="handleCommand"
            >
              <span>
                <el-avatar
                  src="/hors/portal/user/avatar?id=1"
                  :size="40"
                ></el-avatar>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="home">
                  首页
                </el-dropdown-item>
                <el-dropdown-item command="center">个人中心</el-dropdown-item>
                <el-dropdown-item command="logout">
                  退出系统
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-col>
        </el-row>
      </el-header>
      <el-container style="height:calc(100% - 60px)">
        <el-aside
          style="box-shadow: rgba(44, 51, 73, 0.1) 0.5rem 0 1rem 0px;transition: width 0.4s;background-color:#dfe1e3;overflow-x: hidden"
          v-bind:style="{ width: widthData }"
        >
          <el-menu
            :default-active="$route.path"
            text-color="#222"
            active-text-color="#ff9900"
            background-color="#dfe1e3"
            style="text-align: left;"
            router
            :collapse="collapse"
          >
            <menu-tree :data="menus"></menu-tree>
          </el-menu>
        </el-aside>
        <el-main style="overflow-x: hidden">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { removeToken } from "@/utils/token";
import menuTree from "@/components/MenuTree.vue";
import screenfull from "screenfull";
export default {
  name: "AppMain",
  components: {
    menuTree: menuTree
  },
  data() {
    return {
      setting: false,
      collapse: false,
      globalSearch: false,
      globalSearchData: '',
      globalSearchAreaHidden: true,
      widthData: "200px",
      menus: this.$store.getters.menus,
      screenfull: screenfull
    }
  },
  methods: {
    showSearchArea() {
      this.globalSearch = !this.globalSearch
      this.globalSearchData = ''
      this.globalSearchAreaHidden = !this.globalSearchAreaHidden
    },
    logout() {
      this.$confirm("是否退出系统?", "退出提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info",
        showClose: false
      })
        .then(() => {
          removeToken();
          this.$router.push({ path: this.redirect || "/login" });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消退出"
          });
        });
    },
    collapseMenu() {
      this.collapse = !this.collapse;
      this.widthData = this.collapse ? "64px" : "200px";
    },
    handleCommand(tag) {
      if ("logout" === tag) {
        this.logout();
      } else if ("home" === tag) {
        if (this.$router.currentRoute.path !== "/home") {
          this.$router.push({ path: this.redirect || "/" });
        }
      } else if ("center" === tag) {
        if (this.$router.currentRoute.path !== "/center") {
          this.$router.push({ path: this.redirect || "/center" });
        }
      } else {
        this.$message.success("点击了" + tag);
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.el-menu {
  border-right: none;
}
.header {
  background: url(../assets/bg.gif);
  box-shadow: 0 0.5rem 1rem 0 rgba(44, 51, 73, 0.1);
  color: #ffffff;
  z-index: 10001;
}
.setting {
  position: absolute;
  transform: translateY(50vh);
  left: calc(100% - 70px);
  padding: 10px 0 10px 10px;
  z-index: 10000;
}
.setting:hover {
  background-color: rgba(143, 155, 179, 0.16);
  border-color: #8f9bb3;
  color: #8f9bb3;
}
.button-area:hover {
  background: #cccccc40;
  color: #ccc;
  cursor: pointer;
}
.globalSearch {
  transition: transform 0.5s cubic-bezier(0.2, 1, 0.3, 1);
  transform-origin: 50vw 50vh;
  transform: perspective(1000px) translate3d(0, 50vh, 0)
    rotate3d(1, 0, 0, 30deg);
  pointer-events: none;
}
.global-search-area {
  height: 50vh;
  position: absolute;
  width: 100%;
}
.global-search-input {
  top: 50%;
  transform: translateY(-50%);
  position: relative;
  font-size: 7vw !important;
  width: 100% !important;
  border-bottom: 1px solid #edf1f7 !important;
  color: #222b45 !important;
  font-family: Open Sans, sans-serif !important;
  font-weight: 700 !important;
  line-height: 3rem !important;
  border-top: 0 !important;
  border-right: 0 !important;
  border-left: 0 !important;
  background: transparent !important;
  border-radius: 0 !important;
  line-height: 1 !important;
  display: inline-block !important;
  box-sizing: border-box !important;
  padding: 0.05rem 0 !important;
  -webkit-appearance: none !important;
  overflow: visible !important;
}
.global-search-input:focus {
  outline: none;
}
</style>
