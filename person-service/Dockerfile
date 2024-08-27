FROM openjdk:22-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} person-service.jar
ENTRYPOINT ["java","-jar","/person-service.jar"]