FROM openjdk:8-jdk-alpine
MAINTAINER xiejiarong 937890254@qq.com
ADD /editor-service/front-service/build/libs/*.jar  /editor-front-service.jar
EXPOSE 8081
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005","-jar","/editor-front-service.jar","--spring.profiles.active=prod"]