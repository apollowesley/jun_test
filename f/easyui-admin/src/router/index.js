import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

const routes = [{
	path: '/',
	component: resolve => require(['@/views/layout/AppLayout.vue'], resolve),
	children: [{
			path: '/hello',
			component: resolve => require(['@/components/HelloWorld.vue'], resolve)
		},

		{
			path: '/test',
			component: resolve => require(['@/views/Test.vue'], resolve)
		},
		{
			path: '/other',
			component: resolve => require(['@/views/Other.vue'], resolve)
		},

		{
			path: '/table',
			component: resolve => require(['@/views/tables/Index.vue'], resolve)
		},
		{
			path: '/editTable',
			component: resolve => require(['@/views/tables/TableEdit.vue'], resolve)
		}

	]
}, {
	path: '/singledemo',
	component: resolve => require(['@/views/layout/SingleDemo.vue'], resolve)
}]


export default new Router({
	routes: routes
})
