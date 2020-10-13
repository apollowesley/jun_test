package org.neuedu.crm.system.util;

import org.neuedu.crm.system.entity.SysUser;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/12/14-15:18
 **/
public class ConstUtil {

    /** 默认日期时间格式 */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /** 默认日期格式 */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    /** 默认时间格式 */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    /** 当前登录用户 */
    public static final String CURRENT_USER = "currentUser";

    //系统角色
    public interface SysRole {
        int ADMINISTRATOR = 1;  //管理员
        int MAIN_MANAGER = 2;  //主管
        int ACCOUNT_MANAGER = 3; //客户经理
        int MANAGER = 4; //高管
    }

    //销售机会状态
    public interface OpportunityState{
        int NOT_ASSIGNED = 0;  //未指派
        int ASSIGNED = 1;  //已指派
        int MARKE_PLANED = 2;  //已制定计划项
        int EXECUTE = 3;  //执行中
        int DEVELOP_SUCCESS = 4;  //开发成功
        int DEVELOP_FAILURE = 5; //开发失败
    }

}
