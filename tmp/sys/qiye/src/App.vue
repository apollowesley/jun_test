<template>
    <div id="app" :style="'width:'+width+'px;height:'+height+'px'">
        <router-view />
    </div>
</template>
<script>
    import yc from 'yc-js';
    var Time=yc.Time;
    import Request from "./request/index.js";
    export default {
        data(){
            return{
                screen:{
                    width:0,
                    height:0,
                    mode:true
                }
            }
        },
        computed: {
            width(){
               return this.$store.getters.screen.width
            },
            height(){
               return  this.$store.getters.screen.height;
            }
        },
        mounted() {
            // console.log(this.$store.getters.screen)
            var that = this;
            // 监听窗口宽高变化
            (function screenListener() {
                // 动态获取宽高
                function width() {
                    return window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth
                };
                function height() {
                    return window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
                };
                that.$store.commit('screen', {
                    width: width(),
                    height: height()
                });
                that.screen = that.$store.getters.screen;
                window.addEventListener('resize', function() {
                    that.$store.commit('screen', {
                        width: width(),
                        height: height()
                    });
                    that.screen = that.$store.getters.screen;
                }, false);
            })()
            //每次刷新需要从缓存获取数据给store
            // 是否长期有效登录
            var longLogin= this.$store.getters.longLogin;
            this.$store.commit('longLogin',longLogin);
            // token 和用户信息
            var token =  this.$store.getters.token;
            var userInfo = this.$store.getters.userInfo;
            var nowTime=Time.getTime();
            if (token && token.token && token.expires && token.expires > nowTime) {
                // 每次刷新需要重新设置login信息
                this.$store.commit('login');
                this.$store.commit('token', token);
                this.$store.commit('userInfo', userInfo);
                    // console.log(token.expires-nowTime<300)
                //有效期少于600秒 请求更新token
                if(token.expires-nowTime<600){
                    //这里重置token
                    return Request.get('resetLogin')
                        .then((res) => {
                            var rt=res.data;
                            if (rt.code==200) {
                                this.$store.commit('login');
                                //家里保持长期登录环境
                                this.$store.commit('userInfo', rt.data.userInfo);
                                this.$store.commit('token', rt.data.token);
                            } else{
                                this.$store.commit('loginOut');
                            }
        
        
                        });
                }
            } else {
                this.$store.commit('loginOut');
            }
        },
        watch: {
            '$route'(to, from) {
                // 不需要登陆认证的路由地址pathname
                var safePathName={
                    'login':'login',
                    'login_login':'login_login',
                    'login_register':'login_register',
                    'index':'index',
                    'home':'home',
                    'list':'list',
                    'list_news':'list_news',
                    'list_about':'list_about',
                    'detail':'detail',
                    'detail_news':'detail_news'
                };
                // console.log(to.name);
                var safe=true;
                    if(safePathName[to.name]){
                        safe=false;
                    }

                if(!this.$store.getters.hasLogin && safe){
                     this.$confirm('当前页面需在登陆后浏览', '立即登陆?', {
                        confirmButtonText: '确定',
                        cancelButtonText: '去注册',
                        type: 'warning'
                    }).then(() => {
                        
                    }).catch(() => {
                        // 登陆成功返回上页
                        this.$router.push({path:from.path});
                    });
                    setTimeout(this.$router.push({name:'login'}),2000)
                    
                }
                let routersArr = yc.Storage.Session.get('routers') && yc.Storage.Session.get('routers').split(',') || [];
                if (routersArr.length == 0) {
                    routersArr.push(from.path)
                    routersArr.push(to.path);
                } else {
                    // console.log(routersArr.indexOf(to.path));
                    if (routersArr.indexOf(to.path) != -1) {
                        this.transitionName = 'slide-right'
                        routersArr.splice(routersArr.indexOf(to.path) + 1, 100)
                    } else {
                        this.transitionName = 'slide-left'
                        routersArr.push(to.path)
                    }
                }
                yc.Storage.Session.set('routers', routersArr.join(','))
            }
        }
    }
</script>
<style lang="scss">
    @import url("//at.alicdn.com/t/font_1076444_ju2xpxwlq1k.css");
    @import "./common/yc.css";
	html,body{
        margin: 0;
        padding: 0;
        height:100vh; 
		box-sizing: border-box;
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
		// background:#f60;
		width: 100%;
	}
    #app {
        height: 100vh;
        position: relative;
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
         background:#f60;
    }
    .body-content{
        position: relative;
        height: 100%;
        width: 100%;
    }
//     #nav {
//         // padding: 30px;
// 
//         a {
//             font-weight: bold;
//             color: #2c3e50;
// 
//             &.router-link-exact-active {
//                 color: #42b983;
//             }
//         }
//     }
</style>
