export default [
  //login
  {
    path: '/login',
    name: 'login',
    component: '../layouts/BlankLayout',
    routes: [
      { 
        path: '/login', 
        component: './Login/Index',
      }
    ]
  },
  // app
  {
    path: '/',
    component: '../layouts/BasicLayout',
    Routes: ['src/pages/Authorized'],
    authority: 'admin',
    routes: [
      {
        path: '/',
        redirect: '/system/user',
      },
      {
        path: '/system',
        name: 'system',
        routes: [
          {
            path: '/system/user',
            name: 'user',
            component: './System/User/Index',
          },
          {
            path: '/system/menu',
            name: 'menu',
            component: './System/Menu/Index',
          },
          {
            path: '/system/role',
            name: 'role',
            component: './System/Role/Index',
          },
        ],
      },
      {
        path: '/operation',
        name: 'operation',
        routes:[
          {
            path: '/operation/log',
            name: 'log',
            component: './Operation/Log/Index'
          },
          {
            path: '/operation/article',
            name: 'article',
            routes:[
              {
                path: '/operation/article',
                component: './Operation/Article/Index'
              },{
                path: '/operation/article/:id',
                name: 'edit',
                component: './Operation/Article/Edit'
              }
            ]
          },
          {
            path: '/operation/sqlmonitor',
            name: 'sqlmonitor',
            component: './Operation/SqlMonitor/Index'
          }
        ]
      },
      {
        component: '404',
      },
    ],
  },
];
