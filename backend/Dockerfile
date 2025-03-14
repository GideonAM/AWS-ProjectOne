FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} File-Server-Backend-0.0.1.jar

ENTRYPOINT ["java", "-jar", "/File-Server-Backend-0.0.1.jar"]