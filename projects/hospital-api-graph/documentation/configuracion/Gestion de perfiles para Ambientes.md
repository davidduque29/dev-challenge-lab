# 🏥 Guía de Configuración de Perfiles en Spring Boot con MongoDB

## 🎯 Objetivo
Configurar **dos perfiles de ejecución (`local` y `cloud`)** para que la aplicación se conecte automáticamente a la base de datos correspondiente (MongoDB local o MongoDB Atlas), sin necesidad de cambiar el código manualmente.

---

## 📁 Estructura de archivos

```json 
src/
└─ main/
├─ java/
│ └─ com.example.hospitalapi/
│ ├─ controller/
│ ├─ model/
│ ├─ repository/
│ ├─ service/
│ ├─ SpringBoot2Application.java
│ └─ StartupLogger.java
└─ resources/
├─ application.properties
├─ application-local.properties
└─ application-cloud.properties
```

---

## ⚙️ Configuración de Propiedades

### 1️⃣ `application.properties` → Archivo base común
```properties
spring.application.name=hospital-api
server.port=8080
```
Este archivo contiene solo la configuración común.
No debe incluir la URI de Mongo ni el perfil activo.


2️⃣ application-local.properties → Perfil local (base de datos en tu equipo)
spring.data.mongodb.uri=mongodb://localhost:27017/hospitaldb
spring.data.mongodb.database=hospitaldb

3️⃣ application-cloud.properties → Perfil cloud (MongoDB Atlas) user=mongouser pass=password
spring.data.mongodb.uri=mongodb+srv://{user}:{pass}@hospitalcluster.0dey0la.mongodb.net/hospitalapi?retryWrites=true&w=majority&appName=hospitalCluster
spring.data.mongodb.database=hospitalapi

🧠 Activación de Perfiles

Spring Boot carga automáticamente:

application.properties (siempre)

application-{profile}.properties (solo el activo)

El perfil activo se define con la propiedad:

```json
spring.profiles.active=<nombre_del_perfil>
```
Pero no se coloca dentro del archivo,
sino que se pasa por parámetro en la ejecución.

🧰 Configuración en IntelliJ IDEA

Abre Run → Edit Configurations...

Crea dos configuraciones de ejecución de tipo Spring Boot:

🖥️ Configuración Local

Name: Hospital API - Local

Main class: com.example.hospitalapi.SpringBoot2Application

VM Options:

```json
-Dspring.profiles.active=local
```
Working directory: raíz del proyecto

☁️ Configuración Cloud

Name: Hospital API - Cloud

Main class: com.example.hospitalapi.SpringBoot2Application

VM Options:

```json
-Dspring.profiles.active=cloud
```
🚀 Ejecución por Línea de Comando (opcional)
Modo local
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

Modo cloud
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=cloud
```
🧾 Clase StartupLogger.java

Esta clase detecta el perfil activo y muestra mensajes visuales al iniciar la aplicación, 
indicando qué entorno se está usando y a qué base se conectó.

```java
package com.example.hospitalapi;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger {

    private final Environment environment;

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    public StartupLogger(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void logStartupInfo() {
        String[] profiles = environment.getActiveProfiles();
        String activeProfile = profiles.length > 0 ? profiles[0] : "default";

        System.out.println("--------------------------------------------------------");
        System.out.println("🚀 Aplicación iniciando: " + environment.getProperty("spring.application.name"));
        System.out.println("🌍 Perfil activo: " + activeProfile.toUpperCase());

        if (activeProfile.equalsIgnoreCase("local")) {
            System.out.println("💻 Entorno: Local (MongoDB en localhost)");
        } else if (activeProfile.equalsIgnoreCase("cloud")) {
            System.out.println("☁️ Entorno: Cloud (MongoDB Atlas)");
        } else {
            System.out.println("⚙️ Entorno: Default (sin perfil específico)");
        }

        System.out.println("🔗 URI de conexión: " + mongoUri);
        System.out.println("✅ Aplicación lista en http://localhost:" + environment.getProperty("server.port"));
        System.out.println("--------------------------------------------------------");
    }
}
```
🧪 Pruebas desde Postman
Crear hospital (POST)
POST http://localhost:8080/hospitals
```json
Body (raw, JSON):
{
    "name": "Apollo Hospital",
    "city": "Chennai",
    "rating": 4.2
}
```
🧩 Resultado Esperado
Si corres con perfil local
```console  
💻 Entorno: Local (MongoDB en localhost)
🔗 URI de conexión: mongodb://localhost:27017/hospitaldb
✅ Aplicación lista en http://localhost:8080
```
Si corres con perfil cloud
```console  
☁️ Entorno: Cloud (MongoDB Atlas)
🔗 URI de conexión: mongodb+srv://mongouser@hospitalcluster.mongodb.net/hospitalapi
✅ Aplicación lista en http://localhost:8080
```
✅ Conclusión

Con esta configuración:

No es necesario editar archivos entre entornos.
Puedes alternar perfiles desde IntelliJ o consola.
Los logs indican claramente el entorno activo.
MongoDB Compass o Atlas reflejarán los datos según el perfil seleccionado.


> Renombrar el archivo a application.properties para que funcione en la nube y lo mismo en el caso de local

> En caso de que no quiera renombrar sí puedes tener varios archivos con nombres distintos,
> siempre que empiecen con application- y actives el perfil correcto.

> en este caso de NUBE desde la opcion de RUN>EditConfigurations.. en la opcion ModifyOptions,
> activa addVmOptions, una vez activa en VM Options, pon -Dspring-boot.run.profiles=CLOU

✍️ Autor: Iván David Duque
📅 Versión: Octubre 2025
📘 Proyecto: hospital-api-mongo

---
 
