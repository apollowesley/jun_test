
FROM centos:latest

ADD target/test-maven-api.jar /app.jar
ADD ./jdk-11.0.1 /var/local/jdk11
ENV JAVA_HOME /var/local/jdk11/bin
ENV PATH=$PATH:$JAVA_HOME
EXPOSE 8080
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:InitialRAMPercentage=50 -XX:MaxRAMPercentage=80"
#ENV JAVA_OPTS="-Xms4g -Xmx4g"
ENTRYPOINT java ${JAVA_OPTS} -jar /app.jar
