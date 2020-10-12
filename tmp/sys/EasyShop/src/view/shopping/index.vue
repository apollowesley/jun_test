<template>
    <div class="shopping">
        <m-loginbar :options="loginConfig"></m-loginbar>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                loginConfig: {
                    tip: '登录后可同步账户购物车中的商品',
                    handler: () => this.loginHandler()
                }
            }
        },
        methods: {
            loginHandler() {
                this.goto('/login');
            }
        }
    }
</script>

<style>

</style>
