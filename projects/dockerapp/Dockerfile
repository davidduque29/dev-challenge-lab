# Imagen base con Java 22
FROM eclipse-temurin:22-jdk

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR generado al contenedor
COPY target/dockerapp-0.0.1-SNAPSHOT.jar app.jar

# Puerto que expone el contenedor
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
