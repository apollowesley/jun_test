@rem Please give JAVA_HOME location to run benchmark, can't contains whitespace.
@set JAVA_HOME=D:\UserWork\JavaSDK\jdk7u55x64
@rem Please give libraries path.
@set Libraries=D:\UserWork\JavaSpace\discuss\teb\lib

@set PATH=.;%JAVA_HOME%\bin;
@set CLASSPATH=%CD%;
@for /F "delims=" %%i in ('dir /A:-D /B /S "%Libraries%"') do @if exist %%i (
	@call :SetClassPath %%i
)
@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\lib\tools.jar
@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\lib\dt.jar
@set CLASSPATH=%CLASSPATH%;%JAVA_HOME%\jre\lib\rt.jar

@%JAVA_HOME%\bin\java kiang.teb.Benchmark

@pause 

@rem ========================SetClassPath Function============================
:SetClassPath
@set CLASSPATH=%CLASSPATH%;%1
@GOTO :EOF