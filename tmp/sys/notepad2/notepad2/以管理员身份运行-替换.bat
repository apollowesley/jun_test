@ECHO OFF
PUSHD %~DP0
taskkill /f /im notepad*>NUL 2>NUL
Md "%WinDir%\System32\test_permissions" 2>NUL||(Echo 请使用右键管理员身份运行&&Pause >NUL&&Exit)
Rd "%WinDir%\System32\test_permissions" 2>NUL
SetLocal EnableDelayedExpansion
if not exist "%WinDir%\SysWOW64" reg add "HKLM\Software\Microsoft\Windows NT\CurrentVersion\Image File Execution Options\notepad.exe" /v "Debugger" /t REG_SZ /d "\"%~dp0Notepad2.exe\" /z" /f
if exist "%WinDir%\SysWOW64" reg add "HKLM\Software\Microsoft\Windows NT\CurrentVersion\Image File Execution Options\notepad.exe" /v "Debugger" /t REG_SZ /d "\"%~dp0Notepad2_x64.exe\" /z" /f
exit
