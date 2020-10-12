@echo off
setlocal

call setclasspath.bat
rem Input Main Class
set /P USER_INPUT_MAIN=please input run main class(default is Main):
rem set MAIN_CLASS=%1
echo "%USER_INPUT_MAIN%"
if "%USER_INPUT_MAIN%"=="" (
   set MAIN_CLASS=%_PACKAGE_NAME%.Main kiwipeach@qq.com
) else (
   set MAIN_CLASS=%_PACKAGE_NAME%.%USER_INPUT_MAIN% %USER_INPUT_MAIN%@qq.com
)
rem Main-Class
rem set MAIN_CLASS=%_PACKAGE_NAME%.JvmMain liuburu@qq.com

rem 执行命令
java -cp %PROJECT_CLASS_PATH% %MAIN_CLASS%

endlocal
