# Etapa 1: compilar la app
FROM maven:3.9.6-eclipse-temurin-22 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: ejecutar la app
FROM openjdk:22-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
COPY src/main/resources/application.properties /app/config/application.properties
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/app/config/application.properties"]



