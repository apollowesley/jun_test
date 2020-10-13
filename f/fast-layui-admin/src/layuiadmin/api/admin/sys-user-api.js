/**
 * 系统用户API
 */
var user = {
    //登录
    login: api.admin + "sysUser/login",
    //退出
    logout: api.admin + "sysUser/logout",
    //修改状态
    updateStatus: api.admin + "sysUser/updateStatus/",
    //获取个人资料
    getUserInfo: api.admin + "sysUser/getUserInfo",
    //分页查询
    getListByPage: api.admin + "sysUser/getListByPage",
    //批量删除
    batchDelete: api.admin + "sysUser/batchDelete",
    //新增
    userAdd: api.admin + "sysUser/add",
    //修改
    userUpdate: api.admin + "sysUser/update",
    //根据id获取
    getUserById: api.admin + "sysUser/get/"
}