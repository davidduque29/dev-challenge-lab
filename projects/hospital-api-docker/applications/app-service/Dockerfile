# Etapa 1: compilar
FROM maven:3.9.6-eclipse-temurin-22 AS builder
WORKDIR /app
COPY . .
RUN mvn -f pom.xml clean package -DskipTests \
    && ls -R /app/applications

# Etapa 2: ejecutar
FROM openjdk:22-jdk-slim
WORKDIR /app
COPY --from=builder /app/applications/*/target/*.jar app.jar
# Si deseas la configuración, descomenta luego y ajusta la ruta
# COPY --from=builder /app/applications/*/src/main/resources/application.properties /app/config/application.properties
ENTRYPOINT ["java", "-jar", "app.jar"]




