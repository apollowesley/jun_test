classpath=lib/dsb-bootstrap-obf-1.0.jar:lib/commons-codec-1.6.jar:lib/mybatis-3.2.8.jar:lib/command-linker-3.0.jar
java_opt="-Xms128m -Xmx512m -Xss1024K -XX:PermSize=512m -XX:MaxPermSize=512m"
debug_opt="-Xdebug -Xrunjdwp:transport=dt_socket,address=10008,server=y,suspend=n"
boot_opt="lib/dsb-3.5.jar common.framework.dsb.server.Main dsb.key dsb.enc dsb.lcs"
java $debug_opt $java_opt -cp $classpath com.binarysoft.dsb.bootstrap.DSBBootstrap $boot_opt