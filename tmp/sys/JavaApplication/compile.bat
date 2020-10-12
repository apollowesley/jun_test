@echo off
rem 设置配置信息
setlocal
call setclasspath.bat
rem 需要编译的源文件对象，可以是正则表示打包多个
rem set _SOURCE_FILES=Main.java

rem Source-File
rem set SOURCE_FILE_LIST=%_SOURCE_SRC%\%_PACKAGE_PATH%\%_SOURCE_FILES%
rem set SOURCE_FILE_LIST=%_SOURCE_SRC%\cn\kiwipeach\demo\*.java
dir *.java /S/B > %_JAVA_TEMP_FILES_INFO%
set SOURCE_FILE_LIST=@%_JAVA_TEMP_FILES_INFO%

rem 如果不存在编译输出目录则创建编译输出目录
IF NOT EXIST %_OUT_PATH% MD %_OUT_PATH%

rem 编译到myout输出目录
javac  -encoding UTF-8 -cp %PROJECT_CLASS_PATH% -d %_OUT_PATH% %SOURCE_FILE_LIST%
rem javac  -encoding UTF-8 -cp %PROJECT_CLASS_PATH% -d %_OUT_PATH% %_SOURCE_SRC%\cn\kiwipeach\demo\*.java
endlocal