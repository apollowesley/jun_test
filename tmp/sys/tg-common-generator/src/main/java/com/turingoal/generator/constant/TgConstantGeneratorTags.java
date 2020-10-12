package com.turingoal.generator.constant;

/**
 * 常量-标签
 */
public interface TgConstantGeneratorTags {
    // 全局
    String TAG_GLOBAL_PROJECT_NAME = "projectName"; // 项目名称
    String TAG_GLOBAL_PROJECT_NAME_WARP = "${" + TAG_GLOBAL_PROJECT_NAME + "}"; // 项目名称
    String TAG_GLOBAL_PROJECT_TITLE = "projectTitle"; // 项目标题
    String TAG_GLOBAL_PROJECT_TITLE_WARP = "${" + TAG_GLOBAL_PROJECT_TITLE + "}"; // 项目标题
    String TAG_GLOBAL_PROJECT_TITLE_SHORT = "projectTitleShort"; // 项目标题（短）
    String TAG_GLOBAL_PROJECT_TITLE_SHORT_WARP = "${" + TAG_GLOBAL_PROJECT_TITLE_SHORT + "}"; // 项目标题（短）
    String TAG_GLOBAL_PROJECT_DESC = "projectDesc"; // 项目描述
    String TAG_GLOBAL_PROJECT_DESC_WARP = "${" + TAG_GLOBAL_PROJECT_DESC + "}"; // 项目描述
    String TAG_GLOBAL_BASE_PACKAGE = "basePackage"; // 包
    String TAG_GLOBAL_BASE_PACKAGE_WARP = "${" + TAG_GLOBAL_BASE_PACKAGE + "}"; // 包
    String TAG_GLOBAL_DB_URL = "datasourceUrl"; // 连接池url
    String TAG_GLOBAL_DB_URL_WARP = "${" + TAG_GLOBAL_DB_URL + "}"; // 连接池url
    String TAG_GLOBAL_DB_USERNAME = "datasourceUsername"; // 连接池用户名
    String TTAG_GLOBAL_DB_USERNAME_WARP = "${" + TAG_GLOBAL_DB_USERNAME + "}"; // 连接池用户名
    String TAG_GLOBAL_DB_PASSWORD = "datasourcePassword"; // db schema
    String TAG_GLOBAL_DB_PASSWORD_WARP = "${" + TAG_GLOBAL_DB_PASSWORD + "}"; // 连接池密码
    String TAG_GLOBAL_DB_SCHEMA = "schema"; // db schema
    String TAG_GLOBAL_DB_SCHEMA_WARP = "${" + TAG_GLOBAL_DB_SCHEMA + "}"; // db schema
    // 目录
    String TAG_DIR_BASE_PACKAGE = "basePackage_dir"; // 包路径
    String TAG_DIR_BASE_PACKAGE_WARP = "${" + TAG_DIR_BASE_PACKAGE + "}"; // 包路径
    String TAG_DIR_TABLE_NAME = "tableName_dir"; // tableName
    String TAG_DIR_TABLE_NAME_WARP = "${" + TAG_DIR_TABLE_NAME + "}"; // tableName
    String TAG_DIR_CLASS_NAME = "className_dir"; // className
    String TAG_DIR_CLASS_NAME_WARP = "${" + TAG_DIR_CLASS_NAME + "}"; // className
    String TAG_DIR_CLASS_NAME_FIRST_LOWER = "classNameFirstLower_dir"; // classNameFirstLower
    String TAG_DIR_CLASS_NAME_FIRST_LOWER_WARP = "${" + TAG_DIR_CLASS_NAME_FIRST_LOWER + "}"; // classNameFirstLower
    // 文件
    String TAG_FILE_TABLE_NAME = "tableName"; // tableName
    String TAG_FILE_TABLE_NAME_WARP = "${" + TAG_FILE_TABLE_NAME + "}"; // tableName
    String TAG_FILE_CLASS_NAME = "className"; // className
    String TAG_FILE_CLASS_NAME_WARP = "${" + TAG_FILE_CLASS_NAME + "}"; // className
    String TAG_FILE_CLASS_NAME_FIRST_LOWER = "classNameFirstLower"; // classNameFirstLower
    String TAG_FILE_CLASS_NAME_FIRST_LOWER_WARP = "${" + TAG_FILE_CLASS_NAME_FIRST_LOWER + "}"; // className
}
