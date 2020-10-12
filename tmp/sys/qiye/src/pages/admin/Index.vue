<template>
        <el-container  style="height:100vh; border: 1px solid #eee" class="user-body">
            <el-header style="height:70px;">
                <v-head @setCollapse="setCollapse"></v-head>
            </el-header>

            <el-container>
                <el-aside :width="collapse?'auto':'auto'" style="background-color: rgb(238, 241, 246)">
                    <v-sidebar :collapse="collapse" style="height: 100%;"></v-sidebar>
                </el-aside>

                <el-main>
                    <v-tags></v-tags>
                    <transition name="move" mode="out-in">
                        <!-- 缓存页面 防止重复渲染DOM -->
                        <keep-alive :include="tagsList">
                            <router-view></router-view>
                        </keep-alive>
                    </transition>
                </el-main>
            </el-container>
        </el-container>
</template>
<script>
    import vHead from './Header.vue';
    import vSidebar from './Sidebar.vue';
    import vTags from './Tags.vue';
    // import bus from './bus';
    export default {
        data() {
            return {
                tagsList: [],
                collapse: true
            }
        },
        components: {
            vHead,
            vSidebar,
            vTags
        },
        created() {

        },
        methods: {
            setCollapse(e) {
                this.collapse = !this.collapse;
            }
        }
    }
</script>
<style lang="scss">
.user-body{
    .el-header,
    .el-footer {
      background-color: #B3C0D1;
      color: #333;
      text-align: center;
      line-height: 60px;
    }

    .el-aside {
      background-color: #D3DCE6;
      color: #333;
      text-align: center;
    }

    .el-main {
      background-color: #E9EEF3;
      color: #333;
      text-align: center;
    }
    .content-collapse {
        left: 65px;
    }
}



</style>
