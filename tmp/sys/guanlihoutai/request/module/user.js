import {
    API
} from '@/config/index.js'
export default {
    UserHabit_list: {
        url: API + "/user/my/habit/list",
        // method: 'POST',
        header: {
            Authorization: true
        }
    },
    // 用户自定义信息
    UserHabit_detail: {
        url: API + "/user/my/habit/detail",
        // method: 'POST',
        header: {
            Authorization: true
        }
    },
    // 用户自定义修改
    UserHabit_update: {
        url: API + "/user/my/habit/update",
        method: 'PUT',
        header: {
            Authorization: true
        }
    },

    // 发送消息
    UserChat_send: {
        url: API + "/chat/my.chat/send",
        method: 'POST',
        header: {
            Authorization: true
        }
    },
    // 消息列表
    UserChat_list: {
        url: API + "/chat/my.chat/list",
        method: 'GET',
        header: {
            Authorization: true
        }
    },
    // 好友列表
    UserFriend_list: {
        url: API + "/friend/my.friend/list",
        header: {
            Authorization: true
        }
    },
    // 添加好友
    UserFriend_add: {
        url: API + "/friend/my.friend/add",
        header: {
            Authorization: true
        }
    },
    // 删除好友
    UserFriend_delete: {
        url: API + "/friend/my.friend/delete",
        header: {
            Authorization: true
        }
    },
    // 查看用户信息
    UserUser_info: {
        url: API + "/user/home.user/info",
        header: {
            Authorization: true
        }
    },
    // 修改用户信息
    UserUser_Update: {
        url: API + "/user/my.user/update",
        method: 'PUT',
        header: {
            Authorization: true
        }
    },
    // 添加地址信息
    UserAddress_Add: {
        url: API + "/user/my.address/add",
        method: 'POST',
        header: {
            Authorization: true
        }
    },
    // 我的地址列表
    UserAddress_List: {
        url: API + "/user/my.address/list",
        header: {
            Authorization: true
        }
    },
    // 删除用户地址
    UserAddress_Delete: {
        url: API + "/user/my.address/delete",
        method: 'DELETE',
        header: {
            Authorization: true
        }
    },
    UserProve_detail: {
        url: API + "/prove/detail",
        method: 'POST',
        header: {
            Authorization: true
        }
    },
    UserProve_create: {
        url: API + "/prove/create",
        method: 'POST',
        header: {
            Authorization: true
        }
    }
}
