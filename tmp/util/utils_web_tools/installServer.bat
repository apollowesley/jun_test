@echo off

set SERVICE_NAME=IntelliJIDEALicenseServer
set APP_DIRECTORY=D:\IntelliJIDEALicenseServer

%APP_DIRECTORY%\instsrv.exe %SERVICE_NAME% %APP_DIRECTORY%\srvany.exe

reg add HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Services\%SERVICE_NAME%\Parameters
reg add HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Services\%SERVICE_NAME%\Parameters /v AppDirectory /t REG_SZ /d %APP_DIRECTORY%
reg add HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Services\%SERVICE_NAME%\Parameters /v Application /t REG_SZ /d %APP_DIRECTORY%\IntelliJIDEALicenseServer_windows_amd64.exe

net start %SERVICE_NAME%

PAUSE