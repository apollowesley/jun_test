/**
 * 系统角色API
 */
var role = {
    //获取全部角色列表
    getRoleList: api.admin + "sysRole/getList",
    //分页查询
    getListByPage: api.admin + "sysRole/getListByPage",
    //添加
    roleAdd: api.admin + "sysRole/add",
    //批量删除
    batchDelete: api.admin + "sysRole/batchDelete",
    //更新
    roleUpdate: api.admin + "sysRole/update",
    //根据id获得角色
    getRoleById: api.admin + "sysRole/get/",
}