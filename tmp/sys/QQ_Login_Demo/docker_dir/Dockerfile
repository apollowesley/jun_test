#基础镜像
FROM tomcat
MAINTAINER www.kiwipeach.cn
#部署包上传路径
ENV UPLOAD_DIR /data/tomcat/upload
#tomcat服务发布路径
ENV TOMCAT_WEBAPPS /usr/local/tomcat/webapps
#清楚tomcat默认webapps目录下的文件
RUN  rm -rf $TOMCAT_WEBAPPS/*
#上传本地部署文件
ADD ./sdk4j_demo.war $UPLOAD_DIR/sdk4j_demo.war
#VOLUME $TOMCAT_WEBAPPS
#配置工作目录
WORKDIR $TOMCAT_WEBAPPS
#解压war包到ROOT目录
RUN unzip $UPLOAD_DIR/sdk4j_demo.war -d $TOMCAT_WEBAPPS/ROOT/
#暴露端口
EXPOSE 8080
#启动tomcat
CMD ["catalina.sh", "run"]
