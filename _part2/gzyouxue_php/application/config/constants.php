<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');

/*
|--------------------------------------------------------------------------
| File and Directory Modes
|--------------------------------------------------------------------------
|
| These prefs are used when checking and setting modes when working
| with the file system.  The defaults are fine on servers with proper
| security, but you may wish (or even need) to change the values in
| certain environments (Apache running a separate process for each
| user, PHP under CGI with Apache suEXEC, etc.).  Octal values should
| always be used to set the mode correctly.
|
*/
define('FILE_READ_MODE', 0644);
define('FILE_WRITE_MODE', 0666);
define('DIR_READ_MODE', 0755);
define('DIR_WRITE_MODE', 0777);

/*
|--------------------------------------------------------------------------
| File Stream Modes
|--------------------------------------------------------------------------
|
| These modes are used when working with fopen()/popen()
|
*/

define('FOPEN_READ',							'rb');
define('FOPEN_READ_WRITE',						'r+b');
define('FOPEN_WRITE_CREATE_DESTRUCTIVE',		'wb'); // truncates existing file data, use with care
define('FOPEN_READ_WRITE_CREATE_DESTRUCTIVE',	'w+b'); // truncates existing file data, use with care
define('FOPEN_WRITE_CREATE',					'ab');
define('FOPEN_READ_WRITE_CREATE',				'a+b');
define('FOPEN_WRITE_CREATE_STRICT',				'xb');
define('FOPEN_READ_WRITE_CREATE_STRICT',		'x+b');

/********************** 验证码常量 ************************/
define("AUTH_CODE_IMAGE_WIDTH", 100);
define("AUTH_CODE_IMAGE_HEIGHT", 40);
define("AUTH_CODE_LENGTH", 5);
define("AUTH_CODE_STRING", '23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz');

/********************** 用户资料中未填写 ************************/
define("PROFILE_EMPTY", "未填写");

/********************** 注册用户的默认头像地址 ************************/
define("DEFAULT_USER_ICON", "public/images/default-icon.png");

/********************** session key 常量 ************************/
define("LOGINED_USER", "logined_user");
define("LOGINED_ADMIN", "logined_admin");

define("SESSION_USER_UID", "uid");
define("SESSION_USER_EMAIL", "email");
define("SESSION_USER_ACCESS_TOKEN", "access_token");
define("SESSION_USER_OPENID", "openid");
define("SESSION_USER_NICKNAME", "nickname");
define("SESSION_USER_FIGURE", "figure");
define("SESSION_USER_TYPE", "user_type");
define("SESSION_USER_LOGINED_FROM", "logined_from");
define("SESSION_USER_BIND_WITH", "bind_with");

define("SESSION_ADMIN_UID", "uid");
define("SESSION_ADMIN_EMAIL", "email");
define("SESSION_ADMIN_REAL_NAME", "real_name");
define("SESSION_ADMIN_FIGURE", "figure");
define("SESSION_ADMIN_TYPE", "admin_type");

/********************** 用户以什么方式绑定账号，登录系统常量 ************************/
define("USER_LOGINED_FROM_QQ", "qq");
define("USER_LOGINED_FROM_WEIBO", "weibo");
define("USER_LOGINED_FROM_WECHAT", "wechat");
define("USER_LOGINED_FROM_EMAIL", "email");

define("BIND_WITH_QQ", "qq");
define("BIND_WITH_WEIBO", "weibo");
define("BIND_WITH_WECHAT", "wechat");
define("BIND_WITH_EMAIL", "email");

/********************** 管理员类型常量 ************************/
define("ADMIN_TYPE_SUPER", "super");
define("ADMIN_TYPE_NORMAL", "normal");

/********************** 用户类型常量 ************************/
define("USER_TYPE_NORMAL", "normal");
define("USER_TYPE_STUDENT", "student");
define("USER_TYPE_KID", "kid");
define("USER_TYPE_PARENT", "parent");
define("USER_TYPE_TEACHER", "teacher");

/********************** 教师类别常量 ************************/
define("TEACHER_TYPE_STUDENT", "在校大学生");
define("TEACHER_TYPE_TEACHER", "在职教师");
define("TEACHER_TYPE_OTHER", "其他");

/********************** 辅导状态常量 ************************/
define("TUTORSHIP_STATUS_BEGIN", "报名中");
define("TUTORSHIP_STATUS_ING", "辅导中");
define("TUTORSHIP_STATUS_END", "已结束");

/********************** 孩子状态常量 ************************/
define("STUDENT_STATUS_BEGIN", "报名中");
define("STUFENT_STATUS_ING", "辅导中");
define("STUDENT_STATUS_NONE", "无辅导");

