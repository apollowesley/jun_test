import config from '../config/index.js'
var API = config.API;
var userAPI = config.userAPI;

export default {
    Device_edition:{
      url: API + "/update/edition/",
      // url:"https://unidemo.dcloud.net.cn/api/news",
      // method: 'POST',
      data: {
          // access_token: true
      }  
    },
     // 查看回复列表
    NewsCommentReply_list: {
        url: API + "/news_comment_reply/list",
        method: 'GET',
        data: {
            access_token: true
        }
    },
     // 回复评论
    UserNewsCommentReply_publish: {
        url: userAPI + "/news_comment_reply/publish",
        // url:"https://unidemo.dcloud.net.cn/api/news",
        method: 'POST',
        data: {
            access_token: true
        }
    },
    NewsComment_list: {
        url: API + "/news_comment/list",
        method: 'GET',
        data: {
            access_token: true
        }
    },
     // 发表新闻评论
    UserNewsComment_publish: {
        url: userAPI + "/news_comment/publish",
        // url:"https://unidemo.dcloud.net.cn/api/news",
        method: 'POST',
        data: {
            access_token: true
        }
    },
    
     // 新闻分类列表
    NewsCategory_list: {
        url: API + "/news_category/list",
        // url:"https://unidemo.dcloud.net.cn/api/news",
        // method: 'POST',
        data: {
            // access_token: true
        }
    },
    // 新闻列表
    News_list: {
        url: API + "/news/list",
        // url:"https://unidemo.dcloud.net.cn/api/news",
        // method: 'POST',
        data: {
            // access_token: true
        }
    },
    News_detail: {
        url: API + "/news/detail",
        data: {
            // access_token: true
        }
    },
     // 新闻列表
    UserNews_list: {
        url: userAPI + "/news/list",
        // url:"https://unidemo.dcloud.net.cn/api/news",
        // method: 'POST',
        data: {
            access_token: true
        }
    },
    UserNews_delete: {
        url: userAPI + "/news/delete",
        // url:"https://unidemo.dcloud.net.cn/api/news",
        method: 'DELETE',
        data: {
            access_token: true
        }
    },
    // 新闻发布
    UserNews_add: {
        url: userAPI + "/news/add",
        // url:"https://unidemo.dcloud.net.cn/api/news",
        method: 'POST',
        data: {
            access_token: true
        }
    },
    // 新闻点赞
    UserNewsZan_update: {
        url: userAPI + "/news_zan/update",
        // url:"https://unidemo.dcloud.net.cn/api/news",
        method: 'PUT',
        data: {
            access_token: true
        }
    },
    // 发送消息
    UserChat_send: {
        url: userAPI + "/chat/send",
        method: 'POST',
        data: {
            access_token: true
        }
    },
    // 消息列表
    UserChat_list: {
        url: userAPI + "/chat/list",
        method: 'GET',
        data: {
            access_token: true
        }
    },
    // 好友列表
    UserFriend_list: {
        url: userAPI + "/friend/list",
        data: {
            access_token: true
        }
    },
    // 添加好友
    UserFriend_add: {
        url: userAPI + "/friend/add",
        data: {
            access_token: true
        }
    },
    // 删除好友
    UserFriend_delete: {
        url: userAPI + "/friend/delete",
        data: {
            access_token: true
        }
    },
    // 查看用户信息
    UserUser_info: {
        url: userAPI + "/user/info",
        data: {
            access_token: true
        }
    },
    // 修改用户信息
    UserUser_Update: {
        url: userAPI + "/user/update",
        method: 'PUT',
        data: {
            access_token: true
        }
    },
    // 添加地址信息
    UserAddress_Add: {
        url: userAPI + "/address/add",
        method: 'POST',
        data: {
            access_token: true
        }
    },
    // 我的地址列表
    UserAddress_List: {
        url: userAPI + "/address/list",
        data: {
            access_token: true
        }
    },
    // 删除用户地址
    UserAddress_Delete: {
        url: userAPI + "/address/delete",
        method: 'DELETE',
        data: {
            access_token: true
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
            access_token: true
        }
    },
    // 重置登录token授权秘钥
    resetLogin: {
        url: API + "/login/resetLogin",
        method: 'PUT',
        data: {
            access_token: true
        }
    },
    // 退出登录
    logOut: {
        url: API + "/login/LogOut",
        method: 'PUT',
        data: {
            access_token: true
        }
    },
    // 注册账号
    Register: {
        url: API + "/login/register",
        method: 'POST',
        data: {
            username: '',
            password: '',
            captcha: '', //验证码
            access_token: true
        }
    },
    Captcha: {
        url: API + "/verify/captcha",
        method: 'GET',
        data: {
            id: 'captcha', //验证场景
            access_token: true
        }
    }
}
