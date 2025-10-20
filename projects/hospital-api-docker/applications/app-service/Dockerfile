# Imagen base con Java 22
FROM openjdk:22-jdk-slim
WORKDIR /app
COPY target/*.jar app.jar
COPY src/main/resources/application.properties /app/config/application.properties
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/app/config/application.properties"]
EXPOSE 8080


