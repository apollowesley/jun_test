/**
 * 系统日志API
 */
const log = {
    //分页查询
    getListByPage: api.admin + "sysLog/getListByPage",
    //批量删除
    batchDelete: api.admin + "sysLog/batchDelete",
    //导出
    export: api.admin + "sysLog/logExport",
};