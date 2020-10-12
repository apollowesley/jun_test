# 指定基础镜像，必须为第一个命令
FROM reg.jdy.com/base/java-centos:oracle-8-jdk
# 维护这信息
MAINTAINER k8s-microservice
#用于指定传递给构建运行时的变量
#ARG docker-demo
#功能类似ADD，但是ls是不会自动解压文件，也不能访问网络资源
COPY target/boot-ms-admin.jar app.jar
#配置容器，使其可执行化。配合CMD可省去"application"，只使用参数。
ENTRYPOINT ["java","-jar","/app.jar"]