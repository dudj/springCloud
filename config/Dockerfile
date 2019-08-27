FROM alpine:3.7

MAINTAINER dudj 13020078873@163.com

ADD target/*.jar config.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/config.jar"]