@echo off
setlocal
call setclasspath.bat
rem 没有清单文件MANIFEST.MF时候，使用“java -cp”执行jar包文件
rem java -cp %PROJECT_JAR_PATH% cn.kiwipeach.demo.Main

rem 有清单文件MANIFEST.MF时候，使用“java -jar”执行jar包文件
rem xcopy lib\*.jar myout\lib
java -jar %_JAR_FILE_PATH%
endlocal