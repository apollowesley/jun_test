<template>
    <div class="wrapper">
        <v-head></v-head>
        <v-sidebar></v-sidebar>
        <div class="content-box" :class="{'content-collapse':collapse}">
            <v-tags></v-tags>
            <div class="content">
                <transition name="move" mode="out-in">
                    <keep-alive :include="tagsList">
                        <router-view></router-view>
                    </keep-alive>
                </transition>
               
            </div>

        </div>
         
    </div>
</template>

<script>
    import VueQr from 'vue-qr'
    import vHead from './Header.vue';
    import vSidebar from './Sidebar.vue';
    import vTags from './Tags.vue';
    import bus from './bus';
     import {getToken,removeToken} from '@/utils/auth'
    import {getMsg} from '@/api/user'
    import {mapMutations,mapGetters} from 'vuex'
    export default {
        data(){
            return {
                tagsList: [],
                collapse: false,
                
            }
        },
         mounted(){
                this.getMsg()
        },
        computed:{
            ...mapGetters([
                'msgCount'
            ])
        },
        methods:{
            getMsg(){
                getMsg('user','countUserUnreadMsg')
                .then(res=>{
                    if(res.code==200){
                        this.setMsgCount(res.result)
                    }
                    if(res.code==10006){
                            removeToken()
                            this.$router.push('/login')
                    }
                })
            },
            ...mapMutations({
                setMsgCount:'SET_MSGCOUNT'
            })
        },
        components:{
            vHead, vSidebar, vTags
        },
        created(){
            bus.$on('collapse', msg => {
                this.collapse = msg;
            })

            // 只有在标签页列表里的页面才使用keep-alive，即关闭标签之后就不保存到内存中了。
            bus.$on('tags', msg => {
                let arr = [];
                for(let i = 0, len = msg.length; i < len; i ++){
                    msg[i].name && arr.push(msg[i].name);
                }
                this.tagsList = arr;
            })
        }
    }
</script>

