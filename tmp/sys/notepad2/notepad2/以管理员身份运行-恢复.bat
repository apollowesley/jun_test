@ECHO OFF
PUSHD %~DP0
taskkill /f /im notepad*>NUL 2>NUL
Md "%WinDir%\System32\test_permissions" 2>NUL||(Echo 请使用右键管理员身份运行&&Pause >NUL&&Exit)
Rd "%WinDir%\System32\test_permissions" 2>NUL
SetLocal EnableDelayedExpansion
reg delete "HKLM\Software\Microsoft\Windows NT\CurrentVersion\Image File Execution Options\notepad.exe" /f
reg delete "HKLM\Software\Wow6432Node\Microsoft\Windows NT\CurrentVersion\Image File Execution Options\notepad.exe" /f
exit