import {API} from '@/config/index.js'

export default {
    // 新闻分类列表
    NewsCategory_list: {
        url: API + "/news/home/category/list",
        header: {
            Authorization: true
        }
    },
    NewsIndex: {
        url: API + "/news/home.index",
    },
    // 新闻列表
    News_list: {
        url: API + "/news/home.news/list",
        header: {
            Authorization: true
        }
    },
    News_detail: {
        url: API + "/news/home.news/detail",
        header: {
            Authorization: true
        }
    },
    // 新闻列表
    UserNews_list: {
        url: API + "/news/my.news/list",
        header: {
            Authorization: true
        }
    },
    UserNews_delete: {
        url: API + "/news/my.news/delete",
        method: 'DELETE',
        header: {
            Authorization: true
        }
    },
    // 新闻发布
    UserNews_add: {
        url: API + "/news/my.news/publish",
        method: 'POST',
        header: {
            Authorization: true
        }
    },
    UserNewsCollect_list: {
        url: API + "/news/my.collect/list",
        header: {
            Authorization: true
        }
    },
    UserNewsCollect_update: {
        // 收藏 
        url: API + "/news/home.news/collect",
        method: 'PUT',
        header: {
            Authorization: true
        }
    },
    UserNewsCommentZan_update: {
        // 评论点赞 +取消
        url: API + "/news/home.comment/zan",
        method: 'PUT',
        header: {
            Authorization: true
        }
    },
    UserNewsShare_update: {
        // 收藏
        url: API + "/news/home.news/share",
        method: 'POST',
        header: {
            Authorization: true
        }
    },

    UserNewsReport_publish: {
        // 举报
        url: API + "/news/home.news/report",
        method: 'POST',
        header: {
            Authorization: true
        }
    },
    // 新闻点赞
    UserNewsZan_update: {
        // 点赞、取消点赞
        url: API + "/news/home.news/zan",
        method: 'PUT',
        header: {
            Authorization: true
        }
    },
    // 发表新闻评论
    UserNewsComment_publish: {
        url: API + "/news/home.news/comment",
        method: 'POST',
        header: {
            Authorization: true
        }
    },
    NewsComment_list: {
        url: API + "/news/home.comment/list",
        method: 'GET',
        header: {
            Authorization: true
        }
    },
    // 回复评论
    UserNewsCommentReply_publish: {
        url: API + "/news/home.comment/publish",
        method: 'POST',
        header: {
            Authorization: true
        }
    },
    // 查看回复列表
    NewsCommentReply_list: {
        url: API + "/news/home.comment/list",
        method: 'GET',
        header: {
            Authorization: true
        }
    },
    UserNewsCommentReplyZan_update: {
        // 评论点赞 +取消
        url: API + "/news/home.comment/zan",
        method: 'PUT',
        header: {
            Authorization: true
        }
    }


}
