echo off
set classpath=lib/dsb-boot-obf-1.0.jar;lib/commons-codec-1.6.jar;lib/mybatis-3.2.8.jar;lib/command-linker-3.0.jar
set java_opt=-Xms512m -Xmx1024m -Xss1024K -XX:PermSize=128m -XX:MaxPermSize=512m
java %java_opt% -cp %classpath% common.framework.dsb.client.ServiceDeployer %1 %2 %3
echo on