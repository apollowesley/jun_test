@echo off
rem 项目约束结构配置
set _SOURCE_SRC=src

rem 三方jar包依赖存放位置
set _LIB_PATH=lib

rem 源代码编译输出目录
set _OUT_PATH=myout\production\JavaApplication

rem 包名前缀
set _PACKAGE_NAME=cn.kiwipeach.demo
rem 包的全路径
set _PACKAGE_PATH=cn\kiwipeach\demo

rem 编译缓存目录文件
set _TEMP_PATH=myout\temp
set _TEMP_CACHE_FILE=java_list.txt
IF NOT EXIST %_TEMP_PATH% MD %_TEMP_PATH%
set _JAVA_TEMP_FILES_INFO=%_TEMP_PATH%\%_TEMP_CACHE_FILE%

rem 打包文件目录
set _CONTEXT_JAR_PATH=myout
set _CONTEXT_JAR_PATH_FILE=helloworld.jar
IF NOT EXIST %_CONTEXT_JAR_PATH%\%_LIB_PATH% MD %_CONTEXT_JAR_PATH%\%_LIB_PATH%
set _JAR_FILE_PATH=%_CONTEXT_JAR_PATH%\%_CONTEXT_JAR_PATH_FILE%
rem 打包时用的清单文件
set _MANIFEST=MANIFEST.txt

rem compile Class-Path（项目编译的时候依赖的jar包）
set PROJECT_CLASS_PATH=%_OUT_PATH%;%_LIB_PATH%\hutool-all-4.5.1.jar

rem jar runtime Class-Path(jar包运行时候依赖的jar包)
set PROJECT_JAR_PATH=%_JAR_FILE_PATH%;%_LIB_PATH%\hutool-all-4.5.1.jar
