import {API} from '@/config/index.js'

export default {
  LoginCreateUsername: {
      url: API + "/login/my.login/createUsername",
      method: 'POST',
      header:{
          Authorization:true
      }
  },
  //登录入口
  Login: {
      url: API + "/login/login",
      method: 'GET',
      data: {
          username: '',
          password: '',
          captcha: '', //验证码
          
      },header:{
          Authorization:true
      }
  },
  // 重置登录token授权秘钥
  resetLogin: {
      url: API + "/login/my.login/resetLogin",
      method: 'PUT',
      header:{
          Authorization:true
      }
  },
  // 退出登录
  logOut: {
      url: API + "/login/my.login/LogOut",
      method: 'PUT',
      header:{
          Authorization:true
      }
  },
  // 注册账号
  Register: {
      url: API + "/login/login/register",
      method: 'POST',
      data: {
          username: '',
          password: '',
          captcha: '', //验证码
          
      },header:{
          Authorization:true
      }
  },    UserLogin_BindList:{
       url: API + "/login/my.login/bindList",
       method: 'GET',
       header:{
           Authorization:true
       }
   },
   weixinAppLogin: {
       url: API + "/login/weixin/login/app",
       method: 'GET',
       
   },
   weixinLogin: {
       url: API + "/login/weixin/login/weixin",
       method: 'POST',
       data: {
           code: '',
       }
   },
   Login_phoneLogin:{
       url: API + "/login/login/phone",
       method: 'GET',
       header:{
           Authorization:true
       }
   },
   Login_emailLogin:{
       url: API + "/login/login/email",
       method: 'GET',
       header:{
           Authorization:true
       }
   },
   UserLogin_bindEmail:{
       url: API + "/login/my.login/bindEmail",
       // url:"https://unidemo.dcloud.net.cn/api/news",
       method: 'POST',
       header:{
           Authorization:true
       }
   },
   UserLogin_bindPhone:{
       url: API + "/login/my.login/bindPhone",
       // url:"https://unidemo.dcloud.net.cn/api/news",
       method: 'POST',
       header:{
           Authorization:true
       }
   },
   
   UserVerify_SendSms:{
       url: API + "/verify/my.sms/captcha",
       method: 'GET',
       header:{
           Authorization:true
       }
   },
   Verify_SendSms:{
       url: API + "/verify/home.sms/captcha",
       method: 'GET',
       header:{
           Authorization:true
       }
   },
   UserVerify_SendEmail:{
       url: API + "/verify/my.email/captcha",
       method: 'GET',
       header:{
           Authorization:true
       }
   },
   Verify_SendEmail:{
       url: API + "/verify/home.email/captcha",
       method: 'GET',
       header:{
           Authorization:true
       }
   }
   
}
