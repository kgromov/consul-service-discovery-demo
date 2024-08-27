FROM openjdk:22-jdk-slim
WORKDIR /app
COPY target/*.jar /app/account-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "account-service.jar"]