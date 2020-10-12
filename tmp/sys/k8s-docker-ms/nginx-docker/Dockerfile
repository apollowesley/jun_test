# 指定基础镜像，必须为第一个命令
FROM nginx
# 维护这信息
MAINTAINER k8s-microservice
#用于指定传递给构建运行时的变量
#ARG docker-demo
#功能类似ADD，但是ls是不会自动解压文件，也不能访问网络资源
COPY nginx.conf /etc/nginx/nginx.conf
#配置容器，使其可执行化。配合CMD可省去"application"，只使用参数。
# 容器启动时执行命令
#CMD ["/usr/sbin/nginx"]