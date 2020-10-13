@echo off
title dsb-3.5 server
@echo Ctrl+C to terminate server
set classpath=lib/dsb-bootstrap-obf-1.0.jar;lib/commons-codec-1.6.jar;lib/mybatis-3.2.8.jar;lib/command-linker-3.0.jar
set java_opt=-Xms512m -Xmx1024m -Xss1024K -XX:PermSize=128m -XX:MaxPermSize=512m
set rmi_opt=-Dsun.rmi.transport.proxy.connectTimeout=200 -Dsun.rmi.transport.tcp.responseTimeout=200
set boot_opt=lib/dsb-3.5.jar common.framework.dsb.main.Main dsb.key dsb.enc dsb.lcs
java %rmi_opt% %java_opt% -cp %classpath% com.binarysoft.dsb.bootstrap.DSBBootstrap %boot_opt%
echo on