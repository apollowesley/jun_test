<template>
                    <router-view></router-view>

</template>

<script>
//     
    export default{
        computed:{
            onRoutes(){
                return this.$router.path.replace(" ",'');
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
