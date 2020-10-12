@echo off

rem 环境准备
setlocal
call setclasspath.bat

rem jar执行之前需要将相关的依赖lib拷贝到相应的目录下
xcopy /Y %_LIB_PATH%\*.jar %_CONTEXT_JAR_PATH%\%_LIB_PATH%

rem 不包含清单文件
rem jar -cvf %_JAR_FILE_PATH% -C %_OUT_PATH% .

rem 包含清单文件
jar -cvfm %_JAR_FILE_PATH% %_MANIFEST% -C %_OUT_PATH% .
endlocal
