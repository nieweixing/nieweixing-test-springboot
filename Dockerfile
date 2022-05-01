FROM maven:3.8-openjdk-8
MAINTAINER nieweixing nwx_qdlg@163.com
ADD . /opt
RUN mv /opt/target/spring-boot-helloworld-1.0.0-SNAPSHOT.jar /app.jar
WORKDIR /
RUN apt-get update -y
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y wget
RUN wget https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.13.0/jmx_prometheus_javaagent-0.13.0.jar
ADD ./prometheus-jmx-config.yaml /prometheus-jmx-config.yaml
EXPOSE 8080
ENTRYPOINT java -javaagent:./jmx_prometheus_javaagent-0.13.0.jar=8081:/opt/prometheus-jmx-config.yaml -jar /app.jar
