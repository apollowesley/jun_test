<template>
  <el-form
    ref="form"
    :model="form"
    :rules="rules"
    class="ms-content"
  >
    <el-form-item prop="username">
      <el-input
        v-model="form.username"
        placeholder="username"
      >
        <el-button slot="prepend" class="el-button-tip" >
          用户名
        </el-button>
      </el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input
         :type="psdShow?'password':''"
        v-model="form.password"
        autocomplete="off"
      >
        <el-button slot="prepend" class="el-button-tip" 
          @click="psdShow=!psdShow" >
          密码
        </el-button>
        <span slot="suffix" style="line-height: 2.7em;"  @click="psdShow=!psdShow"  :class="psdShow?'el-icon-view':'iconfont icon-biyanjing'"></span>
      </el-input>
    </el-form-item>
    <el-form-item prop="confirm_password">
      <el-input
         :type="psdShow?'password':''"
        v-model="form.confirm_password"
        autocomplete="off"
      >
        <el-button slot="prepend" class="el-button-tip" @click="psdShow=!psdShow" >
          密码确认
        </el-button>
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
        type="password"
        v-model="form.captcha"
        autocomplete="off"
      >
        <el-button
          slot="prepend" class="el-button-tip"
          style="padding: ;height: 100%;"
        >
          验证码
        </el-button>
      </el-input>
    </el-form-item>
    <div
      class="login-btn"
      style="display: flex;"
    >
      <el-button
        style="flex:1"
        type="primary"
        @click="onSubmit('form')"
      >
        提交
      </el-button>
      <el-button style="flex:1">
        取消
      </el-button>
    </div>
  </el-form>
</template>

<script>

    import api from '../../../request/api.js'
    import Request from "../../../request/index.js"
    import yc from "yc-js";
    var Validate =yc.Validate;
    var Md5=yc.Md5;
    export default {
        name: 'AdminUserFrom',
        data: function(){

              var validatePass = (rule, value, callback) => {
                              var rule=[{checkType:"password",name:'password'}]
                              var data={password:value}
                              var Val=Validate;
                              var check=Val.check(data,rule);
                                  if(!check){
                                      callback(Val.error)
                                      return Val.error;
                                  }
                                  this.$refs.form.validateField('confirm_password');
                                  callback()
                            }
              var validatePass2 = (rule, value, callback) => {
                            if (value === '') {
                              callback(new Error('请再次输入密码'));
                            } else if (value !== this.form.password) {
                              callback(new Error('两次输入密码不一致!'));
                            } else {
                              callback();
                            }
              };
            return {
              psdShow:true,
                captcha:api.Captcha.url,
                // rank: rank,
                form: {
                    username: 't1234567',
                    password: 't1234567',
					confirm_password: 't1234567',
                    captcha:'1234'
                },rules: {
                        username: [
                            { validator: validatePass, trigger: 'blur' }
                        ],
                        password: [
                            { validator: validatePass, trigger: 'blur' }
                        ],
                        confirm_password: [
                            { validator: validatePass2, trigger: 'blur' }
                        ],captcha:[
                             { required: true, message: '请输入验证码', trigger: 'blur' }
                        ]
                }
            }
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
            onSubmit(formName) {
                var form={
                    password:Md5.Md5(Md5.Md5(this.form.username+this.form.password)),
                    username:this.form.username,
                    captcha:this.form.captcha
                }
				// console.log(this.form);
				this.$refs[formName].validate((valid) => {
				  if (valid ) {
                      Request('Register',{data:form})
                      .then((res)=>{
                          if(res.code==200){
                              this.$message.success('注册成功！');
                              this.$router.push({path: '/'});
                          }else{
                              this.tapCaptcha();
                              this.$alert(res.data.message, '提示', {
                                 confirmButtonText: '确定',
                                 type: 'warning'
                               })
                          }
                      })
					
				  } else {
					// console.log('error submit!!');
					return false;
				  }
				});
                
            }
        }
    }
</script>
<style>
/* @import url("style.css"); */
</style>