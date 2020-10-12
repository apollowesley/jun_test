@echo off
setlocal
rem 环境准备
call setclasspath.bat
rem 编译源代码
call compile.bat
rem 执行源代码
call execute.bat
endlocal