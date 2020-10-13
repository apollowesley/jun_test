/**
 * 系统权限API
 */
var permission = {
    //根据用户获取导航树形
    getNavTree: api.admin + "sysPermission/getNavTree",
    //获取全部树
    getAllTree: api.admin + "sysPermission/getAllTree",
    //获取角色新增树
    getRoleTree: api.admin + "sysPermission/getRoleTree",
    //根据角色获取权限
    getAllTreeByRoleId: api.admin + "sysPermission/getAllTreeByRoleId/",
    //根据id获取实体
    getPermissionById: api.admin + "sysPermission/get/",
    //编辑,没有id就新增，有id就修改
    submit: api.admin + "sysPermission/submit",
    //删除
    deleteById: api.admin + "sysPermission/delete/",
    //根据父id查找
    getListByParentId: api.admin + "sysPermission/getListMap/parentId/",
    //获取全部列表
    getList: api.admin + "sysPermission/getList",
    //新增
    addPermission:api.admin+"sysPermission/add",
   
};