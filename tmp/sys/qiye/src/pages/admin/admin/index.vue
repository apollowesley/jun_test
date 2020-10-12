<template>
  <router-view />
</template>

<script>
//     
    export default{
        computed:{
            onRoutes(){
                return this.$route.path.replace(" ",'');
            }
        },
        data(){
            return {
            }
        },mounted() {
        	
        },methods:{

        }
    }
</script>

<style>
    /* @import url("style.css"); */
</style>
