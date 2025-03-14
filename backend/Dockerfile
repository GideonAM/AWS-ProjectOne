FROM openjdk:17

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} backend-0.0.1-SNAPSHORT.jar

ENTRYPOINT ["java", "-jar", "backend-0.0.1-SNAPSHORT.jar"]