FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/digital-champions-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "./app.jar"]