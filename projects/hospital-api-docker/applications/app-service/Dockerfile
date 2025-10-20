# Etapa 1: construir to do el proyecto con el POM raíz
FROM maven:3.9.6-eclipse-temurin-22 AS builder
WORKDIR /app
COPY . .
RUN mvn -f pom.xml clean package -DskipTests

# Etapa 2: ejecutar el microservicio principal
FROM openjdk:22-jdk-slim
WORKDIR /app
# Ajusta el nombre del jar según el que se genere (ver target/)
COPY applications/**/target/*.jar app.jar
COPY src/main/resources/application.properties /app/config/application.properties
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/app/config/application.properties"]



