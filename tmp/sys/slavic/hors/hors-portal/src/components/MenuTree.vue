<template>
  <fragment>
    <template v-for="menu in data">
      <el-submenu
        :key="menu.path"
        :index="menu.path"
        v-if="menu.children && !menu.hidden"
      >
        <template slot="title">
          <i :class="menu.meta.icon"></i>
          <span>{{menu.meta ? menu.meta.title : menu.name}}</span>
        </template>
        <label>
          <menu-tree
            :data="menu.children"
            :basePath="menu.path"
          ></menu-tree>
        </label>
      </el-submenu>
      <el-menu-item
        :key="menu.path"
        v-if="!menu.children && !menu.hidden"
        :index=" basePath === '/' ? '/' +  menu.path : basePath + '/' + menu.path"
      >
        <i :class="menu.meta.icon"></i>
        <span slot="title">{{menu.meta ? menu.meta.title : menu.name}}</span>
      </el-menu-item>
    </template>
  </fragment>
</template>

<script>
import menuTree from "@/components/MenuTree.vue";
export default {
  name: "menuTree",
  data() {
    return {
    };
  },
  components: {
    menuTree: menuTree
  },
  props: {
    data: {
      type: Array,
      required: true
    },
    basePath: {
      type: String,
      default: ''
    }
  }
}
</script>