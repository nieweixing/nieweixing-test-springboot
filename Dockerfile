FROM maven:3.8-openjdk-8
MAINTAINER nieweixing nwx_qdlg@163.com
ADD . /opt
RUN cd /opt && mvn clean package -Dmaven.test.skip=true -Dhttps.protocols=TLSv1.2	
RUN mv /opt/target/spring-boot-helloworld-1.0.0-SNAPSHOT.jar /app.jar
EXPOSE 8080
#ENTRYPOINT ["/bin/sh","/opt/run.sh"]
#ENTRYPOINT java -jar -Djava.security.egd=file:/dev/./urandom /app.jar  > /data/app.log
ENTRYPOINT ["java","-jar","-Djava.security.egd=file:/dev/./urandom","/app.jar"]
