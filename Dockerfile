FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/cloudtest-0.2.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080
