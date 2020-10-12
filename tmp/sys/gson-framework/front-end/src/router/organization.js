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
    path: 'org',
    name: 'OrgList',
    component: () => import('../views/organization/Org.vue'),
    meta: {title: '组织管理', group: '组织结构'}
  },
  {
    path: 'org/form/:id(\\d+)?',
    name: 'OrgForm',
    props: true,
    component: () => import('../views/organization/OrgForm.vue'),
    meta: {title: '组织信息'}
  },
  {
    path: 'dept',
    name: 'deptList',
    component: () => import('../views/organization/Dept.vue'),
    meta: {title: '部门管理', group: '组织结构'}
  },
  {
    path: 'dept/:org(\\d+)?/form/:id(\\d+)?',
    name: 'DeptForm',
    props: true,
    component: () => import('../views/organization/DeptForm.vue'),
    meta: {title: '部门信息'}
  },
  {
    path: 'position',
    name: 'PositionList',
    component: () => import('../views/organization/Position.vue'),
    meta: {title: '职位管理', group: '组织结构'}
  },
  {
    path: 'position/:org(\\d+)?/form/:id(\\d+)?',
    name: 'PositionForm',
    props: true,
    component: () => import('../views/organization/PositionForm.vue'),
    meta: {title: '职位信息'}
  },
  {
    path: 'employees',
    name: 'EmployeesList',
    component: () => import('../views/organization/Employees.vue'),
    meta: {title: '员工管理', group: '组织结构'}
  },
  {
    path: 'employees/:dept(\\d+)?/form/:id(\\d+)?',
    name: 'EmployeesForm',
    props: true,
    component: () => import('../views/organization/EmployeesForm.vue'),
    meta: {title: '员工信息'}
  }
]
