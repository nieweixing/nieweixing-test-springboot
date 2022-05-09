FROM maven:3.8-openjdk-8
MAINTAINER nieweixing nwx_qdlg@163.com
ADD . /opt
RUN mv /opt/target/spring-boot-helloworld-1.0.0-SNAPSHOT.jar /app.jar
WORKDIR /
RUN apt-get update -y
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y wget
EXPOSE 8080
ENTRYPOINT java -jar /app.jar
