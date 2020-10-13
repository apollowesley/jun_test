FROM openjdk:8-jre-alpine
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY target/iartisan-admin-template*.jar  /iartisan-admin-template.jar
EXPOSE 8080
VOLUME ["/wls"]

ENTRYPOINT ["java","-Dfile.encoding=utf-8","-jar","/iartisan-admin-template.jar"]