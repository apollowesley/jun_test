/**
 *<p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : </li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年01月30日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
export default [
  {
    path: 'account',
    name: 'AccountList',
    component: () => import('../views/system/Account.vue'),
    meta: {title: '账号管理', group: '系统设置'}
  },
  {
    path: 'account/form/:id(\\d+)?',
    name: 'AccountForm',
    props: true,
    component: () => import('../views/system/AccountForm.vue'),
    meta: {title: '账号信息'}
  },
  {
    path: 'role',
    name: 'RoleList',
    component: () => import('../views/system/Role.vue'),
    meta: {title: '角色管理', group: '系统设置'}
  },
  {
    path: 'role/:org(\\d+)?/form/:id(\\d+)?',
    name: 'RoleForm',
    props: true,
    component: () => import('../views/system/RoleForm.vue'),
    meta: {title: '角色信息', group: '系统设置'}
  },
  {
    path: 'right',
    name: 'RightConfig',
    component: () => import('../views/system/Right.vue'),
    meta: {title: '权限配置', group: '系统设置'}
  }
]