/********************** 教师状态常量 ************************/
define("TEACHER_STATUS_NONE", "无辅导");
define("TEACHER_STATUS_ING", "辅导中");

/********************** 404页面未找到常量 ************************/
define("TO_PAGE_NOT_FOUND", "page_not_found");
define("PAGE_NOT_FOUND_TITLE", "页面未找到");

/********************** 前台页面常量 ************************/
define("TUTORSHIPS_PAGE_TITLE", "首页-所有辅导-优学网");
define("TUTORSHIP_PAGE_TITLE", "辅导详情-优学网");
define("STUDENTS_PAGE_TITLE", "所有学生-优学网");
define("STUDENT_PAGE_TITLE", "学生信息-优学网");
define("TEACHERS_PAGE_TITLE", "所有教师-优学网");
define("TEACHER_PAGE_TITLE", "教师信息-优学网");

/********************** 关于我们及相关页面标题常量 ************************/
define("ABOUT_PAGE_TITLE", "关于我们-优学网");
define("CONTACT_PAGE_TITLE", "联系我们-优学网");
define("EVALUATION_PAGE_TITLE", "用户评价-优学网");
define("HELP_PAGE_TITLE", "问题及帮助-优学网");
define("SUGGESTION_PAGE_TITLE", "留言及建议-优学网");
define("BLACKLIST_PAGE_TITLE", "黑名单-优学网");
define("PRIVACY_PAGE_TITLE", "隐私声明-优学网");
define("SCHOOL_PAGE_TITLE", "学校列表-优学网");
define("BRAND_PAGE_TITLE", "品牌列表-优学网");
define("SPONSOR_PAGE_TITLE", "赞助商列表-优学网");
define("ADS_PAGE_TITLE", "广告合作-优学网");
define("LINKS_PAGE_TITLE", "友情链接-优学网");
define("THANKS_PAGE_TITLE", "特别鸣谢-优学网");

/********************** 后台页面标题常量 ************************/
define("ADMIN_LOGIN_PAGE_TITLE", "登录-优学网后台管理");
define("ADMIN_TUTORSHIPS_PAGE_TITLE", "所有辅导-优学网后台管理");
define("ADMIN_TUTORSHIP_PAGE_TITLE", "辅导详情-优学网后台管理");
define("ADMIN_STUDENTS_PAGE_TITLE", "所有学生-优学网后台管理");
define("ADMIN_STUDENT_PAGE_TITLE", "学生详情-优学网后台管理");
define("ADMIN_TEACHERS_PAGE_TITLE", "所有教师-优学网后台管理");
define("ADMIN_TEACHER_PAGE_TITLE", "教师详情-优学网后台管理");
define("ADMIN_ADD_TUTORSHIP_PAGE_TITLE", "添加辅导-优学网后台管理");
define("ADMIN_ADD_STUDENT_PAGE_TITLE", "添加学生-优学网后台管理");
define("ADMIN_ADD_TEACHER_PAGE_TITLE", "添加教师-优学网后台管理");

define("PROFILE_PAGE_TITLE", "我的信息-优学网后台管理");

/********************** 用户管理页面标题常量 ************************/
define("USER_LOGIN_PAGE_TITLE", "用户登录-优学网");
define("USER_REG_PAGE_TITLE", "用户注册-优学网");

define("USER_PROFILE_TITLE", "我的信息-优学网");
define("USER_MY_KIDS_TITLE", "我的孩子-优学网");
define("USER_PROFILE_ADD_KID_TITLE", "添加孩子-优学网");
define("USER_PROFILE_COMPLETE_TEACHER_TITLE", "完善教师信息-优学网");
define("USER_PROFILE_COMPLETE_STUDENT_TITLE", "完善学生信息-优学网");
define("USER_PROFILE_EDIT_PAGE_TITLE", "编辑我的信息-优学网");
define("USER_SETTING_PAGE_TITLE", "账号设置-优学网");
define("USER_ADD_TUTORSHIP_KID_TITLE", "选择孩子-优学网");
define("USER_ADD_TUTORSHIP_INFO_TITLE", "添加辅导信息-优学网");
define("USER_EDIT_TUTORSHIP_KID_TITLE", "编辑辅导信息-优学网");
define("USER_TUTORSHIPS_TITLE", "我的所有辅导-优学网");
define("USER_TUTORSHIP_TITLE", "辅导详情-优学网");

/* End of file constants.php */
/* Location: ./application/config/constants.php */