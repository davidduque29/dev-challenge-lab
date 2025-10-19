# 🏥 Hospital API - Versionamiento y Evolución del Proyecto

**Autor:** Iván David Duque Perdomo  
**Lenguaje base:** Java 22  
**Arquitectura:** Clean Architecture (multi-módulo Maven)  
**Gestor de dependencias:** Maven  
**Repositorio raíz:** `hospital-api`

---

## 📘 Visión general

El proyecto **Hospital API** nace como una plataforma modular para la gestión hospitalaria, basada en **microservicios** con arquitectura limpia y soporte para **GraphQL**, **MongoDB**, **RabbitMQ** y **Docker**.  
Su evolución ha seguido un esquema incremental de versiones, donde cada submódulo representa un hito funcional.

---

## 🧩 Versiones y Módulos

### 🏗️ **v1.0 – `hospital-api` (Base del proyecto)**
**Descripción:**  
Primera versión del sistema. Implementa la estructura base Clean Architecture con módulos `model`, `usecase`, `driven-adapters`, y `entry-points`.

**Principales implementaciones:**
- Estructura generada con *Project Generator Clean v2*.
- Configuración inicial de Maven multi-módulo.
- Definición de entidades del dominio: `PacienteDocument`, `Camilla`, `Hospital`.
- Configuración de perfiles `local` y `cloud`.
- Integración básica con MongoDB (URI configurable).
- Soporte inicial para variables de entorno mediante `.env`.

---

### 🗄️ **v1.1 – `hospital-api-mongo`**
**Descripción:**  
Extiende la base agregando una integración robusta con **MongoDB Atlas**.

**Implementaciones destacadas:**
- Configuración avanzada de `spring.data.mongodb.*` con timeout y reconexión.
- Optimización del cliente Mongo para entornos cloud y QA.
- Repositorios reactivos (`ReactiveMongoRepository`).
- Mecanismo de pruebas locales con `mongo:latest` en Docker.
- Documentación de configuración de entorno (`application-cloud.properties`).

---

### 🔍 **v1.2 – `hospital-api-graph`**
**Descripción:**  
Introducción de **GraphQL** para exposición de servicios hospitalarios.

**Implementaciones destacadas:**
- Configuración de dependencias `spring-boot-starter-graphql`.
- Creación de queries y mutations:
    - `pacientePorId`, `camillaPorId`, `crearPaciente`, `liberarCamilla`.
- Uso de Altair y Postman para pruebas de consultas.
- Validación de errores con `ControllerAdvisor`.
- Documentación de endpoints en formato `.graphqls`.

---

### 📡 **v1.3 – `hospital-api-events`**
**Descripción:**  
Implementa la **comunicación asíncrona** con RabbitMQ para eventos hospitalarios.

**Implementaciones destacadas:**
- Creación de colas y exchanges: `hospital.events.exchange`, `hospital.events.queue`.
- Definición de eventos: `CamillaLiberadaEvent`, `PacienteAdmitidoEvent`.
- Publicación de eventos en capa de dominio con `EventPublisherPort`.
- Integración con microservicios a través de `RabbitMQTemplate`.
- Logging detallado de envío y recepción.

---

### ⚙️ **v1.4 – `hospital-api-modules`**
**Descripción:**  
Consolidación modular del ecosistema de microservicios hospitalarios.

**Implementaciones destacadas:**
- Separación lógica por dominios:
    - `ms_pacientes`, `ms_camillas`, `ms_tareas`, `ms_hospital`.
- Integración entre módulos mediante eventos internos.
- Normalización de estructuras Maven.
- Incorporación de `project-generator-clean-v2` como dependencia.
- Implementación de controladores por módulo con `@RestController` y `@GraphQlController`.

---

### 🐳 **v1.5 – `hospital-api-docker`**
**Descripción:**  
Despliegue del ecosistema completo usando **Docker**.

**Implementaciones destacadas:**
- Creación de `Dockerfile` base para el microservicio hospitalario.
- Configuración de `docker-compose.yml` con contenedores para:
    - `mongo`, `rabbitmq`, `hospital-api`.
- Variables de entorno para conexión a Mongo y RabbitMQ.
- Pruebas locales exitosas con comunicación entre contenedores.
- Preparación para despliegue gratuito en **Render.com** y **GHCR.io**.

---

## 🚀 Próximos hitos (planeado v1.6+)
- Despliegue continuo con **GitHub Actions**.
- Monitoreo y trazabilidad con **Prometheus + Grafana**.
- Generación de imágenes GHCR automatizadas.
- Versión front-end con **Vue 3 + GraphQL Client**.

---

## 📄 Licencia
Proyecto bajo licencia **MIT**.  
© 2025 Iván David Duque Perdomo. Todos los derechos reservados.

---
