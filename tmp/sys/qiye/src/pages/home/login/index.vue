<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">
        {{ title }}
      </div>

      <router-view />
      <div class="login-tips" style="border-top: 1px solid #B3C0D1;display: flex;justify-content:space-around;">
        <div
          v-for="(item,key) in menu" :key="key" @click="handleClick(item.name)" style="flex:1" >
          {{ item.title }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>

    export default{
        components:{

        },
        computed:{
            onRoutes(){
                return this.$route.path.replace(" ",'');
            },
           title(){
               var title='';
               switch (this.on){
               	case 'login_login':
                title="登陆"
               		break;
                case 'login_register':
                title="注册"
                	break;
               	default:
                title="登录"
               		break;
               }
               return '会员【'+title+'】';
           } 
        },
        data(){
            return {
                on:'',
                menu: [
                    {
                        icon: 'el-icon-caret-left',
                		name:'login_login',
                        title: '登陆'
                    },{
                        icon: 'el-icon-caret-left',
                		name:'login_register',
                        title: '注册'
                    },
                 ]
            }
        },mounted() {
        	
        },methods:{
            handleClick(e){
                this.on=e;
                this.$router.push({ name: e})
            }
        }
    }
</script>

<style lang="scss" >
    .login-wrap{
    	display: flex;
    	justify-content: center;
    	align-item: center;
        background: #f60;
    	flex-direction: column;
        width:100vw;
        height:100vh;
        background-size: 100%;
    }
    .el-button-tip{
        width: 100px;
    }
    .ms-title{
        width:100%;
        line-height: 50px;
        text-align: center;
        font-size:20px;
        color: #fff;
        border-bottom: 1px solid #ddd;
    }
    .ms-login{
    	align-self: center;
        width:350px;
        border-radius: 5px;
        background: rgba(255,255,255, 0.3);
        overflow: hidden;
    }
    .ms-content{
        padding: 30px 30px;
    }
    .login-btn{
        text-align: center;
    }

    .login-tips{
        font-size:12px;
        line-height:30px;
        color:#fff;
    }
</style>
