# Etapa 1: compilar
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
RUN ls -R /app/applications/app-service/target

# Etapa 2: ejecutar
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/applications/app-service/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]


