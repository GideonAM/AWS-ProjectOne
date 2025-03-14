FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} project-one-backend.jar

ENTRYPOINT ["java", "-jar", "/project-one-backend.jar"]