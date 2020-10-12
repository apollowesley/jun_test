FROM alpine

MAINTAINER lijiantao

# 拷贝jdk、maven 文件
ADD jdk1.8.0_211 /usr/local/jdk
ADD apache-maven-3.6.1 /usr/local/maven

#安装alpine下 jdk 底层 Glibc
RUN apk --no-cache add ca-certificates wget
RUN wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub
RUN wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.29-r0/glibc-2.29-r0.apk
RUN apk add glibc-2.29-r0.apk

#删除
RUN rm glibc-2.29-r0.apk

#环境变量
ENV JAVA_HOME=/usr/local/jdk
ENV CLASSPATH=${JAVA_HOME}/lib/
ENV MAVEN_HOME=/usr/local/maven
ENV PATH=.:${JAVA_HOME}/bin:${MAVEN_HOME}/bin:$PATH
