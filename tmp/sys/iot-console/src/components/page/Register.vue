<template>
    <div class="login">
        <div class="login-wrapper">
            <div class="login-header">
               <h1>BozaiAdmin</h1> 
            </div>
            <div class="login-main">
                <p class="msg" v-if=msg.length>{{msg}}</p>
                <div class="ipt-box">
                    <span class="text">手机号：</span>
                    <el-input class="input" v-model="phone" placeholder="请输入手机号"
                    @blur="verifyPhone"
                    ></el-input>
                </div>
                <div class="ipt-box">
                    <span class="text">密码：</span>
                    <el-input class="input" v-model="password" placeholder="请输入密码" type='password'></el-input>
                </div>
                <div class="ipt-box">
                    <span class="text">验证码:</span>
                    <el-input class="set-yz" v-model="yzm" placeholder="请输入验证码" type='text'></el-input>
                    <el-button class="get-yz"  type="primary" @click=sendYzm>发送验证码</el-button>
                </div>
                
            </div>
            <div class="login-footer">
                  <el-button class="go"  type="primary" @click=register>注册</el-button>
            </div>
        </div>
        
    </div>
</template>
<script>
import {setToken} from '@/utils/auth'
import {checkMobile} from '@/utils/test'
import {yzm,register} from '@/api/user'
export default {
    data(){
        return {
            phone:"",
            password:"",
            yzm:"",
            msg:''
        }
    },
    methods:{
        sendYzm(){
            yzm(this.phone)
            .then(res=>{
                
                if(res.code!==200){
                    if(res.code===40006){
                        this.$message.error(res.msg+'3秒后跳转登陆页');
                        setTimeout(()=>{
                            this.$router.push('/login')
                        },3000)
                    }
                    this.$message.error(res.msg)
                }
            })
        },
        register(){
            register(this.phone,this.password,this.yzm)
            .then(res=>{
                if(res.code===200){
                     if(res.code==200&&res.result){
                         setToken(res.result)
                     }
                    this.$router.push('/')
                }
            })
        },
        verifyPhone(){
           checkMobile(this.phone)
           .then(resolve=>{
               this.msg = ''
           })
           .catch(reject=>{
               this.msg = reject
           })
        }
    }
}
</script>
<style lang='stylus' scoped>
    .login{
        position relative
        width 100%
        height 100%
        background url('../../assets/login-bg.jpg') no-repeat center
        background-size 100% 100%
        .login-wrapper{
            position absolute
            top 50% 
            left 50% 
            width: 350px;
            padding 0 20px;
            padding-bottom 20px
            transform translate(-50%,-60%)   
            border-radius 10px;
            background: hsla(0,0%,100%,.3);
            .login-header{
                padding 10px 0;
                text-align center;
                border-bottom 1px solid gray
            }
            .login-main{
                margin-top 30px
                .msg{
                    font-weight 700
                    color #ff6700
                }
                .ipt-box{
                    margin-top 20px 
                }
                .input{
                    width 78%
                    border 1px  
                }
                .text{
                    display inline-block
                    width 20%
                }
                .set-yz{
                    width 40%
                }
                .get-yz{
                    width 35%    
                }
            }
            .login-footer{
                margin-top 20px;
                .go{
                    width 100%
                }
                .go-register{
                    margin-top 20px
                    text-align right    
                }
            }
        }
    }
</style>


