FROM openjdk:11-jdk
ARG JAR_FILE=filters-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]