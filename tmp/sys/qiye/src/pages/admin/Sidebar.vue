<template>
    <!-- <div class="sidebar" style="height: 100%;"> -->
        <el-menu class="sidebar-el-menu"     @select="handleSelect" :collapse="collapse" 
            text-color="#bfcbd9" active-text-color="#20a0ff"  unique-opened >
<!-- 			<el-menu-item  @click="collapse=!collapse">
			 <i class="el-icon-menu"></i> <span>折叠面板</span>
			</el-menu-item> -->
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i><span slot="title" >{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu v-if="subItem.subs" :index="subItem.index" :key="subItem.index">
                                <template slot="title" >{{ subItem.title }}</template>
                                <el-menu-item v-for="(threeItem,i) in subItem.subs" :key="i" :index="threeItem.index">
                                    {{ threeItem.title }}
                                </el-menu-item>
                            </el-submenu>
                            <el-menu-item v-else :index="subItem.index" :key="subItem.index">
                                {{ subItem.title }}
                            </el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i><span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
		<!-- <div style="flex: 1;background: #324157;"></div> -->
    <!-- </div> -->
</template>

<script>
    // import bus from '../common/bus';
    export default {
		props:['collapse'],
        data() {
            return {
                // collapse: false,
                items: [
                    {
                        icon: 'iconfont icon-fangzi',
                    	index:'/',
                        title: '前台首页'
                    },
                    {
                        icon: 'iconfont icon-houtai2',
						index:'admin',
                        title: '后台首页'
                    },{
                        icon: 'iconfont icon-daohang',
                        title: '导航管理',
                    	index: 'admin_nav',
                    },{
                        icon: 'iconfont icon-guanggaoguanli',
                        title: '广告管理',
                    	index: 'admin_ad',
                    	subs:[{
                    		icon: 'iconfont ',
                    		index: 'admin_ad_category',
                    		title: '分类管理'
                    	},{
                    		icon: 'iconfont ',
                    		index: 'admin_ad_list',
                    		title: '广告列表'
                    	}]
                    },{
                        icon: 'iconfont icon-Icon-zhuanjiatuanduiguanli',
                        title: '团队管理',
						index: 'admin_admin',
						subs:[{
							icon: 'el-icon-edit-outline',
							index: 'admin_admin_user',
							title: '团队成员'
						},{
							icon: 'el-icon-edit-outline',
							index: 'admin_admin_rank',
							title: '权限分类'
						},{
							icon: 'el-icon-edit-outline',
							index: 'admin_admin_myFunc',
							title: '我的权限'
						}],
                    },{
                        icon: 'iconfont icon-yonghuguanli',
                        title: '用户管理',
						index: '1',
						subs:[{
							icon: 'el-icon-edit-outline',
							index: 'admin_user_list',
							title: '用户列表'
						}]
                    },{
                        icon: 'iconfont icon-wenbenbianji',
                        title: '新闻管理',
                    	index: 'admin_news',
                    	subs:[{
                    		icon: 'iconfont ',
                    		index: 'admin_news_category',
                    		title: '分类管理'
                    	},{
                    		icon: 'iconfont ',
                    		index: 'admin_news_add',
                    		title: '发布新闻'
                    	},{
                    		icon: 'iconfont ',
                    		index: 'admin_news_list',
                    		title: '新闻列表'
                    	}]
                    }
                ]
            }
        },
        computed:{
            onRoutes(){
				return 1;
                // return this.$router.path.replace("",'');
            }
        },
        methods: {
        	      handleSelect(key, keyPath) {
                      if(key=='/'){
                          var domain = window.location.host;
                          window.open('http://'+domain)
                      }else{
                           this.$router.push({ name: key, params: { userId: 123 }})   
                      }
                      
                }
        },
        created(){
        }
    }
</script>

<style scoped>
   .sidebar{
		height: 100%;
		display: block;
		left: 0;
        top: 0px;
        bottom:0;
    }
    .iconfont{
        padding-right: 5px;
    }
    .sidebar::-webkit-scrollbar{
        width: 0;
    }
    .sidebar-el-menu:not(.el-menu--collapse){
        width: 250px;
    }
</style>
