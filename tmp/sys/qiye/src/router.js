
export default {
//   mode: 'history',
//   base: process.env.BASE_URL,
  routes: [
    {
        path: '/',
        name: 'index',
        component: () => import('./pages/home/index/index.vue'),
        redirect: '/index',
        children:[
		{
				path: '/index',
				name: 'home',
				meta: { title: '首页' },
				component: () => import('./pages/home/index/home.vue')
		},{
				path: '/list',
				name: 'list',
				meta: { title: '新闻动态' },
				component: () => import('./pages/home/index/news.vue'),
                redirect: '/list/news',
                children:[
                    {
                               path: '/list/news',//mode=模板
                               name: 'list_news',
                               meta: { title: '列表页' },
                               component: () => import('./pages/home/list/news.vue')
                        },{
                               path: '/list/about',//mode=模板
                               name: 'list_about',
                               meta: { title: '关于页' },
                               component: () => import('./pages/home/list/about.vue')
                        }]
                },{
				path: '/detail',
				name: 'detail',
				meta: { title: '详情页' },
				component: () => import('./pages/home/detail/index.vue'),
                redirect: '/Detail/news',
                children:[
                    {
                                path: '/detail/news',//mode=模板
                                name: 'detail_news',
                                props: true,
                                meta: { title: '详情' },
                                component: () => import('./pages/home/detail/news.vue')
                        }]
                }]
    },{
		path: '/admin',
		name: 'admin',
		meta: { title: '后台首页' },
		component: () => import('./pages/admin/Index.vue'),
        redirect: '/admin/chart',
		children:[{
                        
					path: '/admin/chart',
					name: 'admin_chart',
					meta: { title: '图表' },
                    redirect: '/admin/chart/list',
					component: () => import('./pages/admin/chart/index.vue'),
                                        children:[{
                                                path: '/admin/chart/list',
                                                name: 'admin_chart_list',
                                                meta: { title: '图表' },
                                                component: () => import('./pages/admin/chart/chart.vue'), 
                                        }]
        },{  
					path: '/admin/user',
					name: 'admin_user',
					meta: { title: '用户管理' },
                    redirect: '/user/user/list',
					component: () => import('./pages/admin/user/index.vue'),
                            children:[{
                                            path: '/admin/user/list',
                                            name: 'admin_user_list',
                                            meta: { title: '用户列表' },
                                            component: () => import('./pages/admin/user/list.vue')
                                    }],
        },{
					path: '/admin/news',
					name: 'admin_news',
					meta: { title: '新闻管理' },
					component: () => import('./pages/admin/news/index.vue'),
                                        children:[
                                        	{
                                                        path: '/admin/news/add',
                                                        name: 'admin_news_add',
                                                        meta: { title: '添加新闻' },
                                                        component: () => import('./pages/admin/news/add.vue')
                                        	},{
                                                        path: '/admin/news/list',
                                                        name: 'admin_news_list',
                                                        meta: { title: '新闻列表' },
                                                        component: () => import('./pages/admin/news/list.vue')
                                        	},{
                                                        path: '/admin/news/category',
                                                        name: 'admin_news_category',
                                                        meta: { title: '分类管理' },
                                                        component: () => import('./pages/admin/news/category.vue')
                                        	}]
		},{
					path: '/admin/nav',
					name: 'admin_nav',
                    redirect: '/admin/nav/list',
					meta: { title: '导航管理' },
					component: () => import('./pages/admin/nav/index.vue'),
                                        children:[{
                                                        path: '/admin/nav/list',
                                                        name: 'admin_nav_list',
                                                        meta: { title: '导航列表' },
                                                        component: () => import('./pages/admin/nav/list.vue')
                                        }]
        },{
					path: '/admin/ad',
					name: 'admin_ad',
					meta: { title: '广告管理' },
					component: () => import('./pages/admin/ad/index.vue'),
                                        children:[
                                        	{
                                                        path: '/admin/ad/list',
                                                        name: 'admin_ad_list',
                                                        meta: { title: '广告列表' },
                                                        component: () => import('./pages/admin/ad/list.vue')
                                        	},{
                                                        path: '/admin/ad/category',
                                                        name: 'admin_ad_category',
                                                        meta: { title: '分类管理' },
                                                        component: () => import('./pages/admin/ad/category.vue')
                                        	}]
		},{
                        path: '/admin/admin',
                        name: 'admin_admin',
                        meta: { title: '后台管理' },
                        component: () => import('./pages/admin/admin/index.vue'),
                        children:[
                        {
                                 path: '/admin_admin_user',
                                 name: 'admin_admin_user',
                                 meta: { title: '成员团队' },
                                 component: () => import('./pages/admin/admin/user.vue')
                        },{
                                 path: '/admin_admin_rank',
                                 name: 'admin_admin_rank',
                                 meta: { title: '权限管理' },
                                 component: () => import('./pages/admin/admin/rank.vue')
                        },{
                                 path: '/admin_admin_myFunc',
                                 name: 'admin_admin_myFunc',
                                 meta: { title: '我的职权' },
                                 component: () => import('./pages/admin/admin/myFunc.vue')
                        }]
                }
			]
	},{
		path: '/login',
		name: 'login',
		meta: { title: '登陆' },
		component: () => import('./pages/home/login/index.vue'),
        redirect: '/login_login',
        children:[
        {
                 path: '/login_login',
                 name: 'login_login',
                 meta: { title: '登陆' },
                 component: () => import('./pages/home/login/login.vue')
        },
        {
                 path: '/login_register',
                 name: 'login_register',
                 meta: { title: '用户注册' },
                 component: () => import('./pages/home/login/register.vue')
        }   
        ]
	}
  ]
}
