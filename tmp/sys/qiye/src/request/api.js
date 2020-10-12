import Config from '../config.js'
var Api = Config.api;
var adminAPI=Config.adminAPI;
var Cdn=Config.cdn;
/**
 * api 接口默认值及相关参数
 */
export default {
    // 全部功能名列表
    AdminAdminFunc_List: {
        url: adminAPI + "/admin_func/list",
        params: {
            access_token: true
        } //用户get请求的数据
    },
    // 添加管理人员
    AdminAdminUser_Add: {
        url: adminAPI + "/admin_user/add",
        method:'POST',
        data: {
            access_token: true
        } //用户get请求的数据
    },
    // 管理团队列表
    AdminAdminUser_List:{
        url: adminAPI + "/admin_user/list",
        params: {
            access_token: true
        } //用户get请求的数据
    },
    AdminAdminUser_update: {
        url: adminAPI + "/admin_user/update",
        method: 'PUT',
        data: {
            access_token: true
        }
    },
     // 删除单条
    AdminAdminUser_Delete: {
        url: adminAPI + "/admin_user/delete",
        method: 'DELETE',
        data: {
            user_id: '0', //要恢复的用户id
            access_token: true
        }
    },
    // 恢复一条
    AdminAdminUser_Restore: {
        url: adminAPI + "/admin_user/restore",
        method: 'PUT',
        data: {
            user_id: 0, //要恢复的用户id
            access_token: true
        }
    },
    AdminAdminUser_Myupdate: {
        url: adminAPI + "/admin_user/MyUpdate",
        method: 'PUT',
        data: {
            access_token: true
        }
    },
      // 全部权限功能列表
    AdminAdminRank_List: {
        url: adminAPI + "/admin_rank/list",
        params: {
            access_token: true
        } //用户get请求的数据
    },
    // 用户的功能
    AdminAdminRank_MyRankFunc: {
        url: adminAPI + "/admin_rank/MyRankFunc",
        params: {
            access_token: true
        } //用户get请求的数据
    },
        // 删除单条
    AdminAdminRank_Add: {
        url: adminAPI + "/admin_rank/add",
        method: 'POST',
        params: {
            access_token: true
        }
    }, // 删除单条
    AdminAdminRank_Delete: {
        url: adminAPI + "/admin_rank/delete",
        method: 'DELETE',
        data: {
            user_id: '0', //要恢复的用户id
            access_token: true
        }
    },
    // 恢复一条
    AdminAdminRank_Restore: {
        url: adminAPI + "/admin_rank/restore",
        method: 'PUT',
        data: {
            user_id: 0, //要恢复的用户id
            access_token: true
        }
    },
    // 修改
    AdminAdminRank_Update: {
        url: adminAPI + "/admin_rank/update",
        method: 'PUT',
        data: {
            access_token: true
        },
    },
    
    AdminAdminRankFunc_Update: {
        url: adminAPI + "/admin_rank_func/update",
        method: 'PUT',
        data: {
            access_token: true
        }
    },
    AdminAdminRankFunc_Delete: {
        url: adminAPI + "/admin_rank_func/delete",
        method: 'DELETE',
        data: {
            access_token: true
        }
    },
    // 给用户添加新功能
    AdminAdminRankFunc_Add: {
        url: adminAPI + "/admin_rank_func/add",
        method: 'POST',
        data: {
            access_token: true
        }
    },
    
    
    
    
    
    
    Area_List:{
        url: Api + "/area/list",
    },
    adList:{
        url: Api + "/ad/list",
    },
// 查询多条
   AdminUser_List: {
       url: adminAPI + "/user/list",
       headers: {}, //设置请求头
       data: {}, //用于其他请求的数据
       params: {
           access_token: true,
           p: 0, //页码
           start_time: 15655556666, //起始时间
           last_time: 15655576666, //截至时间
           trashed: 0, //是否被删除的
       } //用户get请求的数据
   },
   // 修改
   AdminUser_update: {
       url: adminAPI + "/user/update",
       method: 'PUT',
       data: {
           access_token: true
       },
       headers: {
           // 'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'
           // 'Content-Type':'multipart/form-data'
           // "signature":JSON.stringify(configure.signature),
           // "Content-Type":"application/x-www-form-urlencoded"
       }
   },
   // 删除单条
   AdminUser_Delete: {
       url: adminAPI + "/user/delete",
       method: 'DELETE',
       data: {
           id: '0', //要恢复的用户id
           access_token: true
       }
   },
   // 删除多条
   AdminUser_DeleteAll: {
       url:adminAPI + "/user/deleteAll",
       method: 'DELETE',
       data: {
           list: [], //要恢复的用户id
           access_token: true
       }
   },
   // 恢复一条
   AdminUser_Restore: {
       url: adminAPI + "/user/restore",
       method: 'PUT',
       data: {
           id: 0, //要恢复的用户id
           access_token: true
       }
   },
   // 恢复多条
  AdminUser_RestoreAll: {
       url: adminAPI + "/user/restoreAll",
       method: 'PUT',
       data: {
           list: [], //要恢复的用户id
           access_token: true
       }
   },
   // 添加用户
   AdminUser_Add: {
       url: adminAPI + "/user/add",
       method: 'POST',
       data: {
           username: '',
           password: '',
           rank: 0, //权限
           access_token: true
       }
   },
    AdminAd_List:{
        url: adminAPI + "/ad/list",
        params:{
            access_token: true
        }
    },
    AdminAdCategory_List:{
        url: adminAPI + "/ad_category/list",
        params:{
            access_token: true
        }
    },
    AdminAdCategory_Add:{
        url: adminAPI + "/ad_category/add",
        method: 'POST',
        data: {
            access_token: true
        } 
    },
    AdminAdCategory_Update:{
        url: adminAPI + "/ad_category/update",
        method: 'PUT',
        data: {
            access_token: true
        } 
    },
     // 要删除的图片
    UserAdCategory_Delete: {
        url: adminAPI + "/ad_category/delete",
        method: 'DELETE',
        data: {
            id: '0', 
            access_token: true
        }
    },
    AdminAd_Detail:{
       url: adminAPI + "/ad/detail",
       params:{
           access_token: true
       }
    },
    AdminAd_Add:{
       url: adminAPI + "/ad/add",
       method: 'POST',
       data: {
           access_token: true
       } 
    },
   AdminAd_Update:{
       url: adminAPI + "/ad/update",
       method: 'PUT',
       data: {
           access_token: true
       } 
    },
    AdminAd_Delete: {
        url: adminAPI + "/ad/delete",
        method: 'DELETE',
        data: {
            id: '0', //要恢复的用户id
            access_token: true
        }
    },AdminAd_Restore: {
        url: adminAPI + "/ad/restore",
        method: 'PUT',
        data: {
            id: 0, //要恢复的用户id
            access_token: true
        }
    },
    NavList:{
        url: Api + "/nav/list",
    },
    newsDetail:{
       url: Api + "/news/detail",
       params:{
          id:'' 
       }
    },
    NewsCategory_List:{
        url: Api + "/news_category/list",
    },

    newsList:{
       url: Api + "/news/list",
       params:{
           
       }
    },
    AdminNav_List:{
       url: adminAPI + "/nav/list", 
       params:{
           access_token: true
       }
    },
    AdminNav_Add:{
        url: adminAPI + "/nav/add",
        method: 'POST',
        data: {
            access_token: true
        } 
    },
    AdminNav_Update:{
        url: adminAPI + "/nav/update",
        method: 'PUT',
        data: {
            access_token: true
        } 
    },
         // 要删除的图片
    AdminNav_Delete: {
        url: adminAPI + "/nav/delete",
        method: 'DELETE',
        data: {
            id: '0', 
            access_token: true
        }
    },
    AdminNewsCategory_List:{
        url: adminAPI + "/news_category/list",
        params:{
            access_token: true
        }
    },
    AdminNewsCategory_Add:{
        url: adminAPI + "/news_category/add",
        method: 'POST',
        data: {
            access_token: true
        } 
    },
    AdminNewsCategory_Update:{
        url: adminAPI + "/news_category/update",
        method: 'PUT',
        data: {
            access_token: true
        } 
    },
     // 要删除的图片
    AdminNewsCategory_Delete: {
        url: adminAPI + "/news_category/delete",
        method: 'DELETE',
        data: {
            id: '0', 
            access_token: true
        }
    },
    AdminNews_Add:{
       url: adminAPI + "/news/add",
       method: 'POST',
       data: {
           access_token: true
       } 
    },
    AdminNews_Update:{
       url: adminAPI + "/news/update",
       method: 'PUT',
       data: {
           access_token: true
       } 
    },
    AdminNews_List:{
       url: adminAPI + "/news/list",
       params: {
           access_token: true
       } 
    },
    AdminNews_Detail:{
       url: adminAPI + "/news/detail",
       params: {
           access_token: true
       } 
    },
    AdminNews_Delete: {
        url: adminAPI + "/news/delete",
        method: 'DELETE',
        data: {
            id: '0', //要恢复的用户id
            access_token: true
        }
    },
    AdminNews_Restore: {
        url: adminAPI + "/news/restore",
        method: 'PUT',
        data: {
            id: 0, //要恢复的用户id
            access_token: true
        }
    },
    AdminNewsImage_DetailedList:{
        url: adminAPI + "/news_image/detailedList",
        params: {
            access_token: true
        } 
    },
    AdminNewsImage_Upload:{
        url: adminAPI + "/news_image/upload",
        method: 'POST',
        data: {
            access_token: true
        } 
    },
    // 要删除的图片
    AdminNewsImage_Delete: {
        url: adminAPI + "/news_image/delete",
        method: 'DELETE',
        data: {
            id: '0', 
            access_token: true
        }
    },


    // 登陆
    Login: {
        url: Api + "/login/login",
        method: 'GET',
        params: {
            username: '',
            password: '',
            captcha: '', //验证码
            access_token: true
        }
    },
    resetLogin: {
        url: Api + "/login/resetLogin",
        method: 'PUT',
        params: {
            access_token: true
        }
    },
    // 注册
    Register: {
        url:  Api + "/login/register",
        method: 'POST',
        data: {
            username: '',
            password: '',
            captcha: '', //验证码
            access_token: true
        }
    },
    Captcha: {
        url:  Api + "/verify/captcha",
        method: 'GET',
        params: {
            id: 'captcha', //验证场景
            access_token: true
        }
    }
}
