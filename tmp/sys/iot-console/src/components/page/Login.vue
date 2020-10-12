<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">后台管理系统</div>
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="ms-content">
                <el-form-item prop="username">
                    <el-input v-model="ruleForm.username" placeholder="username">
                        <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" placeholder="password" v-model="ruleForm.password" @keyup.enter.native="submitForm('ruleForm')">
                        <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm()">登录</el-button>
                </div>
                <div class="login-footer">
                  <router-link to="/register" tag='p'  class="go-register">没有账户？前往注册！</router-link >
            </div>
            </el-form>
        </div>
    </div>
</template>

<script>
    import {login,getMsg} from '@/api/user'
    import { getToken ,setToken } from '@/utils/auth'
    export default {
        data: function(){
            return {
                ruleForm: {
                    username: '',
                    password: ''
                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            submitForm() {
                login(this.ruleForm.username,this.ruleForm.password)
                .then(res=>{
                    console.log(res)
                    if(res.msg){
                        this.$message.error(res.msg);
                    }
                    if(res.code==200&&res.result){
                        setToken(res.result)
                        console.log(res)
                         this.$router.push('/')
                        
                    }
                   
                })
            }
        }
    }
</script>

<style scoped lang='stylus'>
    .login-wrap{
        position: relative;
        width:100%;
        height:100%;
        background-image: url(../../assets/login-bg.jpg);
        background-repeat no-repeat
        background-position center
        background-size: 100%;
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
        position: absolute;
        left:50%;
        top:50%;
        width:350px;
        margin:-190px 0 0 -175px;
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
    .login-btn button{
        width:100%;
        height:36px;
        margin-bottom: 10px;
    }
    .login-tips{
        font-size:12px;
        line-height:30px;
        color:#fff;
    }
    .login-footer{
                margin-top 20px;
                .go-register{
                    margin-top 20px
                    text-align right   
                    cursor pointer 
                }
            }
</style>