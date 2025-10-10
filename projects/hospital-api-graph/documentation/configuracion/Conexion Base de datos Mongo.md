# 🏥 API de Hospitales con Paginación en Spring Boot

## 📘 Descripción general

🧩 1️⃣  En MongoDB Conexion local a MongoDB version yaml

Cuando usas JPA/Hibernate, sí necesitas una entity con la anotación @Entity 
para mapear la clase Java con una tabla de la base de datos.

---
### Ejemplo local:
```yaml
    spring:
    data:
      mongodb:
        uri: mongodb://localhost:27017/hospitaldb
        database: hospitaldb
```
🔸 Aquí el ORM (Hibernate) convierte tu clase en una tabla SQL.

🍃 2️⃣  En MongoDB Conexion local a MongoDB version properties

### Ejemplo local:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/hospitaldb
spring.data.mongodb.database=hospitaldb 
```
---
En MongoDB Atlas Conexion nube a MongoDB


---
👉 Solo se coloca spring.profiles.active en un solo archivo: application.properties
No debe estar en application-local.properties ni en application-cloud.properties

spring.profiles.active=local   # 🖥️ usa tu Mongo local
spring.profiles.active=cloud   # ☁️ usa tu Mongo Atlas

Spring Boot funciona así cuando arranca tu app:

Siempre busca primero el archivo principal:
application.properties

Dentro de ese archivo, si encuentra esto 👇
spring.profiles.active=local

entonces automáticamente busca y carga:
application-local.properties

💡 MongoDB guarda los datos así:
Request
```json
{
  "_id": "672e4d9a...",
  "name": "Apollo Hospital",
  "city": "Chennai",
  "rating": 4.2
}

```
Response

```json
{
  "id": "68e6933cad084572aa56041e",
  "name": "Apollo Hospital",
  "city": "Chennai",
  "rating": 4.6
}
```
---
Request
```json
{
  "name": "Hospital San Vicente",
  "city": "Medellín",
  "rating": 4.8
}
```
Response

```json
{
  "id": "68e6933cad084572aa56041e",
  "name": "Apollo Hospital",
  "city": "Chennai",
  "rating": 4.6
}
```
 
