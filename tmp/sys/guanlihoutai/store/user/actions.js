import Request from '@/request/index.js'
export default {
    isUsername: async function(context, type) {
        return await new Promise((resolve, reject) => {
            var login = context.getters.userInfo.login;
            // console.log(context)
            if (!login) {
                uni.showModal({
                    title: "您还未设置用户名密码,去设置?",
                    content: "点击确认，为您跳转到设置页面",
                    success: (e) => {
                        if (e.confirm) {
                            //登陆
                            uni.navigateTo({
                                url: '/pages/pagesB/login/my/createUsername'
                            })
                        } else {
                            // console.log("放弃登陆")
                        }
                    }
                })
                resolve(false);
                return false
            } else {
                resolve(true);
                return true
            }
        })
    },
    isLogin: async function(context) {
        return await new Promise((resolve, reject) => {
            var hasLogin = context.getters.hasLogin;
            // console.log(context)
            if (!hasLogin) {
                uni.showModal({
                    title: "您还未登陆,立即登陆?",
                    content: "请登陆后进行访问,确认为您跳转到登录页面",
                    success: (e) => {
                        if (e.confirm) {
                            var pages = getCurrentPages();
                            if (pages[pages.length - 1].route ==
                                "/pages/pagesA/login/public/login") {} else {
                                //如果当前路由不是登陆页面，跳转过去
                                uni.navigateTo({
                                    url: '/pages/pagesA/login/public/login'
                                })
                            }

                        } else {
                            // console.log("放弃登陆")
                        }
                    }
                })
                resolve(false);
                return false
            } else {
                resolve(true);
                return true
            }
        })
    },
    thirdLogin: async function(context, type) {
        return await new Promise((resolve, reject) => {
            var origin = false;
            switch (type) {
                case 'weixin':
                    origin = true;
                    break;
                case 'qq':
                    origin = true;
                    break;
                default:
                    break;
            }
            if (origin) {
                resolve(context.dispatch(type + 'Login', type))
            } else {
                resolve(false)
                uni.showToast({
                    title: '暂不支持',
                    icon: "none"
                })
                // reject('不支持的登录方法')
            }

        })
    },
    qqLogin: async function(context, type) {

    },
    weixinLogin: async function(context, type) {

        var self = this;
        return await new Promise((resolve, reject) => {

            uni.login({
                provider: type,
                success: (e) => {
                    // console.log(JSON.stringify(e))
                    // #ifdef APP-PLUS
                    if (e.authResult && typeof e.authResult == 'object') {
                        var resdata = {
                            refreshToken: e.authResult.refresh_token,
                            accessToken: e.authResult.access_token,
                            openid: e.authResult.openid,
                            scope: e.authResult.scope,
                            unionid: e.authResult.unionid
                        }
                        Request(type + 'AppLogin', {
                            data: resdata
                        }).then(appLogin => {
                            // console.log(JSON.stringify(appLogin))
                            resolve(appLogin)

                        })
                    }
                    // #endif
                    // #ifdef MP-WEIXIN
                    if (e.code) {
                        var code = e.code

                        wx.getSetting({
                            success: res => {
                                if (res.authSetting['scope.userInfo']) {
                                    // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
                                    uni.getUserInfo({
                                        provider: type,
                                        success: (res) => {
                                            // console.log(res)
                                            Request(type + 'Login', {
                                                data: {
                                                    model:context.getters.systemInfo.model,
                                                    code: code,
                                                    encryptedData: encodeURIComponent(
                                                        res
                                                        .encryptedData
                                                    ), //一定要把加密串转成URI编码res.encryptedData,
                                                    iv: encodeURIComponent(
                                                        res
                                                        .iv
                                                    )
                                                }
                                            }).then(ress => {
                                                resolve(ress)
                                            })
                                            /**
                                             * 实际开发中，获取用户信息后，需要将信息上报至服务端。
                                             * 服务端可以用 userInfo.openId 作为用户的唯一标识新增或绑定用户信息。
                                             */

                                        },
                                        fail: (err) => {
                                            console.log(err)
                                            uni.showModal({
                                                title: '警告通知',
                                                content: '需要微信授权,否则显示个人信息,点击确定重新获取授权。',
                                                success: (res) => {
                                                    if (res
                                                        .confirm
                                                    ) {
                                                        wx.openSetting({
                                                            success: (
                                                                res
                                                            ) => {
                                                                console
                                                                    .log(
                                                                        res
                                                                    )
                                                                if (
                                                                    res
                                                                    .authSetting[
                                                                        "scope.userInfo"
                                                                    ]
                                                                ) { ////如果用户重新同意了授权登录
                                                                    uni
                                                                        .login({
                                                                            success: (
                                                                                res_login
                                                                            ) => {
                                                                                if (
                                                                                    res_login
                                                                                    .code
                                                                                ) {
                                                                                    uni
                                                                                        .getUserInfo({
                                                                                            withCredentials: true,
                                                                                            success: (
                                                                                                res
                                                                                            ) => {
                                                                                                Request
                                                                                                    (
                                                                                                        'weixinLogin', {
                                                                                                            data: {
                                                                                                                model:context.getters.systemInfo.model,
                                                                                                                code: res_login
                                                                                                                    .code,
                                                                                                                encryptedData: encodeURIComponent(
                                                                                                                    res
                                                                                                                    .encryptedData
                                                                                                                ), //一定要把加密串转成URI编码res.encryptedData,
                                                                                                                iv: encodeURIComponent(
                                                                                                                    res
                                                                                                                    .iv
                                                                                                                )
                                                                                                            }
                                                                                                        }
                                                                                                    )
                                                                                                    .then(
                                                                                                        ress => {
                                                                                                            resolve
                                                                                                                (
                                                                                                                    ress
                                                                                                                )
                                                                                                        }
                                                                                                    )
                                                                                            }
                                                                                        })
                                                                                }
                                                                            }
                                                                        });
                                                                }
                                                            },
                                                            fail: function(
                                                                res
                                                            ) {

                                                            }
                                                        })

                                                    }
                                                }
                                            })

                                            resolve(false)
                                            // console.log(res)
                                        },
                                        complete: (res) => {


                                        }


                                    });
                                } else {
                                    resolve('weixin')
                                    uni.showToast({
                                        title: '微信未授权,请授权后尝试',
                                        icon: 'none'
                                    });
                                }
                            }
                        })

                    }
                    // #endif
                    // console.log(JSON.stringify(e))
                },
                fail: (err) => {
                    resolve(false)
                    reject(err)
                    // console.error('授权登录失败：' + JSON.stringify(err));
                }
            });
        })
    }
}
