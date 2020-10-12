<template>
    <el-container class="el-container-body" >
        <header-nav :screenMode="screenMode" ></header-nav>
        <el-main>
            <router-view></router-view>
        </el-main>

        <el-footer style="display: flex;justify-content: center;">
            <footer-nav style="padding: 10px;"></footer-nav>
        </el-footer>
	</el-container>
</template>
 
 <style>
     .el-container-body{
         /* background: #00FF00; */
        padding: 0;
        margin: 0;
     }
 </style>
<script>
	import headerNav from "../../../components/public/header";
	import footerNav from "../../../components/public/footer";
  export default {
		components: {
			headerNav,
			footerNav
		},
    data() {
      return {
				
      };
    },computed:{
        screenMode(){
            return this.$store.getters.screen.mode;
        }
		},
    mounted() {
 
    },
    methods: {
    }
 
  }
</script>
 
 
<style>

</style>