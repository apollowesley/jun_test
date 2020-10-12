export default {
  user: {},
  sliderCollapsed: false,
  menu: [
    {
      title: '首页',
      key: 'Home',
      icon: 'h-icon-home'
    },
    {
      title: '组织结构',
      key: 'organization',
      icon: 'icon-layers',
      children: [
        {
          title: '组织管理',
          key: 'OrgList'
        },
        {
          title: '部门管理',
          key: 'deptList'
        },
        {
          title: '职位管理',
          key: 'PositionList'
        },
        {
          title: '员工管理',
          key: 'EmployeesList'
        }
      ]
    },
    {
      title: '系统设置',
      key: 'system',
      icon: 'icon-cog',
      children: [
        {
          title: '帐号管理',
          key: 'AccountList'
        },
        {
          title: '角色管理',
          key: 'RoleList'
        },
        {
          title: '权限配置',
          key: 'RightConfig'
        }
      ]
    }
  ]
}
