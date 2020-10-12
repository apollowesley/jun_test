<template>
    <div>
        <a href="javascript:;" @click='router("/register")'>注册</a>
        <a href="javascript:;" @click='router("/login")'>登录</a>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                
            }
        },
        methods: {
            /** 路由跳转 */
            router(url) {
                this.$router.push({path : url});
            }
        }
    }
</script>

