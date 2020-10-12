<template>
  <el-form
    :model="form"
    :rules="rules"
    ref="form"
    label-width="0"
    class="ms-content"
  >
    <el-form-item prop="username">
      <el-input
        v-model="form.username"
        placeholder="username"
      >
        <el-button  class="el-button-tip"
          slot="prepend"
          icon="icon-wode iconfont"
        />
      </el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input
        :type="psdShow?'password':''"
        placeholder="password"
        v-model="form.password"
        @keyup.enter.native="submitForm('form')"
      >
        <el-button
          slot="prepend" class="el-button-tip icon-denglumima iconfont"
          @click="psdShow=!psdShow"
        />
        <span slot="suffix" style="line-height: 2.7em;"  @click="psdShow=!psdShow"  :class="psdShow?'el-icon-view':'iconfont icon-biyanjing'"></span>
      </el-input>
    </el-form-item>
                
    <div style="display: flex;width: 100%;padding-bottom: 5px;">
      <img
        :src="captcha"
        @click="tapCaptcha()"
        mode=""
        style="flex:1;width: 60%;"
      >
    </div>
    <el-form-item prop="captcha">
      <el-input
        placeholder="请输入图片验证码"
        v-model="form.captcha"
        autocomplete="off" >
        <el-button class="el-button-tip"
          slot="prepend"
          style="padding: ;height: 100%;" >
          验证码
        </el-button>
      </el-input>
    </el-form-item>
    <div class="login-btn">
      <el-button
        type="primary"
        style="width: 100%;"
        @click="submitForm('form')"
      >
        登录
      </el-button>
    </div>
  </el-form>
</template>

<script >

    import Request from "../../../request/index.js"
    import yc from "yc-js";
   var Md5=yc.Md5;
    export default {
        data(){
            return {
                captcha:'',
                psdShow:true,
                form: {
                    username: 'admin',
                    password: 'admin',
                    captcha:''
                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' }
                    ],captcha:[
                        { required: true, message: '请输入验证码', trigger: 'blur' }
                    ]
                }
            }
        },
        computed:{
        },
        created () {
        	this.tapCaptcha();
        },
        methods: {
            tapCaptcha(){
               var that=this;
                  Request('Captcha', {
                      responseType: 'blob',
                  }).then(res => {
                     that.captcha= window.URL.createObjectURL(res.data)
                  })
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        var Obj={};
                        Object.assign(Obj,this.form);
                        
                        Obj.password=Md5.Md5(Md5.Md5(Obj.username+Obj.password));
                            // console.log(Obj)
                            //这里写业务请求服务器验证是否登陆
                             Request.get('Login', {
                                    params: Obj
                                })
                                .then((res) => {
                                    // console.log(res);
                                    let rt = res.data;
                                    if (res.data.code == 200) {
                                        
                                        this.$confirm('保存密码后,下次使用无需重复登陆,如果是在公共场合不建议保存以免密码泄露', '是否保存登陆密码?', {
                                            confirmButtonText: '保存本地',
                                            cancelButtonText: '不保存',
                                            type: 'warning'
                                        }).then(() => {
                                            this.$store.commit('login');
                                            this.$store.commit('longLogin',true);
                                            //家里保持长期登录环境
                                            this.$store.commit('userInfo', rt.data.userInfo);
                                            this.$store.commit('token', rt.data.token);
                                            this.$router.push({
                                                path: '/'
                                            });
                                        }).catch(() => {
                                            this.$store.commit('login');
                                            this.$store.commit('longLogin',false);
                                            this.$store.commit('userInfo', rt.data.userInfo);
                                            this.$store.commit('token', rt.data.token);
                                            //登陆成功返回首页
                                            this.$router.push({
                                                path: '/'
                                            });
                                        });
                                    } else {
                                        this.tapCaptcha();
                                        this.$alert(rt.message, '提示', {
                                            confirmButtonText: '确定',
                                            type: 'warning'
                                        })
                                    }
                                    return res.data;
                                })

                    } else {
                        return false;
                    }
                });
            }
        }
    }
</script>

<style lang="scss">
/* @import url("style.css"); */
</style>