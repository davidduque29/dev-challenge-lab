# 🏥 API de Hospitales con Paginación en Spring Boot

## 📘 Descripción general

Este proyecto implementa una API REST sencilla en **Spring Boot** que gestiona una lista de hospitales simulando una base de datos en memoria.  
Incluye un endpoint para **paginación manual**, lo que permite consultar los hospitales por páginas según parámetros `page` y `size`.

---

## ⚙️ Estructura principal

- **Hospital.java** → modelo que representa los datos del hospital.
- **HospitalService.java** → contiene la lógica de negocio, incluyendo la función de paginación.
- **HospitalController.java** → expone los endpoints REST.
- **SpringBoot2Application.java** → clase principal que inicia la aplicación.

---

## 🚀 Endpoints principales

| Método | Endpoint | Descripción |
|--------|-----------|-------------|
| `GET` | `/api/hospitals` | Lista todos los hospitales |
| `GET` | `/api/hospitals/{id}` | Devuelve un hospital por su ID |
| `GET` | `/api/hospitals/page?page=1&size=5` | Devuelve hospitales paginados |

---

## 🧮 Lógica de paginación

La paginación se basa en dos parámetros:
- `page`: número de página (empieza en 1).
- `size`: cantidad de elementos por página.

### 🔹 Cálculo del índice inicial
```java
int start = (page - 1) * size;
```
# 🏥 API de Hospitales con Paginación en Spring Boot

## 📘 Descripción general

Este proyecto implementa una API REST sencilla en **Spring Boot** que gestiona una lista de hospitales simulando una base de datos en memoria.  
Incluye un endpoint para **paginación manual**, lo que permite consultar los hospitales por páginas según parámetros `page` y `size`.

---

## ⚙️ Estructura principal

- **Hospital.java** → modelo que representa los datos del hospital.  
- **HospitalService.java** → contiene la lógica de negocio, incluyendo la función de paginación.  
- **HospitalController.java** → expone los endpoints REST.  
- **SpringBoot2Application.java** → clase principal que inicia la aplicación.

---

## 🚀 Endpoints principales

| Método | Endpoint | Descripción |
|--------|-----------|-------------|
| `GET` | `/api/hospitals` | Lista todos los hospitales |
| `GET` | `/api/hospitals/{id}` | Devuelve un hospital por su ID |
| `GET` | `/api/hospitals/page?page=1&size=5` | Devuelve hospitales paginados |

---

## 🧮 Lógica de paginación

La paginación se basa en dos parámetros:
- `page`: número de página (empieza en 1).
- `size`: cantidad de elementos por página.

### 🔹 Cálculo del índice inicial
```java
int start = (page - 1) * size;
```
Si page = 1 → start = 0

Si page = 2 → start = 5

Si page = 3 → start = 10

De esta manera, cada página “salta” los elementos de las anteriores.

🧠 Flujo funcional

El cliente realiza una solicitud HTTP con page y size.

El controlador envía esos valores al servicio.

El servicio calcula el rango (start, end) de elementos a devolver.

Se usa subList(start, end) para cortar la lista original.

El resultado se devuelve como JSON.

```java
GET /api/hospitals/page?page=2&size=5

```
 

```json
[
  {
    "id": 1006,
    "name": "Kauvery Hospital",
    "city": "Trichy",
    "rating": 3.7
  },
  {
    "id": 1007,
    "name": "Manipal Hospital",
    "city": "Hyderabad",
    "rating": 4.0
  },
  {
    "id": 1008,
    "name": "Columbia Asia Hospital",
    "city": "Pune",
    "rating": 3.9
  },
  {
    "id": 1009,
    "name": "Aster Medcity",
    "city": "Kochi",
    "rating": 4.4
  },
  {
    "id": 1010,
    "name": "Amrita Institute of Medical Sciences",
    "city": "Kochi",
    "rating": 4.5
  }
]

```
Conclusión

La paginación se logra sin base de datos, solo con manipulación de listas.

Es una base excelente para después integrar Spring Data JPA y paginación con Pageable.

Esta lógica es reutilizable para cualquier colección de datos en memoria o respuesta parcial de una API.
