FROM alpine:3.7

ADD target/*.jar eureka.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/eureka.jar"]